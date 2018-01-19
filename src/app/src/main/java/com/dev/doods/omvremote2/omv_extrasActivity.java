package com.dev.doods.omvremote2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import Adapters.PinnedSectionRepoAdapter;
import Adapters.RepoV3Adapter;
import Client.Call;
import Client.Callback;
import Client.CallbackImpl;
import Client.Response;

import Controllers.omvExtrasController;

import Interfaces.OutputListener;
import Models.Errors;
import Models.Repo;
import Models.RepoV3;
import Models.Result;
import OMV.Base.NavigationBaseActivity;
import OMVFragment.Dialogs.OutputDialogFragment;

public class omv_extrasActivity extends NavigationBaseActivity
        implements OutputListener {

    private omvExtrasController controller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        NavigationId = R.id.nav_extra;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_omv_extras);

        this.controller = new omvExtrasController(this);
        mHandler= new Handler();
        //mOutputController.AddListener(this);

        controller.GetArch(new CallbackImpl(this) {


            @Override
            public void OnOMVServeurError(Call call, Errors error) {

                mHandler.post(new Runnable(){
                    public void run() {


                com.github.clans.fab.FloatingActionMenu menu = (com.github.clans.fab.FloatingActionMenu )findViewById(R.id.menu);
                menu.setVisibility(View.GONE);

                ListView listView = (ListView) findViewById(R.id.LstRepo);
                listView.setVisibility(View.GONE);

                TextView mNoOMVExtra= (TextView) findViewById(R.id.No_OMV_Extra);
                mNoOMVExtra.setVisibility(View.VISIBLE);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException, InterruptedException {
                super.onResponse(call,response);
                InternalgetRepo();
            }
        });

        com.github.clans.fab.FloatingActionButton save = (com.github.clans.fab.FloatingActionButton )findViewById(R.id.fab_Save);

        if(controller.GetApiVersion() == 3)
        {
            save.setImageResource(R.drawable.ic_add_box_black_24dp);
            save.setLabelText(getString(R.string.Add_Repo));
            save.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {

                    if(IsFinalized(true))
                    startActivity(new Intent(omv_extrasActivity.this, AddRepoV3Activity.class));

                }
            });
        }
        else
        save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                if(IsFinalized(true))
                controller.SetRepo(_lst, new CallbackImpl(omv_extrasActivity.this) {


                    @Override
                    public void onResponse(Call call, Response response) throws IOException,InterruptedException {
                        super.onResponse(call,response);
                        InternalgetRepo();
                    }
                });

            }
        });

        com.github.clans.fab.FloatingActionButton aptClean = (com.github.clans.fab.FloatingActionButton )findViewById(R.id.fab_AptClean);
        aptClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(IsFinalized(true))
                controller.Aptclean(new CallbackImpl(omv_extrasActivity.this) {

                    @Override
                    public void onResponse(Call call, Response response) throws IOException,InterruptedException {
                        super.onResponse(call,response);
                        final String filename =  response.GetResultObject( new TypeToken<String>(){});
                        mHandler.post(new Runnable(){
                            public void run() {
                                ShowProcess(filename);
                            }
                        });
                    }
                });
            }
        });

        com.github.clans.fab.FloatingActionButton refresh = (com.github.clans.fab.FloatingActionButton )findViewById(R.id.fab_Refresh);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(IsFinalized(true))
                controller.Aptclean(new CallbackImpl(omv_extrasActivity.this) {

                    @Override
                    public void onResponse(Call call, Response response) throws IOException,InterruptedException {
                        super.onResponse(call,response);
                        InternalgetRepo();
                    }
                });
            }
        });
    }

    private void ShowProcess(String fileName)
    {

        //mOutputController.getOutput(fileName);
        CreateDialog(false,"Apt clean",fileName);
    }

    private OutputDialogFragment mOutputDialogFragment;
    private void CreateDialog(Boolean justWaitCursor,String title,String fimeName)
    {

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

    private void InternalgetRepo()
    {
        controller.getRepo(new Callback() {
            @Override
            public void onFailure(Call call, Exception e) {
                e.printStackTrace();
                ShowSnackError(e.getMessage(),false);
            }

            @Override
            public void OnOMVServeurError(Call call, Errors error) {
                ShowSnackError(error.getMessage(),false);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if(controller.GetApiVersion() == 3)
                {
                    final Result<RepoV3> res = response.GetResultObject(new TypeToken<Result<RepoV3>>(){});
                    mHandler.post(new Runnable() {
                        public void run() {
                            ShowReposV3(res.getData());
                        }
                    });
                }
                else {
                    final ArrayList<Repo> res = response.GetResultObject(new TypeToken<ArrayList<Repo>>() {});
                    mHandler.post(new Runnable() {
                        public void run() {
                            ShowRepos(res);
                        }
                    });
                }
            }
        });
    }

    private List<RepoV3> _lstV3;
    private void ShowReposV3(List<RepoV3> lst)
    {
        _lstV3 = lst;
        Collections.sort(_lstV3, new Comparator<RepoV3>() {
            @Override
            public int compare(RepoV3 plugins, RepoV3 t1) {
                return plugins.getName().compareToIgnoreCase(t1.getName()); // To compare string values

            }
        });

        ListView listView = (ListView) findViewById(R.id.LstRepo);
        listView.setVisibility(View.GONE);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.LstRepoV3);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        mrecyclerViewAdapter = new RepoV3Adapter(this,_lstV3,controller);
        recyclerView.setAdapter(mrecyclerViewAdapter);
        //listView.setAdapter(new RepoV3Adapter(omv_extrasActivity.this,_lstV3));
    }
    private RepoV3Adapter mrecyclerViewAdapter;
    private List<Repo> _lst;
    private void ShowRepos(List<Repo> lst)
    {

        _lst = lst;
        Collections.sort(_lst, new Comparator<Repo>() {
            @Override
            public int compare(Repo plugins, Repo t1) {
                return plugins.getRepo().compareToIgnoreCase(t1.getRepo()); // To compare string values

            }
        });
        //List<String> namesList = lst.stream()
        //.map(Plugins::getName)
        //.collect(Collectors.toList());

        List<String> reposectionList = new ArrayList<>();

        for (Repo p:_lst) {

            reposectionList.add( p.getType());

        }
        Set<String> hs = new HashSet<>();

        hs.addAll(reposectionList);
        reposectionList.clear();
        reposectionList.addAll(hs);

        List<Repo> lst2 = new ArrayList<>();
        for (String str:reposectionList) {

            lst2.add(new Repo(Repo.SECTION,str));
            for (Repo p:_lst) {

                if(p.getType().equals(str))
                {
                    lst2.add(p);

                }

            }
        }

        _lst = lst2;

        ListView listView = (ListView) findViewById(R.id.LstRepo);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.LstRepoV3);
        recyclerView.setVisibility(View.GONE);
        listView.setAdapter(new PinnedSectionRepoAdapter(omv_extrasActivity.this,_lst));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.omv_extras, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id ==R.id.action_settings) {


            startActivity(new Intent(omv_extrasActivity.this, OMV_ExtraSettingsTabbedActivity.class));
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
