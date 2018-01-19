package com.dev.doods.omvremote2;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.gson.reflect.TypeToken;
import com.innahema.collections.query.functions.Predicate;
import com.innahema.collections.query.queriables.Queryable;

//import org.acra.ACRA;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Client.Call;
import Client.Callback;
import Client.Response;
import Controllers.ShareMgmtController;
import Interfaces.NoticeDialogListener;
import Models.Errors;
import Models.SharedFolder;
import Models.SimpleDevice;
import OMV.Classe.TreeFolderNode;
import OMVFragment.Dialogs.FolderBrowserDialogFragment;

public class AddEditeSharedFolderActivity extends AppCompatActivity implements NoticeDialogListener {

    private SharedFolder mFolder;
    //private OutputController mOutputController = new OutputController(AddEditeSharedFolderActivity.this);
    private ShareMgmtController mController = new ShareMgmtController(this);
    private FloatingActionButton fab_FolderBrowser;
    private Handler handler;

    private EditText mFolderNameView;
    private EditText mFolferPathView;
    private EditText mFolderCommentView;
    private FolderBrowserDialogFragment mDialog;
    private Spinner mVolumeView;
    private Spinner mPermissionView;
    ArrayAdapter<SimpleDevice> mAdapterVolume;
    private List<SimpleDevice> mDevices = new ArrayList<SimpleDevice>();
    private FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edite_shared_folder);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        handler= new Handler();

        Bundle bundle = getIntent().getExtras();


        mFolderNameView = (EditText) findViewById(R.id.etFolderName);
        mVolumeView = (Spinner) findViewById(R.id.spinnerVolume);



        if(bundle!= null && bundle.containsKey("SharedFolder"))
        {
            setTitle(getString(R.string.edite_folder));
            mFolder = (SharedFolder)bundle.getSerializable("SharedFolder");
            mFolderNameView.setEnabled(false);

        }
        else
            mFolder = new SharedFolder();

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setEnabled(false);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Save();
            }
        });

        fab_FolderBrowser = (FloatingActionButton) findViewById(R.id.fab_FolderBrowser);

        fab_FolderBrowser.setEnabled(false);

        fab_FolderBrowser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowFolderBRowserDialog();
            }
        });

        mAdapterVolume = new ArrayAdapter<SimpleDevice>(AddEditeSharedFolderActivity.this,
                android.R.layout.simple_spinner_item, mDevices);
        mVolumeView.setAdapter(mAdapterVolume);

        mFolferPathView = (EditText) findViewById(R.id.etFolderPath);
        mPermissionView = (Spinner) findViewById(R.id.spinnerPermissions);

        ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(AddEditeSharedFolderActivity.this,
                android.R.layout.simple_spinner_item, ShareMgmtController.PermissionsType);
        mPermissionView.setAdapter(mAdapter);

        mFolderCommentView = (EditText) findViewById(R.id.etFolderComment);

        mController.getCandidates(new Callback() {
            @Override
            public void onFailure(Call call, Exception e) {

            }

            @Override
            public void OnOMVServeurError(Call call, Errors error) {
                Snackbar.make(fab, error.getMessage(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException, InterruptedException {

                final ArrayList<SimpleDevice> res = response.GetResultObject(new TypeToken<ArrayList<SimpleDevice>>(){});
                handler.post(new Runnable(){
                    public void run() {
                        PopulateVoumeView(res);
                    }
                });
            }
        });

        PopulateTextViews();
    }

    private void PopulateTextViews()
    {
        mFolderNameView.setText(mFolder.getName());
        mFolferPathView.setText(mFolder.getReldirpath());
        mFolderCommentView.setText(mFolder.getComment());

    }


    private void PopulateVoumeView(List<SimpleDevice> res)
    {
        mDevices.clear();
        mDevices.addAll(res);
        mAdapterVolume.notifyDataSetChanged();

        SimpleDevice sd = Queryable.from(mDevices).firstOrDefault(new Predicate<SimpleDevice>() {
            @Override
            public boolean apply(SimpleDevice element) {
                return element.getUuid().equals(mFolder.getMntentref());
            }
        });

        if(sd != null)
        {
            mVolumeView.setSelection(mDevices.indexOf(sd));
        }

        if(!mDevices.isEmpty())
        {
            fab_FolderBrowser.setEnabled(true);
            fab.setEnabled(true);
        }
        else
        {
            Snackbar snackbar = Snackbar
                    .make(fab_FolderBrowser, getString(R.string.not_volumes),
                            Snackbar.LENGTH_INDEFINITE);
            snackbar.show();
        }
    }


    public void ShowFolderBRowserDialog()
    {
        Bundle bundle = new Bundle();

        int i = mVolumeView.getSelectedItemPosition();

        SimpleDevice device = mDevices.get(i);

        bundle.putSerializable("UUID", device.getUuid());
        // Create an instance of the dialog fragment and show it
        mDialog = new FolderBrowserDialogFragment();
        mDialog.setArguments(bundle);
        mDialog.show(getSupportFragmentManager(), "FilterPluginDialogFragment");

    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {

        TreeFolderNode node = mDialog.getSelectedNode();

        if(node != null)
        {

            if(mFolder.getUuid() == null)
                mFolderNameView.setText(node.data.toString());
            mFolferPathView.setText(node.Path());
        }

    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }

    private void Save()
    {
        int i = mVolumeView.getSelectedItemPosition();
        SimpleDevice device = mDevices.get(i);

        String tmp = mFolderNameView.getText().toString();
        if(StringUtils.isEmpty(tmp))
        {
            mFolderNameView.setError(getString(R.string.error_field_required));
            return;
        }
        else
            mFolderNameView.setError(null);

        tmp = mFolferPathView.getText().toString();
        if(StringUtils.isEmpty(tmp))
        {
            mFolferPathView.setError(getString(R.string.error_field_required));
            return;
        }
        else
            mFolferPathView.setError(null);

        mFolder.setName(mFolderNameView.getText().toString());
        mFolder.setReldirpath(mFolferPathView.getText().toString());
        mFolder.setMntentref(device.getUuid());
        mFolder.setComment(mFolderCommentView.getText().toString());
        i = mPermissionView.getSelectedItemPosition();
        String mode = ShareMgmtController.PermissionsVal.get(i);

        mController.setSharedFolder(mFolder, mode, new Callback() {
            @Override
            public void onFailure(Call call, Exception e) {

            }

            @Override
            public void OnOMVServeurError(Call call, Errors error) {
                Snackbar.make(fab, error.getMessage(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException, InterruptedException {
                finish();
            }
        });
    }


}
