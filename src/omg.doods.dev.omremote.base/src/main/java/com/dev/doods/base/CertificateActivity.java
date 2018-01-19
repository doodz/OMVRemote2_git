package com.dev.doods.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;


import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Adapters.CertificateAdapter;
import Adapters.CertificateSshAdapter;
import Client.Call;
import Client.CallbackImpl;
import Client.Response;
import Controllers.CertificateController;
import Interfaces.NoticeDialogListener;
import Models.Certificate;
import Models.CertificateSsh;
import Models.Result;
import OMV.Classe.NavigationBaseActivity;
import OMVFragment.Dialogs.AddSshDialogFragment;
import OMVFragment.Dialogs.AddSslDialogFragment;

public class CertificateActivity extends NavigationBaseActivity implements NoticeDialogListener {

    CertificateController mController = new CertificateController(this);
    AddSshDialogFragment mDialogSsh;
    RecyclerView mRecyclerViewSSH;
    RecyclerView mRecyclerViewSSL;
    List<Certificate> lstCertificate = new ArrayList<Certificate>();
    CertificateAdapter mCertificateAdapter;
    List<CertificateSsh> lstCertificateSsh = new ArrayList<CertificateSsh>();
    CertificateSshAdapter mCertificateSshAdapter;
    private RecyclerView recyclerViewSSH;
    private RecyclerView recyclerViewSSL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        NavigationId = R.id.nav_certificate;
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_certificate);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        GetViews();
        GetSshList();
        GetCertificate();
    }

    private void GetCertificate()
    {
        mController.GetCertificate(new CallbackImpl(this) {
            @Override
            public void onResponse(Call call, Response response) throws IOException, InterruptedException {
                super.onResponse(call,response);
                final Result<Certificate> res = response.GetResultObject(new TypeToken<Result<Certificate>>(){});
                mHandler.post(new Runnable(){
                    public void run() {
                        ShowCertificate(res.getData());
                    }
                });
            }
        });

    }

    private void GetSshList()
    {
        mController.GetSshList(new CallbackImpl(this) {

            @Override
            public void onResponse(Call call, Response response) throws IOException, InterruptedException {
                super.onResponse(call,response);
                final Result<CertificateSsh> res = response.GetResultObject(new TypeToken<Result<CertificateSsh>>(){});
                mHandler.post(new Runnable(){
                    public void run() {
                        ShowCertificateSsh(res.getData());
                    }
                });
            }
        });

    }


    private void GetViews()
    {

        mRecyclerViewSSH = (RecyclerView)findViewById(R.id.RecyclerViewSSH);
        mRecyclerViewSSL = (RecyclerView)findViewById(R.id.RecyclerViewSSL);

        mCertificateAdapter = new CertificateAdapter(this,lstCertificate,mController);
        mRecyclerViewSSL.setAdapter(mCertificateAdapter);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        mRecyclerViewSSH.setLayoutManager(mLayoutManager);
        mRecyclerViewSSH.setItemAnimator(new DefaultItemAnimator());

        mCertificateSshAdapter = new CertificateSshAdapter(this,lstCertificateSsh,mController);
        mRecyclerViewSSH.setAdapter(mCertificateSshAdapter);

        RecyclerView.LayoutManager mLayoutManager2 = new GridLayoutManager(this, 1);
        mRecyclerViewSSL.setLayoutManager(mLayoutManager2);
        mRecyclerViewSSL.setItemAnimator(new DefaultItemAnimator());


        com.github.clans.fab.FloatingActionButton fab_add_certificate_SSH = (com.github.clans.fab.FloatingActionButton)findViewById(R.id.fab_add_certificate_SSH);

        fab_add_certificate_SSH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(IsFinalized(true))
                ShowAddSshDialogFragment();
            }
        });

        com.github.clans.fab.FloatingActionButton fab_add_certificate_SSL = (com.github.clans.fab.FloatingActionButton)findViewById(R.id.fab_add_certificate_SSL);

        fab_add_certificate_SSL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(IsFinalized(true))
                ShowAddSslDialogFragment();
            }
        });
    }


    private void ShowCertificateSsh(List<CertificateSsh> Certificates)
    {
        lstCertificateSsh.clear();
        lstCertificateSsh.addAll(Certificates);
        mCertificateSshAdapter.notifyDataSetChanged();
    }

    private void ShowCertificate(List<Certificate> Certificates)
    {
        lstCertificate.clear();
        lstCertificate.addAll(Certificates);
        mCertificateAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.certificate, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if(id == R.id.nav_home)
        {
            startActivity(new Intent(CertificateActivity.this, HomeActivity.class));
        }
        else if(id == R.id.nav_update)
        {
            startActivity(new Intent(CertificateActivity.this, PackagesInfomationActivity.class));
        }
        else if(id == R.id.nav_disk)
        {
            startActivity(new Intent(CertificateActivity.this, FileSystemsActivity.class));
        }
        else if(id == R.id.nav_plugin)
        {
            startActivity(new Intent(CertificateActivity.this, PluginsActivity.class));
        }
        else if(id == R.id.nav_extra)
        {
            startActivity(new Intent(CertificateActivity.this, omv_extrasActivity.class));
        }
        else if(id == R.id.nav_host)
        {
            startActivity(new Intent(CertificateActivity.this, HostManagerActivity.class));
        }
        else if(id == R.id.nav_about)
        {
            startActivity(new Intent(CertificateActivity.this, AboutActivity.class));
        }
        else if (id == R.id.nav_logs)
        {
            startActivity(new Intent(CertificateActivity.this, LogsActivity.class));
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void ShowAddSshDialogFragment()
    {

        Bundle bundle = new Bundle();
        //bundle.putSerializable("CertificateSsh", device.getUuid());
        // Create an instance of the dialog fragment and show it
        mDialogSsh = new AddSshDialogFragment();
        mDialogSsh.setArguments(bundle);
        mDialogSsh.show(getSupportFragmentManager(), "AddSshDialogFragment");
    }

    AddSslDialogFragment mDialogSsl;
    private void ShowAddSslDialogFragment()
    {

        Bundle bundle = new Bundle();
        //bundle.putSerializable("CertificateSsh", device.getUuid());
        // Create an instance of the dialog fragment and show it
        mDialogSsl = new AddSslDialogFragment();
        mDialogSsl.setArguments(bundle);
        mDialogSsl.show(getSupportFragmentManager(), "AddSslDialogFragment");
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        GetSshList();
        GetCertificate();

    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }
}
