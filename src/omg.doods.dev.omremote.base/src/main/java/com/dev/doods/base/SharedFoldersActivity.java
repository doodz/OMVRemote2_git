package com.dev.doods.base;

import android.graphics.Color;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.TextView;
import org.acra.ACRA;

import Interfaces.OnFragmentInteractionListener;
import OMV.Classe.SwipeViewBaseActivity;

public class SharedFoldersActivity extends SwipeViewBaseActivity implements OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_shared_folders, menu);
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


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onFragmentMessage(String msg)
    {

        Snackbar.make(fabSave, msg, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();

    }

    @Override
    public void onFragmentMessageError(String error)
    {

        Snackbar snackbar = Snackbar
                .make(fabSave, "an error occurred!", Snackbar.LENGTH_LONG)
                .setAction("Send Logs", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ACRA.getErrorReporter().handleException(null);
                    }
                });

        // Changing message text color
        snackbar.setActionTextColor(Color.RED);

        // Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.YELLOW);
        snackbar.show();

    }

}
