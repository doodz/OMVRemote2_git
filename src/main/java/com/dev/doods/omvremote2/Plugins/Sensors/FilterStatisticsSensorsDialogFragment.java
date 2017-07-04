package com.dev.doods.omvremote2.Plugins.Sensors;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Switch;

import com.dev.doods.omvremote2.R;
import com.dev.doods.omvremote2.StatisticsScrollingActivity;

import Interfaces.NoticeDialogListener;
import OMVFragment.Dialogs.FilterStatisticsDialogFragment;

/**
 * Created by Ividata7 on 21/06/2017.
 */

public class FilterStatisticsSensorsDialogFragment extends DialogFragment {


    // Use this instance of the interface to deliver action events
    NoticeDialogListener mListener;


    private Switch swithSensorsCpuHour;
    private Switch swithSensorsCpuDay;
    private Switch swithSensorsCpuWeek;
    private Switch swithSensorsCpuMonth;
    private Switch swithSensorsCpuYear;

    private Switch swithMotherBoardHour;
    private Switch swithMotherBoardDay;
    private Switch swithMotherBoardWeek;
    private Switch swithMotherBoardMonth;
    private Switch swithMotherBoardYear;

    private Switch swithCPUFanSpeedHour;
    private Switch swithCPUFanSpeedDay;
    private Switch swithCPUFanSpeedWeek;
    private Switch swithCPUFanSpeedMonth;
    private Switch swithCPUFanSpeedYear;

    private Switch swithSYSFanSpeedHour;
    private Switch swithSYSFanSpeedDay;
    private Switch swithSYSFanSpeedWeek;
    private Switch swithSYSFanSpeedMonth;
    private Switch swithSYSFanSpeedYear;

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

        final View mView =inflater.inflate(R.layout.dialog_filter_statictics_sensors, null);

        _settings = getActivity().getSharedPreferences(StatisticsScrollingActivity.PREFS_STATISTICS, 0);

        swithSensorsCpuHour = (Switch) mView.findViewById(R.id.swithSensorsCpuHour);
        swithSensorsCpuHour.setChecked(_settings.getBoolean("swithSensorsCpuHour",true));

        swithSensorsCpuDay = (Switch) mView.findViewById(R.id.swithSensorsCpuDay);
        swithSensorsCpuDay.setChecked(_settings.getBoolean("swithSensorsCpuDay",true));

        swithSensorsCpuWeek = (Switch) mView.findViewById(R.id.swithSensorsCpuWeek);
        swithSensorsCpuWeek.setChecked(_settings.getBoolean("swithSensorsCpuWeek",true));

        swithSensorsCpuMonth = (Switch) mView.findViewById(R.id.swithSensorsCpuMonth);
        swithSensorsCpuMonth.setChecked(_settings.getBoolean("swithSensorsCpuMonth",true));

        swithSensorsCpuYear = (Switch) mView.findViewById(R.id.swithSensorsCpuYear);
        swithSensorsCpuYear.setChecked(_settings.getBoolean("swithSensorsCpuYear",true));


        swithMotherBoardHour = (Switch) mView.findViewById(R.id.swithMotherBoardHour);
        swithMotherBoardHour.setChecked(_settings.getBoolean("swithMotherBoardHour",true));

        swithMotherBoardDay = (Switch) mView.findViewById(R.id.swithMotherBoardDay);
        swithMotherBoardDay.setChecked(_settings.getBoolean("swithMotherBoardDay",true));

        swithMotherBoardWeek = (Switch) mView.findViewById(R.id.swithMotherBoardWeek);
        swithMotherBoardWeek.setChecked(_settings.getBoolean("swithMotherBoardWeek",true));

        swithMotherBoardMonth = (Switch) mView.findViewById(R.id.swithMotherBoardMonth);
        swithMotherBoardMonth.setChecked(_settings.getBoolean("swithMotherBoardMonth",true));

        swithMotherBoardYear = (Switch) mView.findViewById(R.id.swithMotherBoardYear);
        swithMotherBoardYear.setChecked(_settings.getBoolean("swithMotherBoardYear",true));


        swithCPUFanSpeedHour = (Switch) mView.findViewById(R.id.swithCPUFanSpeedHour);
        swithCPUFanSpeedHour.setChecked(_settings.getBoolean("swithCPUFanSpeedHour",true));

        swithCPUFanSpeedDay = (Switch) mView.findViewById(R.id.swithCPUFanSpeedDay);
        swithCPUFanSpeedDay.setChecked(_settings.getBoolean("swithCPUFanSpeedDay",true));

