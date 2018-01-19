package com.dev.doods.omvremote2.Storage.Smart;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

import Client.Callback;
import Client.JSONRPCParamsBuilder;
import Controllers.Abstractcontroller;

/**
 * Created by Ividata7 on 27/01/2017.
 */

public class SmartController extends Abstractcontroller {


    public static List<String> PowerModeList = new ArrayList<String>(){{add("Never");add("Sleep");
        add("Standby");add("Idle");
    }};

    public static List<String> ScheduledTestTypeList = new ArrayList<String>(){{add("Short self-test");add("Long self-test");
        add("Conveyance self-test");add("Offline immediate test");
    }};

    public SmartController(Activity activity) {
        super(activity);
    }



    public void getSettings(Callback callBack)
    {
        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();
            //set parameters
            params.setService("Smart");
            params.setMethod("getSettings");


            this.enqueue(params,callBack);
        }
        catch (Exception e)
        {
            e.printStackTrace();

        }
    }

    public void getListDevices(Callback callBack)
    {
        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();
            //set parameters
            params.setService("Smart");
            params.setMethod("getList");
            params.addParam("limit",25);
            params.addParam("sortdir","ASC");
            params.addParam("sortfield","devicefile");
            params.addParam("start",0);

            this.enqueue(params,callBack);
        }
        catch (Exception e)
        {
            e.printStackTrace();

        }
    }


    public void getScheduleList(Callback callBack)
    {
        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();
            //set parameters
            params.setService("Smart");
            params.setMethod("getScheduleList");
            params.addParam("limit",25);
            params.addParam("sortdir","ASC");
            params.addParam("sortfield","volumedevicefile");
            params.addParam("start",0);

            this.enqueue(params,callBack);
        }
        catch (Exception e)
        {
            e.printStackTrace();

        }
    }
    public void setSettings(SmartSettings settings, Callback callBack)
    {
        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();
            //set parameters
            params.setService("Smart");
            params.setMethod("setSettings");
            params.addParam("enable",settings.getEnable()?"mTrue":"mFalse");
            params.addParam("interval",settings.getInterval());
            params.addParam("powermode",settings.getPowermode());
            params.addParam("tempcrit",settings.getTempcrit());
            params.addParam("tempdiff",settings.getTempdiff());
            params.addParam("tempinfo",settings.getTempinfo());

            this.enqueue(params,callBack);
        }
        catch (Exception e)
        {
            e.printStackTrace();

        }
    }


    public void enumerateMonitoredDevices(Callback callBack)
    {
        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();
            //set parameters
            params.setService("Smart");
            params.setMethod("enumerateMonitoredDevices");
            this.enqueue(params,callBack);
        }
        catch (Exception e)
        {
            e.printStackTrace();

        }

    }

    public void getScheduledTest(String uuid, Callback callBack)
    {
        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();
            //set parameters
            params.setService("Smart");
            params.setMethod("getScheduledTest");
            params.addParam("uuid",uuid);

            this.enqueue(params,callBack);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    public void setScheduledTest(SmartScheduledTest cheduledTest,Callback callBack)
    {
        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();

            params.setService("Smart");
            params.setMethod("setScheduledTest");
            //set parameters
            params.addParam("comment",cheduledTest.getComment());
            params.addParam("dayofmonth",cheduledTest.getDayofmonth());
            params.addParam("dayofweek",cheduledTest.getDayofweek());
            params.addParam("devicefile",cheduledTest.getDevicefile());
            params.addParam("enable",cheduledTest.getEnable()?"mTrue":"mFalse");
            params.addParam("hour",cheduledTest.getHour());
            params.addParam("month",cheduledTest.getMonth());
            params.addParam("type",cheduledTest.getType());
            params.addParam("uuid",cheduledTest.getUuid());

            this.enqueue(params,callBack);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void getDeviceSettings(String devicefile,Callback callBack)
    {
        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();
            //set parameters
            params.setService("Smart");
            params.setMethod("getDeviceSettings");
            params.addParam("devicefile",devicefile);


            this.enqueue(params,callBack);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void setDeviceSettings(SmartDevices device, Callback callBack)
    {
        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();
            //set parameters
            params.setService("Smart");
            params.setMethod("setDeviceSettings");
            params.addParam("uuid",device.getUuid());
            params.addParam("devicefile",device.getDevicefile());
            params.addParam("enable",device.getMonitor()?"mTrue":"mFalse");


            this.enqueue(params,callBack);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void getInformation(String devicefile,Callback callBack)
    {
        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();
            //set parameters
            params.setService("Smart");
            params.setMethod("getInformation");
            params.addParam("devicefile",devicefile);


            this.enqueue(params,callBack);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void getAttributes(String devicefile,Callback callBack)
    {
        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();
            //set parameters
            params.setService("Smart");
            params.setMethod("getAttributes");
            params.addParam("devicefile",devicefile);


            this.enqueue(params,callBack);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void getSelfTestLogs(String devicefile,Callback callBack)
    {
        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();
            //set parameters
            params.setService("Smart");
            params.setMethod("getSelfTestLogs");
            params.addParam("devicefile",devicefile);


            this.enqueue(params,callBack);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void getExtendedInformation(String devicefile,Callback callBack)
    {
        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();
            //set parameters
            params.setService("Smart");
            params.setMethod("getSelfTestLogs");
            params.addParam("devicefile",devicefile);


            this.enqueue(params,callBack);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void executeScheduledTest(String uuid,Callback callBack)
    {
        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();
            //set parameters
            params.setService("Smart");
            params.setMethod("executeScheduledTest");
            params.addParam("uuid",uuid);


            this.enqueue(params,callBack);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void deleteScheduledTest(String uuid,Callback callBack)
    {
        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();
            //set parameters
            params.setService("Smart");
            params.setMethod("deleteScheduledTest");
            params.addParam("uuid",uuid);


            this.enqueue(params,callBack);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
