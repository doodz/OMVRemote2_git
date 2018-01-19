package Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
/**
 * Created by thiba on 26/10/2016.
 */

public class Shares {

    @SerializedName("uuid")
    @Expose
    private String uuid;
    @SerializedName("enable")
    @Expose
    private String enable;
    @SerializedName("sharedfolderref")
    @Expose
    private String sharedfolderref;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("guest")
    @Expose
    private String guest;
    @SerializedName("readonly")
    @Expose
    private String readonly;
    @SerializedName("browseable")
    @Expose
    private String browseable;
    @SerializedName("inheritacls")
    @Expose
    private String inheritacls;
    @SerializedName("inheritpermissions")
    @Expose
    private String inheritpermissions;
    @SerializedName("recyclebin")
    @Expose
    private String recyclebin;
    @SerializedName("recyclemaxsize")
    @Expose
    private String recyclemaxsize;
    @SerializedName("recyclemaxage")
    @Expose
    private String recyclemaxage;
    @SerializedName("hidedotfiles")
    @Expose
    private String hidedotfiles;
    @SerializedName("easupport")
    @Expose
    private String easupport;
    @SerializedName("storedosattributes")
    @Expose
    private String storedosattributes;
    @SerializedName("hostsallow")
    @Expose
    private String hostsallow;
    @SerializedName("hostsdeny")
    @Expose
    private String hostsdeny;
    @SerializedName("audit")
    @Expose
    private String audit;
    @SerializedName("extraoptions")
    @Expose
    private String extraoptions;

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
     * The enable
     */
    public String getEnable() {
        return enable;
    }

    /**
     *
     * @param enable
     * The enable
     */
    public void setEnable(String enable) {
        this.enable = enable;
    }

    /**
     *
     * @return
     * The sharedfolderref
     */
    public String getSharedfolderref() {
        return sharedfolderref;
    }

