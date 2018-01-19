package Controllers;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import Client.AsyncCall;
import Client.Call;
import Client.Callback;
import Client.JSONRPCClient;
import Client.JSONRPCParamsBuilder;
import Client.Response;
import Interfaces.OutputListener;
import Models.Errors;
import Models.Output;
import OMVFragment.Dialogs.OutputDialogFragment;

import static java.lang.Thread.sleep;

/**
 * Created by thiba on 14/12/2016.
 */

public class OutputDialogController {
    private AlertDialog alert;
    private OutputDialogFragment mOutputDialogFragment;
    private ProgressDialog progressDialog;
    private Handler handler = new Handler();
    private Boolean mContinue = true;
    private final Collection<OutputListener> mListeners = new ArrayList<OutputListener>();
    public OutputDialogController(OutputDialogFragment fragment)
    {
        mOutputDialogFragment = fragment;
    }


    public void IsRunningFile(String filename)
    {
        mContinue = true;
        InternalIsRunningFile(filename);
    }

    private void InternalIsRunningFile(String filename)
    {

        isRunningFile(filename, new Callback() {
            @Override
            public void onFailure(Call call, Exception e) {

            }

            @Override
            public void OnOMVServeurError(Call call,final Errors error) {


                handler.post(new Runnable(){
                    public void run() {
                        mOutputDialogFragment.SetTitle("An error occurred");
                        //mOutputDialogFragment.ShowSmal(false);
                        mOutputDialogFragment.SetMessage(error.getMessage());
                        mOutputDialogFragment.SetProgressBarVisibility(false);
                    }});

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException, InterruptedException {

                final Output o =  response.GetResultObject(new TypeToken<Output>(){});
                handler.post(new Runnable(){
                    public void run() {

                        if(mOutputDialogFragment == null && mContinue)
                        {
                            //CreateDialog(true,"Please wait ...");
                            mOutputDialogFragment.SetMessage("Task in progress ...");
                            //progressDialog = ProgressDialog.show(mActivity, "Please wait ...",  "Task in progress ...", true);
                            //progressDialog.setCancelable(true);
                        }
                        if(!o.getRunning())
                        {
                            try {
                                sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            boolean a = mOutputDialogFragment.isCancelable();
                            boolean b = mOutputDialogFragment.isDetached();
                            boolean c = mOutputDialogFragment.isHidden();
                            boolean d = mOutputDialogFragment.isRemoving();
                            boolean e = mOutputDialogFragment.isVisible();



                            if(mOutputDialogFragment != null)
                                mOutputDialogFragment.dismiss();
                            mOutputDialogFragment = null;
                            FireFinich();
                        }

                    }
                });
                sleep(1000);
                mCurentOutput = o;
                if(o.getRunning() &&  mContinue &&  mOutputDialogFragment != null) {

                    InternalIsRunningFile(o.getFilename());
                }

            }
        });
    }

    private void isRunningFile(String filename, Callback callBack)
    {
        try {
            JSONRPCClient jsonRpc = JSONRPCClient.getInstance();
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();

            //set parameters
            params.setService("Exec");
            params.setMethod("isRunning");
            params.addParam("filename",filename);
            String jsonParams = params.toString();
            AsyncCall call = new AsyncCall(callBack,params,null,jsonRpc);
            jsonRpc.Dispatcher().enqueue(call);

        }
        catch (Exception e)
        {
            e.printStackTrace();

        }
    }

    public void getOutput(String filename)
    {

        mContinue = true;
        getOutput(filename, new Callback() {
            @Override
            public void onFailure(Call call, Exception e) {

            }

            @Override
            public void OnOMVServeurError(Call call, Errors error) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final Output o = response.GetResultObject( new TypeToken<Output>(){});
               /* handler.post(new Runnable(){
                    public void run() {


                        //CreateDialogOld();
                        CreateDialog(true,"Apt update");
                    }
                });
                */
                  if(mOutputDialogFragment != null) {
                    handler.post(new Runnable(){
                        public void run() {
                            mOutputDialogFragment.AddMessage(o.getOutput() + (o.getRunning()? "": "\\r\\n Done !!!"));
                        }
                    });
                }
                if(o.getRunning())
                    UpdateAlertDialog(o);
                else if(mOutputDialogFragment != null)
                    handler.post(new Runnable(){
                        public void run() {
                            mOutputDialogFragment.SetProgressBarVisibility(false);
                        }
                    });

            }
        },0);
    }
    /*
    private void CreateDialog(Boolean justWaitCursor,String title)
    {

        Bundle bundle = new Bundle();
        bundle.putBoolean("justWaitCursor", justWaitCursor);
        bundle.putString("title", title);
        // Create an instance of the dialog fragment and show it
        mOutputDialogFragment = new OutputDialogFragment();
        //mOutputDialogFragment.setListener(this);
        mOutputDialogFragment.setArguments(bundle);
        android.app.FragmentManager fm = mActivity.getFragmentManager();
        mOutputDialogFragment.show(fm, "FilterPluginDialogFragment");
    }
    */
    public void AddListener(OutputListener listener)
    {
        mListeners.add(listener);
    }

    public void RemoveListener(OutputListener listener)
    {
        mListeners.remove(listener);
    }
    private void FireFinich()
    {
        for(OutputListener listener : mListeners) {
            listener.OnFinich();
        }
    }
    private void FireCanceled()
    {
        for(OutputListener listener : mListeners) {
            listener.OnCanceled();
        }
    }

    private void getOutput(String filename, Callback callBack, int pos)
    {

        try {
            JSONRPCClient jsonRpc = JSONRPCClient.getInstance();
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();

            //set parameters
            params.setService("Exec");
            params.setMethod("getOutput");
            params.addParam("pos",pos);
            params.addParam("filename",filename);
            String jsonParams = params.toString();
            AsyncCall call = new AsyncCall(callBack,params,null,jsonRpc);

            jsonRpc.Dispatcher().enqueue(call);

        }
        catch (Exception e)
        {
            e.printStackTrace();

        }
    }



    private void UpdateAlertDialog(Output out)
    {

        getOutput(out.getFilename(), new Callback() {
            @Override
            public void onFailure(Call call, Exception e) {

            }

            @Override
            public void OnOMVServeurError(Call call, final Errors error) {


                String str = error.getMessage();
                handler.post(new Runnable(){
                    public void run() {
                        mOutputDialogFragment.SetTitle("An error occurred");
                        mOutputDialogFragment.SetMessage(error.getMessage());
                        mOutputDialogFragment.SetProgressBarVisibility(false);
                        //mOutputDialogFragment.ShowSmal(true);
                    }});
                mContinue = false;
            }

            @Override
            public void onResponse(Call call,final Response response) throws IOException, InterruptedException {

                final Output o = response.GetResultObject(new TypeToken<Output>(){});

                if(mOutputDialogFragment != null) {
                    handler.post(new Runnable(){
                        public void run() {
                            mOutputDialogFragment.AddMessage(o.getOutput() + (o.getRunning()? "": "\\r\\n Done !!!"));
                        }
                    });
                }
                sleep(1000);
                mCurentOutput = o;
                if(mContinue && o != null && o.getRunning() && mOutputDialogFragment != null ) {

                    UpdateAlertDialog(o);
                }
                else
                {
                    handler.post(new Runnable() {
                        public void run() {
                            mOutputDialogFragment.SetProgressBarVisibility(false);
                        };
                    });
                }
            }
        },out.getPos());

    }

    private Output mCurentOutput;
    public Output Stop()
    {
        this.mContinue = false;
        return mCurentOutput;
    }
}
