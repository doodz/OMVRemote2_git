package Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by doods on 03/09/2017.
 */

public class NutSettings {

    @SerializedName("enable")
    @Expose
    private Boolean enable;
    @SerializedName("upsname")
    @Expose
    private String upsname;
    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("driverconf")
    @Expose
    private String driverconf;
    @SerializedName("shutdownmode")
    @Expose
    private String shutdownmode;
    @SerializedName("shutdowntimer")
    @Expose
    private Integer shutdowntimer;
    @SerializedName("remotemonitor")
    @Expose
    private Boolean remotemonitor;
    @SerializedName("remoteuser")
    @Expose
    private String remoteuser;
    @SerializedName("remotepassword")
    @Expose
    private String remotepassword;

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public String getUpsname() {
        return upsname;
    }

    public void setUpsname(String upsname) {
        this.upsname = upsname;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDriverconf() {
        return driverconf;
    }

    public void setDriverconf(String driverconf) {
        this.driverconf = driverconf;
    }

    public String getShutdownmode() {
        return shutdownmode;
    }

    public void setShutdownmode(String shutdownmode) {
        this.shutdownmode = shutdownmode;
    }

    public Integer getShutdowntimer() {
        return shutdowntimer;
    }

    public void setShutdowntimer(Integer shutdowntimer) {
        this.shutdowntimer = shutdowntimer;
    }

    public Boolean getRemotemonitor() {
        return remotemonitor;
    }

    public void setRemotemonitor(Boolean remotemonitor) {
        this.remotemonitor = remotemonitor;
    }

    public String getRemoteuser() {
        return remoteuser;
    }

    public void setRemoteuser(String remoteuser) {
        this.remoteuser = remoteuser;
    }

    public String getRemotepassword() {
        return remotepassword;
    }

    public void setRemotepassword(String remotepassword) {
        this.remotepassword = remotepassword;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(enable).append(upsname).append(comment).append(driverconf).append(shutdownmode).append(shutdowntimer).append(remotemonitor).append(remoteuser).append(remotepassword).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof NutSettings) == false) {
            return false;
        }
        NutSettings rhs = ((NutSettings) other);
        return new EqualsBuilder().append(enable, rhs.enable).append(upsname, rhs.upsname).append(comment, rhs.comment).append(driverconf, rhs.driverconf).append(shutdownmode, rhs.shutdownmode).append(shutdowntimer, rhs.shutdowntimer).append(remotemonitor, rhs.remotemonitor).append(remoteuser, rhs.remoteuser).append(remotepassword, rhs.remotepassword).isEquals();
    }
}
