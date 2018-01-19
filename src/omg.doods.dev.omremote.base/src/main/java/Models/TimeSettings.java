package Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


/**
 * Created by thiba on 14/10/2016.
 */

public class TimeSettings {

    @SerializedName("date")
    @Expose
    private Date date;
    @SerializedName("timezone")
    @Expose
    private String timezone;
    @SerializedName("ntpenable")
    @Expose
    private Boolean ntpenable;
    @SerializedName("ntptimeservers")
    @Expose
    private String ntptimeservers;

    /**
     *
     * @return
     * The date
     */
    public Date getDate() {
        return date;
    }

    /**
     *
     * @param date
     * The date
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     *
     * @return
     * The timezone
     */
    public String getTimezone() {
        return timezone;
    }

    /**
     *
     * @param timezone
     * The timezone
     */
    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    /**
     *
     * @return
     * The ntpenable
     */
    public Boolean getNtpenable() {
        return ntpenable;
    }

    /**
     *
     * @param ntpenable
     * The ntpenable
     */
    public void setNtpenable(Boolean ntpenable) {
        this.ntpenable = ntpenable;
    }

    /**
     *
     * @return
     * The ntptimeservers
     */
    public String getNtptimeservers() {
        return ntptimeservers;
    }

    /**
     *
     * @param ntptimeservers
     * The ntptimeservers
     */
    public void setNtptimeservers(String ntptimeservers) {
        this.ntptimeservers = ntptimeservers;
    }
}

