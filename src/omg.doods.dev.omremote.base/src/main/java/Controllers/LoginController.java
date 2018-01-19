package Controllers;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONObject;

import Client.Host;
import Client.JSONRPCClient;
import Client.JSONRPCParamsBuilder;

/**
 * Created by thiba on 09/08/2016.
 */
public class LoginController extends Abstractcontroller{


    private SharedPreferences sharedPref;

    public LoginController(Activity activity)
    {
        super(activity);
        sharedPref =  this.GetActivity().getSharedPreferences("LoginPref",Context.MODE_PRIVATE);
    }

    public void SetHost(String name,String url,String login, String password,Integer port,boolean ssl)
    {
        JSONRPCClient jsonRpc = JSONRPCClient.getInstance();
        Host host = new Host();
        host.setName(name);
        host.setAddr(url);
        host.setPass(password);
        host.setUser(login);
        host.setPort( port);
        host.setSll(ssl);
        jsonRpc.SetHost(host);
    }

    public JSONObject connectionTask(String name,String url,String email, String password,Integer port,boolean ssl)
    {
        JSONObject jsonObj = null;
        try {


            JSONRPCClient jsonRpc = JSONRPCClient.getInstance();
            jsonRpc.setBaseUrl("http"+ (ssl?"s":"")+"://"+ url+":"+port);
            Log.i("LoginController","try connect at "+jsonRpc.getBaseUrl());
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();

            //set parameters
            params.setService("Session");
            params.setMethod("login");

            params.addParam("username",email);
            params.addParam("password",password);

            String jsonParams = params.toString();
            jsonObj = jsonRpc.connect(jsonParams);

            if (jsonObj != null) {
                Log.i("LoginController","Success at"+jsonRpc.getBaseUrl() +" with user "+email);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("hostName",url);
                editor.putString("userName",email);
                editor.putString("password",password);
                editor.putInt("port",port);
                editor.putBoolean("ssl",ssl);

                editor.commit();
                //parsing the returned json here
                return jsonObj;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i("LoginController","JsonObj is null an error occurred");
        return jsonObj;
    }
}
