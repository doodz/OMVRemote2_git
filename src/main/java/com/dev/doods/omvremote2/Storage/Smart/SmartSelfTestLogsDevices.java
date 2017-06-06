package com.dev.doods.omvremote2.Storage.Smart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Ividata7 on 10/05/2017.
 */

public class SmartSelfTestLogsDevices {
    @SerializedName("num")
    @Expose
    private String num;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("remaining")
    @Expose
    private String remaining;
    @SerializedName("lifetime")
    @Expose
    private String lifetime;
    @SerializedName("lbaoffirsterror")
    @Expose
    private String lbaoffirsterror;

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemaining() {
        return remaining;
    }

    public void setRemaining(String remaining) {
        this.remaining = remaining;
    }

    public String getLifetime() {
        return lifetime;
    }

    public void setLifetime(String lifetime) {
        this.lifetime = lifetime;
    }

    public String getLbaoffirsterror() {
        return lbaoffirsterror;
    }

    public void setLbaoffirsterror(String lbaoffirsterror) {
        this.lbaoffirsterror = lbaoffirsterror;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(num).append(description).append(status).append(remaining).append(lifetime).append(lbaoffirsterror).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof SmartSelfTestLogsDevices) == false) {
            return false;
        }
        SmartSelfTestLogsDevices rhs = ((SmartSelfTestLogsDevices) other);
        return new EqualsBuilder().append(num, rhs.num).append(description, rhs.description).append(status, rhs.status).append(remaining, rhs.remaining).append(lifetime, rhs.lifetime).append(lbaoffirsterror, rhs.lbaoffirsterror).isEquals();
    }

}
