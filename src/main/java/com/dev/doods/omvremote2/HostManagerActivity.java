package com.dev.doods.omvremote2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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

import java.util.List;

import Adapters.HostAdapter;
import Client.Host;
import Client.JSONRPCClient;
import DAL.HostsDAO;
import OMV.Classe.RecyclerItemClickListener;

public class HostManagerActivity extends AppCompatActivity  implements RecyclerItemClickListener.OnItemClickListener{
    private HostsDAO datasource;

    private RecyclerView recyclerView;

    private List<Host> mHosts;
    private Host mHost;
    private HostAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.dev.doods.base.R.layout.activity_host_manager);
        Toolbar toolbar = (Toolbar) findViewById(com.dev.doods.base.R.id.toolbar);
        setSupportActionBar(toolbar);

        datasource = new HostsDAO(this);
        datasource.open();


        //mLstHostView =  (ListView) findViewById(R.id.lstHost);
        //mLstHostView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
//      mLstHostView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//                mHost = mHosts.get(i);
//                //Bundle bundle = new Bundle();
//                //bundle.putSerializable("host", mHost);
//                //Intent intent = new Intent(HostManagerActivity.this, LoginActivity.class);
//                //intent.putExtras(bundle);
//                //startActivity(intent);
//                view.setSelected(true);
//                startSupportActionMode(modeCallBack);
//                //startActionMode(modeCallBack);
//
//            }
//        });

        mHosts = datasource.getAllHosts();
        //mAdapter = new HostAdapter(HostManagerActivity.this,mHosts);
        //mLstHostView.setAdapter(mAdapter);
        FloatingActionButton fab = (FloatingActionButton) findViewById(com.dev.doods.base.R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HostManagerActivity.this, SearchHostActivity.class));
            }
        });

        AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                mHost = mHosts.get(i);
                //Bundle bundle = new Bundle();
                //bundle.putSerializable("host", mHost);
                //Intent intent = new Intent(HostManagerActivity.this, LoginActivity.class);
                //intent.putExtras(bundle);
                //startActivity(intent);
                view.setSelected(true);
                startSupportActionMode(modeCallBack);
                //startActionMode(modeCallBack);

           }
        };


        recyclerView = (RecyclerView) findViewById(com.dev.doods.base.R.id.recycler_view);
        mAdapter = new HostAdapter(HostManagerActivity.this,mHosts,listener);
        recyclerView.setAdapter(mAdapter);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this,this));

        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int itemPosition = recyclerView.getChildLayoutPosition(v);
                mHost = mHosts.get(itemPosition);
                startSupportActionMode(modeCallBack);
            }
        });

    }

    private void DeleteHost()
    {

        datasource.deleteHost(mHost);
        mHosts.remove(mHost);
        mHost = null;
        mAdapter.notifyDataSetChanged();
    }
    private void SelectHost()
    {
        SharedPreferences sharedPref = getSharedPreferences("electedHost", Context.MODE_PRIVATE);
        sharedPref.edit().putLong("electedHostId", mHost.getId()).commit();
        JSONRPCClient jsonRpc = JSONRPCClient.getInstance();
        jsonRpc.SetHost(mHost);
        startActivity(new Intent(HostManagerActivity.this, HomeActivity.class));
    }
    private void EditeHost()
    {

        Bundle bundle = new Bundle();
        bundle.putSerializable("host", mHost);
        Intent intent = new Intent(HostManagerActivity.this, LoginActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);

    }

    @Override
    protected void onResume() {
        datasource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        datasource.close();
        super.onPause();
    }


    private ActionMode.Callback modeCallBack = new ActionMode.Callback() {

        public boolean onPrepareActionMode(ActionMode mode, Menu menu){
            return false;
        }

        public void onDestroyActionMode(ActionMode mode) {
            mode = null;
        }

        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.setTitle(mHost.getName());
            mode.getMenuInflater().inflate(com.dev.doods.base.R.menu.row_host_menu, menu);
            return true;
        }

        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

            if(item.getItemId() == com.dev.doods.base.R.id.action_delete)
            {
                DeleteHost();
                mode.finish();
                return true;
            }
            else if(item.getItemId() == com.dev.doods.base.R.id.action_select)
            {
                SelectHost();
                mode.finish();
                return true;
            }
            else if(item.getItemId() == com.dev.doods.base.R.id.action_edite)
            {

                EditeHost();
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
        mHost = mHosts.get(position);
        //Bundle bundle = new Bundle();
        //bundle.putSerializable("host", mHost);
        //Intent intent = new Intent(HostManagerActivity.this, LoginActivity.class);
        //intent.putExtras(bundle);
        //startActivity(intent);
        childView.setSelected(true);
        startSupportActionMode(modeCallBack);
        //startActionMode(modeCallBack);
    }

    @Override
    public void onItemLongPress(View childView, int position) {

    }
}
