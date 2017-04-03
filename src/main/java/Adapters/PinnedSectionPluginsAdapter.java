package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import com.dev.doods.base.R;

import java.util.List;

import Models.Plugins;
import de.halfbit.pinnedsection.PinnedSectionListView;

/**
 * Created by thiba on 08/09/2016.
 */
public class PinnedSectionPluginsAdapter extends ArrayAdapter<Plugins> implements PinnedSectionListView.PinnedSectionListAdapter {


    private  List<Plugins> source;

    private static final int[] COLORS = new int[] {
            R.color.pinned_green_light, R.color.pinned_orange_light,
            R.color.pinned_blue_light, R.color.pinned_red_light };

    public PinnedSectionPluginsAdapter(Context context,  List<Plugins> plugins) {
        super(context, 0, plugins);
        source = plugins;
    }

    @Override public View getView(int position, View convertView, final ViewGroup parent) {
        final int pos = position;
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_plugin,parent, false);
        }

        PluginsViewHolder viewHolder = (PluginsViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new PluginsViewHolder();
            viewHolder.Name = (TextView) convertView.findViewById(R.id.Name);
            viewHolder.Summary = (TextView) convertView.findViewById(R.id.Summary);
            viewHolder.extendeddescription = (TextView) convertView.findViewById(R.id.extendeddescription);
            viewHolder.checkbox = (CheckBox)convertView.findViewById(R.id.checkBox);
            viewHolder.switchInstalled = (Switch) convertView.findViewById(R.id.switchInstalled );
            convertView.setTag(viewHolder);
        }


        final Plugins data = getItem(position);

        viewHolder.Name.setText(data.getName());

        if (data.type == Plugins.SECTION) {
            //view.setOnClickListener(PinnedSectionListActivity.this);
            convertView.setBackgroundColor(parent.getResources().getColor(R.color.colorOMV));
            viewHolder.Summary.setVisibility(View.GONE);
            viewHolder.extendeddescription.setVisibility(View.GONE);
            viewHolder.checkbox.setVisibility(View.GONE);
            viewHolder.switchInstalled.setVisibility(View.GONE);
            return convertView;
        }

        convertView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                PluginsViewHolder viewHolder = (PluginsViewHolder) v.getTag();
                CheckBox checkbox = viewHolder.checkbox;
                checkbox.performClick();
            }
        });

        viewHolder.Summary.setText(String.format(" (%s) ",data.getSummary()));
        viewHolder.extendeddescription.setText(data.getExtendeddescription());
        viewHolder.switchInstalled.setChecked(data.getInstalled());

        viewHolder.checkbox.setTag(data);
        viewHolder.checkbox.setChecked(data.isSelected());
        viewHolder.checkbox.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                Plugins plugins = (Plugins) cb.getTag();

                plugins.setSelected(cb.isChecked());

                ListView lstv= (ListView)parent;

                //lstv.setSelection(pos);

                //lstv.get(pos).setSelected(cb.isChecked());

            }
        });


        return convertView;
    }
    @Override public int getViewTypeCount() {
        return 2;
    }

    @Override public int getItemViewType(int position) {

        return getItem(position).type;
    }

    @Override
    public boolean isItemViewTypePinned(int viewType) {
        return viewType == Plugins.SECTION;
    }


    public void UpdateSource(List<Plugins> lst)
    {
        this.source.clear();
        this.source.addAll(lst);
        this.notifyDataSetChanged();
    }

    private class PluginsViewHolder{
        public TextView Name;
        public TextView Summary;
        public TextView extendeddescription;
        public CheckBox checkbox;
        public Switch switchInstalled;

    }
}
