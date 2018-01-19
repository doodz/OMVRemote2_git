package Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by thiba on 26/10/2016.
 */

public class SharedFolderNFS {

    @SerializedName("uuid")
    @Expose
    private String uuid;
    @SerializedName("sharedfolderref")
    @Expose
    private String sharedfolderref;
    @SerializedName("mntentref")
    @Expose
    private String mntentref;
    @SerializedName("client")
    @Expose
    private String client;
    @SerializedName("options")
    @Expose
    private String options;
    @SerializedName("extraoptions")
    @Expose
    private String extraoptions;
    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("sharedfoldername")
    @Expose
    private String sharedfoldername;

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
     * The client
     */
    public String getClient() {
        return client;
    }

    /**
     *
     * @param client
     * The client
     */
    public void setClient(String client) {
        this.client = client;
    }

    /**
     *
     * @return
     * The options
     */
    public String getOptions() {
        return options;
    }

    /**
     *
     * @param options
     * The options
     */
    public void setOptions(String options) {
        this.options = options;
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
     * The sharedfoldername
     */
    public String getSharedfoldername() {
        return sharedfoldername;
    }

    /**
     *
     * @param sharedfoldername
     * The sharedfoldername
     */
    public void setSharedfoldername(String sharedfoldername) {
        this.sharedfoldername = sharedfoldername;
    }
}
