package Models;

import java.util.HashMap;
import java.util.Map;

import Interfaces.IServicesSettings;

/**
 * Created by thiba on 27/10/2016.
 */

public abstract class BaseSettings implements IServicesSettings {


    private String mServiceName;

    public String getServiceName(){return mServiceName;}
    public void setServiceName(String serviceName){
         mServiceName = serviceName;}


    public BaseSettings(String serviceName)
    {
        mServiceName = serviceName;

    }

    public Map<String, String> getPArams()
    {

        Map<String, String> dictionary = new HashMap<String, String>();
        return dictionary;
    }

    public String ToJson()
    {
        return"";
    }

}
