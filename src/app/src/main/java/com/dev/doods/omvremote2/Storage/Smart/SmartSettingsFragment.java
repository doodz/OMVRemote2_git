package com.dev.doods.omvremote2.Storage.Smart;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import com.dev.doods.omvremote2.HomeActivity;
import com.dev.doods.omvremote2.R;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;

import Client.Call;
import Client.CallbackImpl;
import Client.Response;
import OMV.Base.FragmentInteractionBase;
import utils.CheckDirty;
import utils.Util;

/**
 * Created by Ividata7 on 10/05/2017.
 */

public class SmartSettingsFragment extends FragmentInteractionBase {

    private SmartController mController;
    private SmartSettings mSettings;
    Switch swithEnable;
    EditText CheckInterval;
    private Spinner Powermode;
    private ArrayAdapter<String> mAdapter;
    EditText Difference;
    EditText Informal;
    EditText Critical;

    public SmartSettingsFragment() {
        HaveUpdateMethode = true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mController = new SmartController(getActivity());
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.content_smart, container, false);

        FindProgressBarr(rootView);

        swithEnable = (Switch) rootView.findViewById(R.id.swithEnable);
        CheckInterval = (EditText) rootView.findViewById(R.id.CheckInterval);
        Powermode = (Spinner) rootView.findViewById(R.id.Powermode);
        mAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, SmartController.PowerModeList);
        Powermode.setAdapter(mAdapter);

        Difference = (EditText) rootView.findViewById(R.id.Difference);
        Informal = (EditText) rootView.findViewById(R.id.Informal);
        Critical = (EditText) rootView.findViewById(R.id.Critical);
        mContent = rootView;
        return rootView;
    }

    @CallSuper
    public void onResume() {
        super.onResume();
        new CheckDirty(getActivity()).Check();
        mController.getSettings(new CallbackImpl(this){
            @Override
            public void onResponse(Call call, Response response) throws IOException,InterruptedException {
                super.onResponse(call,response);
                mSettings =  response.GetResultObject(new TypeToken<SmartSettings>(){});
                mHandler.post(new Runnable(){
                    public void run() {
                        PopulatesViews();
                    }
                });
            }
        });
    }

    private void PopulatesViews()
    {
        swithEnable.setChecked(mSettings.getEnable());

        Util.SetInteger(CheckInterval, mSettings.getInterval());
        int i =  SmartController.PowerModeList.indexOf(mSettings.getPowermode());
        Powermode.setSelection(i);

        Util.SetInteger(Difference, mSettings.getTempdiff());
        Util.SetInteger(Informal, mSettings.getTempinfo());
        Util.SetInteger(Critical, mSettings.getTempcrit());
    }

    @Override
    public int GetIconActionId()
    {
        return R.drawable.ic_save_black_24dp;
    }

    @Override
    public void Update() {
        Save();
    }

    private void Save()
    {

        mSettings.setEnable(swithEnable.isChecked());
        mSettings.setPowermode((String)Powermode.getSelectedItem());
        mSettings.setTempdiff(Util.GetInteger(Difference));
        mSettings.setTempinfo(Util.GetInteger(Informal));
        mSettings.setTempcrit(Util.GetInteger(Critical));
        mController.setSettings(mSettings,new CallbackImpl(this){
            @Override
            public void onResponse(Call call, Response response) throws IOException, InterruptedException {
            super.onResponse(call,response);
                new CheckDirty(getActivity()).Check();
            }
        });
    }
}
