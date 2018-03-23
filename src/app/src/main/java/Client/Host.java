package Client;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by thiba on 09/08/2016.
 */
public class Host implements Serializable {

    public static final String API_PATH = "/rpc.php";
    public static final String RDD_PATH = "/rdd.php";

    public static final int DEFAULT_HTTP_PORT = 80;
    public static final int DEFAULT_TIMEOUT = 5000;
    public static final int DEFAULT_WOL_WAIT = 40;
    public static final int DEFAULT_WOL_PORT = 9;
    /**
     * HTTP API Port
     */
    private int _port = DEFAULT_HTTP_PORT;

    public int getPort()
    {
        return _port;

    }
    public void setPort(int port)
    {
        _port= port;
    }


    /**
     * TCP socket read timeout in milliseconds
     */
    public int timeout = DEFAULT_TIMEOUT;

    /**
     * The time to wait after sending WOL
     */
    public int wol_wait = DEFAULT_WOL_WAIT;

    /**
     * The port to send the WOL to
     */
    private int wol_port = DEFAULT_WOL_PORT;

    public int getWolport()
    {
        return wol_port;

    }

    public void setWolport(int wolport)
    {
         wol_port = wolport;
    }

    private static final String TAG = "Host";
    private static final long serialVersionUID = 7886482294339161092L;
    /**
     * Database ID
     */
    private long _id;


    public long getId()
    {
        return _id;

    }
    public void setId(long id)
    {
      _id= id;
    }

    /**
     * Name (description/label) of the host
     */
    private String _name;

    public String getName()
    {
        return _name;

    }
    public void setName(String name)
    {
        _name= name;
    }

    /**
     * IP address or host name of the host
     */
    private String _addr;

    public String getAddr()
    {
        return _addr;

    }
    public void setAddr(String addr)
    {
        _addr= addr;
    }


    /**
     * IP address or host name of the Wol
     */
    private String _addrBroadcast ;

    public String getAddrBroadcast()
    {
        return _addrBroadcast;

    }
    public void setAddrBroadcast(String addrBroadcast)
    {
        _addrBroadcast= addrBroadcast;
    }

    /**
     * User name of in case of HTTP authentication
     */
    private String _user;
    public String getUser()
    {
        return _user;

    }
    public void setUser(String user)
    {
        _user= user;
    }
    /**
     * Password of in case of HTTP authentication
     */
    private String _pass;
    public String getPass()
    {
        return _pass;

    }
    public void setPass(String pass)
    {
        _pass= pass;
    }
    /**
     * If this host is only available through wifi
     */
    public boolean wifi_only = false;
    /**
     * If wifi only is true there might be an access point specified to connect to
     */
    public String access_point;
    /**
     * The MAC address of this host
     */
    private String mac_addr;

    public String getMacAddr()
    {
        return mac_addr;

    }
    public void setMacAddr(String macAddr)
    {
        mac_addr= macAddr;
    }


    private Boolean _ssl = false;
    public Boolean getSll()
    {
        return _ssl;

    }
    public void setSll(Boolean sll)
    {
        _ssl= sll;
    }
    /**
     * Something readable
     */


    //TODO add to db
    /**
     * Version of the api
     */
    private int _version;

    public int getVersion()
    {

        return _version;

    }
    public void setVersion(int version)
    {
        _version= version;
    }


    public String toString() {
        return _addr + ":" + _port;
    }

    public String getSummary() {
        return toString();
    }

    public String toJson() {
        try {
            JSONObject json = new JSONObject();
            json.put("name", _name);
            json.put("addr", _addr);
            json.put("port", _port);
            json.put("user", _user);
            json.put("pass", _pass);
            json.put("timeout", timeout);
            json.put("wifi_only", wifi_only);
            json.put("access_point", access_point);
            json.put("mac_addr", mac_addr);
            json.put("wol_wait", wol_wait);
            json.put("wol_port", wol_port);
            return json.toString();
        } catch (JSONException e) {
            Log.e(TAG, "Error into Json", e);
            return "";
        }
    }

    public String getBaseUrl()
    {
        return "http"+ (_ssl?"s":"")+"://"+ _addr+":"+_port+API_PATH;
    }

    public String getUrl()
    {
        return "http"+ (_ssl?"s":"")+"://"+ _addr+":"+_port;
    }
}
