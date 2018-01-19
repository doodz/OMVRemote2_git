package com.dev.doods.omvremote2;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Client.Call;
import Client.Callback;
import Client.Response;
import Controllers.HomeController;
import Models.Certificate;
import Models.Errors;
import Models.Result;
import Models.WebSettings;
import utils.InputFilterMinMax;

public class HomeSettingsActivity extends AppCompatActivity {

    private Switch _SwithEnableSSL;
    private Switch _SwithForceSSL;
    private EditText _TextViewPort;
    private EditText _TextViewPortSSL;
    private EditText _TextViewTimeout;
    private Spinner _SpinnerSertificates;
    private Button _ButtonSave;
    private HomeController controller;
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_settings);

        handler= new Handler();
        controller = new HomeController(this);




        _SwithEnableSSL = (Switch) findViewById(R.id.swithEnableSSL);
        _SwithForceSSL = (Switch) findViewById(R.id.swithForceSSL);
        _TextViewPort = (EditText)findViewById(R.id.etPort);
        _TextViewPortSSL = (EditText)findViewById(R.id.etPortSSL);
        _TextViewTimeout = (EditText)findViewById(R.id.etTimeout);
        _SpinnerSertificates = (Spinner)findViewById(R.id.spinnerSertificates);


        _TextViewPort.setFilters(new InputFilter[]{ new InputFilterMinMax(1, 99999) });
        _TextViewPortSSL.setFilters(new InputFilter[]{ new InputFilterMinMax(1, 99999) });
        GetSettings();
    }


    private void GetSettings()
    {

        controller.GetSettings(new Callback() {
            @Override
            public void onFailure(Call call, Exception e) {

            }

            @Override
            public void OnOMVServeurError(Call call, Errors error) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException, InterruptedException {
                final WebSettings res = response.GetResultObject(new TypeToken<WebSettings>(){});
                handler.post(new Runnable(){
                    public void run() {
                        InitSettings(res);
                    }
                });
            }
        });

        controller.GetCertificate(new Callback() {
            @Override
            public void onFailure(Call call, Exception e) {

            }

            @Override
            public void OnOMVServeurError(Call call, Errors error) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException, InterruptedException {

                final Result<Certificate> res = response.GetResultObject( new TypeToken<Result<Certificate>>(){});
                handler.post(new Runnable(){
                    public void run() {
                        setCertificate(res.getData());
                    }
                });
            }
        });

    }

    private void setCertificate(List<Certificate> res)
    {
       List<String> arraySpinner = new ArrayList<>();

        for (Certificate cer:res ) {
            arraySpinner.add(cer.getName());
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        if(_SpinnerSertificates == null)_SpinnerSertificates = (Spinner)findViewById(R.id.spinnerSertificates);
        _SpinnerSertificates.setAdapter(adapter);
    }



    private void InitSettings(final WebSettings settings)
    {



        _SwithEnableSSL.setChecked(settings.getEnablessl());
        _SwithEnableSSL.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                _SwithForceSSL.setEnabled(b);
                _TextViewPortSSL.setEnabled(b);
                _SpinnerSertificates.setEnabled(b);
            }
        });

        _SwithForceSSL.setChecked(settings.getForcesslonly());
        _SwithForceSSL.setEnabled(settings.getEnablessl());

        _TextViewPort.setText(Integer.toString(settings.getPort()));

        _TextViewPortSSL.setText(Integer.toString(settings.getSslport()));
        _TextViewPortSSL.setEnabled(settings.getEnablessl());

        _TextViewTimeout.setText(Integer.toString(settings.getTimeout()));


        _SpinnerSertificates.setEnabled(settings.getEnablessl());

        _ButtonSave = (Button) findViewById(R.id.buttonSave);
        _ButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handler.post(new Runnable(){
                    public void run() {

                        WebSettings settings = new WebSettings();
                        settings.setEnablessl(_SwithEnableSSL.isChecked());
                        settings.setForcesslonly(_SwithForceSSL.isChecked());
                        settings.setPort(Integer.parseInt(_TextViewPort.getText().toString()));
                        settings.setSslcertificateref("");
                        settings.setSslport(Integer.parseInt(_TextViewPortSSL.getText().toString()));
                        settings.setTimeout(Integer.parseInt(_TextViewTimeout.getText().toString()));
                        controller.SetSettings(settings, new Callback() {
                            @Override
                            public void onFailure(Call call, Exception e) {

                            }

                            @Override
                            public void OnOMVServeurError(Call call, Errors error) {

                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException, InterruptedException {

                            }
                        });
                    }
                });
            }
        });

    }
}
