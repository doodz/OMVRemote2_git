package Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by thiba on 01/12/2016.
 */

public class CertificateSsl {

    @SerializedName("uuid")
    @Expose
    private String uuid;
    @SerializedName("certificate")
    @Expose
    private String certificate;
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
     * The certificate
     */
    public String getCertificate() {
        return certificate;
    }

    /**
     *
     * @param certificate
     * The certificate
     */
    public void setCertificate(String certificate) {
        this.certificate = certificate;
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
        return new HashCodeBuilder().append(uuid).append(certificate).append(privatekey).append(comment).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof CertificateSsl) == false) {
            return false;
        }
        CertificateSsl rhs = ((CertificateSsl) other);
        return new EqualsBuilder().append(uuid, rhs.uuid).append(certificate, rhs.certificate).append(privatekey, rhs.privatekey).append(comment, rhs.comment).isEquals();
    }
}
