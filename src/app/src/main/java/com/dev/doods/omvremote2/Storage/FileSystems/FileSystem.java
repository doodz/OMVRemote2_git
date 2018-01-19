package com.dev.doods.omvremote2.Storage.FileSystems;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by thiba on 06/09/2016.
 */
public class FileSystem {

    @SerializedName("devicefile")
    @Expose
    private String devicefile;
    @SerializedName("uuid")
    @Expose
    private String uuid;
    @SerializedName("label")
    @Expose
    private String label;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("blocks")
    @Expose
    private String blocks;
    @SerializedName("mounted")
    @Expose
    private Boolean mounted;
    @SerializedName("mountpoint")
    @Expose
    private String mountpoint;
    @SerializedName("used")
    @Expose
    private String usedSize;
    @SerializedName("available")
    @Expose
    private String available;
    @SerializedName("size")
    @Expose
    private String size;
    @SerializedName("percentage")
    @Expose
    private Integer percentage;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("propposixacl")
    @Expose
    private Boolean propposixacl;
    @SerializedName("propquota")
    @Expose
    private Boolean propquota;
    @SerializedName("propresize")
    @Expose
    private Boolean propresize;
    @SerializedName("propfstab")
    @Expose
    private Boolean propfstab;
    @SerializedName("propreadonly")
    @Expose
    private Boolean propreadonly;
    @SerializedName("_readonly")
    @Expose
    private Boolean readonly;
    @SerializedName("_used")
    @Expose
    private Boolean used;
    @SerializedName("status")
    @Expose
    private Integer status;

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
     * The label
     */
    public String getLabel() {
        return label;
    }

    /**
     *
     * @param label
     * The label
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     *
     * @return
     * The type
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type
     * The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     * @return
     * The blocks
     */
    public String getBlocks() {
        return blocks;
    }

    /**
     *
     * @param blocks
     * The blocks
     */
    public void setBlocks(String blocks) {
        this.blocks = blocks;
    }

    /**
     *
     * @return
     * The mounted
     */
    public Boolean getMounted() {
        return mounted;
    }

    /**
     *
     * @param mounted
     * The mounted
     */
    public void setMounted(Boolean mounted) {
        this.mounted = mounted;
    }

    /**
     *
     * @return
     * The mountpoint
     */
    public String getMountpoint() {
        return mountpoint;
    }

    /**
     *
     * @param mountpoint
     * The mountpoint
     */
    public void setMountpoint(String mountpoint) {
        this.mountpoint = mountpoint;
    }

    /**
     *
     * @return
     * The used
     */
    public String getUsedSize() {
        return usedSize;
    }

    /**
     *
     * @param usedSize
     * The used
     */
    public void setUsedSize(String usedSize) {
        this.usedSize = usedSize;
    }

    /**
     *
     * @return
     * The available
     */
    public String getAvailable() {
        return available;
    }

    /**
     *
     * @param available
     * The available
     */
    public void setAvailable(String available) {
        this.available = available;
    }

    /**
     *
     * @return
     * The size
     */
    public String getSize() {
        return size;
    }

    /**
     *
     * @param size
     * The size
     */
    public void setSize(String size) {
        this.size = size;
    }

    /**
     *
     * @return
     * The percentage
     */
    public Integer getPercentage() {
        return percentage;
    }

    /**
     *
     * @param percentage
     * The percentage
     */
    public void setPercentage(Integer percentage) {
        this.percentage = percentage;
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
     * The propposixacl
     */
    public Boolean getPropposixacl() {
        return propposixacl;
    }

    /**
     *
     * @param propposixacl
     * The propposixacl
     */
    public void setPropposixacl(Boolean propposixacl) {
        this.propposixacl = propposixacl;
    }

    /**
     *
     * @return
     * The propquota
     */
    public Boolean getPropquota() {
        return propquota;
    }

    /**
     *
     * @param propquota
     * The propquota
     */
    public void setPropquota(Boolean propquota) {
        this.propquota = propquota;
    }

    /**
     *
     * @return
     * The propresize
     */
    public Boolean getPropresize() {
        return propresize;
    }

    /**
     *
     * @param propresize
     * The propresize
     */
    public void setPropresize(Boolean propresize) {
        this.propresize = propresize;
    }

    /**
     *
     * @return
     * The propfstab
     */
    public Boolean getPropfstab() {
        return propfstab;
    }

    /**
     *
     * @param propfstab
     * The propfstab
     */
    public void setPropfstab(Boolean propfstab) {
        this.propfstab = propfstab;
    }

    /**
     *
     * @return
     * The propreadonly
     */
    public Boolean getPropreadonly() {
        return propreadonly;
    }

    /**
     *
     * @param propreadonly
     * The propreadonly
     */
    public void setPropreadonly(Boolean propreadonly) {
        this.propreadonly = propreadonly;
    }

    /**
     *
     * @return
     * The readonly
     */
    public Boolean getReadonly() {
        return readonly;
    }

    /**
     *
     * @param readonly
     * The _readonly
     */
    public void setReadonly(Boolean readonly) {
        this.readonly = readonly;
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
     * The used
     */
    public void setUsed(Boolean used) {
        this.used = used;
    }

    /**
     *
     * @return
     * The status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     *
     * @param status
     * The status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

}