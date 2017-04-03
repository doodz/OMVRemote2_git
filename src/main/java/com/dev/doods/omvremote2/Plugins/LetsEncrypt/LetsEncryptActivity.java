package com.dev.doods.omvremote2.Plugins.LetsEncrypt;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;

import com.dev.doods.base.R;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;

import Client.Call;
import Client.CallbackImpl;
import Client.Response;
import OMV.Classe.AppCompatBaseActivity;

public class LetsEncryptActivity extends AppCompatBaseActivity {


    private LetsEncryptController mContoller = new LetsEncryptController(this);
    private com.dev.doods.omvremote2.Plugins.LetsEncrypt.LetsEncrypt res;
    Switch switchScheduleRefresh;
    Switch switchTestCertificate;
    EditText DomainView;
    EditText WebRootView;
    EditText Adresse_MailView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lets_encrypt);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Save();
            }
        });

        BindView();

        mContoller.GetSettings(new CallbackImpl(this){
            @Override
            public void onResponse(Call call, Response response) throws IOException, InterruptedException {
                super.onResponse(call,response);
                res= response.GetResultObject(new TypeToken<com.dev.doods.omvremote2.Plugins.LetsEncrypt.LetsEncrypt>(){});
                mHandler.post(new Runnable() {
                    public void run() {
                        ShowSettings(res);
                    }
                });
            }
        });
    }

    private void BindView()
    {
        Adresse_MailView =(EditText) findViewById(R.id.Adresse_Mail);
        DomainView =(EditText) findViewById(R.id.Domain);
        WebRootView =(EditText) findViewById(R.id.WebRoot);
        switchScheduleRefresh=(Switch) findViewById(R.id.switchScheduleRefresh);
        switchTestCertificate=(Switch) findViewById(R.id.switchTestCertificate);
    }

    private void ShowSettings(com.dev.doods.omvremote2.Plugins.LetsEncrypt.LetsEncrypt res)
    {
        Adresse_MailView.setText(res.getEmail());
        DomainView.setText(res.getDomain());
        WebRootView.setText(res.getWebroot());
        switchScheduleRefresh.setChecked(res.getEnable());
        switchTestCertificate.setChecked(res.getTestCert());
    }

    private void Save()
    {
        String str = Adresse_MailView.getText().toString();
        res.setEmail(str);

        str = DomainView.getText().toString();
        res.setDomain(str);

        str = WebRootView.getText().toString();
        res.setWebroot(str);

        res.setEnable(switchScheduleRefresh.isChecked());
        res.setTestCert(switchTestCertificate.isChecked());

        mContoller.SetSettings(res,new CallbackImpl(this)
                {
                    @Override
                    public void onResponse(Call call, Response response) throws IOException, InterruptedException {
                        super.onResponse(call,response);
                       showInfo(getString(R.string.Saved));
                    }
                }

        );
    }
}
