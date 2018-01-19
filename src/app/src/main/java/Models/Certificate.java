package Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by thiba on 06/10/2016.
 */

public class Certificate implements Serializable {
    @SerializedName("uuid")
    @Expose
    private String uuid;
    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("validto")
    @Expose
    private Integer validto;
    @SerializedName("_used")
    @Expose
    private Boolean used;

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
     * The validto
     */
    public Integer getValidto() {
        return validto;
    }

    /**
     *
     * @param validto
     * The validto
     */
    public void setValidto(Integer validto) {
        this.validto = validto;
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


    @Override
    public String toString()
    {
        return this.name;
    }


}
