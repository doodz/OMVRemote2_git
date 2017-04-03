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

public class SNMPSettings extends BaseSettings{

    @SerializedName("enable")
    @Expose
    private Boolean enable;
    @SerializedName("community")
    @Expose
    private String community;
    @SerializedName("syslocation")
    @Expose
    private String syslocation;
    @SerializedName("syscontact")
    @Expose
    private String syscontact;
    @SerializedName("version")
    @Expose
    private String version;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("securitylevel")
    @Expose
    private String securitylevel;
    @SerializedName("authtype")
    @Expose
    private String authtype;
    @SerializedName("authpassphrase")
    @Expose
    private String authpassphrase;
    @SerializedName("privtype")
    @Expose
    private String privtype;
    @SerializedName("privpassphrase")
    @Expose
    private String privpassphrase;
    @SerializedName("extraoptions")
    @Expose
    private String extraoptions;
    @SerializedName("trapenable")
    @Expose
    private Boolean trapenable;
    @SerializedName("trapcommunity")
    @Expose
    private String trapcommunity;
    @SerializedName("traphost")
    @Expose
    private String traphost;
    @SerializedName("trapport")
    @Expose
    private Integer trapport;

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
     * The community
     */
    public String getCommunity() {
        return community;
    }

    /**
     *
     * @param community
     * The community
     */
    public void setCommunity(String community) {
        this.community = community;
    }

    /**
     *
     * @return
     * The syslocation
     */
    public String getSyslocation() {
        return syslocation;
    }

    /**
     *
     * @param syslocation
     * The syslocation
     */
    public void setSyslocation(String syslocation) {
        this.syslocation = syslocation;
    }

    /**
     *
     * @return
     * The syscontact
     */
    public String getSyscontact() {
        return syscontact;
    }

    /**
     *
     * @param syscontact
     * The syscontact
     */
    public void setSyscontact(String syscontact) {
        this.syscontact = syscontact;
    }

    /**
     *
     * @return
     * The version
     */
    public String getVersion() {
        return version;
    }

    /**
     *
     * @param version
     * The version
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     *
     * @return
     * The username
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     * @param username
     * The username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     *
     * @return
     * The securitylevel
     */
    public String getSecuritylevel() {
        return securitylevel;
    }

    /**
     *
     * @param securitylevel
     * The securitylevel
     */
    public void setSecuritylevel(String securitylevel) {
        this.securitylevel = securitylevel;
    }

    /**
     *
     * @return
     * The authtype
     */
    public String getAuthtype() {
        return authtype;
    }

    /**
     *
     * @param authtype
     * The authtype
     */
    public void setAuthtype(String authtype) {
        this.authtype = authtype;
    }

    /**
     *
     * @return
     * The authpassphrase
     */
    public String getAuthpassphrase() {
        return authpassphrase;
    }

    /**
     *
     * @param authpassphrase
     * The authpassphrase
     */
    public void setAuthpassphrase(String authpassphrase) {
        this.authpassphrase = authpassphrase;
    }

    /**
     *
     * @return
     * The privtype
     */
    public String getPrivtype() {
        return privtype;
    }

    /**
     *
     * @param privtype
     * The privtype
     */
    public void setPrivtype(String privtype) {
        this.privtype = privtype;
    }

    /**
     *
     * @return
     * The privpassphrase
     */
    public String getPrivpassphrase() {
        return privpassphrase;
    }

