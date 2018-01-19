package com.dev.doods.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Adapters.PackageInformationRecyclerAdapter;
import Client.Call;
import Client.CallbackImpl;
import Client.Response;
import Controllers.UpdateController;
import Interfaces.OutputListener;
import Models.PackageInformation;
import OMV.Classe.NavigationBaseActivity;
import OMVFragment.Dialogs.OutputDialogFragment;

public class PackagesInfomationActivity extends NavigationBaseActivity
        implements OutputListener {


    private TextView NoUpdateVew;
    private UpdateController controller;
    private OutputDialogFragment mOutputDialogFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        NavigationId = R.id.nav_update;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_packages_infomation);

        controller = new UpdateController(this);
        PopulateLst();
        //mOutputController.AddListener(this);

        NoUpdateVew = (TextView) findViewById(R.id.NoUpdate);

        com.github.clans.fab.FloatingActionButton fab_Refresh = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.fab_Refresh);
        fab_Refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                controller.CheckUpdate(new CallbackImpl(PackagesInfomationActivity.this) {


                    @Override
                    public void onResponse(Call call, Response response) throws IOException, InterruptedException {
                        super.onResponse(call,response);
                        final String filename =  response.GetResultObject(new TypeToken<String>(){});
                        mHandler.post(new Runnable(){
                            public void run() {
                                //mOutputController.IsRunningFile(filename);
                                CreateDialog(true,"Apt update",filename);
                            }
                        });
                    }
                });

            }
        });

        com.github.clans.fab.FloatingActionButton fab_SelectAll = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.fab_SelectAll);
        fab_SelectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for (PackageInformation pi:_lst ) {
                    pi.setSelected(true);
                }
                mHandler.post(new Runnable(){
                    public void run() {
                        mrecyclerViewAdapter.notifyDataSetChanged();
                    }
                });
            }
        });

        com.github.clans.fab.FloatingActionButton fab_Update = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.fab_Update);
        fab_Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!IsFinalized(true))
                {
                    return;
                }
                int i = 0;

                List<PackageInformation> lst2 = new ArrayList<PackageInformation>();
                for (PackageInformation pi:_lst ) {

                    if(pi.isSelected())lst2.add(pi);

                }

                controller.UpdatePackage(lst2, new CallbackImpl(PackagesInfomationActivity.this) {

                    @Override
                    public void onResponse(Call call, Response response) throws IOException,InterruptedException {
                        super.onResponse(call,response);
                       final String filename = response.GetResultObject( new TypeToken<String>(){});
                        mHandler.post(new Runnable(){
                            public void run() {
                                ShowProcess(filename);
                            }
                        });
                    }
                });

            }
        });

        controller.EnumerateUpgraded(new CallbackImpl(PackagesInfomationActivity.this) {

            @Override
            public void onResponse(Call call, Response response) throws IOException,InterruptedException {
                super.onResponse(call,response);
                final ArrayList<PackageInformation> res = response.GetResultObject( new TypeToken< ArrayList<PackageInformation> >(){});
                mHandler.post(new Runnable(){
                    public void run() {
                        showUpgraded(res);
                    }
                });
            }
        });
    }

    PackageInformationRecyclerAdapter mrecyclerViewAdapter;

    private void PopulateLst()
    {

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.LstPackageInformation);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        mrecyclerViewAdapter = new PackageInformationRecyclerAdapter(this,_lst,controller);
        recyclerView.setAdapter(mrecyclerViewAdapter);
    }

    private void ShowProcess(String fileName)
    {
        //mOutputController.getOutput(fileName);
        CreateDialog(false,"Apt install",fileName);
    }


    private Boolean mJustWaitCursor;
    private String mTitle;
    private String mFileName;
    private void CreateDialog(Boolean justWaitCursor,String title,String fimeName)
    {

        mJustWaitCursor = justWaitCursor;
                mTitle = title;
        mFileName = fimeName;
        Bundle bundle = new Bundle();
        bundle.putBoolean("justWaitCursor", justWaitCursor);
        bundle.putString("title", title);
        bundle.putString("fileName", fimeName);

        // Create an instance of the dialog fragment and show it
        mOutputDialogFragment = new OutputDialogFragment();
        mOutputDialogFragment.AddListener(this);
        //mOutputDialogFragment.setListener(this);
        mOutputDialogFragment.setArguments(bundle);
        android.app.FragmentManager fm = getFragmentManager();
        mOutputDialogFragment.show(fm, "OutputDialogFragment");


    }

    private AlertDialog alert;
    private  List<PackageInformation> _lst = new ArrayList<PackageInformation>();
    //private PackageInformationAdapter _adapter;
    private void showUpgraded(List<PackageInformation> lst)
    {
        _lst.clear();
        _lst.addAll(lst);
        mrecyclerViewAdapter.notifyDataSetChanged();

        //ListView listView = (ListView) findViewById(R.id.LstPackageInformation);
        //_adapter = new PackageInformationAdapter(PackagesInfomationActivity.this,_lst);
        //listView.setAdapter(_adapter);
        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                CheckBox cb = (CheckBox) view.findViewById(R.id.checkBox);
                cb.performClick();
                Toast.makeText(PackagesInfomationActivity.this, "Clicked at positon = " + position, Toast.LENGTH_SHORT).show();

            }
        });*/
    }
    private void UpdateUpgraded(List<PackageInformation> lst)
    {
        _lst.clear();
        _lst.addAll(lst);
        mrecyclerViewAdapter.notifyDataSetChanged();
        if(!_lst.isEmpty())
            NoUpdateVew.setVisibility(View.GONE);
        else
            NoUpdateVew.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        if(mOutputDialogFragment != null)
            mOutputDialogFragment.dismiss();

        if(outState == null) outState = new Bundle();
        if(mJustWaitCursor != null) {
            outState.putBoolean("justWaitCursor", mJustWaitCursor);
            outState.putString("title", mTitle);
            outState.putString("fileName", mFileName);
        }

        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.packages_infomation, menu);
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

            startActivity(new Intent(PackagesInfomationActivity.this, UpdateSetingsActivity.class));

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if  (id == R.id.nav_home)
        {
            startActivity(new Intent(PackagesInfomationActivity.this, HomeActivity.class));
        }else if(id == R.id.nav_update)
        {
           // startActivity(new Intent(PackagesInfomationActivity.this, PackagesInfomationActivity.class));
        }
        else if(id == R.id.nav_disk)
        {
            startActivity(new Intent(PackagesInfomationActivity.this, FileSystemsActivity.class));
        }
        else if(id == R.id.nav_plugin)
        {
            startActivity(new Intent(PackagesInfomationActivity.this, PluginsActivity.class));
        }
        else if(id == R.id.nav_extra)
        {
            startActivity(new Intent(PackagesInfomationActivity.this, omv_extrasActivity.class));
        }
        else if(id == R.id.nav_host)
        {
            startActivity(new Intent(PackagesInfomationActivity.this, HostManagerActivity.class));
        }
        else if(id == R.id.nav_about)
        {
            startActivity(new Intent(PackagesInfomationActivity.this, AboutActivity.class));
        }
        else if (id == R.id.nav_certificate)
        {
            startActivity(new Intent(PackagesInfomationActivity.this, CertificateActivity.class));
        }
        else if (id == R.id.nav_logs)
        {
            startActivity(new Intent(PackagesInfomationActivity.this, LogsActivity.class));
        }
        else if (id == R.id.nav_cron)
        {
            startActivity(new Intent(PackagesInfomationActivity.this, CronActivity.class));
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void OnFinich() {
        controller.EnumerateUpgraded(new CallbackImpl(PackagesInfomationActivity.this) {

            @Override
            public void onResponse(Call call, Response response) throws IOException,InterruptedException {
                super.onResponse(call,response);
                final ArrayList<PackageInformation> res = response.GetResultObject( new TypeToken<ArrayList<PackageInformation> >(){});
                mHandler.post(new Runnable(){
                    public void run() {
                        UpdateUpgraded(res);
                    }
                });

            }
        });
    }

    @Override
    public void OnCanceled() {
        controller.EnumerateUpgraded(new CallbackImpl(PackagesInfomationActivity.this) {

            @Override
            public void onResponse(Call call, Response response) throws IOException,InterruptedException {
                super.onResponse(call,response);
                final ArrayList<PackageInformation> res = response.GetResultObject(new TypeToken<ArrayList<PackageInformation>>() {
                });
                mHandler.post(new Runnable() {
                    public void run() {
                        UpdateUpgraded(res);
                    }
                });

            }
        });
    }

    @Override
    public void OnStarted() {

    }
}
