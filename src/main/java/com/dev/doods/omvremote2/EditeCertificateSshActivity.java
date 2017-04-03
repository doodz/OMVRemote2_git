package com.dev.doods.omvremote2;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import Controllers.CertificateController;
import Models.CertificateSsh;

public class EditeCertificateSshActivity extends AppCompatActivity {

    private Handler handler= new Handler();
    CertificateSsh CertificateSsh;

    CertificateController mController = new CertificateController(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.dev.doods.base.R.layout.activity_edite_certificate_ssh);
        Toolbar toolbar = (Toolbar) findViewById(com.dev.doods.base.R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getExtras();
        if(bundle!= null && bundle.containsKey("CertificateSsh"))
        {
            CertificateSsh =  (CertificateSsh)bundle.getSerializable("CertificateSsh");
        }

        setViews();

        FloatingActionButton fab = (FloatingActionButton) findViewById(com.dev.doods.base.R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               CertificateSsh.setPrivatekey(PrivateKeyView.getText().toString());
                CertificateSsh.setPublickey(PublicKeyView.getText().toString());
                CertificateSsh.setComment(CommentView.getText().toString());
                mController.SetSsh(CertificateSsh, null);
                finish();
            }
        });
    }

    EditText PrivateKeyView;
    EditText PublicKeyView;

    EditText CommentView;
    private void setViews()
    {
        PrivateKeyView = (EditText) findViewById(com.dev.doods.base.R.id.Et_Private_key);
        PublicKeyView = (EditText) findViewById(com.dev.doods.base.R.id.Et_Public_key);
        CommentView = (EditText) findViewById(com.dev.doods.base.R.id.Et_Comment);


        PrivateKeyView.setText(CertificateSsh.getPrivatekey());
        PublicKeyView.setText(CertificateSsh.getPublickey());
        CommentView.setText(CertificateSsh.getComment());
    }

}
