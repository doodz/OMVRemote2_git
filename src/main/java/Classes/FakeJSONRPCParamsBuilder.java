package Classes;

import Interfaces.IJSONRPCParamsBuilder;

/**
 * Created by Ividata7 on 12/04/2017.
 */

public class FakeJSONRPCParamsBuilder implements IJSONRPCParamsBuilder {

    public String params;
    @Override
    public String toString()
    {
        return params;
    }
}
