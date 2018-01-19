package OMVFragment.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dev.doods.omvremote2.R;

import Controllers.CertificateController;
import Interfaces.NoticeDialogListener;
import Models.CertificateSsh;

/**
 * Created by thiba on 10/12/2016.
 */

public class AddSshDialogFragment extends DialogFragment {

    CertificateController mController = new CertificateController(getActivity());
    NoticeDialogListener mListener;
    public EditText mComment;
    CertificateSsh certif;
    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (NoticeDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View mView = inflater.inflate(R.layout.dialog_add_ssh_certificate, null);
        mComment = (EditText) mView.findViewById(R.id.Comment);
        Bundle bundle = getArguments();

        if(bundle.containsKey("CertificateSsh")) {
            certif =(CertificateSsh) bundle.getSerializable("CertificateSsh");
            mComment.setText(certif.getComment());
        }

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(mView)
                // Add action buttons
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //EditText et = (EditText)mView.findViewById(R.id.filterOnName);
                        String cmt =mComment.getText().toString();
                        if (TextUtils.isEmpty(cmt)) {
                            mComment.setError(getString(R.string.error_field_required));
                            mComment.requestFocus();
                           return;
                        }
                        // Send the positive button event back to the host activity
                        mListener.onDialogPositiveClick(AddSshDialogFragment.this);
                    }
                })
                .setNegativeButton("Cansel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Send the negative button event back to the host activity
                        mListener.onDialogNegativeClick(AddSshDialogFragment.this);
                    }
                });
        builder.setTitle(R.string.action_add_certificate_ssh);
        return builder.create();

    }

    @Override
    public void onStart()
    {
        super.onStart();    //super.onStart() is where dialog.show() is actually called on the underlying dialog, so we have to do it after this point
        AlertDialog d = (AlertDialog)getDialog();
        if(d != null)
        {
            Button positiveButton = (Button) d.getButton(Dialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    String cmt =mComment.getText().toString();
                    if (TextUtils.isEmpty(cmt)) {
                        mComment.setError(getString(R.string.error_field_required));
                        mComment.requestFocus();
                        return;
                    }
                    mController.CreateSsh(cmt,null);
                    // Send the positive button event back to the host activity
                    mListener.onDialogPositiveClick(AddSshDialogFragment.this);
                    dismiss();
                }
            });
        }
    }
}
