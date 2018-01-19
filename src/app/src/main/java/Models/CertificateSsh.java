package Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by thiba on 23/11/2016.
 */

public class CertificateSsh implements Serializable {
    @SerializedName("uuid")
    @Expose
    private String uuid;
    @SerializedName("publickey")
    @Expose
    private String publickey;
    @SerializedName("privatekey")
    @Expose
    private String privatekey;
    @SerializedName("comment")
    @Expose
    private String comment;

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
     * The publickey
     */
    public String getPublickey() {
        return publickey;
    }

    /**
     *
     * @param publickey
     * The publickey
     */
    public void setPublickey(String publickey) {
        this.publickey = publickey;
    }

    /**
     *
     * @return
     * The privatekey
     */
    public String getPrivatekey() {
        return privatekey;
    }

    /**
     *
     * @param privatekey
     * The privatekey
     */
    public void setPrivatekey(String privatekey) {
        this.privatekey = privatekey;
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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(uuid).append(publickey).append(privatekey).append(comment).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof CertificateSsh) == false) {
            return false;
        }
        CertificateSsh rhs = ((CertificateSsh) other);
        return new EqualsBuilder().append(uuid, rhs.uuid).append(publickey, rhs.publickey).append(privatekey, rhs.privatekey).append(comment, rhs.comment).isEquals();
    }

}
