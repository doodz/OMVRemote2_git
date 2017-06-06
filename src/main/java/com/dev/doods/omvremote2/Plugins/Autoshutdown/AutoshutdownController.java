package com.dev.doods.omvremote2.Plugins.Autoshutdown;

import android.app.Activity;

import Client.Callback;
import Client.JSONRPCParamsBuilder;
import Controllers.Abstractcontroller;

/**
 * Created by Ividata7 on 03/05/2017.
 */

public class AutoshutdownController extends Abstractcontroller {


    public AutoshutdownController(Activity activity) {
        super(activity);
    }

    public void getSettings( Callback callBack)
    {

        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();
            //set parameters
            params.setService("AutoShutdown");
            params.setMethod("getSettings");

            this.enqueue(params,callBack);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void setSettings( AutoshutdownSettings settings,  Callback callBack)
    {
        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();
            //set parameters
            params.setService("AutoShutdown");
            params.setMethod("setSettings");


            if(settings.getChecksamba() != null)
                params.addParam("checksamba",settings.getChecksamba()?"mTrue":"mFalse");
            if(settings.getCheckcli() != null)
                params.addParam("checkcli",settings.getCheckcli()?"mTrue":"mFalse");

            params.addParam("checkclockactive",settings.getCheckclockactive()?"mTrue":"mFalse");


            params.addParam("cycles",settings.getCycles());
            params.addParam("enable",settings.getEnable()?"mTrue":"mFalse");
            params.addParam("extraoptions",settings.getExtraoptions());
            params.addParam("fake",settings.getFake()?"mTrue":"mFalse");
            params.addParam("hddiocheck",settings.getHddiocheck()?"mTrue":"mFalse");
            params.addParam("hddiorate",settings.getHddiorate());
            params.addParam("loadaverage",settings.getLoadaverage());
            params.addParam("loadaveragecheck",settings.getLoadaveragecheck()?"mTrue":"mFalse");

            params.addParam("nsocketnumbers",settings.getNsocketnumbers());
            params.addParam("range",settings.getRange());
            params.addParam("shutdowncommand",settings.getShutdowncommand());
            params.addParam("sleep",settings.getSleep());

            params.addParam("syslog",settings.getSyslog()?"mTrue":"mFalse");
            params.addParam("uldlcheck",settings.getUldlcheck()?"mTrue":"mFalse");
            params.addParam("uldlrate",settings.getUldlrate());
            params.addParam("uphours-begin",settings.getUphoursBegin());
            params.addParam("uphours-end",settings.getUphoursEnd());
            params.addParam("verbose",settings.getVerbose()?"mTrue":"mFalse");

            this.enqueue(params,callBack);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
