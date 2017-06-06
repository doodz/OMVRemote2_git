package com.dev.doods.omvremote2.Storage.FileSystems;

import android.app.Activity;

import Client.AsyncCall;
import Client.Callback;
import Client.JSONRPCClient;
import Client.JSONRPCParamsBuilder;
import Controllers.Abstractcontroller;

/**
 * Created by thiba on 06/09/2016.
 */
public class DisckController extends Abstractcontroller {


    public DisckController(Activity activity)  { super(activity); }

    public void getListDevies(Callback callBack)
    {

        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();
            //set parameters
            params.setService("DiskMgmt");
            params.setMethod("getList");
            params.addParam("start",0);
            params.addParam("limit",25);
            params.addParam("sortfield","devicefile");
            params.addParam("sortdir","ASC");
            this.enqueue(params,callBack);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void getListFilesSystems(Callback callBack)
    {

        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();
            //set parameters
            params.setService("FileSystemMgmt");
            params.setMethod("getList");
            params.addOption("updatelastaccess", "mFalse");
            params.addParam("start",0);
            params.addParam("limit",25);
            params.addParam("sortfield","devicefile");
            params.addParam("sortdir","ASC");
            this.enqueue(params,callBack);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