        swithCPUFanSpeedWeek = (Switch) mView.findViewById(R.id.swithCPUFanSpeedWeek);
        swithCPUFanSpeedWeek.setChecked(_settings.getBoolean("swithCPUFanSpeedWeek",true));

        swithCPUFanSpeedMonth = (Switch) mView.findViewById(R.id.swithCPUFanSpeedMonth);
        swithCPUFanSpeedMonth.setChecked(_settings.getBoolean("swithCPUFanSpeedMonth",true));

        swithCPUFanSpeedYear = (Switch) mView.findViewById(R.id.swithCPUFanSpeedYear);
        swithCPUFanSpeedYear.setChecked(_settings.getBoolean("swithCPUFanSpeedYear",true));


        swithSYSFanSpeedHour = (Switch) mView.findViewById(R.id.swithSYSFanSpeedHour);
        swithSYSFanSpeedHour.setChecked(_settings.getBoolean("swithSYSFanSpeedHour",true));

        swithSYSFanSpeedDay = (Switch) mView.findViewById(R.id.swithSYSFanSpeedDay);
        swithSYSFanSpeedDay.setChecked(_settings.getBoolean("swithSYSFanSpeedDay",true));

        swithSYSFanSpeedWeek = (Switch) mView.findViewById(R.id.swithSYSFanSpeedWeek);
        swithSYSFanSpeedWeek.setChecked(_settings.getBoolean("swithSYSFanSpeedWeek",true));

        swithSYSFanSpeedMonth = (Switch) mView.findViewById(R.id.swithSYSFanSpeedMonth);
        swithSYSFanSpeedMonth.setChecked(_settings.getBoolean("swithSYSFanSpeedMonth",true));

        swithSYSFanSpeedYear = (Switch) mView.findViewById(R.id.swithSYSFanSpeedYear);
        swithSYSFanSpeedYear.setChecked(_settings.getBoolean("swithSYSFanSpeedYear",true));



        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(mView)
                // Add action buttons
                .setPositiveButton("Filter", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        Save();
                        // Send the positive button event back to the host activity
                        mListener.onDialogPositiveClick(FilterStatisticsSensorsDialogFragment.this);
                    }
                })
                .setNegativeButton("Cansel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Send the negative button event back to the host activity
                        mListener.onDialogNegativeClick(FilterStatisticsSensorsDialogFragment.this);
                    }
                });
        return builder.create();
    }


    private void Save()
    {

        SharedPreferences.Editor editor = _settings.edit();
        editor.putBoolean("swithSensorsCpuHour",swithSensorsCpuHour.isChecked());
        editor.putBoolean("swithSensorsCpuDay",swithSensorsCpuDay.isChecked());
        editor.putBoolean("swithSensorsCpuWeek",swithSensorsCpuWeek.isChecked());
        editor.putBoolean("swithSensorsCpuMonth",swithSensorsCpuMonth.isChecked());
        editor.putBoolean("swithSensorsCpuYear",swithSensorsCpuYear.isChecked());

        editor.putBoolean("swithMotherBoardHour",swithMotherBoardHour.isChecked());
        editor.putBoolean("swithMotherBoardDay",swithMotherBoardDay.isChecked());
        editor.putBoolean("swithMotherBoardWeek",swithMotherBoardWeek.isChecked());
        editor.putBoolean("swithMotherBoardMonth",swithMotherBoardMonth.isChecked());
        editor.putBoolean("swithMotherBoardYear",swithMotherBoardYear.isChecked());

        editor.putBoolean("swithCPUFanSpeedHour",swithCPUFanSpeedHour.isChecked());
        editor.putBoolean("swithCPUFanSpeedDay",swithCPUFanSpeedDay.isChecked());
        editor.putBoolean("swithCPUFanSpeedWeek",swithCPUFanSpeedWeek.isChecked());
        editor.putBoolean("swithCPUFanSpeedMonth",swithCPUFanSpeedMonth.isChecked());
        editor.putBoolean("swithCPUFanSpeedYear",swithCPUFanSpeedYear.isChecked());

        editor.putBoolean("swithSYSFanSpeedHour",swithSYSFanSpeedHour.isChecked());
        editor.putBoolean("swithSYSFanSpeedDay",swithSYSFanSpeedDay.isChecked());
        editor.putBoolean("swithSYSFanSpeedWeek",swithSYSFanSpeedWeek.isChecked());
        editor.putBoolean("swithSYSFanSpeedMonth",swithSYSFanSpeedMonth.isChecked());
        editor.putBoolean("swithSYSFanSpeedYear",swithSYSFanSpeedYear.isChecked());
        editor.commit();
    }
}
