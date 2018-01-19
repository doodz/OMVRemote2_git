package Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * Created by thiba on 19/12/2016.
 */

public class OmvUser {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("uid")
    @Expose
    private Integer uid;
    @SerializedName("gid")
    @Expose
    private Integer gid;
    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("dir")
    @Expose
    private String dir;
    @SerializedName("shell")
    @Expose
    private String shell;
    @SerializedName("lastchanged")
    @Expose
    private String lastchanged;
    @SerializedName("minimum")
    @Expose
    private String minimum;
    @SerializedName("maximum")
    @Expose
    private String maximum;
    @SerializedName("warn")
    @Expose
    private String warn;
    @SerializedName("inactive")
    @Expose
    private String inactive;
    @SerializedName("expire")
    @Expose
    private String expire;
    @SerializedName("reserved")
    @Expose
    private String reserved;
    //@SerializedName("groups")
    //@Expose
    //private List<String> groups = null;
    @SerializedName("system")
    @Expose
    private Boolean system;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getGid() {
        return gid;
    }

    public void setGid(Integer gid) {
        this.gid = gid;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getShell() {
        return shell;
    }

    public void setShell(String shell) {
        this.shell = shell;
    }

    public String getLastchanged() {
        return lastchanged;
    }

    public void setLastchanged(String lastchanged) {
        this.lastchanged = lastchanged;
    }

    public String getMinimum() {
        return minimum;
    }

    public void setMinimum(String minimum) {
        this.minimum = minimum;
    }

    public String getMaximum() {
        return maximum;
    }

    public void setMaximum(String maximum) {
        this.maximum = maximum;
    }

    public String getWarn() {
        return warn;
    }

    public void setWarn(String warn) {
        this.warn = warn;
    }

    public String getInactive() {
        return inactive;
    }

    public void setInactive(String inactive) {
        this.inactive = inactive;
    }

    public String getExpire() {
        return expire;
    }

    public void setExpire(String expire) {
        this.expire = expire;
    }

    public String getReserved() {
        return reserved;
    }

    public void setReserved(String reserved) {
        this.reserved = reserved;
    }

    //public List<String> getGroups() {
    //    return groups;
    //}

    //public void setGroups(List<String> groups) {
    //    this.groups = groups;
    //}

    public Boolean getSystem() {
        return system;
    }

    public void setSystem(Boolean system) {
        this.system = system;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(name).append(uid).append(gid).append(comment).append(dir).append(shell).append(lastchanged).append(minimum).append(maximum).append(warn).append(inactive).append(expire).append(reserved)/*.append(groups)*/.append(system).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof OmvUser) == false) {
            return false;
        }
        OmvUser rhs = ((OmvUser) other);
        return new EqualsBuilder().append(name, rhs.name).append(uid, rhs.uid).append(gid, rhs.gid).append(comment, rhs.comment).append(dir, rhs.dir).append(shell, rhs.shell).append(lastchanged, rhs.lastchanged).append(minimum, rhs.minimum).append(maximum, rhs.maximum).append(warn, rhs.warn).append(inactive, rhs.inactive).append(expire, rhs.expire).append(reserved, rhs.reserved)./*append(groups, rhs.groups).*/append(system, rhs.system).isEquals();
    }
}
