package com.dev.doods.omvremote2.Plugins.Fail2ban;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import com.dev.doods.omvremote2.R;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;

import Client.Call;
import Client.CallbackImpl;
import Client.Response;
import Controllers.ShareMgmtController;
import Models.SharedFolder;
import OMV.Base.FragmentInteractionBase;
import utils.CheckDirty;

/**
 * Created by Ividata7 on 26/04/2017.
 */

public class Fail2banSettingsFragment extends FragmentInteractionBase {

    private Fail2banController mController ;
    private Fail2banSettings mFail2banSettings;

    private Switch mswithEnable;
    private EditText mIgnoreip;
    private EditText mFindtime;
    private EditText mBantime;
    private EditText mMaxretry;
    private EditText mDestemail;
    private EditText mAction;

    public Fail2banSettingsFragment() {
        HaveUpdateMethode = true;
    }

    @Override
    public int GetIconActionId()
    {
        return R.drawable.ic_save_black_24dp;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mController = new Fail2banController(getActivity());
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.activity_fail2ban, container, false);

        FindProgressBarr(rootView);

        mswithEnable = (Switch)  rootView.findViewById(R.id.swithEnable);
        mIgnoreip = (EditText)  rootView.findViewById(R.id.Ignoreip);
        mFindtime = (EditText)  rootView.findViewById(R.id.Findtime);
        mBantime = (EditText)  rootView.findViewById(R.id.Bantime);
        mMaxretry = (EditText)  rootView.findViewById(R.id.Maxretry);
        mDestemail = (EditText)  rootView.findViewById(R.id.Destemail);
        mAction = (EditText)  rootView.findViewById(R.id.Action);
        mContent = rootView;
        return rootView;
    }

    @CallSuper
    public void onResume() {
        super.onResume();
        mController.getSettings(new CallbackImpl(Fail2banSettingsFragment.this){

            @Override
            public void onResponse(Call call, Response response) throws IOException, InterruptedException {
                super.onResponse(call,response);

                //final Result<Datum> res = response.GetResultObject(new TypeToken<Result<Datum>>(){});
                mFail2banSettings = response.GetResultObject(new TypeToken<Fail2banSettings>(){});
                mHandler.post(new Runnable(){
                    public void run() {
                        populateViews();
                    }
                });

            }
        });
    }

    private void populateViews()
    {
        if(mFail2banSettings == null) return;
        mswithEnable.setChecked(mFail2banSettings.getEnable());
        mIgnoreip.setText(mFail2banSettings.getIgnoreip());
        mFindtime.setText(mFail2banSettings.getFindtime());
        mBantime.setText(mFail2banSettings.getBantime());
        mMaxretry.setText(mFail2banSettings.getMaxretry());
        mDestemail.setText(mFail2banSettings.getDestemail());
        mAction.setText(mFail2banSettings.getAction());
    }

    @Override
    public void Update() {
      Save();
    }
    private void  Save()
    {
        if(mFail2banSettings == null) return;

        if(IsFinalized(true)) {
            mFail2banSettings.setEnable(mswithEnable.isChecked());
            mFail2banSettings.setIgnoreip(mIgnoreip.getText().toString());
            mFail2banSettings.setFindtime(mFindtime.getText().toString());
            mFail2banSettings.setBantime(mBantime.getText().toString());
            mFail2banSettings.setMaxretry(mMaxretry.getText().toString());
            mFail2banSettings.setDestemail(mDestemail.getText().toString());
            mFail2banSettings.setAction(mAction.getText().toString());


            mController.setSettings(mFail2banSettings, new CallbackImpl(this) {
                @Override
                public void onResponse(Call call, Response response) throws IOException, InterruptedException {
                    super.onResponse(call, response);
                    new CheckDirty(getActivity()).Check();
                }

            });
        }
    }
}
