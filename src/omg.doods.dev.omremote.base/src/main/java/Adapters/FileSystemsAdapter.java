package Adapters;

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

import com.dev.doods.base.R;
import com.dev.doods.base.SharedFoldersActivity;
import com.dev.doods.base.StatisticsScrollingActivity;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.data.PieDataSet;

import java.util.ArrayList;
import java.util.List;

import Models.FileSystem;
import utils.Util;

/**
 * Created by thiba on 06/09/2016.
 */
public class FileSystemsAdapter  extends RecyclerView.Adapter<FileSystemsAdapter.MyViewHolder> {


    private Context mContext;
    private List<FileSystem> mFileSystemList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView overflow;

        public TextView devicefile;
        public TextView available_size;
        public TextView used_size;
        public TextView type;
        public TextView size;
        public PieChart pieChartFileSystems;
        public ImageView colorAvailable;
        public ImageView colorUsed;

        public MyViewHolder(View view) {
            super(view);
            devicefile = (TextView) view.findViewById(R.id.devicefile);
            type = (TextView) view.findViewById(R.id.type);
            size = (TextView) view.findViewById(R.id.size);
            used_size = (TextView) view.findViewById(R.id.Used_size);
            available_size = (TextView) view.findViewById(R.id.Available_size);

            colorAvailable = (ImageView) view.findViewById(R.id.ColorAvailable);
            colorUsed = (ImageView) view.findViewById(R.id.ColorUsed);
            pieChartFileSystems = (PieChart) view.findViewById(R.id.PieChartFileSystems);

            overflow = (ImageView) view.findViewById(R.id.overflow);
        }
    }
    public FileSystemsAdapter(Context mContext, List<FileSystem> fileSystemList) {
        this.mContext = mContext;
        this.mFileSystemList = fileSystemList;
    }

    @Override
    public FileSystemsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_file_systems, parent, false);

        return new FileSystemsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final FileSystemsAdapter.MyViewHolder holder,final int position) {
        final FileSystem data = mFileSystemList.get(position);
        String str = data.getLabel();

        if(str == null || str.isEmpty())
            holder.devicefile.setText(data.getDevicefile());
        else
            holder.devicefile.setText(str);

        holder.type.setText(data.getType());


        Double sizeLong = Double.parseDouble(data.getSize());
        String sizeStr = Util.humanReadableByteCount(sizeLong,false);
        holder.size.setText(sizeStr);

        float usaed =  (float)data.getPercentage();
        float free = 100.0f -usaed;

        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry((float) usaed, 0));
        entries.add(new PieEntry((float) free, 1));

        PieDataSet dataset = new PieDataSet(entries, "");

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("Used");
        labels.add("Free");

        // adding colors
        ArrayList<Integer> colors = new ArrayList<Integer>();

        colors.add(mContext.getResources().getColor(R.color.data_set_use));
        colors.add(mContext.getResources().getColor(R.color.colorOMV));

        dataset.setColors(colors);

        PieData pieData = new PieData(dataset);
        holder.pieChartFileSystems.setData(pieData);
        holder.pieChartFileSystems.setDrawEntryLabels(false);
        holder.pieChartFileSystems.setClickable(false);
        holder.pieChartFileSystems.getDescription().setEnabled(false);
        holder.pieChartFileSystems.getLegend().setEnabled(false);
        holder.pieChartFileSystems.setDrawHoleEnabled(true);
        holder.pieChartFileSystems.setHighlightPerTapEnabled(true);


        Double availableLong = Double.parseDouble(data.getAvailable());
        Double usedLong = sizeLong - availableLong;
        String availableSize = free+" % / "+Util.humanReadableByteCount(availableLong,false);
        String usedSize = usaed+" % / "+Util.humanReadableByteCount(usedLong,false);
        holder.used_size.setText(usedSize);
        holder.available_size.setText(availableSize);



        holder.colorUsed.setBackgroundResource(R.color.data_set_use);
        holder.colorAvailable.setBackgroundResource( R.color.colorOMV);

        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.overflow,position);
            }
        });
    }

    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view,int position) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_filesystem_card, popup.getMenu());
        popup.setOnMenuItemClickListener(new FileSystemsAdapter.MyMenuItemClickListener(position));
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
                mContext.startActivity(new Intent(mContext, SharedFoldersActivity.class));
                return true;
            }
            else if(menuItem.getItemId() ==R.id.action_show_statistics)
            {
                Intent intent = new Intent(mContext, StatisticsScrollingActivity.class);

                final FileSystem data = mFileSystemList.get(mPosition);

                Bundle extras = new Bundle();
                extras.putString("uuid", data.getUuid());

                String str = data.getLabel();

                if(str == null || str.isEmpty())
                    extras.putString("title", data.getDevicefile());
                else
                    extras.putString("title", str);


                intent.putExtras(extras);

                mContext.startActivity(intent);
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
        return mFileSystemList.size();
    }

}
