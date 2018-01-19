package Adapters;

import android.content.Context;
import android.support.v4.util.Pair;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev.doods.omvremote2.R;

import java.util.AbstractMap;
import java.util.List;

import Classes.KeyValueObject;
import Interfaces.OnEditTextChanged;
import Models.bd.Command;

/**
 * Created by Ividata7 on 12/04/2017.
 */

public class KeyValueAdapter  extends RecyclerView.Adapter<KeyValueAdapter.MyViewHolder> {

    private Context mContext;
    private List<KeyValueObject> mList;
    public View mView;
    private OnEditTextChanged onEditTextChanged;
    public class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView overflow;
        public TextView firstLine;
        public TextView secondLine;

        public MyViewHolder(View view) {
            super(view);
            mView = view;
            firstLine = (TextView) view.findViewById(R.id.Name);
            secondLine = (TextView) view.findViewById(R.id.Value);

            //overflow = (ImageView) view.findViewById(R.id.overflow);
        }
    }

    public KeyValueAdapter(Context mContext, List<KeyValueObject> lst, OnEditTextChanged onEditTextChanged) {
        this.mContext = mContext;
        this.mList = lst;
        this.onEditTextChanged = onEditTextChanged;
    }

    @Override
    public KeyValueAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_key_value, parent, false);

        return new KeyValueAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final KeyValueAdapter.MyViewHolder holder,final int position) {
        final KeyValueObject data = mList.get(position);

        holder.firstLine.setText(data.getKey());

        holder.firstLine.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                onEditTextChanged.onTextChanged(position, charSequence.toString(),true);
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        holder.secondLine.setText(data.getValue());

        holder.secondLine.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                onEditTextChanged.onTextChanged(position, charSequence.toString(),false);
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
        /*
        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.overflow,position);
            }
        });
        */
    }

    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view,int position) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_command, popup.getMenu());
        popup.setOnMenuItemClickListener(new KeyValueAdapter.MyMenuItemClickListener(position));
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
            else if(menuItem.getItemId() ==R.id.action_exec)
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
        return mList.size();
    }
}
