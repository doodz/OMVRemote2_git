package Client;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.Deque;

import utils.Util;

/**
 * Created by thiba on 29/08/2016.
 *
 * when async requests are executed.
 */
public class Dispatcher {

    private int maxRequests = 64;
    private int maxRequestsPerHost = 1;
    /** Executes calls. Created lazily. */
    private ExecutorService executorService;
    /** Running asynchronous calls. Includes canceled calls that haven't finished yet. */
    private final Deque<AsyncCall> runningAsyncCalls = new ArrayDeque<>();
    /** Ready async calls in the order they'll be run. */
    private final Deque<AsyncCall> readyAsyncCalls = new ArrayDeque<>();
    public synchronized ExecutorService executorService() {
        if (executorService == null) {
            executorService = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60, TimeUnit.SECONDS,
                    new SynchronousQueue<Runnable>(), Util.threadFactory("OMV remote Dispatcher", false));
        }
        return executorService;
    }

    public synchronized void enqueue(AsyncCall call) {
        if (runningAsyncCalls.size() < maxRequests && (runningCallsForHost(call) < maxRequestsPerHost)) {
            runningAsyncCalls.add(call);
            executorService().execute(call);
        } else {
            readyAsyncCalls.add(call);
        }
    }

    /** Used by {@code AsyncCall#run} to signal completion. */
    void finished(AsyncCall call) {
        finished(runningAsyncCalls, call, true);
    }

    private <T> void finished(Deque<T> calls, T call, boolean promoteCalls) {
        int runningCallsCount;
        Runnable idleCallback;
        synchronized (this) {
            if (calls.remove(call))
                //throw new AssertionError("Call wasn't in-flight!");
                if (promoteCalls) promoteCalls();
            runningCallsCount = runningCallsCount();
            //idleCallback = this.idleCallback;
        }

        //if (runningCallsCount == 0 && idleCallback != null) {
        //    idleCallback.run();
        //}
    }

    public void Clear()
    {

        for (Iterator<AsyncCall> i = readyAsyncCalls.iterator(); i.hasNext(); ) {
            AsyncCall call = i.next();
            call.Finish();
        }

        for (Iterator<AsyncCall> i = runningAsyncCalls.iterator(); i.hasNext(); ) {
            AsyncCall call = i.next();
            call.Finish();
        }
        runningAsyncCalls.clear();
        readyAsyncCalls.clear();

    }

    private void promoteCalls() {
        if (runningAsyncCalls.size() >= maxRequests) return; // Already running max capacity.
        if (readyAsyncCalls.isEmpty()) return; // No ready calls to promote.

        for (Iterator<AsyncCall> i = readyAsyncCalls.iterator(); i.hasNext(); ) {
            AsyncCall call = i.next();

            if (runningCallsForHost(call) < maxRequestsPerHost) {
                i.remove();
                runningAsyncCalls.add(call);
                executorService().execute(call);
            }

            if (runningAsyncCalls.size() >= maxRequests) return; // Reached max capacity.
        }
    }

    /** Returns the number of running calls that share a host with {@code call}. */
    private int runningCallsForHost(AsyncCall call) {
        //int result = 0;
        //for (AsyncCall c : runningAsyncCalls) {
        //    if (c.host().equals(call.host())) result++;
        //}
        //return result;
        return runningCallsCount();
    }

    public synchronized int runningCallsCount() {
        return runningAsyncCalls.size();
    }
}
