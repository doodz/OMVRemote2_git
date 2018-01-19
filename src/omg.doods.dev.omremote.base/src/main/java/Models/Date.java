package Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by thiba on 14/10/2016.
 */

public class Date {

    @SerializedName("local")
    @Expose
    private String local;
    @SerializedName("ISO8601")
    @Expose
    private String iSO8601;

    /**
     *
     * @return
     * The local
     */
    public String getLocal() {
        return local;
    }

    /**
     *
     * @param local
     * The local
     */
    public void setLocal(String local) {
        this.local = local;
    }

    /**
     *
     * @return
     * The iSO8601
     */
    public String getISO8601() {
        return iSO8601;
    }

    /**
     *
     * @param iSO8601
     * The ISO8601
     */
    public void setISO8601(String iSO8601) {
        this.iSO8601 = iSO8601;
    }

}