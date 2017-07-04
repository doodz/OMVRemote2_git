package Controllers;

import android.app.Activity;

import Client.AsyncCall;
import Client.Callback;
import Client.JSONRPCClient;
import Client.JSONRPCParamsBuilder;

/**
 * Created by thiba on 10/11/2016.
 */

public class DaignostiquesSystemeController extends Abstractcontroller{

    public static String Diagnostique_cpu0_hour = "cpu-0-hour.png";
    public static String Diagnostique_cpu0_day = "cpu-0-day.png";
    public static String Diagnostique_cpu0_week = "cpu-0-week.png";
    public static String Diagnostique_cpu0_month = "cpu-0-month.png";
    public static String Diagnostique_cpu0_year = "cpu-0-year.png";


    public static String Diagnostique_load_average_hour = "load-hour.png";
    public static String Diagnostique_load_average_day = "load-day.png";
    public static String Diagnostique_load_average_week = "load-week.png";
    public static String Diagnostique_load_average_month = "load-month.png";
    public static String Diagnostique_load_average_year = "load-year.png";

    public static String Diagnostique_interface_eth0_hour = "interface-eth0-hour.png";
    public static String Diagnostique_interface_eth0_day = "interface-eth0-day.png";
    public static String Diagnostique_interface_eth0_week = "interface-eth0-week.png";
    public static String Diagnostique_interface_eth0_month = "interface-eth0-month.png";
    public static String Diagnostique_interface_eth0_year = "interface-eth0-year.png";

    public static String Diagnostique_interface_xxxx_hour = "interface-xxxx-hour.png";
    public static String Diagnostique_interface_xxxx_day = "interface-xxxx-day.png";
    public static String Diagnostique_interface_xxxx_week = "interface-xxxx-week.png";
    public static String Diagnostique_interface_xxxx_month = "interface-xxxx-month.png";
    public static String Diagnostique_interface_xxxx_year = "interface-xxxx-year.png";

    public static String Diagnostique_memory_hour = "memory-hour.png";
    public static String Diagnostique_memory_day = "memory-day.png";
    public static String Diagnostique_memory_week = "memory-week.png";
    public static String Diagnostique_memory_month = "memory-month.png";
    public static String Diagnostique_memory_year = "memory-year.png";

    //Cpu Temps
    public static String Diagnostique_sensors_hour="sensors-hour.png";
    public static String Diagnostique_sensors_day="sensors-day.png";
    public static String Diagnostique_sensors_week="sensors-week.png";
    public static String Diagnostique_sensors_month="sensors-month.png";
    public static String Diagnostique_sensors_year="sensors-year.png";

    //MotherBoard Temp
    public static String Diagnostique_mb_hour="mb-hour.png";
    public static String Diagnostique_mb_day="mb-day.png";
    public static String Diagnostique_mb_week="mb-week.png";
    public static String Diagnostique_mb_month="mb-month.png";
    public static String Diagnostique_mb_year="mb-year.png";

    //CPU fan speed
    public static String Diagnostique_fan_hour="fan-hour.png";
    public static String Diagnostique_fan_day="fan-day.png";
    public static String Diagnostique_fan_week="fan-week.png";
    public static String Diagnostique_fan_month="fan-month.png";
    public static String Diagnostique_fan_year="fan-year.png";

    //SYS fan speed
    public static String Diagnostique_sys_hour="sys-hour.png";
    public static String Diagnostique_sys_day="sys-day.png";
    public static String Diagnostique_sys_week="sys-week.png";
    public static String Diagnostique_sys_month="sys-month.png";
    public static String Diagnostique_sys_year="sys-year.png";



    // Disque usage ?!?

    public DaignostiquesSystemeController(Activity activity) {
        super(activity);
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
