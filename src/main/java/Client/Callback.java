package Client;



import java.io.IOException;

import Models.Errors;

/**
 * Created by thiba on 17/08/2016.
 */
public interface Callback {

    /**
     * Called when the request could not be executed due to cancellation, a connectivity problem or
     * timeout. Because networks can fail during an exchange, it is possible that the remote server
     * accepted the request before the failure.
     */
    void onFailure(Call call, Exception e);

    void OnOMVServeurError(Call call, Errors error);

    /**
     * Called when the HTTP response was successfully returned by the remote server.
     */
    void onResponse(Call call, Response response) throws IOException, InterruptedException;
}
