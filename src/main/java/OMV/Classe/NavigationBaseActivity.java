package OMV.Classe;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.dev.doods.omvremote2.AboutActivity;
import com.dev.doods.omvremote2.CertificateActivity;
import com.dev.doods.omvremote2.CronActivity;
import com.dev.doods.omvremote2.FileSystemsActivity;
import com.dev.doods.omvremote2.HomeActivity;
import com.dev.doods.omvremote2.HostManagerActivity;
import com.dev.doods.omvremote2.LogsActivity;
import com.dev.doods.omvremote2.PackagesInfomationActivity;
import com.dev.doods.omvremote2.PluginsActivity;
import com.dev.doods.base.R;
import com.dev.doods.omvremote2.omv_extrasActivity;
import com.google.android.gms.ads.AdView;

import net.hockeyapp.android.metrics.MetricsManager;

/**
 * Created by Ividata7 on 19/03/2017.
 */

public class NavigationBaseActivity extends AppCompatBaseActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private AdView mBannerheader;
    private NavigationView _NavigationView;
    protected Toolbar _Toolbar;
    public int NavigationId = R.id.nav_home;
    private LinearLayout nav_header_layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MetricsManager.register(getApplication(),"7076800147a94538934dfa2e5e0ee2ad");
    }
    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        _NavigationView = (NavigationView) findViewById(R.id.nav_view);
        _NavigationView.setNavigationItemSelectedListener(this);
        _NavigationView.setCheckedItem(R.id.nav_cron);

        _Toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(_Toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, _Toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        //nav_header_layout = (LinearLayout)_NavigationView.findViewById(R.id.nav_header_layout);
        //nav_header_layout = (LinearLayout)findViewById(R.id.nav_header_layout);

       //mBannerheader = (AdView) findViewById(R.id.bannerheader);
        //mBannerheader =MyApplication.mAdViewSmallHaeder;


    }

    public void onPostResume()
    {
        super.onPostResume();
        //if(nav_header_layout != null)
        //nav_header_layout.addView(MyApplication.mAdViewSmallHaeder);

    }
    @Override
    public void onPause()
    {
        super.onPause();
        //if(nav_header_layout != null)
        //nav_header_layout.removeView(MyApplication.mAdViewSmallHaeder);
    }

    @Override
    protected void onResume() {
        super.onResume();
        _NavigationView.setCheckedItem(NavigationId);
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if  (id == R.id.nav_home)
        {
            startActivity(new Intent(this, HomeActivity.class));
        }
        else if(id == R.id.nav_update)
        {
            startActivity(new Intent(this, PackagesInfomationActivity.class));
        }
        else if(id == R.id.nav_disk)
        {
            startActivity(new Intent(this, FileSystemsActivity.class));
        }
        else if(id == R.id.nav_plugin)
        {
            startActivity(new Intent(this, PluginsActivity.class));
        }
        else if(id == R.id.nav_extra)
        {
            startActivity(new Intent(this, omv_extrasActivity.class));
        }
        else if(id == R.id.nav_host)
        {
            startActivity(new Intent(this, HostManagerActivity.class));
        }
        else if(id == R.id.nav_about)
        {
            startActivity(new Intent(this, AboutActivity.class));
        }
        else if (id == R.id.nav_certificate)
        {
            startActivity(new Intent(this, CertificateActivity.class));
        }
        else if (id == R.id.nav_logs)
        {
            startActivity(new Intent(this, LogsActivity.class));
        }
        else if (id == R.id.nav_cron)
        {
            startActivity(new Intent(this, CronActivity.class));
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
