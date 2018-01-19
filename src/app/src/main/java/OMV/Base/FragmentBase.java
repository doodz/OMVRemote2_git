package OMV.Base;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.ProgressBar;

import com.dev.doods.omvremote2.R;

import Interfaces.IHandlerCallback;
import utils.SnackBarError;
import utils.SnackBarStartActivity;

/**
 * Created by Ividata7 on 08/02/2017.
 */

public class FragmentBase extends Fragment implements IHandlerCallback {

    View mContent;
    private boolean IsFinalized;
    private boolean IsOnError;
    private ProgressBar mProgress;
    public String LastError = "";

    private Handler mHandler = new Handler();


    public int BusyCount = 0;


    public void AddBusy()
    {
        if(!Isbusy())
            SetProgresVisibility(View.VISIBLE);
        BusyCount++;

    }

    public void SuppBusy()
    {
        BusyCount--;
        if(!Isbusy())
            SetProgresVisibility(View.GONE);
    }

    public Boolean Isbusy()
    {
        return BusyCount > 0;
    }

    private void SetProgresVisibility(final int i)
    {

        mHandler.post(new Runnable(){
            public void run() {

                if(mProgress != null)
                {
                    mProgress.setVisibility(i);
                }
            }
        });
    }


    public void SetFinalized(boolean finalized)
    {
        this.IsFinalized = finalized;
        SetProgresVisibility(View.GONE);

    }

    public void SetOnError(boolean onError)
    {
        IsOnError = onError;
        SetProgresVisibility(View.GONE);
    }

    public void ShowSnackError(String msg,boolean cansendLogs)
    {
        LastError = msg;
        IsOnError = true;
        SetProgresVisibility(View.GONE);
        new SnackBarError(mContent,msg,cansendLogs);
    }

    public void SnackBarStartActivity(String msg,Class<?> cls)
    {
        new SnackBarStartActivity(mContent,msg,cls);
    }

    public void showInfo(String info)
    {
        Snackbar.make(mContent, info, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();

    }
    protected boolean IsFinalized(boolean repeatError )
    {

        if(IsOnError && repeatError)
        {
            ShowSnackError(LastError,false);
        }

        return !this.IsOnError && IsFinalized;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContent=  getView().findViewById(android.R.id.content);
        mProgress = (ProgressBar)getView().findViewById(R.id.app_progress);
        if(!IsFinalized)
            SetProgresVisibility(View.VISIBLE);
    }


}
