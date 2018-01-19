package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import com.dev.doods.base.R;

import java.util.List;


import Models.Repo;
import de.halfbit.pinnedsection.PinnedSectionListView;

/**
 * Created by thiba on 14/09/2016.
 */
public class PinnedSectionRepoAdapter extends ArrayAdapter<Repo> implements PinnedSectionListView.PinnedSectionListAdapter {

    public PinnedSectionRepoAdapter(Context context, List<Repo> repos) {
        super(context, 0, repos);

    }

    @Override public View getView(int position, View convertView, final ViewGroup parent) {
        final int pos = position;
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_repo,parent, false);
        }

        PluginsViewHolder viewHolder = (PluginsViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new PluginsViewHolder();
            viewHolder.Repo = (TextView) convertView.findViewById(R.id.Repo);
            viewHolder.IsEnableTxt = (TextView) convertView.findViewById(R.id.IsEnable);
            viewHolder.Description = (TextView) convertView.findViewById(R.id.Description);
            viewHolder.SwitchEnable = (Switch) convertView.findViewById(R.id.SwitchEnable );
            convertView.setTag(viewHolder);
        }


        final Repo data = getItem(position);

        viewHolder.Repo.setText(data.getRepo());

        if (data.typeAffichage == Repo.SECTION) {
            //view.setOnClickListener(PinnedSectionListActivity.this);
            convertView.setBackgroundColor(parent.getResources().getColor(R.color.colorOMV));
            viewHolder.Description.setVisibility(View.GONE);
            viewHolder.SwitchEnable.setVisibility(View.GONE);
            viewHolder.IsEnableTxt.setVisibility(View.GONE);
            return convertView;
        }

        convertView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                PluginsViewHolder viewHolder = (PluginsViewHolder) v.getTag();
                Switch _switch = viewHolder.SwitchEnable;
                _switch.performClick();
            }
        });

        viewHolder.Description.setText(data.getDescription());

        viewHolder.SwitchEnable.setChecked(data.getEnable().equals("1"));

        viewHolder.SwitchEnable.setTag(data);

        viewHolder.SwitchEnable.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Switch _switch = (Switch) v;
                Repo plugins = (Repo) _switch.getTag();

                plugins.setSelected(_switch.isChecked());

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

        return getItem(position).typeAffichage;
    }

    @Override
    public boolean isItemViewTypePinned(int viewType) {
        return viewType == Repo.SECTION;
    }

    private class PluginsViewHolder{
        public TextView Repo;
        public TextView Description;
        public Switch SwitchEnable;
        public TextView IsEnableTxt;
    }
}
