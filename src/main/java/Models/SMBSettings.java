package Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
/**
 * Created by thiba on 26/10/2016.
 */

public class SMBSettings extends BaseSettings {

    @SerializedName("enable")
    @Expose
    private Boolean enable;
    @SerializedName("workgroup")
    @Expose
    private String workgroup;
    @SerializedName("serverstring")
    @Expose
    private String serverstring;
    @SerializedName("loglevel")
    @Expose
    private Integer loglevel;
    @SerializedName("usesendfile")
    @Expose
    private Boolean usesendfile;
    @SerializedName("aio")
    @Expose
    private Boolean aio;
    @SerializedName("nullpasswords")
    @Expose
    private Boolean nullpasswords;
    @SerializedName("localmaster")
    @Expose
    private Boolean localmaster;
    @SerializedName("timeserver")
    @Expose
    private Boolean timeserver;
    @SerializedName("winssupport")
    @Expose
    private Boolean winssupport;
    @SerializedName("winsserver")
    @Expose
    private String winsserver;
    @SerializedName("homesenable")
    @Expose
    private Boolean homesenable;
    @SerializedName("homesbrowseable")
    @Expose
    private Boolean homesbrowseable;
    @SerializedName("extraoptions")
    @Expose
    private String extraoptions;
    @SerializedName("shares")
    @Expose
    private Shares shares;

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
     * The workgroup
     */
    public String getWorkgroup() {
        return workgroup;
    }

    /**
     *
     * @param workgroup
     * The workgroup
     */
    public void setWorkgroup(String workgroup) {
        this.workgroup = workgroup;
    }

    /**
     *
     * @return
     * The serverstring
     */
    public String getServerstring() {
        return serverstring;
    }

    /**
     *
     * @param serverstring
     * The serverstring
     */
    public void setServerstring(String serverstring) {
        this.serverstring = serverstring;
    }

    /**
     *
     * @return
     * The loglevel
     */
    public Integer getLoglevel() {
        return loglevel;
    }

    /**
     *
     * @param loglevel
     * The loglevel
     */
    public void setLoglevel(Integer loglevel) {
        this.loglevel = loglevel;
    }

    /**
     *
     * @return
     * The usesendfile
     */
    public Boolean getUsesendfile() {
        return usesendfile;
    }

    /**
     *
     * @param usesendfile
     * The usesendfile
     */
    public void setUsesendfile(Boolean usesendfile) {
        this.usesendfile = usesendfile;
    }

    /**
     *
     * @return
     * The aio
     */
    public Boolean getAio() {
        return aio;
    }

    /**
     *
     * @param aio
     * The aio
     */
    public void setAio(Boolean aio) {
        this.aio = aio;
    }

    /**
     *
     * @return
     * The nullpasswords
     */
    public Boolean getNullpasswords() {
        return nullpasswords;
    }

    /**
     *
     * @param nullpasswords
     * The nullpasswords
     */
    public void setNullpasswords(Boolean nullpasswords) {
        this.nullpasswords = nullpasswords;
    }

    /**
     *
     * @return
     * The localmaster
     */
    public Boolean getLocalmaster() {
        return localmaster;
    }

    /**
     *
     * @param localmaster
     * The localmaster
     */
    public void setLocalmaster(Boolean localmaster) {
        this.localmaster = localmaster;
    }

    /**
     *
     * @return
     * The timeserver
     */
    public Boolean getTimeserver() {
        return timeserver;
    }

    /**
     *
     * @param timeserver
     * The timeserver
     */
    public void setTimeserver(Boolean timeserver) {
        this.timeserver = timeserver;
    }

    /**
     *
     * @return
     * The winssupport
     */
    public Boolean getWinssupport() {
        return winssupport;
    }

    /**
     *
     * @param winssupport
     * The winssupport
     */
    public void setWinssupport(Boolean winssupport) {
        this.winssupport = winssupport;
    }

    /**
     *
     * @return
     * The winsserver
     */
    public String getWinsserver() {
        return winsserver;
    }

    /**
     *
     * @param winsserver
     * The winsserver
     */
    public void setWinsserver(String winsserver) {
        this.winsserver = winsserver;
    }

    /**
     *
     * @return
     * The homesenable
     */
    public Boolean getHomesenable() {
        return homesenable;
    }

