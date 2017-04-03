package com.dev.doods.omvremote2;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.innahema.collections.query.functions.Predicate;
import com.innahema.collections.query.queriables.Queryable;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import Adapters.VirtualBoxMachineRecyclerAdapter;
import Client.Call;
import Client.CallbackImpl;
import Client.Response;
import Controllers.ShareMgmtController;
import Controllers.VirtualBoxController;
import Deserializers.PrivilegesDeserializer;
import Interfaces.OutputListener;
import Models.Privileges;
import Models.Result;
import Models.SharedFolder;
import Models.VirtualBoxMachine;
import Models.VirtualBoxSettings;
import OMV.Classe.AppCompatBaseActivity;
import OMVFragment.Dialogs.OutputDialogFragment;
import utils.CheckDirty;

public class VirtualboxActivity extends AppCompatBaseActivity implements OutputListener {


    Switch swithEnable;
    Spinner spinnerSharedFolder;
    Button btnFixModulKernel;
    Switch swithAdvancedConfig;
    Switch swithShowTab;
    RecyclerView LstVirtualMachines;

    private VirtualBoxController mController = new VirtualBoxController(this);
    private ShareMgmtController mShareMgmtController = new ShareMgmtController(this);
    private OutputDialogFragment mOutputDialogFragment;
    Handler handler = new Handler();

