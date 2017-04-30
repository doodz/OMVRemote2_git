package OMV.Base;

import Interfaces.INameDescObject;

/**
 * Created by Ividata7 on 27/04/2017.
 */

public abstract class NameDescObjectBase implements INameDescObject {

    public abstract Boolean getEnable();
    public abstract String getName();
    public abstract String getDescription();
}
