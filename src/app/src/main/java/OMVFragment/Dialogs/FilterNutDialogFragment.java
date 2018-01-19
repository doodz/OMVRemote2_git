package OMVFragment.Dialogs;

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

/**
 * Created by doods on 03/09/2017.
 */

public class FilterNutDialogFragment extends DialogFragment {


    // Use this instance of the interface to deliver action events
    NoticeDialogListener mListener;


    private Switch swithNutChargeHour;
    private Switch swithNutChargeDay;
    private Switch swithNutChargeWeek;
    private Switch swithNutChargeMonth;
    private Switch swithNutChargeYear;

    private Switch swithNutloadHour;
    private Switch swithNutloadDay;
    private Switch swithNutloadWeek;
    private Switch swithNutloadMonth;
    private Switch swithNutloadYear;

    private Switch swithNutTemperatureHour;
    private Switch swithNutTemperatureDay;
    private Switch swithNutTemperatureWeek;
    private Switch swithNutTemperatureMonth;
    private Switch swithNutTemperatureYear;

    private Switch swithNutVoltageHour;
    private Switch swithNutVoltageDay;
    private Switch swithNutVoltageWeek;
    private Switch swithNutVoltageMonth;
    private Switch swithNutVoltageYear;
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

        final View mView =inflater.inflate(R.layout.dialog_filter_nut, null);

        _settings = getActivity().getSharedPreferences(StatisticsScrollingActivity.PREFS_STATISTICS, 0);

        swithNutChargeHour = (Switch) mView.findViewById(R.id.swithNutChargeHour);
        swithNutChargeHour.setChecked(_settings.getBoolean("swithNutChargeHour",true));

        swithNutChargeDay = (Switch) mView.findViewById(R.id.swithNutChargeDay);
        swithNutChargeDay.setChecked(_settings.getBoolean("swithNutChargeDay",true));

        swithNutChargeWeek = (Switch) mView.findViewById(R.id.swithNutChargeWeek);
        swithNutChargeWeek.setChecked(_settings.getBoolean("swithNutChargeWeek",true));

        swithNutChargeMonth = (Switch) mView.findViewById(R.id.swithNutChargeMonth);
        swithNutChargeMonth.setChecked(_settings.getBoolean("swithNutChargeMonth",true));

        swithNutChargeYear = (Switch) mView.findViewById(R.id.swithNutChargeYear);
        swithNutChargeYear.setChecked(_settings.getBoolean("swithNutChargeYear",true));


        swithNutloadHour = (Switch) mView.findViewById(R.id.swithNutloadHour);
        swithNutloadHour.setChecked(_settings.getBoolean("swithNutloadHour",true));

        swithNutloadDay = (Switch) mView.findViewById(R.id.swithNutloadDay);
        swithNutloadDay.setChecked(_settings.getBoolean("swithNutloadDay",true));

        swithNutloadWeek = (Switch) mView.findViewById(R.id.swithNutloadWeek);
        swithNutloadWeek.setChecked(_settings.getBoolean("swithNutloadWeek",true));

        swithNutloadMonth = (Switch) mView.findViewById(R.id.swithNutloadMonth);
        swithNutloadMonth.setChecked(_settings.getBoolean("swithNutloadMonth",true));

        swithNutloadYear = (Switch) mView.findViewById(R.id.swithNutloadYear);
        swithNutloadYear.setChecked(_settings.getBoolean("swithNutloadYear",true));


        swithNutTemperatureHour = (Switch) mView.findViewById(R.id.swithNutTemperatureHour);
        swithNutTemperatureHour.setChecked(_settings.getBoolean("swithNutTemperatureHour",true));

        swithNutTemperatureDay = (Switch) mView.findViewById(R.id.swithNutTemperatureDay);
        swithNutTemperatureDay.setChecked(_settings.getBoolean("swithNutTemperatureDay",true));

        swithNutTemperatureWeek = (Switch) mView.findViewById(R.id.swithNutTemperatureWeek);
        swithNutTemperatureWeek.setChecked(_settings.getBoolean("swithNutTemperatureWeek",true));

