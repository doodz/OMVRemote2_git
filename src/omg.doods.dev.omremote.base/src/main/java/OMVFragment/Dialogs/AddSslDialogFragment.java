package OMVFragment.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.dev.doods.base.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import Controllers.CertificateController;
import Interfaces.NoticeDialogListener;
import Models.SslCertificateInfoCreate;

/**
 * Created by thiba on 11/12/2016.
 */

public class AddSslDialogFragment extends DialogFragment {


    public static final List<String> KeysSizesString = new ArrayList<String>(){{add("512b");add("1024b");add("2048b");add("4096b");}};
    public static final List<Integer> KeysSizesVal = new ArrayList<Integer>(){{add(512);add(1024);add(2048);add(4096);}};

    public static final List<String> PedioValidityString = new ArrayList<String>(){{
        add("1 day");add("2 days");add("3 days");add("4 days");add("5 days");add("6 days");
        add("1 week");add("2 weeks");add("3 weeks");
        add("1 month");add("3 months");add("6 months");add("9 months");
        add("1 year");add("2 years");add("3 years");add("4 years");add("5 years");add("10 years");
        add("15 years");add("20 years");add("25 years");
    }};

    public static final List<Integer> PedioValiditySize = new ArrayList<Integer>(){{
        add(1); add(2);add(3);add(4);add(5);add(6);add(7);
        add(14);add(21);add(30);add(90);add(180);add(270);add(365);add(730);
        add(1095);add(1460);add(1825);add(3650);add(5475);add(7300);add(9125);
    }};
    CertificateController mController = new CertificateController(getActivity());
    NoticeDialogListener mListener;
    public EditText mComment;
    SslCertificateInfoCreate certif;
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
        final View mView = inflater.inflate(R.layout.dialog_add_ssl_certificate, null);

        Bundle bundle = getArguments();

        if(bundle.containsKey("SslCertificateInfoCreate")) {
            certif =(SslCertificateInfoCreate) bundle.getSerializable("SslCertificateInfoCreate");
            //mComment.setText(certif.getComment());
        }
        else
            certif = new SslCertificateInfoCreate();

        getViews(mView);
        populateView();
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(mView)
                // Add action buttons
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //EditText et = (EditText)mView.findViewById(R.id.filterOnName);
                        //String cmt =mComment.getText().toString();
                        /*
                        if (TextUtils.isEmpty(cmt)) {
                            mComment.setError(getString(R.string.error_field_required));
                            mComment.requestFocus();
                            return;
                        }
                        */

                        populateCertif();
                        mController.CreateSsl(certif,null);
                        // Send the positive button event back to the host activity
                        mListener.onDialogPositiveClick(AddSslDialogFragment.this);
                    }
                })
                .setNegativeButton("Cansel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Send the negative button event back to the host activity
                        mListener.onDialogNegativeClick(AddSslDialogFragment.this);
                    }
                });

        builder.setTitle(R.string.action_add_certificate_ssl);
        return builder.create();

    }

    Spinner mSpinnerRsaLength;
    Spinner mSpinnerValidity;
    EditText mCommonName;
    EditText mOrganizationName;
    EditText mOrganisationUnit;
    EditText mCity;
    EditText mState;
    Spinner mSpinnerCountry;
    EditText mEmail;

    private void getViews(View mView)
    {

        mSpinnerRsaLength = (Spinner)mView.findViewById(R.id.spinnerRsaLength);
        mSpinnerValidity = (Spinner)mView.findViewById(R.id.spinnerValidity);
        mCommonName = (EditText)mView.findViewById(R.id.etCommonName);
        mOrganizationName = (EditText)mView.findViewById(R.id.etOrganizationName);
        mOrganisationUnit = (EditText)mView.findViewById(R.id.etOrganisationUnit);
        mCity = (EditText)mView.findViewById(R.id.etCity);
        mState = (EditText)mView.findViewById(R.id.etState);
        mSpinnerCountry = (Spinner)mView.findViewById(R.id.spinnerCountry);
        mEmail = (EditText) mView.findViewById(R.id.etEmail);

    }


    private void populateCertif()
    {

        int i = mSpinnerCountry.getSelectedItemPosition();
        certif.setC(CountryCode.get(i));

        certif.setCn(mCommonName.getText().toString());

        i = mSpinnerRsaLength.getSelectedItemPosition();
        certif.setSize(KeysSizesVal.get(i));

        i = mSpinnerValidity.getSelectedItemPosition();
        certif.setDays(PedioValiditySize.get(i));

        certif.setO(mOrganizationName.getText().toString());
        certif.setOu(mOrganisationUnit.getText().toString());
        certif.setL(mCity.getText().toString());
        certif.setSt(mState.getText().toString());
        certif.setEmail(mEmail.getText().toString());

    }

    private void populateView()
    {
        ArrayAdapter<String> mSpinnerRsaLengthAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, KeysSizesString);
        mSpinnerRsaLength.setAdapter(mSpinnerRsaLengthAdapter);
        ArrayAdapter<String> mSpinnerValidityAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, PedioValidityString);
         mSpinnerValidity.setAdapter(mSpinnerValidityAdapter);
        if(certif.getCn() != null)
         mCommonName.setText(certif.getCn());
        if(certif.getO() != null)
         mOrganizationName.setText(certif.getO());
        if(certif.getOu() != null)
         mOrganisationUnit.setText(certif.getOu());
        if(certif.getL() != null)
         mCity.setText(certif.getL());
        if(certif.getSt() != null)
         mState.setText(certif.getSt());
        if(certif.getEmail() != null)
            mEmail.setText(certif.getEmail());


        getListOfCountries();
        ArrayAdapter<String> mSpinnerCountryAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, CountryName);
         mSpinnerCountry.setAdapter(mSpinnerCountryAdapter);

    }


    private ArrayList<String>  CountryName;
    private ArrayList<String>  CountryCode;
    public void getListOfCountries()
    {

        CountryName = new ArrayList<String>();
        CountryCode = new ArrayList<String>();
        String[] locales = Locale.getISOCountries();
        for (String countryCode : locales) {

            Locale obj = new Locale("", countryCode);
            CountryName.add(obj.getDisplayCountry());
            CountryCode.add(obj.getCountry());
        }

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


                    populateCertif();
                    mController.CreateSsl(certif,null);
                    // Send the positive button event back to the host activity
                    mListener.onDialogPositiveClick(AddSslDialogFragment.this);
                    dismiss();
                }
            });
        }
    }
}
