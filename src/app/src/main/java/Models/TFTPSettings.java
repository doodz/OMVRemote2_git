package Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by thiba on 26/10/2016.
 */

public class TFTPSettings extends BaseSettings{

    @SerializedName("enable")
    @Expose
    private Boolean enable;
    @SerializedName("port")
    @Expose
    private Integer port;
    @SerializedName("retransmit")
    @Expose
    private Integer retransmit;
    @SerializedName("blocksize")
    @Expose
    private Integer blocksize;
    @SerializedName("allownewfiles")
    @Expose
    private Boolean allownewfiles;
    @SerializedName("sharedfolderref")
    @Expose
    private String sharedfolderref;
    @SerializedName("extraoptions")
    @Expose
    private String extraoptions;

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
     * The port
     */
    public Integer getPort() {
        return port;
    }

    /**
     *
     * @param port
     * The port
     */
    public void setPort(Integer port) {
        this.port = port;
    }

    /**
     *
     * @return
     * The retransmit
     */
    public Integer getRetransmit() {
        return retransmit;
    }

    /**
     *
     * @param retransmit
     * The retransmit
     */
    public void setRetransmit(Integer retransmit) {
        this.retransmit = retransmit;
    }

    /**
     *
     * @return
     * The blocksize
     */
    public Integer getBlocksize() {
        return blocksize;
    }

    /**
     *
     * @param blocksize
     * The blocksize
     */
    public void setBlocksize(Integer blocksize) {
        this.blocksize = blocksize;
    }

    /**
     *
     * @return
     * The allownewfiles
     */
    public Boolean getAllownewfiles() {
        return allownewfiles;
    }

    /**
     *
     * @param allownewfiles
     * The allownewfiles
     */
    public void setAllownewfiles(Boolean allownewfiles) {
        this.allownewfiles = allownewfiles;
    }

    /**
     *
     * @return
     * The sharedfolderref
     */
    public String getSharedfolderref() {
        return sharedfolderref;
    }

    /**
     *
     * @param sharedfolderref
     * The sharedfolderref
     */
    public void setSharedfolderref(String sharedfolderref) {
        this.sharedfolderref = sharedfolderref;
    }

    /**
     *
     * @return
     * The extraoptions
     */
    public String getExtraoptions() {
        return extraoptions;
    }

    /**
     *
     * @param extraoptions
     * The extraoptions
     */
    public void setExtraoptions(String extraoptions) {
        this.extraoptions = extraoptions;
    }

    private TFTPSettings()
    {
        super("TFTP");

    }

    public Map<String, String> getPArams()
    {
        Map<String, String> dictionary = new HashMap<String, String>();

        dictionary.put("enable",enable?"mTrue":"mFalse");
        dictionary.put("allownewfiles",allownewfiles?"mTrue":"mFalse");
        dictionary.put("port",port.toString());
        dictionary.put("retransmit",retransmit.toString());
        dictionary.put("blocksize",blocksize.toString());
        dictionary.put("sharedfolderref",sharedfolderref);
        dictionary.put("extraoptions",extraoptions);

        return dictionary;
    }

    @Override
    public String ToJson()
    {

        StringBuilder sb = new StringBuilder();
        sb.append('{');
        sb.append("\"enable\":"+(enable?"true":"false"));
        sb.append(",\"allownewfiles\":"+(allownewfiles?"true":"false"));
        sb.append(",\"port\":"+port);
        sb.append(",\"sharedfolderref\":\""+sharedfolderref+"\"");
        sb.append(",\"retransmit\":"+retransmit);
        sb.append(",\"blocksize\":"+blocksize);
        sb.append(",\"extraoptions\":\""+extraoptions+"\"");
        sb.append('}');
        return sb.toString();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(enable).append(port).append(retransmit).append(blocksize).append(allownewfiles).append(sharedfolderref).append(extraoptions).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof TFTPSettings) == false) {
            return false;
        }
        TFTPSettings rhs = ((TFTPSettings) other);
        return new EqualsBuilder().append(enable, rhs.enable).append(port, rhs.port).append(retransmit, rhs.retransmit).append(blocksize, rhs.blocksize).append(allownewfiles, rhs.allownewfiles).append(sharedfolderref, rhs.sharedfolderref).append(extraoptions, rhs.extraoptions).isEquals();
    }


}
