package Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Ividata7 on 20/06/2017.
 */

public class Interface {

    @SerializedName("uuid")
    @Expose
    private String uuid;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("devicename")
    @Expose
    private String devicename;
    @SerializedName("method")
    @Expose
    private String method;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("netmask")
    @Expose
    private String netmask;
    @SerializedName("gateway")
    @Expose
    private String gateway;
    @SerializedName("method6")
    @Expose
    private String method6;
    @SerializedName("address6")
    @Expose
    private String address6;
    @SerializedName("netmask6")
    @Expose
    private Integer netmask6;
    @SerializedName("gateway6")
    @Expose
    private String gateway6;
    @SerializedName("dnsnameservers")
    @Expose
    private String dnsnameservers;
    @SerializedName("dnssearch")
    @Expose
    private String dnssearch;
    /*
    @SerializedName("mtu")
    @Expose
    private Integer mtu;
    */
    @SerializedName("wol")
    @Expose
    private Boolean wol;
    @SerializedName("options")
    @Expose
    private String options;
    @SerializedName("comment")
    @Expose
    private String comment;
    /*
    @SerializedName("slaves")
    @Expose
    private String slaves;
    @SerializedName("bondprimary")
    @Expose
    private String bondprimary;
    @SerializedName("bondmode")
    @Expose
    private Integer bondmode;
    @SerializedName("bondmiimon")
    @Expose
    private Integer bondmiimon;
    @SerializedName("bonddowndelay")
    @Expose
    private Integer bonddowndelay;
    @SerializedName("bondupdelay")
    @Expose
    private Integer bondupdelay;
    @SerializedName("vlanid")
    @Expose
    private Integer vlanid;
    @SerializedName("vlanrawdevice")
    @Expose
    private String vlanrawdevice;
    @SerializedName("wpassid")
    @Expose
    private String wpassid;
    @SerializedName("wpapsk")
    @Expose
    private String wpapsk;
    */
    @SerializedName("_used")
    @Expose
    private Boolean used;
    @SerializedName("_readonly")
    @Expose
    private Boolean readonly;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDevicename() {
        return devicename;
    }

    public void setDevicename(String devicename) {
        this.devicename = devicename;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNetmask() {
        return netmask;
    }

    public void setNetmask(String netmask) {
        this.netmask = netmask;
    }

    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway;
    }

    public String getMethod6() {
        return method6;
    }

    public void setMethod6(String method6) {
        this.method6 = method6;
    }

    public String getAddress6() {
        return address6;
    }

    public void setAddress6(String address6) {
        this.address6 = address6;
    }

    public Integer getNetmask6() {
        return netmask6;
    }

    public void setNetmask6(Integer netmask6) {
        this.netmask6 = netmask6;
    }

    public String getGateway6() {
        return gateway6;
    }

    public void setGateway6(String gateway6) {
        this.gateway6 = gateway6;
    }

    public String getDnsnameservers() {
        return dnsnameservers;
    }

    public void setDnsnameservers(String dnsnameservers) {
        this.dnsnameservers = dnsnameservers;
    }

    public String getDnssearch() {
        return dnssearch;
    }

    public void setDnssearch(String dnssearch) {
        this.dnssearch = dnssearch;
    }
    /*
    public Integer getMtu() {
        return mtu;
    }

    public void setMtu(Integer mtu) {
        this.mtu = mtu;
    }
    */
    public Boolean getWol() {
        return wol;
    }

    public void setWol(Boolean wol) {
        this.wol = wol;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
/*
    public String getSlaves() {
        return slaves;
    }

    public void setSlaves(String slaves) {
        this.slaves = slaves;
    }

    public String getBondprimary() {
        return bondprimary;
    }

    public void setBondprimary(String bondprimary) {
        this.bondprimary = bondprimary;
    }

    public Integer getBondmode() {
        return bondmode;
    }

    public void setBondmode(Integer bondmode) {
        this.bondmode = bondmode;
    }

    public Integer getBondmiimon() {
        return bondmiimon;
    }

    public void setBondmiimon(Integer bondmiimon) {
        this.bondmiimon = bondmiimon;
    }

    public Integer getBonddowndelay() {
        return bonddowndelay;
    }

    public void setBonddowndelay(Integer bonddowndelay) {
        this.bonddowndelay = bonddowndelay;
    }

    public Integer getBondupdelay() {
        return bondupdelay;
    }

    public void setBondupdelay(Integer bondupdelay) {
        this.bondupdelay = bondupdelay;
    }

    public Integer getVlanid() {
        return vlanid;
    }

    public void setVlanid(Integer vlanid) {
        this.vlanid = vlanid;
    }

    public String getVlanrawdevice() {
        return vlanrawdevice;
    }

    public void setVlanrawdevice(String vlanrawdevice) {
        this.vlanrawdevice = vlanrawdevice;
    }

    public String getWpassid() {
        return wpassid;
    }

    public void setWpassid(String wpassid) {
        this.wpassid = wpassid;
    }

    public String getWpapsk() {
        return wpapsk;
    }

    public void setWpapsk(String wpapsk) {
        this.wpapsk = wpapsk;
    }
*/
    public Boolean getUsed() {
        return used;
    }

    public void setUsed(Boolean used) {
        this.used = used;
    }

    public Boolean getReadonly() {
        return readonly;
    }

    public void setReadonly(Boolean readonly) {
        this.readonly = readonly;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(uuid).append(type).append(devicename).append(method).append(address).append(netmask).append(gateway).append(method6).append(address6).append(netmask6).append(gateway6).append(dnsnameservers).append(dnssearch)./*append(mtu).*/append(wol).append(options).append(comment)/*.append(slaves).append(bondprimary).append(bondmode).append(bondmiimon).append(bonddowndelay).append(bondupdelay).append(vlanid).append(vlanrawdevice).append(wpassid).append(wpapsk)*/.append(used).append(readonly).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Interface) == false) {
            return false;
        }
        Interface rhs = ((Interface) other);
        return new EqualsBuilder().append(uuid, rhs.uuid).append(type, rhs.type).append(devicename, rhs.devicename).append(method, rhs.method).append(address, rhs.address).append(netmask, rhs.netmask).append(gateway, rhs.gateway).append(method6, rhs.method6).append(address6, rhs.address6).append(netmask6, rhs.netmask6).append(gateway6, rhs.gateway6).append(dnsnameservers, rhs.dnsnameservers).append(dnssearch, rhs.dnssearch)./*append(mtu, rhs.mtu).*/append(wol, rhs.wol).append(options, rhs.options).append(comment, rhs.comment)./*append(slaves, rhs.slaves).append(bondprimary, rhs.bondprimary).append(bondmode, rhs.bondmode).append(bondmiimon, rhs.bondmiimon).append(bonddowndelay, rhs.bonddowndelay).append(bondupdelay, rhs.bondupdelay).append(vlanid, rhs.vlanid).append(vlanrawdevice, rhs.vlanrawdevice).append(wpassid, rhs.wpassid).append(wpapsk, rhs.wpapsk).*/append(used, rhs.used).append(readonly, rhs.readonly).isEquals();
    }
}
