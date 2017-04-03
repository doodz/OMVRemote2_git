package com.dev.doods.omvremote2;

import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.google.gson.reflect.TypeToken;

import java.io.IOException;

import Client.Call;
import Client.Callback;
import Client.Response;
import Controllers.UpdateController;
import Models.Errors;
import Models.UpdateSettings;

public class UpdateSetingsActivity extends AppCompatActivity {

    private UpdateController controller;
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.dev.doods.base.R.layout.activity_update_setings);

        controller = new UpdateController(this);
        handler= new Handler();


        controller.getSettings(new Callback() {
            @Override
            public void onFailure(Call call, Exception e) {

            }

            @Override
            public void OnOMVServeurError(Call call, Errors error) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final UpdateSettings res =response.GetResultObject( new TypeToken<UpdateSettings>(){});
                handler.post(new Runnable(){
                    public void run() {
                        initSettings(res);
                    }
                });
            }
        });
        final Button saveButton = (Button) findViewById(com.dev.doods.base.R.id.SaveButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            controller.setSettings(mSettings, new Callback() {
                @Override
                public void onFailure(Call call, Exception e) {
                    Snackbar.make(saveButton, "Error", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

                @Override
                public void OnOMVServeurError(Call call, Errors error) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    UpdateSetingsActivity.this.finish();

                }
            });

            }
        });
    }

    private UpdateSettings mSettings;
    private void initSettings(UpdateSettings settings)
    {
        mSettings = settings;
        Switch pre_release_switch = (Switch) findViewById(com.dev.doods.base.R.id.pre_release_switch);
        pre_release_switch.setChecked(settings.getProposed());

        pre_release_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                          @Override
                                                          public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                                              mSettings.setProposed(b);
                                                          }
                                                      });

        Switch Community_switch = (Switch) findViewById(com.dev.doods.base.R.id.Community_switch);
        Community_switch.setChecked(settings.getPartner());
        Community_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mSettings.setPartner(b);
            }
        });

    }
}
