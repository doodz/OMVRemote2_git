package com.dev.doods.omvremote2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Adapters.StatisticItemAdapter;
import Client.Call;
import Client.Callback;
import Client.CallbackImpl;
import Client.Response;
import Controllers.DaignostiquesSystemeController;
import Interfaces.NoticeDialogListener;
import Interfaces.OutputListener;
import Models.Errors;
import Models.StatisticItem;
import OMV.Base.AppCompatBaseActivity;
import OMVFragment.Dialogs.FilterPluginDialogFragment;
import OMVFragment.Dialogs.FilterStatisticsDialogFragment;
import OMVFragment.Dialogs.OutputDialogFragment;

public class StatisticsScrollingActivity extends AppCompatBaseActivity implements OutputListener, NoticeDialogListener {

    public static final String PREFS_STATISTICS = "MyPrefsFileStatistics";

    private String mUuid;
    private DaignostiquesSystemeController mController = new DaignostiquesSystemeController(this);
    //private OutputController mOutputController = new OutputController(this);

    private RecyclerView recyclerView;
    private StatisticItemAdapter adapter;
    private List<StatisticItem> mStatisticItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cpu_usage_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //mOutputController.AddListener(this);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mController.generateRRD(new CallbackImpl(StatisticsScrollingActivity.this) {

