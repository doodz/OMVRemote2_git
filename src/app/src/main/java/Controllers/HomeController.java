package Controllers;

import android.app.Activity;

import org.json.JSONObject;

import java.io.IOException;

import Client.AsyncCall;
import Client.Call;
import Client.Callback;
import Client.JSONRPCClient;
import Client.JSONRPCParamsBuilder;
import Client.Response;
import Models.WebSettings;

/**
 * Created by thiba on 16/08/2016.
 */
public class HomeController extends Abstractcontroller{


    public HomeController(Activity activity) { super(activity); }


    public void GetStatusServices(Callback callBack) {

        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();
            //set parameters
            params.setService("Services");
            params.setMethod("getStatus");
            //params.addParam("username",email);
            //params.addParam("password",password);
            params.addOption("updatelastaccess", "mFalse");
            this.enqueue(params,callBack);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void GetSystemInformation(Callback callBack) {

        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();
            //set parameters
            params.setService("System");
            params.setMethod("getInformation");
            params.addOption("updatelastaccess", "mFalse");
            this.enqueue(params,callBack);
        }
        catch (Exception e)
        {
            e.printStackTrace();

        }
    }

    public void GetSettings(Callback callBack)
    {
        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();
            //set parameters
            params.setService("WebGui");
            params.setMethod("getSettings");
            this.enqueue(params,callBack);
        }
        catch (Exception e)
        {
            e.printStackTrace();

        }
    }

    public void SetSettings(WebSettings settings, Callback callBack)
    {
        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();

            //set parameters
            params.setService("WebGui");
            params.setMethod("setSettings");

            params.addParam("enablessl",settings.getEnablessl()?"mTrue":"mFalse");
            params.addParam("forcesslonly",settings.getForcesslonly()?"mTrue":"mFalse");
            params.addParam("port",settings.getPort());
            params.addParam("sslcertificateref",settings.getSslcertificateref());
            params.addParam("sslport",settings.getSslport());
            params.addParam("timeout",settings.getTimeout());
            this.enqueue(params,callBack);
        }
        catch (Exception e)
        {
            e.printStackTrace();

        }
    }

    public void GetCertificate(Callback callBack)
    {
        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();

            //set parameters
            params.setService("CertificateMgmt");
            params.setMethod("getList");
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
}
