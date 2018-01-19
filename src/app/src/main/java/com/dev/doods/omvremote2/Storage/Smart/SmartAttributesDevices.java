package com.dev.doods.omvremote2.Storage.Smart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Ividata7 on 10/05/2017.
 */

public class SmartAttributesDevices {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("attrname")
    @Expose
    private String attrname;
    @SerializedName("flags")
    @Expose
    private String flags;
    @SerializedName("value")
    @Expose
    private Integer value;
    @SerializedName("worst")
    @Expose
    private Integer worst;
    @SerializedName("treshold")
    @Expose
    private Integer treshold;
    @SerializedName("whenfailed")
    @Expose
    private String whenfailed;
    @SerializedName("rawvalue")
    @Expose
    private String rawvalue;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("prefailure")
    @Expose
    private Boolean prefailure;
    @SerializedName("assessment")
    @Expose
    private String assessment;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAttrname() {
        return attrname;
    }

    public void setAttrname(String attrname) {
        this.attrname = attrname;
    }

    public String getFlags() {
        return flags;
    }

    public void setFlags(String flags) {
        this.flags = flags;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer getWorst() {
        return worst;
    }

    public void setWorst(Integer worst) {
        this.worst = worst;
    }

    public Integer getTreshold() {
        return treshold;
    }

    public void setTreshold(Integer treshold) {
        this.treshold = treshold;
    }

    public String getWhenfailed() {
        return whenfailed;
    }

    public void setWhenfailed(String whenfailed) {
        this.whenfailed = whenfailed;
    }

    public String getRawvalue() {
        return rawvalue;
    }

    public void setRawvalue(String rawvalue) {
        this.rawvalue = rawvalue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getPrefailure() {
        return prefailure;
    }

    public void setPrefailure(Boolean prefailure) {
        this.prefailure = prefailure;
    }

    public String getAssessment() {
        return assessment;
    }

    public void setAssessment(String assessment) {
        this.assessment = assessment;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).append(attrname).append(flags).append(value).append(worst).append(treshold).append(whenfailed).append(rawvalue).append(description).append(prefailure).append(assessment).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof SmartAttributesDevices) == false) {
            return false;
        }
        SmartAttributesDevices rhs = ((SmartAttributesDevices) other);
        return new EqualsBuilder().append(id, rhs.id).append(attrname, rhs.attrname).append(flags, rhs.flags).append(value, rhs.value).append(worst, rhs.worst).append(treshold, rhs.treshold).append(whenfailed, rhs.whenfailed).append(rawvalue, rhs.rawvalue).append(description, rhs.description).append(prefailure, rhs.prefailure).append(assessment, rhs.assessment).isEquals();
    }
}
