package Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev.doods.omvremote2.R;

import java.io.IOException;
import java.util.List;

import Classes.FakeJSONRPCParamsBuilder;
import Client.AsyncCall;
import Client.Call;
import Client.Callback;
import Client.JSONRPCClient;
import Client.Response;
import DAL.CommandDAO;
import Models.Errors;
import Models.bd.Command;
import OMV.Base.AppCompatBaseActivity;

/**
 * Created by Ividata7 on 10/04/2017.
 */

public class CommandAdapter  extends RecyclerView.Adapter<CommandAdapter.MyViewHolder> {

    protected Handler mHandler = new Handler();
    private AppCompatBaseActivity mContext;
    private List<Command> mCommandList;
    public View mView;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView overflow;
        public TextView firstLine;
        public TextView secondLine;

        public MyViewHolder(View view) {
            super(view);
            mView = view;
            firstLine = (TextView) view.findViewById(R.id.firstLine);
            secondLine = (TextView) view.findViewById(R.id.secondLine);

            overflow = (ImageView) view.findViewById(R.id.overflow);
        }
    }

    public CommandAdapter(AppCompatBaseActivity mContext, List<Command> hostList) {
        this.mContext = mContext;
        this.mCommandList = hostList;

    }

    @Override
    public CommandAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_host, parent, false);

        return new CommandAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CommandAdapter.MyViewHolder holder,final int position) {
        final Command data = mCommandList.get(position);

        holder.firstLine.setText(data.getName());

        holder.secondLine.setVisibility(View.GONE);

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
        inflater.inflate(R.menu.menu_command, popup.getMenu());
        popup.setOnMenuItemClickListener(new CommandAdapter.MyMenuItemClickListener(position));
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

            if(menuItem.getItemId() ==R.id.action_delete)
            {
                Command cmd = mCommandList.get(mPosition);
                CommandDAO datasource = new CommandDAO(mContext);
                datasource.open();
                datasource.deleteCommand(cmd);
                datasource.close();
                mCommandList.remove(mPosition);
                notifyDataSetChanged();
                return true;
            }
            else if(menuItem.getItemId() ==R.id.action_exec)
            {
                Command cmd = mCommandList.get(mPosition);

                Callback callBack = new Callback() {
                    @Override
                    public void onFailure(Call call, final Exception e) {



                        mHandler.post(new Runnable(){
                            public void run() {

                                complain(e.getMessage());
                            }
                        });

                    }

                    @Override
                    public void OnOMVServeurError(Call call,final Errors error) {
                        mHandler.post(new Runnable(){
                            public void run() {

                                complain(error.getMessage());
                            }
                        });

                    }

                    @Override
                    public void onResponse(Call call,final Response response) throws IOException, InterruptedException {
                        mHandler.post(new Runnable(){
                            public void run() {
                                alert(response.GetJsonResult().toString());
                            }
                        });

                    }
                };


                FakeJSONRPCParamsBuilder fakeParamsBuilder = new FakeJSONRPCParamsBuilder();
                fakeParamsBuilder.params = cmd.getCommand();

                AsyncCall call = new AsyncCall(callBack,fakeParamsBuilder,mContext, JSONRPCClient.getInstance());

                JSONRPCClient.getInstance().Dispatcher().enqueue(call);
                return true;
            }
            else
            {
                return false;
            }
        }
    }

    protected void complain(String message) {
        alert("Error: " + message);
    }

    protected void alert(String message) {
        AlertDialog.Builder bld = new AlertDialog.Builder(mContext);
        bld.setMessage(message);
        bld.setNeutralButton("OK", null);
        Log.d("CommandAdapter", "Showing alert dialog: " + message);
        bld.create().show();
    }

    @Override
    public int getItemCount() {
        return mCommandList.size();
    }
}
