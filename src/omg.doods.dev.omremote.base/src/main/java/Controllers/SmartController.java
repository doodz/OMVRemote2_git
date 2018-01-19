package Controllers;

import android.app.Activity;

import Client.Callback;
import Client.JSONRPCParamsBuilder;
import Models.SmartSettings;

/**
 * Created by Ividata7 on 27/01/2017.
 */

public class SmartController extends Abstractcontroller  {

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

}
