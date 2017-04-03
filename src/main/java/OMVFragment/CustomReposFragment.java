package OMVFragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.CallSuper;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
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

import com.dev.doods.omvremote2.R;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Client.Call;
import Client.Callback;
import Client.Response;
import Controllers.omvExtrasController;
import Interfaces.NoticeDialogListener;
import Interfaces.OnFragmentInteractionListener;
import Models.CustomRepo;
import Models.Errors;
import Models.Result;
import OMVFragment.Dialogs.AddEditeCustomRepoDialogFragment;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class CustomReposFragment extends FragmentInteractionBase implements NoticeDialogListener {


    private List<CustomRepo> mLst = new ArrayList<CustomRepo>();
    private ArrayAdapter<CustomRepo> mAdapter;
    private CustomRepo mRepo;
    private ListView mCustomRepo;
    private Handler handler = new Handler();

    private omvExtrasController mController ;
    public CustomReposFragment() {
        // Required empty public constructor
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
        View rootView = inflater.inflate(R.layout.fragment_custom_repos, container, false);

        mCustomRepo = (ListView) rootView.findViewById(R.id.Listview_custom_repo);


        mAdapter = new ArrayAdapter<CustomRepo>(getActivity(),
                android.R.layout.simple_list_item_1, mLst);
        mCustomRepo.setAdapter(mAdapter);


        mCustomRepo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                mRepo = mLst.get(i);
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


    @Override
    public int GetIconActionId()
    {
        return R.drawable.ic_add_box_black_24dp;
    }



    @CallSuper
    public void onResume() {
        super.onResume();

        getCustomlist();
    }


    private  void getCustomlist()
    {
        mController.getCustomList(new Callback() {
            @Override
            public void onFailure(Call call, Exception e) {

            }

            @Override
            public void OnOMVServeurError(Call call, Errors error) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException, InterruptedException {

                final Result<CustomRepo> res = response.GetResultObject(new TypeToken<Result<CustomRepo>>(){});
                handler.post(new Runnable(){
                    public void run() {
                        populateLstView(res.getData());
                    }
                });

            }
        });
    }


    private void populateLstView(List<CustomRepo> lst)
    {
        mLst.clear();
        mLst.addAll(lst);
        mAdapter.notifyDataSetChanged();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mController = new omvExtrasController(getActivity());
    }

    @Override
    public void Update()
    {
        mDialog = new AddEditeCustomRepoDialogFragment();
        mDialog.setListener(this);
        mDialog.show(getActivity().getSupportFragmentManager(), "AddEditeCustomRepoDialogFragment");

    }


    private AddEditeCustomRepoDialogFragment mDialog;
    public void ShowCustomRepoDialog()
    {
        Bundle bundle = new Bundle();



        bundle.putSerializable("CustomRepo", mRepo);
        // Create an instance of the dialog fragment and show it
        mDialog = new AddEditeCustomRepoDialogFragment();
        mDialog.setArguments(bundle);
        mDialog.setListener(this);
        mDialog.show(getActivity().getSupportFragmentManager(), "AddEditeCustomRepoDialogFragment");

    }

    private void SelectCutomRepo()
    {
        Snackbar.make(mCustomRepo, "not implemented", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();

    }

    private void EditeCustomRepo()
    {
        ShowCustomRepoDialog();
    }
    private void DeleteCustomRepo()
    {




        mController.deleteCustom(mRepo.getUuid(),null);

        mLst.remove(mRepo);
        mAdapter.notifyDataSetChanged();

    }
    private ActionMode.Callback modeCallBack = new ActionMode.Callback() {

        public boolean onPrepareActionMode(ActionMode mode, Menu menu){
            return false;
        }

        public void onDestroyActionMode(ActionMode mode) {
            mode = null;
        }

        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.setTitle(mRepo.getName());
            mode.getMenuInflater().inflate(R.menu.row_host_menu, menu);
            return true;
        }

        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            int id = item.getItemId();

            if(item.getItemId() ==R.id.action_delete)
            {
                DeleteCustomRepo();
                mode.finish();
                return true;
            }
            else if(item.getItemId() ==R.id.action_select)
            {
                SelectCutomRepo();
                mode.finish();
                return true;
            }
            else if(item.getItemId() ==R.id.action_edite)
            {

                EditeCustomRepo();
                mode.finish();
                return true;
            }
            else
            {
                return false;
            }

        }
    };

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        getCustomlist();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }
}
