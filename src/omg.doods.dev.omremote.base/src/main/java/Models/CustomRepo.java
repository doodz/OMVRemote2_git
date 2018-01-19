package Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by thiba on 10/11/2016.
 */

public class CustomRepo implements Serializable {

    @SerializedName("uuid")
    @Expose
    private String uuid;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("repo")
    @Expose
    private String repo;
    @SerializedName("key")
    @Expose
    private String key;
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
     * The repo
     */
    public String getRepo() {
        return repo;
    }

    /**
     *
     * @param repo
     * The repo
     */
    public void setRepo(String repo) {
        this.repo = repo;
    }

    /**
     *
     * @return
     * The key
     */
    public String getKey() {
        return key;
    }

    /**
     *
     * @param key
     * The key
     */
    public void setKey(String key) {
        this.key = key;
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
        return this.name;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(uuid).append(name).append(repo).append(key).append(comment).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof CustomRepo) == false) {
            return false;
        }
        CustomRepo rhs = ((CustomRepo) other);
        return new EqualsBuilder().append(uuid, rhs.uuid).append(name, rhs.name).append(repo, rhs.repo).append(key, rhs.key).append(comment, rhs.comment).isEquals();
    }

}
