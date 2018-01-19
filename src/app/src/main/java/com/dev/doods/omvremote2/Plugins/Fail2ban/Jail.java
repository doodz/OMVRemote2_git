package com.dev.doods.omvremote2.Plugins.Fail2ban;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

import Interfaces.INameDescObject;
import OMV.Base.NameDescObjectBase;

/**
 * Created by Ividata7 on 26/04/2017.
 */

public class Jail extends NameDescObjectBase implements Serializable {

    @SerializedName("uuid")
    @Expose
    private String uuid;
    @SerializedName("enable")
    @Expose
    private Boolean enable;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("port")
    @Expose
    private String port;
    @SerializedName("maxretry")
    @Expose
    private String maxretry;
    @SerializedName("bantime")
    @Expose
    private String bantime;
    @SerializedName("filter")
    @Expose
    private String filter;
    @SerializedName("logpath")
    @Expose
    private String logpath;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return port;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getMaxretry() {
        return maxretry;
    }

    public void setMaxretry(String maxretry) {
        this.maxretry = maxretry;
    }

    public String getBantime() {
        return bantime;
    }

    public void setBantime(String bantime) {
        this.bantime = bantime;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String getLogpath() {
        return logpath;
    }

    public void setLogpath(String logpath) {
        this.logpath = logpath;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(uuid).append(enable).append(name).append(port).append(maxretry).append(bantime).append(filter).append(logpath).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Jail) == false) {
            return false;
        }
        Jail rhs = ((Jail) other);
        return new EqualsBuilder().append(uuid, rhs.uuid).append(enable, rhs.enable).append(name, rhs.name).append(port, rhs.port).append(maxretry, rhs.maxretry).append(bantime, rhs.bantime).append(filter, rhs.filter).append(logpath, rhs.logpath).isEquals();
    }

}
