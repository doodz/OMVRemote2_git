package com.dev.doods.omvremote2.Plugins.Sensors;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Ividata7 on 21/06/2017.
 */

public class SensorsSettings {

    @SerializedName("cpuenable")
    @Expose
    private Boolean cpuenable;
    @SerializedName("sysfanenable")
    @Expose
    private Boolean sysfanenable;
    @SerializedName("cpufanenable")
    @Expose
    private Boolean cpufanenable;
    @SerializedName("mbtemp")
    @Expose
    private Boolean mbtemp;
    @SerializedName("fheit")
    @Expose
    private Boolean fheit;
    @SerializedName("jntemp")
    @Expose
    private Boolean jntemp;
    @SerializedName("jnfans")
    @Expose
    private Boolean jnfans;
    @SerializedName("shcore")
    @Expose
    private Boolean shcore;
    @SerializedName("sysoffset")
    @Expose
    private String sysoffset;
    @SerializedName("tabmain")
    @Expose
    private String tabmain;
    @SerializedName("tabcputemp")
    @Expose
    private String tabcputemp;
    @SerializedName("tabmbtemp")
    @Expose
    private String tabmbtemp;
    @SerializedName("tabcpufan")
    @Expose
    private String tabcpufan;
    @SerializedName("tabsysfan")
    @Expose
    private String tabsysfan;
    @SerializedName("graphcputitle")
    @Expose
    private String graphcputitle;
    @SerializedName("graphmbtitle")
    @Expose
    private String graphmbtitle;
    @SerializedName("graphcfantitle")
    @Expose
    private String graphcfantitle;
    @SerializedName("graphsfantitle")
    @Expose
    private String graphsfantitle;
    @SerializedName("linecputemp")
    @Expose
    private String linecputemp;
    @SerializedName("linecpufan")
    @Expose
    private String linecpufan;
    @SerializedName("linembtemp")
    @Expose
    private String linembtemp;
    @SerializedName("linesysfan")
    @Expose
    private String linesysfan;

    public Boolean getCpuenable() {
        return cpuenable;
    }

    public void setCpuenable(Boolean cpuenable) {
        this.cpuenable = cpuenable;
    }

    public Boolean getSysfanenable() {
        return sysfanenable;
    }

    public void setSysfanenable(Boolean sysfanenable) {
        this.sysfanenable = sysfanenable;
    }

    public Boolean getCpufanenable() {
        return cpufanenable;
    }

    public void setCpufanenable(Boolean cpufanenable) {
        this.cpufanenable = cpufanenable;
    }

    public Boolean getMbtemp() {
        return mbtemp;
    }

    public void setMbtemp(Boolean mbtemp) {
        this.mbtemp = mbtemp;
    }

    public Boolean getFheit() {
        return fheit;
    }

    public void setFheit(Boolean fheit) {
        this.fheit = fheit;
    }

    public Boolean getJntemp() {
        return jntemp;
    }

    public void setJntemp(Boolean jntemp) {
        this.jntemp = jntemp;
    }

    public Boolean getJnfans() {
        return jnfans;
    }

    public void setJnfans(Boolean jnfans) {
        this.jnfans = jnfans;
    }

    public Boolean getShcore() {
        return shcore;
    }

    public void setShcore(Boolean shcore) {
        this.shcore = shcore;
    }

    public String getSysoffset() {
        return sysoffset;
    }

    public void setSysoffset(String sysoffset) {
        this.sysoffset = sysoffset;
    }

    public String getTabmain() {
        return tabmain;
    }

    public void setTabmain(String tabmain) {
        this.tabmain = tabmain;
    }

    public String getTabcputemp() {
        return tabcputemp;
    }

    public void setTabcputemp(String tabcputemp) {
        this.tabcputemp = tabcputemp;
    }

    public String getTabmbtemp() {
        return tabmbtemp;
    }

    public void setTabmbtemp(String tabmbtemp) {
        this.tabmbtemp = tabmbtemp;
    }

    public String getTabcpufan() {
        return tabcpufan;
    }

    public void setTabcpufan(String tabcpufan) {
        this.tabcpufan = tabcpufan;
    }

    public String getTabsysfan() {
        return tabsysfan;
    }

    public void setTabsysfan(String tabsysfan) {
        this.tabsysfan = tabsysfan;
    }

    public String getGraphcputitle() {
        return graphcputitle;
    }

    public void setGraphcputitle(String graphcputitle) {
        this.graphcputitle = graphcputitle;
    }

    public String getGraphmbtitle() {
        return graphmbtitle;
    }

    public void setGraphmbtitle(String graphmbtitle) {
        this.graphmbtitle = graphmbtitle;
    }

    public String getGraphcfantitle() {
        return graphcfantitle;
    }

    public void setGraphcfantitle(String graphcfantitle) {
        this.graphcfantitle = graphcfantitle;
    }

    public String getGraphsfantitle() {
        return graphsfantitle;
    }

    public void setGraphsfantitle(String graphsfantitle) {
        this.graphsfantitle = graphsfantitle;
    }

    public String getLinecputemp() {
        return linecputemp;
    }

    public void setLinecputemp(String linecputemp) {
        this.linecputemp = linecputemp;
    }

    public String getLinecpufan() {
        return linecpufan;
    }

    public void setLinecpufan(String linecpufan) {
        this.linecpufan = linecpufan;
    }

    public String getLinembtemp() {
        return linembtemp;
    }

    public void setLinembtemp(String linembtemp) {
        this.linembtemp = linembtemp;
    }

    public String getLinesysfan() {
        return linesysfan;
    }

    public void setLinesysfan(String linesysfan) {
        this.linesysfan = linesysfan;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(cpuenable).append(sysfanenable).append(cpufanenable).append(mbtemp).append(fheit).append(jntemp).append(jnfans).append(shcore).append(sysoffset).append(tabmain).append(tabcputemp).append(tabmbtemp).append(tabcpufan).append(tabsysfan).append(graphcputitle).append(graphmbtitle).append(graphcfantitle).append(graphsfantitle).append(linecputemp).append(linecpufan).append(linembtemp).append(linesysfan).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof SensorsSettings) == false) {
            return false;
        }
        SensorsSettings rhs = ((SensorsSettings) other);
        return new EqualsBuilder().append(cpuenable, rhs.cpuenable).append(sysfanenable, rhs.sysfanenable).append(cpufanenable, rhs.cpufanenable).append(mbtemp, rhs.mbtemp).append(fheit, rhs.fheit).append(jntemp, rhs.jntemp).append(jnfans, rhs.jnfans).append(shcore, rhs.shcore).append(sysoffset, rhs.sysoffset).append(tabmain, rhs.tabmain).append(tabcputemp, rhs.tabcputemp).append(tabmbtemp, rhs.tabmbtemp).append(tabcpufan, rhs.tabcpufan).append(tabsysfan, rhs.tabsysfan).append(graphcputitle, rhs.graphcputitle).append(graphmbtitle, rhs.graphmbtitle).append(graphcfantitle, rhs.graphcfantitle).append(graphsfantitle, rhs.graphsfantitle).append(linecputemp, rhs.linecputemp).append(linecpufan, rhs.linecpufan).append(linembtemp, rhs.linembtemp).append(linesysfan, rhs.linesysfan).isEquals();
    }
}
