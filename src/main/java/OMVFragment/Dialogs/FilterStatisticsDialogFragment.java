package OMVFragment.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
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
import android.widget.Switch;

import com.dev.doods.omvremote2.R;
import com.dev.doods.omvremote2.StatisticsScrollingActivity;

import java.util.ArrayList;

import Interfaces.NoticeDialogListener;
import Models.FilterPlugin;

/**
 * Created by Ividata7 on 30/04/2017.
 */

public class FilterStatisticsDialogFragment extends DialogFragment {


    // Use this instance of the interface to deliver action events
    NoticeDialogListener mListener;


    private Switch swithCpuHour;
    private Switch swithCpuDay;
    private Switch swithCpuWeek;
    private Switch swithCpuMonth;
    private Switch swithCpuYear;

    private Switch swithInterfaceHour;
    private Switch swithInterfaceDay;
    private Switch swithInterfacesWeek;
    private Switch swithInterfacesMonth;
    private Switch swithInterfacesYear;

    private Switch swithLoadAverageHour;
    private Switch swithLoadAverageDay;
    private Switch swithLoadAverageWeek;
    private Switch swithLoadAverageMonth;
    private Switch swithLoadAverageYear;

    private Switch swithMemoryHour;
    private Switch swithMemoryDay;
    private Switch swithMemoryWeek;
    private Switch swithMemoryMonth;
    private Switch swithMemoryYear;
    SharedPreferences _settings;

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (NoticeDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " must implement NoticeDialogListener");
        }
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        final View mView =inflater.inflate(R.layout.dialog_filter_statistics, null);

        _settings = getActivity().getSharedPreferences(StatisticsScrollingActivity.PREFS_STATISTICS, 0);

        swithCpuHour = (Switch) mView.findViewById(R.id.swithCpuHour);
        swithCpuHour.setChecked(_settings.getBoolean("swithCpuHour",true));

        swithCpuDay = (Switch) mView.findViewById(R.id.swithCpuDay);
        swithCpuDay.setChecked(_settings.getBoolean("swithCpuDay",true));

        swithCpuWeek = (Switch) mView.findViewById(R.id.swithCpuWeek);
        swithCpuWeek.setChecked(_settings.getBoolean("swithCpuWeek",true));

        swithCpuMonth = (Switch) mView.findViewById(R.id.swithCpuMonth);
        swithCpuMonth.setChecked(_settings.getBoolean("swithCpuMonth",true));

        swithCpuYear = (Switch) mView.findViewById(R.id.swithCpuYear);
        swithCpuYear.setChecked(_settings.getBoolean("swithCpuYear",true));


        swithInterfaceHour = (Switch) mView.findViewById(R.id.swithInterfaceHour);
        swithInterfaceHour.setChecked(_settings.getBoolean("swithInterfaceHour",true));

        swithInterfaceDay = (Switch) mView.findViewById(R.id.swithInterfaceDay);
        swithInterfaceDay.setChecked(_settings.getBoolean("swithInterfaceDay",true));

        swithInterfacesWeek = (Switch) mView.findViewById(R.id.swithInterfacesWeek);
        swithInterfacesWeek.setChecked(_settings.getBoolean("swithInterfacesWeek",true));

        swithInterfacesMonth = (Switch) mView.findViewById(R.id.swithInterfacesMonth);
        swithInterfacesMonth.setChecked(_settings.getBoolean("swithInterfacesMonth",true));

        swithInterfacesYear = (Switch) mView.findViewById(R.id.swithInterfacesYear);
        swithInterfacesYear.setChecked(_settings.getBoolean("swithInterfacesYear",true));


        swithLoadAverageHour = (Switch) mView.findViewById(R.id.swithLoadAverageHour);
        swithLoadAverageHour.setChecked(_settings.getBoolean("swithLoadAverageHour",true));

        swithLoadAverageDay = (Switch) mView.findViewById(R.id.swithLoadAverageDay);
        swithLoadAverageDay.setChecked(_settings.getBoolean("swithLoadAverageDay",true));

        swithLoadAverageWeek = (Switch) mView.findViewById(R.id.swithLoadAverageWeek);
        swithLoadAverageWeek.setChecked(_settings.getBoolean("swithLoadAverageWeek",true));

        swithLoadAverageMonth = (Switch) mView.findViewById(R.id.swithLoadAverageMonth);
        swithLoadAverageMonth.setChecked(_settings.getBoolean("swithLoadAverageMonth",true));

        swithLoadAverageYear = (Switch) mView.findViewById(R.id.swithLoadAverageYear);
        swithLoadAverageYear.setChecked(_settings.getBoolean("swithLoadAverageYear",true));


        swithMemoryHour = (Switch) mView.findViewById(R.id.swithMemoryHour);
        swithMemoryHour.setChecked(_settings.getBoolean("swithMemoryHour",true));

        swithMemoryDay = (Switch) mView.findViewById(R.id.swithMemoryDay);
        swithMemoryDay.setChecked(_settings.getBoolean("swithMemoryDay",true));

        swithMemoryWeek = (Switch) mView.findViewById(R.id.swithMemoryWeek);
        swithMemoryWeek.setChecked(_settings.getBoolean("swithMemoryWeek",true));

        swithMemoryMonth = (Switch) mView.findViewById(R.id.swithMemoryMonth);
        swithMemoryMonth.setChecked(_settings.getBoolean("swithMemoryMonth",true));

        swithMemoryYear = (Switch) mView.findViewById(R.id.swithMemoryYear);
        swithMemoryYear.setChecked(_settings.getBoolean("swithMemoryYear",true));



        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(mView)
                // Add action buttons
                .setPositiveButton("Filter", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        Save();
                        // Send the positive button event back to the host activity
                       mListener.onDialogPositiveClick(FilterStatisticsDialogFragment.this);
                    }
                })
                .setNegativeButton("Cansel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Send the negative button event back to the host activity
                        mListener.onDialogNegativeClick(FilterStatisticsDialogFragment.this);
                    }
                });
        return builder.create();
    }


    private void Save()
    {

        SharedPreferences.Editor editor = _settings.edit();
        editor.putBoolean("swithCpuHour",swithCpuHour.isChecked());
        editor.putBoolean("swithCpuDay",swithCpuDay.isChecked());
        editor.putBoolean("swithCpuWeek",swithCpuWeek.isChecked());
        editor.putBoolean("swithCpuMonth",swithCpuMonth.isChecked());
        editor.putBoolean("swithCpuYear",swithCpuYear.isChecked());

        editor.putBoolean("swithInterfaceHour",swithInterfaceHour.isChecked());
        editor.putBoolean("swithInterfaceDay",swithInterfaceDay.isChecked());
        editor.putBoolean("swithInterfacesWeek",swithInterfacesWeek.isChecked());
        editor.putBoolean("swithInterfacesMonth",swithInterfacesMonth.isChecked());
        editor.putBoolean("swithInterfacesYear",swithInterfacesYear.isChecked());

        editor.putBoolean("swithLoadAverageHour",swithLoadAverageHour.isChecked());
        editor.putBoolean("swithLoadAverageDay",swithLoadAverageDay.isChecked());
        editor.putBoolean("swithLoadAverageWeek",swithLoadAverageWeek.isChecked());
        editor.putBoolean("swithLoadAverageMonth",swithLoadAverageMonth.isChecked());
        editor.putBoolean("swithLoadAverageYear",swithLoadAverageYear.isChecked());

        editor.putBoolean("swithMemoryHour",swithMemoryHour.isChecked());
        editor.putBoolean("swithMemoryDay",swithMemoryDay.isChecked());
        editor.putBoolean("swithMemoryWeek",swithMemoryWeek.isChecked());
        editor.putBoolean("swithMemoryMonth",swithMemoryMonth.isChecked());
        editor.putBoolean("swithMemoryYear",swithMemoryYear.isChecked());
        editor.commit();
    }
}
