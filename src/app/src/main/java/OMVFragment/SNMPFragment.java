package OMVFragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;

import com.dev.doods.omvremote2.R;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;

import Client.Call;
import Client.Callback;
import Client.Response;
import Controllers.ServicesController;
import Interfaces.IUpdateFragment;
import Interfaces.OnFragmentInteractionListener;
import Models.Errors;
import Models.SNMPSettings;
import utils.InputFilterMinMax;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SNMPFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SNMPFragment extends Fragment implements IUpdateFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ServicesController mController ;
    Handler handler;
    private SNMPSettings mSNMPSettings;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;



    public SNMPFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SNMPFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SNMPFragment newInstance(String param1, String param2) {
        SNMPFragment fragment = new SNMPFragment();
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
    private EditText mLocation;
    private EditText mContact;
    private EditText mCommuityView;
    private Spinner mVersion;

    private Switch mEnableTraps;
    private EditText mTrapsPort;
    private EditText mTrapsCommunity;
    private EditText mTrapsHost;
    private EditText mOptionView;



    private EditText mUsername;
    private Spinner mSpinerSecurityLevel;
    private Spinner mAuthType;
    private EditText mAuthPassPhrase;
    private Spinner mPrivType;
    private EditText mPrivPassPhrase;

    private LinearLayout LayoutV3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_snmp, container, false);
        handler= new Handler();
        mVersion= (Spinner) rootView.findViewById(R.id.spinnerVersion);
        mSwithEnable = (Switch) rootView.findViewById(R.id.swithEnable);
        mSwithEnable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SetEnableView(isChecked);
            }
        });
        mLocation = (EditText) rootView.findViewById(R.id.etLocation);
        mContact =  (EditText) rootView.findViewById(R.id.etContact);



        mAuthType = (Spinner) rootView.findViewById(R.id.spinnerAnthentificationType);
        ArrayAdapter<String> mAuthTypeAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, SNMPSettings.AuthType );
        mAuthType.setAdapter(mAuthTypeAdapter);

        mAuthPassPhrase = (EditText) rootView.findViewById(R.id.etAuthPassphrase);
        mPrivType = (Spinner) rootView.findViewById(R.id.spinnerPrivacyType);
        ArrayAdapter<String> mPrivTypeAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, SNMPSettings.PrivType );
        mPrivType.setAdapter(mPrivTypeAdapter);

        mPrivPassPhrase = (EditText) rootView.findViewById(R.id.etPrivacyPassphrase);
        mUsername =  (EditText) rootView.findViewById(R.id.etUsername);
        mSpinerSecurityLevel = (Spinner)  rootView.findViewById(R.id.spinnerSecurityLevel);

        LayoutV3 = (LinearLayout)rootView.findViewById(R.id.LayoutV3);

        ArrayAdapter<String> mSecurityLevelAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, SNMPSettings.SecurityLevels );
        mSpinerSecurityLevel.setAdapter(mSecurityLevelAdapter);

        mSpinerSecurityLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                mAuthType.setVisibility(View.GONE);
                mAuthPassPhrase.setVisibility(View.GONE);
                mPrivType.setVisibility(View.GONE);
                mPrivPassPhrase.setVisibility(View.GONE);
                if(position >=1)
                {
                    mAuthType.setVisibility(View.VISIBLE);
                    mAuthPassPhrase.setVisibility(View.VISIBLE);
                }
                if(position >=2)
                {
                    mPrivType.setVisibility(View.VISIBLE);
                    mPrivPassPhrase.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ArrayAdapter<String> mVersionAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, SNMPSettings.Versions );
        mVersion.setAdapter(mVersionAdapter);
        mVersion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                boolean b = position != 1;

                if(b) {
                    LayoutV3.setVisibility(View.GONE);
                }
                else
                {
                    LayoutV3.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mCommuityView = (EditText)  rootView.findViewById(R.id.etCommunity);

        mOptionView = (EditText) rootView.findViewById(R.id.etOption);


        mEnableTraps = (Switch) rootView.findViewById(R.id.swithEnableTraps);
        mEnableTraps.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

               boolean b= mSwithEnable.isChecked()&& isChecked;

                SetEnableTrapsViews(b);

            }
        });

        mTrapsCommunity = (EditText) rootView.findViewById(R.id.etTrapsCommunity);
        mTrapsHost = (EditText) rootView.findViewById(R.id.etTrapsHost);
        mTrapsPort = (EditText) rootView.findViewById(R.id.etTrapsPort);
        mTrapsPort.setFilters(new InputFilter[]{ new InputFilterMinMax(1, 99999) });


        mController.getService("SNMP", new Callback() {
            @Override
            public void onFailure(Call call, Exception e) {

            }

            @Override
            public void OnOMVServeurError(Call call, Errors error) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException, InterruptedException {


                mSNMPSettings = response.GetResultObject(new TypeToken<SNMPSettings>(){});

                handler.post(new Runnable(){
                    public void run() {
                        mSwithEnable.setChecked(mSNMPSettings.getEnable());
                        mLocation.setText(mSNMPSettings.getSyslocation());
                        mContact.setText(mSNMPSettings.getSyscontact());
                        mCommuityView.setText(mSNMPSettings.getCommunity());

                        String vertion =mSNMPSettings.getVersion();

                        if(vertion.equals("3"))
                            mVersion.setSelection(1);

                        String authtype = mSNMPSettings.getAuthtype();
                        int i = SNMPSettings.AuthType.indexOf(authtype);
                        mAuthType.setSelection(i);

                        String privtype = mSNMPSettings.getPrivtype();
                        i = SNMPSettings.PrivType.indexOf(privtype);
                        mPrivType.setSelection(i);

                        // noauth auth priv
                        String securitylevel = mSNMPSettings.getSecuritylevel();
                        i = SNMPSettings.SecurityLevelsAbrev.indexOf(securitylevel);
                        mSpinerSecurityLevel.setSelection(i);

                        mUsername.setText(mSNMPSettings.getUsername());
                        mAuthPassPhrase.setText(mSNMPSettings.getAuthpassphrase());
                        mPrivPassPhrase.setText(mSNMPSettings.getPrivpassphrase());

                        mOptionView.setText(mSNMPSettings.getExtraoptions());

                        mEnableTraps.setChecked(mSNMPSettings.getTrapenable());
                        mTrapsHost.setText(mSNMPSettings.getTraphost());
                        mTrapsCommunity.setText(mSNMPSettings.getTrapcommunity());
                        mTrapsPort.setText( Integer.toString(mSNMPSettings.getTrapport()));


                        SetEnableView(mSNMPSettings.getEnable());
                        boolean b= mSwithEnable.isChecked()&& mEnableTraps.isChecked();
                        SetEnableTrapsViews(b);
                    }
                });
            }
        });


        return  rootView;
    }


    private void SetEnableTrapsViews(boolean b)
    {
        mTrapsHost.setEnabled(b);
        mTrapsPort.setEnabled(b);
        mTrapsCommunity.setEnabled(b);
    }

    private void SetEnableView(boolean b) {

        mLocation.setEnabled(b);
        mContact.setEnabled(b);
        mOptionView.setEnabled(b);
        mCommuityView.setEnabled(b);

        mVersion.setEnabled(b);
        mUsername.setEnabled(b);
        mSpinerSecurityLevel.setEnabled(b);
        mAuthType.setEnabled(b);
        mPrivType.setEnabled(b);

        mEnableTraps.setEnabled(b);


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

        mLocation.setError(null);
        mContact.setError(null);

        String str =mLocation.getText().toString();

        if( mSwithEnable.isChecked() && (str == null || str.equals("")) )
        {
            mLocation.setError(getString(R.string.error_field_required));
            return;
        }

        str =mContact.getText().toString();

        if( mSwithEnable.isChecked() && (str == null || str.equals("")) )
        {
            mContact.setError(getString(R.string.error_field_required));
            return;
        }

        str =mCommuityView.getText().toString();

        if( mSwithEnable.isChecked() && (str == null || str.equals("")) )
        {
            mCommuityView.setError(getString(R.string.error_field_required));
            return;
        }

        str =mTrapsHost.getText().toString();

        if( (mSwithEnable.isChecked() && mEnableTraps.isChecked()) && (str == null || str.equals("")) )
        {
            mTrapsHost.setError(getString(R.string.error_field_required));
            return;
        }
        str =mTrapsCommunity.getText().toString();

        if( (mSwithEnable.isChecked() && mEnableTraps.isChecked())&& (str == null || str.equals("")) )
        {
            mTrapsCommunity.setError(getString(R.string.error_field_required));
            return;
        }

        mSNMPSettings.setCommunity(mCommuityView.getText().toString());
        mSNMPSettings.setEnable(mSwithEnable.isChecked());
        mSNMPSettings.setSyslocation(mLocation.getText().toString());
        mSNMPSettings.setSyscontact(mContact.getText().toString());
        mSNMPSettings.setExtraoptions(mOptionView.getText().toString());
        mSNMPSettings.setTrapenable(mEnableTraps.isChecked());
        mSNMPSettings.setTraphost(mTrapsHost.getText().toString());
        mSNMPSettings.setTrapcommunity(mTrapsCommunity.getText().toString());
        mSNMPSettings.setTrapport(Integer.parseInt(mTrapsPort.getText().toString()));


        mSNMPSettings.setUsername(mUsername.getText().toString());
        mSNMPSettings.setAuthpassphrase(mAuthPassPhrase.getText().toString());
        mSNMPSettings.setPrivpassphrase(mPrivPassPhrase.getText().toString());


        int i = mVersion.getSelectedItemPosition();
        mSNMPSettings.setVersion(SNMPSettings.VersionsAbrev.get(i));

        i = mSpinerSecurityLevel.getSelectedItemPosition();
        mSNMPSettings.setSecuritylevel(SNMPSettings.SecurityLevelsAbrev.get(i));

        i= mAuthType.getSelectedItemPosition();
        mSNMPSettings.setAuthtype(SNMPSettings.AuthType.get(i));

        i= mPrivType.getSelectedItemPosition();
        mSNMPSettings.setPrivtype(SNMPSettings.PrivType.get(i));

        mController.setService(mSNMPSettings, new Callback() {
            @Override
            public void onFailure(Call call, Exception e) {

            }

            @Override
            public void OnOMVServeurError(Call call, Errors error) {
                OnError(error.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException, InterruptedException {
                OnMessage(mSNMPSettings.getServiceName()+": config saved");
            }
        });
    }


    //Version :
    //  2c = SNMP version 1/2c
    //

    @Override
    public String getServiceName() {
        return mSNMPSettings.getServiceName();
    }
}
