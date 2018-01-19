package com.dev.doods.omvremote2.Plugins.Fail2ban;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;

import com.dev.doods.omvremote2.R;

import java.io.IOException;

import Client.Call;
import Client.CallbackImpl;
import Client.Response;
import OMV.Base.AppCompatBaseActivity;
import OMV.Classe.openmediavault_default;

public class AddEditeJailActivity extends AppCompatBaseActivity {
    Fail2banController mController = new Fail2banController(this);
    public static String JAIL_BUNDLE_KEY = "JailKay";
    private EditText Name;
    private EditText Port;
    private EditText Max_Retry;
    private EditText Ban_Time;
    private EditText Filter;
    private EditText Log_Path;
    private Switch SwithEnable;
    private Jail mJail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edite_jail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Save();
            }
        });

        BindViews();

        Bundle bundle = getIntent().getExtras();
        if(bundle!= null && bundle.containsKey(JAIL_BUNDLE_KEY))
        {
            setTitle(getString(R.string.edite));
            mJail = (Jail)bundle.getSerializable(JAIL_BUNDLE_KEY);
        }
        else {
            mJail = new Jail();
            mJail.setEnable(true);
            if(mController.GetApiVersion() == 3)
            {
                mJail.setUuid(openmediavault_default.OMV_CONFIGOBJECT_NEW_UUID);
            }

        }

        PopulateViews();

    }

    private void BindViews()
    {
        SwithEnable = (Switch) findViewById(R.id.swithEnable);
        Name = (EditText) findViewById(R.id.Name);
        Port = (EditText) findViewById(R.id.Port);
        Max_Retry = (EditText) findViewById(R.id.Max_Retry);
        Ban_Time = (EditText) findViewById(R.id.Ban_Time);
        Filter = (EditText) findViewById(R.id.Filter);
        Log_Path = (EditText) findViewById(R.id.Log_Path);

    }

    private void PopulateViews()
    {
        Name.setText(mJail.getName());
        Port.setText(mJail.getPort());
        Max_Retry.setText(mJail.getMaxretry());
        Ban_Time.setText(mJail.getBantime());
        Filter.setText(mJail.getFilter());
        Log_Path.setText(mJail.getLogpath());
        SwithEnable.setChecked(mJail.getEnable());
    }

    private void PopulateJail()
    {

        mJail.setName(Name.getText().toString());
        mJail.setPort(Port.getText().toString());
        mJail.setMaxretry(Max_Retry.getText().toString());
        mJail.setBantime(Ban_Time.getText().toString());
        mJail.setFilter(Filter.getText().toString());
        mJail.setLogpath(Log_Path.getText().toString());
        mJail.setEnable(SwithEnable.isChecked());
    }

    private void Save()
    {
        if(IsFinalized(true)) {
            PopulateJail();

            mController.setJail(mJail, new CallbackImpl(this) {
                @Override
                public void onResponse(Call call, Response response) throws IOException, InterruptedException {

                    AddEditeJailActivity.this.finish();
                }
            });
        }
    }

}
