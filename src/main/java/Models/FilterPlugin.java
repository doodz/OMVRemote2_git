package Models;

import java.io.Serializable;
import java.util.List;

/**
 * Created by thiba on 08/10/2016.
 */

public class  FilterPlugin implements Serializable
{
    public String FilterOnPluginSection = null;
    public String FilterOnName = null;
    public Boolean FilterOnInstaled = null;

    public List<String> PluginsectionList;

}