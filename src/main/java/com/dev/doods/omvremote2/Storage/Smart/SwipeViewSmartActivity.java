package com.dev.doods.omvremote2.Storage.Smart;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;

import com.dev.doods.omvremote2.Plugins.Fail2ban.Fail2banJailsFragment;
import com.dev.doods.omvremote2.Plugins.Fail2ban.Fail2banSettingsFragment;
import com.dev.doods.omvremote2.R;

import java.util.ArrayList;
import java.util.Arrays;

import Adapters.SectionsPagerAdapter;
import Interfaces.OutputListener;
import OMV.Base.SwipeViewBaseActivity;

/**
 * Created by Ividata7 on 10/05/2017.
 */

public class SwipeViewSmartActivity extends SwipeViewBaseActivity  implements OutputListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("S.M.A.R.T");
    }

    @Override
    public  void initPagetAdapter()
    {
        setContentView(R.layout.activity_swipe_view_base);
        java.util.ArrayList<Fragment> items =  new ArrayList<Fragment>(
                Arrays.asList(new SmartSettingsFragment(),new SmartDevicesFragment(),new SmartScheduledTestsFragment()));
        ArrayList<String> titles =  new ArrayList<String>(
                Arrays.asList("Settings", "Devices","Scheduled test"));
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(),titles,items);
    }

    @Override
    public void OnFinich() {

    }

    @Override
    public void OnCanceled() {

    }

    @Override
    public void OnStarted() {

    }
}
