package com.dev.doods.omvremote2.Plugins.Sensors;

import android.app.Activity;

import Client.Callback;
import Client.JSONRPCParamsBuilder;
import Controllers.Abstractcontroller;

/**
 * Created by Ividata7 on 21/06/2017.
 */

public class SensorsController extends Abstractcontroller {


    public SensorsController(Activity activity) {
        super(activity);
    }

    public void getSettings( Callback callBack)
    {

        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();
            //set parameters
            params.setService("Sensors");
            params.setMethod("getSettings");

            this.enqueue(params,callBack);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}
