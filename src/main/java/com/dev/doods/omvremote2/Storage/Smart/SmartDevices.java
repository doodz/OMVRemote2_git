package com.dev.doods.omvremote2.Storage.Smart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * Created by Ividata7 on 27/01/2017.
 */

public class SmartDevices {
    @SerializedName("devicelinks")
    @Expose
    private List<String> devicelinks = null;
    @SerializedName("devicename")
    @Expose
    private String devicename;
    @SerializedName("devicefile")
    @Expose
    private String devicefile;
    @SerializedName("devicefilebyid")
    @Expose
    private String devicefilebyid;
    @SerializedName("model")
    @Expose
    private String model;
    @SerializedName("size")
    @Expose
    private String size;
    @SerializedName("temperature")
    @Expose
    private String temperature;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("vendor")
    @Expose
    private String vendor;
    @SerializedName("serialnumber")
    @Expose
    private String serialnumber;
    @SerializedName("overallstatus")
    @Expose
    private String overallstatus;
    @SerializedName("uuid")
    @Expose
    private String uuid;
    @SerializedName("monitor")
    @Expose
    private Boolean monitor;

    public String getDevicename() {
        return devicename;
    }

    public void setDevicename(String devicename) {
        this.devicename = devicename;
    }

    public String getDevicefile() {
        return devicefile;
    }

    public void setDevicefile(String devicefile) {
        this.devicefile = devicefile;
    }

    public List<String> getDevicelinks() {
        return devicelinks;
    }

    public void setDevicelinks(List<String> devicelinks) {
        this.devicelinks = devicelinks;
    }

    public String getDevicefilebyid() {
        return devicefilebyid;
    }

    public void setDevicefilebyid(String devicefilebyid) {
        this.devicefilebyid = devicefilebyid;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getSerialnumber() {
        return serialnumber;
    }

    public void setSerialnumber(String serialnumber) {
        this.serialnumber = serialnumber;
    }

    public String getOverallstatus() {
        return overallstatus;
    }

    public void setOverallstatus(String overallstatus) {
        this.overallstatus = overallstatus;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Boolean getMonitor() {
        return monitor;
    }

    public void setMonitor(Boolean monitor) {
        this.monitor = monitor;
    }

    @Override
    public String toString() {

        return description;
        //return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(devicename).append(devicefile).append(devicelinks).append(devicefilebyid).append(model).append(size).append(temperature).append(description).append(vendor).append(serialnumber).append(overallstatus).append(uuid).append(monitor).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof SmartDevices) == false) {
            return false;
        }
        SmartDevices rhs = ((SmartDevices) other);
        return new EqualsBuilder().append(devicename, rhs.devicename).append(devicefile, rhs.devicefile).append(devicelinks, rhs.devicelinks).append(devicefilebyid, rhs.devicefilebyid).append(model, rhs.model).append(size, rhs.size).append(temperature, rhs.temperature).append(description, rhs.description).append(vendor, rhs.vendor).append(serialnumber, rhs.serialnumber).append(overallstatus, rhs.overallstatus).append(uuid, rhs.uuid).append(monitor, rhs.monitor).isEquals();
    }
}