    VirtualBoxMachineRecyclerAdapter mrecyclerViewAdapter;
    private List<VirtualBoxMachine> _lst = new ArrayList<VirtualBoxMachine>();
    private List<SharedFolder> lstSharedFolder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.dev.doods.base.R.layout.activity_virtualbox);
        Toolbar toolbar = (Toolbar) findViewById(com.dev.doods.base.R.id.toolbar);
        setSupportActionBar(toolbar);

        new CheckDirty(this).Check();

        FloatingActionButton fab = (FloatingActionButton) findViewById(com.dev.doods.base.R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(IsFinalized(true))
                saveSettings();
            }
        });
        bindViews();
        mController.getSettings(new CallbackImpl(this) {


            @Override
            public void onResponse(Call call, Response response) throws IOException, InterruptedException {
                super.onResponse(call,response);
               vbs= response.GetResultObject(new TypeToken<VirtualBoxSettings>(){});
                handler.post(new Runnable(){
                    public void run() {

                        //mOutputController.IsRunningFile(filename);
                        populateViews(vbs);
                    }
                });

            }
        });

        mShareMgmtController.enumerateSharedFolders(new CallbackImpl(this) {

            @Override
            public void onResponse(Call call, Response response) throws IOException, InterruptedException {
               //lstSharedFolder = response.GetResultObject(new TypeToken< List<SharedFolder>>(){});
                super.onResponse(call,response);

                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.registerTypeAdapter(Privileges.class, new PrivilegesDeserializer());
                //gsonBuilder.registerTypeAdapter(SystemInformation.class, new SystemInformationDeserializer());
                Gson gson = gsonBuilder.create();
                JsonElement j = response.GetJsonResult();

                TypeToken tt = new TypeToken<List<SharedFolder>>(){};
                Type t =  tt.getType();
                // ArrayList<LinkedTreeMap<String,String>> oo =Util.FromJson(j,Object.class);
                lstSharedFolder = gson.fromJson(j,t);

                handler.post(new Runnable(){
                    public void run() {
                        //mOutputController.IsRunningFile(filename);
                        updatespinnerSharedFolder();
                    }
                });

            }
        });

        mController.getMachines(new CallbackImpl(this) {

            @Override
            public void onResponse(Call call, Response response) throws IOException, InterruptedException {
                super.onResponse(call,response);
                final Result<VirtualBoxMachine> lst = response.GetResultObject(new TypeToken< Result<VirtualBoxMachine>>(){});
                handler.post(new Runnable(){
                    public void run() {
                        //mOutputController.IsRunningFile(filename);
                        updateVirtualBoxMachines(lst.getData());
                    }
                });
            }
        });
    }


    private void updateVirtualBoxMachines(List<VirtualBoxMachine> lst)
    {
        _lst.clear();
        _lst.addAll(lst);
        mrecyclerViewAdapter.notifyDataSetChanged();
    }

    private void updatespinnerSharedFolder()
    {

        if(lstSharedFolder != null)
        {
            ArrayAdapter mAdapterVolume = new ArrayAdapter<SharedFolder>(VirtualboxActivity.this,
                    android.R.layout.simple_spinner_item, lstSharedFolder);
            spinnerSharedFolder.setAdapter(mAdapterVolume);
        }
        if(vbs != null && lstSharedFolder != null)
        {
           final String uuid =  vbs.getMachinesSharedfolderref();
            SharedFolder sf = Queryable.from(lstSharedFolder).firstOrDefault(new Predicate<SharedFolder>() {
                @Override
                public boolean apply(SharedFolder element) {
                    return element.getUuid().equals(uuid);
                }
            });

            if(sf != null)
            {
                spinnerSharedFolder.setSelection(lstSharedFolder.indexOf(sf));
            }

        }
    }

    VirtualBoxSettings vbs;
    private void saveSettings()
    {
        if(vbs == null) return;

        vbs.setEnable(swithEnable.isChecked());
        vbs.setEnableAdvanced(swithAdvancedConfig.isChecked());
        vbs.setShowTab(swithShowTab.isChecked());

        int i = spinnerSharedFolder.getSelectedItemPosition();
        SharedFolder folder = lstSharedFolder.get(i);
        vbs.setMachinesSharedfolderref(folder.getUuid());

        if(mController.GetApiVersion() == 3)
        {
            mController.setSettingsV3(vbs, new CallbackImpl(this));
        }
        else
        mController.setSettings(vbs, new CallbackImpl(this));
    }

    private void populateViews(VirtualBoxSettings vbs)
    {
        swithEnable.setChecked(vbs.getEnable());
        swithAdvancedConfig.setChecked(vbs.getEnableAdvanced());

        // Erasmus
        if(vbs.getShowTab() == null)
        {
            swithShowTab.setVisibility(View.GONE);
            btnFixModulKernel.setVisibility(View.GONE);
        }
        else
            swithShowTab.setChecked(vbs.getShowTab());


        updatespinnerSharedFolder();
        spinnerSharedFolder.setEnabled(vbs.getEnable());
        btnFixModulKernel.setEnabled(vbs.getEnable());
        swithAdvancedConfig.setEnabled(vbs.getEnable());
        swithShowTab.setEnabled(vbs.getEnable());
        LstVirtualMachines.setEnabled(vbs.getEnable());
    }

    private void bindViews()
    {

        swithEnable=(Switch) findViewById(com.dev.doods.base.R.id.swithEnable);
        spinnerSharedFolder=(Spinner) findViewById(com.dev.doods.base.R.id.spinnerSharedFolder);
        btnFixModulKernel=(Button) findViewById(com.dev.doods.base.R.id.btnFixModulKernel);
        swithAdvancedConfig=(Switch) findViewById(com.dev.doods.base.R.id.swithAdvancedConfig);
        swithShowTab=(Switch) findViewById(com.dev.doods.base.R.id.swithShowTab);
        LstVirtualMachines=(RecyclerView) findViewById(com.dev.doods.base.R.id.LstVirtualMachines);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        LstVirtualMachines.setLayoutManager(mLayoutManager);
        LstVirtualMachines.setItemAnimator(new DefaultItemAnimator());
        mrecyclerViewAdapter = new VirtualBoxMachineRecyclerAdapter(this,_lst,mController);
        LstVirtualMachines.setAdapter(mrecyclerViewAdapter);

        btnFixModulKernel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(IsFinalized(true))
                mController.fixModule(new CallbackImpl(VirtualboxActivity.this) {
                    @Override
                    public void onResponse(Call call, Response response) throws IOException, InterruptedException {
                        super.onResponse(call,response);
                        final String filename =  response.GetResultObject(new TypeToken<String>(){});
                        handler.post(new Runnable(){
                            public void run() {
                                //mOutputController.IsRunningFile(filename);
                                CreateDialog(false,"Fix Mudule Kernel",filename);
                            }
                        });
                    }
                });
            }
        });
    }

    private void CreateDialog(Boolean justWaitCursor,String title,String fimeName)
    {

        Bundle bundle = new Bundle();
        bundle.putBoolean("justWaitCursor", justWaitCursor);
        bundle.putString("title", title);
        bundle.putString("fileName", fimeName);

        // Create an instance of the dialog fragment and show it
        mOutputDialogFragment = new OutputDialogFragment();
        mOutputDialogFragment.AddListener(this);
        //mOutputDialogFragment.setListener(this);
        mOutputDialogFragment.setArguments(bundle);
        android.app.FragmentManager fm = getFragmentManager();
        mOutputDialogFragment.show(fm, "OutputDialogFragment");
    }


    @Override
    public void OnFinich() {

    }

    @Override
    public void OnCanceled() {

    }

    @Override
    public void OnStarted() {

    }
}
