package OMVFragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.CallSuper;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.dev.doods.omvremote2.R;
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
import OMV.Base.FragmentInteractionBase;
import OMVFragment.Dialogs.OutputDialogFragment;

/**
 * Created by thiba on 16/12/2016.
 */

public class OMVExtraKernelV3Fragment extends FragmentInteractionBase {


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

    //private OutputController mOutputController = new OutputController(getActivity());
    private CurentKernel mCurentKernel;
    private List<KernelItem> mLstKernel;
    private Handler mHandler;
    private ArrayAdapter<KernelItem> mAdapterKernel;

    private Handler handler;

    private omvExtrasController mController ;

    public OMVExtraKernelV3Fragment() {
        // Required empty public constructor
        // HaveUpdateMethode = true;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_omvextra_kernel_v3, container, false);

        mController = new omvExtrasController(getActivity());
        mHandler= new Handler();
        mLstKernel = new ArrayList<KernelItem>();


        mKernelVersionView = (TextView)rootView.findViewById(R.id.KernelVersion);
        mKernelArchitectureView = (TextView)rootView.findViewById(R.id.KernelArchitecture);
        mKernelDeveloperView = (TextView)rootView.findViewById(R.id.KernelDeveloper);
        mKernelDistributionView = (TextView)rootView.findViewById(R.id.KernelDistribution);
        mKernelNameView = (TextView)rootView.findViewById(R.id.KernelName);
        mSpinnerKernelsView = (Spinner) rootView.findViewById(R.id.spinnerKernels);


        mAdapterKernel = new ArrayAdapter<KernelItem>(getActivity(),
                android.R.layout.simple_spinner_item, mLstKernel);


        mSpinnerKernelsView.setAdapter(mAdapterKernel);

        fab_Backports_kernel = (FloatingActionButton)rootView.findViewById(R.id.fab_Backports_kernel);

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


        mFloatingActionMenu =(FloatingActionMenu) rootView.findViewById(R.id.menu);
        fab_Headers_kernel = (FloatingActionButton)rootView.findViewById(R.id.fab_Headers_kernel);;
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
                                //mOutputController.getOutput(filename);
                                CreateDialog(false,"Install headers",filename);
                            }
                        });
                    }
                });
            }
        });
        return rootView;
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
        android.app.FragmentManager fm = getActivity().getFragmentManager();
        mOutputDialogFragment.show(fm, "OutputDialogFragment");
    }

    @CallSuper
    public void onResume() {
        super.onResume();

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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mController = new omvExtrasController(getActivity());
    }

    @Override
    public void Update()
    {}
}
