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
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import com.dev.doods.base.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.innahema.collections.query.functions.Predicate;
import com.innahema.collections.query.queriables.Queryable;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import Client.Call;
import Client.Callback;
import Client.Response;
import Controllers.ServicesController;
import Deserializers.PrivilegesDeserializer;
import Interfaces.IUpdateFragment;
import Interfaces.OnFragmentInteractionListener;
import Models.Errors;
import Models.Privileges;
import Models.SharedFolder;
import Models.TFTPSettings;
import utils.InputFilterMinMax;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TFTPFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TFTPFragment extends Fragment implements IUpdateFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ServicesController mController ;
    private List<SharedFolder> mSharedFolders = new ArrayList<SharedFolder>();
    private ArrayAdapter<SharedFolder> mAdapter;
    Handler handler;
    private TFTPSettings mTFTPSettings;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public TFTPFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TFTPFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TFTPFragment newInstance(String param1, String param2) {
        TFTPFragment fragment = new TFTPFragment();
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
    private EditText mPortView;
    private EditText mOptionView;
    private EditText mBlocksize;
    private EditText mRetryTimeout;
    private Switch mNewFiles;
    private Spinner mSpinnerSharedFolder;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_tftp, container, false);
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

        mSpinnerSharedFolder = (Spinner) rootView.findViewById(R.id.spinnerSharedFolder);
        mAdapter = new ArrayAdapter<SharedFolder>(getActivity(),
                android.R.layout.simple_spinner_item, mSharedFolders);
        mSpinnerSharedFolder.setAdapter(mAdapter);


        mBlocksize = (EditText) rootView.findViewById(R.id.etBlocksize);
        mBlocksize.setFilters(new InputFilter[]{ new InputFilterMinMax(512, 65464) });
        mRetryTimeout  = (EditText) rootView.findViewById(R.id.etRetryTimeout);
        mNewFiles = (Switch) rootView.findViewById(R.id.swithNewFiles);
        mOptionView = (EditText) rootView.findViewById(R.id.etOption);


        mController.getService("TFTP", new Callback() {
            @Override
            public void onFailure(Call call, Exception e) {

            }

            @Override
            public void OnOMVServeurError(Call call, Errors error) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException, InterruptedException {


                mTFTPSettings = response.GetResultObject(new TypeToken<TFTPSettings>(){});

                handler.post(new Runnable(){
                    public void run() {
                        mSwithEnable.setChecked(mTFTPSettings.getEnable());
                        mPortView.setText(Integer.toString(mTFTPSettings.getPort()));

                        //TODO implmeter le truc
                        String charedfolder = mTFTPSettings.getSharedfolderref();

                        mBlocksize.setText(Integer.toString(mTFTPSettings.getBlocksize()));
                        mRetryTimeout.setText(Integer.toString(mTFTPSettings.getRetransmit()));
                        mNewFiles.setChecked(mTFTPSettings.getAllownewfiles());
                        mOptionView.setText(mTFTPSettings.getExtraoptions());
                        SetEnableView(mTFTPSettings.getEnable());
                        selectDefaultFolder();
                    }
                });
            }
        });
        mController.getSharedFoldersFromMger(new Callback() {
            @Override
            public void onFailure(Call call, Exception e) {

            }

            @Override
            public void OnOMVServeurError(Call call, Errors error) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException, InterruptedException {


                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.registerTypeAdapter(Privileges.class, new PrivilegesDeserializer());
                //gsonBuilder.registerTypeAdapter(SystemInformation.class, new SystemInformationDeserializer());
                Gson gson = gsonBuilder.create();
                JsonElement j = response.GetJsonResult();

                TypeToken tt = new TypeToken<List<SharedFolder>>(){};
                Type t =  tt.getType();
                // ArrayList<LinkedTreeMap<String,String>> oo =Util.FromJson(j,Object.class);
                final  List<SharedFolder> res = gson.fromJson(j,t);

                //final List<SharedFolder> res = response.GetResultObject(new TypeToken<List<SharedFolder>>(){});
                handler.post(new Runnable(){
                    public void run() {
                        PopulateSharedFolders(res);
                    }
                });

            }
        });
        return  rootView;
    }



    private void PopulateSharedFolders(List<SharedFolder> lst)
    {
        mSharedFolders.clear();

        SharedFolder sfNull = new SharedFolder();
        sfNull.setDescription("None");
        sfNull.setMntentref("");
        mSharedFolders.add(sfNull);
        mSharedFolders.addAll(lst);
        mAdapter.notifyDataSetChanged();

        selectDefaultFolder();

    }

    private void selectDefaultFolder()
    {

        if((mTFTPSettings != null && mSharedFolders.size() > 0) && !mTFTPSettings.getSharedfolderref().equals(""))
        {
            SharedFolder sf= Queryable.from(mSharedFolders).firstOrDefault(new Predicate<SharedFolder>() {
                @Override
                public boolean apply(SharedFolder element) {
                    return element.getMntentref().equals(mTFTPSettings.getSharedfolderref());
                }
            });

            if(sf != null)
            {
                mSpinnerSharedFolder.setSelection(mSharedFolders.indexOf(sf));
            }

        }
    }


    private void SetEnableView(boolean b) {
        mPortView.setEnabled(b);
        mSpinnerSharedFolder.setEnabled(b);
        mBlocksize.setEnabled(b);
        mRetryTimeout.setEnabled(b);
        mNewFiles.setEnabled(b);
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

        mTFTPSettings.setEnable(mSwithEnable.isChecked());
        mTFTPSettings.setPort(Integer.parseInt(mPortView.getText().toString()));
        mTFTPSettings.setBlocksize(Integer.parseInt(mBlocksize.getText().toString()));
        mTFTPSettings.setRetransmit(Integer.parseInt(mRetryTimeout.getText().toString()));
        mTFTPSettings.setAllownewfiles(mNewFiles.isChecked());
        mTFTPSettings.setExtraoptions(mOptionView.getText().toString());


        int i = mSpinnerSharedFolder.getSelectedItemPosition();
        SharedFolder sf =(SharedFolder) mSpinnerSharedFolder.getSelectedItem();
        if(sf == null || sf.getMntentref().equals("")) {

            OnMessage("Shared folder is Required");

            mSpinnerSharedFolder.requestFocus();
            return;
            //mTFTPSettings.setSharedfolderref("");

        }
        else
            mTFTPSettings.setSharedfolderref(sf.getUuid());

            mController.setService(mTFTPSettings, new Callback() {
            @Override
            public void onFailure(Call call, Exception e) {



            }

                @Override
                public void OnOMVServeurError(Call call, Errors error) {
                    OnError(error.toString());
                }

                @Override
            public void onResponse(Call call, Response response) throws IOException, InterruptedException {


                OnMessage(mTFTPSettings.getServiceName()+": config saved");

            }
        });

    }

    @Override
    public String getServiceName() {
        return mTFTPSettings.getServiceName();
    }
}
