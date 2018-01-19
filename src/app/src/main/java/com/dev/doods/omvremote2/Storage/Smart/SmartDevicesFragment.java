package com.dev.doods.omvremote2.Storage.Smart;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import OMV.Classe.RecyclerItemClickListener;

/**
 * Created by Ividata7 on 10/05/2017.
 */

public class SmartDevicesFragment extends FragmentInteractionBase {


    private SmartController mController;
    private RecyclerView recyclerView;
    private List<SmartDevices> mSmartDevices= new ArrayList<SmartDevices>();
    private SmartDeviceAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mController = new SmartController(getActivity());


        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_jail, container, false);
        FindProgressBarr(rootView);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mAdapter = new SmartDeviceAdapter(getContext(), mSmartDevices);
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

        mController.getListDevices(new CallbackImpl(SmartDevicesFragment.this){

            @Override
            public void onResponse(Call call, Response response) throws IOException, InterruptedException {
                super.onResponse(call,response);

                final Result<SmartDevices> res = response.GetResultObject(new TypeToken<Result<SmartDevices>>(){});

                mHandler.post(new Runnable(){
                    public void run() {
                        populateViews(res.getData());
                    }
                });

            }
        });
    }


    private void populateViews(List<SmartDevices> smartDevices)
    {
        mSmartDevices.clear();
        mSmartDevices.addAll(smartDevices);
        mAdapter.notifyDataSetChanged();
    }
}
