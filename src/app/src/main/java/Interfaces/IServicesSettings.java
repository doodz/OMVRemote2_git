package Interfaces;

import java.util.Map;

/**
 * Created by thiba on 27/10/2016.
 */

public interface IServicesSettings {

    String getServiceName();
    void setServiceName(String serviceName);
    Map<String, String> getPArams();
    String ToJson();
}
