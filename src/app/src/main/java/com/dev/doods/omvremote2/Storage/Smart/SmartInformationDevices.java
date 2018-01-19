package com.dev.doods.omvremote2.Storage.Smart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Ividata7 on 10/05/2017.
 */

public class SmartInformationDevices {

    @SerializedName("devicemodel")
    @Expose
    private String devicemodel;
    @SerializedName("serialnumber")
    @Expose
    private String serialnumber;
    @SerializedName("firmwareversion")
    @Expose
    private String firmwareversion;
    @SerializedName("usercapacity")
    @Expose
    private String usercapacity;
    @SerializedName("modelfamily")
    @Expose
    private String modelfamily;
    @SerializedName("luwwndeviceid")
    @Expose
    private String luwwndeviceid;
    @SerializedName("sectorsize")
    @Expose
    private String sectorsize;
    @SerializedName("deviceis")
    @Expose
    private String deviceis;
    @SerializedName("ataversionis")
    @Expose
    private String ataversionis;
    @SerializedName("sataversionis")
    @Expose
    private String sataversionis;
    @SerializedName("localtimeis")
    @Expose
    private String localtimeis;
    @SerializedName("smartsupportis")
    @Expose
    private String smartsupportis;
    @SerializedName("aamlevelis")
    @Expose
    private String aamlevelis;
    @SerializedName("apmlevelis")
    @Expose
    private String apmlevelis;
    @SerializedName("rdlook-aheadis")
    @Expose
    private String rdlookAheadis;
    @SerializedName("writecacheis")
    @Expose
    private String writecacheis;
    @SerializedName("atasecurityis")
    @Expose
    private String atasecurityis;
    @SerializedName("wtcachereorder")
    @Expose
    private String wtcachereorder;

    public String getDevicemodel() {
        return devicemodel;
    }

    public void setDevicemodel(String devicemodel) {
        this.devicemodel = devicemodel;
    }

    public String getSerialnumber() {
        return serialnumber;
    }

    public void setSerialnumber(String serialnumber) {
        this.serialnumber = serialnumber;
    }

    public String getFirmwareversion() {
        return firmwareversion;
    }

    public void setFirmwareversion(String firmwareversion) {
        this.firmwareversion = firmwareversion;
    }

    public String getUsercapacity() {
        return usercapacity;
    }

    public void setUsercapacity(String usercapacity) {
        this.usercapacity = usercapacity;
    }

    public String getModelfamily() {
        return modelfamily;
    }

    public void setModelfamily(String modelfamily) {
        this.modelfamily = modelfamily;
    }

    public String getLuwwndeviceid() {
        return luwwndeviceid;
    }

    public void setLuwwndeviceid(String luwwndeviceid) {
        this.luwwndeviceid = luwwndeviceid;
    }

    public String getSectorsize() {
        return sectorsize;
    }

    public void setSectorsize(String sectorsize) {
        this.sectorsize = sectorsize;
    }

    public String getDeviceis() {
        return deviceis;
    }

    public void setDeviceis(String deviceis) {
        this.deviceis = deviceis;
    }

    public String getAtaversionis() {
        return ataversionis;
    }

    public void setAtaversionis(String ataversionis) {
        this.ataversionis = ataversionis;
    }

    public String getSataversionis() {
        return sataversionis;
    }

    public void setSataversionis(String sataversionis) {
        this.sataversionis = sataversionis;
    }

    public String getLocaltimeis() {
        return localtimeis;
    }

    public void setLocaltimeis(String localtimeis) {
        this.localtimeis = localtimeis;
    }

    public String getSmartsupportis() {
        return smartsupportis;
    }

    public void setSmartsupportis(String smartsupportis) {
        this.smartsupportis = smartsupportis;
    }

    public String getAamlevelis() {
        return aamlevelis;
    }

    public void setAamlevelis(String aamlevelis) {
        this.aamlevelis = aamlevelis;
    }

    public String getApmlevelis() {
        return apmlevelis;
    }

    public void setApmlevelis(String apmlevelis) {
        this.apmlevelis = apmlevelis;
    }

    public String getRdlookAheadis() {
        return rdlookAheadis;
    }

    public void setRdlookAheadis(String rdlookAheadis) {
        this.rdlookAheadis = rdlookAheadis;
    }

    public String getWritecacheis() {
        return writecacheis;
    }

    public void setWritecacheis(String writecacheis) {
        this.writecacheis = writecacheis;
    }

    public String getAtasecurityis() {
        return atasecurityis;
    }

    public void setAtasecurityis(String atasecurityis) {
        this.atasecurityis = atasecurityis;
    }

    public String getWtcachereorder() {
        return wtcachereorder;
    }

    public void setWtcachereorder(String wtcachereorder) {
        this.wtcachereorder = wtcachereorder;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(devicemodel).append(serialnumber).append(firmwareversion).append(usercapacity).append(modelfamily).append(luwwndeviceid).append(sectorsize).append(deviceis).append(ataversionis).append(sataversionis).append(localtimeis).append(smartsupportis).append(aamlevelis).append(apmlevelis).append(rdlookAheadis).append(writecacheis).append(atasecurityis).append(wtcachereorder).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof SmartInformationDevices) == false) {
            return false;
        }
        SmartInformationDevices rhs = ((SmartInformationDevices) other);
        return new EqualsBuilder().append(devicemodel, rhs.devicemodel).append(serialnumber, rhs.serialnumber).append(firmwareversion, rhs.firmwareversion).append(usercapacity, rhs.usercapacity).append(modelfamily, rhs.modelfamily).append(luwwndeviceid, rhs.luwwndeviceid).append(sectorsize, rhs.sectorsize).append(deviceis, rhs.deviceis).append(ataversionis, rhs.ataversionis).append(sataversionis, rhs.sataversionis).append(localtimeis, rhs.localtimeis).append(smartsupportis, rhs.smartsupportis).append(aamlevelis, rhs.aamlevelis).append(apmlevelis, rhs.apmlevelis).append(rdlookAheadis, rhs.rdlookAheadis).append(writecacheis, rhs.writecacheis).append(atasecurityis, rhs.atasecurityis).append(wtcachereorder, rhs.wtcachereorder).isEquals();
    }
}
