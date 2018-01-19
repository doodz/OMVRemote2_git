package Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by thiba on 31/08/2016.
 */
public class Output implements Serializable {

    @SerializedName("filename")
    @Expose
    private String filename;
    @SerializedName("pos")
    @Expose
    private Integer pos;
    @SerializedName("output")
    @Expose
    private String output;
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
     * The pos
     */
    public Integer getPos() {
        return pos;
    }

    /**
     *
     * @param pos
     * The pos
     */
    public void setPos(Integer pos) {
        this.pos = pos;
    }

    /**
     *
     * @return
     * The output
     */
    public String getOutput() {
        return output;
    }

    /**
     *
     * @param output
     * The output
     */
    public void setOutput(String output) {
        this.output = output;
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
