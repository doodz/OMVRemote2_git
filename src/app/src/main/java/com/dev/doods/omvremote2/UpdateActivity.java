package com.dev.doods.omvremote2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.dev.doods.omvremote2.Plugins.PluginsActivity;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Adapters.RecyclerViewPackageInformationAdapter;
import Client.Call;
import Client.Callback;
import Client.Response;
import Controllers.UpdateController;
import Interfaces.OutputListener;
import Models.Errors;
import Models.PackageInformation;

public class UpdateActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OutputListener {

    private UpdateController controller;
    private Handler handler;
    //private OutputController mOutputController = new OutputController(UpdateActivity.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        controller = new UpdateController(this);
        handler= new Handler();
        //mOutputController.AddListener(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setTitle("My title");

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int i = 0;
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();

                //ListView listView = (ListView) findViewById(R.id.LstPackageInformation);
                //listView.getSelectedItem();
            }
        });

        controller.EnumerateUpgraded(new Callback() {
            @Override
            public void onFailure(Call call, Exception e) {

            }

            @Override
            public void OnOMVServeurError(Call call, Errors error) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final ArrayList<PackageInformation> res = response.GetResultObject( new TypeToken<ArrayList<PackageInformation>>(){});
                handler.post(new Runnable(){
                    public void run() {
                        showUpgraded(res);
                    }
                });

            }
        });
    }


    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private void showUpgraded(List<PackageInformation> lst)
    {

        //ListView listView = (ListView) findViewById(R.id.LstPackageInformation);
        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerPackageInformation);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new RecyclerViewPackageInformationAdapter(lst, R.layout.row_packageinformation);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i = 0;
            }
        });

     /* listView.setAdapter(new PackageInformationAdapter(UpdateActivity.this,lst));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CheckBox cb = (CheckBox) view.findViewById(R.id.checkBox);
                cb.performClick();
            }
        });*/
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if(id == R.id.nav_home)
        {
            startActivity(new Intent(UpdateActivity.this, HomeActivity.class));
        }
        else if(id == R.id.nav_plugin)
        {
            startActivity(new Intent(UpdateActivity.this, PluginsActivity.class));
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void OnFinich() {

    }

    @Override
    public void OnCanceled() {

    }

    @Override
    public void OnStarted() {

    }
}
