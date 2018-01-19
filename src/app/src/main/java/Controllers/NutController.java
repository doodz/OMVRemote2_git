package Controllers;

import android.app.Activity;

import Client.Callback;
import Client.JSONRPCParamsBuilder;

/**
 * Created by thiba on 10/11/2016.
 */

public class NutController extends Abstractcontroller{

    public static String Nut_charge_hour = "nut-charge-hour.png";
    public static String Nut_charge_day = "nut-charge-day.png";
    public static String Nut_charge_week = "nut-charge-week.png";
    public static String Nut_charge_month = "nut-charge-month.png";
    public static String Nut_charge_year = "nut-charge-year.png";


    public static String Nut_load_hour = "nut-load-hour.png";
    public static String Nut_load_day = "nut-load-day.png";
    public static String Nut_load_week = "nut-load-week.png";
    public static String Nut_load_month = "nut-load-month.png";
    public static String Nut_load_year = "nut-load-year.png";

    public static String Nut_temperature_hour = "nut-temperature-hour.png";
    public static String Nut_temperature_day = "nut-temperature-day.png";
    public static String Nut_temperature_week = "nut-temperature-week.png";
    public static String Nut_temperature_month = "nut-temperature-month.png";
    public static String Nut_temperature_year = "nut-temperature-year.png";


    public static String Nut_voltage_hour = "nut-voltage-hour.png";
    public static String Nut_voltage_day = "nut-voltage-day.png";
    public static String Nut_voltage_week = "nut-voltage-week.png";
    public static String Nut_voltage_month = "nut-voltage-month.png";
    public static String Nut_voltage_year = "nut-voltage-year.png";





    // Disque usage ?!?

    public NutController(Activity activity) {
        super(activity);
    }


    public void getSettings(Callback callBack)
    {
        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();
            //set parameters
            params.setService("Nut");
            params.setMethod("get");
            this.enqueue(params,callBack);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void generateRRD(Callback callBack)
    {

        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();
            //set parameters
            params.setService("Rrd");
            params.setMethod("generate");
            this.enqueue(params,callBack);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
