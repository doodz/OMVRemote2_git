package Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by thiba on 09/11/2016.
 */

public class KernelItem {

    @SerializedName("key")
    @Expose
    private Integer key;
    @SerializedName("name")
    @Expose
    private String name;

    /**
     *
     * @return
     * The key
     */
    public Integer getKey() {
        return key;
    }

    /**
     *
     * @param key
     * The key
     */
    public void setKey(Integer key) {
        this.key = key;
    }

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


    @Override
    public String toString()
    {

        return name;
    }
}
