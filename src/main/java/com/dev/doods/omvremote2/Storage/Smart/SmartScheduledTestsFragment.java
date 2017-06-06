package com.dev.doods.omvremote2.Storage.Smart;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dev.doods.omvremote2.Plugins.Fail2ban.AddEditeJailActivity;
import com.dev.doods.omvremote2.Plugins.Fail2ban.Jail;
import com.dev.doods.omvremote2.R;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Client.Call;
import Client.CallbackImpl;
import Client.Response;
import Models.Result;
import OMV.Base.FragmentInteractionBase;
import utils.CheckDirty;

/**
 * Created by Ividata7 on 10/05/2017.
 */

public class SmartScheduledTestsFragment extends FragmentInteractionBase {

    private SmartController mController;
    private RecyclerView recyclerView;
    private List<SmartScheduledTest> mSmartScheduledTest = new ArrayList<SmartScheduledTest>();
    private SmartScheduledTestsAdapter mAdapter;

    public SmartScheduledTestsFragment() {
        HaveUpdateMethode = true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mController = new SmartController(getActivity());


        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_jail, container, false);
        FindProgressBarr(rootView);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mAdapter = new SmartScheduledTestsAdapter(getContext(), mSmartScheduledTest,mController);
        recyclerView.setAdapter(mAdapter);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(),this));

        mContent = rootView;
        return rootView;
    }

        @CallSuper
    public void onResume() {
        super.onResume();

            new CheckDirty(getActivity()).Check();
        mController.getScheduleList(new CallbackImpl(this){
            @Override
            public void onResponse(Call call, Response response) throws IOException,InterruptedException {
                super.onResponse(call,response);
                final Result<SmartScheduledTest> res = response.GetResultObject(new TypeToken<Result<SmartScheduledTest>>(){});

                mHandler.post(new Runnable(){
                    public void run() {
                        populateViews(res.getData());
                    }
                });
            }
        });
    }

    @Override
    public int GetIconActionId()
    {
        return R.drawable.ic_add_box_black_24dp;
    }

    private void populateViews(List<SmartScheduledTest> smartScheduledTest)
    {
        mSmartScheduledTest.clear();
        mSmartScheduledTest.addAll(smartScheduledTest);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void Update() {
        Add();
    }


    private void Add()
    {
        if(IsFinalized(true))
            startActivity(new Intent(getContext(), AddEditeScheduledTestActivity.class));
    }
}
