package com.dev.doods.omvremote2;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.google.gson.reflect.TypeToken;

import java.io.IOException;

import Client.Call;
import Client.Callback;
import Client.Response;
import Controllers.CertificateController;
import Models.Errors;

public class EditeCertificateActivity extends AppCompatActivity {

    private Handler handler= new Handler();
    Models.Certificate Certificate;
    Models.CertificateSsl CertificateSsl;

    CertificateController mController = new CertificateController(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edite_certificate);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getExtras();
        if(bundle!= null && bundle.containsKey("Certificate"))
        {
            Certificate =  (Models.Certificate )bundle.getSerializable("Certificate");

            mController.Get(Certificate.getUuid(), new Callback() {
                @Override
                public void onFailure(Call call, Exception e) {

                }

                @Override
                public void OnOMVServeurError(Call call, Errors error) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException, InterruptedException {

                    CertificateSsl = response.GetResultObject(new TypeToken<Models.CertificateSsl>(){});
                    handler.post(new Runnable(){
                        public void run() {
                            setViews();
                        }
                    });

                }
            });
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CertificateSsl.setPrivatekey(PrivateKeyView.getText().toString());
                CertificateSsl.setCertificate(CertificateView.getText().toString());
                Certificate.setComment(CommentView.getText().toString());
                mController.Set(CertificateSsl,null);
                finish();
            }
        });
    }



    EditText PrivateKeyView;
    EditText CertificateView;
    EditText CommentView;
    private void setViews()
    {
        PrivateKeyView = (EditText) findViewById(R.id.Et_Private_key);
        CertificateView = (EditText) findViewById(R.id.Et_Certificate);
        CommentView = (EditText) findViewById(R.id.Et_Comment);

        PrivateKeyView.setText(CertificateSsl.getPrivatekey());
        CertificateView.setText(CertificateSsl.getCertificate());
        CommentView.setText(Certificate.getComment());
    }
}
