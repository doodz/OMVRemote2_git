package OMVFragment.Dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dev.doods.base.R;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

import Client.Call;
import Client.Callback;
import Client.Response;
import Controllers.omvExtrasController;

import Interfaces.NoticeDialogListener;
import Models.CustomRepo;
import Models.Errors;

/**
 * Created by thiba on 13/11/2016.
 */

public class AddEditeCustomRepoDialogFragment extends DialogFragment {

    private NoticeDialogListener mListener;
    private omvExtrasController mController = new omvExtrasController(getActivity());
    EditText mCustomRepoName;
    EditText mCustomRepoRepo;
    EditText mCustomRepoKey;
    EditText mCustomRepoComment;

    private CustomRepo mCustomrepo;


    public void setListener(NoticeDialogListener listener)
    {
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (NoticeDialogListener) listener;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(listener.toString()
                    + " must implement NoticeDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        Bundle bundle = getArguments();

        if(bundle != null && bundle.containsKey("CustomRepo"))
            mCustomrepo = (CustomRepo) bundle.getSerializable("CustomRepo");
        else
            mCustomrepo = new CustomRepo();
        final View mView = inflater.inflate(R.layout.dialog_add_edite_custom_repo, null);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(mView)
                // Add action buttons
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //EditText et = (EditText)mView.findViewById(R.id.filterOnName);

                        // Send the positive button event back to the host activity
                        mListener.onDialogPositiveClick(AddEditeCustomRepoDialogFragment.this);
                    }
                })
                .setNegativeButton("Cansel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Send the negative button event back to the host activity
                        mListener.onDialogNegativeClick(AddEditeCustomRepoDialogFragment.this);
                    }
                });


        mCustomRepoName = (EditText) mView.findViewById(R.id.customRepoName);
        mCustomRepoRepo = (EditText) mView.findViewById(R.id.customRepoRepo);
        mCustomRepoKey = (EditText) mView.findViewById(R.id.customRepoKey);
        mCustomRepoComment = (EditText) mView.findViewById(R.id.customRepoComment);
        PopulateViews();
        return builder.create();
    }


    private void PopulateViews()
    {

        mCustomRepoName.setText(mCustomrepo.getName());
        mCustomRepoRepo.setText(mCustomrepo.getRepo());
        mCustomRepoKey.setText(mCustomrepo.getKey());
        mCustomRepoComment.setText(mCustomrepo.getComment());
    }

    private void PopulateCustomRepo()
    {

        mCustomrepo.setName(mCustomRepoName.getText().toString());
        mCustomrepo.setRepo(mCustomRepoRepo.getText().toString());
        mCustomrepo.setKey(mCustomRepoKey.getText().toString());
        mCustomrepo.setComment(mCustomRepoComment.getText().toString());

    }


    private boolean Validate()
    {
        boolean mReturn = true;
        String str = mCustomRepoName.getText().toString();
        if(StringUtils.isEmpty(str))
        {
            mCustomRepoName.setError(getString(R.string.error_field_required));
            mReturn = false;
        }
        else
            mCustomRepoName.setError(null);

        str = mCustomRepoRepo.getText().toString();
        if(StringUtils.isEmpty(str))
        {
            mCustomRepoRepo.setError(getString(R.string.error_field_required));
            mReturn = false;
        }
        else
            mCustomRepoRepo.setError(null);

        str = mCustomRepoComment.getText().toString();
        if(StringUtils.isEmpty(str))
        {
            mCustomRepoComment.setError(getString(R.string.error_field_required));
            mReturn = false;
        }
        else
            mCustomRepoComment.setError(null);
        return mReturn;
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



                    if(!Validate()) return;
                    PopulateCustomRepo();

                    mController.setCustom(mCustomrepo, new Callback() {
                        @Override
                        public void onFailure(Call call, Exception e) {

                        }

                        @Override
                        public void OnOMVServeurError(Call call, Errors error) {
                            Snackbar.make(mCustomRepoName, error.getMessage(), Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException, InterruptedException {

                            // Send the positive button event back to the host activity
                            mListener.onDialogPositiveClick(AddEditeCustomRepoDialogFragment.this);

                            //Do stuff, possibly set wantToCloseDialog to true then...

                            dismiss();
                            //else dialog stays open. Make sure you have an obvious way to close the dialog especially if you set cancellable to false.
                        }
                    });



                }
            });
        }
    }
}
