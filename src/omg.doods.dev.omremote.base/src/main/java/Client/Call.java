package Client;

import java.io.IOException;

/**
 * Created by thiba on 29/08/2016.
 */
public interface  Call {


    JSONRPCParamsBuilder params();
    void cancel();
    boolean isExecuted();

    boolean isCanceled();

    Response execute() throws IOException, MovedPermanentlyException;
}