    /**
     *
     * @param privpassphrase
     * The privpassphrase
     */
    public void setPrivpassphrase(String privpassphrase) {
        this.privpassphrase = privpassphrase;
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
     * The trapenable
     */
    public Boolean getTrapenable() {
        return trapenable;
    }

    /**
     *
     * @param trapenable
     * The trapenable
     */
    public void setTrapenable(Boolean trapenable) {
        this.trapenable = trapenable;
    }

    /**
     *
     * @return
     * The trapcommunity
     */
    public String getTrapcommunity() {
        return trapcommunity;
    }

    /**
     *
     * @param trapcommunity
     * The trapcommunity
     */
    public void setTrapcommunity(String trapcommunity) {
        this.trapcommunity = trapcommunity;
    }

    /**
     *
     * @return
     * The traphost
     */
    public String getTraphost() {
        return traphost;
    }

    /**
     *
     * @param traphost
     * The traphost
     */
    public void setTraphost(String traphost) {
        this.traphost = traphost;
    }

    /**
     *
     * @return
     * The trapport
     */
    public Integer getTrapport() {
        return trapport;
    }

    /**
     *
     * @param trapport
     * The trapport
     */
    public void setTrapport(Integer trapport) {
        this.trapport = trapport;
    }


    private SNMPSettings()
    {
        super("SNMP");

    }

    public Map<String, String> getPArams()
    {
        Map<String, String> dictionary = new HashMap<String, String>();

        dictionary.put("enable",enable?"mTrue":"mFalse");
        dictionary.put("community",community);
        dictionary.put("syslocation",syslocation);
        dictionary.put("syscontact",syscontact);
        dictionary.put("version",version);
        dictionary.put("username",username);
        dictionary.put("securitylevel",securitylevel);
        dictionary.put("authtype",authtype);

        dictionary.put("authpassphrase",authpassphrase);
        dictionary.put("privtype",privtype);
        dictionary.put("privpassphrase",privpassphrase);
        dictionary.put("extraoptions",extraoptions);
        dictionary.put("trapenable",trapenable?"mTrue":"mFalse");
        dictionary.put("trapcommunity",trapcommunity);
        dictionary.put("traphost",traphost);
        dictionary.put("trapport",trapport.toString());
        return dictionary;
    }


    @Override
    public String ToJson()
    {

        StringBuilder sb = new StringBuilder();
        sb.append('{');
        sb.append("\"enable\":"+(enable?"true":"false"));
        sb.append(",\"community\":\""+community+"\"");
        sb.append(",\"syslocation\":\""+syslocation+"\"");
        sb.append(",\"syscontact\":\""+syscontact+"\"");
        sb.append(",\"version\":\""+version+"\"");
        sb.append(",\"username\":\""+username+"\"");
        sb.append(",\"securitylevel\":\""+securitylevel+"\"");
        sb.append(",\"authtype\":\""+authtype+"\"");
        sb.append(",\"authpassphrase\":\""+authpassphrase+"\"");
        sb.append(",\"privtype\":\""+privtype+"\"");
        sb.append(",\"privpassphrase\":\""+privpassphrase+"\"");
        sb.append(",\"extraoptions\":\""+extraoptions+"\"");
        sb.append(",\"trapenable\":"+(trapenable?"true":"false"));
        sb.append(",\"trapcommunity\":\""+trapcommunity+"\"");
        sb.append(",\"traphost\":\""+traphost+"\"");
        sb.append(",\"trapport\":"+trapport);



        sb.append('}');
        return sb.toString();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(enable).append(community).append(syslocation).append(syscontact).append(version).append(username).append(securitylevel).append(authtype).append(authpassphrase).append(privtype).append(privpassphrase).append(extraoptions).append(trapenable).append(trapcommunity).append(traphost).append(trapport).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof SNMPSettings) == false) {
            return false;
        }
        SNMPSettings rhs = ((SNMPSettings) other);
        return new EqualsBuilder().append(enable, rhs.enable).append(community, rhs.community).append(syslocation, rhs.syslocation).append(syscontact, rhs.syscontact).append(version, rhs.version).append(username, rhs.username).append(securitylevel, rhs.securitylevel).append(authtype, rhs.authtype).append(authpassphrase, rhs.authpassphrase).append(privtype, rhs.privtype).append(privpassphrase, rhs.privpassphrase).append(extraoptions, rhs.extraoptions).append(trapenable, rhs.trapenable).append(trapcommunity, rhs.trapcommunity).append(traphost, rhs.traphost).append(trapport, rhs.trapport).isEquals();
    }

    public static List<String> Versions = new ArrayList<String>(){{add("SNMP version  1/2c");add("SNMP version 3");}};
    public static List<String> VersionsAbrev = new ArrayList<String>(){{add("2c");add("3");}};

    public static List<String> SecurityLevels = new ArrayList<String>(){{add("No authentification and no privacy");add("Authentification and no privacy");add("Authentification and privacy");}};
    public static List<String> SecurityLevelsAbrev = new ArrayList<String>(){{add("noauth");add("auth");add("priv");}};
    public static List<String> AuthType = new ArrayList<String>(){{add("MD5");add("SHA");}};
    public static List<String> PrivType = new ArrayList<String>(){{add("AES");add("DES");}};

}
