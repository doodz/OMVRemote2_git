package Interfaces;




/**
 * Created by doods on 18/05/14.
 */
public interface INotifiableManager {

    public void onFinish(String response);

    public void onWrongConnectionState(int state, String cmd);

    public void onError(Exception e);

    public void onMessage(String message);

    //public void onMessage(int code, String message);
    public void retryAll();
}