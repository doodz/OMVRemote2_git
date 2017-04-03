package Controllers;

import android.app.Activity;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import Client.AsyncCall;
import Client.Callback;
import Client.JSONRPCClient;
import Client.JSONRPCParamsBuilder;
import Models.PackageInformation;
import Models.UpdateSettings;

/**
 * Created by thiba on 31/08/2016.
 */
public class UpdateController extends Abstractcontroller{

    private Activity mActivity;

    public UpdateController(Activity activity)
    {
        super(activity);
    }


    public void EnumerateUpgraded(Callback callBack)
    {
        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();
            //set parameters
            params.setService("Apt");
            params.setMethod("enumerateUpgraded");
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


    public void CheckIsRunning(String filename,Callback callBack)
    {

        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();

            //set parameters
            params.setService("Exec");
            params.setMethod("isRunning");
            params.addParam("filename",filename);
            this.enqueue(params,callBack);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void CheckUpdate(Callback callBack)
    {
        // return filename
        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();

            //set parameters
            params.setService("Apt");
            params.setMethod("update");
            this.enqueue(params,callBack);
        }
        catch (Exception e)
        {
            e.printStackTrace();

        }

    }


    public void UpdatePackage(List<PackageInformation> packages, Callback callBack)
    {
        // return filename
        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();

            //set parameters
            params.setService("Apt");
            params.setMethod("upgrade");
            List<String> lst = new ArrayList<String>() ;

            for (PackageInformation pack:packages) {
                lst.add(pack.getName());
            }

            Gson gson = new Gson();
            String str = gson.toJson(lst);
            params.addParam("packages",str);
            //params.addObjParam("packages",lst);
            this.enqueue(params,callBack);
        }
        catch (Exception e)
        {
            e.printStackTrace();

        }

    }

    public void setSettings(UpdateSettings settings,Callback callBack)
    {
        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();

            //set parameters
            params.setService("Apt");
            params.setMethod("setSettings");
            params.addParam("partner",settings.getPartner());
            params.addParam("proposed",settings.getProposed());
            this.enqueue(params,callBack);
        }
        catch (Exception e)
        {
            e.printStackTrace();

        }

    }

    public void  getSettings(Callback callBack)
    {
        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();

            //set parameters
            params.setService("Apt");
            params.setMethod("getSettings");
            this.enqueue(params,callBack);
        }
        catch (Exception e)
        {
            e.printStackTrace();

        }

    }

    public void  getChangeLog(String  filename,Callback callBack)
    {
        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();

            //set parameters
            params.setService("Apt");
            params.setMethod("getChangeLog");
            params.addParam("filename",filename);
            this.enqueue(params,callBack);
        }
        catch (Exception e)
        {
            e.printStackTrace();

        }

    }

}
