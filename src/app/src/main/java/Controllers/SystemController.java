package Controllers;

import android.app.Activity;

import Client.AsyncCall;
import Client.Callback;
import Client.Host;
import Client.JSONRPCClient;
import Client.JSONRPCParamsBuilder;
import Models.SettingsNetwork;
import Models.TimeSettings;
import utils.Util;
import utils.Wol;

/**
 * Created by thiba on 10/10/2016.
 */

public class SystemController extends Abstractcontroller{

      public SystemController(Activity activity)
      {
          super(activity);
      }



    public void Reboot( Callback callBack)
    {
        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();

            //set parameters
            params.setService("System");
            params.setMethod("reboot");
            params.addParam("delay",3);
            this.enqueue(params,callBack);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void Standby(Callback callBack)
    {
        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();

            //set parameters
            params.setService("System");
            params.setMethod("standby");
            params.addParam("delay",3);
            this.enqueue(params,callBack);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
    public void Shutdown(Callback callBack)
    {
        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();

            //set parameters
            params.setService("System");
            params.setMethod("shutdown");
            params.addParam("delay",3);
            this.enqueue(params,callBack);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }


    public void getTimeSettings(Callback callBack)
    {
        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();

            //set parameters
            params.setService("System");
            params.setMethod("getTimeSettings");
            this.enqueue(params,callBack);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void  getTimeZoneList(Callback callBack)
    {
        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();

            //set parameters
            params.setService("System");
            params.setMethod("getTimeZoneList");
            this.enqueue(params,callBack);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }


    public void  getGeneralSettingsNetwork(Callback callBack)
    {
        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();

            //set parameters
            params.setService("Network");
            params.setMethod("getGeneralSettings");
            this.enqueue(params,callBack);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void  setGeneralSettings(SettingsNetwork settings,Callback callBack)
    {
        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();

            //set parameters
            params.setService("Network");
            params.setMethod("setGeneralSettings");

            params.addParam("hostname",settings.getHostname());
            params.addParam("domainname",settings.getDomainname());
            this.enqueue(params,callBack);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void setTimeSettings(TimeSettings settings, Callback callBack)
    {
        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();

            //set parameters
            params.setService("System");
            params.setMethod("setTimeSettings");


            params.addParam("ntpenable",settings.getNtpenable()?"mTrue":"mFalse");
            params.addParam("ntptimeservers",settings.getNtptimeservers());
            params.addParam("timezone",settings.getTimezone());
            this.enqueue(params,callBack);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }


    public void setDate(long timestamp,Callback callBack)
    {
        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();

            //set parameters
            params.setService("System");
            params.setMethod("setDate");

            params.addParam("timestamp",timestamp);

            this.enqueue(params,callBack);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }



    public void Wakeup()
    {
        try {
            JSONRPCClient jsonRpc = JSONRPCClient.getInstance();
           Host h = jsonRpc.GetHost();

            if(h != null)
            {
                String addr = h.getAddrBroadcast();
                if(addr== null || addr.equals(""))
                    addr= h.getAddr();
                Wol.wakeup(addr,h.getMacAddr(),h.getWolport());
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}
