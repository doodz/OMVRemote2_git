package OMVFragment.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
//import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dev.doods.omvremote2.MyApplicationBase;
import com.dev.doods.omvremote2.R;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.NativeExpressAdView;

import Controllers.OutputDialogController;
import Interfaces.OutputListener;
import Models.Output;

/**
 * Created by thiba on 13/12/2016.
 */

public class OutputDialogFragment extends DialogFragment {

    private Boolean justWaitCursor;
    private String mTitle;
    private String mFileName;
    private OutputListener mListener;
    private OutputDialogController mOutputDialogController = new OutputDialogController(this);
    private LinearLayout LinearLayoutDialog;

    public void show(FragmentManager manager, String tag) {
      super.show(manager,tag);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);



        Activity activity = getActivity();

        activity.isFinishing();
        // quand on ferme l'application avant l'ouverture de la popup.
        if(activity.isFinishing())
            this.dismiss();
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (OutputListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " must implement OutputListener");
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mOutput = mOutputDialogController.Stop();
    }

    private Output mOutput;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        mOutput = mOutputDialogController.Stop();
        if(mOutput != null)
            mOutput.setOutput(mMessage);
        outState.putSerializable("Output", mOutput);
        super.onSaveInstanceState(outState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Activity activity = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        // Get the layout inflater
        LayoutInflater inflater = activity.getLayoutInflater();

        Bundle bundle = getArguments();
        justWaitCursor = bundle.getBoolean("justWaitCursor");
        mTitle = bundle.getString("title");
        mFileName = bundle.getString("fileName");
        final View mView = inflater.inflate(R.layout.dialog_output, null);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(mView)
                // Add action buttons
                .setNegativeButton(getActivity().getString(R.string.action_back), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Send the negative button event back to the host activity
                        //TODO move this fiking null.
                        if(mListener != null)
                            mListener.OnFinich();
                    }
                });
        builder.setTitle(mTitle);
        mDialog = builder.create();
        mTextView = (TextView) mView.findViewById(R.id.message);
        mProgressBarView = (ProgressBar)mView.findViewById(R.id.progressBar);
        if(mMessage != null)
            mTextView.setText(mMessage);

        if(justWaitCursor)
            mOutputDialogController.IsRunningFile(mFileName);
        else
            mOutputDialogController.getOutput(mFileName);

        if(savedInstanceState != null)
        {

        }

        //adView1 = (AdView) mView.findViewById(R.id.banner1);
        //adView2 = (NativeExpressAdView) mView.findViewById(R.id.banner2);
        LinearLayoutDialog =(LinearLayout) mView.findViewById(R.id.LinearLayoutDialog);
        ShowSmal(!justWaitCursor);
        return mDialog;
    }
    AdView adView1;
    NativeExpressAdView adView2;
    private Dialog mDialog;
    private String mMessage = "";
    private TextView mTextView;
    private ProgressBar mProgressBarView;


    private void ShowSmal(Boolean b)
    {
        if(!MyApplicationBase.light)
        {
            return;
        }

        // Doods : Petite sécurité, car je n’arrive pas à déterminer le cas où l’on ne détache pas les parents des adview … ce qui cause des crashs.
        ViewParent pview = MyApplicationBase.mAdViewSmall.getParent();
        if(pview != null)
            ((ViewGroup)pview).removeView(MyApplicationBase.mAdViewSmall);

        pview = MyApplicationBase.mNativeExpressAdView.getParent();
        if(pview != null)
            ((ViewGroup)pview).removeView(MyApplicationBase.mNativeExpressAdView);

       if(b)
       {
           adView1 = MyApplicationBase.mAdViewSmall;
           LinearLayoutDialog.addView(adView1,0);
       }
        else
       {
           adView2 = MyApplicationBase.mNativeExpressAdView;
           LinearLayoutDialog.addView(adView2,0);
       }
    }


    public void onCancel(DialogInterface dialog) {
/*
        if(adView1 != null)
        {
            LinearLayoutDialog.removeView(adView1);
        }
        else
        {
            LinearLayoutDialog.removeView(adView2);
        }
*/
        super.onCancel(dialog);

    }

    public void onDismiss(DialogInterface dialog) {

        if(adView1 != null)
        {
            LinearLayoutDialog.removeView(adView1);
        }
        else
        {
            LinearLayoutDialog.removeView(adView2);
        }

       super.onDismiss(dialog);

    }


    public void SetProgressBarVisibility(Boolean visible)
    {
        mProgressBarView.setVisibility(visible?View.VISIBLE:View.GONE);
    }

    public void SetTitle(String title)
    {
        mTitle = title;
        if(mDialog != null)
        {
            mDialog.setTitle(title);
        }
    }

    public void SetMessage(String message)
    {
        mMessage = message;
        if(mTextView != null)
        {
            mTextView.setText(mMessage);
        }
    }

    public void AddMessage(String message)
    {
        mMessage = mMessage+message;
        if(mTextView != null)
        {
            mTextView.setText(mMessage);
        }
    }

    public void AddListener(OutputListener listener)
    {
        mOutputDialogController.AddListener(listener);
    }
}
