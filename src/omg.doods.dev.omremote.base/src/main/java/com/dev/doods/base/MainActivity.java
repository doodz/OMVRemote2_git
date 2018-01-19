package com.dev.doods.base;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import Adapters.ServicesAdapter;
import Client.Call;
import Client.Callback;
import Client.Response;
import Controllers.HomeController;
import Models.Datum;
import Models.Errors;
import Models.Result;
import Models.SystemInformation;
import Models.Value;
import Deserializers.ValueDeserializer;
import utils.Util;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private HomeController controller;
    Handler handler;
    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        handler= new Handler();
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);
        controller = new HomeController(this);

    }

    @Override
       public void onClick(View v) {

        Log.v("MainActivity","Onclick from MainActivity");

        if(v.getId() ==R.id.fab)
        {

            showInfo("Get System Information");

            controller.GetSystemInformation(new Callback(){


                @Override
                public void onFailure(Call call, Exception e) {
                    e.printStackTrace();
                    showInfo("Error");
                }

                @Override
                public void OnOMVServeurError(Call call, Errors error) {
                    showInfo(error.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    showInfo("onResponse");

                    GsonBuilder gsonBuilder = new GsonBuilder();
                    gsonBuilder.registerTypeAdapter(Value.class, new ValueDeserializer());
                    //gsonBuilder.registerTypeAdapter(SystemInformation.class, new SystemInformationDeserializer());
                    Gson gson = gsonBuilder.create();
                    JsonElement j = response.GetJsonResult();

                    TypeToken tt = new TypeToken<ArrayList<SystemInformation>>(){};
                    Type t =  tt.getType();
                    // ArrayList<LinkedTreeMap<String,String>> oo =Util.FromJson(j,Object.class);
                    final  ArrayList<SystemInformation> res = gson.fromJson(j,t);
                    handler.post(new Runnable(){
                        public void run() {
                            showSystemInformation(res);
                        }
                    });


                }
            });

            controller.GetStatusServices(new Callback(){


                @Override
                public void onFailure(Call call, Exception e) {
                    e.printStackTrace();
                    showInfo("Error");
                }

                @Override
                public void OnOMVServeurError(Call call, Errors error) {
                    showInfo(error.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    showInfo("onResponse");


                    if(response.Error())
                    {

                    }
                    if(response.Result())
                    {
                        JsonElement j = response.GetJsonResult();

                        TypeToken tt = new TypeToken<Result<Datum>>(){};
                        Type t =  tt.getType();

                        final Result<Datum> res = Util.FromJson(j, t);
                        handler.post(new Runnable(){
                            public void run() {
                                showStatusServices(res.getData());
                            }
                        });

                    }

                }
            });
        }
    }

    private void showStatusServices(List<Datum> res)
    {

        ListView listView = (ListView) findViewById(R.id.StatusServices);
        listView.setAdapter(new ServicesAdapter(MainActivity.this,res));

    }

    private void showSystemInformation(List<SystemInformation> res)
    {
        for (SystemInformation d: res) {
            populateTexboxForSystemInformation(d.getName(),d.getValue().getText());
        }

    }

    private void populateTexboxForSystemInformation(String tb,Object obj)
    {
        String val = obj.toString();
        TextView tv = null;
        switch (tb.replace(' ','_'))
        {
            case "Hostname":
                tv = (TextView)findViewById(R.id.HostName);
                tv.setText(val);
                break;
            case "Version":
                tv = (TextView)findViewById(R.id.Version);
                tv.setText(val);
                break;
            case "Processor":
                tv = (TextView)findViewById(R.id.Processor);
                tv.setText(val);
                break;
            case "KernelItem":
                tv = (TextView)findViewById(R.id.Kernel);
                tv.setText(val);
                break;
            case "System_time":
                tv = (TextView)findViewById(R.id.System_time);
                tv.setText(val);
                break;
            case "Uptime":
                tv = (TextView)findViewById(R.id.Uptime);
                tv.setText(val);
                break;
            case "Load_average":
                tv = (TextView)findViewById(R.id.Load_average);
                tv.setText(val);
                break;
            case "CPU_usage":
                tv = (TextView)findViewById(R.id.CPU_usage);
                tv.setText(val);
                break;
            case "Memory_usage":
                tv = (TextView)findViewById(R.id.Memory_usage);
                tv.setText(val);
                break;
            default:
                Log.i("MainActivity","unknown property name : "+tb.replace(' ','_'));
        }
    }

    private void showInfo(String info)
    {
        Snackbar.make(fab, info, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();

    }


}
