package Adapters;

/**
 * Created by thiba on 12/11/2016.
 */


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public Map<Integer, String> mFragmentTags;
    private FragmentManager mFragmentManager;

    private ArrayList<String> mTitles;
    private ArrayList<Fragment> mItems;

    public SectionsPagerAdapter(FragmentManager fm, ArrayList<String> titles, ArrayList<Fragment> items) {
        super(fm);
        mFragmentManager = fm;
        mFragmentTags = new HashMap<Integer, String>();

        mTitles = titles;
        mItems = items;
    }

    @Override
    public Fragment getItem(int position) {


        if(mItems.size() > position)
           return mItems.get(position);
        else
            return null;
    }

    @Override
    public int getCount() {

        return mItems.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(mTitles.size() > position)
            return mTitles.get(position);
        else
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