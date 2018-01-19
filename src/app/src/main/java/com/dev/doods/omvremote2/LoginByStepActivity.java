package com.dev.doods.omvremote2;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Switch;

import Client.Host;
import Client.JSONRPCClient;
import DAL.HostsDAO;
import Interfaces.IYesNoListenerDialog;
import Models.Errors;
import OMVFragment.Dialogs.YesNoDialog;
import utils.CallBackAsyncTask;
import utils.CallBackTask;
import utils.CheckHostNameTask;
import utils.CheckURLTask;
import utils.InputFilterMinMax;
import utils.SnackBarError;
import utils.UserLoginTask;


public class LoginByStepActivity extends AppCompatActivity implements IYesNoListenerDialog {

    private FloatingActionButton fab;

    private EditText mHostNameView;
    private EditText mUrlView;
    private AutoCompleteTextView mLoginView;
    private EditText mPasswordView;
    private EditText mPortView;
    private Switch mSSLView;
    private CardView mCardUser;
    private CardView mCardPort;
    private CardView mCardHost;
    private CardView mCardWol;
    private EditText mWolPortView;
    private EditText mMacView;
    private Host mHost;
    private int mState = 0;
    private Boolean mCanSAve = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_by_step);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mCanSAve)
                    Save();
                else
               switch (mState)
               {
                   case 0:
                       CheckHost();
                       break;
                   case 1:
                       CheckURL();
                       break;
                  default:
                       CheckUser();
                       break;
               }
            }
        });

        BindViews();

        Bundle bundle = getIntent().getExtras();
        if(bundle != null && bundle.containsKey("host"))
        {
            mHost = (Host) bundle.getSerializable("host");
        }
        else
            mHost = new Host();

        PopulateViews();
    }

    private void PopulateViews()
    {
        mCardPort.setVisibility(View.GONE);
        mCardUser.setVisibility(View.GONE);
        mCardWol.setVisibility(View.GONE);
        mHostNameView.setText(mHost.getName());
        mUrlView.setText(mHost.getAddr());
        mLoginView.setText(mHost.getUser());
        mPasswordView.setText(mHost.getPass());
        mPortView.setText(Integer.toString(mHost.getPort()));
        mSSLView.setChecked(mHost.getSll());
        mWolPortView.setText(Integer.toString(mHost.getWolport()));
        mMacView.setText(mHost.getMacAddr());
    }
    private void BindViews()
    {
        mCardPort = (CardView)findViewById(R.id.card_port);
        mCardHost = (CardView)findViewById(R.id.card_host);
        mCardUser = (CardView)findViewById(R.id.card_user);
        mCardWol = (CardView)findViewById(R.id.card_wol);
        mHostNameView = (EditText) findViewById(R.id.hostName);
        mUrlView = (EditText) findViewById(R.id.host);

        mLoginView = (AutoCompleteTextView) findViewById(R.id.login);
        mPasswordView = (EditText) findViewById(R.id.password);
        mPortView = (EditText) findViewById(R.id.etPort);

        mPortView.setFilters(new InputFilter[]{ new InputFilterMinMax(1, 99999) });
        mSSLView= (Switch) findViewById(R.id.swithEnableSSL);

        mWolPortView = (EditText) findViewById(R.id.etWolPort);
        mWolPortView.setFilters(new InputFilter[]{ new InputFilterMinMax(1, 99999) });
        mMacView = (EditText) findViewById(R.id.etMac);
    }


    private void CheckHost()
    {
        if(!CheckVarByState())
        {
            return;
        }
        String hostname = mUrlView.getText().toString();


        AsyncTask task = new CheckHostNameTask(hostname);
        new CallBackAsyncTask(task, new CallBackTask() {
            @Override
            public void handleMessageError(@Nullable Errors error) {
                new SnackBarError(fab,error.getMessage(),false);

                if(error.getCode() == 1)
                {
                    DialogFragment dialog = new YesNoDialog();
                    Bundle args = new Bundle();
                    args.putString("title", error.getMessage());
                    args.putString("message", getString(R.string.Continue));
                    dialog.setArguments(args);
                    //dialog.setTargetFragment(OMVSystemActivity.this, YesNoDialog.YES_NO_CALL);
                    dialog.show(getSupportFragmentManager(), "tag");

                }


            }

            @Override
            public void handleFinich() {
                mState++;
                mCardPort.setVisibility(View.VISIBLE);
            }
        }).run();
    }

    private void CheckURL()
    {
        if(!CheckVarByState())
        {
            return;
        }
        String hostname = mUrlView.getText().toString();
        Integer port =Integer.parseInt(mPortView.getText().toString());
        boolean ssl = mSSLView.isChecked();




        AsyncTask task = new CheckURLTask(hostname,port,ssl);
        new CallBackAsyncTask(task, new CallBackTask() {
            @Override
            public void handleMessageError(@Nullable Errors error) {
                new SnackBarError(fab,error.getMessage(),false);
            }

            @Override
            public void handleFinich() {
                mState++;
                mCardUser.setVisibility(View.VISIBLE);
            }
        }).run();

    }

    private void CheckUser()
    {
        if(!CheckVarByState())
        {
            return;
        }
        String hostname = mUrlView.getText().toString();
        Integer port =Integer.parseInt(mPortView.getText().toString());
        boolean ssl = mSSLView.isChecked();
        String login = mLoginView.getText().toString();
        String password = mPasswordView.getText().toString();




        AsyncTask task = new UserLoginTask(hostname,login,password,port,ssl);
        new CallBackAsyncTask(task, new CallBackTask() {
            @Override
            public void handleMessageError(@Nullable Errors error) {
                new SnackBarError(fab,error.getMessage(),false);
            }

            @Override
            public void handleFinich() {
                mCardWol.setVisibility(View.VISIBLE);

                fab.setImageResource(R.drawable.ic_save_black_24dp);
                mCanSAve = true;
            }
        }).run();
    }

    private boolean CheckVarByState()
    {
        mHostNameView.setError(null);
        mLoginView.setError(null);
        mPasswordView.setError(null);
        mPortView.setError(null);
        if(mState >=0)
        {
            String hostname = mHostNameView.getText().toString();

            if (TextUtils.isEmpty(hostname)) {
                mHostNameView.setError(getString(R.string.error_field_required));
                mHostNameView.requestFocus();;
                return false;
            }
            String url = mUrlView.getText().toString();
            if (TextUtils.isEmpty(url)) {
                mUrlView.setError(getString(R.string.error_field_required));
                mUrlView.requestFocus();;
                return false;
            }
        }

        if(mState >=1)
        {

          String port = mPortView.getText().toString();
            if (TextUtils.isEmpty(port)) {
                mPortView.setError(getString(R.string.error_field_required));
                mPortView.requestFocus();
                return false;
            }

        }
        if(mState >=2) {
            String login = mLoginView.getText().toString();
            String password = mPasswordView.getText().toString();

            if (TextUtils.isEmpty(login)) {
                mLoginView.setError(getString(R.string.error_field_required));
                mLoginView.requestFocus();
                return false;
            }
            if (TextUtils.isEmpty(password)) {
                mPasswordView.setError(getString(R.string.error_field_required));
                mPasswordView.requestFocus();
                return false;
            }


        }

        return true;
    }

    private void Save()
    {
        String hostname = mHostNameView.getText().toString();
        String addr = mUrlView.getText().toString();
        Integer port =Integer.parseInt(mPortView.getText().toString());
        boolean ssl = mSSLView.isChecked();
        String login = mLoginView.getText().toString();
        String password = mPasswordView.getText().toString();
        Integer wolport = Integer.parseInt(mWolPortView.getText().toString());
        String macaddr =  mMacView.getText().toString();



        HostsDAO datasource = new HostsDAO(getApplicationContext());
        datasource.open();

        mHost.setName(hostname);
        mHost.setAddr(addr);
        mHost.setUser(login);
        mHost.setPass(password);
        mHost.setPort(port);
        mHost.setSll(ssl);
        mHost.setWolport(wolport);
        mHost.setMacAddr(macaddr);

        JSONRPCClient jsonRpc = JSONRPCClient.getInstance();
        jsonRpc.SetHost(mHost);

        if(mHost.getId() == 0)
            datasource.createHost(mHost);
        else
            datasource.UpdateHost(mHost);
        datasource.close();
        startActivity(new Intent(LoginByStepActivity.this, HomeActivity.class));
    }

    @Override
    public void onYesNoActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode ==  Activity.RESULT_OK)
        {
            mState++;
            mCardPort.setVisibility(View.VISIBLE);
        }
    }
}
