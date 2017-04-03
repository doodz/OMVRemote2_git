package Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
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

import com.dev.doods.omvremote2.EditeCertificateActivity;
import com.dev.doods.omvremote2.R;
import com.google.gson.reflect.TypeToken;

//import org.acra.ACRA;

import java.io.IOException;
import java.util.List;

import Client.Call;
import Client.Callback;
import Client.Response;
import Controllers.CertificateController;
import Controllers.ConfigController;
import Models.Certificate;
import Models.Errors;
import OMVFragment.Dialogs.OutputDialogFragment;

/**
 * Created by thiba on 28/11/2016.
 */

public class CertificateAdapter extends RecyclerView.Adapter<CertificateAdapter.MyViewHolder>  {


    private Context mContext;
    private List<Certificate> mCertificateList;
    private CertificateController mController;
    final ConfigController configController ;
    private OutputDialogFragment mOutputDialogFragment;
    //final OutputController outputController ;
    public class MyViewHolder extends RecyclerView.ViewHolder {


        public TextView NameView;
        public Switch swithEnableView;
        public ImageView overflow;

        public MyViewHolder(View view) {
            super(view);
            NameView = (TextView) view.findViewById(R.id.Name);
            swithEnableView = (Switch) view.findViewById(R.id.swithEnable);

            overflow = (ImageView) view.findViewById(R.id.overflow);

        }
    }
    public CertificateAdapter(Context mContext, List<Certificate> certificateList,CertificateController controller) {
        this.mContext = mContext;
        this.mCertificateList = certificateList;
        this.mController = controller;
        configController = new ConfigController(mController.GetActivity());
        //outputController = new OutputController(mController.GetActivity());
    }

    @Override
    public CertificateAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_certificate, parent, false);

        return new CertificateAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CertificateAdapter.MyViewHolder holder,final int position) {
        final Certificate data = mCertificateList.get(position);
        holder.NameView.setText(data.getName());


        holder.swithEnableView.setChecked(data.getUsed() == null?false: data.getUsed());
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
        inflater.inflate(R.menu.menu_certificate_card, popup.getMenu());
        popup.setOnMenuItemClickListener(new CertificateAdapter.MyMenuItemClickListener(position,view));
        popup.show();
    }

    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        private int mPosition;
        private View mView;
        private Handler handler= new Handler();

        public MyMenuItemClickListener(int position, View view) {

            mPosition = position;
            mView = view;
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {

            if(menuItem.getItemId() ==R.id.action_edite)
            {
                Intent mIntent = new Intent(mContext, EditeCertificateActivity.class);
                Bundle extras = new Bundle();
                extras.putSerializable("Certificate", mCertificateList.get(mPosition));
                mIntent.putExtras(extras);
                mContext.startActivity(mIntent);
                return true;
            }
            else if(menuItem.getItemId() ==R.id.action_detail)
            {

                String str = mContext.getString(R.string.not_implemented);
                Snackbar.make(mView, str, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                return true;
            }
            else if(menuItem.getItemId() ==R.id.action_delete)
            {
                Certificate cert = mCertificateList.get(mPosition);

                if (cert.getUsed() != null && cert.getUsed()) {
                    String str2 = mContext.getString(R.string.certificate_is_used);
                    Snackbar.make(mView, str2, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return true;
                }
                mController.DeleteSsl(cert.getUuid(), new Callback() {
                    @Override
                    public void onFailure(Call call, Exception e) {

                    }

                    @Override
                    public void OnOMVServeurError(Call call, Errors error) {
                        showOMVServeurError(error);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException, InterruptedException {
                        handler.post(new Runnable(){
                            public void run() {
                                mCertificateList.remove(mPosition);
                                notifyDataSetChanged();
                            }
                        });

                    }
                });

                return true;
            }
            else
            {
                return false;
            }
        }


        private void showOMVServeurError(final Errors error)
        {

            configController.isDirty(new Callback() {
                @Override
                public void onFailure(Call call, Exception e) {

                }

                @Override
                public void OnOMVServeurError(Call call, Errors error) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException, InterruptedException {

                    final Boolean needupdate =  response.GetResultObject(new TypeToken<Boolean>(){});

                    if(needupdate)
                    {

                        Snackbar snackbar = Snackbar
                                .make(mView, error.getMessage(), Snackbar.LENGTH_LONG)
                                .setAction("Apply", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        configController.applyChangesBg(new Callback() {
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
                                                        CreateDialog(true,"Apply certificate",filename);

                                                    }
                                                });


                                            }
                                        });
                                    }
                                });

                        // Changing message text color
                        snackbar.setActionTextColor(Color.RED);

                        // Changing action button text color
                        View sbView = snackbar.getView();
                        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                        textView.setTextColor(Color.YELLOW);
                        snackbar.show();
                    }
                    else
                    {
                        Snackbar.make(mView, error.getMessage(), Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                }
            });




        }
    }

    private void CreateDialog(Boolean justWaitCursor,String title,String fimeName)
    {

        Bundle bundle = new Bundle();
        bundle.putBoolean("justWaitCursor", justWaitCursor);
        bundle.putString("title", title);
        bundle.putString("fileName", fimeName);


        // Create an instance of the dialog fragment and show it
        mOutputDialogFragment = new OutputDialogFragment();

        //mOutputDialogFragment.setListener(this);
        mOutputDialogFragment.setArguments(bundle);
        android.app.FragmentManager fm = configController.GetActivity().getFragmentManager();
        mOutputDialogFragment.show(fm, "OutputDialogFragment");
    }

    @Override
    public int getItemCount() {
        return mCertificateList.size();
    }

}
