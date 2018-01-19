package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.dev.doods.base.R;

import java.util.List;

import Models.SharedFolderNFS;

/**
 * Created by thiba on 28/10/2016.
 */

public class SharedFilderNFSAdapter  extends ArrayAdapter<SharedFolderNFS> {

    public SharedFilderNFSAdapter(Context context, List<SharedFolderNFS> fileSystems)
    {
        super(context,0,fileSystems);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_sharedfolder_nfs,parent, false);
        }

        SharedFilderNFSAdapter.SharedFilderViewHolder viewHolder = (SharedFilderNFSAdapter.SharedFilderViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new SharedFilderNFSAdapter.SharedFilderViewHolder();
            viewHolder.SharedFolderClient = (TextView) convertView.findViewById(R.id.SharedFolderClient);
            viewHolder.SharedFolderName = (TextView) convertView.findViewById(R.id.SharedFolderName);
            viewHolder.SharedFolderOptions = (TextView)  convertView.findViewById(R.id.SharedFolderOptions);
            viewHolder.SharedFolderComment = (TextView)  convertView.findViewById(R.id.SharedFolderComment);
            convertView.setTag(viewHolder);
        }

        SharedFolderNFS data = getItem(position);

        viewHolder.SharedFolderClient.setText(data.getClient());
        viewHolder.SharedFolderName.setText(data.getSharedfoldername());
        viewHolder.SharedFolderOptions.setText(data.getOptions()+","+data.getExtraoptions());
        viewHolder.SharedFolderComment.setText(data.getComment());

        return convertView;
    }

    private class SharedFilderViewHolder{
        public TextView SharedFolderClient;
        public TextView SharedFolderName;
        public TextView SharedFolderOptions;
        public TextView SharedFolderComment;

    }
}
