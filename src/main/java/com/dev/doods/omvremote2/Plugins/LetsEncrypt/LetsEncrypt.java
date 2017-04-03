package com.dev.doods.omvremote2.Plugins.LetsEncrypt;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Ividata7 on 31/03/2017.
 */

public class LetsEncrypt {

    @SerializedName("domain")
    @Expose
    private String domain;
    @SerializedName("enable")
    @Expose
    private Boolean enable;
    @SerializedName("test_cert")
    @Expose
    private Boolean testCert;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("webroot")
    @Expose
    private String webroot;
    @SerializedName("certuuid")
    @Expose
    private String certuuid;
    @SerializedName("cron_uuid")
    @Expose
    private String cronUuid;

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public Boolean getTestCert() {
        return testCert;
    }

    public void setTestCert(Boolean testCert) {
        this.testCert = testCert;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebroot() {
        return webroot;
    }

    public void setWebroot(String webroot) {
        this.webroot = webroot;
    }

    public String getCertuuid() {
        return certuuid;
    }

    public void setCertuuid(String certuuid) {
        this.certuuid = certuuid;
    }

    public String getCronUuid() {
        return cronUuid;
    }

    public void setCronUuid(String cronUuid) {
        this.cronUuid = cronUuid;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(domain).append(enable).append(testCert).append(email).append(webroot).append(certuuid).append(cronUuid).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof LetsEncrypt) == false) {
            return false;
        }
        LetsEncrypt rhs = ((LetsEncrypt) other);
        return new EqualsBuilder().append(domain, rhs.domain).append(enable, rhs.enable).append(testCert, rhs.testCert).append(email, rhs.email).append(webroot, rhs.webroot).append(certuuid, rhs.certuuid).append(cronUuid, rhs.cronUuid).isEquals();
    }
}
