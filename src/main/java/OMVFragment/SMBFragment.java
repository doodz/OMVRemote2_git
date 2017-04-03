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
import android.widget.Spinner;
import android.widget.Switch;

import com.dev.doods.omvremote2.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;

import Client.Call;
import Client.Callback;
import Client.Response;
import Controllers.ServicesController;
import Deserializers.SharesDeserializer;
import Interfaces.IUpdateFragment;
import Interfaces.OnFragmentInteractionListener;
import Models.Errors;
import Models.SMBSettings;
import Models.Shares;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SMBFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SMBFragment extends Fragment implements IUpdateFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ServicesController mController ;
    Handler handler;
    private SMBSettings mSMBSettings;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public SMBFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SMBFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SMBFragment newInstance(String param1, String param2) {
        SMBFragment fragment = new SMBFragment();
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

    private Switch mSwithLocalBrowser;
    private Switch mSwithTimeServer;
    private Switch mSwithUserHome;
    private Switch mSwithBrowsable;
    private Switch mSwithWinsSupport;

    private Switch mwithNULLPasswords;
    private Switch mSwithUseSendfile;
    private Switch mSwithAsyncIO;

    private EditText mWorkgroup;
    private EditText mDescription;
    private EditText mWINSServer;
    private EditText mOptionView;

    private Spinner mLogsLevel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_smb, container, false);
        handler= new Handler();
        mSwithEnable = (Switch) rootView.findViewById(R.id.swithEnable);
        mSwithEnable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SetEnableView(isChecked);
            }
        });
        mWorkgroup = (EditText) rootView.findViewById(R.id.etWorkgroup);
        mDescription = (EditText) rootView.findViewById(R.id.etDescription);

        mSwithLocalBrowser = (Switch) rootView.findViewById(R.id.swithLocalBrowser);
        mSwithTimeServer = (Switch) rootView.findViewById(R.id.swithTimeServer);
        mSwithUserHome = (Switch) rootView.findViewById(R.id.swithUserHome);
        mSwithBrowsable = (Switch) rootView.findViewById(R.id.swithBrowsable);
        mSwithWinsSupport = (Switch) rootView.findViewById(R.id.swithWinsSupport);

        mWINSServer= (EditText) rootView.findViewById(R.id.etWINSServer);
        mLogsLevel = (Spinner) rootView.findViewById(R.id.spinnerLogs);

        mwithNULLPasswords = (Switch) rootView.findViewById(R.id.swithNULLPasswords);
        mSwithUseSendfile = (Switch) rootView.findViewById(R.id.swithUseSendfile);
        mSwithAsyncIO = (Switch) rootView.findViewById(R.id.swithAsyncIO);

        mOptionView = (EditText) rootView.findViewById(R.id.etOption);

        mController.getService("SMB", new Callback() {
            @Override
            public void onFailure(Call call, Exception e) {

            }

            @Override
            public void OnOMVServeurError(Call call, Errors error) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException, InterruptedException {

                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.registerTypeAdapter(Shares.class, new SharesDeserializer());
                //gsonBuilder.registerTypeAdapter(SystemInformation.class, new SystemInformationDeserializer());
                Gson gson = gsonBuilder.create();
                JsonElement j = response.GetJsonResult();

                TypeToken tt = new TypeToken<SMBSettings>(){};
                Type t =  tt.getType();

                mSMBSettings = gson.fromJson(j,t);
                //mSMBSettings = response.GetResultObject(new TypeToken<SMBSettings>(){});

                handler.post(new Runnable(){
                    public void run() {
                        mSwithEnable.setChecked(mSMBSettings.getEnable());
                        mWorkgroup.setText(mSMBSettings.getWorkgroup());
                        mDescription.setText(mSMBSettings.getServerstring());
                        mSwithLocalBrowser.setChecked(mSMBSettings.getLocalmaster());
                        mSwithTimeServer.setChecked(mSMBSettings.getTimeserver());
                        mSwithUserHome.setChecked(mSMBSettings.getHomesenable());
                        mSwithBrowsable.setChecked(mSMBSettings.getHomesbrowseable());
                        mSwithWinsSupport.setChecked(mSMBSettings.getWinssupport());
                        mWINSServer.setText(mSMBSettings.getWinsserver());
                        Integer levelLog = mSMBSettings.getLoglevel();
                        //TODO make list;

                        mwithNULLPasswords.setChecked(mSMBSettings.getNullpasswords());
                        mSwithUseSendfile.setChecked(mSMBSettings.getUsesendfile());
                        mSwithAsyncIO.setChecked(mSMBSettings.getAio());
                        mOptionView.setText(mSMBSettings.getExtraoptions());
                        SetEnableView(mSMBSettings.getEnable());
                    }
                });

            }
        });

        return  rootView;
    }

    private void SetEnableView(boolean b) {
        mWorkgroup.setEnabled(b);
        mDescription.setEnabled(b);
        mSwithLocalBrowser.setEnabled(b);
        mSwithTimeServer.setEnabled(b);
        mSwithUserHome.setEnabled(b);
        mSwithBrowsable.setEnabled(b);
        mSwithWinsSupport.setEnabled(b);
        mWINSServer.setEnabled(b);
        mwithNULLPasswords.setEnabled(b);
        mSwithUseSendfile.setEnabled(b);
        mSwithAsyncIO.setEnabled(b);
        mOptionView.setEnabled(b);
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


        mSMBSettings.setEnable(mSwithEnable.isChecked());
        mSMBSettings.setWorkgroup(mWorkgroup.getText().toString());
        mSMBSettings.setServerstring(mDescription.getText().toString());
        mSMBSettings.setLocalmaster(mSwithLocalBrowser.isChecked());
        mSMBSettings.setTimeserver(mSwithTimeServer.isChecked());
        mSMBSettings.setHomesenable(mSwithUserHome.isChecked());
        mSMBSettings.setHomesbrowseable(mSwithBrowsable.isChecked());
        mSMBSettings.setWinssupport(mSwithWinsSupport.isChecked());
        mSMBSettings.setWinsserver(mWINSServer.getText().toString());
        mSMBSettings.setNullpasswords(mwithNULLPasswords.isChecked());
        mSMBSettings.setUsesendfile(mSwithUseSendfile.isChecked());
        mSMBSettings.setAio(mSwithAsyncIO.isChecked());
        mSMBSettings.setExtraoptions(mOptionView.getText().toString());

        mController.setService(mSMBSettings, new Callback() {
            @Override
            public void onFailure(Call call, Exception e) {

            }

            @Override
            public void OnOMVServeurError(Call call, Errors error) {
                OnError(error.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException, InterruptedException {

                OnMessage(mSMBSettings.getServiceName()+": config saved");
            }
        });
    }

    @Override
    public String getServiceName() {
        return mSMBSettings.getServiceName();
    }
}
