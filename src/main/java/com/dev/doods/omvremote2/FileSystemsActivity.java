package com.dev.doods.omvremote2;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;

import Adapters.FileSystemsAdapter;
import Client.Call;
import Client.CallbackImpl;
import Client.Response;
import Controllers.DisckController;
import Models.Device;
import Models.FileSystem;
import Models.Result;
import OMV.Classe.NavigationBaseActivity;

public class FileSystemsActivity extends NavigationBaseActivity {
    private DisckController controller;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        NavigationId = R.id.nav_disk;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_systems);

        controller = new DisckController(this);

        controller.getListDevies(new CallbackImpl(this) {
            @Override
            public void onResponse(Call call, Response response) throws IOException,InterruptedException {
                super.onResponse(call,response);
                final Result<Device> res = response.GetResultObject(new TypeToken<Result<Device>>(){});
                mHandler.post(new Runnable(){
                    public void run() {
                        showDevices(res.getData());
                    }
                });
            }
        });

        controller.getListFilesSystems(new CallbackImpl(this) {
            @Override
            public void onResponse(Call call, Response response) throws IOException,InterruptedException {
                super.onResponse(call,response);
                final Result<FileSystem> res = response.GetResultObject(new TypeToken<Result<FileSystem>>(){});
                mHandler.post(new Runnable(){
                    public void run() {
                        ShowFileSystem(res.getData());
                    }
                });
            }
        });
    }

    private void ShowFileSystem(List<FileSystem> lst)
    {

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setAdapter(new FileSystemsAdapter(FileSystemsActivity.this,lst));
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
       // recyclerView.setAdapter(adapter);

    }

    private void showDevices(List<Device> lst)
    {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.file_systems, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
