package OMV.Classe;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.dev.doods.base.R;

import java.util.ArrayList;
import java.util.Arrays;

import Adapters.SectionsPagerAdapter;
import Interfaces.IFragmentInteraction;

/**
 * Created by thiba on 03/11/2016.
 */

public class SwipeViewBaseActivity extends AppCompatBaseActivity {



    public FloatingActionButton fabSave;
    public float fabSaveScaleX;
    public float fabSaveScaleY;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    public void setSectionsPagerAdapter(SectionsPagerAdapter adapter)
    {
        mSectionsPagerAdapter = adapter;
    }


    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    public  void initPagetAdapter()
    {
        setContentView(R.layout.activity_shared_folders);
        java.util.ArrayList<Fragment> items =  new ArrayList<Fragment>(
                Arrays.asList(new OMVFragment.SharedFoldersFragment(),
                        new OMVFragment.ResetPermissionsFragment(),new OMVFragment.SharedFolderInUseFragment()));
        ArrayList<String> titles =  new ArrayList<String>(
                Arrays.asList("Shared folders", "Reset permissions","Shared folder in use"));
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(),titles,items);
    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        initPagetAdapter();
        fabSave = (FloatingActionButton) findViewById(R.id.fab);
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            private int state = 0;
            private boolean isFloatButtonHidden = false;
            private int position = 0;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


                //Log.i("<=========>",""+fabSave.getScaleX());

                if (isFloatButtonHidden == false && state == 1 && positionOffset != 0.0) {
                    isFloatButtonHidden = true;
                    //scaleView(fabSave,fabSave.getScaleX(), 0.2f);
                    //fabSave.setScaleX(1f - positionOffset);
                    //fabSave.setScaleY(1f -positionOffset);
                    //hide floating action button
                    //swappingAway();
                }
            }

            @Override
            public void onPageSelected(int position) {
                //reset floating
                this.position = position;

                if (state == 2) {
                    //have end in selected tab
                    isFloatButtonHidden = false;
                    selectedTabs(position);

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //state 0 = nothing happen, state 1 = begining scrolling, state 2 = stop at selected tab.
                this.state = state;
                if (state == 0) {
                    isFloatButtonHidden = false;
                } else if (state == 2 && isFloatButtonHidden) {
                    //this only happen if user is swapping but swap back to current tab (cancel to change tab)
                    selectedTabs(position);
                }

            }
        });
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                int i = tab.getPosition();
                //if(i>0)
                selectedTabs(i );
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        fabSaveScaleX = fabSave.getScaleX();
        fabSaveScaleY = fabSave.getScaleY();

        fabSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int i = mViewPager.getCurrentItem();

                //mSectionsPagerAdapter.get

                Fragment frag =  mSectionsPagerAdapter.getFragment(i);
                if(frag instanceof IFragmentInteraction) {
                    IFragmentInteraction iFrag= (IFragmentInteraction)frag;
                    iFrag.Update();

                }
            }
        });

        // fabSave.setVisibility(View.GONE);

        //selectedTabs (tabLayout.getSelectedTabPosition());
    }


    private void selectedTabs(int tab)
    {

        //fabSave.show();

        //a bit animation of popping up.
        fabSave.clearAnimation();

        IFragmentInteraction frag = (IFragmentInteraction)  mSectionsPagerAdapter.getFragment(tab);

        if(frag == null) return;

        Animation animation = AnimationUtils.loadAnimation(this,frag.HaveUpdateMethode()? R.anim.pop_up:  R.anim.pop_down);

        fabSave.setVisibility(frag.HaveUpdateMethode()? View.VISIBLE:View.GONE);

        int imgId = frag.GetIconActionId();
        if(imgId != 0)
            fabSave.setImageResource(imgId);
        else
            fabSave.setImageResource(android.R.drawable.ic_menu_save);

        fabSave.startAnimation(animation);

        //you can do more task. for example, change color for each tabs, or custom action for each tabs.
    }



    @Override
    public void onResume() {
        super.onResume();
        if(!mSectionsPagerAdapter.mFragmentTags.isEmpty())
        {
            for(int i = 0; i<mSectionsPagerAdapter.getCount();i++)
            {
                Fragment f = mSectionsPagerAdapter.getFragment(i);
                if(f != null)
                    f.onResume();
            }


        }


    }
}
