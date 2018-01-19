package Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Ividata7 on 27/01/2017.
 */

public class DeviceMonitor {
    @SerializedName("uuid")
    @Expose
    private String uuid;
    @SerializedName("devicefile")
    @Expose
    private String devicefile;
    @SerializedName("enable")
    @Expose
    private String enable;
    @SerializedName("type")
    @Expose
    private String type;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getDevicefile() {
        return devicefile;
    }

    public void setDevicefile(String devicefile) {
        this.devicefile = devicefile;
    }

    public String getEnable() {
        return enable;
    }

    public void setEnable(String enable) {
        this.enable = enable;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(uuid).append(devicefile).append(enable).append(type).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof DeviceMonitor) == false) {
            return false;
        }
        DeviceMonitor rhs = ((DeviceMonitor) other);
        return new EqualsBuilder().append(uuid, rhs.uuid).append(devicefile, rhs.devicefile).append(enable, rhs.enable).append(type, rhs.type).isEquals();
    }
}
