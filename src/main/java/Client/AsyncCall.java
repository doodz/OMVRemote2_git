package Client;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.util.Log;

import com.dev.doods.omvremote2.HostManagerActivity;
import com.dev.doods.omvremote2.R;

import java.io.IOException;

import Interfaces.IJSONRPCParamsBuilder;
import OMV.Base.AppCompatBaseActivity;
import OMV.Base.FragmentInteractionBase;
import OMV.Base.SwipeViewBaseActivity;
import utils.SnackBarError;
import utils.SnackBarStartActivity;

/**
 * Created by thiba on 29/08/2016.
 */
public class AsyncCall extends CallImpl implements Runnable  {
    private final Callback mResponseCallback;
    private Thread t;
    private Activity mActivity;
    private JSONRPCClient mClient;
    public AsyncCall(@Nullable Callback responseCallback, IJSONRPCParamsBuilder params, Activity activity, JSONRPCClient client)
    {
        super(params);
        mActivity = activity;

        //TODO voir pour faire la meme avec les fragments SwipeViewBaseActivity?
        if(mActivity instanceof AppCompatBaseActivity)
        {
            ((AppCompatBaseActivity)mActivity).AddBusy();
        }

        mResponseCallback = responseCallback;
        mClient = client;
    }

    @Override public void run()
    {
        Response resp = null;

        try{
            resp = execute();

            if(resp.Error())
            {
                if(mResponseCallback != null)
                    mResponseCallback.OnOMVServeurError(this, resp.GetErrorObject());
            }
            else {
                if(mResponseCallback != null)
                    mResponseCallback.onResponse(this, resp);
            }

        }
        catch (MovedPermanentlyException ex)
        {
            ex.printStackTrace();
            if(mActivity != null)
            {
                new SnackBarStartActivity(mActivity.getCurrentFocus(),mActivity.getString(R.string.MovedPermanentlyException), HostManagerActivity.class);
            }

        }
        catch (IOException ex)
        {
            //ex.printStackTrace();
            String message  =   ex.getMessage();
            if(message == null) message = "IOException message error is null";

            Log.i("AsyncCall IOException",message);
            /*else
            {
                Log.i("AsyncCall IOException",ex.toString());
               Throwable my= ex.getCause();
              while (my != null && my.getCause() != null)
              {
                  message  =   my.getMessage();
                  if(message != null)
                      Log.i("AsyncCall IOException",ex.getMessage());
              }

            }*/


            if(mActivity != null)
                new SnackBarError(mActivity.getCurrentFocus(),ex.getMessage());

            if(mResponseCallback != null)
                mResponseCallback.onFailure(this,ex);

        }
        catch (InterruptedException ex)
        {
            ex.printStackTrace();
            if(mResponseCallback != null)
                mResponseCallback.onFailure(this,ex);

        }
        finally {
            mClient.Dispatcher().finished(this);
            Finish();
        }

    }

    public void Finish()
    {

        if(mActivity instanceof AppCompatBaseActivity)
        {
            ((AppCompatBaseActivity)mActivity).SuppBusy();
        }

    }
}
