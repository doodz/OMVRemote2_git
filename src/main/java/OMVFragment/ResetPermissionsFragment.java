package OMVFragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Switch;

import com.dev.doods.omvremote2.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import Client.Call;
import Client.Callback;
import Client.Response;
import Controllers.ShareMgmtController;
import Deserializers.PrivilegesDeserializer;
import Interfaces.OnFragmentInteractionListener;
import Models.Errors;
import Models.Privileges;
import Models.Result;
import Models.SharedFolder;
import OMV.Base.FragmentInteractionBase;
import utils.SnackBarError;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class ResetPermissionsFragment extends FragmentInteractionBase {


    private ShareMgmtController mController ;
    private ArrayAdapter<SharedFolder> mAdapterSharedFolder;
    private Spinner mSharedFolderView;
    private Spinner mPermissionsView;
    private Switch ClearAclView;
    public ResetPermissionsFragment() {
        // Required empty public constructon
        HaveUpdateMethode = true;

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
        View rootView = inflater.inflate(R.layout.fragment_reset_permissions, container, false);
        mSharedFolderView =(Spinner) rootView.findViewById(R.id.spinnerSharedFolder);
        mPermissionsView =(Spinner) rootView.findViewById(R.id.spinnerPermissions);
        ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, ShareMgmtController.PermissionsType);
        mPermissionsView.setAdapter(mAdapter);

        ClearAclView =(Switch) rootView.findViewById(R.id.SwithClearAcl);

        mLst = new ArrayList<SharedFolder>();
        mAdapterSharedFolder = new ArrayAdapter<SharedFolder>(getActivity(),
                android.R.layout.simple_spinner_item, mLst);
        mSharedFolderView.setAdapter(mAdapterSharedFolder);

        return rootView;
    }

    @CallSuper
    public void onResume() {
        super.onResume();
        mController.getSharedFolders(new Callback() {
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

                TypeToken tt = new TypeToken<Result<SharedFolder>>(){};
                Type t =  tt.getType();
                // ArrayList<LinkedTreeMap<String,String>> oo =Util.FromJson(j,Object.class);
                final Result<SharedFolder> res = gson.fromJson(j,t);


                //final Result<SharedFolder> res = response.GetResultObject( new TypeToken<Result<SharedFolder>>(){});
                mHandler.post(new Runnable(){
                    public void run() {
                        populateSharedFolderView(res.getData());
                    }
                });

            }
        });
    }

    List<SharedFolder> mLst;

    private void populateSharedFolderView(List<SharedFolder> lst)
    {
        mLst.clear();
        mLst.addAll(lst);
        mAdapterSharedFolder.notifyDataSetChanged();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mController = new ShareMgmtController(getActivity());
    }

    @Override
    public void Update()
    {

        boolean b = ClearAclView.isChecked();
        int i = mSharedFolderView.getSelectedItemPosition();
        int j = mPermissionsView.getSelectedItemPosition();

        if(i == -1 || j == -1)
        {
            new SnackBarError(ClearAclView,"You need shared folders for resetting permissions.",false);
            return;
        }

        mController.setResetPerms(ClearAclView.isChecked(), ShareMgmtController.PermissionsVal.get(j), ShareMgmtController.DirpermsVal.get(j),
                ShareMgmtController.FilepermsVal.get(j), mLst.get(i).getUuid(), new Callback() {
            @Override
            public void onFailure(Call call, Exception e) {

            }

                    @Override
                    public void OnOMVServeurError(Call call, Errors error) {

                    }

                    @Override
            public void onResponse(Call call, Response response) throws IOException, InterruptedException {

            }
        });


    }

}
