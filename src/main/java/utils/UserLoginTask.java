package utils;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.URLUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLHandshakeException;

import Client.JSONRPCClient;
import Client.JSONRPCParamsBuilder;
import Models.Errors;

/**
 * Created by thiba on 21/12/2016.
 */

public class UserLoginTask extends AsyncTask<CallBackTask, Void, Errors> {


    private final String mUser;
    private final String mPassword;
    private final String mUrl;
    private final Integer mPort;
    private final boolean mSSL;

    private Errors mError = null;
    private CallBackTask mCallBackTask;
    public UserLoginTask(String url,String user, String password,Integer port,boolean ssl) {

        mUrl = url;
        mUser = user;
        mPassword = password;
        mPort = port;
        mSSL = ssl;
        mError = new Errors();
    }
    @Override
    protected Errors doInBackground(CallBackTask... params) {
        mCallBackTask = params[0];


        JSONObject jsonObj = null;
        try {


            JSONRPCClient jsonRpc = JSONRPCClient.getInstance();
            jsonRpc.setBaseUrl("http"+ (mSSL?"s":"")+"://"+ mUrl+":"+mPort);
            Log.i("UserLoginTask","try connect at "+jsonRpc.getBaseUrl());
            JSONRPCParamsBuilder paramsBuilder = new JSONRPCParamsBuilder();

            //set parameters
            paramsBuilder.setService("Session");
            paramsBuilder.setMethod("login");

            paramsBuilder.addParam("username",mUser);
            paramsBuilder.addParam("password",mPassword);

            String jsonParams = paramsBuilder.toString();
           return connect(jsonParams);


        } catch (Exception e) {

            mError.setMessage(e.getMessage());
            mError.setTrace(e.getStackTrace().toString());
            Log.i("UserLoginTask", "Can not connect to server : " + mUrl);
            e.printStackTrace();
            return mError;
        }
        //Log.i("UserLoginTask","JsonObj is null an error occurred");
        //return null;
    }
    private Errors connect(String jsonParams){

        String api = "http"+ (mSSL?"s":"")+"://"+ mUrl+":"+mPort+"/rpc.php";

        URL url = null;
        try {
            url = new URL(api);
        } catch (MalformedURLException e) {
            mError.setMessage(e.getMessage());
            mError.setTrace(e.getStackTrace().toString());
            Log.i("UserLoginTask", "MalformedURLException");
            e.printStackTrace();
            return mError;
        }
        HttpsURLConnection urlConnectionSSL = null;
        HttpURLConnection urlConnection = null;
        if(URLUtil.isHttpsUrl(api)) {

            try {
                urlConnectionSSL = (HttpsURLConnection) url
                        .openConnection();
            } catch (IOException e) {
                mError.setMessage("Can not connect to server");
                mError.setTrace(e.getStackTrace().toString());
                Log.i("UserLoginTask", "Can not connect to server : " + mUrl);
                e.printStackTrace();
                return mError;
            }
            urlConnectionSSL.setHostnameVerifier(JSONRPCClient.DUMMY_VERIFIER);
                urlConnection = urlConnectionSSL;

        }
        else
            try {
                urlConnection = (HttpURLConnection)url.openConnection();
            } catch (IOException e) {
                mError.setMessage("Can not connect to server");
                mError.setTrace(e.getStackTrace().toString());
                Log.i("UserLoginTask", "Can not connect to server : " + mUrl);
                e.printStackTrace();
                return mError;
            }

        JSONObject jsonResult = null;
        JSONObject jsonResponse = null;
        try {

            Log.i("UserLoginTask", "POST ".concat(api));
            Log.i("UserLoginTask", "Data: ".concat(jsonParams));

            StringBuilder result = new StringBuilder();
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type","application/json");
            //urlConnection.setRequestProperty("Content-length", "0");
            urlConnection.setUseCaches(false);
            urlConnection.setDoOutput(true);
            urlConnection.setAllowUserInteraction(false);
            urlConnection.setConnectTimeout(5000);
            urlConnection.setReadTimeout(5000);


            OutputStreamWriter dStream = new OutputStreamWriter(urlConnection.getOutputStream());
            dStream.write(jsonParams);
            //dStream.writeBytes(entity); //Writes out the string to the underlying output stream as a sequence of bytes
            dStream.flush(); // Flushes the data output stream.
            dStream.close(); // Closing the output stream.

            //urlConnection.connect();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            Log.i("UserLoginTask", result.toString());

            int responseCode = urlConnection.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_MOVED_PERM)
            {
                Log.i("UserLoginTask", " responseCode HTTP_MOVED_PERM");
                result = new StringBuilder();


                result.append( "{\"response\":{\"authenticated\":false,\"responseCode\":\"HTTP_MOVED_PERM\"},\"error\":null}");

            }

            if (responseCode == HttpsURLConnection.HTTP_OK)
            {
                Log.i("UserLoginTask", " responseCode HTTP_OK");


                Map<String, List<String>> headerFields = urlConnection.getHeaderFields();

            }


            jsonResponse = (JSONObject) new JSONTokener(result.toString()).nextValue();
            if (!jsonResponse.isNull("response")) {
                jsonResult = jsonResponse.getJSONObject("response");
            }
        } catch (SSLHandshakeException e) {
            mError.setMessage("Cannot connect to server. Are you sure SSL is needed? Or check your port");
            mError.setTrace(e.getStackTrace().toString());
            Log.i("UserLoginTask", "Can not connect to server : " + mUrl);
            e.printStackTrace();
            return mError;
        } catch (IOException e) {
            mError.setMessage(e.getMessage());
            mError.setTrace(e.getStackTrace().toString());
            Log.i("UserLoginTask", "IOException");
            e.printStackTrace();
            return mError;
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if(urlConnection != null)
                urlConnection.disconnect();
        }


        return CheckJsonResult(jsonResult);
    }

    private Errors CheckJsonResult(JSONObject jsonObj)
    {
        String api = "http"+ (mSSL?"s":"")+"://"+ mUrl+":"+mPort+"/rpc.php";
        if (jsonObj != null) {
            Log.i("UserLoginTask","Success at"+api);

            if( !jsonObj.isNull("responseCode")) {
                mError.setMessage("Unable to connect to the API");
                return mError;
            }
            else if (!jsonObj.isNull("username")) {

                try {
                    if(!jsonObj.getBoolean("authenticated")) {
                        mError.setMessage("Incorrect username or password");
                        return mError;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    mError.setMessage(e.getMessage());
                    mError.setTrace(e.getStackTrace().toString());
                    return mError;
                }
            }



        }
        return null;
    }

    @Override
    protected void onPostExecute(Errors res) {

        if(res!= null)
            mCallBackTask.handleMessageError(res);
        else
            mCallBackTask.handleFinich();
    }

}
