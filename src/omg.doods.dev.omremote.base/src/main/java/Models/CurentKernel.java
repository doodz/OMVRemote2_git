package Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by thiba on 09/11/2016.
 */

public class CurentKernel {

    @SerializedName("enable")
    @Expose
    private String enable;
    @SerializedName("testing")
    @Expose
    private String testing;
    @SerializedName("vbox")
    @Expose
    private String vbox;
    @SerializedName("plex")
    @Expose
    private String plex;
    @SerializedName("greyhole")
    @Expose
    private String greyhole;
    @SerializedName("vdr")
    @Expose
    private String vdr;
    @SerializedName("miller")
    @Expose
    private String miller;
    @SerializedName("millertesting")
    @Expose
    private String millertesting;
    @SerializedName("btsync")
    @Expose
    private String btsync;
    @SerializedName("vpn")
    @Expose
    private String vpn;
    @SerializedName("zfs")
    @Expose
    private String zfs;
    @SerializedName("zfstesting")
    @Expose
    private String zfstesting;
    @SerializedName("gluster")
    @Expose
    private String gluster;
    @SerializedName("mono")
    @Expose
    private String mono;
    @SerializedName("monotesting")
    @Expose
    private String monotesting;
    @SerializedName("docker")
    @Expose
    private String docker;
    @SerializedName("dockertesting")
    @Expose
    private String dockertesting;
    @SerializedName("hwraid")
    @Expose
    private String hwraid;
    @SerializedName("beta")
    @Expose
    private String beta;
    @SerializedName("developer")
    @Expose
    private String developer;
    @SerializedName("kernels")
    @Expose
    private Integer kernels;
    @SerializedName("versionname")
    @Expose
    private String versionname;
    @SerializedName("version")
    @Expose
    private String version;
    @SerializedName("kernel")
    @Expose
    private String kernel;
    @SerializedName("arch")
    @Expose
    private String arch;

    /**
     *
     * @return
     * The enable
     */
    public String getEnable() {
        return enable;
    }

    /**
     *
     * @param enable
     * The enable
     */
    public void setEnable(String enable) {
        this.enable = enable;
    }

    /**
     *
     * @return
     * The testing
     */
    public String getTesting() {
        return testing;
    }

    /**
     *
     * @param testing
     * The testing
     */
    public void setTesting(String testing) {
        this.testing = testing;
    }

    /**
     *
     * @return
     * The vbox
     */
    public String getVbox() {
        return vbox;
    }

    /**
     *
     * @param vbox
     * The vbox
     */
    public void setVbox(String vbox) {
        this.vbox = vbox;
    }

    /**
     *
     * @return
     * The plex
     */
    public String getPlex() {
        return plex;
    }

    /**
     *
     * @param plex
     * The plex
     */
    public void setPlex(String plex) {
        this.plex = plex;
    }

    /**
     *
     * @return
     * The greyhole
     */
    public String getGreyhole() {
        return greyhole;
    }

    /**
     *
     * @param greyhole
     * The greyhole
     */
    public void setGreyhole(String greyhole) {
        this.greyhole = greyhole;
    }

    /**
     *
     * @return
     * The vdr
     */
    public String getVdr() {
        return vdr;
    }

    /**
     *
     * @param vdr
     * The vdr
     */
    public void setVdr(String vdr) {
        this.vdr = vdr;
    }

    /**
     *
     * @return
     * The miller
     */
    public String getMiller() {
        return miller;
    }

    /**
     *
     * @param miller
     * The miller
     */
    public void setMiller(String miller) {
        this.miller = miller;
    }

    /**
     *
     * @return
     * The millertesting
     */
    public String getMillertesting() {
        return millertesting;
    }

    /**
     *
     * @param millertesting
     * The millertesting
     */
    public void setMillertesting(String millertesting) {
        this.millertesting = millertesting;
    }

    /**
     *
     * @return
     * The btsync
     */
    public String getBtsync() {
        return btsync;
    }

    /**
     *
     * @param btsync
     * The btsync
     */
    public void setBtsync(String btsync) {
        this.btsync = btsync;
    }

    /**
     *
     * @return
     * The vpn
     */
    public String getVpn() {
        return vpn;
    }

    /**
     *
     * @param vpn
     * The vpn
     */
    public void setVpn(String vpn) {
        this.vpn = vpn;
    }

    /**
     *
     * @return
     * The zfs
     */
    public String getZfs() {
        return zfs;
    }

    /**
     *
     * @param zfs
     * The zfs
     */
    public void setZfs(String zfs) {
        this.zfs = zfs;
    }

