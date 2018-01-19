package Adapters;

import android.content.Context;
import android.content.Intent;
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

import com.dev.doods.base.AddRepoV3Activity;
import com.dev.doods.base.R;

import java.util.List;

import Controllers.omvExtrasController;
import Models.RepoV3;

/**
 * Created by thiba on 11/12/2016.
 */

public class RepoV3Adapter  extends RecyclerView.Adapter<RepoV3Adapter.MyViewHolder> {
    private Context mContext;
    private List<RepoV3> mRepoV3List;
    private omvExtrasController mController;


    public class MyViewHolder extends RecyclerView.ViewHolder {


        public TextView NameView;
        public TextView DescriptionView;
        public Switch swithEnableView;
        public ImageView overflow;


        public MyViewHolder(View view) {
            super(view);
            NameView = (TextView) view.findViewById(R.id.Repo);
            DescriptionView = (TextView) view.findViewById(R.id.Description);
            swithEnableView = (Switch) view.findViewById(R.id.SwitchEnable);

            overflow = (ImageView) view.findViewById(R.id.overflow);

        }
    }
    public RepoV3Adapter(Context mContext, List<RepoV3> repoV3List, omvExtrasController controller) {
        this.mContext = mContext;



        this.mRepoV3List = repoV3List;
        this.mController = controller;

    }

    @Override
    public RepoV3Adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_repo, parent, false);

        return new RepoV3Adapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RepoV3Adapter.MyViewHolder holder,final int position) {
        final RepoV3 data = mRepoV3List.get(position);
        holder.NameView.setText(data.getName());
        holder.DescriptionView.setText(data.getComment());
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
        inflater.inflate(R.menu.menu_certificate_card, popup.getMenu());
        popup.setOnMenuItemClickListener(new RepoV3Adapter.MyMenuItemClickListener(position,view));
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
            if(menuItem.getItemId() ==R.id.action_edite)
            {
                Intent mIntent = new Intent(mContext, AddRepoV3Activity.class);
                Bundle extras = new Bundle();
                extras.putSerializable("RepoV3", mRepoV3List.get(mPosition));
                mIntent.putExtras(extras);
                mContext.startActivity(mIntent);
                return true;
            }
            else if(menuItem.getItemId() ==R.id.action_detail)
            {
                String str2 = mContext.getString(R.string.not_implemented);
                Snackbar.make( mView,str2, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                return true;
            }
            else if(menuItem.getItemId() ==R.id.action_delete)
            {

                RepoV3 cc =mRepoV3List.get(mPosition);
                mController.DeleteRepoV3(cc.getUuid(),null);
                mRepoV3List.remove(mPosition);
                notifyDataSetChanged();
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
        return mRepoV3List.size();
    }

}
