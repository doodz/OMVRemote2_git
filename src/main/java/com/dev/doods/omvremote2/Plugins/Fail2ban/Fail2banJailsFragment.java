package com.dev.doods.omvremote2.Plugins.Fail2ban;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.dev.doods.omvremote2.AddEditeCronActivity;
import com.dev.doods.omvremote2.CronActivity;
import com.dev.doods.omvremote2.HomeActivity;
import com.dev.doods.omvremote2.R;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Adapters.SwithNameDescAdapter;
import Client.Call;
import Client.CallbackImpl;
import Client.Response;
import Interfaces.INameDescObject;
import Models.Result;
import OMV.Base.FragmentInteractionBase;
import OMV.Base.NameDescObjectBase;
import OMV.Classe.RecyclerItemClickListener;
import utils.CheckDirty;

/**
 * Created by Ividata7 on 26/04/2017.
 */

public class Fail2banJailsFragment extends FragmentInteractionBase implements AdapterView.OnItemClickListener, RecyclerItemClickListener.OnItemClickListener {

    private Fail2banController mController;
    private RecyclerView recyclerView;
    private List<Jail> mJails = new ArrayList<Jail>();
    private SwithNameDescAdapter mAdapter;
    public Fail2banJailsFragment() {
        HaveUpdateMethode = true;
    }

    @Override
    public int GetIconActionId()
    {
        return R.drawable.ic_add_box_black_24dp;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mController = new Fail2banController(getActivity());


        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_jail, container, false);
        FindProgressBarr(rootView);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mAdapter = new SwithNameDescAdapter(getContext(), mJails,this,mController);
        recyclerView.setAdapter(mAdapter);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(),this));

        mContent = rootView;
        return rootView;
    }

    @CallSuper
    public void onResume() {
        super.onResume();
        new CheckDirty(getActivity()).Check();
        mController.getJailList(new CallbackImpl(Fail2banJailsFragment.this){

            @Override
            public void onResponse(Call call, Response response) throws IOException, InterruptedException {
                super.onResponse(call,response);

                final Result<Jail> res = response.GetResultObject(new TypeToken<Result<Jail>>(){});

                mHandler.post(new Runnable(){
                    public void run() {
                        populateViews(res.getData());
                    }
                });

            }
        });
    }


    private void populateViews(List<Jail> jails)
    {
        mJails.clear();
        mJails.addAll(jails);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void Update() {
        Add();
    }


    private void Add()
    {
        if(IsFinalized(true))
            startActivity(new Intent(getContext(), AddEditeJailActivity.class));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onItemClick(View childView, int position) {

    }

    @Override
    public void onItemLongPress(View childView, int position) {

    }
}
