package Models;


        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;


/**
 * Created by thiba on 30/08/2016.
 */
public class SystemInformation {


    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("value")
    @Expose
    private Value value;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("index")
    @Expose
    private Integer index;

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
     * The value
     */
    public Value getValue() {
        return value;
    }

    /**
     *
     * @param value
     * The value
     */
    public void setValue(Value value) {
        this.value = value;
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
     * The index
     */
    public Integer getIndex() {
        return index;
    }

    /**
     *
     * @param index
     * The index
     */
    public void setIndex(Integer index) {
        this.index = index;
    }

}

