package OMVFragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import com.dev.doods.omvremote2.R;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Client.Call;
import Client.CallbackImpl;
import Client.Response;
import Controllers.HomeController;
import Interfaces.OnFragmentInteractionListener;
import Models.Certificate;
import Models.Result;
import Models.WebSettings;
import OMV.Classe.FragmentBase;
import utils.InputFilterMinMax;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the

 * to handle interaction events.
 * Use the {@link WebAdministrationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WebAdministrationFragment extends FragmentBase {


    private Switch _SwithEnableSSL;
    private Switch _SwithForceSSL;
    private EditText _TextViewPort;
    private EditText _TextViewPortSSL;
    private EditText _TextViewTimeout;
    private Spinner _SpinnerSertificates;
    private FloatingActionButton _ButtonSave;
    private HomeController controller;
    Handler handler;
    List<Certificate> Certificates;
    ArrayAdapter<Certificate> adapter;
    private OnFragmentInteractionListener mListener;

    public WebAdministrationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WebAdministrationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WebAdministrationFragment newInstance(String param1, String param2) {
        WebAdministrationFragment fragment = new WebAdministrationFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView =  inflater.inflate(R.layout.fragment_web_administration, container, false);


                handler= new Handler();
        controller = new HomeController(getActivity());

        _SwithEnableSSL = (Switch) rootView.findViewById(R.id.swithEnableSSL);
        _SwithForceSSL = (Switch) rootView.findViewById(R.id.swithForceSSL);
        _TextViewPort = (EditText)rootView.findViewById(R.id.etPort);
        _TextViewPortSSL = (EditText)rootView.findViewById(R.id.etPortSSL);
        _TextViewTimeout = (EditText)rootView.findViewById(R.id.etTimeout);
        _SpinnerSertificates = (Spinner)rootView.findViewById(R.id.spinnerSertificates);
        _ButtonSave = (FloatingActionButton) rootView.findViewById(R.id.fab_Save);

        _TextViewPort.setFilters(new InputFilter[]{ new InputFilterMinMax(1, 99999) });
        _TextViewPortSSL.setFilters(new InputFilter[]{ new InputFilterMinMax(1, 99999) });
        GetSettings();
        // Inflate the layout for this fragment
        Certificates = new ArrayList<Certificate>() ;
       adapter = new ArrayAdapter<Certificate>(getActivity().getApplicationContext(),
                android.R.layout.simple_spinner_item, Certificates);

        //_SpinnerSertificates = (Spinner)getActivity().findViewById(R.id.spinnerSertificates);
        _SpinnerSertificates.setAdapter(adapter);
        return rootView;
    }

    private void GetSettings()
    {

        controller.GetSettings(new CallbackImpl(WebAdministrationFragment.this) {
            @Override
            public void onResponse(Call call, Response response) throws IOException, InterruptedException {

                super.onResponse(call,response);
                final WebSettings res = response.GetResultObject( new TypeToken<WebSettings>(){});

                handler.post(new Runnable(){
                    public void run() {
                        InitSettings(res);
                    }
                });

                controller.GetCertificate(new CallbackImpl(WebAdministrationFragment.this) {

                    @Override
                    public void onResponse(Call call, Response response) throws IOException, InterruptedException {
                        super.onResponse(call,response);
                        final Result<Certificate> res = response.GetResultObject( new TypeToken<Result<Certificate>>(){});

                        if(res != null && res.getTotal() > 0)
                            handler.post(new Runnable(){
                                public void run() {
                                    setCertificate(res.getData());
                                }
                            });
                    }
                });
            }
        });



    }

    private void setCertificate(List<Certificate> res)
    {
        Certificates.clear();
        Certificates.addAll(res);
        adapter.notifyDataSetChanged();
    }

    private void InitSettings(final WebSettings settings)
    {



        _SwithEnableSSL.setChecked(settings.getEnablessl());
        _SwithEnableSSL.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                _SwithForceSSL.setEnabled(b);
                _TextViewPortSSL.setEnabled(b);
                _SpinnerSertificates.setEnabled(b);
            }
        });

        _SwithForceSSL.setChecked(settings.getForcesslonly());
        _SwithForceSSL.setEnabled(settings.getEnablessl());

        _TextViewPort.setText(Integer.toString(settings.getPort()));

        _TextViewPortSSL.setText(Integer.toString(settings.getSslport()));
        _TextViewPortSSL.setEnabled(settings.getEnablessl());

        _TextViewTimeout.setText(Integer.toString(settings.getTimeout()));


        _SpinnerSertificates.setEnabled(settings.getEnablessl());


        _ButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handler.post(new Runnable(){
                    public void run() {

                        if(!IsFinalized(true))return;
                        WebSettings settings = new WebSettings();
                        settings.setEnablessl(_SwithEnableSSL.isChecked());
                        settings.setForcesslonly(_SwithForceSSL.isChecked());
                        settings.setPort(Integer.parseInt(_TextViewPort.getText().toString()));

                        Certificate cerf = (Certificate)_SpinnerSertificates.getSelectedItem();

                        settings.setSslcertificateref(cerf != null ?cerf.getUuid():"");
                        settings.setSslport(Integer.parseInt(_TextViewPortSSL.getText().toString()));
                        settings.setTimeout(Integer.parseInt(_TextViewTimeout.getText().toString()));
                        controller.SetSettings(settings, new CallbackImpl(WebAdministrationFragment.this));
                    }
                });
            }
        });

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
