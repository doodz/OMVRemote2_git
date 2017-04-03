package com.dev.doods.omvremote2;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.innahema.collections.query.functions.Predicate;
import com.innahema.collections.query.queriables.Queryable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import Adapters.PinnedSectionPluginsAdapter;
import Client.Call;
import Client.CallbackImpl;
import Client.Response;
import Controllers.PluginsController;
import Interfaces.NoticeDialogListener;
import OMV.Classe.NavigationBaseActivity;
import OMVFragment.Dialogs.FilterPluginDialogFragment;
import Interfaces.OutputListener;
import Models.FilterPlugin;
import Models.Plugins;
import OMVFragment.Dialogs.OutputDialogFragment;

public class PluginsActivity extends NavigationBaseActivity
        implements OutputListener, NoticeDialogListener {

    private PluginsController controller;
    //private OutputController mOutputController = new OutputController(PluginsActivity.this);
    private FilterPlugin _Filters = new FilterPlugin();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        NavigationId = R.id.nav_plugin;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plugins);

        controller = new PluginsController(this);
        //mOutputController.AddListener(this);
        com.github.clans.fab.FloatingActionButton fab_Install = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.fab_Install);
        fab_Install.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        //.setAction("Action", null).show();
                if(!IsFinalized(true))
                {

                    return;
                }
                int i = 0;

                List<Plugins> lst2 = new ArrayList<Plugins>();
                for (Plugins plug:_lst ) {
                    if(plug.isSelected() && !plug.getInstalled())lst2.add(plug);
                }

                controller.InstallPlugins(lst2, new CallbackImpl(PluginsActivity.this) {

                    @Override
                    public void onResponse(Call call, Response response) throws IOException,InterruptedException {
                        super.onResponse(call,response);
                        JsonElement j = response.GetJsonResult();
                        final String filename =  j.getAsString();
                        //mOutputController.getOutput(filename);

                        mHandler.post(new Runnable(){
                            public void run() {

                                CreateDialog(false, "Remove Plugins", filename);
                            }
                        });
                    }
                });
            }
        });

        com.github.clans.fab.FloatingActionButton fab_UnInstall = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.fab_UnInstall);
        fab_UnInstall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //.setAction("Action", null).show();
                if(IsFinalized(true))
                {
                    return;
                }
                int i = 0;

                List<Plugins> lst2 = new ArrayList<Plugins>();
                for (Plugins plug:_lst ) {

                    if(plug.isSelected() && plug.getInstalled())lst2.add(plug);

                }

                controller.RemovePlugins(lst2, new CallbackImpl(PluginsActivity.this) {
                    @Override
                    public void onResponse(Call call, Response response) throws IOException,InterruptedException {
                        super.onResponse(call,response);
                        JsonElement j = response.GetJsonResult();
                        final String filename =  j.getAsString();
                        //mOutputController.getOutput(filename);

                        mHandler.post(new Runnable(){
                            public void run() {

                                CreateDialog(false,"Remove Plugins",filename);
                            }
                        });

                    }
                });
            }
        });

        com.github.clans.fab.FloatingActionButton fab_Filter = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.fab_Filter);
        fab_Filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowFilterDialog();
            }
        });
        controller.enumeratePlugins(new CallbackImpl(PluginsActivity.this) {

            @Override
            public void onResponse(Call call, Response response) throws IOException,InterruptedException {
                super.onResponse(call,response);
                final ArrayList<Plugins> res = response.GetResultObject(new TypeToken<ArrayList<Plugins>>(){});
                mHandler.post(new Runnable(){
                    public void run() {
                        ShowPlugins(res);
                    }
                });
            }
        });
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
        //mOutputDialogFragment.AddListener(this);
        //mOutputDialogFragment.setListener(this);
        mOutputDialogFragment.setArguments(bundle);
        android.app.FragmentManager fm = getFragmentManager();
        mOutputDialogFragment.show(fm, "OutputDialogFragment");
    }


    private List<Plugins> _RealLstPlugins;
    private  List<Plugins> _lst;
    private List<String> _PluginsectionList;
    private PinnedSectionPluginsAdapter _PinnedSectionPluginsAdapter;

    private void Filter()
    {

        _lst = _RealLstPlugins;

        if(_Filters.FilterOnPluginSection != null)
            _lst = (List<Plugins>) Queryable.from(_lst).filter(new Predicate<Plugins>() {
                @Override
                public boolean apply(Plugins element) {
                    return element.getPluginsection().equals(_Filters.FilterOnPluginSection);
                }
            }).toList();
        if(_Filters.FilterOnName != null)
            _lst = (List<Plugins>) Queryable.from(_lst).filter(new Predicate<Plugins>() {
                @Override
                public boolean apply(Plugins element) {
                    return element.getName().contains(_Filters.FilterOnName);
                }
            }).toList();
        if(_Filters.FilterOnInstaled != null)
            _lst = (List<Plugins>) Queryable.from(_lst).filter(new Predicate<Plugins>() {
                @Override
                public boolean apply(Plugins element) {
                    return element.getInstalled() == _Filters.FilterOnInstaled;
                }
            }).toList();


        if(_Filters.FilterOnPluginSection != null)
        {
            _lst.add(0,new Plugins(Plugins.SECTION, _Filters.FilterOnPluginSection));
        }
        else {
            List<Plugins> lst2 = new ArrayList<>();
            for (String str : _PluginsectionList) {

                lst2.add(new Plugins(Plugins.SECTION, str));
                for (Plugins p : _lst) {

                    if (p.getPluginsection().equals(str)) {
                        lst2.add(p);
                    }
                }
            }

            _lst = lst2;
        }

        _PinnedSectionPluginsAdapter.UpdateSource(_lst);
    }

    private void ShowPlugins(List<Plugins> lst)
    {
        _RealLstPlugins = lst;


        Collections.sort(_RealLstPlugins, new Comparator<Plugins>() {
            @Override
            public int compare(Plugins plugins, Plugins t1) {
                return plugins.getName().compareToIgnoreCase(t1.getName()); // To compare string values

            }
        });

        _lst = _RealLstPlugins;
        //List<String> namesList = lst.stream()
                //.map(Plugins::getName)
                //.collect(Collectors.toList());

        _PluginsectionList = new ArrayList<>();

        for (Plugins p:_lst) {
            _PluginsectionList.add( p.getPluginsection());
        }
        Set<String> hs = new HashSet<>();

        hs.addAll(_PluginsectionList);
        _PluginsectionList.clear();
        _PluginsectionList.addAll(hs);

        List<Plugins> lst2 = new ArrayList<>();
        for (String str:_PluginsectionList) {

            lst2.add(new Plugins(Plugins.SECTION,str));
            for (Plugins p:_lst) {

                if(p.getPluginsection().equals(str))
                {
                    lst2.add(p);
                }
            }
        }

        _lst = lst2;

        ListView listView = (ListView) findViewById(R.id.LstPlugins);

        _PinnedSectionPluginsAdapter = new PinnedSectionPluginsAdapter(PluginsActivity.this,_lst);


        listView.setAdapter(_PinnedSectionPluginsAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.plugins, menu);
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
        controller.enumeratePlugins(new CallbackImpl(PluginsActivity.this) {

            @Override
            public void onResponse(Call call, Response response) throws IOException,InterruptedException {
                super.onResponse(call,response);
                final ArrayList<Plugins> res = response.GetResultObject(  new TypeToken<ArrayList<Plugins>>(){});
                mHandler.post(new Runnable(){
                    public void run() {
                        ShowPlugins(res);
                    }
                });
            }
        });
    }

    @Override
    public void OnCanceled() {

    }

    @Override
    public void OnStarted() {

    }

    private String _FilterOnPluginSection = null;
    private String _FilterOnName = null;
    private Boolean _FilterOnInstaled = null;


    public void ShowFilterDialog()
    {

        Bundle bundle = new Bundle();

        _Filters.PluginsectionList = _PluginsectionList;
        bundle.putSerializable("FilterPlugin", _Filters);
        // Create an instance of the dialog fragment and show it
        DialogFragment dialog = new FilterPluginDialogFragment();
        dialog.setArguments(bundle);
        dialog.show(getSupportFragmentManager(), "FilterPluginDialogFragment");

    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {

        _Filters = ((FilterPluginDialogFragment)dialog).filters;
        Filter();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }
}