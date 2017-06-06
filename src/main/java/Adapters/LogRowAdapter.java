package Adapters;

import android.content.Context;
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

import com.dev.doods.omvremote2.R;

import java.util.List;

import Controllers.CertificateController;
import Models.LogRow;

/**
 * Created by thiba on 13/12/2016.
 */

public class LogRowAdapter  extends RecyclerView.Adapter<LogRowAdapter.MyViewHolder>  {


    private Context mContext;
    private List<LogRow> mLogRowList;
    private CertificateController mController;
    public class MyViewHolder extends RecyclerView.ViewHolder {


        public TextView messageView;
        public TextView dateView;
        public ImageView overflow;

        public MyViewHolder(View view) {
            super(view);
            messageView = (TextView) view.findViewById(R.id.tvMessage);
            dateView = (TextView) view.findViewById(R.id.tvDate);
            overflow = (ImageView) view.findViewById(R.id.overflow);

        }
    }

    public LogRowAdapter(Context mContext, List<LogRow> logRowList) {
        this.mContext = mContext;
        this.mLogRowList = logRowList;
    }

    @Override
    public LogRowAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_log, parent, false);

        return new LogRowAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final LogRowAdapter.MyViewHolder holder,final int position) {
        final LogRow data = mLogRowList.get(position);

        String mess = data.getMessage();

        if(mess == null ||mess.isEmpty())
        {
            mess = data.getEvent();
        }

        if(mess == null ||mess.isEmpty())
        {
            mess = data.getLog();
        }
        holder.messageView.setText(mess);


        holder.dateView.setText(data.getDate());
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
        popup.setOnMenuItemClickListener(new LogRowAdapter.MyMenuItemClickListener(position,view));
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
        return mLogRowList.size();
    }

}
