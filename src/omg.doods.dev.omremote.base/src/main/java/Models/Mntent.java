package Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by thiba on 30/10/2016.
 */

public class Mntent  implements Serializable {

    @SerializedName("devicefile")
    @Expose
    private String devicefile;
    @SerializedName("fsname")
    @Expose
    private String fsname;
    @SerializedName("dir")
    @Expose
    private String dir;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("posixacl")
    @Expose
    private Boolean posixacl;

    /**
     *
     * @return
     * The devicefile
     */
    public String getDevicefile() {
        return devicefile;
    }

    /**
     *
     * @param devicefile
     * The devicefile
     */
    public void setDevicefile(String devicefile) {
        this.devicefile = devicefile;
    }

    /**
     *
     * @return
     * The fsname
     */
    public String getFsname() {
        return fsname;
    }

    /**
     *
     * @param fsname
     * The fsname
     */
    public void setFsname(String fsname) {
        this.fsname = fsname;
    }

    /**
     *
     * @return
     * The dir
     */
    public String getDir() {
        return dir;
    }

    /**
     *
     * @param dir
     * The dir
     */
    public void setDir(String dir) {
        this.dir = dir;
    }

    /**
     *
     * @return
     * The type
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type
     * The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     * @return
     * The posixacl
     */
    public Boolean getPosixacl() {
        return posixacl;
    }

    /**
     *
     * @param posixacl
     * The posixacl
     */
    public void setPosixacl(Boolean posixacl) {
        this.posixacl = posixacl;
    }

}
