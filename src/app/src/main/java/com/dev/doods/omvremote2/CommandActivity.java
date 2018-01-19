package com.dev.doods.omvremote2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.List;

import Adapters.CommandAdapter;
import Client.Host;
import DAL.CommandDAO;
import Models.bd.Command;
import OMV.Base.AppCompatBaseActivity;
import OMV.Base.NavigationBaseActivity;
import OMV.Classe.RecyclerItemClickListener;

public class CommandActivity extends NavigationBaseActivity implements RecyclerItemClickListener.OnItemClickListener{

    CommandDAO datasource;
    private RecyclerView mRecyclerView;
    private CommandAdapter mAdapter;
    private  List<Command> LstCommand = new ArrayList<Command>();
    private Command mCommand;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        NavigationId = R.id.nav_Command;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_command);

        datasource= new CommandDAO(this);
        datasource.open();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CommandActivity.this, AddCommandActivity.class));
            }
        });


        mRecyclerView = (RecyclerView)findViewById(R.id.LstCommends);
        mAdapter = new CommandAdapter(this,LstCommand);
        mRecyclerView.setAdapter(mAdapter);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this,this));

        mRecyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int itemPosition = mRecyclerView.getChildLayoutPosition(v);
                mCommand = LstCommand.get(itemPosition);
                startSupportActionMode(modeCallBack);
            }
        });

    }

    @Override
    protected void onResume() {
        datasource.open();
        super.onResume();

        getCommands();
        SuppBusy();
    }

    @Override
    public void onPause() {
        datasource.close();
        super.onPause();
    }
    private void getCommands()
    {

        List<Command> lst = datasource.getAllCommands();
        LstCommand.clear();
        LstCommand.addAll(lst);
        mCommand = null;
        mAdapter.notifyDataSetChanged();
    }

    private ActionMode.Callback modeCallBack = new ActionMode.Callback() {

        public boolean onPrepareActionMode(ActionMode mode, Menu menu){
            return false;
        }

        public void onDestroyActionMode(ActionMode mode) {
            mode = null;
        }

        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.setTitle(mCommand.getName());
            mode.getMenuInflater().inflate(R.menu.row_host_menu, menu);
            return true;
        }

        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

            if(item.getItemId() == R.id.action_delete)
            {
                //DeleteHost();
                mode.finish();
                return true;
            }
            else if(item.getItemId() == R.id.action_run)
            {
                //SelectHost();
                mode.finish();
                return true;
            }
            else if(item.getItemId() == R.id.action_edite)
            {

                //EditeHost();
                mode.finish();
                return true;
            }
            else
            {
                return false;
            }


        }
    };
    @Override
    public void onItemClick(View childView, int position) {
        mCommand = LstCommand.get(position);

        childView.setSelected(true);
        startSupportActionMode(modeCallBack);

    }

    @Override
    public void onItemLongPress(View childView, int position) {

    }
}
