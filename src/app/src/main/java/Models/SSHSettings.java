package Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by thiba on 26/10/2016.
 */

public class SSHSettings extends BaseSettings {

    @SerializedName("enable")
    @Expose
    private Boolean enable;
    @SerializedName("port")
    @Expose
    private Integer port;
    @SerializedName("extraoptions")
    @Expose
    private String extraoptions;
    @SerializedName("passwordauthentication")
    @Expose
    private Boolean passwordauthentication;
    @SerializedName("pubkeyauthentication")
    @Expose
    private Boolean pubkeyauthentication;
    @SerializedName("permitrootlogin")
    @Expose
    private Boolean permitrootlogin;
    @SerializedName("tcpforwarding")
    @Expose
    private Boolean tcpforwarding;
    @SerializedName("compression")
    @Expose
    private Boolean compression;

    /**
     *
     * @return
     * The enable
     */
    public Boolean getEnable() {
        return enable;
    }

    /**
     *
     * @param enable
     * The enable
     */
    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    /**
     *
     * @return
     * The port
     */
    public Integer getPort() {
        return port;
    }

    /**
     *
     * @param port
     * The port
     */
    public void setPort(Integer port) {
        this.port = port;
    }

    /**
     *
     * @return
     * The extraoptions
     */
    public String getExtraoptions() {
        return extraoptions;
    }

    /**
     *
     * @param extraoptions
     * The extraoptions
     */
    public void setExtraoptions(String extraoptions) {
        this.extraoptions = extraoptions;
    }

    /**
     *
     * @return
     * The passwordauthentication
     */
    public Boolean getPasswordauthentication() {
        return passwordauthentication;
    }

    /**
     *
     * @param passwordauthentication
     * The passwordauthentication
     */
    public void setPasswordauthentication(Boolean passwordauthentication) {
        this.passwordauthentication = passwordauthentication;
    }

    /**
     *
     * @return
     * The pubkeyauthentication
     */
    public Boolean getPubkeyauthentication() {
        return pubkeyauthentication;
    }

    /**
     *
     * @param pubkeyauthentication
     * The pubkeyauthentication
     */
    public void setPubkeyauthentication(Boolean pubkeyauthentication) {
        this.pubkeyauthentication = pubkeyauthentication;
    }

    /**
     *
     * @return
     * The permitrootlogin
     */
    public Boolean getPermitrootlogin() {
        return permitrootlogin;
    }

    /**
     *
     * @param permitrootlogin
     * The permitrootlogin
     */
    public void setPermitrootlogin(Boolean permitrootlogin) {
        this.permitrootlogin = permitrootlogin;
    }

    /**
     *
     * @return
     * The tcpforwarding
     */
    public Boolean getTcpforwarding() {
        return tcpforwarding;
    }

    /**
     *
     * @param tcpforwarding
     * The tcpforwarding
     */
    public void setTcpforwarding(Boolean tcpforwarding) {
        this.tcpforwarding = tcpforwarding;
    }

    /**
     *
     * @return
     * The compression
     */
    public Boolean getCompression() {
        return compression;
    }

    /**
     *
     * @param compression
     * The compression
     */
    public void setCompression(Boolean compression) {
        this.compression = compression;
    }


    private SSHSettings()
    {
        super("SSH");

    }

    public Map<String, String> getPArams()
    {
        Map<String, String> dictionary = new HashMap<String, String>();

        dictionary.put("enable",enable?"mTrue":"mFalse");
        dictionary.put("port",port.toString());
        dictionary.put("extraoptions",extraoptions);
        dictionary.put("passwordauthentication",passwordauthentication?"mTrue":"mFalse");
        dictionary.put("pubkeyauthentication",pubkeyauthentication?"mTrue":"mFalse");
        dictionary.put("permitrootlogin",permitrootlogin?"mTrue":"mFalse");
        dictionary.put("tcpforwarding",tcpforwarding?"mTrue":"mFalse");
        dictionary.put("compression",compression?"mTrue":"mFalse");

        return dictionary;
    }


    @Override
    public String ToJson()
    {

        //{"enable":true,"port":22,"extraoptions":"","passwordauthentication":true,"pubkeyauthentication":true,"permitrootlogin":true,"tcpforwarding":false,"compression":true}

        StringBuilder sb = new StringBuilder();
        sb.append('{');
        sb.append("\"enable\":"+(enable?"true":"false"));
        sb.append(",\"port\":"+port);
        sb.append(",\"extraoptions\":\""+extraoptions+"\"");
        sb.append(",\"passwordauthentication\":"+(passwordauthentication?"true":"false"));
        sb.append(",\"pubkeyauthentication\":"+(pubkeyauthentication?"true":"false"));
        sb.append(",\"permitrootlogin\":"+(permitrootlogin?"true":"false"));
        sb.append(",\"tcpforwarding\":"+(tcpforwarding?"true":"false"));
        sb.append(",\"compression\":"+(compression?"true":"false"));

        sb.append('}');
        return sb.toString();
    }


    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(enable).append(port).append(extraoptions).append(passwordauthentication).append(pubkeyauthentication).append(permitrootlogin).append(tcpforwarding).append(compression).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof SSHSettings) == false) {
            return false;
        }
        SSHSettings rhs = ((SSHSettings) other);
        return new EqualsBuilder().append(enable, rhs.enable).append(port, rhs.port).append(extraoptions, rhs.extraoptions).append(passwordauthentication, rhs.passwordauthentication).append(pubkeyauthentication, rhs.pubkeyauthentication).append(permitrootlogin, rhs.permitrootlogin).append(tcpforwarding, rhs.tcpforwarding).append(compression, rhs.compression).isEquals();
    }
}