    /**
     *
     * @param homesenable
     * The homesenable
     */
    public void setHomesenable(Boolean homesenable) {
        this.homesenable = homesenable;
    }

    /**
     *
     * @return
     * The homesbrowseable
     */
    public Boolean getHomesbrowseable() {
        return homesbrowseable;
    }

    /**
     *
     * @param homesbrowseable
     * The homesbrowseable
     */
    public void setHomesbrowseable(Boolean homesbrowseable) {
        this.homesbrowseable = homesbrowseable;
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

    /**
     *
     * @return
     * The shares
     */
    public Shares getShares() {
        return shares;
    }

    /**
     *
     * @param shares
     * The shares
     */
    public void setShares(Shares shares) {
        this.shares = shares;
    }

    private SMBSettings()
    {
        super("SMB");

    }

    @Override
    public String ToJson()
    {

        StringBuilder sb = new StringBuilder();
        sb.append('{');
        sb.append("\"enable\":"+(enable?"true":"false"));
        sb.append(",\"workgroup\":\""+workgroup+"\"");
        sb.append(",\"serverstring\":\""+serverstring+"\"");
        sb.append(",\"localmaster\":"+(localmaster?"true":"false"));
        sb.append(",\"timeserver\":"+(timeserver?"true":"false"));
        sb.append(",\"homesenable\":"+(homesenable?"true":"false"));
        sb.append(",\"homesbrowseable\":"+(homesbrowseable?"true":"false"));
        sb.append(",\"winssupport\":"+(winssupport?"true":"false"));
        sb.append(",\"winsserver\":\""+winsserver+"\"");
        sb.append(",\"loglevel\":"+loglevel);
        sb.append(",\"nullpasswords\":"+(nullpasswords?"true":"false"));
        sb.append(",\"usesendfile\":"+(usesendfile?"true":"false"));
        sb.append(",\"aio\":"+(aio?"true":"false"));
        sb.append(",\"extraoptions\":\""+extraoptions+"\"");
        sb.append('}');
        return sb.toString();
    }

    public Map<String, String> getPArams()
    {
        Map<String, String> dictionary = new HashMap<String, String>();

        dictionary.put("enable",enable?"mTrue":"mFalse");
        dictionary.put("workgroup",workgroup);
        dictionary.put("serverstring",serverstring);
        dictionary.put("loglevel",loglevel.toString());
        dictionary.put("usesendfile",usesendfile?"mTrue":"mFalse");
        dictionary.put("aio",aio?"mTrue":"mFalse");
        dictionary.put("nullpasswords",nullpasswords?"mTrue":"mFalse");
        dictionary.put("localmaster",localmaster?"mTrue":"mFalse");
        dictionary.put("timeserver",timeserver?"mTrue":"mFalse");

        dictionary.put("winssupport",winssupport?"mTrue":"mFalse");
        dictionary.put("winsserver",winsserver);
        dictionary.put("homesenable",homesenable?"mTrue":"mFalse");
        dictionary.put("homesbrowseable",homesbrowseable?"mTrue":"mFalse");
        dictionary.put("extraoptions",extraoptions);
        return dictionary;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(enable).append(workgroup).append(serverstring).append(loglevel).append(usesendfile).append(aio).append(nullpasswords).append(localmaster).append(timeserver).append(winssupport).append(winsserver).append(homesenable).append(homesbrowseable).append(extraoptions).append(shares).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof SMBSettings) == false) {
            return false;
        }
        SMBSettings rhs = ((SMBSettings) other);
        return new EqualsBuilder().append(enable, rhs.enable).append(workgroup, rhs.workgroup).append(serverstring, rhs.serverstring).append(loglevel, rhs.loglevel).append(usesendfile, rhs.usesendfile).append(aio, rhs.aio).append(nullpasswords, rhs.nullpasswords).append(localmaster, rhs.localmaster).append(timeserver, rhs.timeserver).append(winssupport, rhs.winssupport).append(winsserver, rhs.winsserver).append(homesenable, rhs.homesenable).append(homesbrowseable, rhs.homesbrowseable).append(extraoptions, rhs.extraoptions).append(shares, rhs.shares).isEquals();
    }

}


