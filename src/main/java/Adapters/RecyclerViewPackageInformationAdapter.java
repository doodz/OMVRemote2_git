package Adapters;


import java.util.List;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.dev.doods.base.R;

import Models.PackageInformation;

/**
 * Created by thiba on 01/09/2016.
 */
public class RecyclerViewPackageInformationAdapter   extends RecyclerView.Adapter<RecyclerViewPackageInformationAdapter.ViewHolder> {

    /**
     * List items
     */
    private  List<PackageInformation> items;
    /**
     * the resource id of item Layout
     */
    private int itemLayout;

    /**
     * Constructor RecyclerSimpleViewAdapter
     * @param items : the list items
     * @param itemLayout : the resource id of itemView
     */
    public RecyclerViewPackageInformationAdapter( List<PackageInformation> items, int itemLayout) {
        this.items = items;
        this.itemLayout = itemLayout;
    }

    /**
     * Create View Holder by Type
     * @param parent, the view parent
     * @param viewType : the type of View
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // get inflater and get view by resource id itemLayout
        View v = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
        // return ViewHolder with View
        return new ViewHolder(v);
    }

    /**
     * Get the size of items in adapter
     * @return the size of items in adapter
     */
    @Override
    public int getItemCount() {
        return items.size();
    }
    /**
     * Bind View Holder with Items
     * @param viewHolder: the view holder
     * @param position : the current position
     */
    @Override
    public void onBindViewHolder(RecyclerViewPackageInformationAdapter.ViewHolder viewHolder, int position) {
        // find item by position
        PackageInformation data = items.get(position);
        viewHolder.title.setText(data.getName());

        viewHolder.sourceversion.setText(String.format(" (%s) ",data.getSourceversion()));
        viewHolder.coprs.setText(data.getAbstract());


        viewHolder.checkbox.setTag(data);
        viewHolder.checkbox.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                PackageInformation packageInformation = (PackageInformation) cb.getTag();

                packageInformation.setSelected(cb.isChecked());

                //ListView lstv= (ListView)parent;

                //lstv.setSelection(pos);

                // lstv.get(pos).setSelected(cb.isChecked());

            }
        });
    }
    /**
     *
     * @author florian
     * Class viewHolder
     * Hold an textView
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // TextViex
        public TextView title;
        public TextView sourceversion;
        public TextView coprs;
        public CheckBox checkbox;
        /**
         * Constructor ViewHolder
         * @param itemView: the itemView
         */
        public ViewHolder(View itemView) {
            super(itemView);
            // link primaryText
            title = (TextView) itemView.findViewById(R.id.PackageInformation_Title);
            sourceversion = (TextView) itemView.findViewById(R.id.PackageInformation_Sourceversion);
            coprs = (TextView) itemView.findViewById(R.id.PackageInformation_Corps);
            checkbox = (CheckBox)itemView.findViewById(R.id.checkBox);
        }
    }
}