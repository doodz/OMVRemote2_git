package com.dev.doods.omvremote2.Storage.Smart;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import com.dev.doods.omvremote2.R;
import com.dev.doods.omvremote2.System.AddEditeCronActivity;
import com.dev.doods.omvremote2.System.CronController;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Client.Call;
import Client.CallbackImpl;
import Client.Response;
import Models.OmvUser;
import OMV.Base.AppCompatBaseActivity;
import OMV.Classe.openmediavault_default;
import utils.ListUtils;

public class AddEditeScheduledTestActivity extends AppCompatBaseActivity {

    public static String BUNDLE_SCHEDULED_TEST_KEY = "BUNDLE_SCHEDULED_TEST_KEY";
    private SmartController mController = new SmartController(this);
    private SmartScheduledTest mSmartScheduledTest;
    private Switch swithEnable;
    private List<SmartDevices> DevicesList = new ArrayList<SmartDevices>();
    ArrayAdapter<SmartDevices> spinnerDeviceAdapter;
    private Spinner spinnerDevice;
    private Spinner spinnerType;
    private Spinner spinnerHour;
    private Spinner spinnerMonth;
    private Spinner spinnerDayMonth;
    private Spinner spinnerDayWeek;

    private EditText etComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edite_scheduled_test);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        BindView();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!IsFinalized(true) && CanSave()) return;
                Save();

            }
        });
        Bundle bundle = getIntent().getExtras();
        if(bundle!= null && bundle.containsKey(BUNDLE_SCHEDULED_TEST_KEY))
        {
            setTitle(getString(R.string.edite_Scheduled));
            mSmartScheduledTest = (SmartScheduledTest)bundle.getSerializable(BUNDLE_SCHEDULED_TEST_KEY);
        }
        else {
            mSmartScheduledTest = new SmartScheduledTest();
            mSmartScheduledTest.setEnable(true);
            if(mController.GetApiVersion() == 3)
            {
                mSmartScheduledTest.setUuid(openmediavault_default.OMV_CONFIGOBJECT_NEW_UUID);
            }

        }

        mController.enumerateMonitoredDevices(new CallbackImpl(this)
        {
            @Override
            public void onResponse(Call call, Response response) throws IOException, InterruptedException {
                super.onResponse(call,response);
                final List<SmartDevices> res = response.GetResultObject(new TypeToken<List<SmartDevices>>(){});
                mHandler.post(new Runnable(){
                    public void run() {
                        PopulatespinnerDevice(res);
                    }
                });
            }
        });

        PopulateView();
    }


    private void BindView()
    {

        swithEnable = (Switch)findViewById(R.id.swithEnable);

        spinnerDevice = (Spinner) findViewById(R.id.spinnerDevice);

        spinnerDeviceAdapter = new ArrayAdapter<SmartDevices>(this, android.R.layout.simple_spinner_item, DevicesList);
        spinnerDevice.setAdapter(spinnerDeviceAdapter);

        spinnerType = (Spinner) findViewById(R.id.spinnerType);
        spinnerHour = (Spinner) findViewById(R.id.spinnerHour);
        spinnerMonth = (Spinner) findViewById(R.id.spinnerMonth);
        spinnerDayMonth = (Spinner) findViewById(R.id.spinnerDayMonth);
        spinnerDayWeek = (Spinner) findViewById(R.id.spinnerDayWeek);

        etComment = (EditText)findViewById(R.id.etComment);

    }

    private void PopulateView()
    {
        swithEnable.setChecked(mSmartScheduledTest.getEnable());

        ArrayAdapter<String> spinnerHourAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, utils.ListUtils.HourVal);
        spinnerHour.setAdapter(spinnerHourAdapter);
        //int i = utils.ListUtils.HourVal.indexOf(mSmartScheduledTest.getHour());
        spinnerHour.setSelection(getIndexspinnerHour(mSmartScheduledTest.getHour()));

        ArrayAdapter<String> spinnerDayMonthAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, utils.ListUtils.DayVal);
        spinnerDayMonth.setAdapter(spinnerDayMonthAdapter);
        //i = utils.ListUtils.DayVal.indexOf(mSmartScheduledTest.getDayofmonth());
        spinnerDayMonth.setSelection(getIndexspinner(mSmartScheduledTest.getDayofmonth()));

        ArrayAdapter<String> spinnerMonthAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, utils.ListUtils.MonthVal);
        spinnerMonth.setAdapter(spinnerMonthAdapter);
        //i = utils.ListUtils.MonthVal.indexOf(mSmartScheduledTest.getMonth());
        spinnerMonth.setSelection(getIndexspinner(mSmartScheduledTest.getMonth()));

        ArrayAdapter<String> spinnerDayWeekAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ListUtils.DayOfWeekVal);
        spinnerDayWeek.setAdapter(spinnerDayWeekAdapter);
        // i = utils.ListUtils.DayOfWeekVal.indexOf(mSmartScheduledTest.getDayofweek());
        spinnerDayWeek.setSelection(getIndexspinner(mSmartScheduledTest.getDayofweek()));

        ArrayAdapter<String> spinnerTypeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, SmartController.ScheduledTestTypeList);
        spinnerType.setAdapter(spinnerTypeAdapter);
        //int i = SmartController.ScheduledTestTypeList.indexOf(mSmartScheduledTest.getType());
        spinnerType.setSelection(getIndexspinnerType(mSmartScheduledTest.getType()));

        etComment.setText(mSmartScheduledTest.getComment());
    }

    private void PopulatespinnerDevice(List<SmartDevices> devices)
    {
        DevicesList.clear();
        DevicesList.addAll(devices);
        spinnerDeviceAdapter.notifyDataSetChanged();
        int i = DevicesList.indexOf(mSmartScheduledTest.getDevicefile());
        spinnerDevice.setSelection(i);
    }
    private boolean CanSave()
    {
        return DevicesList.size() >0;
    }

    private int getIndexspinnerType(String val)
    {
        if(val == null ) return 0;
        switch (val)
        {
            case "S":
                return 0;
            case "L":
                return 1;
            case "C":
                return 2;
            case "O":
                return 3;
            default:
                return -1;
        }

    }

    private int getIndexspinner(String val)
    {
        if(val == null ) return 0;
        if(val.equals("*")) return 0;

        int i = Integer.parseInt(val);

        return i;

    }

    private int getIndexspinnerHour(String val)
    {
        if(val == null ) return 0;
        if(val.equals("*")) return 0;

        int i = Integer.parseInt(val);

        return ++i;

    }

    private String formateValueSpiner(Spinner spinner)
    {
        String tmp = (String)spinner.getSelectedItem();
        int i = spinner.getSelectedItemPosition();

        if(i == 0)
        {
            return "*";
        }
        if(i>0 && i<10)
            return "0"+i;

        return ""+i;

    }

    private String formateValueSpinerHour(Spinner spinner)
    {
        String tmp = (String)spinner.getSelectedItem();
        int i = spinner.getSelectedItemPosition();

        if(i == 0)
        {
            return "*";
        }
        if(i>0 && i<11)
            return "0"+--i;

        return ""+--i;

    }

    private String formateValueDayofweek(Spinner spinner)
    {
        int i = spinnerDayWeek.getSelectedItemPosition();
        String str = null;
        if(i == 0)
        {
            str ="*";
        }
        else
            str = ""+i;
        return str;

    }

    private void Save()
    {
        mSmartScheduledTest.setEnable(swithEnable.isChecked());
        SmartDevices selected = (SmartDevices)(spinnerDevice.getSelectedItem());
        mSmartScheduledTest.setDevicefile(selected.getDevicelinks().get(0));

        String selectedtype = (String)spinnerType.getSelectedItem();
        mSmartScheduledTest.setType(selectedtype.charAt(0)+"");

        mSmartScheduledTest.setHour(formateValueSpinerHour(spinnerHour));
        mSmartScheduledTest.setMonth( formateValueSpiner(spinnerMonth));
        mSmartScheduledTest.setDayofmonth(formateValueSpiner(spinnerDayMonth));
        mSmartScheduledTest.setDayofweek(formateValueDayofweek(spinnerDayWeek));

        mSmartScheduledTest.setComment(etComment.getText().toString());

       mController.setScheduledTest(mSmartScheduledTest,new CallbackImpl(this) {
           @Override
           public void onResponse(Call call, Response response) throws IOException, InterruptedException {
               super.onResponse(call,response);
               AddEditeScheduledTestActivity.this.finish();
           }
       });
    }
}
