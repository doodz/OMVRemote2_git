package Adapters;

import android.content.Context;
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
import android.widget.TextView;

import com.dev.doods.omvremote2.R;

import java.util.List;

import Client.Host;
import utils.Wol;

/**
 * Created by thiba on 12/10/2016.
 */

public class HostAdapter   extends RecyclerView.Adapter<HostAdapter.MyViewHolder> {

    private Context mContext;
    private List<Host> mHostList;
    private final AdapterView.OnItemClickListener mListener;
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

    public HostAdapter(Context mContext, List<Host> hostList,AdapterView.OnItemClickListener listener) {
        this.mContext = mContext;
        this.mHostList = hostList;
        this.mListener = listener;
    }

    @Override
    public HostAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_host, parent, false);

        return new HostAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final HostAdapter.MyViewHolder holder,final int position) {
        final Host data = mHostList.get(position);

        holder.firstLine.setText(data.getName());
        holder.secondLine.setText(data.getAddr());

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
        inflater.inflate(R.menu.menu_host_card, popup.getMenu());
        popup.setOnMenuItemClickListener(new HostAdapter.MyMenuItemClickListener(position));
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
                return true;
            }
            else if(menuItem.getItemId() ==R.id.action_select)
            {
                return true;
            }
            else if(menuItem.getItemId() ==R.id.action_edite)
            {
                return true;
            }
            else if(menuItem.getItemId() ==R.id.action_wakeup)
            {
                Host h = mHostList.get(mPosition);
                if(h.getMacAddr().equals("") || h.getMacAddr() == null)
                {
                    Snackbar.make(mView, mContext.getString(R.string.no_mac), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                else {
                    try {
                        String addr = h.getAddrBroadcast();
                        if(addr== null || addr.equals(""))
                            addr= h.getAddr();
                        Wol.wakeup(addr,h.getMacAddr(),h.getWolport());


                    }catch(IllegalArgumentException iae) {
                        Snackbar.make(mView, mContext.getString(R.string.send_failed)+":\n"+iae.getMessage(), Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        return false;

                    }catch(Exception e) {
                        Snackbar.make(mView, mContext.getString(R.string.send_failed)+":\n"+e.getMessage(), Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();

                        return false;
                    }


                }

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
        return mHostList.size();
    }
}
