package Controllers;

import android.app.Activity;

import Client.Callback;
import Client.JSONRPCParamsBuilder;

/**
 * Created by Ividata7 on 20/06/2017.
 */

public class NetworkController extends Abstractcontroller {
    public NetworkController(Activity activity) {
        super(activity);
    }

    public void getInterfaceList(Callback callBack)
    {

        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();
            //set parameters
            params.setService("Network");
            params.setMethod("getInterfaceList");

            params.addParam("start",0);
            params.addParam("limit",25);
            params.addParam("sortdir","mNull");
            params.addParam("sortfield","mNull");

            this.enqueue(params,callBack);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
