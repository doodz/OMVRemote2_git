package Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

/**
 * Created by thiba on 30/10/2016.
 */

public class SharedFolder implements Serializable {

    @SerializedName("uuid")
    @Expose
    private String uuid;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("mntentref")
    @Expose
    private String mntentref;
    @SerializedName("reldirpath")
    @Expose
    private String reldirpath;
    @SerializedName("privileges")
    @Expose
    private Privileges privileges;
    @SerializedName("_used")
    @Expose
    private Boolean used;
    @SerializedName("volume")
    @Expose
    private String volume;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("mntent")
    @Expose
    private Mntent mntent;

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
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The comment
     */
    public String getComment() {
        return comment;
    }

    /**
     *
     * @param comment
     * The comment
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     *
     * @return
     * The mntentref
     */
    public String getMntentref() {
        return mntentref;
    }

    /**
     *
     * @param mntentref
     * The mntentref
     */
    public void setMntentref(String mntentref) {
        this.mntentref = mntentref;
    }

    /**
     *
     * @return
     * The reldirpath
     */
    public String getReldirpath() {
        return reldirpath;
    }

    /**
     *
     * @param reldirpath
     * The reldirpath
     */
    public void setReldirpath(String reldirpath) {
        this.reldirpath = reldirpath;
    }

    /**
     *
     * @return
     * The privileges
     */
    public Privileges getPrivileges() {
        return privileges;
    }

    /**
     *
     * @param privileges
     * The privileges
     */
    public void setPrivileges(Privileges privileges) {
        this.privileges = privileges;
    }

    /**
     *
     * @return
     * The used
     */
    public Boolean getUsed() {
        return used;
    }

    /**
     *
     * @param used
     * The _used
     */
    public void setUsed(Boolean used) {
        this.used = used;
    }

    /**
     *
     * @return
     * The volume
     */
    public String getVolume() {
        return volume;
    }

    /**
     *
     * @param volume
     * The volume
     */
    public void setVolume(String volume) {
        this.volume = volume;
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

    /**
     *
     * @return
     * The mntent
     */
    public Mntent getMntent() {
        return mntent;
    }

    /**
     *
     * @param mntent
     * The mntent
     */
    public void setMntent(Mntent mntent) {
        this.mntent = mntent;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(uuid).append(name).append(comment).append(mntentref).append(reldirpath).append(privileges).append(used).append(volume).append(description).append(mntent).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof SharedFolder) == false) {
            return false;
        }
        SharedFolder rhs = ((SharedFolder) other);
        return new EqualsBuilder().append(uuid, rhs.uuid).append(name, rhs.name).append(comment, rhs.comment).append(mntentref, rhs.mntentref).append(reldirpath, rhs.reldirpath).append(privileges, rhs.privileges).append(used, rhs.used).append(volume, rhs.volume).append(description, rhs.description).append(mntent, rhs.mntent).isEquals();
    }


    @Override
    public String toString()
    {
        return description;
    }

}
