package Adapters;

import android.content.Context;
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
import android.widget.TextView;

import com.dev.doods.base.R;

import java.util.List;

import Controllers.VirtualBoxController;
import Models.VirtualBoxMachine;
import OMVFragment.Dialogs.OutputDialogFragment;

/**
 * Created by Ividata7 on 20/01/2017.
 */

public class VirtualBoxMachineRecyclerAdapter extends RecyclerView.Adapter<VirtualBoxMachineRecyclerAdapter.MyViewHolder> {
    private Context mContext;
    private List<VirtualBoxMachine> mVirtualBoxMachineList;
    private VirtualBoxController mController;
    private OutputDialogFragment mOutputDialogFragment;
    private Handler handler = new Handler();
    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView VirtualMachineName;
        public TextView state;
        public TextView StatUpMode;
        public ImageView overflowInfo;

        public MyViewHolder(View view) {
            super(view);
            VirtualMachineName = (TextView) view.findViewById(R.id.VirtualMachineName);
            state = (TextView) view.findViewById(R.id.state);
            StatUpMode = (TextView) view.findViewById(R.id.StatUpMode);
            overflowInfo = (ImageView) view.findViewById(R.id.overflowInfo);
        }
    }
    public VirtualBoxMachineRecyclerAdapter(Context mContext, List<VirtualBoxMachine> List, VirtualBoxController controller) {
        this.mContext = mContext;
        this.mVirtualBoxMachineList = List;
        this.mController = controller;
    }

    @Override
    public VirtualBoxMachineRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_virtualmachine, parent, false);

        return new VirtualBoxMachineRecyclerAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final VirtualBoxMachineRecyclerAdapter.MyViewHolder holder,final int position) {

        final VirtualBoxMachine data = mVirtualBoxMachineList.get(position);
        holder.VirtualMachineName.setText(data.getName());
        holder.state.setText(String.format(" (%s) ",data.getState()));
        holder.StatUpMode.setText(data.getStartupMode());

        holder.overflowInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.overflowInfo,position);
            }
        });
    }

    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view,int position) {

        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_virtualboxmachine, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener(position,view));
        popup.show();
    }

    private void CreateDialog(Boolean justWaitCursor,String title,String fimeName)
    {

        Bundle bundle = new Bundle();
        bundle.putBoolean("justWaitCursor", justWaitCursor);
        bundle.putString("title", title);
        bundle.putString("fileName", fimeName);

        // Create an instance of the dialog fragment and show it
        mOutputDialogFragment = new OutputDialogFragment();
        //mOutputDialogFragment.AddListener(this);
        //mOutputDialogFragment.setListener(this);
        mOutputDialogFragment.setArguments(bundle);
        android.app.FragmentManager fm = mController.GetActivity().getFragmentManager();
        mOutputDialogFragment.show(fm, "OutputDialogFragment");
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
            VirtualBoxMachine vbm = mVirtualBoxMachineList.get(mPosition);
            if(menuItem.getItemId() ==R.id.action_powerButton)
            {
                mController.setMachineState("powerButton",vbm.getUuid(),null);
                return true;
            }
            else if(menuItem.getItemId() ==R.id.action_powerDown)
            {
                mController.setMachineState("powerDown",vbm.getUuid(),null);
                return true;
            }
            else if(menuItem.getItemId() ==R.id.action_powerUp)
            {

                mController.setMachineState("powerUp",vbm.getUuid(),null);
                return true;
            }
            else if(menuItem.getItemId() ==R.id.action_pause)
            {

                mController.setMachineState("pause",vbm.getUuid(),null);
                return true;
            }
            else if(menuItem.getItemId() ==R.id.action_saveState)
            {
                mController.setMachineState("saveState",vbm.getUuid(),null);

                return true;
            }
            else if(menuItem.getItemId() ==R.id.action_reset)
            {
                mController.setMachineState("reset",vbm.getUuid(),null);

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
        return mVirtualBoxMachineList.size();
    }
}

