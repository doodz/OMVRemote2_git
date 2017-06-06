package Client;

import java.io.IOException;

import Interfaces.IJSONRPCParamsBuilder;

/**
 * Created by thiba on 29/08/2016.
 */
public class CallImpl implements Call {

    private boolean mExecuted;
    private boolean isCanceled;
    private IJSONRPCParamsBuilder mParams;

    @Override public IJSONRPCParamsBuilder params()
    {
        return mParams;

    }

    public CallImpl(IJSONRPCParamsBuilder params)
    {
        mParams = params;
    }

    @Override public void cancel() {


    }

    @Override public synchronized boolean isExecuted() {
        return mExecuted;
    }

    @Override public boolean isCanceled() {
        return isCanceled();
    }

    @Override public Response execute() throws IOException, MovedPermanentlyException,HttpPageException {

        //only allow one thread at a time
        synchronized (this) {
            if (mExecuted) throw new IllegalStateException("Already Executed");
            mExecuted = true;

            // TODO : doods : il faut utliser le client passer a AsyncCall.
            Response responce = JSONRPCClient.getInstance().Execute(this);
            if (responce == null) throw new IOException("Canceled");
            return responce;
        }

    }
}
