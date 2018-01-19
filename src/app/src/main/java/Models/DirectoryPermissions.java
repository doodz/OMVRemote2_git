package Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Ividata7 on 31/03/2017.
 */

public class DirectoryPermissions {

    @SerializedName("executable")
    @Expose
    private Boolean executable;
    @SerializedName("readable")
    @Expose
    private Boolean readable;
    @SerializedName("writable")
    @Expose
    private Boolean writable;

    public Boolean getExecutable() {
        return executable;
    }

    public void setExecutable(Boolean executable) {
        this.executable = executable;
    }

    public Boolean getReadable() {
        return readable;
    }

    public void setReadable(Boolean readable) {
        this.readable = readable;
    }

    public Boolean getWritable() {
        return writable;
    }

    public void setWritable(Boolean writable) {
        this.writable = writable;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(executable).append(readable).append(writable).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof DirectoryPermissions) == false) {
            return false;
        }
        DirectoryPermissions rhs = ((DirectoryPermissions) other);
        return new EqualsBuilder().append(executable, rhs.executable).append(readable, rhs.readable).append(writable, rhs.writable).isEquals();
    }

}
