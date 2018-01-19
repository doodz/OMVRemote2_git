package Controllers;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

import Client.AsyncCall;
import Client.Callback;
import Client.JSONRPCClient;
import Client.JSONRPCParamsBuilder;

/**
 * Created by thiba on 13/12/2016.
 */

public class LogsController  extends Abstractcontroller {

    public static List<String> LogsIdsPlugins = new ArrayList<String>(){{add("openvpn");add("autoshutdown");
        add("letsencrypt");add("fail2ban");
        add("transmissionbt");
    }};


    public static List<String> LogsIds = new ArrayList<String>(){{add("syslog");add("boot");
        add("daemon");add("proftpd");
        add("proftpd_xferlog");add("messages");
        add("rsync");add("rsyncd");
        add("smartd");add("smbdaudit");
        add("auth");
    }};

    public static List<String> LogsIdsV3 = new ArrayList<String>(){{add("syslog");add("boot");
        add("daemon");add("proftpd");
        add("proftpd_xferlog");add("messages");
        add("rsync");add("rsyncd");
        add("smartd");add("smbdaudit");
        add("apt_history");add("apt_term");
        add("auth");
    }};
    public LogsController(Activity activity) {
        super(activity);
    }

    public void getLogsList(String id, Callback callBack )
    {
        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();
            //set parameters
            params.setService("LogFile");
            params.setMethod("getList");
            params.addParam("id",id);
            params.addParam("limit",50);
            params.addParam("sortdir","DESC");
            params.addParam("sortfield","rownum");
            params.addParam("start",0);

            this.enqueue(params,callBack);

        }
        catch (Exception e)
        {
            e.printStackTrace();

        }

    }

    public void ClearLogs(String id,Callback callBack)
    {
        try {
            JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();
            //set parameters
            params.setService("LogFile");
            params.setMethod("clear");
            params.addParam("id",id);
            this.enqueue(params,callBack);
        }
        catch (Exception e)
        {
            e.printStackTrace();

        }

    }
}
