package utils;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;

import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.dev.doods.omvremote2.MyApplicationBase;
import com.dev.doods.omvremote2.R;

//import org.acra.ACRA;

import Models.Errors;

/**
 * Created by thiba on 15/12/2016.
 */

public class SnackBarError {


private View mView;
    public SnackBarError(View v, String message)
    {
        mView = v;

        InputMethodManager imm = (InputMethodManager)MyApplicationBase.getAppContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput (InputMethodManager.HIDE_IMPLICIT_ONLY, InputMethodManager.RESULT_HIDDEN);
       this.set(v,message);
    }


    public SnackBarError(View v, String message,boolean CanSendLog)
    {
        //InputMethodManager imm = (InputMethodManager)MyApplicationBase.getAppContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        //imm.toggleSoftInput (InputMethodManager.HIDE_IMPLICIT_ONLY, InputMethodManager.RESULT_HIDDEN);
        mView = v;
        if(CanSendLog)
            this.set(v,message);
        else
            this.set2(v,message);
    }


    private void set(View v, String message)
    {

        if(v == null) return ;
        Snackbar snackbar = Snackbar
                .make(v, message, Snackbar.LENGTH_LONG)
                .setAction(MyApplicationBase.getAppContext().getString(R.string.send_logs), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SendLogs();

                    }
                });

        // Changing message text color
        snackbar.setActionTextColor(Color.RED);

        // Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.YELLOW);
        snackbar.show();
    }

    private void set2(View v, String message)
    {
        if(v == null) return;
        Snackbar snackbar = Snackbar.make(v, message, Snackbar.LENGTH_LONG);

        // Changing message text color
        snackbar.setActionTextColor(Color.RED);

        // Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.YELLOW);
        snackbar.show();
    }

    private void SendLogs()
    {
        AsyncTask task = new SendLogsTask(MyApplicationBase.getAppContext());
        new CallBackAsyncTask(task, new CallBackTask() {
            @Override
            public void handleMessageError(@Nullable Errors error) {

                if(mView != null)

                new SnackBarError(mView,error.getMessage(),false);
            }

            @Override
            public void handleFinich() {
                if(mView != null)
                Snackbar.make(mView,  R.string.send, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }).run();
    }
}
