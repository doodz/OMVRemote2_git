package com.dev.doods.omvremote2;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Adapters.CommandAdapter;
import Adapters.KeyValueAdapter;
import Classes.FakeJSONRPCParamsBuilder;
import Classes.KeyValueObject;
import Client.AsyncCall;
import Client.Call;
import Client.Callback;
import Client.JSONRPCClient;
import Client.JSONRPCParamsBuilder;
import Client.Response;
import DAL.CommandDAO;
import Interfaces.OnEditTextChanged;
import Models.Errors;
import Models.bd.Command;
import OMV.Base.AppCompatBaseActivity;

public class AddCommandActivity extends AppCompatBaseActivity {


    private EditText CommandNameView;
    private EditText CommandServiceNameView;
    private EditText CommandMethodNameView;

    private RecyclerView mRecyclerParamsView;
    private KeyValueAdapter mAdapterParams;
    private RecyclerView mRecyclerOptionsView;
    private KeyValueAdapter mAdapterOptions;
    private List<KeyValueObject> LstParams = new ArrayList<>();
    private  List<KeyValueObject> LstOptions = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_command);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        com.github.clans.fab.FloatingActionButton fabSave = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.fabTest);
        fabSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mHandler.post(new Runnable(){
                    public void run() {
                       test();
                    }
                });
            }
        });


        com.github.clans.fab.FloatingActionButton fabTest = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.fabSave);
        fabTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mHandler.post(new Runnable(){
                    public void run() {
                        save();
                    }
                });
            }
        });

        FloatingActionButton AddOptions = (FloatingActionButton) findViewById(R.id.AddOptions);
        AddOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mHandler.post(new Runnable(){
                    public void run() {

                        LstOptions.add(new KeyValueObject());
                        mAdapterOptions.notifyDataSetChanged();
                    }
                });

            }
        });
        FloatingActionButton AddParams = (FloatingActionButton) findViewById(R.id.AddParams);
        AddParams.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mHandler.post(new Runnable(){
                    public void run() {

                        LstParams.add(new KeyValueObject());
                        mAdapterParams.notifyDataSetChanged();
                    }
                });

            }
        });

        CommandNameView=  (EditText)findViewById(R.id.CommandName);
        CommandServiceNameView=  (EditText)findViewById(R.id.CommandServiceName);
        CommandMethodNameView=  (EditText)findViewById(R.id.CommandMethodName);

        mRecyclerParamsView = (RecyclerView)findViewById(R.id.RecyclerParams);

        OnEditTextChanged OnEditTextParamsChanged = new OnEditTextChanged() {
            @Override
            public void onTextChanged(int position, String charSeq, boolean iskey) {
                KeyValueObject val = LstParams.get(position);

                if(iskey)
                {
                    val.setKey(charSeq);
                }
                else
                {
                    val.setValue(charSeq);
                }

            }
        };
        mAdapterParams = new KeyValueAdapter(this,LstParams,OnEditTextParamsChanged);

        mRecyclerParamsView.setAdapter(mAdapterParams);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        mRecyclerParamsView.setLayoutManager(mLayoutManager);
        mRecyclerParamsView.setItemAnimator(new DefaultItemAnimator());


        mRecyclerOptionsView = (RecyclerView)findViewById(R.id.RecyclerOptions);

        OnEditTextChanged OnEditTextOptionChanged = new OnEditTextChanged() {
            @Override
            public void onTextChanged(int position, String charSeq, boolean iskey) {
                KeyValueObject val = LstOptions.get(position);

                if(iskey)
                {
                    val.setKey(charSeq);
                }
                else
                {
                    val.setValue(charSeq);
                }
            }
        };
        mAdapterOptions = new KeyValueAdapter(this,LstOptions,OnEditTextOptionChanged);

        mRecyclerOptionsView.setAdapter(mAdapterOptions);
        RecyclerView.LayoutManager mLayoutManager2 = new GridLayoutManager(this, 1);
        mRecyclerOptionsView.setLayoutManager(mLayoutManager2);
        mRecyclerOptionsView.setItemAnimator(new DefaultItemAnimator());
    }


    private Command  makeCommand()
    {
        Command cmd = new Command();
        cmd.setName(CommandNameView.getText().toString());

        params = new JSONRPCParamsBuilder();
        //set parameters
        params.setService(CommandServiceNameView.getText().toString());
        params.setMethod(CommandMethodNameView.getText().toString());

        for (KeyValueObject keyval:LstParams
                ) {

            String val = keyval.getValue();
            if (val.equalsIgnoreCase("true") || val.equalsIgnoreCase("false")) {

                params.addParam(keyval.getKey(), Boolean.valueOf(val));
            }
            else
                params.addParam(keyval.getKey(), val);

        }

        for (KeyValueObject keyval:LstOptions
                ) {

            String val = keyval.getValue();
            if (val.equalsIgnoreCase("true") || val.equalsIgnoreCase("false")) {

                params.addOption(keyval.getKey(), Boolean.valueOf(val));
            }
            else if(isInteger(val))
            {
                params.addOption(keyval.getKey(), Integer.parseInt(val));
            }
            else
                params.addOption(keyval.getKey(), val);

        }
        cmd.setCommand( params.toString());
        return cmd;
    }

    private JSONRPCParamsBuilder params;
    private void save()
    {

        Command cmd =makeCommand();
        saveInDb(cmd);

    }

    private void test()
    {

        Command cmd =makeCommand();
        test(cmd);

    }
    private void saveInDb(Command cmd)
    {

        CommandDAO datasource = new CommandDAO(this);
        datasource.open();
        datasource.createCommand(cmd);
        datasource.close();
        finish();
    }

    private void test(Command cmd)
    {

        Callback callBack = new Callback() {
            @Override
            public void onFailure(Call call,final Exception e) {

                mHandler.post(new Runnable(){
                    public void run() {

                        complain(e.getMessage());
                    }
                });

            }

            @Override
            public void OnOMVServeurError(Call call,final Errors error) {
                mHandler.post(new Runnable(){
                    public void run() {

                        complain(error.getMessage());
                    }
                });

            }

            @Override
            public void onResponse(Call call,final Response response) throws IOException, InterruptedException {
                mHandler.post(new Runnable(){
                    public void run() {
                        alert(response.GetJsonResult().toString());
                    }
                });

            }
        };


        FakeJSONRPCParamsBuilder fakeParamsBuilder = new FakeJSONRPCParamsBuilder();
        fakeParamsBuilder.params = cmd.getCommand();

        AsyncCall call = new AsyncCall(callBack,fakeParamsBuilder,this,JSONRPCClient.getInstance());

        JSONRPCClient.getInstance().Dispatcher().enqueue(call);

    }

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        } catch(NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }
}
