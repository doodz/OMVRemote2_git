package com.dev.doods.omvremote2.Plugins.Fail2ban;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Ividata7 on 26/04/2017.
 */

public class Fail2banSettings {

    @SerializedName("enable")
    @Expose
    private Boolean enable;
    @SerializedName("ignoreip")
    @Expose
    private String ignoreip;
    @SerializedName("findtime")
    @Expose
    private String findtime;
    @SerializedName("bantime")
    @Expose
    private String bantime;
    @SerializedName("maxretry")
    @Expose
    private String maxretry;
    @SerializedName("destemail")
    @Expose
    private String destemail;
    @SerializedName("action")
    @Expose
    private String action;

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public String getIgnoreip() {
        return ignoreip;
    }

    public void setIgnoreip(String ignoreip) {
        this.ignoreip = ignoreip;
    }

    public String getFindtime() {
        return findtime;
    }

    public void setFindtime(String findtime) {
        this.findtime = findtime;
    }

    public String getBantime() {
        return bantime;
    }

    public void setBantime(String bantime) {
        this.bantime = bantime;
    }

    public String getMaxretry() {
        return maxretry;
    }

    public void setMaxretry(String maxretry) {
        this.maxretry = maxretry;
    }

    public String getDestemail() {
        return destemail;
    }

    public void setDestemail(String destemail) {
        this.destemail = destemail;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(enable).append(ignoreip).append(findtime).append(bantime).append(maxretry).append(destemail).append(action).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Fail2banSettings) == false) {
            return false;
        }
        Fail2banSettings rhs = ((Fail2banSettings) other);
        return new EqualsBuilder().append(enable, rhs.enable).append(ignoreip, rhs.ignoreip).append(findtime, rhs.findtime).append(bantime, rhs.bantime).append(maxretry, rhs.maxretry).append(destemail, rhs.destemail).append(action, rhs.action).isEquals();
    }
}
