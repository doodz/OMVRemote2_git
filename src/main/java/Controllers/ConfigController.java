package Controllers;

import android.app.Activity;

import Client.AsyncCall;
import Client.Callback;
import Client.JSONRPCClient;
import Client.JSONRPCParamsBuilder;

/**
 * Created by thiba on 11/12/2016.
 */

public class ConfigController extends Abstractcontroller {

    public ConfigController(Activity activity) {
        super(activity);
    }

    public void isDirty(Callback callBack)
    {
        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();
            //set parameters
            params.setService("Config");
            params.setMethod("isDirty");
            params.addOption("updatelastaccess","mFalse");
            this.enqueue(params,callBack);
        }
        catch (Exception e)
        {
            e.printStackTrace();

        }
    }

    public void applyChangesBg(Callback callBack)
    {
        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();
            //set parameters
            params.setService("Config");
            params.setMethod("applyChangesBg");
            params.addParam("force","mFalse");
            params.addParam("modules","[]");
            this.enqueue(params,callBack);
        }
        catch (Exception e)
        {
            e.printStackTrace();

        }
    }

    public void revertChangesBg(Callback callBack)
    {
        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();
            //set parameters
            params.setService("Config");
            params.setMethod("revertChangesBg");
            params.addParam("filename","");
            this.enqueue(params,callBack);
        }
        catch (Exception e)
        {
            e.printStackTrace();

        }
    }

    public void CheckUserDirectoryPermissions(String username,String uuid,Callback callBack)
    {
        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();
            //set parameters
            params.setService("PermissionsInfo");
            params.setMethod("checkUserDirectoryPermissions");
            params.addParam("username",username);
            params.addParam("uuid",uuid);
            this.enqueue(params,callBack);
        }
        catch (Exception e)
        {
            e.printStackTrace();

        }
    }
}
