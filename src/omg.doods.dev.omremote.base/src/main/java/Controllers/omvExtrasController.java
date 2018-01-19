package Controllers;

import android.app.Activity;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import Client.AsyncCall;
import Client.Callback;
import Client.JSONRPCClient;
import Client.JSONRPCParamsBuilder;
import Models.CustomRepo;
import Models.Plugins;
import Models.Repo;
import Models.RepoV3;

/**
 * Created by thiba on 14/09/2016.
 */
public class omvExtrasController extends Abstractcontroller{


    public omvExtrasController(Activity activity) { super(activity); }

    public void getRepo(Callback callBack)
    {
        try {

            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();

            //set parameters
            if(GetApiVersion() == 3)
            {
                getRepoList(callBack);
                return;
            }

            params.setService("OmvExtrasOrg");
            params.setMethod("getRepo");
            this.enqueue(params,callBack);

        }
        catch (Exception e)
        {
            e.printStackTrace();

        }
    }

    private void getRepoV3 (String uuid,Callback callBack)
    {
        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();

            //set parameters
            params.setService("OmvExtras");
            params.setMethod("getRepo");
            params.addParam("uuid",uuid);

            this.enqueue(params,callBack);

        }
        catch (Exception e)
        {
            e.printStackTrace();

        }
    }


    private void getRepoList (Callback callBack)
    {
        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();

            //set parameters
            params.setService("OmvExtras");
            params.setMethod("getRepoList");
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

    public void SetRepo(List<Repo> repos,Callback callBack)
    {
        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();

            //set parameters
            if(GetApiVersion() == 3)
                params.setService("OmvExtras");
            else
                params.setService("OmvExtrasOrg");
            params.setMethod("setRepo");

            JSONArray array = new JSONArray ();

            for (Repo r:repos) {
                if(r.getEnable() == null) continue;
                JSONObject item = new JSONObject();
                item.put("enable",r.isSelected());
                item.put("id",r.getId());
                array.put(item);
            }
            params.SetparamArray(array);
            this.enqueue(params,callBack);
        }
        catch (Exception e)
        {
            e.printStackTrace();

        }

    }

    public void Aptclean(Callback callBack)
    {
        // return filename
        try {

            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();

            //set parameters
            if(GetApiVersion() == 3)
                params.setService("OmvExtras");
            else
                params.setService("OmvExtrasOrg");
            params.setMethod("doCommand");
            params.addParam("command","aptclean");
            this.enqueue(params,callBack);
        }
        catch (Exception e)
        {
            e.printStackTrace();

        }


    }

    public void GetArch(Callback callBack)
    {
        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();

            //set parameters
            if(GetApiVersion() == 3)
                params.setService("OmvExtras");
            else
                params.setService("OmvExtrasOrg");
            params.setMethod("getArch");
            this.enqueue(params,callBack);
        }
        catch (Exception e)
        {
            e.printStackTrace();

        }
    }

    public void getKernelList(Callback callBack)
    {
        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();

            //set parameters
            if(GetApiVersion() == 3)
                params.setService("OmvExtras");
            else
                params.setService("OmvExtrasOrg");
            params.setMethod("getKernelList");
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

    public void getKernel(Callback callBack)
    {
        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();
            //set parameters
            if(GetApiVersion() == 3)
                params.setService("OmvExtras");
            else
                params.setService("OmvExtrasOrg");
            params.setMethod("getKernel");
            this.enqueue(params,callBack);
        }
        catch (Exception e)
        {
            e.printStackTrace();

        }
    }

    public void setBootKernel(int key,Callback callBack)
    {
        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();

            //set parameters
            if(GetApiVersion() == 3)
                params.setService("OmvExtras");
            else
                params.setService("OmvExtrasOrg");
            params.setMethod("setBootKernel");
            params.addParam("key",key);
            this.enqueue(params,callBack);
        }
        catch (Exception e)
        {
            e.printStackTrace();

        }
    }


    public void installheaders(Callback callBack)
    {
        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();

            //set parameters
            if(GetApiVersion() == 3)
                params.setService("OmvExtras");
            else
                params.setService("OmvExtrasOrg");
            params.setMethod("doCommand");
            params.addParam("command","installheaders");
            this.enqueue(params,callBack);
        }
        catch (Exception e)
        {
            e.printStackTrace();

        }

    }

    public void installbackports(Callback callBack)
    {
        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();

            //set parameters
            if(GetApiVersion() == 3)
                params.setService("OmvExtras");
            else
                params.setService("OmvExtrasOrg");
            params.setMethod("doCommand");
            params.addParam("command","installbackports");
            this.enqueue(params,callBack);
        }
        catch (Exception e)
        {
            e.printStackTrace();

        }

    }


    public void getCustomList(Callback callBack)
    {
        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();

            //set parameters
            if(GetApiVersion() == 3)
                params.setService("OmvExtras");
            else
                params.setService("OmvExtrasOrg");
            params.setMethod("getCustomList");
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

    public void deleteCustom(String uuid,Callback callBack)
    {
        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();

            //set parameters
            if(GetApiVersion() == 3)
                params.setService("OmvExtras");
            else
                params.setService("OmvExtrasOrg");
            params.setMethod("deleteCustom");
            params.addParam("uuid",uuid);
            this.enqueue(params,callBack);
        }
        catch (Exception e)
        {
            e.printStackTrace();

        }

    }

    public void setCustom(CustomRepo custom, Callback callBack)
    {
        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();

            //set parameters
            if(GetApiVersion() == 3)
                params.setService("OmvExtras");
            else
                params.setService("OmvExtrasOrg");
            params.setMethod("setCustom");
            params.addParam("comment",custom.getComment());
            params.addParam("key",custom.getKey());
            params.addParam("name",custom.getName());
            params.addParam("repo",custom.getRepo());
            params.addParam("uuid", StringUtils.isEmpty(custom.getUuid())? "undefined":custom.getUuid() );
            this.enqueue(params,callBack);
        }
        catch (Exception e)
        {
            e.printStackTrace();

        }

    }



    public void setRepoV3(RepoV3 repo,  Callback callBack)
    {
        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();

            //set parameters

            params.setService("OmvExtras");
            params.setMethod("setRepo");

            params.addParam("comment",repo.getComment());
            params.addParam("enable",repo.getEnable()?"mTrue":"mFalse");
            params.addParam("key1",repo.getKey1());
            params.addParam("key2",repo.getKey2());
            params.addParam("key3",repo.getKey3());
            params.addParam("name",repo.getName());
            params.addParam("package1",repo.getPackage1());
            params.addParam("package2",repo.getPackage2());
            params.addParam("package3",repo.getPackage3());
            params.addParam("permanent",repo.getPermanent()?"mTrue":"mFalse");
            params.addParam("pin1",repo.getPin1());
            params.addParam("pin2",repo.getPin2());
            params.addParam("pin3",repo.getPin3());
            params.addParam("priority1",repo.getPriority1());
            params.addParam("priority2",repo.getPriority2());
            params.addParam("priority3",repo.getPriority3());
            params.addParam("repo1",repo.getRepo1());
            params.addParam("repo2",repo.getRepo2());
            params.addParam("repo3",repo.getRepo3());
            params.addParam("uuid",repo.getUuid());


            this.enqueue(params,callBack);
        }
        catch (Exception e)
        {
            e.printStackTrace();

        }

    }


    public void DeleteRepoV3(String uuid,Callback callBack)
    {

        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();

            //set parameters

            params.setService("OmvExtras");
            params.setMethod("deleteRepo");
            params.addParam("uuid",uuid);


            this.enqueue(params,callBack);
        }
        catch (Exception e)
        {
            e.printStackTrace();

        }
    }

}
