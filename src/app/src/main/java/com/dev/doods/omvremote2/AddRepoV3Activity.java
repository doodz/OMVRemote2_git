package com.dev.doods.omvremote2;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;

import java.io.IOException;

import Client.Call;
import Client.Callback;
import Client.Response;
import Controllers.ConfigController;
import Controllers.omvExtrasController;
import Models.Errors;
import Models.RepoV3;
import OMV.Classe.openmediavault_default;

public class AddRepoV3Activity extends AppCompatActivity {

    private RepoV3 mRepo;

    omvExtrasController mController = new omvExtrasController(this);

    ConfigController mConfigController = new ConfigController(this);
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_repo_v3);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        BindViews();
        Bundle bundle = getIntent().getExtras();
        if(bundle!= null && bundle.containsKey("RepoV3"))
        {
            setTitle(getString(R.string.edite_Repo));
            mRepo = (RepoV3)bundle.getSerializable("RepoV3");
            populateViews();
        }
        else
        {
            mRepo = new RepoV3();
            mRepo.setUuid(openmediavault_default.OMV_CONFIGOBJECT_NEW_UUID);
        }




        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                populateRepo();
                mController.setRepoV3(mRepo, new Callback() {
                    @Override
                    public void onFailure(Call call, Exception e) {

                    }

                    @Override
                    public void OnOMVServeurError(Call call, Errors error) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException, InterruptedException {
                        mConfigController.applyChangesBg(new Callback() {
                            @Override
                            public void onFailure(Call call, Exception e) {

                            }

                            @Override
                            public void OnOMVServeurError(Call call, Errors error) {

                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException, InterruptedException {
                                finish();
                            }
                        });
                    }
                });

            }
        });
    }


    private Switch mSwithEnable;
    private EditText etNameView;
    private EditText etCommentView;

    private EditText etRepo1View;
    private EditText etKey1View;
    private EditText etPackage1View;
    private EditText etPin1View;
    private EditText etPriority1View;

    private EditText etRepo2View;
    private EditText etKey2View;
    private EditText etPackage2View;
    private EditText etPin2View;
    private EditText etPriority2View;

    private EditText etRepo3View;
    private EditText etKey3View;
    private EditText etPackage3View;
    private EditText etPin3View;
    private EditText etPriority3View;

    private Switch swithPermanentView;
    private void BindViews()
    {

        mSwithEnable = (Switch) findViewById(R.id.swithEnable);
        etNameView = (EditText)  findViewById(R.id.etName);
        etCommentView = (EditText)  findViewById(R.id.etComment);

        etRepo1View = (EditText)  findViewById(R.id.etRepo1);
        etKey1View = (EditText)  findViewById(R.id.etKey1);
        etPackage1View = (EditText)  findViewById(R.id.etPackage1);
        etPin1View = (EditText)  findViewById(R.id.etPin1);
        etPriority1View = (EditText)  findViewById(R.id.etPriority1);

        etRepo2View = (EditText)  findViewById(R.id.etRepo2);
        etKey2View = (EditText)  findViewById(R.id.etKey2);
        etPackage2View = (EditText)  findViewById(R.id.etPackage2);
        etPin2View = (EditText)  findViewById(R.id.etPin2);
        etPriority2View = (EditText)  findViewById(R.id.etPriority2);

        etRepo3View = (EditText)  findViewById(R.id.etRepo3);
        etKey3View = (EditText)  findViewById(R.id.etKey3);
        etPackage3View = (EditText)  findViewById(R.id.etPackage3);
        etPin3View = (EditText)  findViewById(R.id.etPin3);
        etPriority3View = (EditText)  findViewById(R.id.etPriority3);

        swithPermanentView = (Switch) findViewById(R.id.swithPermanent);
    }


    private void populateViews()
    {

        mSwithEnable.setChecked(mRepo.getEnable());
        etNameView.setText(mRepo.getName());
        etCommentView.setText(mRepo.getComment());

        etRepo1View.setText(mRepo.getRepo1());
        etKey1View.setText(mRepo.getKey1());
        etPackage1View.setText(mRepo.getPackage1());
        etPin1View.setText(mRepo.getPin1());
        etPriority1View.setText(mRepo.getPriority1());

        etRepo2View.setText(mRepo.getRepo2());
        etKey2View.setText(mRepo.getKey2());
        etPackage2View.setText(mRepo.getPackage2());
        etPin2View.setText(mRepo.getPin2());
        etPriority2View.setText(mRepo.getPriority2());

        etRepo3View.setText(mRepo.getRepo3());
        etKey3View.setText(mRepo.getKey3());
        etPackage3View.setText(mRepo.getPackage3());
        etPin3View.setText(mRepo.getPin3());
        etPriority3View.setText(mRepo.getPriority3());

        swithPermanentView.setChecked(mRepo.getPermanent());


        etNameView.setEnabled(!mRepo.getPermanent());
        etCommentView.setEnabled(!mRepo.getPermanent());

        etRepo1View.setEnabled(!mRepo.getPermanent());
        etKey1View.setEnabled(!mRepo.getPermanent());
        etPackage1View.setEnabled(!mRepo.getPermanent());
        etPin1View.setEnabled(!mRepo.getPermanent());
        etPriority1View.setEnabled(!mRepo.getPermanent());

        etRepo2View.setEnabled(!mRepo.getPermanent());
        etKey2View.setEnabled(!mRepo.getPermanent());
        etPackage2View.setEnabled(!mRepo.getPermanent());
        etPin2View.setEnabled(!mRepo.getPermanent());
        etPriority2View.setEnabled(!mRepo.getPermanent());

        etRepo3View.setEnabled(!mRepo.getPermanent());
        etKey3View.setEnabled(!mRepo.getPermanent());
        etPackage3View.setEnabled(!mRepo.getPermanent());
        etPin3View.setEnabled(!mRepo.getPermanent());
        etPriority3View.setEnabled(!mRepo.getPermanent());

        swithPermanentView.setEnabled(!mRepo.getPermanent());

    }

    private void populateRepo()
    {

        mRepo.setEnable(mSwithEnable.isChecked());
        mRepo.setName(etNameView.getText().toString());
        mRepo.setComment(etCommentView.getText().toString());

        mRepo.setRepo1(etRepo1View.getText().toString());
        mRepo.setKey1(etKey1View.getText().toString());
        mRepo.setPackage1(etPackage1View.getText().toString());
        mRepo.setPin1(etPin1View.getText().toString());
        mRepo.setPriority1(etPriority1View.getText().toString());

        mRepo.setRepo2(etRepo2View.getText().toString());
        mRepo.setKey2(etKey2View.getText().toString());
        mRepo.setPackage2(etPackage2View.getText().toString());
        mRepo.setPin2(etPin2View.getText().toString());
        mRepo.setPriority2(etPriority2View.getText().toString());

        mRepo.setRepo3(etRepo3View.getText().toString());
        mRepo.setKey3(etKey3View.getText().toString());
        mRepo.setPackage3(etPackage3View.getText().toString());
        mRepo.setPin3(etPin3View.getText().toString());
        mRepo.setPriority3(etPriority3View.getText().toString());

        mRepo.setPermanent(swithPermanentView.isChecked());

    }

}
