package Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by thiba on 11/12/2016.
 */

public class SslCertificateInfoCreate {

    @SerializedName("size")
    @Expose
    private Integer size;
    @SerializedName("days")
    @Expose
    private Integer days;
    @SerializedName("cn")
    @Expose
    private String cn;
    @SerializedName("o")
    @Expose
    private String o;
    @SerializedName("ou")
    @Expose
    private String ou;
    @SerializedName("l")
    @Expose
    private String l;
    @SerializedName("st")
    @Expose
    private String st;
    @SerializedName("c")
    @Expose
    private String c;
    @SerializedName("email")
    @Expose
    private String email;

    /**
     *
     * @return
     * The size
     */
    public Integer getSize() {
        return size;
    }

    /**
     *
     * @param size
     * The size
     */
    public void setSize(Integer size) {
        this.size = size;
    }

    /**
     *
     * @return
     * The days
     */
    public Integer getDays() {
        return days;
    }

    /**
     *
     * @param days
     * The days
     */
    public void setDays(Integer days) {
        this.days = days;
    }

    /**
     *
     * @return
     * The cn
     */
    public String getCn() {
        return cn;
    }

    /**
     *
     * @param cn
     * The cn
     */
    public void setCn(String cn) {
        this.cn = cn;
    }

    /**
     *
     * @return
     * The o
     */
    public String getO() {
        return o;
    }

    /**
     *
     * @param o
     * The o
     */
    public void setO(String o) {
        this.o = o;
    }

    /**
     *
     * @return
     * The ou
     */
    public String getOu() {
        return ou;
    }

    /**
     *
     * @param ou
     * The ou
     */
    public void setOu(String ou) {
        this.ou = ou;
    }

    /**
     *
     * @return
     * The l
     */
    public String getL() {
        return l;
    }

    /**
     *
     * @param l
     * The l
     */
    public void setL(String l) {
        this.l = l;
    }

    /**
     *
     * @return
     * The st
     */
    public String getSt() {
        return st;
    }

    /**
     *
     * @param st
     * The st
     */
    public void setSt(String st) {
        this.st = st;
    }

    /**
     *
     * @return
     * The c
     */
    public String getC() {
        return c;
    }

    /**
     *
     * @param c
     * The c
     */
    public void setC(String c) {
        this.c = c;
    }

    /**
     *
     * @return
     * The email
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     * The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(size).append(days).append(cn).append(o).append(ou).append(l).append(st).append(c).append(email).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof SslCertificateInfoCreate) == false) {
            return false;
        }
        SslCertificateInfoCreate rhs = ((SslCertificateInfoCreate) other);
        return new EqualsBuilder().append(size, rhs.size).append(days, rhs.days).append(cn, rhs.cn).append(o, rhs.o).append(ou, rhs.ou).append(l, rhs.l).append(st, rhs.st).append(c, rhs.c).append(email, rhs.email).isEquals();
    }


}
