package com.dev.doods.omvremote2;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import org.acra.ACRA;
import java.io.IOException;

import Client.Call;
import Client.Callback;
import Client.Response;
import Controllers.ConfigController;
import Interfaces.IYesNoListenerDialog;
import Interfaces.OnFragmentInteractionListener;
import Models.Errors;
import OMVFragment.Dialogs.YesNoDialog;

public class OMVSystemActivity extends AppCompatActivity implements OnFragmentInteractionListener, IYesNoListenerDialog {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    Handler handler = new Handler();
    ConfigController mConfigController = new ConfigController(this);

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_omv_system);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        mConfigController.isDirty(new Callback() {
            @Override
            public void onFailure(Call call, Exception e) {

            }

            @Override
            public void OnOMVServeurError(Call call, Errors error) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException, InterruptedException {

               boolean b = response.GetResultObject(new TypeToken<Boolean>(){});

                if(b)
                {
                    ShowPopUpDirty();
                }
            }
        });

    }


    private void ShowPopUpDirty()
    {

        DialogFragment dialog = new YesNoDialog();
        Bundle args = new Bundle();
        args.putString("title", getString(R.string.ApplyChanges));
        args.putString("message", getString(R.string.ApplyChangesMessage));
        dialog.setArguments(args);
        //dialog.setTargetFragment(OMVSystemActivity.this, YesNoDialog.YES_NO_CALL);
        dialog.show(getSupportFragmentManager(), "tag");



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_omvsystem, menu);
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

        Snackbar.make(mViewPager, msg, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();

    }

    @Override
    public void onFragmentMessageError(String error)
    {

        Snackbar snackbar = Snackbar
                .make(mViewPager, "an error occurred!", Snackbar.LENGTH_LONG)
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

    @Override
    public void onYesNoActivityResult(int requestCode, int resultCode, Intent data) {

       if(resultCode ==  Activity.RESULT_OK)
       {
           mConfigController.applyChangesBg(null);
       }
       else
           finish();


    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position) {
                case 0:
                    return new OMVFragment.WebAdministrationFragment();
                case 1:
                    return new OMVFragment.DateAndTimeFragment();
                case 2:
                    return new OMVFragment.NetworkFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "General Settings";
                case 1:
                    return "Date & time";
                case 2:
                    return "Network";
            }
            return null;
        }
    }
}
