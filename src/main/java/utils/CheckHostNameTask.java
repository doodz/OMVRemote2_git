package utils;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import Models.Errors;

/**
 * Created by thiba on 21/12/2016.
 */

public class CheckHostNameTask extends AsyncTask<CallBackTask, Void, Errors> {

    private Errors mError = new Errors();
    private String mHostName;
    private CallBackTask mCallBackTask;
    public CheckHostNameTask(String hostName)
    {
        mHostName = hostName;

    }

    @Override
    protected Errors doInBackground(CallBackTask... params) {

        mCallBackTask = params[0];
        try {
            InetAddress address = InetAddress.getByName(mHostName);

            if(!address.isReachable(100))
            {
                mError.setMessage("Address is not reachable");
                mError.setCode(1);
                return mError;
            }
        } catch (UnknownHostException e) {
            mError.setMessage("Can not resolve host");
            mError.setTrace(e.getStackTrace().toString());
            mError.setCode(2);
            Log.i("CheckHostNameTask", "Can not resolve host : " + mHostName);
            e.printStackTrace();
            return mError;
        } catch (IOException e) {
            mError.setMessage("Can not connect to server");
            mError.setTrace(e.getStackTrace().toString());
            mError.setCode(3);
            Log.i("CheckHostNameTask", "Can not connect to server : " + mHostName);
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