    /**
     *
     * @param sharedfolderref
     * The sharedfolderref
     */
    public void setSharedfolderref(String sharedfolderref) {
        this.sharedfolderref = sharedfolderref;
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
     * The guest
     */
    public String getGuest() {
        return guest;
    }

    /**
     *
     * @param guest
     * The guest
     */
    public void setGuest(String guest) {
        this.guest = guest;
    }

    /**
     *
     * @return
     * The readonly
     */
    public String getReadonly() {
        return readonly;
    }

    /**
     *
     * @param readonly
     * The readonly
     */
    public void setReadonly(String readonly) {
        this.readonly = readonly;
    }

    /**
     *
     * @return
     * The browseable
     */
    public String getBrowseable() {
        return browseable;
    }

    /**
     *
     * @param browseable
     * The browseable
     */
    public void setBrowseable(String browseable) {
        this.browseable = browseable;
    }

    /**
     *
     * @return
     * The inheritacls
     */
    public String getInheritacls() {
        return inheritacls;
    }

    /**
     *
     * @param inheritacls
     * The inheritacls
     */
    public void setInheritacls(String inheritacls) {
        this.inheritacls = inheritacls;
    }

    /**
     *
     * @return
     * The inheritpermissions
     */
    public String getInheritpermissions() {
        return inheritpermissions;
    }

    /**
     *
     * @param inheritpermissions
     * The inheritpermissions
     */
    public void setInheritpermissions(String inheritpermissions) {
        this.inheritpermissions = inheritpermissions;
    }

    /**
     *
     * @return
     * The recyclebin
     */
    public String getRecyclebin() {
        return recyclebin;
    }

    /**
     *
     * @param recyclebin
     * The recyclebin
     */
    public void setRecyclebin(String recyclebin) {
        this.recyclebin = recyclebin;
    }

    /**
     *
     * @return
     * The recyclemaxsize
     */
    public String getRecyclemaxsize() {
        return recyclemaxsize;
    }

    /**
     *
     * @param recyclemaxsize
     * The recyclemaxsize
     */
    public void setRecyclemaxsize(String recyclemaxsize) {
        this.recyclemaxsize = recyclemaxsize;
    }

    /**
     *
     * @return
     * The recyclemaxage
     */
    public String getRecyclemaxage() {
        return recyclemaxage;
    }

    /**
     *
     * @param recyclemaxage
     * The recyclemaxage
     */
    public void setRecyclemaxage(String recyclemaxage) {
        this.recyclemaxage = recyclemaxage;
    }

    /**
     *
     * @return
     * The hidedotfiles
     */
    public String getHidedotfiles() {
        return hidedotfiles;
    }

    /**
     *
     * @param hidedotfiles
     * The hidedotfiles
     */
    public void setHidedotfiles(String hidedotfiles) {
        this.hidedotfiles = hidedotfiles;
    }

    /**
     *
     * @return
     * The easupport
     */
    public String getEasupport() {
        return easupport;
    }

    /**
     *
     * @param easupport
     * The easupport
     */
    public void setEasupport(String easupport) {
        this.easupport = easupport;
    }

    /**
     *
     * @return
     * The storedosattributes
     */
    public String getStoredosattributes() {
        return storedosattributes;
    }

    /**
     *
     * @param storedosattributes
     * The storedosattributes
     */
    public void setStoredosattributes(String storedosattributes) {
        this.storedosattributes = storedosattributes;
    }

    /**
     *
     * @return
     * The hostsallow
     */
    public String getHostsallow() {
        return hostsallow;
    }

    /**
     *
     * @param hostsallow
     * The hostsallow
     */
    public void setHostsallow(String hostsallow) {
        this.hostsallow = hostsallow;
    }

    /**
     *
     * @return
     * The hostsdeny
     */
    public String getHostsdeny() {
        return hostsdeny;
    }

    /**
     *
     * @param hostsdeny
     * The hostsdeny
     */
    public void setHostsdeny(String hostsdeny) {
        this.hostsdeny = hostsdeny;
    }

    /**
     *
     * @return
     * The audit
     */
    public String getAudit() {
        return audit;
    }

    /**
     *
     * @param audit
     * The audit
     */
    public void setAudit(String audit) {
        this.audit = audit;
    }

    /**
     *
     * @return
     * The extraoptions
     */
    public String getExtraoptions() {
        return extraoptions;
    }

    /**
     *
     * @param extraoptions
     * The extraoptions
     */
    public void setExtraoptions(String extraoptions) {
        this.extraoptions = extraoptions;
    }


    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(uuid).append(enable).append(sharedfolderref).append(name).append(comment).append(guest).append(readonly).append(browseable).append(inheritacls).append(inheritpermissions).append(recyclebin).append(recyclemaxsize).append(recyclemaxage).append(hidedotfiles).append(easupport).append(storedosattributes).append(hostsallow).append(hostsdeny).append(audit).append(extraoptions).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Shares) == false) {
            return false;
        }
        Shares rhs = ((Shares) other);
        return new EqualsBuilder().append(uuid, rhs.uuid).append(enable, rhs.enable).append(sharedfolderref, rhs.sharedfolderref).append(name, rhs.name).append(comment, rhs.comment).append(guest, rhs.guest).append(readonly, rhs.readonly).append(browseable, rhs.browseable).append(inheritacls, rhs.inheritacls).append(inheritpermissions, rhs.inheritpermissions).append(recyclebin, rhs.recyclebin).append(recyclemaxsize, rhs.recyclemaxsize).append(recyclemaxage, rhs.recyclemaxage).append(hidedotfiles, rhs.hidedotfiles).append(easupport, rhs.easupport).append(storedosattributes, rhs.storedosattributes).append(hostsallow, rhs.hostsallow).append(hostsdeny, rhs.hostsdeny).append(audit, rhs.audit).append(extraoptions, rhs.extraoptions).isEquals();
    }
}
