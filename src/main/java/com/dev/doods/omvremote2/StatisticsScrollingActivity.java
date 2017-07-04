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

import com.dev.doods.omvremote2.Plugins.Sensors.FilterStatisticsSensorsDialogFragment;
import com.dev.doods.omvremote2.Plugins.Sensors.SensorsController;
import com.dev.doods.omvremote2.Plugins.Sensors.SensorsSettings;
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
import Controllers.NetworkController;
import Interfaces.NoticeDialogListener;
import Interfaces.OutputListener;
import Models.Certificate;
import Models.Errors;
import Models.Interface;
import Models.Result;
import Models.StatisticItem;
import OMV.Base.AppCompatBaseActivity;
import OMVFragment.Dialogs.FilterPluginDialogFragment;
import OMVFragment.Dialogs.FilterStatisticsDialogFragment;
import OMVFragment.Dialogs.OutputDialogFragment;

public class StatisticsScrollingActivity extends AppCompatBaseActivity implements OutputListener, NoticeDialogListener {

    public static final String PREFS_STATISTICS = "MyPrefsFileStatistics";

    private String mUuid;
    private DaignostiquesSystemeController mController = new DaignostiquesSystemeController(this);
    private NetworkController mNetworkController = new NetworkController(this);
    private SensorsController mSensorsController = new SensorsController(this);

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
                        super.onResponse(call,response);
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
            mNetworkController.getInterfaceList(new CallbackImpl(this){
                @Override
                public void onResponse(Call call, Response response) throws IOException, InterruptedException {
                    super.onResponse(call,response);
                    final Result<Interface> res = response.GetResultObject(new TypeToken<Result<Interface>>(){});
                    mHandler.post(new Runnable(){
                        public void run() {
                            prepareStatisticItems(res.getData());
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

    private void prepareStatisticItems(List<Interface> interfaces)
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


        for (Interface inter: interfaces) {
            if(settings.getBoolean("swithInterfaceHour",true)) {

                String str = DaignostiquesSystemeController.Diagnostique_interface_xxxx_hour;
                str = str.replace("xxxx",inter.getDevicename());
                item = new StatisticItem(str);
                mStatisticItemList.add(item);
            }
            if(settings.getBoolean("swithInterfaceDay",true)) {
                String str = DaignostiquesSystemeController.Diagnostique_interface_xxxx_day;
                str = str.replace("xxxx",inter.getDevicename());
                item = new StatisticItem(str);
                mStatisticItemList.add(item);
            }
            if(settings.getBoolean("swithInterfacesWeek",true)) {
                String str = DaignostiquesSystemeController.Diagnostique_interface_xxxx_week;
                str = str.replace("xxxx",inter.getDevicename());
                item = new StatisticItem(str);
                mStatisticItemList.add(item);
            }
            if(settings.getBoolean("swithInterfacesMonth",true)) {
                String str = DaignostiquesSystemeController.Diagnostique_interface_xxxx_month;
                str = str.replace("xxxx",inter.getDevicename());
                item = new StatisticItem(str);
                mStatisticItemList.add(item);
            }
            if(settings.getBoolean("swithInterfacesYear",true)) {
                String str = DaignostiquesSystemeController.Diagnostique_interface_xxxx_year;
                str = str.replace("xxxx",inter.getDevicename());
                item = new StatisticItem(str);
                mStatisticItemList.add(item);
            }
        }

        /*
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
*/
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
        mSensorsController.getSettings(new CallbackImpl(StatisticsScrollingActivity.this) {

            @Override
            public void onResponse(Call call, Response response) throws IOException, InterruptedException {
                super.onResponse(call,response);
                final SensorsSettings settings =  response.GetResultObject(new TypeToken<SensorsSettings>(){});
                mHandler.post(new Runnable(){
                    public void run() {
                        //mOutputController.IsRunningFile(filename);
                        AddSensors(settings);
                    }
                });
            }
        });

    }

    private Boolean Sensorsplugin = false;
    private void AddSensors(SensorsSettings settings2) {
        Sensorsplugin = false;

        if (settings2 == null) {
            adapter.notifyDataSetChanged();
            HideSensors();
            return;
        }
        Sensorsplugin = true;

        SharedPreferences settings = getSharedPreferences(StatisticsScrollingActivity.PREFS_STATISTICS, 0);
        StatisticItem item;
        if (settings2.getCpuenable()) {

            //Cpu Temps
            if (settings.getBoolean("swithSensorsCpuHourDay", true)) {
                item = new StatisticItem(DaignostiquesSystemeController.Diagnostique_sensors_hour);
                mStatisticItemList.add(item);
            }
            if (settings.getBoolean("swithSensorsCpuHourWeek", true)) {
                item = new StatisticItem(DaignostiquesSystemeController.Diagnostique_sensors_day);
                mStatisticItemList.add(item);
            }
            if (settings.getBoolean("swithSensorsCpuHourWeek", true)) {
                item = new StatisticItem(DaignostiquesSystemeController.Diagnostique_sensors_week);
                mStatisticItemList.add(item);
            }
            if (settings.getBoolean("swithSensorsCpuHourWeek", true)) {
                item = new StatisticItem(DaignostiquesSystemeController.Diagnostique_sensors_month);
                mStatisticItemList.add(item);
            }
            if (settings.getBoolean("swithSensorsCpuHourWeek", true)) {
                item = new StatisticItem(DaignostiquesSystemeController.Diagnostique_sensors_year);
                mStatisticItemList.add(item);
            }
        }
        if (settings2.getMbtemp()) {
            //MotherBoard Temp


            if (settings.getBoolean("swithMotherBoardWeek", true)) {
                item = new StatisticItem(DaignostiquesSystemeController.Diagnostique_mb_hour);
                mStatisticItemList.add(item);
            }
            if (settings.getBoolean("swithMotherBoardWeek", true)) {
                item = new StatisticItem(DaignostiquesSystemeController.Diagnostique_mb_day);
                mStatisticItemList.add(item);
            }
            if (settings.getBoolean("swithMotherBoardWeek", true)) {
                item = new StatisticItem(DaignostiquesSystemeController.Diagnostique_mb_week);
                mStatisticItemList.add(item);
            }
            if (settings.getBoolean("swithMotherBoardWeek", true)) {
                item = new StatisticItem(DaignostiquesSystemeController.Diagnostique_mb_month);
                mStatisticItemList.add(item);
            }
            if (settings.getBoolean("swithMotherBoardWeek", true)) {
                item = new StatisticItem(DaignostiquesSystemeController.Diagnostique_mb_year);
                mStatisticItemList.add(item);
            }
        }
        if (settings2.getCpufanenable()) {
            //CPU fan speed


        if (settings.getBoolean("swithCPUFanSpeedWeek", true)) {
            item = new StatisticItem(DaignostiquesSystemeController.Diagnostique_fan_hour);
            mStatisticItemList.add(item);
        }
        if (settings.getBoolean("swithCPUFanSpeedWeek", true)) {
            item = new StatisticItem(DaignostiquesSystemeController.Diagnostique_fan_day);
            mStatisticItemList.add(item);
        }
        if (settings.getBoolean("swithCPUFanSpeedWeek", true)) {
            item = new StatisticItem(DaignostiquesSystemeController.Diagnostique_fan_week);
            mStatisticItemList.add(item);
        }
        if (settings.getBoolean("swithCPUFanSpeedWeek", true)) {
            item = new StatisticItem(DaignostiquesSystemeController.Diagnostique_fan_month);
            mStatisticItemList.add(item);
        }
        if (settings.getBoolean("swithCPUFanSpeedWeek", true)) {
            item = new StatisticItem(DaignostiquesSystemeController.Diagnostique_fan_year);
            mStatisticItemList.add(item);
        }
    }
        if(settings2.getSysfanenable()) {
            //SYS fan speed

        if(settings.getBoolean("swithSYSFanSpeedWeek",true)) {
            item = new StatisticItem(DaignostiquesSystemeController.Diagnostique_sys_hour);
            mStatisticItemList.add(item);
        }
        if(settings.getBoolean("swithSYSFanSpeedWeek",true)) {
            item = new StatisticItem(DaignostiquesSystemeController.Diagnostique_sys_day);
            mStatisticItemList.add(item);
        }
        if(settings.getBoolean("swithSYSFanSpeedWeek",true)) {
            item = new StatisticItem(DaignostiquesSystemeController.Diagnostique_sys_week);
            mStatisticItemList.add(item);
        }
        if(settings.getBoolean("swithSYSFanSpeedWeek",true)) {
            item = new StatisticItem(DaignostiquesSystemeController.Diagnostique_sys_month);
            mStatisticItemList.add(item);
        }
        if(settings.getBoolean("swithSYSFanSpeedWeek",true)) {
            item = new StatisticItem(DaignostiquesSystemeController.Diagnostique_sys_year);
            mStatisticItemList.add(item);
        }
        }
        adapter.notifyDataSetChanged();
    }


    @Override
    public void OnFinich() {
        mStatisticItemList.clear();
        if(mUuid != null)
            prepareStatisticItems(mUuid);
        else
            mNetworkController.getInterfaceList(new CallbackImpl(this){
                @Override
                public void onResponse(Call call, Response response) throws IOException, InterruptedException {
                    super.onResponse(call,response);
                    final Result<Interface> res = response.GetResultObject(new TypeToken<Result<Interface>>(){});
                    mHandler.post(new Runnable(){
                        public void run() {
                            prepareStatisticItems(res.getData());
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        mMenu = menu;
            getMenuInflater().inflate(R.menu.menu_sensors, menu);

        return true;
    }

    private Menu mMenu;

    private void HideSensors()
    {
        MenuItem item = mMenu.getItem(1);
        item.setVisible(false);
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

        if (id == R.id.action_sensors_settings) {
            showDialogSensors();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showDialogSensors()
    {
        DialogFragment dialog = new FilterStatisticsSensorsDialogFragment();
        dialog.show(getSupportFragmentManager(), "FilterStatisticsSensorsDialogFragment");
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
            mNetworkController.getInterfaceList(new CallbackImpl(this){
                @Override
                public void onResponse(Call call, Response response) throws IOException, InterruptedException {
                    super.onResponse(call,response);
                    final Result<Interface> res = response.GetResultObject(new TypeToken<Result<Interface>>(){});
                    mHandler.post(new Runnable(){
                        public void run() {
                            prepareStatisticItems(res.getData());
                        }
                    });
                }
            });

    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }
}
