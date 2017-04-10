package com.dev.doods.omvremote2.Plugins.Virtualbox;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Ividata7 on 20/01/2017.
 */

public class VirtualBoxSettings {

    @SerializedName("enable")
    @Expose
    private Boolean enable;
    @SerializedName(value="name", alternate={"machines.sharedfolderref", "sharedfolderref"})
    @Expose
    private String machinesSharedfolderref;
    @SerializedName("enable_advanced")
    @Expose
    private Boolean enableAdvanced;
    @SerializedName("show_tab")
    @Expose
    private Boolean showTab;

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public String getMachinesSharedfolderref() {
        return machinesSharedfolderref;
    }

    public void setMachinesSharedfolderref(String machinesSharedfolderref) {
        this.machinesSharedfolderref = machinesSharedfolderref;
    }

    public Boolean getEnableAdvanced() {
        return enableAdvanced;
    }

    public void setEnableAdvanced(Boolean enableAdvanced) {
        this.enableAdvanced = enableAdvanced;
    }

    public Boolean getShowTab() {
        return showTab;
    }

    public void setShowTab(Boolean showTab) {
        this.showTab = showTab;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(enable).append(machinesSharedfolderref).append(enableAdvanced).append(showTab).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof VirtualBoxSettings) == false) {
            return false;
        }
        VirtualBoxSettings rhs = ((VirtualBoxSettings) other);
        return new EqualsBuilder().append(enable, rhs.enable).append(machinesSharedfolderref, rhs.machinesSharedfolderref).append(enableAdvanced, rhs.enableAdvanced).append(showTab, rhs.showTab).isEquals();
    }
}
