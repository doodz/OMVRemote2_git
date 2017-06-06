package com.dev.doods.omvremote2.System;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

import Client.Callback;
import Client.JSONRPCParamsBuilder;
import Controllers.Abstractcontroller;
import Models.Cron;

/**
 * Created by thiba on 19/12/2016.
 */

public class CronController  extends Abstractcontroller {

    public static List<String> TimeExecVal = new ArrayList<String>(){{add("Certain date");add("Hourly");
        add("Daily");add("Weekly");        add("Monthly");add("Yearly");        add("At reboot");}};

    public static List<String> TimeExecValServer = new ArrayList<String>(){{add("exactly");add("hourly");
        add("daily");add("weekly");        add("monthly");add("yearly");        add("reboot");}};





    public CronController(Activity activity) {
        super(activity);
    }

    public void GetCronList(Callback callBack)
    {
        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();
            //set parameters
            params.setService("Cron");
            params.setMethod("getList");
            params.addParam("limit",25);
            params.addParam("sortdir","ASC");
            params.addParam("sortfield","enable");
            params.addParam("start",0);
            params.addParam("type", "[\"userdefined\"]");
            this.enqueue(params,callBack);
        }
        catch (Exception e)
        {
            e.printStackTrace();

        }
    }

    public void EnumerateAllUsers(Callback callBack)
    {
        //List<OmvUser>();
        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();
            //set parameters
            params.setService("UserMgmt");
            params.setMethod("enumerateAllUsers");
            this.enqueue(params,callBack);
        }
        catch (Exception e)
        {
            e.printStackTrace();

        }
    }


    public void setCron(Cron cron, Callback callBack)
    {

        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();
            //set parameters
            params.setService("Cron");
            params.setMethod("set");

            params.addParam("command",cron.getCommand());
            params.addParam("comment",cron.getComment());
            params.addParam("dayofmonth",cron.getDayofmonth());
            params.addParam("dayofweek",cron.getDayofweek());
            params.addParam("enable", cron.getEnable()?"mTrue":"mFalse");
            params.addParam("everyndayofmonth",cron.getEveryndayofmonth()?"mTrue":"mFalse");
            params.addParam("everynhour",cron.getEverynhour()?"mTrue":"mFalse");
            params.addParam("everynminute",cron.getEverynminute()?"mTrue":"mFalse");
            params.addParam("execution",cron.getExecution());
            params.addParam("hour",cron.getHour());
            params.addParam("minute",cron.getMinute());
            params.addParam("month",cron.getMonth());
            params.addParam("sendemail",cron.getSendemail()?"mTrue":"mFalse");
            params.addParam("type","userdefined");
            params.addParam("username", cron.getUsername());
            params.addParam("uuid",(cron.getUuid() == null ||cron.getUuid() == "")? "undefined":cron.getUuid() );

            this.enqueue(params,callBack);
        }
        catch (Exception e)
        {
            e.printStackTrace();

        }

    }


    public void deleteCron(String uuid, Callback callBack)
    {
        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();
            //set parameters
            params.setService("Cron");
            params.setMethod("delete");
            params.addParam("uuid",uuid);
            this.enqueue(params,callBack);
        }
        catch (Exception e)
        {
            e.printStackTrace();

        }
    }

    public void executeCron(String uuid, Callback callBack)
    {
        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();
            //set parameters
            params.setService("Cron");
            params.setMethod("execute");
            params.addParam("uuid",uuid);
            this.enqueue(params,callBack);
        }
        catch (Exception e)
        {
            e.printStackTrace();

        }
    }

}
