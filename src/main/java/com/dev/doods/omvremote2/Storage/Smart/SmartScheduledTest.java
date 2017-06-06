package com.dev.doods.omvremote2.Storage.Smart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by Ividata7 on 03/05/2017.
 */

public class SmartScheduledTest implements Serializable {

    @SerializedName("uuid")
    @Expose
    private String uuid;
    @SerializedName("enable")
    @Expose
    private Boolean enable;
    @SerializedName("devicefile")
    @Expose
    private String devicefile;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("hour")
    @Expose
    private String hour;
    @SerializedName("month")
    @Expose
    private String month;
    @SerializedName("dayofmonth")
    @Expose
    private String dayofmonth;
    @SerializedName("dayofweek")
    @Expose
    private String dayofweek;
    @SerializedName("comment")
    @Expose
    private String comment;

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

    public String getDevicefile() {
        return devicefile;
    }

    public void setDevicefile(String devicefile) {
        this.devicefile = devicefile;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDayofmonth() {
        return dayofmonth;
    }

    public void setDayofmonth(String dayofmonth) {
        this.dayofmonth = dayofmonth;
    }

    public String getDayofweek() {
        return dayofweek;
    }

    public void setDayofweek(String dayofweek) {
        this.dayofweek = dayofweek;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(uuid).append(enable).append(devicefile).append(type).append(hour).append(month).append(dayofmonth).append(dayofweek).append(comment).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof SmartScheduledTest) == false) {
            return false;
        }
        SmartScheduledTest rhs = ((SmartScheduledTest) other);
        return new EqualsBuilder().append(uuid, rhs.uuid).append(enable, rhs.enable).append(devicefile, rhs.devicefile).append(type, rhs.type).append(hour, rhs.hour).append(month, rhs.month).append(dayofmonth, rhs.dayofmonth).append(dayofweek, rhs.dayofweek).append(comment, rhs.comment).isEquals();
    }
}
