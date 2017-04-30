package com.dev.doods.omvremote2.Plugins.Fail2ban;

import android.app.Activity;

import Client.Callback;
import Client.JSONRPCParamsBuilder;
import Controllers.Abstractcontroller;

/**
 * Created by Ividata7 on 26/04/2017.
 */

public class Fail2banController extends Abstractcontroller {


    public Fail2banController(Activity activity) {
        super(activity);
    }

    public void getJailList( Callback callBack)
    {

        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();
            //set parameters
            params.setService("Fail2ban");
            params.setMethod("getJailList");

            params.addParam("start",0);
            params.addParam("limit",25);
            params.addParam("sortfield","name");
            params.addParam("sortdir","ASC");

            this.enqueue(params,callBack);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void getSettings( Callback callBack)
    {

        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();
            //set parameters
            params.setService("Fail2ban");
            params.setMethod("getSettings");

            this.enqueue(params,callBack);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void setSettings(Fail2banSettings settings, Callback callBack)
    {
        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();
            //set parameters
            params.setService("Fail2ban");
            params.setMethod("setSettings");

            params.addParam("action",settings.getAction());
            params.addParam("bantime",settings.getBantime());
            params.addParam("destemail",settings.getDestemail());
            params.addParam("enable",settings.getEnable()?"mTrue":"mFalse");
            params.addParam("findtime",settings.getFindtime());
            params.addParam("ignoreip",settings.getIgnoreip());
            params.addParam("maxretry",settings.getMaxretry());

            this.enqueue(params,callBack);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void setJail(Jail jail, Callback callBack)
    {
        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();
            //set parameters
            params.setService("Fail2ban");
            params.setMethod("setJail");

            params.addParam("bantime",jail.getBantime());
            params.addParam("enable",jail.getEnable()?"mTrue":"mFalse");
            params.addParam("filter",jail.getFilter());
            params.addParam("logpath",jail.getLogpath());
            params.addParam("maxretry",jail.getMaxretry());
            params.addParam("name",jail.getName());
            params.addParam("port",jail.getPort());
            params.addParam("uuid",jail.getUuid());

            this.enqueue(params,callBack);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void deleteJail(String uuid,Callback callBack)
    {
        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();
            //set parameters
            params.setService("Fail2ban");
            params.setMethod("deleteJail");

            params.addParam("uuid",uuid);

            this.enqueue(params,callBack);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}
