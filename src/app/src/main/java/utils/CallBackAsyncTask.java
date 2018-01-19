package utils;

import android.os.AsyncTask;
import android.os.Handler;

import Models.Errors;


/**
 * Created by thiba on 21/12/2016.
 */

public class CallBackAsyncTask implements Runnable {

    private final AsyncTask<CallBackTask,Void, Errors> mTask;

    private final CallBackTask mCallback;
    private CallBackTask mCallBackTask;
    public CallBackAsyncTask(AsyncTask task, CallBackTask callback)
    {
        mTask = task;
        mCallback = callback;
    }


    @Override
    public void run() {
       mTask.execute(mCallback);
    }
}
