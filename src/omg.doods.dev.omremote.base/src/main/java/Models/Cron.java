package Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by thiba on 19/12/2016.
 */

public class Cron implements Serializable {

    @SerializedName("uuid")
    @Expose
    private String uuid;
    @SerializedName("enable")
    @Expose
    private Boolean enable= false;
    @SerializedName("execution")
    @Expose
    private String execution = "exactly";
    @SerializedName("sendemail")
    @Expose
    private Boolean sendemail= false;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("minute")
    @Expose
    private String minute= "42";
    @SerializedName("everynminute")
    @Expose
    private Boolean everynminute= false;
    @SerializedName("hour")
    @Expose
    private String hour = "12";
    @SerializedName("everynhour")
    @Expose
    private Boolean everynhour = false;
    @SerializedName("dayofmonth")
    @Expose
    private String dayofmonth = "*";
    @SerializedName("everyndayofmonth")
    @Expose
    private Boolean everyndayofmonth= false;
    @SerializedName("month")
    @Expose
    private String month = "*";
    @SerializedName("dayofweek")
    @Expose
    private String dayofweek = "*";
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("command")
    @Expose
    private String command = "";
    @SerializedName("comment")
    @Expose
    private String comment = "";

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public String getExecution() {
        return execution;
    }

    public void setExecution(String execution) {
        this.execution = execution;
    }

    public Boolean getSendemail() {
        return sendemail;
    }

    public void setSendemail(Boolean sendemail) {
        this.sendemail = sendemail;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMinute() {
        return minute;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }

    public Boolean getEverynminute() {
        return everynminute;
    }

    public void setEverynminute(Boolean everynminute) {
        this.everynminute = everynminute;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public Boolean getEverynhour() {
        return everynhour;
    }

    public void setEverynhour(Boolean everynhour) {
        this.everynhour = everynhour;
    }

    public String getDayofmonth() {
        return dayofmonth;
    }

    public void setDayofmonth(String dayofmonth) {
        this.dayofmonth = dayofmonth;
    }

    public Boolean getEveryndayofmonth() {
        return everyndayofmonth;
    }

    public void setEveryndayofmonth(Boolean everyndayofmonth) {
        this.everyndayofmonth = everyndayofmonth;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDayofweek() {
        return dayofweek;
    }

    public void setDayofweek(String dayofweek) {
        this.dayofweek = dayofweek;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


    public String getScheduling()
    {
        StringBuilder sb = new StringBuilder();

        if(everynminute)
            sb.append("*/");
        sb.append(minute+ " ");
        if(everynhour)
            sb.append("*/");
        sb.append(hour+ " ");
        if(everyndayofmonth)
            sb.append("*/");
        sb.append(dayofmonth+ " ");
        sb.append(month+ " ");
        sb.append(dayofweek+ " ");

        return sb.toString();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(uuid).append(enable).append(execution).append(sendemail).append(type).append(minute).append(everynminute).append(hour).append(everynhour).append(dayofmonth).append(everyndayofmonth).append(month).append(dayofweek).append(username).append(command).append(comment).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Cron) == false) {
            return false;
        }
        Cron rhs = ((Cron) other);
        return new EqualsBuilder().append(uuid, rhs.uuid).append(enable, rhs.enable).append(execution, rhs.execution).append(sendemail, rhs.sendemail).append(type, rhs.type).append(minute, rhs.minute).append(everynminute, rhs.everynminute).append(hour, rhs.hour).append(everynhour, rhs.everynhour).append(dayofmonth, rhs.dayofmonth).append(everyndayofmonth, rhs.everyndayofmonth).append(month, rhs.month).append(dayofweek, rhs.dayofweek).append(username, rhs.username).append(command, rhs.command).append(comment, rhs.comment).isEquals();
    }

}
