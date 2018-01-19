package com.dev.doods.omvremote2.Plugins;

import android.app.Activity;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import Client.AsyncCall;
import Client.Callback;
import Client.JSONRPCClient;
import Client.JSONRPCParamsBuilder;
import Controllers.Abstractcontroller;
import Models.PackageInformation;
import Models.Plugins;

/**
 * Created by thiba on 08/09/2016.
 */
public class PluginsController extends Abstractcontroller {


    public PluginsController(Activity activity){ super(activity); }
    public void enumeratePlugins(Callback callBack)
    {
        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();

            //set parameters
            params.setService("Plugin");
            params.setMethod("enumeratePlugins");

            params.addParam("limit",25);
            params.addParam("sortdir","mNull");
            params.addParam("sortfield","mNull");
            params.addParam("start",0);
            this.enqueue(params,callBack);
        }
        catch (Exception e)
        {
            e.printStackTrace();

        }
    }

    public void InstallPlugins(List<Plugins> plugins, Callback callBack)
    {
        // return filename
        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();

            //set parameters
            params.setService("Plugin");
            params.setMethod("install");

            List<String> lst = new ArrayList<String>() ;

            for (Plugins pack:plugins) {
                lst.add(pack.getName());
            }

            Gson gson = new Gson();
            String str = gson.toJson(lst);
            params.addParam("packages",str);
            this.enqueue(params,callBack);
        }
        catch (Exception e)
        {
            e.printStackTrace();

        }

    }

    public void RemovePlugins(List<Plugins> plugins, Callback callBack)
    {
        // return filename
        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();

            //set parameters
            params.setService("Plugin");
            params.setMethod("remove");

            List<String> lst = new ArrayList<String>() ;

            for (Plugins pack:plugins) {
                lst.add(pack.getName());
            }

            Gson gson = new Gson();
            String str = gson.toJson(lst);
            params.addParam("packages",str);
            this.enqueue(params,callBack);
        }
        catch (Exception e)
        {
            e.printStackTrace();

        }

    }

    public void getOutput(String filename,Callback callBack,int pos)
    {

        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();

            //set parameters
            params.setService("Exec");
            params.setMethod("getOutput");
            params.addParam("pos",pos);
            params.addParam("filename",filename);
            this.enqueue(params,callBack);
        }
        catch (Exception e)
        {
            e.printStackTrace();

        }
    }
}
