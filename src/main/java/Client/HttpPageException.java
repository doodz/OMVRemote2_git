package Client;

/**
 * Created by Ividata7 on 06/06/2017.
 */

public class HttpPageException extends Exception {

    public String HttpPage;
    public HttpPageException(String message) {
        super("It is not OMV API, but an web page.");
        HttpPage = message;
    }
}

