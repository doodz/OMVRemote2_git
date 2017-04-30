package com.dev.doods.omvremote2;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Client.Call;
import Client.Callback;
import Client.CallbackImpl;
import Client.Response;
import Controllers.CronController;
import Models.Cron;
import Models.Errors;
import Models.OmvUser;
import OMV.Base.AppCompatBaseActivity;
import OMV.Classe.openmediavault_default;
import utils.SnackBarError;
import com.dev.doods.omvremote2.R;
public class AddEditeCronActivity extends AppCompatBaseActivity {

    Switch swithEnable;
    Spinner spinnerTimeExec;
    Spinner spinnerMinute;
    Switch swithEveryNMinute;
    Spinner spinnerHour;
    Switch swithEveryNHour;
    Spinner spinnerDayMonth;
    Switch swithEveryNDayMonth;
    Spinner spinnerMonth;
    Spinner spinnerDayWeek;
    Spinner spinnerUser;
    EditText etCommand;
    Switch swithEmail;
    EditText etComment;
    FloatingActionButton fab;
    CronController mController = new CronController(this);
    Handler handler = new Handler();
    Cron mCron = new Cron();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edite_cron);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Bundle bundle = getIntent().getExtras();
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handler.post(new Runnable(){
                    public void run() {
                        Save();
                    }
                });
            }
        });

        BindViews();
        mController.EnumerateAllUsers(new Callback() {
            @Override
            public void onFailure(Call call, Exception e) {

            }

            @Override
            public void OnOMVServeurError(Call call, Errors error) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException, InterruptedException {
                final List<OmvUser> res = response.GetResultObject(new TypeToken<List<OmvUser>>(){});
                handler.post(new Runnable(){
                    public void run() {
                        PopulateUsersView(res);
                    }
                });
            }
        });

        if(bundle!= null && bundle.containsKey("Cron"))
        {
            setTitle(getString(R.string.edite_Cron));
            mCron = (Cron)bundle.getSerializable("Cron");
        }
        else {
            mCron = new Cron();
            mCron.setEnable(true);
            if(mController.GetApiVersion() == 3)
            {
                mCron.setUuid(openmediavault_default.OMV_CONFIGOBJECT_NEW_UUID);
            }

        }

        PopulateViews(mCron);

    }



    List<OmvUser> mUsers = new ArrayList<OmvUser>();
    private void PopulateUsersView(List<OmvUser> users)
    {
        mUsers.clear();
        mUsers.addAll(users);
        spinnerUserAdapter.notifyDataSetChanged();
        int i = mUsers.indexOf(mCron.getUsername());
        spinnerUser.setSelection(i);

    }
    ArrayAdapter<OmvUser> spinnerUserAdapter;
    private void BindViews()
    {
        swithEnable = (Switch)findViewById(R.id.swithEnable);
        spinnerTimeExec = (Spinner)findViewById(R.id.spinnerTimeExec);

        spinnerMinute = (Spinner)findViewById(R.id.spinnerMinute);
        swithEveryNMinute= (Switch)findViewById(R.id.swithEveryNMinute);

        spinnerHour = (Spinner)findViewById(R.id.spinnerHour);
        swithEveryNHour = (Switch)findViewById(R.id.swithEveryNHour);

        spinnerDayMonth = (Spinner)findViewById(R.id.spinnerDayMonth);
        swithEveryNDayMonth = (Switch)findViewById(R.id.swithEveryNDayMonth);

        spinnerMonth = (Spinner)findViewById(R.id.spinnerMonth);
        spinnerDayWeek = (Spinner)findViewById(R.id.spinnerDayWeek);

        spinnerUser = (Spinner)findViewById(R.id.spinnerUser);
        etCommand = (EditText)findViewById(R.id.etCommand);

        swithEmail = (Switch)findViewById(R.id.swithEmail);
        etComment = (EditText)findViewById(R.id.etComment);
        spinnerUserAdapter = new ArrayAdapter<OmvUser>(this, android.R.layout.simple_spinner_item, mUsers);
        spinnerUser.setAdapter(spinnerUserAdapter);
    }

    private void PopulateViews(Cron cron)
    {
        swithEnable.setChecked(cron.getEnable());
        swithEveryNMinute.setChecked(cron.getEverynminute());
        swithEveryNHour.setChecked(cron.getEverynhour());
        swithEveryNDayMonth.setChecked(cron.getEveryndayofmonth());
        etCommand.setText(cron.getCommand());
        swithEmail.setChecked(cron.getSendemail());
        etComment.setText(cron.getComment());


        ArrayAdapter<String> spinnerTimeExecAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, CronController.TimeExecVal);
        spinnerTimeExec.setAdapter(spinnerTimeExecAdapter);
        int i = CronController.TimeExecValServer.indexOf(cron.getExecution());
        spinnerTimeExec.setSelection(i);

        ArrayAdapter<String> spinnerMinuteAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, CronController.MinuteVal);
        spinnerMinute.setAdapter(spinnerMinuteAdapter);
        i = CronController.MinuteVal.indexOf(cron.getMinute());
        spinnerMinute.setSelection(i);

        ArrayAdapter<String> spinnerHourAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, CronController.HourVal);
        spinnerHour.setAdapter(spinnerHourAdapter);
        i = CronController.HourVal.indexOf(cron.getHour());
        spinnerHour.setSelection(i);

        ArrayAdapter<String> spinnerDayMonthAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, CronController.DayVal);
        spinnerDayMonth.setAdapter(spinnerDayMonthAdapter);
        i = CronController.DayVal.indexOf(cron.getDayofmonth());
        spinnerDayMonth.setSelection(i);

        ArrayAdapter<String> spinnerMonthAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, CronController.MonthVal);
        spinnerMonth.setAdapter(spinnerHourAdapter);
        i = CronController.MonthVal.indexOf(cron.getMonth());
        spinnerMonth.setSelection(i);

    }

    private void PopulateCron(Cron cron)
    {
        cron.setEnable(swithEnable.isChecked());
        cron.setEverynminute(swithEveryNMinute.isChecked());
        cron.setEverynhour(swithEveryNHour.isChecked());
        cron.setEveryndayofmonth(swithEveryNDayMonth.isChecked());
        cron.setCommand(etCommand.getText().toString());
        cron.setSendemail(swithEmail.isChecked());
        cron.setComment(etComment.getText().toString());
        int i = spinnerTimeExec.getSelectedItemPosition();
        cron.setExecution(CronController.TimeExecValServer.get(i));
        cron.setMinute((String)spinnerMinute.getSelectedItem());
        cron.setHour((String)spinnerHour.getSelectedItem());
        cron.setDayofweek((String)spinnerDayMonth.getSelectedItem());
        cron.setMonth((String)spinnerMonth.getSelectedItem());
        i = spinnerUser.getSelectedItemPosition();
        cron.setUsername(mUsers.get(i).getName());


    }

    private void Save()
    {
        PopulateCron(mCron);

        mController.setCron(mCron, new CallbackImpl(this) {
            @Override
            public void onResponse(Call call, Response response) throws IOException, InterruptedException {
                AddEditeCronActivity.this.finish();
            }
        });
    }
}
