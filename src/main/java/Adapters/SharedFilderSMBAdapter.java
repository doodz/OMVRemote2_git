package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Switch;
import android.widget.TextView;

import com.dev.doods.base.R;

import java.util.List;

import Models.Shares;

/**
 * Created by thiba on 28/10/2016.
 */

public class SharedFilderSMBAdapter extends ArrayAdapter<Shares> {

    public SharedFilderSMBAdapter(Context context, List<Shares> fileSystems)
    {
        super(context,0,fileSystems);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_sharedfolder_smb,parent, false);
        }

        SharedFilderSMBAdapter.SharedFilderViewHolder viewHolder = (SharedFilderSMBAdapter.SharedFilderViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new SharedFilderSMBAdapter.SharedFilderViewHolder();
            viewHolder.SharedFolder = (TextView) convertView.findViewById(R.id.SharedFolder);
            viewHolder.SharedFolderName = (TextView) convertView.findViewById(R.id.SharedFolderName);
            viewHolder.SwitchEnable = (Switch)  convertView.findViewById(R.id.SwitchEnable);
            viewHolder.Public = (Switch)  convertView.findViewById(R.id.Public);
            viewHolder.ReadOnly = (Switch)  convertView.findViewById(R.id.ReadOnly);
            viewHolder.Browsable = (Switch)  convertView.findViewById(R.id.Browsable);

            convertView.setTag(viewHolder);
        }

        Shares data = getItem(position);

        viewHolder.SharedFolder.setText(data.getSharedfolderref());
        viewHolder.SharedFolderName.setText(data.getName());
        viewHolder.SwitchEnable.setChecked((data.getEnable().equals("1")));

        //TODO trouver la variable public.

        //viewHolder.Public.setChecked((data.get().equals("1")));
        viewHolder.ReadOnly.setChecked((data.getReadonly().equals("1")));
        viewHolder.Browsable.setChecked((data.getBrowseable().equals("1")));

        return convertView;
    }

    private class SharedFilderViewHolder{
        public TextView SharedFolder;
        public TextView SharedFolderName;
        public Switch SwitchEnable;
        public Switch Public;
        public Switch ReadOnly;
        public Switch Browsable;
    }
}
