package Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by thiba on 08/09/2016.
 */
public class Plugins {

    @SerializedName("abstract")
    @Expose
    private String _abstract;
    @SerializedName("architecture")
    @Expose
    private String architecture;
    @SerializedName("breaks")
    @Expose
    private String breaks;
    @SerializedName("conflicts")
    @Expose
    private String conflicts;
    @SerializedName("depends")
    @Expose
    private String depends;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("descriptionmd5")
    @Expose
    private String descriptionmd5;
    @SerializedName("extendeddescription")
    @Expose
    private String extendeddescription;
    @SerializedName("filename")
    @Expose
    private String filename;
    @SerializedName("homepage")
    @Expose
    private String homepage;
    @SerializedName("installed")
    @Expose
    private Boolean installed;
    @SerializedName("installedsize")
    @Expose
    private Integer installedsize;
    @SerializedName("maintainer")
    @Expose
    private String maintainer;
    @SerializedName("md5sum")
    @Expose
    private String md5sum;
    @SerializedName("multiarch")
    @Expose
    private String multiarch;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("package")
    @Expose
    private String _package;
    @SerializedName("pluginsection")
    @Expose
    private String pluginsection;
    @SerializedName("predepends")
    @Expose
    private String predepends;
    @SerializedName("priority")
    @Expose
    private String priority;
    @SerializedName("repository")
    @Expose
    private String repository;
    @SerializedName("section")
    @Expose
    private String section;
    @SerializedName("sha1")
    @Expose
    private String sha1;
    @SerializedName("sha256")
    @Expose
    private String sha256;
    @SerializedName("size")
    @Expose
    private Integer size;
    @SerializedName("suggests")
    @Expose
    private String suggests;
    @SerializedName("summary")
    @Expose
    private String summary;
    @SerializedName("version")
    @Expose
    private String version;

    /**
     *
     * @return
     * The _abstract
     */
    public String getAbstract() {
        return _abstract;
    }

    /**
     *
     * @param _abstract
     * The abstract
     */
    public void setAbstract(String _abstract) {
        this._abstract = _abstract;
    }

    /**
     *
     * @return
     * The architecture
     */
    public String getArchitecture() {
        return architecture;
    }

    /**
     *
     * @param architecture
     * The architecture
     */
    public void setArchitecture(String architecture) {
        this.architecture = architecture;
    }

    /**
     *
     * @return
     * The breaks
     */
    public String getBreaks() {
        return breaks;
    }

    /**
     *
     * @param breaks
     * The breaks
     */
    public void setBreaks(String breaks) {
        this.breaks = breaks;
    }

    /**
     *
     * @return
     * The conflicts
     */
    public String getConflicts() {
        return conflicts;
    }

    /**
     *
     * @param conflicts
     * The conflicts
     */
    public void setConflicts(String conflicts) {
        this.conflicts = conflicts;
    }

    /**
     *
     * @return
     * The depends
     */
    public String getDepends() {
        return depends;
    }

    /**
     *
     * @param depends
     * The depends
     */
    public void setDepends(String depends) {
        this.depends = depends;
    }

    /**
     *
     * @return
     * The description
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     * The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return
     * The descriptionmd5
     */
    public String getDescriptionmd5() {
        return descriptionmd5;
    }

    /**
     *
     * @param descriptionmd5
     * The descriptionmd5
     */
    public void setDescriptionmd5(String descriptionmd5) {
        this.descriptionmd5 = descriptionmd5;
    }

    /**
     *
     * @return
     * The extendeddescription
     */
    public String getExtendeddescription() {
        return extendeddescription;
    }

    /**
     *
     * @param extendeddescription
     * The extendeddescription
     */
    public void setExtendeddescription(String extendeddescription) {
        this.extendeddescription = extendeddescription;
    }

    /**
     *
     * @return
     * The filename
     */
    public String getFilename() {
        return filename;
    }

    /**
     *
     * @param filename
     * The filename
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }

    /**
     *
     * @return
     * The homepage
     */
    public String getHomepage() {
        return homepage;
    }

