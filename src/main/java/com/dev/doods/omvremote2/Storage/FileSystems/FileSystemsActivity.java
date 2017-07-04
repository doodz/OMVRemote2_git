package com.dev.doods.omvremote2.Storage.FileSystems;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.dev.doods.omvremote2.R;
import com.dev.doods.omvremote2.Storage.Smart.SwipeViewSmartActivity;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Client.Call;
import Client.CallbackImpl;
import Client.Response;
import Interfaces.IUpdateActivity;
import Models.Device;
import Models.Result;
import OMV.Base.NavigationBaseActivity;

public class FileSystemsActivity extends NavigationBaseActivity implements IUpdateActivity {
    private DisckController mController = new DisckController(this);
    private RecyclerView recyclerView;
    private List<FileSystem> _lst = new ArrayList<FileSystem>();
    private FileSystemsAdapter _adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        NavigationId = R.id.nav_disk;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_systems);
/*
        mController.getListDevies(new CallbackImpl(this) {
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
*/
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        _adapter =new FileSystemsAdapter(FileSystemsActivity.this,mController,_lst);
        recyclerView.setAdapter(_adapter);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        update();
    }

    public void update()
    {
        mController.getListFilesSystems(new CallbackImpl(this) {
            @Override
            public void onResponse(Call call, Response response) throws IOException,InterruptedException {
                super.onResponse(call,response);
                final Result<FileSystem> res = response.GetResultObject(new TypeToken<Result<FileSystem>>(){});
                mHandler.post(new Runnable(){
                    public void run() {ShowFileSystem(res.getData());
                    }
                });
            }
        });
    }

    private void ShowFileSystem(List<FileSystem> lst)
    {
        _lst.clear();
        _lst.addAll(lst);
        _adapter.notifyDataSetChanged();
       // recyclerView.setAdapter(adapter);

    }
/*
    private void showDevices(List<Device> lst)
    {

    }
*/
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
        if (id == R.id.action_smart) {
            startActivity(new Intent(FileSystemsActivity.this, SwipeViewSmartActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
