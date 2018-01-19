package com.dev.doods.omvremote2.Plugins.Autoshutdown;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Ividata7 on 03/05/2017.
 */

public class AutoshutdownSettings {

    @SerializedName("enable")
    @Expose
    private Boolean enable;
    @SerializedName("cycles")
    @Expose
    private Integer cycles;
    @SerializedName("sleep")
    @Expose
    private Integer sleep;
    @SerializedName("range")
    @Expose
    private String range;
    @SerializedName("shutdowncommand")
    @Expose
    private Integer shutdowncommand;
    @SerializedName("checkclockactive")
    @Expose
    private Boolean checkclockactive;
    @SerializedName("uphours-begin")
    @Expose
    private Integer uphoursBegin;
    @SerializedName("uphours-end")
    @Expose
    private Integer uphoursEnd;
    @SerializedName("nsocketnumbers")
    @Expose
    private String nsocketnumbers;
    @SerializedName("uldlcheck")
    @Expose
    private Boolean uldlcheck;
    @SerializedName("uldlrate")
    @Expose
    private Integer uldlrate;
    @SerializedName("loadaveragecheck")
    @Expose
    private Boolean loadaveragecheck;
    @SerializedName("loadaverage")
    @Expose
    private Integer loadaverage;
    @SerializedName("hddiocheck")
    @Expose
    private Boolean hddiocheck;
    @SerializedName("hddiorate")
    @Expose
    private Integer hddiorate;
    @SerializedName("checksamba")
    @Expose
    private Boolean checksamba;
    @SerializedName("checkcli")
    @Expose
    private Boolean checkcli;
    @SerializedName("syslog")
    @Expose
    private Boolean syslog;
    @SerializedName("verbose")
    @Expose
    private Boolean verbose;
    @SerializedName("fake")
    @Expose
    private Boolean fake;
    @SerializedName("extraoptions")
    @Expose
    private String extraoptions;

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public Integer getCycles() {
        return cycles;
    }

    public void setCycles(Integer cycles) {
        this.cycles = cycles;
    }

    public Integer getSleep() {
        return sleep;
    }

    public void setSleep(Integer sleep) {
        this.sleep = sleep;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public Integer getShutdowncommand() {
        return shutdowncommand;
    }

    public void setShutdowncommand(Integer shutdowncommand) {
        this.shutdowncommand = shutdowncommand;
    }

    public Boolean getCheckclockactive() {
        return checkclockactive;
    }

    public void setCheckclockactive(Boolean checkclockactive) {
        this.checkclockactive = checkclockactive;
    }

    public Integer getUphoursBegin() {
        return uphoursBegin;
    }

    public void setUphoursBegin(Integer uphoursBegin) {
        this.uphoursBegin = uphoursBegin;
    }

    public Integer getUphoursEnd() {
        return uphoursEnd;
    }

    public void setUphoursEnd(Integer uphoursEnd) {
        this.uphoursEnd = uphoursEnd;
    }

    public String getNsocketnumbers() {
        return nsocketnumbers;
    }

    public void setNsocketnumbers(String nsocketnumbers) {
        this.nsocketnumbers = nsocketnumbers;
    }

    public Boolean getUldlcheck() {
        return uldlcheck;
    }

    public void setUldlcheck(Boolean uldlcheck) {
        this.uldlcheck = uldlcheck;
    }

    public Integer getUldlrate() {
        return uldlrate;
    }

    public void setUldlrate(Integer uldlrate) {
        this.uldlrate = uldlrate;
    }

    public Boolean getLoadaveragecheck() {
        return loadaveragecheck;
    }

    public void setLoadaveragecheck(Boolean loadaveragecheck) {
        this.loadaveragecheck = loadaveragecheck;
    }

    public Integer getLoadaverage() {
        return loadaverage;
    }

    public void setLoadaverage(Integer loadaverage) {
        this.loadaverage = loadaverage;
    }

    public Boolean getHddiocheck() {
        return hddiocheck;
    }

    public void setHddiocheck(Boolean hddiocheck) {
        this.hddiocheck = hddiocheck;
    }

    public Integer getHddiorate() {
        return hddiorate;
    }

    public void setHddiorate(Integer hddiorate) {
        this.hddiorate = hddiorate;
    }

    public Boolean getChecksamba() {
        return checksamba;
    }

    public void setChecksamba(Boolean checksamba) {
        this.checksamba = checksamba;
    }

    public Boolean getCheckcli() {
        return checkcli;
    }

    public void setCheckcli(Boolean checkcli) {
        this.checkcli = checkcli;
    }

    public Boolean getSyslog() {
        return syslog;
    }

    public void setSyslog(Boolean syslog) {
        this.syslog = syslog;
    }

    public Boolean getVerbose() {
        return verbose;
    }

    public void setVerbose(Boolean verbose) {
        this.verbose = verbose;
    }

    public Boolean getFake() {
        return fake;
    }

    public void setFake(Boolean fake) {
        this.fake = fake;
    }

    public String getExtraoptions() {
        return extraoptions;
    }

    public void setExtraoptions(String extraoptions) {
        this.extraoptions = extraoptions;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(enable).append(cycles).append(sleep).append(range).append(shutdowncommand).append(checkclockactive).append(uphoursBegin).append(uphoursEnd).append(nsocketnumbers).append(uldlcheck).append(uldlrate).append(loadaveragecheck).append(loadaverage).append(hddiocheck).append(hddiorate).append(checksamba).append(checkcli).append(syslog).append(verbose).append(fake).append(extraoptions).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof AutoshutdownSettings) == false) {
            return false;
        }
        AutoshutdownSettings rhs = ((AutoshutdownSettings) other);
        return new EqualsBuilder().append(enable, rhs.enable).append(cycles, rhs.cycles).append(sleep, rhs.sleep).append(range, rhs.range).append(shutdowncommand, rhs.shutdowncommand).append(checkclockactive, rhs.checkclockactive).append(uphoursBegin, rhs.uphoursBegin).append(uphoursEnd, rhs.uphoursEnd).append(nsocketnumbers, rhs.nsocketnumbers).append(uldlcheck, rhs.uldlcheck).append(uldlrate, rhs.uldlrate).append(loadaveragecheck, rhs.loadaveragecheck).append(loadaverage, rhs.loadaverage).append(hddiocheck, rhs.hddiocheck).append(hddiorate, rhs.hddiorate).append(checksamba, rhs.checksamba).append(checkcli, rhs.checkcli).append(syslog, rhs.syslog).append(verbose, rhs.verbose).append(fake, rhs.fake).append(extraoptions, rhs.extraoptions).isEquals();
    }

}
