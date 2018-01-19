package Controllers;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import Client.AsyncCall;
import Client.Callback;
import Client.JSONRPCClient;
import Client.JSONRPCParamsBuilder;
import Interfaces.IServicesSettings;
import utils.Util;

/**
 * Created by thiba on 26/10/2016.
 */

public class ServicesController extends Abstractcontroller {
    public ServicesController(Activity activity) {
        super(activity);
    }


    List<String> Services = new ArrayList<String>() {{add("SSH");add("NFS");add("TFTP");add("SNMP");add("SMB");}};



    public void getService(String ServiceName,Callback callBack)
    {

        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();

            params.setService(ServiceName);

            if(ServiceName.equals("NFS") || ServiceName.equals("SMB"))
                params.setMethod("getSettings");
            else
                params.setMethod("get");
            this.enqueue(params,callBack);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    public void setService(IServicesSettings Service, Callback callBack)
    {

        try {

            String ServiceName = Service.getServiceName();
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();
            params.setService(Service.getServiceName());
            if(ServiceName.equals("NFS") || ServiceName.equals("SMB"))
                params.setMethod("setSettings");
            else
            params.setMethod("set");
            params.SetparamStr(Service.ToJson());
            //Map<String,String> dico = Service.getPArams();

            //for (Map.Entry<String, String> entry : dico.entrySet())
            //{
            //    params.addParam(entry.getKey(),entry.getValue());
            //}
            this.enqueue(params,callBack);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


    }

    public void getShareList(String ServiceName,Callback callBack)
    {
        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();

            params.setService(ServiceName);
            params.setMethod("getShareList");

            params.addParam("start",0);
            params.addParam("limit",25);
            params.addParam("sortfield","sharedfoldername");
            params.addParam("sortdir","ASC");
            this.enqueue(params,callBack);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }


    public void getSharedFoldersFromMger(Callback callBack)
    {
        try {
        JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();
        params.setService("ShareMgmt");
        params.setMethod("enumerateSharedFolders");
        this.enqueue(params,callBack);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

}
