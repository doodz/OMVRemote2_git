package Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by thiba on 14/10/2016.
 */

public class SettingsNetwork {


    @SerializedName("hostname")
    @Expose
    private String hostname;
    @SerializedName("domainname")
    @Expose
    private String domainname;

    /**
     *
     * @return
     * The hostname
     */
    public String getHostname() {
        return hostname;
    }

    /**
     *
     * @param hostname
     * The hostname
     */
    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    /**
     *
     * @return
     * The domainname
     */
    public String getDomainname() {
        return domainname;
    }

    /**
     *
     * @param domainname
     * The domainname
     */
    public void setDomainname(String domainname) {
        this.domainname = domainname;
    }

}
