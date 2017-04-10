package com.dev.doods.omvremote2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Adapters.CronAdapter;
import Client.Call;
import Client.CallbackImpl;
import Client.Response;
import Controllers.CronController;
import Interfaces.OutputListener;
import Models.Cron;
import Models.Result;
import OMV.Base.NavigationBaseActivity;
import utils.CheckDirty;

public class CronActivity extends NavigationBaseActivity
        implements OutputListener {

    private CronController mController = new CronController(this);
    private List<Cron> mCronList = new ArrayList<Cron>();
    private CronAdapter mCronAdapter;
    private CheckDirty mCheckDirty;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        NavigationId = R.id.nav_cron;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cron);
        mCheckDirty = new CheckDirty(CronActivity.this);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(IsFinalized(true))
                startActivity(new Intent(CronActivity.this, AddEditeCronActivity.class));
            }
        });

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.LstCron);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        mCronAdapter = new CronAdapter(this,mCronList,mController);
        recyclerView.setAdapter(mCronAdapter);

    }
    @Override
    protected void onResume()
    {
        super.onResume();
        mCheckDirty.Check();
        mController.GetCronList(new CallbackImpl(this) {

            @Override
            public void onResponse(Call call, Response response) throws IOException, InterruptedException {
                super.onResponse(call,response);
                final Result<Cron> res = response.GetResultObject(new TypeToken<Result<Cron>>(){});
                mHandler.post(new Runnable() {
                    public void run() {
                        ShowCrons(res.getData());
                    }
                });
            }
        });
    }


    private void ShowCrons(List<Cron> lst) {
        mCronList.clear();
        mCronList.addAll(lst);

        mCronAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.cron, menu);
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
