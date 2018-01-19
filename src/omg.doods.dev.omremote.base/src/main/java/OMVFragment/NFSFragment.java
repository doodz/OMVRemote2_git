package OMVFragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;

import com.dev.doods.base.R;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Adapters.SharedFilderNFSAdapter;
import Client.Call;
import Client.Callback;
import Client.Response;
import Controllers.ServicesController;
import Interfaces.IUpdateFragment;
import Interfaces.OnFragmentInteractionListener;
import Models.Errors;
import Models.NFSSettings;
import Models.Result;
import Models.SharedFolderNFS;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NFSFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NFSFragment extends Fragment implements IUpdateFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ServicesController mController ;
    private NFSSettings mNFSSettings;
    private ListView mListView;
    private SharedFilderNFSAdapter mAdapter;

    List<SharedFolderNFS> mSharedFolderNFSes = new ArrayList<SharedFolderNFS>();

    Handler handler;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public NFSFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NFSFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NFSFragment newInstance(String param1, String param2) {
        NFSFragment fragment = new NFSFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    private Switch mSwithEnable;
    private EditText mNbServerView;
    private ListView mSharedFolderView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_nfs, container, false);
        handler= new Handler();

        mListView = (ListView) rootView.findViewById(R.id.ShareLst);
        mSwithEnable = (Switch) rootView.findViewById(R.id.swithEnable);
        mSwithEnable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SetEnableView(isChecked);
            }
        });
        mNbServerView = (EditText) rootView.findViewById(R.id.etNbServer);
        mSharedFolderView = (ListView) rootView.findViewById(R.id.ShareLst);

        mController.getService("NFS", new Callback() {
            @Override
            public void onFailure(Call call, Exception e) {

            }

            @Override
            public void OnOMVServeurError(Call call, Errors error) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException, InterruptedException {
                mNFSSettings = response.GetResultObject( new TypeToken<NFSSettings>(){});

                handler.post(new Runnable(){
                    public void run() {
                        mSwithEnable.setChecked(mNFSSettings.getEnable());
                        mNbServerView.setText(Integer.toString(mNFSSettings.getNumproc()));
                        SetEnableView(mNFSSettings.getEnable());

                    }
                });
            }
        });

        mAdapter = new SharedFilderNFSAdapter(getActivity().getApplicationContext(), mSharedFolderNFSes);
        mListView.setAdapter(mAdapter);

        mController.getShareList("NFS", new Callback() {
            @Override
            public void onFailure(Call call, Exception e) {

            }

            @Override
            public void OnOMVServeurError(Call call, Errors error) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException, InterruptedException {

                final Result<SharedFolderNFS> res = response.GetResultObject( new TypeToken<Result<SharedFolderNFS>>(){});
                handler.post(new Runnable(){
                    public void run() {
                        populateLstView(res.getData());
                    }
                });

            }
        });

        return  rootView;
    }


    private void populateLstView(List<SharedFolderNFS> lst)
    {
        mSharedFolderNFSes.clear();
        mSharedFolderNFSes.addAll(lst);
        mAdapter.notifyDataSetChanged();
    }

    private void SetEnableView(boolean b) {

        mNbServerView.setEnabled(b);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public void OnMessage(String msg)
    {
        if (mListener != null) {
            mListener.onFragmentMessage(msg);
        }
    }

    public void OnError(String msg)
    {
        if (mListener != null) {
            mListener.onFragmentMessageError(msg);
        }
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
        mController = new ServicesController(getActivity());
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void Update() {

        mNFSSettings.setEnable(mSwithEnable.isChecked());
        mNFSSettings.setNumproc(Integer.parseInt(mNbServerView.getText().toString()));
        mController.setService(mNFSSettings, new Callback() {
            @Override
            public void onFailure(Call call, Exception e) {

            }

            @Override
            public void OnOMVServeurError(Call call, Errors error) {
                OnError(error.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException, InterruptedException {

                OnMessage(mNFSSettings.getServiceName()+": config saved");
            }
        });
    }

    @Override
    public String getServiceName() {
        return mNFSSettings.getServiceName();
    }

}
