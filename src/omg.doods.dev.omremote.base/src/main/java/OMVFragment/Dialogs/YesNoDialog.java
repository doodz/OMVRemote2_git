package OMVFragment.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import Interfaces.IYesNoListenerDialog;

/**
 * Created by Ividata7 on 08/01/2017.
 */

public class YesNoDialog  extends DialogFragment
{
    IYesNoListenerDialog mCallback;
    static public int YES_NO_CALL = 112;
    public YesNoDialog()
    {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (IYesNoListenerDialog)context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement IYesNoListenerDialog.");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        Bundle args = getArguments();
        String title = args.getString("title", "");
        String message = args.getString("message", "");

        return new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        mCallback.onYesNoActivityResult(getTargetRequestCode(), Activity.RESULT_OK, null);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        mCallback.onYesNoActivityResult(getTargetRequestCode(), Activity.RESULT_CANCELED, null);
                    }
                })
                .create();
    }
}