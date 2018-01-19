package OMVFragment.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.dev.doods.base.R;

import java.util.ArrayList;
import java.util.List;

import Interfaces.NoticeDialogListener;
import Models.FilterPlugin;

/**
 * Created by thiba on 08/10/2016.
 */

public class FilterPluginDialogFragment extends DialogFragment {

    public FilterPlugin filters;
    private List<String> _lst;


    // Use this instance of the interface to deliver action events
    NoticeDialogListener mListener;

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

        Bundle bundle = getArguments();
        filters = (FilterPlugin) bundle.getSerializable("FilterPlugin");
        final View mView =inflater.inflate(R.layout.dialog_filter_plugin, null);

       RadioButton rb = (RadioButton)mView.findViewById(R.id.radioButtonAll);
        rb.setChecked((filters.FilterOnInstaled == null));
        rb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b) filters.FilterOnInstaled = null;
            }
        });

        rb = (RadioButton)mView.findViewById(R.id.radioButtonInsalled);
        if(filters.FilterOnInstaled != null)
            rb.setChecked((filters.FilterOnInstaled));
        rb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b) filters.FilterOnInstaled = true;
            }
        });

        rb = (RadioButton)mView.findViewById(R.id.radioButtonNotInstalled);
        //rb.setChecked((filters.FilterOnInstaled == null));
        rb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b) filters.FilterOnInstaled = false;
            }
        });


        EditText et = (EditText)mView.findViewById(R.id.filterOnName);
        et.setText(filters.FilterOnName);

        Spinner spi = (Spinner)mView.findViewById(R.id.spinnerPluginSection);


        _lst = new ArrayList<String>();
        _lst.add("All");
        _lst.addAll(filters.PluginsectionList);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,_lst);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spi.setAdapter(dataAdapter);


        spi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String val =  _lst.get(i);

                if(val.equals("All"))
                    filters.FilterOnPluginSection = null;
                else
                    filters.FilterOnPluginSection = val;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(mView)
                // Add action buttons
                .setPositiveButton("Filter", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        EditText et = (EditText)mView.findViewById(R.id.filterOnName);
                        filters.FilterOnName = et.getText().toString();

                        if(filters.FilterOnName.equals("") ) filters.FilterOnName = null;

                        // Send the positive button event back to the host activity
                        mListener.onDialogPositiveClick(FilterPluginDialogFragment.this);
                    }
                })
                .setNegativeButton("Cansel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Send the negative button event back to the host activity
                        mListener.onDialogNegativeClick(FilterPluginDialogFragment.this);
                    }
                });
        return builder.create();
    }
}
