package Models.bd;

import java.io.Serializable;

/**
 * Created by Ividata7 on 10/04/2017.
 */

public class Command  implements Serializable {

    /**
     * Database ID
     */
    private long _id;


    public long getId()
    {
        return _id;

    }
    public void setId(long id)
    {
        _id= id;
    }
    /**
     * Name (description/label) of the host
     */
    private String _name;

    public String getName()
    {
        return _name;

    }
    public void setName(String name)
    {
        _name= name;
    }

    /**
     * Name (description/label) of the host
     */
    private String _command;

    public String getCommand()
    {
        return _command;

    }
    public void setCommand(String command)
    {
        _command= command;
    }

    private Boolean _output = false;
    public Boolean getOutput()
    {
        return _output;

    }
    public void setOutput(Boolean output)
    {
        _output= output;
    }

    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



}
