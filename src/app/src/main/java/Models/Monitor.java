package Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * Created by Ividata7 on 27/01/2017.
 */

public class Monitor {
    @SerializedName("device")
    @Expose
    private List<DeviceMonitor> device = null;

    public List<DeviceMonitor> getDevice() {
        return device;
    }

    public void setDevice(List<DeviceMonitor> device) {
        this.device = device;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(device).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Monitor) == false) {
            return false;
        }
        Monitor rhs = ((Monitor) other);
        return new EqualsBuilder().append(device, rhs.device).isEquals();
    }
}
