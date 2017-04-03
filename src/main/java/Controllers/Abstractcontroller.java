package Controllers;

import android.app.Activity;
import android.support.annotation.Nullable;

import Client.AsyncCall;
import Client.Callback;
import Client.JSONRPCClient;
import Client.JSONRPCParamsBuilder;

/**
 * Created by thiba on 10/10/2016.
 */

public abstract class Abstractcontroller {

    private Activity mActivity;

    private JSONRPCClient mClient;
    public void SetClient(JSONRPCClient client)
    {
        mClient = client;
    }

    public Activity GetActivity(){return mActivity;}

    public Abstractcontroller(Activity activity)
    {
        mActivity = activity;
    }

    protected void enqueue(JSONRPCParamsBuilder params,@Nullable Callback callBack)
    {

        JSONRPCClient jsonRpc = (mClient != null)? mClient:JSONRPCClient.getInstance();
        String jsonParams = params.toString();
        AsyncCall call = new AsyncCall(callBack,params,mActivity,jsonRpc);

        jsonRpc.Dispatcher().enqueue(call);
    }

    public  int GetApiVersion()
    {
        JSONRPCClient jsonRpc = (mClient != null)? mClient:JSONRPCClient.getInstance();

        if(!jsonRpc.HaveHost())
            return -1;

        return jsonRpc.GetHost().getVersion();
    }

}
