package Interfaces;

/**
 * Created by Ividata7 on 08/02/2017.
 */

public interface IHandlerCallback {
    void SetFinalized(boolean finalized);
    void SetOnError(boolean onError);
    void ShowSnackError(String msg,boolean cansendLogs);
}
