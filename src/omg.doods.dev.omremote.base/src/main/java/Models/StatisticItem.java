package Models;

/**
 * Created by thiba on 15/11/2016.
 */

public class StatisticItem {

    private String name;
    private String url;

    public StatisticItem(){}
    public StatisticItem(String name){

        this.name = name;
        url ="/rrd.php?name=".concat(name);

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
     * @return
     * The url
     */
    public String getUrl() {
        return url;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

}
