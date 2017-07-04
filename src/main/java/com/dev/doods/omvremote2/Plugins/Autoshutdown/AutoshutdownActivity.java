package com.dev.doods.omvremote2.Plugins.Autoshutdown;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import com.dev.doods.omvremote2.R;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Client.Call;
import Client.CallbackImpl;
import Client.Response;
import Models.SharedFolder;
import OMV.Base.AppCompatBaseActivity;
import utils.CheckDirty;
import utils.InputFilterMinMax;
import utils.Util;

public class AutoshutdownActivity extends AppCompatBaseActivity {


    public static List<String> ShutdownCommandList = new ArrayList<String>(){{add("Shutdown");add("Hibernate");
        add("Suspend");add("Suspend-Hybrid");
    }};

    private Switch swithEnable;

    private EditText Cycles;
    private EditText Sleep;
    private Spinner spinnerShutdownCommand;
    private ArrayAdapter<String> mAdapter;
    private Switch swithCheckClock;
    private EditText Uphours_Begin;
    private EditText Uphours_End;

    private EditText IP_Range;
    private EditText Sockets;
    private Switch swithULDLRate;
    private EditText ULDL_Rate;
    private Switch swithLoadAverage;
    private EditText Load_Average;
    private Switch swithHDDIORate;
    private EditText HDDIO_Rate;
    private Switch swithCheckSmbstatus;
    private Switch swithCheckUsers;

    private Switch swithLogToSyslog;
    private Switch swithVerbose;
    private Switch swithFake;

    private EditText Extra_Options;

