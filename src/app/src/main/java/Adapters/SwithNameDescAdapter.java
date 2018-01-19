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
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.dev.doods.omvremote2.Plugins.Fail2ban.AddEditeJailActivity;
import com.dev.doods.omvremote2.Plugins.Fail2ban.Fail2banController;
import com.dev.doods.omvremote2.Plugins.Fail2ban.Jail;
import com.dev.doods.omvremote2.R;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;

import Client.Call;
import Client.Callback;
import Client.Response;
import Controllers.ConfigController;
import Models.Errors;
import OMVFragment.Dialogs.OutputDialogFragment;

/**
 * Created by Ividata7 on 27/04/2017.
 */

public class SwithNameDescAdapter  extends RecyclerView.Adapter<SwithNameDescAdapter.MyViewHolder> {

    private Fail2banController mController;
    private Context mContext;
    private List<Jail> mList;
    private final AdapterView.OnItemClickListener mListener;
    public View mView;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView overflow;
        public TextView firstLine;
        public TextView secondLine;
        public Switch isEnable;

        public MyViewHolder(View view) {
            super(view);
            mView = view;
            firstLine = (TextView) view.findViewById(R.id.firstLine);
            secondLine = (TextView) view.findViewById(R.id.secondLine);
            isEnable = (Switch) view.findViewById(R.id.IsEnable);
            isEnable.setClickable(false);
            overflow = (ImageView) view.findViewById(R.id.overflow);
        }
    }

    public SwithNameDescAdapter(Context mContext, List<Jail> List, AdapterView.OnItemClickListener listener,Fail2banController controller) {
        this.mContext = mContext;
        this.mList = List;
        this.mListener = listener;
        this.mController = controller;
        configController = new ConfigController(mController.GetActivity());
    }

    @Override
    public SwithNameDescAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_swith_name_desc, parent, false);

        return new SwithNameDescAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder,final int position) {
        final Jail data = mList.get(position);

        holder.firstLine.setText(data.getName());
        holder.secondLine.setText(data.getDescription());
        holder.isEnable.setChecked(data.getEnable());
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
        popup.setOnMenuItemClickListener(new SwithNameDescAdapter.MyMenuItemClickListener(position));
        popup.show();
    }

    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {
        private Handler handler= new Handler();
        private int mPosition;
        public MyMenuItemClickListener(int position) {
            mPosition = position;
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {

            if(menuItem.getItemId() ==R.id.action_delete)
            {
                Jail jail = mList.get(mPosition);
                mController.deleteJail(jail.getUuid(), new Callback() {
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
                                mList.remove(mPosition);
                                notifyDataSetChanged();
                            }
                        });


                    }
                });
                return true;
            }
            else if(menuItem.getItemId() ==R.id.action_select)
            {
                return true;
            }
            else if(menuItem.getItemId() ==R.id.action_edite)
            {
                Intent mIntent = new Intent(mContext, AddEditeJailActivity.class);
                Bundle extras = new Bundle();
                extras.putSerializable(AddEditeJailActivity.JAIL_BUNDLE_KEY, mList.get(mPosition));
                mIntent.putExtras(extras);
                mContext.startActivity(mIntent);
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
                                                        //outputController.IsRunningFile(filename);
                                                        CreateDialog(true,"Apply Jail",filename);
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
                        TextView textView = (TextView) sbView.findViewById(R.id.snackbar_text);
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
        private OutputDialogFragment mOutputDialogFragment;
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
    }

    final ConfigController configController;

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
