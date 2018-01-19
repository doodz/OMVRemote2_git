package Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by thiba on 06/09/2016.
 */
public class Device {

    @SerializedName("devicename")
    @Expose
    private String devicename;
    @SerializedName("devicefile")
    @Expose
    private String devicefile;
    @SerializedName("devicefilebyid")
    @Expose
    private String devicefilebyid;
    @SerializedName("model")
    @Expose
    private String model;
    @SerializedName("size")
    @Expose
    private Boolean size;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("vendor")
    @Expose
    private String vendor;
    @SerializedName("serialnumber")
    @Expose
    private String serialnumber;
    @SerializedName("israid")
    @Expose
    private Boolean israid;
    @SerializedName("isroot")
    @Expose
    private Boolean isroot;



    /**
     *
     * @return
     * The devicename
     */
    public String getDevicename() {
        return devicename;
    }

    /**
     *
     * @param devicename
     * The devicename
     */
    public void setDevicename(String devicename) {
        this.devicename = devicename;
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
     * The devicefilebyid
     */
    public String getDevicefilebyid() {
        return devicefilebyid;
    }

    /**
     *
     * @param devicefilebyid
     * The devicefilebyid
     */
    public void setDevicefilebyid(String devicefilebyid) {
        this.devicefilebyid = devicefilebyid;
    }

    /**
     *
     * @return
     * The model
     */
    public String getModel() {
        return model;
    }

    /**
     *
     * @param model
     * The model
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     *
     * @return
     * The size
     */
    public Boolean getSize() {
        return size;
    }

    /**
     *
     * @param size
     * The size
     */
    public void setSize(Boolean size) {
        this.size = size;
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
     * The vendor
     */
    public String getVendor() {
        return vendor;
    }

    /**
     *
     * @param vendor
     * The vendor
     */
    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    /**
     *
     * @return
     * The serialnumber
     */
    public String getSerialnumber() {
        return serialnumber;
    }

    /**
     *
     * @param serialnumber
     * The serialnumber
     */
    public void setSerialnumber(String serialnumber) {
        this.serialnumber = serialnumber;
    }

    /**
     *
     * @return
     * The israid
     */
    public Boolean getIsraid() {
        return israid;
    }

    /**
     *
     * @param israid
     * The israid
     */
    public void setIsraid(Boolean israid) {
        this.israid = israid;
    }

    /**
     *
     * @return
     * The isroot
     */
    public Boolean getIsroot() {
        return isroot;
    }

    /**
     *
     * @param isroot
     * The isroot
     */
    public void setIsroot(Boolean isroot) {
        this.isroot = isroot;
    }

}
