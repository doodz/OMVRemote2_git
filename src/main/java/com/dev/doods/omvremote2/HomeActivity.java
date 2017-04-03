package com.dev.doods.omvremote2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.innahema.collections.query.functions.Predicate;
import com.innahema.collections.query.queriables.Queryable;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import Adapters.ServicesAdapter;
import Client.Call;
import Client.CallbackImpl;
import Client.Host;
import Client.JSONRPCClient;
import Client.Response;
import Controllers.HomeController;
import Controllers.SystemController;
import DAL.HostsDAO;
import Interfaces.IYesNoListenerDialog;
import Models.Datum;
import Models.Errors;
import Models.Result;
import Models.SystemInformation;
import Models.Value;
import Deserializers.ValueDeserializer;
import OMV.Classe.NavigationBaseActivity;
import OMVFragment.Dialogs.YesNoDialog;
import utils.CheckDirty;
import utils.Util;

public class HomeActivity extends NavigationBaseActivity implements View.OnClickListener, IYesNoListenerDialog {
    private HomeController controller;
    private SystemController mSystemController = new SystemController(this);


    FloatingActionButton fab;
    FloatingActionButton fabInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        NavigationId = R.id.nav_home;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homet);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        checkHost();

        /*
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        */
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);
        registerForContextMenu(fab);
        controller = new HomeController(this);

        fabInfo = (FloatingActionButton) findViewById(R.id.fabInfo);
        fabInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(IsFinalized(true))
                    startActivity(new Intent(HomeActivity.this, StatisticsScrollingActivity.class));
            }
        });
        getInfo();
        new CheckDirty(HomeActivity.this).Check();
    }


    private void checkHost()
    {
        HostsDAO datasource = new HostsDAO(this);
        datasource.open();

        List<Host> lst = datasource.getAllHosts();
        if(lst.isEmpty() || lst.size() <= 0)
        {
            if(MyApplicationBase.light)
                startActivity(new Intent(HomeActivity.this, com.dev.doods.omvremote2.AdMobActivity.class));
            else
                startActivity(new Intent(HomeActivity.this, com.dev.doods.omvremote2.SearchHostActivity.class));
        }

        SharedPreferences sharedPref = getSharedPreferences("electedHost", Context.MODE_PRIVATE);
        final long hostId = sharedPref.getLong("electedHostId",0);


        JSONRPCClient jsonRpc = JSONRPCClient.getInstance();
        if(!jsonRpc.HaveHost() && lst.size() > 0) {

           Host h = Queryable.from(lst).firstOrDefault(new Predicate<Host>() {
                @Override
                public boolean apply(Host element) {
                    return element.getId() == hostId;
                }
            });

            if(h != null)
                jsonRpc.SetHost(h);
            else
                jsonRpc.SetHost(lst.get(0));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_power, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.action_reboot)
        {
            mSystemController.Reboot(null);
            this.showInfo(getString(R.string.Reboot_send));
        }
        else if(id == R.id.action_shutdown)
        {
            mSystemController.Shutdown(null);
            this.showInfo(getString(R.string.Shutdown_send));
        }
        else if(id == R.id.action_standby)
        {
            mSystemController.Standby(null);
            this.showInfo(getString(R.string.Standby_send));
        }else if(id == R.id.action_wakeup)
        {
            JSONRPCClient jsonRpc = JSONRPCClient.getInstance();
            Host h = jsonRpc.GetHost();
            if(h.getMacAddr().equals("") || h.getMacAddr() == null)
            {
                String strMac = Util.getMacFromArpCache(h.getAddr());

                if(strMac == null)
                {
                    DialogFragment dialog = new YesNoDialog();
                    Bundle args = new Bundle();

                    args.putString("title",getString(R.string.no_mac));
                    args.putString("message",getString(R.string.set_mac));
                    dialog.setArguments(args);
                    //dialog.setTargetFragment(OMVSystemActivity.this, YesNoDialog.YES_NO_CALL);
                    dialog.show(getSupportFragmentManager(), "tag");
                     return true;
                }
                h.setMacAddr(strMac);
                HostsDAO datasource = new HostsDAO(this);
                datasource.open();
                datasource.UpdateHost(h);
                datasource.close();

            }

            mSystemController.Wakeup();
            this.showInfo(getString(R.string.Wakeup_send));
        }

        return super.onContextItemSelected(item);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {




        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            if(IsFinalized(true))
            startActivity(new Intent(HomeActivity.this, OMVSystemActivity.class));
            //startActivity(new Intent(HomeActivity.this, HomeSettingsActivity.class));
            return true;
        }
        else if(id == R.id.action_power){
                this.openContextMenu(fab);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        Log.v("MainActivity","Onclick from MainActivity");

        if(v.getId() == R.id.fab)
        {

            getInfo();
        }
        else if(v.getId() == R.id.RelativeLayout_Info_Server)
        {

            if(i == 5) {
                ShowDebug();
                i=0;
            }
            i++;
        }
    }
    int i = 0;
    private void ShowDebug()
    {
        startActivity(new Intent(HomeActivity.this, com.dev.doods.omvremote2.TestActivity.class));
    }

    private void getInfo()
    {

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.rotate_indefinitely);
        fab.startAnimation(animation);

        controller.GetSystemInformation(new CallbackImpl(this){


            @Override
            public void onFailure(Call call, Exception e) {
                e.printStackTrace();
                ShowSnackError(e.getMessage(),false);
                mHandler.post(new Runnable(){
                    public void run() {

                        fab.clearAnimation();
                    }
                });

            }

            @Override
            public void OnOMVServeurError(Call call, Errors error) {

                ShowSnackError(error.getMessage(),false);
                mHandler.post(new Runnable(){
                    public void run() {

                        fab.clearAnimation();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException, InterruptedException{
                super.onResponse(call,response);
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    gsonBuilder.registerTypeAdapter(Value.class, new ValueDeserializer());
                    //gsonBuilder.registerTypeAdapter(SystemInformation.class, new SystemInformationDeserializer());
                    Gson gson = gsonBuilder.create();
                    JsonElement j = response.GetJsonResult();

                    TypeToken tt = new TypeToken<ArrayList<SystemInformation>>(){};
                    Type t =  tt.getType();
                    // ArrayList<LinkedTreeMap<String,String>> oo =Util.FromJson(j,Object.class);
                    final  ArrayList<SystemInformation> res = gson.fromJson(j,t);
                mHandler.post(new Runnable(){
                        public void run() {
                            showSystemInformation(res);
                            fab.clearAnimation();
                        }
                    });
            }
        });

        controller.GetStatusServices(new CallbackImpl(this){


            @Override
            public void onResponse(Call call, Response response) throws IOException,InterruptedException {
                super.onResponse(call,response);
                final Result<Datum> res = response.GetResultObject(new TypeToken<Result<Datum>>(){});
                mHandler.post(new Runnable(){
                        public void run() {
                            showStatusServices(res.getData());
                        }
                    });



            }
        });

    }

    private final List<Datum> LstServices = new ArrayList<Datum>();

    private void showStatusServices(List<Datum> res)
    {
        LstServices.clear();
        LstServices.addAll(res);
        ListView listView = (ListView) findViewById(R.id.StatusServices);
        listView.setAdapter(new ServicesAdapter(HomeActivity.this,LstServices));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Datum item = LstServices.get(position);

                Intent intent = new Intent(HomeActivity.this, com.dev.doods.omvremote2.ServicesActivity.class);
                intent.putExtra("ItemPosition",position);
                intent.putExtra("ItemName",item.getName());
                intent.putExtra("ItemTitle",item.getTitle());



                if(item.getName().equalsIgnoreCase("virtualbox"))
                {
                    intent = new Intent(HomeActivity.this, com.dev.doods.omvremote2.VirtualboxActivity.class);

                }

                if(item.getName().equalsIgnoreCase("virtualbox"))
                {
                    intent = new Intent(HomeActivity.this, com.dev.doods.omvremote2.VirtualboxActivity.class);

                }

                startActivity(intent);
            }
        });

    }

    private void showSystemInformation(List<SystemInformation> res)
    {
        for (SystemInformation d: res) {
            populateTexboxForSystemInformation(d.getName(),d.getValue().getText());
        }

    }

    private void populateTexboxForSystemInformation(String tb,Object obj)
    {
        String val = obj.toString();
        TextView tv = null;
        switch (tb.replace(' ','_'))
        {
            case "Hostname":
                tv = (TextView)findViewById(R.id.HostName);
                tv.setText(val);
                break;
            case "Version":
                tv = (TextView)findViewById(R.id.Version);
                JSONRPCClient jsonRpc = JSONRPCClient.getInstance();
                jsonRpc.GetHost().setVersion(val.contains("(Erasmus)")? 3 : 2);

                tv.setText(val);
                break;
            case "Processor":
                tv = (TextView)findViewById(R.id.Processor);
                //tv.setText(val);
                break;
            case "Kernel":
                tv = (TextView)findViewById(R.id.Kernel);
                tv.setText(val);
                break;
            case "System_time":
                tv = (TextView)findViewById(R.id.System_time);
                tv.setText(val);
                break;
            case "Uptime":
                tv = (TextView)findViewById(R.id.Uptime);
                tv.setText(val);
                break;
            case "Load_average":
                tv = (TextView)findViewById(R.id.Load_average);
                tv.setText(val);
                break;
            case "CPU_usage":
                tv = (TextView)findViewById(R.id.CPU_usage);
                tv.setText(val);
                break;
            case "Memory_usage":
                tv = (TextView)findViewById(R.id.Memory_usage);
                tv.setText(val);
                break;
            default:
                Log.i("MainActivity","unknown property name : "+tb.replace(' ','_'));
        }
    }


    @Override
    public void onResume()
    {
        super.onResume();
        this.i = 0;
    }

    @Override
    public void onYesNoActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode ==  Activity.RESULT_OK)
        {
            JSONRPCClient jsonRpc = JSONRPCClient.getInstance();
            Host mHost = jsonRpc.GetHost();
            Bundle bundle = new Bundle();
            bundle.putSerializable("host", mHost);
            Intent intent = new Intent(HomeActivity.this, com.dev.doods.omvremote2.LoginActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }

    }
}
