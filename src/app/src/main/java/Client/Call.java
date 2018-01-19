package Client;

import java.io.IOException;

import Interfaces.IJSONRPCParamsBuilder;

/**
 * Created by thiba on 29/08/2016.
 */
public interface  Call {


    IJSONRPCParamsBuilder params();
    void cancel();
    boolean isExecuted();

    boolean isCanceled();

    Response execute() throws IOException, MovedPermanentlyException, HttpPageException;
}
