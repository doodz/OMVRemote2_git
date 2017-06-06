package Client;

import android.support.annotation.CallSuper;

import java.io.IOException;

import Interfaces.IHandlerCallback;
import Models.Errors;

/**
 * Created by Ividata7 on 07/02/2017.
 */

public class CallbackImpl implements Callback {


    private IHandlerCallback mActivity;
    public CallbackImpl(IHandlerCallback activity)
    {
        mActivity=activity;
    }
    @Override
    @CallSuper
    public void onFailure(Call call, Exception e) {
        e.printStackTrace();
        mActivity.ShowSnackError(e.getMessage(),false);
    }

    @Override
    @CallSuper
    public void OnOMVServeurError(Call call, Errors error) {
        mActivity.ShowSnackError(error.getMessage(),false);
    }

    @Override
    @CallSuper
    public void onResponse(Call call, Response response) throws IOException, InterruptedException {
        mActivity.SetOnError(false);
        mActivity.SetFinalized(true);
    }
}
