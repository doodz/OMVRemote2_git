package com.dev.doods.omvremote2;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.DhcpInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Client.Host;
import utils.Util;

public class SearchHostActivity extends AppCompatActivity {

    //private Button mSkipView;
    private FloatingActionButton fab;
    private ProgressBar mProgressBar;
    private ListView mInetAddressView;
    private List<InetAddress> lst  = new ArrayList<InetAddress>();
    private ArrayAdapter<InetAddress> mAdapter;
    private static final int MY_PERMISSIONS_REQUEST_WIFI_STATE = 2;
    private AsyncTask mTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_host);
        mProgressBar = (ProgressBar) findViewById(R.id.pBAsync);
        mInetAddressView = (ListView) findViewById(R.id.LstInetAddress);

        mInetAddressView.setLongClickable(false);

        mAdapter = new ArrayAdapter<InetAddress>(this,
                android.R.layout.simple_list_item_1, lst);

        mInetAddressView.setAdapter(mAdapter);

        mInetAddressView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                InetAddress address = lst.get(i);

                Host host = new Host();
                host.setName( address.getHostName());
                host.setAddr(address.getHostAddress());
                String mac = Util.getMacFromArpCache(address.getHostAddress());
                if(!TextUtils.isEmpty(mac))
                {
                    host.setMacAddr(mac);
                }
                Bundle bundle = new Bundle();
                bundle.putSerializable("host", host);

                Intent intent = new Intent(SearchHostActivity.this, LoginByStepActivity.class);
                intent.putExtras(bundle);

                startActivity(intent);
            }
        });

        //mSkipView = (Button) findViewById(R.id.btSkip);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(SearchHostActivity.this, LoginActivity.class));
                startActivity(new Intent(SearchHostActivity.this, LoginByStepActivity.class));
            }
        });

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.INTERNET}, MY_PERMISSIONS_REQUEST_WIFI_STATE);


    }

    @Override
    public void onPause()
    {

        super.onPause();
        if(mTask !=null )
            mTask.cancel(true);
    }


    @Override
    public void onResume()
    {
        super.onResume();
        lst.clear();
        mAdapter.notifyDataSetChanged();

        if(mTask == null)
        {
            int permissionCheck = ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_WIFI_STATE);

            mTask = new getConnectedIps().execute();
        }
        else
        {


            boolean b = mTask.isCancelled();
            AsyncTask.Status sta = mTask.getStatus();
            if(mTask.isCancelled() || mTask.getStatus() == AsyncTask.Status.FINISHED)
            {
                int permissionCheck = ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_WIFI_STATE);
                mTask = new getConnectedIps().execute();
            }

        }
        //mTask = new getConnectedIps().execute();

    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WIFI_STATE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    //new Thread(new Runnable() {
                        //public void run() {
                            //new getConnectedIps();
                        //}
                    //}).start();

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
        }
    }



    private class getConnectedIps extends AsyncTask<Void, Integer, Void>
    {

        public String   s_dns1 ;
        public String   s_dns2;
        public String   s_gateway;
        public String   s_ipAddress;
        public String   s_leaseDuration;
        public String   s_netmask;
        public String   s_serverAddress;
        DhcpInfo d;
        WifiManager wifii;
        private boolean running = true;
        private InetAddress CurrentInetAddress;
        List<InetAddress> InetAddressList = Collections.synchronizedList(new ArrayList());
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            running = true;
            lst.clear();
        }

        @Override
        protected void onProgressUpdate(Integer... values){
            super.onProgressUpdate(values);

            mProgressBar.setProgress(values[0]);

            int pc = values[0];

            //int res = pc % 10 ;
            //int res2 = pc / 10;

            /*if(values[1] == 1)
            //if(res == 0)
            {
                if(CurrentInetAddress != null) {
                    lst.add(CurrentInetAddress);
                    mAdapter.notifyDataSetChanged();
                }
            }*/
            if(!InetAddressList.isEmpty()) {
                lst.addAll(InetAddressList);
                mAdapter.notifyDataSetChanged();
                InetAddressList.clear();
            }
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            int progress =0;

            wifii = (WifiManager) SearchHostActivity.this.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            d = wifii.getDhcpInfo();

            s_dns1 = "DNS 1: " + Util.intToIp(d.dns1);
            s_dns2 = "DNS 2: " + Util.intToIp(d.dns2);
            s_gateway = "Default Gateway: " + Util.intToIp(d.gateway);
            //s_ipAddress = "IP Address: " + String.valueOf(d.ipAddress);

            s_ipAddress = "IP Address: " + Util.intToIp(d.ipAddress);

            String ipStr =  Util.intToIp(d.ipAddress);

            s_leaseDuration = "Lease Time: " + String.valueOf(d.leaseDuration);
            s_netmask = "Subnet Mask: " + String.valueOf(d.netmask);
            s_serverAddress = "Server IP: " +Util.intToIp(d.serverAddress);
            String connections = "";
            InetAddress host;
            try
            {
                host = InetAddress.getByName(Util.intToIp(d.ipAddress));
                byte[] ip = host.getAddress();
                int j =0;
                for(int i = 1; i <= 254; i++)
                {
                    if(isCancelled()) return null;
                    ip[3] = (byte) i;
                    InetAddress address = InetAddress.getByAddress(ip);


                    if(address.getHostAddress().equals(ipStr))
                    {
                        continue;
                    }

                    if(address.isReachable(100))
                    {
                        address.getHostName();
                        address.getHostAddress();
                        System.out.println(address + " machine is turned on and can be pinged");
                        connections+= address+"\n";
                        j =1;
                        //lst.add(address);
                        CurrentInetAddress = address;
                        InetAddressList.add(address);

                    }
                    else if(!address.getHostAddress().equals(address.getHostName()))
                    {
                        System.out.println(address + " machine is known in a DNS lookup");
                    }

                    progress = (int)(((double)i/(double)254)*100);
                    synchronized (InetAddressList) {
                        publishProgress(progress, j);
                    }
                    j =0;
                    CurrentInetAddress = null;

                }
            }
            catch(UnknownHostException e1)
            {
                e1.printStackTrace();
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
            System.out.println(connections);

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

        }

    }

}
