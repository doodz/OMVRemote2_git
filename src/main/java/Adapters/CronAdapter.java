package Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.dev.doods.omvremote2.AddEditeCronActivity;
import com.dev.doods.base.R;
import com.google.gson.reflect.TypeToken;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.List;

import Client.Call;
import Client.Callback;
import Client.Response;
import Controllers.CronController;
import Models.Cron;
import Models.Errors;
import OMVFragment.Dialogs.OutputDialogFragment;

/**
 * Created by thiba on 19/12/2016.
 */

public class CronAdapter  extends RecyclerView.Adapter<CronAdapter.MyViewHolder> {
    private Context mContext;
    private List<Cron> mCronList;
    private CronController mController;
    private OutputDialogFragment mOutputDialogFragment;
    public class MyViewHolder extends RecyclerView.ViewHolder {


        public TextView SchedulingView;
        public TextView UserView;
        public TextView CommandView;
        public Switch swithEnableView;
        public ImageView overflow;


        public MyViewHolder(View view) {
            super(view);
            SchedulingView = (TextView) view.findViewById(R.id.Scheduling);
            UserView = (TextView) view.findViewById(R.id.TvUser);
            CommandView = (TextView) view.findViewById(R.id.TvCommand);
            swithEnableView = (Switch) view.findViewById(R.id.SwitchEnable);

            overflow = (ImageView) view.findViewById(R.id.overflow);

        }
    }
    public CronAdapter(Context mContext, List<Cron> cronList, CronController controller) {
        this.mContext = mContext;
        this.mCronList = cronList;
        this.mController = controller;

    }

    @Override
    public CronAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_cron, parent, false);

        return new CronAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CronAdapter.MyViewHolder holder,final int position) {
        final Cron data = mCronList.get(position);
        holder.SchedulingView.setText(data.getScheduling());
        holder.UserView.setText(data.getUsername());

        if(!StringUtils.isEmpty(data.getComment()))
            holder.CommandView.setText(data.getComment());
        else
            holder.CommandView.setText(data.getCommand());
        holder.swithEnableView.setChecked(data.getEnable());

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
        inflater.inflate(R.menu.menu_cron_card, popup.getMenu());
        popup.setOnMenuItemClickListener(new CronAdapter.MyMenuItemClickListener(position,view));
        popup.show();
    }

    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        private int mPosition;
        private View mView;
        private Handler handler= new Handler();
        public MyMenuItemClickListener(int position,View view) {
            mPosition = position;
            mView = view;
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {

            if(menuItem.getItemId() ==R.id.action_run)
            {
                Cron c =mCronList.get(mPosition);
                mController.executeCron(c.getUuid(), new Callback() {
                    @Override
                    public void onFailure(Call call, Exception e) {

                    }

                    @Override
                    public void OnOMVServeurError(Call call, Errors error) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException, InterruptedException {

                        final String filename = response.GetResultObject( new TypeToken<String>(){});
                        handler.post(new Runnable(){
                            public void run() {
                                CreateDialog(filename);
                            }
                        });
                    }
                });
                return true;
            }
            else if(menuItem.getItemId() ==R.id.action_edite)
            {
                Intent mIntent = new Intent(mContext, AddEditeCronActivity.class);
                Bundle extras = new Bundle();
                extras.putSerializable("Cron", mCronList.get(mPosition));
                mIntent.putExtras(extras);
                mContext.startActivity(mIntent);
                return true;
            }
            else if(menuItem.getItemId() ==R.id.action_delete)
            {

                Cron cc =mCronList.get(mPosition);
                mController.deleteCron(cc.getUuid(),null);
                mCronList.remove(mPosition);
                notifyDataSetChanged();
                return true;
            }
            else
            {
                return false;
            }




        }



    }

    private void CreateDialog(String fimeName)
    {

        Bundle bundle = new Bundle();
        bundle.putBoolean("justWaitCursor", false);
        bundle.putString("title", mController.GetActivity().getString(R.string.ExcecCron));
        bundle.putString("fileName", fimeName);


        // Create an instance of the dialog fragment and show it
        mOutputDialogFragment = new OutputDialogFragment();

        //mOutputDialogFragment.setListener(this);
        mOutputDialogFragment.setArguments(bundle);
        android.app.FragmentManager fm = mController.GetActivity().getFragmentManager();
        mOutputDialogFragment.show(fm, "OutputDialogFragment");
    }


    @Override
    public int getItemCount() {
        return mCronList.size();
    }

}
