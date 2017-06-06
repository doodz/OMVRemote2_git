package com.dev.doods.omvremote2.Storage.Smart;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev.doods.omvremote2.R;
import com.dev.doods.omvremote2.StatisticsScrollingActivity;
import com.dev.doods.omvremote2.Storage.FileSystems.FileSystem;
import com.dev.doods.omvremote2.Storage.FileSystems.FileSystemsAdapter;
import com.dev.doods.omvremote2.Storage.SharedFoldersActivity;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

import utils.Util;

/**
 * Created by Ividata7 on 10/05/2017.
 */

public class SmartDeviceAdapter  extends RecyclerView.Adapter<SmartDeviceAdapter.MyViewHolder> {


    private Context mContext;
    private List<SmartDevices> mSmartDevicesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView devicefile;
        public TextView Model;
        public TextView size;
        public TextView Temperature;
        public ImageView overallstatus;

        public MyViewHolder(View view) {
            super(view);
            devicefile = (TextView) view.findViewById(R.id.devicefile);
            Model = (TextView) view.findViewById(R.id.Model);
            size = (TextView) view.findViewById(R.id.size);
            Temperature = (TextView) view.findViewById(R.id.Temperature);
            overallstatus = (ImageView) view.findViewById(R.id.overallstatus);
        }
    }
    public SmartDeviceAdapter(Context mContext, List<SmartDevices> fileSystemList) {
        this.mContext = mContext;
        this.mSmartDevicesList = fileSystemList;
    }

    @Override
    public SmartDeviceAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_smart_device, parent, false);

        return new SmartDeviceAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final SmartDeviceAdapter.MyViewHolder holder,final int position) {
        final SmartDevices data = mSmartDevicesList.get(position);

            holder.devicefile.setText(data.getDevicefile());

        holder.Model.setText(data.getModel());
        holder.size.setText(Util.humanReadableByteCount(Double.parseDouble(data.getSize()),false));
        holder.Temperature.setText(data.getTemperature());

        if(!data.getOverallstatus().equals("GOOD"))
        {
            holder.overallstatus.setImageResource(R.drawable.ic_round_red_24dp);
        }
        else
            holder.overallstatus.setImageResource(R.drawable.ic_round_green_24dp);

    }

    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view,int position) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_filesystem_card, popup.getMenu());
        popup.setOnMenuItemClickListener(new SmartDeviceAdapter.MyMenuItemClickListener(position));
        popup.show();
    }

    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        private int mPosition;
        public MyMenuItemClickListener(int position) {
            mPosition = position;
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {

            if(menuItem.getItemId() ==R.id.action_show_shared_folders)
            {
                //mContext.startActivity(new Intent(mContext, SharedFoldersActivity.class));
                return true;
            }
            else if(menuItem.getItemId() ==R.id.action_show_statistics)
            {
               /*
                Intent intent = new Intent(mContext, StatisticsScrollingActivity.class);

                final SmartDevices data = mSmartDevicesList.get(mPosition);

                Bundle extras = new Bundle();
                extras.putString("uuid", data.getUuid());

                String str = data.getLabel();

                if(str == null || str.isEmpty())
                    extras.putString("title", data.getDevicefile());
                else
                    extras.putString("title", str);


                intent.putExtras(extras);

                mContext.startActivity(intent);
                */
                return true;
            }
            else if(menuItem.getItemId() ==R.id.action_delete)
            {
                return true;
            }
            else
            {
                return false;
            }

        }
    }

    @Override
    public int getItemCount() {
        return mSmartDevicesList.size();
    }


}
