package Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.doods.omvremote2.R;

import java.util.List;

import Client.JSONRPCClient;
import Models.StatisticItem;

/**
 * Created by thiba on 15/11/2016.
 */

public class StatisticItemAdapter extends RecyclerView.Adapter<StatisticItemAdapter.MyViewHolder>{

    private Context mContext;
    private List<StatisticItem> statisticItemList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView thumbnail, overflow;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            overflow = (ImageView) view.findViewById(R.id.overflow);
        }
    }


    public StatisticItemAdapter(Context mContext, List<StatisticItem> statisticItemList) {
        this.mContext = mContext;
        this.statisticItemList = statisticItemList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_statistic_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final StatisticItem statisticItem = statisticItemList.get(position);
        holder.title.setText(statisticItem.getName());


        // loading album cover using Glide library



        Thread t = new Thread() {
            public void run() {
                final Bitmap bitmap = JSONRPCClient.getInstance().getBitMap(statisticItem.getUrl());
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        holder.thumbnail.setImageBitmap(bitmap);
                    }

            });
        }};
        t.start();
        //Glide.with(mContext).load(album.getThumbnail()).into(holder.thumbnail);

        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.overflow);
            }
        });
        holder.overflow.setVisibility(View.GONE);
    }

    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_statisticitem, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }

    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            if(menuItem.getItemId() ==R.id.action_add_favourite)
            {
                Toast.makeText(mContext, "toto", Toast.LENGTH_SHORT).show();
                return true;
            }
            else if(menuItem.getItemId() ==R.id.action_play_next)
            {
                Toast.makeText(mContext, "tata", Toast.LENGTH_SHORT).show();
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
        return statisticItemList.size();
    }
}
