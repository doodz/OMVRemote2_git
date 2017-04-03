package Controllers;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

import Client.Callback;
import Client.JSONRPCParamsBuilder;
import Models.Cron;

/**
 * Created by thiba on 19/12/2016.
 */

public class CronController  extends Abstractcontroller {

    public static List<String> TimeExecVal = new ArrayList<String>(){{add("Certain date");add("Hourly");
        add("Daily");add("Weekly");        add("Monthly");add("Yearly");        add("At reboot");}};

    public static List<String> TimeExecValServer = new ArrayList<String>(){{add("exactly");add("hourly");
        add("daily");add("weekly");        add("monthly");add("yearly");        add("reboot");}};

    public static List<String> MinuteVal = new ArrayList<String>(){{add("*");add("0");add("1");
        add("2");add("3");        add("4");add("5");        add("6");add("7");
        add("8");add("9");        add("10");add("11");        add("12");add("13");
        add("14");add("15");        add("16");add("17");        add("18");add("19");
        add("20");add("21");        add("22");add("23");        add("24");add("25");
        add("26");add("27");        add("28");add("29");        add("30");add("31");
        add("32");add("33");        add("34");add("35");        add("36");add("37");
        add("38");add("39");        add("40");add("41");        add("42");add("43");
        add("44");add("45");        add("46");add("47");        add("48");add("49");
        add("50");add("51");        add("52");add("53");        add("54");add("55");
        add("56");add("57");        add("58");add("59");
    }};

    public static List<String> HourVal = new ArrayList<String>(){{add("*");add("0");add("1");
        add("2");add("3");        add("4");add("5");        add("6");add("7");
        add("8");add("9");        add("10");add("11");        add("12");add("13");
        add("14");add("15");        add("16");add("17");        add("18");add("19");
        add("20");add("21");        add("22");add("23");
    }};

    public static List<String> DayVal = new ArrayList<String>(){{add("*");add("1");
        add("2");add("3");        add("4");add("5");        add("6");add("7");
        add("8");add("9");        add("10");add("11");        add("12");add("13");
        add("14");add("15");        add("16");add("17");        add("18");add("19");
        add("20");add("21");        add("22");add("23");          add("24");add("25");
        add("26");add("27");        add("28");add("29");        add("30");add("31");
    }};

    public static List<String> MonthVal = new ArrayList<String>(){
        {
            add("*");
            add("January");
            add("February");
            add("March");
            add("April");
            add("May");
            add("June");
            add("July");
            add("August");
            add("September");
            add("October");
            add("November");
            add("December");

        }};

    public CronController(Activity activity) {
        super(activity);
    }

    public void GetCronList(Callback callBack)
    {
        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();
            //set parameters
            params.setService("Cron");
            params.setMethod("getList");
            params.addParam("limit",25);
            params.addParam("sortdir","ASC");
            params.addParam("sortfield","enable");
            params.addParam("start",0);
            params.addParam("type", "[\"userdefined\"]");
            this.enqueue(params,callBack);
        }
        catch (Exception e)
        {
            e.printStackTrace();

        }
    }

    public void EnumerateAllUsers(Callback callBack)
    {
        //List<OmvUser>();
        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();
            //set parameters
            params.setService("UserMgmt");
            params.setMethod("enumerateAllUsers");
            this.enqueue(params,callBack);
        }
        catch (Exception e)
        {
            e.printStackTrace();

        }
    }


    public void setCron(Cron cron, Callback callBack)
    {

        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();
            //set parameters
            params.setService("Cron");
            params.setMethod("set");

            params.addParam("command",cron.getCommand());
            params.addParam("comment",cron.getComment());
            params.addParam("dayofmonth",cron.getDayofmonth());
            params.addParam("dayofweek",cron.getDayofweek());
            params.addParam("enable", cron.getEnable()?"mTrue":"mFalse");
            params.addParam("everyndayofmonth",cron.getEveryndayofmonth()?"mTrue":"mFalse");
            params.addParam("everynhour",cron.getEverynhour()?"mTrue":"mFalse");
            params.addParam("everynminute",cron.getEverynminute()?"mTrue":"mFalse");
            params.addParam("execution",cron.getExecution());
            params.addParam("hour",cron.getHour());
            params.addParam("minute",cron.getMinute());
            params.addParam("month",cron.getMonth());
            params.addParam("sendemail",cron.getSendemail()?"mTrue":"mFalse");
            params.addParam("type","userdefined");
            params.addParam("username", cron.getUsername());
            params.addParam("uuid",(cron.getUuid() == null ||cron.getUuid() == "")? "undefined":cron.getUuid() );

            this.enqueue(params,callBack);
        }
        catch (Exception e)
        {
            e.printStackTrace();

        }

    }


    public void deleteCron(String uuid, Callback callBack)
    {
        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();
            //set parameters
            params.setService("Cron");
            params.setMethod("delete");
            params.addParam("uuid",uuid);
            this.enqueue(params,callBack);
        }
        catch (Exception e)
        {
            e.printStackTrace();

        }
    }

    public void executeCron(String uuid, Callback callBack)
    {
        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();
            //set parameters
            params.setService("Cron");
            params.setMethod("execute");
            params.addParam("uuid",uuid);
            this.enqueue(params,callBack);
        }
        catch (Exception e)
        {
            e.printStackTrace();

        }
    }

}