                    @Override
                    public void onResponse(Call call, Response response) throws IOException, InterruptedException {
                        final String filename =  response.GetResultObject(new TypeToken<String>(){});
                        mHandler.post(new Runnable(){
                            public void run() {
                                //mOutputController.IsRunningFile(filename);
                                CreateDialog(true,"Generate RRD",filename);
                            }
                        });
                    }
                });
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mStatisticItemList = new ArrayList<>();
        adapter = new StatisticItemAdapter(this, mStatisticItemList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        //recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);


        Bundle bundle = getIntent().getExtras();
        if(bundle!= null && bundle.containsKey("uuid"))
        {
            String str = bundle.getString("title");
            setTitle(str);

            mUuid = bundle.getString("uuid");

            prepareStatisticItems(mUuid);
        }
        else
        prepareStatisticItems();
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

    private void prepareStatisticItems(String uuid)
    {

        StatisticItem item = new StatisticItem("df-media-"+uuid+"-hour.png");


        mStatisticItemList.add(item);

        item = new StatisticItem("df-media-"+uuid+"-day.png");
        mStatisticItemList.add(item);

        item = new StatisticItem("df-media-"+uuid+"-week.png");
        mStatisticItemList.add(item);

        item = new StatisticItem("df-media-"+uuid+"-month.png");
        mStatisticItemList.add(item);

        item = new StatisticItem("df-media-"+uuid+"-year.png");
        mStatisticItemList.add(item);
        adapter.notifyDataSetChanged();
    }

    private void prepareStatisticItems()
    {
        SharedPreferences settings = getSharedPreferences(StatisticsScrollingActivity.PREFS_STATISTICS, 0);
        StatisticItem item;

        if(settings.getBoolean("swithCpuHour",true)) {
            item = new StatisticItem(DaignostiquesSystemeController.Diagnostique_cpu0_hour);
            mStatisticItemList.add(item);
        }
        if(settings.getBoolean("swithCpuDay",true)) {
            item = new StatisticItem(DaignostiquesSystemeController.Diagnostique_cpu0_day);
            mStatisticItemList.add(item);
        }
            if(settings.getBoolean("swithCpuWeek",true)) {
                item = new StatisticItem(DaignostiquesSystemeController.Diagnostique_cpu0_week);
                mStatisticItemList.add(item);
            }
                if(settings.getBoolean("swithCpuMonth",true)) {
                    item = new StatisticItem(DaignostiquesSystemeController.Diagnostique_cpu0_month);
                    mStatisticItemList.add(item);
                }
                    if(settings.getBoolean("swithCpuYear",true)) {
                        item = new StatisticItem(DaignostiquesSystemeController.Diagnostique_cpu0_year);
                        mStatisticItemList.add(item);
                    }

        if(settings.getBoolean("swithInterfaceHour",true)) {
            item = new StatisticItem(DaignostiquesSystemeController.Diagnostique_interface_eth0_hour);
            mStatisticItemList.add(item);
        }
            if(settings.getBoolean("swithInterfaceDay",true)) {
                item = new StatisticItem(DaignostiquesSystemeController.Diagnostique_interface_eth0_day);
                mStatisticItemList.add(item);
            }
                if(settings.getBoolean("swithInterfacesWeek",true)) {
                    item = new StatisticItem(DaignostiquesSystemeController.Diagnostique_interface_eth0_week);
                    mStatisticItemList.add(item);
                }
                    if(settings.getBoolean("swithInterfacesMonth",true)) {
                        item = new StatisticItem(DaignostiquesSystemeController.Diagnostique_interface_eth0_month);
                        mStatisticItemList.add(item);
                    }
                        if(settings.getBoolean("swithInterfacesYear",true)) {
                            item = new StatisticItem(DaignostiquesSystemeController.Diagnostique_interface_eth0_year);
                            mStatisticItemList.add(item);
                        }

        if(settings.getBoolean("swithLoadAverageHour",true)) {
            item = new StatisticItem(DaignostiquesSystemeController.Diagnostique_load_average_hour);
            mStatisticItemList.add(item);
        }
            if(settings.getBoolean("swithLoadAverageDay",true)) {
                item = new StatisticItem(DaignostiquesSystemeController.Diagnostique_load_average_day);
                mStatisticItemList.add(item);
            }
                if(settings.getBoolean("swithLoadAverageWeek",true)) {
                    item = new StatisticItem(DaignostiquesSystemeController.Diagnostique_load_average_week);
                    mStatisticItemList.add(item);
                }
                    if(settings.getBoolean("swithLoadAverageMonth",true)) {
                        item = new StatisticItem(DaignostiquesSystemeController.Diagnostique_load_average_month);
                        mStatisticItemList.add(item);
                    }
                        if(settings.getBoolean("swithLoadAverageYear",true)) {
                            item = new StatisticItem(DaignostiquesSystemeController.Diagnostique_load_average_year);
                            mStatisticItemList.add(item);
                        }

        if(settings.getBoolean("swithMemoryHour",true)) {
            item = new StatisticItem(DaignostiquesSystemeController.Diagnostique_memory_hour);
            mStatisticItemList.add(item);
        }
            if(settings.getBoolean("swithMemoryDay",true)) {
                item = new StatisticItem(DaignostiquesSystemeController.Diagnostique_memory_day);
                mStatisticItemList.add(item);
            }
                if(settings.getBoolean("swithMemoryWeek",true)) {
                    item = new StatisticItem(DaignostiquesSystemeController.Diagnostique_memory_week);
                    mStatisticItemList.add(item);
                }
                    if(settings.getBoolean("swithMemoryMonth",true)) {
                        item = new StatisticItem(DaignostiquesSystemeController.Diagnostique_memory_month);
                        mStatisticItemList.add(item);
                    }
                        if(settings.getBoolean("swithMemoryYear",true)) {
                            item = new StatisticItem(DaignostiquesSystemeController.Diagnostique_memory_year);
                            mStatisticItemList.add(item);
                        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void OnFinich() {
        mStatisticItemList.clear();
        if(mUuid != null)
            prepareStatisticItems(mUuid);
        else
            prepareStatisticItems();
    }

    @Override
    public void OnCanceled() {

    }

    @Override
    public void OnStarted() {

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
           showDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showDialog()
    {
        DialogFragment dialog = new FilterStatisticsDialogFragment();
        dialog.show(getSupportFragmentManager(), "FilterStatisticsDialogFragment");
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {

        mStatisticItemList.clear();
        if(mUuid != null)
            prepareStatisticItems(mUuid);
        else
            prepareStatisticItems();

    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }
}
