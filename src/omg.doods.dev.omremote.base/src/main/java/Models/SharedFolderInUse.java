package Models;

import android.os.Environment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.sym.Name;

/**
 * Created by thiba on 03/11/2016.
 */

public class SharedFolderInUse {

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
    @SerializedName("inuse")
    @Expose
    private String inuse;

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
     * The inuse
     */
    public String getInuse() {
        return inuse;
    }

    /**
     *
     * @param inuse
     * The inuse
     */
    public void setInuse(String inuse) {
        this.inuse = inuse;
    }

    @Override
    public String toString() {

        String[] res = inuse.split("<br />");
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        sb.append(" [ ");
        sb.append("Use by ");
        sb.append(res.length);
        sb.append(" Servicies");
        sb.append(" ]");
        return sb.toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(uuid).append(name).append(comment).append(mntentref).append(reldirpath).append(privileges).append(inuse).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof SharedFolderInUse) == false) {
            return false;
        }
        SharedFolderInUse rhs = ((SharedFolderInUse) other);
        return new EqualsBuilder().append(uuid, rhs.uuid).append(name, rhs.name).append(comment, rhs.comment).append(mntentref, rhs.mntentref).append(reldirpath, rhs.reldirpath).append(privileges, rhs.privileges).append(inuse, rhs.inuse).isEquals();
    }

}
