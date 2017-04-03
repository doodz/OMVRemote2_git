package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.dev.doods.base.R;

import java.util.List;

import Models.PackageInformation;

/**
 * Created by thiba on 31/08/2016.
 */
public class PackageInformationAdapter  extends ArrayAdapter<PackageInformation> {

    List<PackageInformation> stList;

    public PackageInformationAdapter(Context context, List<PackageInformation> packageInformations)
    {
        super(context,0,packageInformations);
        stList = packageInformations;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {

        final int pos = position;
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_packageinformation,parent, false);
        }

        ServicesViewHolder viewHolder = (ServicesViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new ServicesViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.PackageInformation_Title);
            viewHolder.sourceversion = (TextView) convertView.findViewById(R.id.PackageInformation_Sourceversion);
            viewHolder.coprs = (TextView) convertView.findViewById(R.id.PackageInformation_Corps);
            viewHolder.checkbox = (CheckBox)convertView.findViewById(R.id.checkBox);

            convertView.setTag(viewHolder);
        }


        final PackageInformation data = getItem(position);


        convertView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                CheckBox checkbox = (CheckBox)v.findViewById(R.id.checkBox);
                checkbox.performClick();
            }
        });

        viewHolder.title.setText(data.getName());

        viewHolder.sourceversion.setText(String.format(" (%s) ",data.getSourceversion()));
        viewHolder.coprs.setText(data.getAbstract());

        viewHolder.checkbox.setChecked(data.isSelected());
        viewHolder.checkbox.setTag(data);
        viewHolder.checkbox.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                PackageInformation packageInformation = (PackageInformation) cb.getTag();

                packageInformation.setSelected(cb.isChecked());

                ListView lstv= (ListView)parent;

                lstv.setSelection(pos);

               // lstv.get(pos).setSelected(cb.isChecked());

            }
        });
        return convertView;
    }

    private class ServicesViewHolder{
        public TextView title;
        public TextView sourceversion;
        public TextView coprs;
        public CheckBox checkbox;

    }
}


