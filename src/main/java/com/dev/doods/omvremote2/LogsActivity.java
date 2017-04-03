package com.dev.doods.omvremote2;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.content.Context;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.content.res.Resources.Theme;

import android.widget.TextView;

import com.google.gson.reflect.TypeToken;

import net.hockeyapp.android.metrics.MetricsManager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Adapters.LogRowAdapter;
import Client.Call;
import Client.CallbackImpl;
import Client.DownloadLogsAsyncTask;
import Client.Response;
import Controllers.LogsController;
import Controllers.ServicesController;
import Models.Errors;
import Models.LogRow;
import Models.Result;
import OMV.Classe.NavigationBaseActivity;

public class LogsActivity extends NavigationBaseActivity {

    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 42;

    LogsController mController = new LogsController(this);
    ServicesController mServicesController = new ServicesController(this);
    RecyclerView mRecyclerLogs;
    List<LogRow> lst = new ArrayList<LogRow>();
    LogRowAdapter mLogRowAdapter;

    private List<String> mLogsId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        NavigationId = R.id.nav_logs;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logs);

        mLogsId = (mController.GetApiVersion() == 3)?LogsController.LogsIdsV3:LogsController.LogsIds;
        // Setup spinner
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(new MyAdapter(
                _Toolbar.getContext(),mLogsId));

        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // When the given dropdown item is selected, show its contents in the
                // container view.

                String idstr = mLogsId.get(position);
                getLogs(idstr);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(IsFinalized(true)) {

                    final File testDirectory = new File(Environment.getExternalStorageDirectory() + "/Plugins");
                    if (!testDirectory.exists()) {
                        testDirectory.mkdir();
                    }

                    final File ff = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                    int permissionCheck = ContextCompat.checkSelfPermission(getApplicationContext(),
                            Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    // if permission denied, request the permission.
                    if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                        ActivityCompat.requestPermissions(LogsActivity.this,
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);

                    }


                    Map<String, String> properties = new HashMap<>();
                    properties.put("logsIdStr", currentlogsIdStr);
                    properties.put("date", new java.util.Date().toString());
                    Map<String, Double> measurements = new HashMap<>();
                    measurements.put("Downloaded", 1d);
                    MetricsManager.trackEvent("GET_LOGS_FILE", properties, measurements);

                    new DownloadLogsAsyncTask(LogsActivity.this.getApplicationContext(), currentlogsIdStr + ".log").execute(currentlogsIdStr);
                }

            }
        });


        mRecyclerLogs = (RecyclerView) findViewById(R.id.RecyclerLogs);
        mLogRowAdapter = new LogRowAdapter(this,lst);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        mRecyclerLogs.setLayoutManager(mLayoutManager);
        mRecyclerLogs.setItemAnimator(new DefaultItemAnimator());
        mRecyclerLogs.setAdapter(mLogRowAdapter);
    }



    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: {
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

    String currentlogsIdStr;
    private void getLogs(String logsIdStr)
    {
        currentlogsIdStr = logsIdStr;
        mController.getLogsList(logsIdStr, new CallbackImpl(this) {

            @Override
            public void OnOMVServeurError(Call call, Errors error) {

                ShowSnackError(error.getMessage(),false);
                mHandler.post(new Runnable(){
                    public void run() {
                        showLogs(new ArrayList<LogRow>());
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException, InterruptedException {
                super.onResponse(call,response);
                final Result<LogRow> res = response.GetResultObject(new TypeToken<Result<LogRow>>(){});
                mHandler.post(new Runnable(){
                    public void run() {
                        showLogs(res.getData());
                    }
                });
            }
        });

    }

    private void showLogs(List<LogRow> logs)
    {
        lst.clear();
        lst.addAll(logs);
        mLogRowAdapter.notifyDataSetChanged();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_logs, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private static class MyAdapter extends ArrayAdapter<String> implements ThemedSpinnerAdapter {
        private final ThemedSpinnerAdapter.Helper mDropDownHelper;

        public MyAdapter(Context context, List<String> objects) {
            super(context, android.R.layout.simple_list_item_1, objects);
            mDropDownHelper = new ThemedSpinnerAdapter.Helper(context);
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            View view;

            if (convertView == null) {
                // Inflate the drop down using the helper's LayoutInflater
                LayoutInflater inflater = mDropDownHelper.getDropDownViewInflater();
                view = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
            } else {
                view = convertView;
            }

            TextView textView = (TextView) view.findViewById(android.R.id.text1);
            textView.setText(getItem(position));

            return view;
        }

        @Override
        public Theme getDropDownViewTheme() {
            return mDropDownHelper.getDropDownViewTheme();
        }

        @Override
        public void setDropDownViewTheme(Theme theme) {
            mDropDownHelper.setDropDownViewTheme(theme);
        }
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_logs, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }
}
