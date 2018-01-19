package Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
/**
 * Created by thiba on 27/10/2016.
 */

public class NFSSettings extends BaseSettings
{

    @SerializedName("enable")
    @Expose
    private Boolean enable;
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

    @SerializedName("numproc")
    @Expose
    private Integer numproc;

    /**
     *
     * @return
     * The numproc
     */
    public Integer getNumproc() {
        return numproc;
    }

    /**
     *
     * @param numproc
     * The numproc
     */
    public void setNumproc(Integer numproc) {
        this.numproc = numproc;
    }

    private NFSSettings()
    {
        super("NFS");

    }

     public Map<String, String> getPArams()
     {
         Map<String, String> dictionary = new HashMap<String, String>();

         dictionary.put("enable",enable?"mTrue":"mFalse");
         dictionary.put("numproc",numproc.toString());

         return dictionary;
     }

    @Override
    public String ToJson()
    {

        StringBuilder sb = new StringBuilder();
        sb.append('{');
        sb.append("\"enable\":"+(enable?"true":"false"));
        sb.append(",\"numproc\":"+numproc);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(enable).append(numproc).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof NFSSettings) == false) {
            return false;
        }
        NFSSettings rhs = ((NFSSettings) other);
        return new EqualsBuilder().append(enable, rhs.enable).append(numproc, rhs.numproc).isEquals();
    }
}