    private AutoshutdownController mController = new AutoshutdownController(this);
    private AutoshutdownSettings Settings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autoshutdown);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateSettings();
            }
        });
        BindViews();
        mController.getSettings(new CallbackImpl(this){
            @Override
            public void onResponse(Call call, Response response) throws IOException, InterruptedException {
                super.onResponse(call, response);

                Settings = response.GetResultObject(new TypeToken<AutoshutdownSettings>(){});
                mHandler.post(new Runnable(){
                    public void run() {
                        pupulateViews();}});
            }
        });
    }


    private void BindViews()
    {
        swithEnable = (Switch) findViewById(R.id.swithEnable);

        Cycles= (EditText) findViewById(R.id.Cycles);
        Cycles.setFilters(new InputFilter[]{ new InputFilterMinMax(1, 999) });
        Sleep= (EditText) findViewById(R.id.Sleep);
        Cycles.setFilters(new InputFilter[]{ new InputFilterMinMax(1, 9999) });
        spinnerShutdownCommand= (Spinner) findViewById(R.id.spinnerShutdownCommand);

        mAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, ShutdownCommandList);
        spinnerShutdownCommand.setAdapter(mAdapter);


        swithCheckClock= (Switch) findViewById(R.id.swithCheckClock);
        Uphours_Begin= (EditText) findViewById(R.id.Uphours_Begin);
        Uphours_Begin.setFilters(new InputFilter[]{ new InputFilterMinMax(0, 23) });
        Uphours_End= (EditText) findViewById(R.id.Uphours_End);
        Uphours_End.setFilters(new InputFilter[]{ new InputFilterMinMax(0, 23) });

        IP_Range= (EditText) findViewById(R.id.IP_Range);
        Sockets= (EditText) findViewById(R.id.Sockets);
        swithULDLRate= (Switch) findViewById(R.id.swithULDLRate);
        ULDL_Rate= (EditText) findViewById(R.id.ULDL_Rate);
        ULDL_Rate.setFilters(new InputFilter[]{ new InputFilterMinMax(0, 9999) });
        swithLoadAverage= (Switch) findViewById(R.id.swithLoadAverage);
        Load_Average= (EditText) findViewById(R.id.Load_Average);
        Load_Average.setFilters(new InputFilter[]{ new InputFilterMinMax(0, 9999) });
        swithHDDIORate= (Switch) findViewById(R.id.swithHDDIORate);
        HDDIO_Rate= (EditText) findViewById(R.id.HDDIO_Rate);
        HDDIO_Rate.setFilters(new InputFilter[]{ new InputFilterMinMax(0, 9999) });
        swithCheckSmbstatus= (Switch) findViewById(R.id.swithCheckSmbstatus);
        swithCheckUsers= (Switch) findViewById(R.id.swithCheckUsers);

        swithLogToSyslog= (Switch) findViewById(R.id.swithLogToSyslog);
        swithVerbose= (Switch) findViewById(R.id.swithVerbose);
        swithFake= (Switch) findViewById(R.id.swithFake);

        Extra_Options= (EditText) findViewById(R.id.Extra_Options);
    }

    private void pupulateViews()
    {
        swithEnable.setChecked(Settings.getEnable());


        Util.SetInteger(Cycles,Settings.getCycles());
        Util.SetInteger(Sleep,Settings.getSleep());
        spinnerShutdownCommand.setSelection(Settings.getShutdowncommand());

        swithCheckClock.setChecked(Settings.getCheckclockactive());
        Util.SetInteger(Uphours_Begin,Settings.getUphoursBegin());
        Util.SetInteger(Uphours_End,Settings.getUphoursEnd());

        IP_Range.setText(Settings.getRange());
        Sockets.setText(Settings.getNsocketnumbers());
        swithULDLRate.setChecked(Settings.getUldlcheck());
        Util.SetInteger(ULDL_Rate,Settings.getUldlrate());
        swithLoadAverage.setChecked(Settings.getLoadaveragecheck());
        Util.SetInteger(Load_Average,Settings.getLoadaverage());
        swithHDDIORate.setChecked(Settings.getHddiocheck());
        Util.SetInteger(HDDIO_Rate,Settings.getHddiorate());

        if(Settings.getChecksamba() == null)
        {
            swithCheckSmbstatus.setChecked(false);
            swithCheckSmbstatus.setEnabled(false);
        }
        else
            swithCheckSmbstatus.setChecked(false);
        if(Settings.getCheckcli() == null)
        {
            swithCheckUsers.setChecked(false);
            swithCheckUsers.setEnabled(false);
        }
        else
        swithCheckUsers.setChecked(Settings.getCheckcli());

        swithLogToSyslog.setChecked(Settings.getSyslog());
        swithVerbose.setChecked(Settings.getVerbose());
        swithFake.setChecked(Settings.getFake());

        Extra_Options.setText(Settings.getExtraoptions());
    }

    private void UpdateSettings()
    {

        if(!IsFinalized(true) || Settings == null) return;


        Settings.setEnable(swithEnable.isChecked());

        Settings.setCycles( Util.GetInteger(Cycles));
        Settings.setSleep(Util.GetInteger(Sleep));
        Settings.setShutdowncommand(spinnerShutdownCommand.getSelectedItemPosition());

        Settings.setCheckclockactive(swithCheckClock.isChecked());

        Settings.setUphoursBegin(Util.GetInteger(Uphours_Begin));

        Settings.setUphoursEnd(Util.GetInteger(Uphours_End));

        Settings.setRange(IP_Range.getText().toString());
        Settings.setNsocketnumbers(Sockets.getText().toString());
        Settings.setUldlcheck(swithULDLRate.isChecked());

        Settings.setUldlrate(Util.GetInteger(ULDL_Rate));
        Settings.setLoadaveragecheck(swithLoadAverage.isChecked());

        Settings.setLoadaverage(Util.GetInteger(Load_Average));
        Settings.setHddiocheck(swithHDDIORate.isChecked());
        Settings.setHddiorate(Util.GetInteger(HDDIO_Rate));
        if(Settings.getChecksamba() != null)
            Settings.setChecksamba(swithCheckSmbstatus.isChecked());
        if(Settings.getCheckcli() != null)
            Settings.setCheckcli(swithCheckUsers.isChecked());

        Settings.setSyslog(	swithLogToSyslog.isChecked());
        Settings.setVerbose(swithVerbose.isChecked());
        Settings.setFake(swithFake.isChecked());

        Settings.setExtraoptions(Extra_Options.getText().toString());

        mController.setSettings(Settings,new CallbackImpl(this){

            @Override
            public void onResponse(Call call, Response response) throws IOException, InterruptedException {
                super.onResponse(call, response);
                new CheckDirty(AutoshutdownActivity.this).Check();
            }
        });

    }

}
