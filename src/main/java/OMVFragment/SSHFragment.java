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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import com.dev.doods.base.R;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;

import Client.Call;
import Client.Callback;
import Client.Response;
import Controllers.ServicesController;
import Interfaces.IUpdateFragment;
import Interfaces.OnFragmentInteractionListener;
import Models.Errors;
import Models.SSHSettings;
import utils.InputFilterMinMax;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SSHFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SSHFragment extends Fragment implements IUpdateFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ServicesController mController ;
    Handler handler;
    private SSHSettings mSSHSettings;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public SSHFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SSHFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SSHFragment newInstance(String param1, String param2) {
        SSHFragment fragment = new SSHFragment();
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
    private Switch mSwithRootView;
    private Switch mSwithPasswordView;
    private Switch mSwithPublicKeyView;
    private Switch mSwithTCPForwardingView;
    private Switch mSwithCompressView;
    private EditText mPortView;
    private EditText mOptionView;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_ssh, container, false);
        handler= new Handler();
        mSwithEnable = (Switch) rootView.findViewById(R.id.swithEnable);
        mSwithEnable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SetEnableView(isChecked);
            }
        });
        mPortView = (EditText) rootView.findViewById(R.id.etPort);
        mPortView.setFilters(new InputFilter[]{ new InputFilterMinMax(1, 99999) });


        mSwithRootView = (Switch) rootView.findViewById(R.id.swithRoot);
        mSwithPasswordView = (Switch) rootView.findViewById(R.id.swithPassword);
        mSwithPublicKeyView = (Switch) rootView.findViewById(R.id.swithPublicKey);
        mSwithTCPForwardingView = (Switch) rootView.findViewById(R.id.swithTCPForwarding);
        mSwithCompressView = (Switch) rootView.findViewById(R.id.swithCompress);

        mOptionView = (EditText) rootView.findViewById(R.id.etOption);

        mController.getService("SSH", new Callback() {
            @Override
            public void onFailure(Call call, Exception e) {

            }

            @Override
            public void OnOMVServeurError(Call call, Errors error) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException, InterruptedException {

                mSSHSettings = response.GetResultObject(new TypeToken<SSHSettings>(){});

                handler.post(new Runnable(){
                    public void run() {
                        mSwithEnable.setChecked(mSSHSettings.getEnable());
                        mPortView.setText(Integer.toString(mSSHSettings.getPort()));
                        mSwithRootView.setChecked(mSSHSettings.getPermitrootlogin());
                        mSwithPasswordView.setChecked(mSSHSettings.getPasswordauthentication());
                        mSwithPublicKeyView.setChecked(mSSHSettings.getPubkeyauthentication());
                        mSwithTCPForwardingView.setChecked(mSSHSettings.getTcpforwarding());
                        mSwithCompressView.setChecked(mSSHSettings.getCompression());
                        mOptionView.setText(mSSHSettings.getExtraoptions());
                        SetEnableView(mSSHSettings.getEnable());

                    }
                });


            }
        });

        return  rootView;

    }

    private void SetEnableView(boolean b)
    {
        mPortView.setEnabled(b);
        mSwithRootView.setEnabled(b);
        mSwithPasswordView.setEnabled(b);
        mSwithPublicKeyView.setEnabled(b);
        mSwithTCPForwardingView.setEnabled(b);
        mSwithCompressView.setEnabled(b);
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

        mSSHSettings.setEnable(mSwithEnable.isChecked());
        mSSHSettings.setPort(Integer.parseInt(mPortView.getText().toString()));
        mSSHSettings.setPermitrootlogin(mSwithRootView.isChecked());
        mSSHSettings.setPasswordauthentication(mSwithPasswordView.isChecked());
        mSSHSettings.setPubkeyauthentication(mSwithPublicKeyView.isChecked());
        mSSHSettings.setTcpforwarding(mSwithTCPForwardingView.isChecked());
        mSSHSettings.setCompression(mSwithCompressView.isChecked());
        mSSHSettings.setExtraoptions(mOptionView.getText().toString());
        mController.setService(mSSHSettings, new Callback() {
            @Override
            public void onFailure(Call call, Exception e) {

            }

            @Override
            public void OnOMVServeurError(Call call, Errors error) {
                OnError(error.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException, InterruptedException {

                OnMessage(mSSHSettings.getServiceName()+": config saved");

            }
        });
    }

    @Override
    public String getServiceName() {
        return mSSHSettings.getServiceName();
    }
}