    /**
     *
     * @return
     * The zfstesting
     */
    public String getZfstesting() {
        return zfstesting;
    }

    /**
     *
     * @param zfstesting
     * The zfstesting
     */
    public void setZfstesting(String zfstesting) {
        this.zfstesting = zfstesting;
    }

    /**
     *
     * @return
     * The gluster
     */
    public String getGluster() {
        return gluster;
    }

    /**
     *
     * @param gluster
     * The gluster
     */
    public void setGluster(String gluster) {
        this.gluster = gluster;
    }

    /**
     *
     * @return
     * The mono
     */
    public String getMono() {
        return mono;
    }

    /**
     *
     * @param mono
     * The mono
     */
    public void setMono(String mono) {
        this.mono = mono;
    }

    /**
     *
     * @return
     * The monotesting
     */
    public String getMonotesting() {
        return monotesting;
    }

    /**
     *
     * @param monotesting
     * The monotesting
     */
    public void setMonotesting(String monotesting) {
        this.monotesting = monotesting;
    }

    /**
     *
     * @return
     * The docker
     */
    public String getDocker() {
        return docker;
    }

    /**
     *
     * @param docker
     * The docker
     */
    public void setDocker(String docker) {
        this.docker = docker;
    }

    /**
     *
     * @return
     * The dockertesting
     */
    public String getDockertesting() {
        return dockertesting;
    }

    /**
     *
     * @param dockertesting
     * The dockertesting
     */
    public void setDockertesting(String dockertesting) {
        this.dockertesting = dockertesting;
    }

    /**
     *
     * @return
     * The hwraid
     */
    public String getHwraid() {
        return hwraid;
    }

    /**
     *
     * @param hwraid
     * The hwraid
     */
    public void setHwraid(String hwraid) {
        this.hwraid = hwraid;
    }

    /**
     *
     * @return
     * The beta
     */
    public String getBeta() {
        return beta;
    }

    /**
     *
     * @param beta
     * The beta
     */
    public void setBeta(String beta) {
        this.beta = beta;
    }

    /**
     *
     * @return
     * The developer
     */
    public String getDeveloper() {
        return developer;
    }

    /**
     *
     * @param developer
     * The developer
     */
    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    /**
     *
     * @return
     * The kernels
     */
    public Integer getKernels() {
        return kernels;
    }

    /**
     *
     * @param kernels
     * The kernels
     */
    public void setKernels(Integer kernels) {
        this.kernels = kernels;
    }

    /**
     *
     * @return
     * The versionname
     */
    public String getVersionname() {
        return versionname;
    }

    /**
     *
     * @param versionname
     * The versionname
     */
    public void setVersionname(String versionname) {
        this.versionname = versionname;
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
     * The kernel
     */
    public String getKernel() {
        return kernel;
    }

    /**
     *
     * @param kernel
     * The kernel
     */
    public void setKernel(String kernel) {
        this.kernel = kernel;
    }

    /**
     *
     * @return
     * The arch
     */
    public String getArch() {
        return arch;
    }

    /**
     *
     * @param arch
     * The arch
     */
    public void setArch(String arch) {
        this.arch = arch;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(enable).append(testing).append(vbox).append(plex).append(greyhole).append(vdr).append(miller).append(millertesting)
                .append(btsync).append(vpn).append(zfs).append(zfstesting).append(gluster).append(mono).append(monotesting).append(docker).append(dockertesting)
                .append(hwraid).append(beta).append(developer).append(kernels).append(versionname).append(version).append(kernel).append(arch).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof CurentKernel) == false) {
            return false;
        }
        CurentKernel rhs = ((CurentKernel) other);
        return new EqualsBuilder().append(enable, rhs.enable).append(testing, rhs.testing).append(vbox, rhs.vbox).append(plex, rhs.plex).append(greyhole, rhs.greyhole)
                .append(vdr, rhs.vdr).append(miller, rhs.miller).append(millertesting, rhs.millertesting).append(btsync, rhs.btsync).append(vpn, rhs.vpn).append(zfs, rhs.zfs)
                .append(zfstesting, rhs.zfstesting).append(gluster, rhs.gluster).append(mono, rhs.mono).append(monotesting, rhs.monotesting).append(docker, rhs.docker).append(dockertesting, rhs.dockertesting)
                .append(hwraid, rhs.hwraid).append(beta, rhs.beta).append(developer, rhs.developer).append(kernels, rhs.kernels).append(versionname, rhs.versionname).append(version, rhs.version)
                .append(kernel, rhs.kernel).append(arch, rhs.arch).isEquals();
    }

}
