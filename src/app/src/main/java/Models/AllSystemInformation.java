package Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllSystemInformation {
    @SerializedName("ts")
    @Expose
    private long ts;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("hostname")
    @Expose
    private String hostname;
    @SerializedName("version")
    @Expose
    private String version;
    @SerializedName("cpuModelName")
    @Expose
    private String cpuModelName;
    @SerializedName("cpuUsage")
    @Expose
    private long cpuUsage;
    @SerializedName("memTotal")
    @Expose
    private String memTotal;
    @SerializedName("memUsed")
    @Expose
    private String memUsed;
    @SerializedName("kernel")
    @Expose
    private String kernel;
    @SerializedName("uptime")
    @Expose
    private String uptime;
    @SerializedName("loadAverage")
    @Expose
    private String loadAverage;
    @SerializedName("configDirty")
    @Expose
    private boolean configDirty;
    @SerializedName("rebootRequired")
    @Expose
    private boolean rebootRequired;
    @SerializedName("pkgUpdatesAvailable")
    @Expose
    private boolean pkgUpdatesAvailable;


    public long getTs() { return ts; }

    public void setTs(long value) { this.ts = value; }


    public String getTime() { return time; }

    public void setTime(String value) { this.time = value; }


    public String getHostname() { return hostname; }

    public void setHostname(String value) { this.hostname = value; }


    public String getVersion() { return version; }

    public void setVersion(String value) { this.version = value; }


    public String getCPUModelName() { return cpuModelName; }

    public void setCPUModelName(String value) { this.cpuModelName = value; }


    public long getCPUUsage() { return cpuUsage; }

    public void setCPUUsage(long value) { this.cpuUsage = value; }


    public String getMemTotal() { return memTotal; }

    public void setMemTotal(String value) { this.memTotal = value; }


    public String getMemUsed() { return memUsed; }

    public void setMemUsed(String value) { this.memUsed = value; }


    public String getKernel() { return kernel; }

    public void setKernel(String value) { this.kernel = value; }


    public String getUptime() { return uptime; }

    public void setUptime(String value) { this.uptime = value; }


    public String getLoadAverage() { return loadAverage; }

    public void setLoadAverage(String value) { this.loadAverage = value; }


    public boolean getConfigDirty() { return configDirty; }

    public void setConfigDirty(boolean value) { this.configDirty = value; }


    public boolean getRebootRequired() { return rebootRequired; }

    public void setRebootRequired(boolean value) { this.rebootRequired = value; }


    public boolean getPkgUpdatesAvailable() { return pkgUpdatesAvailable; }


    public void setPkgUpdatesAvailable(boolean value) { this.pkgUpdatesAvailable = value; }
}