    /**
     *
     * @param homepage
     * The homepage
     */
    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    /**
     *
     * @return
     * The installed
     */
    public Boolean getInstalled() {
        return installed;
    }

    /**
     *
     * @param installed
     * The installed
     */
    public void setInstalled(Boolean installed) {
        this.installed = installed;
    }

    /**
     *
     * @return
     * The installedsize
     */
    public Integer getInstalledsize() {
        return installedsize;
    }

    /**
     *
     * @param installedsize
     * The installedsize
     */
    public void setInstalledsize(Integer installedsize) {
        this.installedsize = installedsize;
    }

    /**
     *
     * @return
     * The maintainer
     */
    public String getMaintainer() {
        return maintainer;
    }

    /**
     *
     * @param maintainer
     * The maintainer
     */
    public void setMaintainer(String maintainer) {
        this.maintainer = maintainer;
    }

    /**
     *
     * @return
     * The md5sum
     */
    public String getMd5sum() {
        return md5sum;
    }

    /**
     *
     * @param md5sum
     * The md5sum
     */
    public void setMd5sum(String md5sum) {
        this.md5sum = md5sum;
    }

    /**
     *
     * @return
     * The multiarch
     */
    public String getMultiarch() {
        return multiarch;
    }

    /**
     *
     * @param multiarch
     * The multiarch
     */
    public void setMultiarch(String multiarch) {
        this.multiarch = multiarch;
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
     * The _package
     */
    public String getPackage() {
        return _package;
    }

    /**
     *
     * @param _package
     * The package
     */
    public void setPackage(String _package) {
        this._package = _package;
    }

    /**
     *
     * @return
     * The pluginsection
     */
    public String getPluginsection() {
        return pluginsection;
    }

    /**
     *
     * @param pluginsection
     * The pluginsection
     */
    public void setPluginsection(String pluginsection) {
        this.pluginsection = pluginsection;
    }

    /**
     *
     * @return
     * The predepends
     */
    public String getPredepends() {
        return predepends;
    }

    /**
     *
     * @param predepends
     * The predepends
     */
    public void setPredepends(String predepends) {
        this.predepends = predepends;
    }

    /**
     *
     * @return
     * The priority
     */
    public String getPriority() {
        return priority;
    }

    /**
     *
     * @param priority
     * The priority
     */
    public void setPriority(String priority) {
        this.priority = priority;
    }

    /**
     *
     * @return
     * The repository
     */
    public String getRepository() {
        return repository;
    }

    /**
     *
     * @param repository
     * The repository
     */
    public void setRepository(String repository) {
        this.repository = repository;
    }

    /**
     *
     * @return
     * The section
     */
    public String getSection() {
        return section;
    }

    /**
     *
     * @param section
     * The section
     */
    public void setSection(String section) {
        this.section = section;
    }

    /**
     *
     * @return
     * The sha1
     */
    public String getSha1() {
        return sha1;
    }

    /**
     *
     * @param sha1
     * The sha1
     */
    public void setSha1(String sha1) {
        this.sha1 = sha1;
    }

    /**
     *
     * @return
     * The sha256
     */
    public String getSha256() {
        return sha256;
    }

    /**
     *
     * @param sha256
     * The sha256
     */
    public void setSha256(String sha256) {
        this.sha256 = sha256;
    }

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
     * The suggests
     */
    public String getSuggests() {
        return suggests;
    }

    /**
     *
     * @param suggests
     * The suggests
     */
    public void setSuggests(String suggests) {
        this.suggests = suggests;
    }

    /**
     *
     * @return
     * The summary
     */
    public String getSummary() {
        return summary;
    }

    /**
     *
     * @param summary
     * The summary
     */
    public void setSummary(String summary) {
        this.summary = summary;
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


    public Plugins(){}

    public Plugins(int type,String name)
    {
        this.type = type;
        this.name = name;

    }

    private Boolean _isSelected = false;
    public static final int ITEM = 0;
    public static final int SECTION = 1;
    public int type = ITEM;

    public boolean isSelected() {
        return _isSelected;
    }

    public void setSelected(boolean isSelected) {
        _isSelected = isSelected;
    }
}
