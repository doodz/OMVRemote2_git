package Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by thiba on 14/09/2016.
 */
public class Repo {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("enable")
    @Expose
    private String enable;
    @SerializedName("repo")
    @Expose
    private String repo;
    @SerializedName("description")
    @Expose
    private String description;

    /**
     *
     * @return
     * The id
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(String id) {
        this.id = id;
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
     * The enable
     */
    public String getEnable() {
        return enable;
    }

    /**
     *
     * @param enable
     * The enable
     */
    public void setEnable(String enable) {
        this.enable = enable;
    }

    /**
     *
     * @return
     * The repo
     */
    public String getRepo() {
        return repo;
    }

    /**
     *
     * @param repo
     * The repo
     */
    public void setRepo(String repo) {
        this.repo = repo;
    }

    /**
     *
     * @return
     * The description
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     * The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    public Repo(){}

    public Repo(int type,String name)
    {
        this.typeAffichage = type;
        this.repo = name;

    }


    public static final int ITEM = 0;
    public static final int SECTION = 1;
    public int typeAffichage = ITEM;

    public boolean isSelected() {
        return enable.equals("1");
    }

    public void setSelected(boolean isSelected) {
        enable = isSelected?"1":"0";
    }
}
