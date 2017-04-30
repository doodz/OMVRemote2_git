package OMVFragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

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
import Models.SharedFolderInUse;
import OMV.Base.FragmentInteractionBase;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class SharedFolderInUseFragment extends FragmentInteractionBase {

    private ShareMgmtController mController ;
    List<SharedFolderInUse> mSharedFolder = new ArrayList<SharedFolderInUse>();
    private ListView mListView;
    private TextView mNoOMVExtra;
    private ArrayAdapter<SharedFolderInUse> mAdapter;

    public SharedFolderInUseFragment() {
        // Required empty public constructor
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
        View rootView = inflater.inflate(R.layout.fragment_shared_folder_in_use, container, false);

        mListView = (ListView) rootView.findViewById(R.id.ShareLst);
        mNoOMVExtra= (TextView) rootView.findViewById(R.id.No_OMV_Extra);

        mAdapter = new ArrayAdapter<SharedFolderInUse>(getActivity(),
                android.R.layout.simple_list_item_1, mSharedFolder);
        mListView.setAdapter(mAdapter);


        return rootView;
    }

    @CallSuper
    public void onResume() {
        super.onResume();
        mController.getSharedFolderInUseList(new Callback() {
            @Override
            public void onFailure(Call call, Exception e) {

            }

            @Override
            public void OnOMVServeurError(Call call, Errors error) {
                mHandler.post(new Runnable(){
                    public void run() {
                            mListView.setVisibility(View.GONE);
                            mNoOMVExtra.setVisibility(View.VISIBLE);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException, InterruptedException {


                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.registerTypeAdapter(Privileges.class, new PrivilegesDeserializer());
                //gsonBuilder.registerTypeAdapter(SystemInformation.class, new SystemInformationDeserializer());
                Gson gson = gsonBuilder.create();
                JsonElement j = response.GetJsonResult();

                TypeToken tt = new TypeToken<Result<SharedFolderInUse>>(){};
                Type t =  tt.getType();
                // ArrayList<LinkedTreeMap<String,String>> oo =Util.FromJson(j,Object.class);
                final Result<SharedFolderInUse> res = gson.fromJson(j,t);

                //final Result<SharedFolderInUse> res = response.GetResultObject( new TypeToken< Result<SharedFolderInUse>>(){});
                mHandler.post(new Runnable(){
                    public void run() {
                            populateLstView(res.getData());
                    }
                });
            }
        });
    }

    private void populateLstView(List<SharedFolderInUse> lst)
    {
        mSharedFolder.clear();
        mSharedFolder.addAll(lst);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mController = new ShareMgmtController(getActivity());
    }



}
