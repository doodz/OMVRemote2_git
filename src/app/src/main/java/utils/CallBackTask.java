package utils;

import android.support.annotation.Nullable;

import Models.Errors;

/**
 * Created by thiba on 21/12/2016.
 */

public interface CallBackTask {

    void handleMessageError(@Nullable Errors error);
    void handleFinich();
}
