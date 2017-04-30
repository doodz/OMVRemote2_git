package com.dev.doods.omvremote2.Plugins.Fail2ban;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;

import com.dev.doods.omvremote2.R;

import java.util.ArrayList;
import java.util.Arrays;

import Adapters.SectionsPagerAdapter;
import OMV.Base.SwipeViewBaseActivity;

/**
 * Created by Ividata7 on 26/04/2017.
 */

public class SwipeViewFail2banActivity extends SwipeViewBaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Fail2ban");

    }

    @Override
    public  void initPagetAdapter()
    {
        setContentView(R.layout.activity_swipe_view_base);
        java.util.ArrayList<Fragment> items =  new ArrayList<Fragment>(
                Arrays.asList(new Fail2banSettingsFragment(),new Fail2banJailsFragment()));
        ArrayList<String> titles =  new ArrayList<String>(
                Arrays.asList("Settings", "Jails"));
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(),titles,items);
    }

}
