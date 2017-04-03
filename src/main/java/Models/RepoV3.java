package Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by thiba on 11/12/2016.
 */

public class RepoV3 implements Serializable {
    @SerializedName("uuid")
    @Expose
    private String uuid;
    @SerializedName("enable")
    @Expose
    private Boolean enable;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("repo1")
    @Expose
    private String repo1;
    @SerializedName("key1")
    @Expose
    private String key1;
    @SerializedName("package1")
    @Expose
    private String package1;
    @SerializedName("pin1")
    @Expose
    private String pin1;
    @SerializedName("priority1")
    @Expose
    private String priority1;
    @SerializedName("repo2")
    @Expose
    private String repo2;
    @SerializedName("key2")
    @Expose
    private String key2;
    @SerializedName("package2")
    @Expose
    private String package2;
    @SerializedName("pin2")
    @Expose
    private String pin2;
    @SerializedName("priority2")
    @Expose
    private String priority2;
    @SerializedName("repo3")
    @Expose
    private String repo3;
    @SerializedName("key3")
    @Expose
    private String key3;
    @SerializedName("package3")
    @Expose
    private String package3;
    @SerializedName("pin3")
    @Expose
    private String pin3;
    @SerializedName("priority3")
    @Expose
    private String priority3;
    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("permanent")
    @Expose
    private Boolean permanent;

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
     * The enable
     */
    public Boolean getEnable() {
        return enable;
    }

    /**
     *
     * @param enable
     * The enable
     */
    public void setEnable(Boolean enable) {
        this.enable = enable;
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
     * The repo1
     */
    public String getRepo1() {
        return repo1;
    }

    /**
     *
     * @param repo1
     * The repo1
     */
    public void setRepo1(String repo1) {
        this.repo1 = repo1;
    }

    /**
     *
     * @return
     * The key1
     */
    public String getKey1() {
        return key1;
    }

    /**
     *
     * @param key1
     * The key1
     */
    public void setKey1(String key1) {
        this.key1 = key1;
    }

    /**
     *
     * @return
     * The package1
     */
    public String getPackage1() {
        return package1;
    }

    /**
     *
     * @param package1
     * The package1
     */
    public void setPackage1(String package1) {
        this.package1 = package1;
    }

    /**
     *
     * @return
     * The pin1
     */
    public String getPin1() {
        return pin1;
    }

    /**
     *
     * @param pin1
     * The pin1
     */
    public void setPin1(String pin1) {
        this.pin1 = pin1;
    }

    /**
     *
     * @return
     * The priority1
     */
    public String getPriority1() {
        return priority1;
    }

    /**
     *
     * @param priority1
     * The priority1
     */
    public void setPriority1(String priority1) {
        this.priority1 = priority1;
    }

    /**
     *
     * @return
     * The repo2
     */
    public String getRepo2() {
        return repo2;
    }

    /**
     *
     * @param repo2
     * The repo2
     */
    public void setRepo2(String repo2) {
        this.repo2 = repo2;
    }

    /**
     *
     * @return
     * The key2
     */
    public String getKey2() {
        return key2;
    }

    /**
     *
     * @param key2
     * The key2
     */
    public void setKey2(String key2) {
        this.key2 = key2;
    }

    /**
     *
     * @return
     * The package2
     */
    public String getPackage2() {
        return package2;
    }

    /**
     *
     * @param package2
     * The package2
     */
    public void setPackage2(String package2) {
        this.package2 = package2;
    }

    /**
     *
     * @return
     * The pin2
     */
    public String getPin2() {
        return pin2;
    }

    /**
     *
     * @param pin2
     * The pin2
     */
    public void setPin2(String pin2) {
        this.pin2 = pin2;
    }

    /**
     *
     * @return
     * The priority2
     */
    public String getPriority2() {
        return priority2;
    }

    /**
     *
     * @param priority2
     * The priority2
     */
    public void setPriority2(String priority2) {
        this.priority2 = priority2;
    }

    /**
     *
     * @return
     * The repo3
     */
    public String getRepo3() {
        return repo3;
    }

    /**
     *
     * @param repo3
     * The repo3
     */
    public void setRepo3(String repo3) {
        this.repo3 = repo3;
    }

    /**
     *
     * @return
     * The key3
     */
    public String getKey3() {
        return key3;
    }

    /**
     *
     * @param key3
     * The key3
     */
    public void setKey3(String key3) {
        this.key3 = key3;
    }

    /**
     *
     * @return
     * The package3
     */
    public String getPackage3() {
        return package3;
    }

    /**
     *
     * @param package3
     * The package3
     */
    public void setPackage3(String package3) {
        this.package3 = package3;
    }

    /**
     *
     * @return
     * The pin3
     */
    public String getPin3() {
        return pin3;
    }

    /**
     *
     * @param pin3
     * The pin3
     */
    public void setPin3(String pin3) {
        this.pin3 = pin3;
    }

    /**
     *
     * @return
     * The priority3
     */
    public String getPriority3() {
        return priority3;
    }

    /**
     *
     * @param priority3
     * The priority3
     */
    public void setPriority3(String priority3) {
        this.priority3 = priority3;
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
     * The permanent
     */
    public Boolean getPermanent() {
        return permanent;
    }

    /**
     *
     * @param permanent
     * The permanent
     */
    public void setPermanent(Boolean permanent) {
        this.permanent = permanent;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(uuid).append(enable).append(name).append(repo1).append(key1).append(package1).append(pin1).append(priority1).append(repo2).append(key2).append(package2).append(pin2).append(priority2).append(repo3).append(key3).append(package3).append(pin3).append(priority3).append(comment).append(permanent).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Datum) == false) {
            return false;
        }
        RepoV3 rhs = ((RepoV3) other);
        return new EqualsBuilder().append(uuid, rhs.uuid).append(enable, rhs.enable).append(name, rhs.name).append(repo1, rhs.repo1).append(key1, rhs.key1).append(package1, rhs.package1).append(pin1, rhs.pin1).append(priority1, rhs.priority1).append(repo2, rhs.repo2).append(key2, rhs.key2).append(package2, rhs.package2).append(pin2, rhs.pin2).append(priority2, rhs.priority2).append(repo3, rhs.repo3).append(key3, rhs.key3).append(package3, rhs.package3).append(pin3, rhs.pin3).append(priority3, rhs.priority3).append(comment, rhs.comment).append(permanent, rhs.permanent).isEquals();
    }


}
