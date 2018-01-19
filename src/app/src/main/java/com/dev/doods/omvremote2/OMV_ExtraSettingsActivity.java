package com.dev.doods.omvremote2;

import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.gson.reflect.TypeToken;
import com.innahema.collections.query.functions.Predicate;
import com.innahema.collections.query.queriables.Queryable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Client.Call;
import Client.Callback;
import Client.Response;
import Controllers.omvExtrasController;
import Models.CurentKernel;
import Models.Errors;
import Models.KernelItem;
import Models.Result;
import OMVFragment.Dialogs.OutputDialogFragment;

public class OMV_ExtraSettingsActivity extends AppCompatActivity {


    private TextView mKernelVersionView ;
    private TextView mKernelArchitectureView ;
    private TextView mKernelDeveloperView ;
    private TextView mKernelDistributionView ;
    private TextView mKernelNameView ;
    private Spinner mSpinnerKernelsView ;
    private Button mSaveDefautKernel;

    FloatingActionMenu mFloatingActionMenu;
    FloatingActionButton fab_Backports_kernel;
    FloatingActionButton fab_Headers_kernel;

    private omvExtrasController mController;
    //private OutputController mOutputController = new OutputController(OMV_ExtraSettingsActivity.this);
    private CurentKernel mCurentKernel;
    private List<KernelItem> mLstKernel;
    private Handler mHandler;
    private ArrayAdapter<KernelItem> mAdapterKernel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_omv_extra_settings);


        mController = new omvExtrasController(this);
        mHandler= new Handler();
        mLstKernel = new ArrayList<KernelItem>();


        mKernelVersionView = (TextView)findViewById(R.id.KernelVersion);
        mKernelArchitectureView = (TextView)findViewById(R.id.KernelArchitecture);
        mKernelDeveloperView = (TextView)findViewById(R.id.KernelDeveloper);
        mKernelDistributionView = (TextView)findViewById(R.id.KernelDistribution);
        mKernelNameView = (TextView)findViewById(R.id.KernelName);
        mSpinnerKernelsView = (Spinner) findViewById(R.id.spinnerKernels);


        mAdapterKernel = new ArrayAdapter<KernelItem>(OMV_ExtraSettingsActivity.this,
                android.R.layout.simple_spinner_item, mLstKernel);


        mSpinnerKernelsView.setAdapter(mAdapterKernel);
        getCurrentkernel();
        mController.getKernelList(new Callback() {
            @Override
            public void onFailure(Call call, Exception e) {

            }

            @Override
            public void OnOMVServeurError(Call call, Errors error) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException, InterruptedException {
                final Result<KernelItem> res = response.GetResultObject(new TypeToken<Result<KernelItem>>(){});
                mHandler.post(new Runnable(){
                    public void run() {
                        populateSpinerKernel(res.getData());
                        showSelectedKernel();
                    }
                });

            }
        });

        fab_Backports_kernel = (FloatingActionButton)findViewById(R.id.fab_Backports_kernel);

        fab_Backports_kernel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mFloatingActionMenu.close(true);
                //mFloatingActionMenu.hideMenu(true);

                mController.installbackports(new Callback() {
                    @Override
                    public void onFailure(Call call, Exception e) {

                    }

                    @Override
                    public void OnOMVServeurError(Call call, Errors error) {
                        Snackbar.make(mSaveDefautKernel, error.getMessage(), Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException, InterruptedException {
                        final String filename =  response.GetResultObject(new TypeToken<String>(){});
                        mHandler.post(new Runnable(){
                            public void run() {
                                //mOutputController.getOutput(filename);
                                CreateDialog(false,"Install backports",filename);
                            }
                        });
                    }
                });

            }
        });

        fab_Headers_kernel = (FloatingActionButton)findViewById(R.id.fab_Headers_kernel);;
        fab_Headers_kernel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFloatingActionMenu.close(true);
                mController.installheaders(new Callback() {
                    @Override
                    public void onFailure(Call call, Exception e) {

                    }

                    @Override
                    public void OnOMVServeurError(Call call, Errors error) {
                        Snackbar.make(mSaveDefautKernel, error.getMessage(), Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException, InterruptedException {
                        final String filename =  response.GetResultObject(new TypeToken<String>(){});
                        mHandler.post(new Runnable(){
                            public void run() {
                                CreateDialog(false,"Install Headers",filename);
                                //mOutputController.getOutput(filename);
                            }
                        });
                    }
                });
            }
        });

        mSaveDefautKernel = (Button) findViewById(R.id.BtnSaveDefautKernel);

        mSaveDefautKernel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final KernelItem mkernel= (KernelItem)mSpinnerKernelsView.getSelectedItem();
                mController.setBootKernel(mkernel.getKey(), new Callback() {
                    @Override
                    public void onFailure(Call call, Exception e) {

                    }

                    @Override
                    public void OnOMVServeurError(Call call, Errors error) {
                        Snackbar.make(mSaveDefautKernel, error.getMessage(), Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException, InterruptedException {


                        Snackbar.make(mSaveDefautKernel, String.format("%s As default kernel ",mkernel.getName() ), Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();


                        getCurrentkernel();

                    }
                });
            }
        });
        mFloatingActionMenu =(FloatingActionMenu) findViewById(R.id.menu);
    }


    private OutputDialogFragment mOutputDialogFragment;
    private void CreateDialog(Boolean justWaitCursor,String title,String fimeName)
    {

        Bundle bundle = new Bundle();
        bundle.putBoolean("justWaitCursor", justWaitCursor);
        bundle.putString("title", title);
        bundle.putString("fileName", fimeName);


        // Create an instance of the dialog fragment and show it
        mOutputDialogFragment = new OutputDialogFragment();
        //mOutputDialogFragment.AddListener(this);
        //mOutputDialogFragment.setListener(this);
        mOutputDialogFragment.setArguments(bundle);
        android.app.FragmentManager fm = getFragmentManager();
        mOutputDialogFragment.show(fm, "OutputDialogFragment");
    }

    private void getCurrentkernel()
    {
        mController.getKernel(new Callback() {
            @Override
            public void onFailure(Call call, Exception e) {

            }

            @Override
            public void OnOMVServeurError(Call call, Errors error) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException, InterruptedException {

                mCurentKernel  = response.GetResultObject(new TypeToken<CurentKernel>(){});
                mHandler.post(new Runnable(){
                    public void run() {
                        PopulateTexteViews();
                        showSelectedKernel();
                    }
                });
            }
        });
    }

    private void PopulateTexteViews()
    {
        mKernelVersionView.setText(mCurentKernel.getVersion());
        mKernelArchitectureView.setText(mCurentKernel.getArch());
        mKernelDeveloperView.setText(mCurentKernel.getDeveloper());
        mKernelDistributionView.setText(mCurentKernel.getVersionname());
        mKernelNameView.setText(mCurentKernel.getKernel());

    }

    private void populateSpinerKernel( List<KernelItem> lst)
    {
        mLstKernel.clear();
        mLstKernel.addAll(lst);
        mAdapterKernel.notifyDataSetChanged();

    }


    private void showSelectedKernel()
    {

        if(mCurentKernel != null ) {
            KernelItem res = Queryable.from(mLstKernel).firstOrDefault(new Predicate<KernelItem>() {
                @Override
                public boolean apply(KernelItem element) {
                    return element.getKey() == mCurentKernel.getKernels();
                }
            });
            mSpinnerKernelsView.setSelection(mLstKernel.indexOf(res));
        }
    }
}
