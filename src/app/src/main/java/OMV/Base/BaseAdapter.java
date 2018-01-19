package OMV.Base;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Ividata7 on 10/05/2017.
 */

public class BaseAdapter<T>  extends RecyclerView.Adapter<BaseAdapter.MyViewHolder>{


    private List<T>  mItems;
    private Context mContext;
    private int mLayoutRes;

    public BaseAdapter(Context mContext, List<T> items,@LayoutRes int layoutRes) {
        this.mContext = mContext;
        this.mItems = items;
        this.mLayoutRes = layoutRes;
    }
    @Override
    public BaseAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(mLayoutRes, parent, false);
        return new BaseAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BaseAdapter.MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        public MyViewHolder(View view) {
            super(view);

        }
    }

}
