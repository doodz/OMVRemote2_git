package utils;

import android.net.SSLCertificateSocketFactory;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.URLUtil;

import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;

import javax.net.SocketFactory;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import Client.JSONRPCClient;
import Models.Errors;

/**
 * Created by thiba on 21/12/2016.
 */

public class CheckURLTask extends AsyncTask<CallBackTask, Void, Errors> {

    private String mUrl;
    private int mPort;
    private Boolean mSSL;
    private Errors mError;

    private CallBackTask mCallBackTask;

    public CheckURLTask(String url,int port,boolean ssl) {
        mUrl = url;
        mPort = port;
        mSSL  = ssl;
        mError = new Errors();
    }
    @Override
    protected Errors doInBackground(CallBackTask... params) {
        mCallBackTask = params[0];
        boolean ok = false;
        Socket ss = null;
        try {


            if(mSSL) {
                //ss = (Socket) SSLCertificateSocketFactory.getDefault().createSocket();
                SocketFactory sf = SSLSocketFactory.getDefault();
                SSLSocket socket = (SSLSocket) sf.createSocket(mUrl, mPort);
                socket.close();
            }
            else {
                ss = SocketFactory.getDefault().createSocket();
                ss.connect(new InetSocketAddress(mUrl, mPort), 2000);
                ss.close();
            }
            ok = true;
        } catch (UnknownHostException e) {
            mError.setMessage("Can not connect to server");
            mError.setTrace(e.getStackTrace().toString());
            Log.i("CheckURLTask", "Can not connect to server : " + mUrl);
            e.printStackTrace();
            return mError;
        } catch (SocketTimeoutException e){
            mError.setMessage(e.getMessage());
            mError.setTrace(e.getStackTrace().toString());
            Log.i("CheckURLTask", "failed to connect to : " + mUrl+" (port "+mPort+") after 1000ms");
            e.printStackTrace();
            return mError;

        } catch (IOException e) {
            mError.setMessage("Can not connect to server");
            mError.setTrace(e.getStackTrace().toString());
            Log.i("CheckURLTask", "Can not connect to server : " + mUrl);
            e.printStackTrace();
            return mError;
        } finally {
            if (!ok) {
                Log.i("CheckURLTask", mError+" : " + mUrl);
            }
        }

        if(ok && this.mSSL)
        {
           return CheckSSL();
        }


        return null;
    }

    private Errors CheckSSL() {
        Log.i("CheckURLTask", "Check SSL");
        String api = "http"+ (mSSL?"s":"")+"://"+ mUrl+":"+mPort;
        URL url = null;
        try {
            url = new URL(api);
        } catch (MalformedURLException e) {
            mError.setMessage(e.getMessage());
            mError.setTrace(e.getStackTrace().toString());
            Log.i("CheckURLTask", "MalformedURLException");
            e.printStackTrace();
            return mError;
        }
        HttpsURLConnection urlConnectionSSL = null;
        HttpURLConnection urlConnection = null;
        if (URLUtil.isHttpsUrl(api)) {
            try {
                urlConnectionSSL = (HttpsURLConnection) url
                        .openConnection();
                urlConnectionSSL.setHostnameVerifier(JSONRPCClient.DUMMY_VERIFIER);
                urlConnection = urlConnectionSSL;

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (urlConnectionSSL != null)
                    urlConnectionSSL.disconnect();
            }
        } else
            try {
                urlConnection = (HttpURLConnection) url.openConnection();
            } catch (IOException e) {
                mError.setMessage("Can not connect to server");
                mError.setTrace(e.getStackTrace().toString());
                Log.i("CheckURLTask", "Can not connect to server : " + api);
                e.printStackTrace();
                return mError;
            }

        JSONObject jsonResult = null;

        try {

            Log.i("JSONRPCClient", "GET ".concat(mUrl));


            StringBuilder result = new StringBuilder();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            //urlConnection.setRequestProperty("Content-length", "0");
            urlConnection.setUseCaches(false);
            urlConnection.setDoOutput(true);
            urlConnection.setAllowUserInteraction(false);
            urlConnection.setConnectTimeout(5000);
            urlConnection.setReadTimeout(5000);
            OutputStreamWriter dStream = new OutputStreamWriter(urlConnection.getOutputStream());
        }
        catch (SSLHandshakeException e)
        {
            mError.setMessage("Cannot connect to server. Are you sure SSL is needed? Or check your port");
            mError.setTrace(e.getStackTrace().toString());
            Log.i("CheckURLTask", "Can not connect to server : " + api);
            e.printStackTrace();
            return mError;
        } catch (ProtocolException e) {
            mError.setMessage(e.getMessage());
            mError.setTrace(e.getStackTrace().toString());
            Log.i("CheckURLTask", "ProtocolException");
            e.printStackTrace();
            return mError;
        } catch (IOException e) {
            mError.setMessage(e.getMessage());
            mError.setTrace(e.getStackTrace().toString());
            Log.i("CheckURLTask", "IOException");
            e.printStackTrace();
            return mError;
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
