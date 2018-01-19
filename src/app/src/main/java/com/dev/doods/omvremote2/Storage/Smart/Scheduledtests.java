package com.dev.doods.omvremote2.Storage.Smart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * Created by Ividata7 on 06/06/2017.
 */

public class Scheduledtests {

    @SerializedName("job")
    @Expose
    private List<Job> job = null;

    public List<Job> getJob() {
        return job;
    }

    public void setJob(List<Job> job) {
        this.job = job;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(job).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Scheduledtests) == false) {
            return false;
        }
        Scheduledtests rhs = ((Scheduledtests) other);
        return new EqualsBuilder().append(job, rhs.job).isEquals();
    }

}