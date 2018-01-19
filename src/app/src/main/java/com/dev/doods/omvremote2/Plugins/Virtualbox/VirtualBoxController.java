package com.dev.doods.omvremote2.Plugins.Virtualbox;

import android.app.Activity;

import Client.Callback;
import Client.JSONRPCParamsBuilder;
import Controllers.Abstractcontroller;

import com.dev.doods.omvremote2.Plugins.Virtualbox.VirtualBoxMachine;
import com.dev.doods.omvremote2.Plugins.Virtualbox.VirtualBoxSettings;

/**
 * Created by Ividata7 on 20/01/2017.
 */

public class VirtualBoxController extends Abstractcontroller {


    // ShareMgmtController.enumerateSharedFolders


    public VirtualBoxController(Activity activity)
    {
        super(activity);
    }

    public void getSettings( Callback callBack)
    {
        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();

            //set parameters
            params.setService("VirtualBox");
            params.setMethod("getSettings");

            this.enqueue(params,callBack);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void setSettings(VirtualBoxSettings settings, Callback callBack)
    {
        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();

            //set parameters
            params.setService("VirtualBox");
            params.setMethod("setSettings");

            params.addParam("enable",settings.getEnable()?"mTrue":"mFalse");
            params.addParam("enable_advanced",settings.getEnableAdvanced()?"mTrue":"mFalse");
            params.addParam("show_tab",settings.getShowTab()?"mTrue":"mFalse");
            params.addParam("machines.sharedfolderref",settings.getMachinesSharedfolderref());

            this.enqueue(params,callBack);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void setSettingsV3(VirtualBoxSettings settings, Callback callBack)
    {
        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();

            //set parameters
            params.setService("VirtualBox");
            params.setMethod("setSettings");

            params.addParam("enable",settings.getEnable()?"mTrue":"mFalse");
            params.addParam("enable_advanced",settings.getEnableAdvanced()?"mTrue":"mFalse");
            params.addParam("sharedfolderref",settings.getMachinesSharedfolderref());

            this.enqueue(params,callBack);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void setMachine(VirtualBoxMachine machine, Callback callBack)
    {
        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();

            //set parameters
            params.setService("VirtualBox");
            params.setMethod("setMachine");

            params.addParam("name",machine.getName());
            params.addParam("startupMode",machine.getStartupMode());
            params.addParam("uuid",machine.getUuid());


            this.enqueue(params,callBack);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void getMachines(Callback callBack)
    {
        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();

            //set parameters
            params.setService("VirtualBox");
            params.setMethod("getMachines");

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

    public void fixModule(Callback callBack)
    {
        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();

            //set parameters
            params.setService("VirtualBox");
            params.setMethod("fixModule");

            this.enqueue(params,callBack);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void setMachineState(String state,String uuid,Callback callBack)
    {
        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();

            //set parameters
            params.setService("VirtualBox");
            params.setMethod("setMachineState");
            //powerButton ACPI shutdown
            //powerDown power off the machine
            //powerUp start
            //pause pause
            //saveState Save the machine state
            //reset reset
            params.addParam("state",state);
            params.addParam("uuid",uuid);

            this.enqueue(params,callBack);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

}
