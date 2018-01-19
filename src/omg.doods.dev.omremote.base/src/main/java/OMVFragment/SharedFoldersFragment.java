package OMVFragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.CallSuper;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.dev.doods.base.AddEditeSharedFolderActivity;
import com.dev.doods.base.R;
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

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class SharedFoldersFragment extends FragmentInteractionBase {

    Handler handler;
    private ShareMgmtController mController ;
    List<SharedFolder> mSharedFoldersLst = new ArrayList<SharedFolder>();
    private ListView mListView;
    private ArrayAdapter<SharedFolder> mAdapter;
    private  SharedFolder mSharedFolder;

    public SharedFoldersFragment() {
        HaveUpdateMethode = true;
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
        View rootView =  inflater.inflate(R.layout.fragment_shared_folders3, container, false);
        handler= new Handler();
        mListView = (ListView) rootView.findViewById(R.id.ShareLst);
        mAdapter = new ArrayAdapter<SharedFolder>(getActivity(),
                android.R.layout.simple_list_item_1, mSharedFoldersLst);
        mListView.setAdapter(mAdapter);


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                mSharedFolder = mlst.get(i);
                //Bundle bundle = new Bundle();
                //bundle.putSerializable("host", mHost);
                //Intent intent = new Intent(HostManagerActivity.this, LoginActivity.class);
                //intent.putExtras(bundle);
                //startActivity(intent);
                view.setSelected(true);

                ( (AppCompatActivity) getActivity()).startSupportActionMode(modeCallBack);

                //startActionMode(modeCallBack);

            }
        });



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
                handler.post(new Runnable(){
                    public void run() {
                        populateLstView(res.getData());
                    }
                });

            }
        });
    }


    List<SharedFolder> mlst;
    private void populateLstView(List<SharedFolder> lst)
    {
        mlst = lst;
        mSharedFoldersLst.clear();
        mSharedFoldersLst.addAll(lst);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mController = new ShareMgmtController(getActivity());
    }

    @Override
    public void Update() {

        startActivity(new Intent(getActivity(), AddEditeSharedFolderActivity.class));

    }


    @Override
    public int GetIconActionId()
    {
       return R.drawable.ic_create_new_folder_black_24dp;
    }

    private void ShowDeleteContentDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Delete Content");
        builder.setMessage(getString(R.string.dialog_delete_Content ));

        String positiveText = getString(R.string._no);
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mController.DeleteSharedFolder(false, mSharedFolder.getUuid(),null);
                        mSharedFoldersLst.remove(mSharedFolder);
                        mAdapter.notifyDataSetChanged();
                    }});


        String neutralText = getString(R.string._yes);
        builder.setNeutralButton(neutralText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mController.DeleteSharedFolder(true, mSharedFolder.getUuid(), null);
                        mSharedFoldersLst.remove(mSharedFolder);
                        mAdapter.notifyDataSetChanged();
                    }
                });

        String negativeText = getString(R.string._cancel);
        builder.setNegativeButton(negativeText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // negative button logic
                    }
                });

        AlertDialog dialog = builder.create();
        // display dialog
        dialog.show();
    }

    private void ShowDeleteItemDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Delete "+mSharedFolder.getName());
        builder.setMessage(getString(R.string.dialog_delete_item ));

        String positiveText = getString(android.R.string.ok);
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ShowDeleteContentDialog();
                    }
                });

        String negativeText = getString(android.R.string.cancel);
        builder.setNegativeButton(negativeText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // negative button logic
                    }
                });

        AlertDialog dialog = builder.create();
        // display dialog
        dialog.show();
    }


    private void DeleteSharedFolder()
    {
        ShowDeleteItemDialog();
    }

    private void SelectSharedFolder()
    {
        Snackbar.make(mListView, "not implemented", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();

    }
    private void EditeSharedFolder()
    {
        Intent mIntent = new Intent(getActivity(), AddEditeSharedFolderActivity.class);
        Bundle extras = new Bundle();
        extras.putSerializable("SharedFolder", mSharedFolder);
        mIntent.putExtras(extras);
        startActivity(mIntent);
    }

    private ActionMode.Callback modeCallBack = new ActionMode.Callback() {

        public boolean onPrepareActionMode(ActionMode mode, Menu menu){
            return false;
        }

        public void onDestroyActionMode(ActionMode mode) {
            mode = null;
        }

        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.setTitle(mSharedFolder.getName());
            mode.getMenuInflater().inflate(R.menu.row_host_menu, menu);
            return true;
        }

        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            int id = item.getItemId();

            if(item.getItemId() ==R.id.action_delete)
            {
                mode.finish();

                return true;
            }
            else if(item.getItemId() ==R.id.action_select)
            {
                SelectSharedFolder();
                mode.finish();
                return true;
            }
            else if(item.getItemId() ==R.id.action_edite)
            {

                EditeSharedFolder();
                mode.finish();
                return true;
            }
            else
            {
                return false;
            }


        }
    };

}
