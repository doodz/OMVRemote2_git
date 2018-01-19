package Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by thiba on 31/08/2016.
 */
public class Running {

    @SerializedName("filename")
    @Expose
    private String filename;
    @SerializedName("running")
    @Expose
    private Boolean running;

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
     * The running
     */
    public Boolean getRunning() {
        return running;
    }

    /**
     *
     * @param running
     * The running
     */
    public void setRunning(Boolean running) {
        this.running = running;
    }
}
