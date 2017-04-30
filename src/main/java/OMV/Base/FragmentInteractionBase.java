package OMV.Base;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ProgressBar;

import com.dev.doods.omvremote2.R;

import Controllers.ShareMgmtController;
import Interfaces.IFragmentButton;
import Interfaces.IFragmentInteraction;
import Interfaces.IHandlerCallback;
import Interfaces.OnFragmentInteractionListener;
import utils.SnackBarError;

/**
 * Created by thiba on 31/10/2016.
 */

public class FragmentInteractionBase extends Fragment implements IFragmentInteraction, IHandlerCallback {

    public Handler mHandler = new Handler();

    private OnFragmentInteractionListener mListener;
    public Boolean HaveUpdateMethode = false;
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
        if(BusyCount>0) BusyCount = 0;
        if(!Isbusy())
            SetProgresVisibility(View.GONE);
    }
    private ProgressBar mProgress;
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

    public Boolean Isbusy()
    {
        return BusyCount > 0;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
    public void OnMessage(String msg)
    {
        if (mListener != null) {
            mListener.onFragmentMessage(msg);
        }
    }
    public void OnError(String msg)
    {
        if (mListener != null) {
            mListener.onFragmentMessageError(msg);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }

    }


    public void FindProgressBarr(View v) {
        mProgress = (ProgressBar) v.findViewById(R.id.app_progress);
        if (!IsFinalized)
            SetProgresVisibility(View.VISIBLE);
        super.onStart();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void Update() {


    }

    public boolean HaveUpdateMethode()
    {
       return HaveUpdateMethode;
    }

    protected boolean IsFinalized(boolean repeatError )
    {

        if(IsOnError && repeatError)
        {
            ShowSnackError(LastError,false);
        }

        return !IsOnError && IsFinalized;
    }
    private boolean IsFinalized;
    private boolean IsOnError;
    public String LastError = "";
    protected View mContent;
    @Override
    public int GetIconActionId() {
        return 0;
    }

    @Override
    public void SetFinalized(boolean finalized)
    {
        this.IsFinalized = finalized;
        //SetProgresVisibility(View.GONE);

    }
    @Override
    public void SetOnError(boolean onError)
    {
        IsOnError = onError;
        //SetProgresVisibility(View.GONE);
    }
    @Override
    public void ShowSnackError(String msg,boolean cansendLogs)
    {
        LastError = msg;
        IsOnError = true;
        //SetProgresVisibility(View.GONE);
        new SnackBarError(mContent,msg,cansendLogs);
    }
}
