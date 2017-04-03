package Controllers;

import android.app.Activity;

import Client.AsyncCall;
import Client.Callback;
import Client.JSONRPCClient;
import Client.JSONRPCParamsBuilder;

/**
 * Created by thiba on 05/11/2016.
 */

public class FolderBrowserController  extends Abstractcontroller  {

    public FolderBrowserController(Activity activity) {
        super(activity);
    }


    public void getListFilesFolder(String path,String mntent,String uuid, Callback callBack)
    {

        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();
            //set parameters
            params.setService("FolderBrowser");
            params.setMethod("get");
            params.addParam("path",path);
            params.addParam("type",mntent);
            params.addParam("uuid",uuid);
            this.enqueue(params,callBack);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
