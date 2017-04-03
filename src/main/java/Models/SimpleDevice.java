package Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by thiba on 05/11/2016.
 */

public class SimpleDevice {

    @SerializedName("uuid")
    @Expose
    private String uuid;
    @SerializedName("devicefile")
    @Expose
    private String devicefile;
    @SerializedName("description")
    @Expose
    private String description;

    /**
     *
     * @return
     * The uuid
     */
    public String getUuid() {
        return uuid;
    }

    /**
     *
     * @param uuid
     * The uuid
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     *
     * @return
     * The devicefile
     */
    public String getDevicefile() {
        return devicefile;
    }

    /**
     *
     * @param devicefile
     * The devicefile
     */
    public void setDevicefile(String devicefile) {
        this.devicefile = devicefile;
    }

    /**
     *
     * @return
     * The description
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     * The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(uuid).append(devicefile).append(description).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof SimpleDevice) == false) {
            return false;
        }
        SimpleDevice rhs = ((SimpleDevice) other);
        return new EqualsBuilder().append(uuid, rhs.uuid).append(devicefile, rhs.devicefile).append(description, rhs.description).isEquals();
    }
}
