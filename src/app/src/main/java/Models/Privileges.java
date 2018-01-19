package Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by thiba on 30/11/2016.
 */

public class Privileges implements Serializable {

    @SerializedName("privilege")
    @Expose
    private List<Privilege> privilege = new ArrayList<Privilege>();

    /**
     *
     * @return
     * The privilege
     */
    public List<Privilege> getPrivilege() {
        return privilege;
    }

    /**
     *
     * @param privilege
     * The privilege
     */
    public void setPrivilege(List<Privilege> privilege) {
        this.privilege = privilege;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(privilege).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Privileges) == false) {
            return false;
        }
        Privileges rhs = ((Privileges) other);
        return new EqualsBuilder().append(privilege, rhs.privilege).isEquals();
    }

}