        swithNutTemperatureMonth = (Switch) mView.findViewById(R.id.swithNutTemperatureMonth);
        swithNutTemperatureMonth.setChecked(_settings.getBoolean("swithNutTemperatureMonth",true));

        swithNutTemperatureYear = (Switch) mView.findViewById(R.id.swithNutTemperatureYear);
        swithNutTemperatureYear.setChecked(_settings.getBoolean("swithNutTemperatureYear",true));


        swithNutVoltageHour = (Switch) mView.findViewById(R.id.swithNutVoltageHour);
        swithNutVoltageHour.setChecked(_settings.getBoolean("swithNutVoltageHour",true));

        swithNutVoltageDay = (Switch) mView.findViewById(R.id.swithNutVoltageDay);
        swithNutVoltageDay.setChecked(_settings.getBoolean("swithNutVoltageDay",true));

        swithNutVoltageWeek = (Switch) mView.findViewById(R.id.swithNutVoltageWeek);
        swithNutVoltageWeek.setChecked(_settings.getBoolean("swithNutVoltageWeek",true));

        swithNutVoltageMonth = (Switch) mView.findViewById(R.id.swithNutVoltageMonth);
        swithNutVoltageMonth.setChecked(_settings.getBoolean("swithNutVoltageMonth",true));

        swithNutVoltageYear = (Switch) mView.findViewById(R.id.swithNutVoltageYear);
        swithNutVoltageYear.setChecked(_settings.getBoolean("swithNutVoltageYear",true));



        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(mView)
                // Add action buttons
                .setPositiveButton("Filter", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        Save();
                        // Send the positive button event back to the host activity
                        mListener.onDialogPositiveClick(FilterNutDialogFragment.this);
                    }
                })
                .setNegativeButton("Cansel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Send the negative button event back to the host activity
                        mListener.onDialogNegativeClick(FilterNutDialogFragment.this);
                    }
                });
        return builder.create();
    }


    private void Save()
    {

        SharedPreferences.Editor editor = _settings.edit();
        editor.putBoolean("swithNutChargeHour",swithNutChargeHour.isChecked());
        editor.putBoolean("swithNutChargeDay",swithNutChargeDay.isChecked());
        editor.putBoolean("swithNutChargeWeek",swithNutChargeWeek.isChecked());
        editor.putBoolean("swithNutChargeMonth",swithNutChargeMonth.isChecked());
        editor.putBoolean("swithNutChargeYear",swithNutChargeYear.isChecked());

        editor.putBoolean("swithNutloadHour",swithNutloadHour.isChecked());
        editor.putBoolean("swithNutloadDay",swithNutloadDay.isChecked());
        editor.putBoolean("swithNutloadWeek",swithNutloadWeek.isChecked());
        editor.putBoolean("swithNutloadMonth",swithNutloadMonth.isChecked());
        editor.putBoolean("swithNutloadYear",swithNutloadYear.isChecked());

        editor.putBoolean("swithNutTemperatureHour",swithNutTemperatureHour.isChecked());
        editor.putBoolean("swithNutTemperatureDay",swithNutTemperatureDay.isChecked());
        editor.putBoolean("swithNutTemperatureWeek",swithNutTemperatureWeek.isChecked());
        editor.putBoolean("swithNutTemperatureMonth",swithNutTemperatureMonth.isChecked());
        editor.putBoolean("swithNutTemperatureYear",swithNutTemperatureYear.isChecked());

        editor.putBoolean("swithNutVoltageHour",swithNutVoltageHour.isChecked());
        editor.putBoolean("swithNutVoltageDay",swithNutVoltageDay.isChecked());
        editor.putBoolean("swithNutVoltageWeek",swithNutVoltageWeek.isChecked());
        editor.putBoolean("swithNutVoltageMonth",swithNutVoltageMonth.isChecked());
        editor.putBoolean("swithNutVoltageYear",swithNutVoltageYear.isChecked());
        editor.commit();
    }
}
