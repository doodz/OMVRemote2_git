package com.dev.doods.omvremote2.Storage.Smart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import Models.Monitor;

/**
 * Created by Ividata7 on 27/01/2017.
 */

public class SmartSettings {

    @SerializedName("enable")
    @Expose
    private Boolean enable;
    @SerializedName("interval")
    @Expose
    private Integer interval;
    @SerializedName("powermode")
    @Expose
    private String powermode;
    @SerializedName("tempdiff")
    @Expose
    private Integer tempdiff;
    @SerializedName("tempinfo")
    @Expose
    private Integer tempinfo;
    @SerializedName("tempcrit")
    @Expose
    private Integer tempcrit;
    /*
    @SerializedName("monitor")
    @Expose
    private Monitor monitor;
    @SerializedName("scheduledtests")
    @Expose
    private Scheduledtests scheduledtests;
    */
    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public Integer getInterval() {
        return interval;
    }

    public void setInterval(Integer interval) {
        this.interval = interval;
    }

    public String getPowermode() {
        return powermode;
    }

    public void setPowermode(String powermode) {
        this.powermode = powermode;
    }

    public Integer getTempdiff() {
        return tempdiff;
    }

    public void setTempdiff(Integer tempdiff) {
        this.tempdiff = tempdiff;
    }

    public Integer getTempinfo() {
        return tempinfo;
    }

    public void setTempinfo(Integer tempinfo) {
        this.tempinfo = tempinfo;
    }

    public Integer getTempcrit() {
        return tempcrit;
    }

    public void setTempcrit(Integer tempcrit) {
        this.tempcrit = tempcrit;
    }

    /*
    public Monitor getMonitor() {
        return monitor;
    }

    public void setMonitor(Monitor monitor) {
        this.monitor = monitor;
    }

    public Scheduledtests getScheduledtests() {
        return scheduledtests;
    }

    public void setScheduledtests(Scheduledtests scheduledtests) {
        this.scheduledtests = scheduledtests;
    }
*/
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(enable).append(interval).append(powermode).append(tempdiff).append(tempinfo).append(tempcrit)/*.append(monitor).append(scheduledtests)*/.toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof SmartSettings) == false) {
            return false;
        }
        SmartSettings rhs = ((SmartSettings) other);
        return new EqualsBuilder().append(enable, rhs.enable).append(interval, rhs.interval).append(powermode, rhs.powermode).append(tempdiff, rhs.tempdiff).append(tempinfo, rhs.tempinfo).append(tempcrit, rhs.tempcrit)/*.append(monitor, rhs.monitor).append(scheduledtests, rhs.scheduledtests)*/.isEquals();
    }

}
