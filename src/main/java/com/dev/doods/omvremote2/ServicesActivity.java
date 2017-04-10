package com.dev.doods.omvremote2;

import android.graphics.Color;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import org.acra.ACRA;

import java.util.HashMap;
import java.util.Map;

import Controllers.ServicesController;
import Interfaces.IUpdateFragment;
import Interfaces.OnFragmentInteractionListener;
import OMV.Base.AppCompatBaseActivity;


public class ServicesActivity extends AppCompatBaseActivity implements OnFragmentInteractionListener {


    private ServicesController mController ;


    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private FloatingActionButton fabSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);

        mController = new ServicesController(this);

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

        fabSave = (FloatingActionButton) findViewById(R.id.fabSave);
        fabSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int i = mViewPager.getCurrentItem();
                Fragment frag =  mSectionsPagerAdapter.getFragment(i);
                if(frag instanceof IUpdateFragment) {
                    IUpdateFragment iFrag= (IUpdateFragment)frag;
                    iFrag.Update();

                }
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_services, menu);
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


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private Map<Integer, String> mFragmentTags;
        private FragmentManager mFragmentManager;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
            mFragmentManager = fm;
            mFragmentTags = new HashMap<Integer, String>();
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new OMVFragment.SSHFragment();
                case 1:
                    return new OMVFragment.NFSFragment();
                case 2:
                    return new OMVFragment.TFTPFragment();
                case 3:
                    return new OMVFragment.SNMPFragment();
                case 4:
                    return new OMVFragment.SMBFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 5 total pages.
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SSH";
                case 1:
                    return "NFS";
                case 2:
                    return "TFTP";
                case 3:
                    return "SNMP";
                case 4:
                    return "SMB";
            }
            return null;
        }
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Object obj = super.instantiateItem(container, position);
            if (obj instanceof Fragment) {
                // record the fragment tag here.
                Fragment f = (Fragment) obj;
                String tag = f.getTag();
                mFragmentTags.put(position, tag);
            }
            return obj;
        }

        public Fragment getFragment(int position) {
            String tag = mFragmentTags.get(position);
            if (tag == null)
                return null;
            return mFragmentManager.findFragmentByTag(tag);
        }
    }
}
