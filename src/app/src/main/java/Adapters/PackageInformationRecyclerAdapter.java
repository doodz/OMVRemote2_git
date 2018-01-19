package Adapters;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev.doods.omvremote2.R;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;

import Client.Call;
import Client.Callback;
import Client.Response;
import Controllers.UpdateController;
import Models.Errors;
import Models.PackageInformation;
import OMVFragment.Dialogs.OutputDialogFragment;

/**
 * Created by Ividata7 on 09/01/2017.
 */

public class PackageInformationRecyclerAdapter  extends RecyclerView.Adapter<PackageInformationRecyclerAdapter.MyViewHolder> {
    private Context mContext;
    private List<PackageInformation> mPackageInformationList;
    private UpdateController mController;
    private OutputDialogFragment mOutputDialogFragment;
    private Handler handler = new Handler();
    public class MyViewHolder extends RecyclerView.ViewHolder {


        public TextView PackageInformation_Title;
        public TextView PackageInformation_Sourceversion;
        public TextView PackageInformation_Corps;
        public CheckBox checkBoxView;
        public ImageView overflowInfo;


        public MyViewHolder(View view) {
            super(view);
            PackageInformation_Title = (TextView) view.findViewById(R.id.PackageInformation_Title);
            PackageInformation_Sourceversion = (TextView) view.findViewById(R.id.PackageInformation_Sourceversion);
            PackageInformation_Corps = (TextView) view.findViewById(R.id.PackageInformation_Corps);

            checkBoxView = (CheckBox) view.findViewById(R.id.checkBox);
            overflowInfo = (ImageView) view.findViewById(R.id.overflowInfo);
        }
    }
    public PackageInformationRecyclerAdapter(Context mContext, List<PackageInformation> repoV3List, UpdateController controller) {
        this.mContext = mContext;
        this.mPackageInformationList = repoV3List;
        this.mController = controller;
    }

    @Override
    public PackageInformationRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_packageinformation, parent, false);

        return new PackageInformationRecyclerAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final PackageInformationRecyclerAdapter.MyViewHolder holder,final int position) {

        final PackageInformation data = mPackageInformationList.get(position);
        holder.PackageInformation_Title.setText(data.getName());
        holder.PackageInformation_Sourceversion.setText(String.format(" (%s) ",data.getSourceversion()));
        holder.PackageInformation_Corps.setText(data.getAbstract());
        holder.checkBoxView.setChecked(data.isSelected());

        holder.checkBoxView.setTag(data);
        holder.checkBoxView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                PackageInformation packageInformation = (PackageInformation) cb.getTag();

                packageInformation.setSelected(cb.isChecked());

                //ListView lstv= (ListView)parent;

                //lstv.setSelection(pos);

                // lstv.get(pos).setSelected(cb.isChecked());

            }
        });

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

        PackageInformation pi = mPackageInformationList.get(position);
        mController.getChangeLog(pi.getFilename(), new Callback() {
            @Override
            public void onFailure(Call call, Exception e) {

            }

            @Override
            public void OnOMVServeurError(Call call, Errors error) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException, InterruptedException {


               final String filename =  response.GetResultObject(new TypeToken<String>(){});



                handler.post(new Runnable(){
                    public void run() {
                CreateDialog(false,"ChangeLog",filename);
                    }
                });
            }
        });
        // inflate menu
        //PopupMenu popup = new PopupMenu(mContext, view);
        //MenuInflater inflater = popup.getMenuInflater();
        //inflater.inflate(R.menu.menu_certificate_card, popup.getMenu());
        //popup.setOnMenuItemClickListener(new PackageInformationRecyclerAdapter.MyMenuItemClickListener(position,view));
        //popup.show();
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
            if(menuItem.getItemId() ==R.id.action_edite)
            {

                return true;
            }
            else if(menuItem.getItemId() ==R.id.action_detail)
            {

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
        return mPackageInformationList.size();
    }
}
