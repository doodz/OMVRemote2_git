package Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by thiba on 06/10/2016.
 */

public class WebSettings {

    @SerializedName("port")
    @Expose
    private Integer port;
    @SerializedName("enablessl")
    @Expose
    private Boolean enablessl;
    @SerializedName("sslport")
    @Expose
    private Integer sslport;
    @SerializedName("forcesslonly")
    @Expose
    private Boolean forcesslonly;
    @SerializedName("sslcertificateref")
    @Expose
    private String sslcertificateref;
    @SerializedName("timeout")
    @Expose
    private Integer timeout;

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
     * The enablessl
     */
    public Boolean getEnablessl() {
        return enablessl;
    }

    /**
     *
     * @param enablessl
     * The enablessl
     */
    public void setEnablessl(Boolean enablessl) {
        this.enablessl = enablessl;
    }

    /**
     *
     * @return
     * The sslport
     */
    public Integer getSslport() {
        return sslport;
    }

    /**
     *
     * @param sslport
     * The sslport
     */
    public void setSslport(Integer sslport) {
        this.sslport = sslport;
    }

    /**
     *
     * @return
     * The forcesslonly
     */
    public Boolean getForcesslonly() {
        return forcesslonly;
    }

    /**
     *
     * @param forcesslonly
     * The forcesslonly
     */
    public void setForcesslonly(Boolean forcesslonly) {
        this.forcesslonly = forcesslonly;
    }

    /**
     *
     * @return
     * The sslcertificateref
     */
    public String getSslcertificateref() {
        return sslcertificateref;
    }

    /**
     *
     * @param sslcertificateref
     * The sslcertificateref
     */
    public void setSslcertificateref(String sslcertificateref) {
        this.sslcertificateref = sslcertificateref;
    }

    /**
     *
     * @return
     * The timeout
     */
    public Integer getTimeout() {
        return timeout;
    }

    /**
     *
     * @param timeout
     * The timeout
     */
    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }
}
