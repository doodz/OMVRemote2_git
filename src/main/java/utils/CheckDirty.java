package utils;

import android.content.DialogInterface;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.dev.doods.omvremote2.R;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;

import Client.Call;
import Client.Callback;
import Client.Response;
import Controllers.ConfigController;
import Models.Errors;

/**
 * Created by Ividata7 on 25/01/2017.
 */

public class CheckDirty {

    private AppCompatActivity _AppCompatActivity;
    ConfigController mConfigController;
    public CheckDirty(AppCompatActivity activity)
    {
        _AppCompatActivity = activity;

        mConfigController = new ConfigController(activity);
    }


    public void Check()
    {
        mConfigController.isDirty(new Callback() {
            @Override
            public void onFailure(Call call, Exception e) {

            }

            @Override
            public void OnOMVServeurError(Call call, Errors error) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException, InterruptedException {

                boolean b = response.GetResultObject(new TypeToken<Boolean>(){});

                if(b)
                {
                    Handler mHandler = new Handler(Looper.getMainLooper()) ;

                    mHandler.post(new Runnable(){
                        public void run() {
                            ShowPopUpDirty();
                        }
                    });

                }
            }
        });

    }

    private void ShowPopUpDirty()
    {


        String title = _AppCompatActivity.getString(R.string.ApplyChanges);
       String message= _AppCompatActivity.getString(R.string.ApplyChangesMessage);

        //dialog.show(_AppCompatActivity.getSupportFragmentManager(), "CheckDirty_YesNoDialog");

        AlertDialog ad = new AlertDialog.Builder(_AppCompatActivity)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        mConfigController.applyChangesBg(null);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {

                    }
                })
                .create();
        ad.show();
    }
}
