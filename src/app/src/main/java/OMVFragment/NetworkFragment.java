package OMVFragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.dev.doods.omvremote2.R;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;

import Client.Call;
import Client.CallbackImpl;
import Client.Response;
import Controllers.SystemController;
import Interfaces.OnFragmentInteractionListener;
import Models.SettingsNetwork;
import OMV.Base.FragmentBase;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the

 * to handle interaction events.
 * Use the {@link NetworkFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NetworkFragment extends FragmentBase {

    private SettingsNetwork mSettingsNetwork;
    private SystemController controller;
    Handler handler;

    private EditText _mHostNameView;
    private EditText _mDomainNameView;
    private FloatingActionButton _mSaveView;
    private OnFragmentInteractionListener mListener;

    public NetworkFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NetworkFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NetworkFragment newInstance(String param1, String param2) {
        NetworkFragment fragment = new NetworkFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView =inflater.inflate(R.layout.fragment_network, container, false);

        _mHostNameView = (EditText) rootView.findViewById(R.id.etHostName);
        _mDomainNameView =(EditText) rootView.findViewById(R.id.etDomainName);
        _mSaveView= (FloatingActionButton) rootView.findViewById(R.id.fab_Save);
        _mSaveView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               if(!IsFinalized(true))return;

                mSettingsNetwork.setHostname(_mHostNameView.getText().toString());
                mSettingsNetwork.setDomainname(_mDomainNameView.getText().toString());

                controller.setGeneralSettings(mSettingsNetwork,new CallbackImpl(NetworkFragment.this));
            }
        });


        handler= new Handler();
        controller = new SystemController(getActivity());

        controller.getGeneralSettingsNetwork(new CallbackImpl(NetworkFragment.this) {


            @Override
            public void onResponse(Call call, Response response) throws IOException, InterruptedException {
                super.onResponse(call,response);
                mSettingsNetwork = response.GetResultObject( new TypeToken<SettingsNetwork>(){});
                handler.post(new Runnable(){
                    public void run() {
                        _mHostNameView.setText(mSettingsNetwork.getHostname());
                        _mDomainNameView.setText(mSettingsNetwork.getDomainname());

                    }
                });
            }
        });

        // Inflate the layout for this fragment
        return rootView;
    }

    private String LastErrors;
    private Boolean CanSave()
    {
        return (mSettingsNetwork != null);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}
