package com.dev.doods.omvremote2;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.SSLCertificateSocketFactory;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.net.SocketFactory;

import Client.Host;
import Controllers.LoginController;
import DAL.HostsDAO;
import utils.InputFilterMinMax;


/**
 * A login screen that offers login via email/password.
 */

public class LoginActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    /**
     * Id to identity REQUEST_INTERNET permission request.
     */
    private static final int MY_PERMISSIONS_REQUEST_INTERNET = 1;
    private LoginController controller;

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;
    private CheckURLTask mUrlTask = null;
    // UI references.
    private EditText mHostNameView;

    private EditText mUrlView;
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private EditText mPortView;
    private Switch mSSLView;
    private View mProgressView;
    private View mLoginFormView;

    private EditText WolPortView;
    private EditText MacView;
    private EditText mAddrBroadcastView;
    private Button mEmailSignInButton;

    private Host mHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        controller = new LoginController(this);

        setContentView(R.layout.activity_login);

        // Check for permissions
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET);
        // if permission denied, request the permission.
        if (permissionCheck == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.INTERNET}, MY_PERMISSIONS_REQUEST_INTERNET);

        }
        // Set up the login form.
        mHostNameView = (EditText) findViewById(R.id.hostName);
        mUrlView = (EditText) findViewById(R.id.host);
        mUrlView.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View view, boolean focus) {
                if (mUrlTask != null || focus) return;
                String url = mUrlView.getText().toString();
                Integer port =Integer.parseInt(mPortView.getText().toString());
                boolean ssl = mSSLView.isChecked();
                mUrlTask = new CheckURLTask(url,port,ssl);
                try {
                    boolean b = mUrlTask.execute((Void) null).get();


                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }


            }
        });

        ;
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);



        mPortView = (EditText) findViewById(R.id.etPort);

        mPortView.setFilters(new InputFilter[]{ new InputFilterMinMax(1, 99999) });
        mSSLView= (Switch) findViewById(R.id.swithEnableSSL);

        mSSLView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b)
                {
                    if(mPortView.getText().toString().equals("80"))
                    {
                        mPortView.setText("443");
                    }

                }
                else
                {
                    if(mPortView.getText().toString().equals("443"))
                    {
                        mPortView.setText("80");
                    }
                }
            }
        });


        WolPortView = (EditText) findViewById(R.id.etWolPort);
        WolPortView.setFilters(new InputFilter[]{ new InputFilterMinMax(1, 99999) });
        MacView = (EditText) findViewById(R.id.etMac);
        mAddrBroadcastView= (EditText) findViewById(R.id.AddrBroadcast);
        Bundle bundle = getIntent().getExtras();
        if(bundle != null && bundle.containsKey("host"))
        {
            mHost = (Host) bundle.getSerializable("host");
        }
        else
            mHost = new Host();
        PopulateEditTexts();
        //filters = (FilterPlugin) bundle.getSerializable("FilterPlugin");
    }


    private void PopulateEditTexts()
    {
        if(false) {
            SharedPreferences sharedPref = getSharedPreferences("LoginPref", Context.MODE_PRIVATE);
            mUrlView.setText(sharedPref.getString("hostName", null));
            mEmailView.setText(sharedPref.getString("userName", null));
            mPasswordView.setText(sharedPref.getString("password", null));
            mPortView.setText(Integer.toString(sharedPref.getInt("port", 80)));
            mSSLView.setChecked(sharedPref.getBoolean("ssl", false));

        }
        else
        {
            mHostNameView.setText(mHost.getName());
            mUrlView.setText(mHost.getAddr());
            mEmailView.setText(mHost.getUser());
            mPasswordView.setText(mHost.getPass());
            mPortView.setText(Integer.toString(mHost.getPort()));
            mSSLView.setChecked(mHost.getSll());
            WolPortView.setText(Integer.toString(mHost.getWolport()));
            MacView.setText(mHost.getMacAddr());
            mAddrBroadcastView.setText(mHost.getAddrBroadcast());
        }

    }


    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_INTERNET: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay!

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
        }
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);
        mPortView.setError(null);
        // Store values at the time of the login attempt.
        String name = mHostNameView.getText().toString();
        String url = mUrlView.getText().toString();
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean ssl = mSSLView.isChecked();
        boolean cancel = false;
        View focusView = null;

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        }

        if (TextUtils.isEmpty(name)) {
            mHostNameView.setError(getString(R.string.error_field_required));
            focusView = mHostNameView;
            cancel = true;
        }

        if (TextUtils.isEmpty((mPortView.getText().toString())))
        {
            mPortView.setError(getString(R.string.error_field_required));
            focusView = mPortView;
            cancel = true;
        }



        // check for a valid url.
        //if (!URLUtil.isNetworkUrl(url)) {
          //  mUrlView.setError("This host is invalid");
            //cancel = true;
        //}

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            if (focusView != null)
                focusView.requestFocus();
            return;
        }
        Integer port =Integer.parseInt(mPortView.getText().toString());
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(name,url,email, password,port,ssl);
            mAuthTask.execute((Void) null);


    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    /**
     * Represents an asynchronous ping task used for check url availability
     */
    public class CheckURLTask extends AsyncTask<Void, Void, Boolean> {


        private String mUrl;
        private int mPort;
        private Boolean mSSL;
        private String mError;
        CheckURLTask(String url,int port,boolean ssl) {
            mUrl = url;
            mPort = port;
            mSSL  = ssl;
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            boolean ok = false;

            //if(!URLUtil.isValidUrl("http://"+mUrl))
            //{
                if(mUrl.contains("http")) {
                    mError = "Remove http(s)://";
                    mUrlTask = null;
                    return ok;
                }

            Socket ss = null;
            try {

                if(mSSL)
                    ss =(Socket) SSLCertificateSocketFactory.getDefault().createSocket();
                else
                    ss = SocketFactory.getDefault().createSocket();
                ss.connect(new InetSocketAddress(mUrl, mPort), 1000);
                ss.close();
                ok = true;
            } catch (UnknownHostException e) {
                mError = "Can not resolve host";
                Log.i("LoginActivity", "Can not resolve host : " + mUrl);
                e.printStackTrace();
                return ok;
            } catch (IOException e) {
                mError = "Can not connect to server";
                Log.i("LoginActivity", "Can not connect to server : " + mUrl);
                e.printStackTrace();
                return ok;
            } finally {
                if (!ok) {
                    Log.i("LoginActivity", mError+" : " + mUrl);

                }
                mUrlTask = null;

                return ok;
            }
        }

        @Override
        protected void onCancelled() {
            mUrlTask = null;
            showProgress(false);
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if(!success)
                mUrlView.setError(mError);
            mEmailSignInButton.setEnabled(success);
        }

    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mName;
        private final String mUser;
        private final String mPassword;
        private final String mUrl;
        private final Integer mPort;
        private final boolean mSSL;

        private String mError = null;

        UserLoginTask(String name,String url,String user, String password,Integer port,boolean ssl) {
            mName = name;
            mUrl = url;
            mUser = user;
            mPassword = password;
            mPort = port;
            mSSL = ssl;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            // network access.
            JSONObject jsonObj = controller.connectionTask(mName,mUrl,mUser,mPassword,mPort,mSSL);

            if(jsonObj == null)
            {
                mError = "an error occurred";
            }
            else if( !jsonObj.isNull("responseCode"))
                mError = "Unable to connect to the API";

            else if (!jsonObj.isNull("username")) {

                try {
                    if(!jsonObj.getBoolean("authenticated"))
                        mError = "Incorrect username or password";
                } catch (JSONException e) {
                    e.printStackTrace();
                    mError = "Error";
                }
            }
            // TODO: register the new account here.
            return mError == null;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {

                controller.SetHost(mName, mUrl,mUser,mPassword,mPort,mSSL);
                HostsDAO datasource = new HostsDAO(getApplicationContext());
                datasource.open();

                mHost.setName(mHostNameView.getText().toString());
                mHost.setAddr(mUrlView.getText().toString());
                mHost.setUser(mEmailView.getText().toString());
                mHost.setPass(mPasswordView.getText().toString());
                mHost.setPort(Integer.parseInt(mPortView.getText().toString()));
                mHost.setSll(mSSLView.isChecked());
                mHost.setWolport(Integer.parseInt(WolPortView.getText().toString()));
                mHost.setMacAddr(MacView.getText().toString());
                mHost.setAddrBroadcast(mAddrBroadcastView.getText().toString());
                if(mHost.getId() == 0)
                    datasource.createHost(mHost);
                else
                    datasource.UpdateHost(mHost);
                datasource.close();
                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                //finish();
            } else {
                mEmailView.setError(mError);
                mEmailView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}