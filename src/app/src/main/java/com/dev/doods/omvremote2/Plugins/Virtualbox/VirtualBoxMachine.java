package com.dev.doods.omvremote2.Plugins.Virtualbox;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Ividata7 on 20/01/2017.
 */

public class VirtualBoxMachine {

    @SerializedName("uuid")
    @Expose
    private String uuid;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("startupMode")
    @Expose
    private String startupMode;
    @SerializedName("OSTypeId")
    @Expose
    private String oSTypeId;
    @SerializedName("sessionState")
    @Expose
    private String sessionState;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStartupMode() {
        return startupMode;
    }

    public void setStartupMode(String startupMode) {
        this.startupMode = startupMode;
    }

    public String getOSTypeId() {
        return oSTypeId;
    }

    public void setOSTypeId(String oSTypeId) {
        this.oSTypeId = oSTypeId;
    }

    public String getSessionState() {
        return sessionState;
    }

    public void setSessionState(String sessionState) {
        this.sessionState = sessionState;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(uuid).append(name).append(state).append(startupMode).append(oSTypeId).append(sessionState).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof VirtualBoxMachine) == false) {
            return false;
        }
        VirtualBoxMachine rhs = ((VirtualBoxMachine) other);
        return new EqualsBuilder().append(uuid, rhs.uuid).append(name, rhs.name).append(state, rhs.state).append(startupMode, rhs.startupMode).append(oSTypeId, rhs.oSTypeId).append(sessionState, rhs.sessionState).isEquals();
    }

}
