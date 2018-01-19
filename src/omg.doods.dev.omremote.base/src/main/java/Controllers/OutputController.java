package Controllers;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
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

import Client.Call;
import Client.Callback;
import Client.JSONRPCParamsBuilder;
import Client.Response;
import Interfaces.NoticeDialogListener;
import Interfaces.OutputListener;
import Models.Errors;
import Models.Output;
import OMVFragment.Dialogs.OutputDialogFragment;

import static java.lang.Thread.sleep;

/**
 * Created by thiba on 28/09/2016.
 */
//TODO use dialog fragment
public class OutputController extends Abstractcontroller implements DialogInterface.OnDismissListener , NoticeDialogListener {

    private AlertDialog alert;
    private OutputDialogFragment mOutputDialogFragment;
    private ProgressDialog progressDialog;
    private Handler handler;
    private Boolean mContinue;
    private final Collection<OutputListener> mListeners = new ArrayList<OutputListener>();
    public OutputController(Activity activity, Context context) {

        super(activity);
        mContinue = true;
        handler= new Handler();

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
            public void OnOMVServeurError(Call call, Errors error) {

                mOutputDialogFragment.SetMessage(error.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException, InterruptedException {

                final Output o =  response.GetResultObject(new TypeToken<Output>(){});
                handler.post(new Runnable(){
                    public void run() {

                        if(mOutputDialogFragment == null && mContinue)
                        {
                            CreateDialog(true,"Please wait ...");
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


                            mOutputDialogFragment.dismiss();
                            mOutputDialogFragment = null;
                            FireFinich();
                        }

                    }
                });
                sleep(1000);
                if(o.getRunning() &&  mContinue&&  mOutputDialogFragment != null) {

                    InternalIsRunningFile(o.getFilename());
                }

            }
        });
    }

    private void isRunningFile(String filename, Callback callBack)
    {
        try {

            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();

            //set parameters
            params.setService("Exec");
            params.setMethod("isRunning");
            params.addParam("filename",filename);
            this.enqueue(params,callBack);

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
                handler.post(new Runnable(){
                    public void run() {


                        //CreateDialogOld();
                        CreateDialog(true,"Apt update");
                    }
                });
                UpdateAlertDialog(o);
            }
        },0);
    }

    private void ProgressDialogOld()
    {

        progressDialog = ProgressDialog.show(GetActivity(), "Please wait ...",  "Task in progress ...", true);
        progressDialog.setCancelable(true);

    }

    private void CreateDialogOld(String output)
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(GetActivity());

        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                mContinue = false;
                FireFinich();
            }
        });

        builder.setOnDismissListener(OutputController.this);

        alert = builder.setTitle("Apt update").setMessage(output).show();

        //alert.setOnDismissListener(OutputController.this);
        alert.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                mContinue = false;
                FireFinich();
            }
        });

        TextView textView = (TextView) alert.findViewById(android.R.id.message);
        textView.setTextSize(10);
        textView.setTypeface(Typeface.MONOSPACE);



    }

    private void CreateDialog(Boolean justWaitCursor,String title)
    {

        Bundle bundle = new Bundle();
        bundle.putBoolean("justWaitCursor", justWaitCursor);
        bundle.putString("title", title);
        // Create an instance of the dialog fragment and show it
        mOutputDialogFragment = new OutputDialogFragment();
        //mOutputDialogFragment.setListener(this);
        mOutputDialogFragment.setArguments(bundle);
        android.app.FragmentManager fm = GetActivity().getFragmentManager();
        mOutputDialogFragment.show(fm, "FilterPluginDialogFragment");
    }

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
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();
            //set parameters
            params.setService("Exec");
            params.setMethod("getOutput");
            params.addParam("pos",pos);
            params.addParam("filename",filename);
            this.enqueue(params,callBack);
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
            public void OnOMVServeurError(Call call, Errors error) {


                String str = error.getMessage();
                mOutputDialogFragment.SetMessage("An error occurred");
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
                if(mContinue && o != null && o.getRunning() && mOutputDialogFragment != null ) {

                    UpdateAlertDialog(o);
                }

            }
        },out.getPos());

    }

    @Override
    public void onDismiss(DialogInterface dialogInterface) {

        mContinue = false;
        FireFinich();
        mOutputDialogFragment = null;
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        mContinue = false;
        FireFinich();
        mOutputDialogFragment = null;
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        mContinue = false;
        FireFinich();
        mOutputDialogFragment = null;
    }
}
