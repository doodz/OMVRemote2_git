package Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by thiba on 13/12/2016.
 */

public class LogRow {

    @SerializedName("rownum")
    @Expose
    private Integer rownum;
    @SerializedName("ts")
    @Expose
    private Integer ts;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("hostname")
    @Expose
    private String hostname;
    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("event")
    @Expose
    private String event;
    @SerializedName("log")
    @Expose
    private String log;
    /**
     *
     * @return
     * The rownum
     */
    public Integer getRownum() {
        return rownum;
    }

    /**
     *
     * @param rownum
     * The rownum
     */
    public void setRownum(Integer rownum) {
        this.rownum = rownum;
    }

    /**
     *
     * @return
     * The ts
     */
    public Integer getTs() {
        return ts;
    }

    /**
     *
     * @param ts
     * The ts
     */
    public void setTs(Integer ts) {
        this.ts = ts;
    }

    /**
     *
     * @return
     * The date
     */
    public String getDate() {
        return date;
    }

    /**
     *
     * @param date
     * The date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     *
     * @return
     * The hostname
     */
    public String getHostname() {
        return hostname;
    }

    /**
     *
     * @param hostname
     * The hostname
     */
    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    /**
     *
     * @return
     * The message
     */
    public String getMessage() {
        return message;
    }
    /**
     *
     * @param message
     * The message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     *
     * @return
     * The event
     */
    public String getEvent(){
        return event;
    }

    /**
     *
     * @param event
     * The message
     */
    public void setEvent(String event) {
        this.event = event;
    }
    /**
     *
     * @return
     * The Log
     */
    public String getLog(){
        return log;
    }

    /**
     *
     * @param log
     * The message
     */
    public void setLog(String log) {
        this.log = log;
    }



    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(rownum).append(ts).append(date).append(hostname).append(message).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof LogRow) == false) {
            return false;
        }
        LogRow rhs = ((LogRow) other);
        return new EqualsBuilder().append(rownum, rhs.rownum).append(ts, rhs.ts).append(date, rhs.date).append(hostname, rhs.hostname).append(message, rhs.message).append(event, rhs.event).append(log, rhs.log).isEquals();
    }
}
