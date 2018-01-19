package OMV.Classe;

import android.support.annotation.NonNull;
import android.util.Log;

import com.android.internal.util.Predicate;

import net.hockeyapp.android.CrashManagerListener;
import net.hockeyapp.android.utils.Util;

//import org.acra.ACRA;
import org.acra.util.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

//import static org.acra.ACRA.LOG_TAG;

/**
 * Created by Ividata7 on 29/01/2017.
 */

public class MyCustomCrashManagerListener extends CrashManagerListener {
    @Override
    public boolean shouldAutoUploadCrashes() {
        return true;
    }
    @Override
    public String getDescription() {
       return utils.Util.getlogs();
    }

    /**
     * Reads an InputStream into a string in an non blocking way for current thread
     * It has a default timeout of 3 seconds.
     *
     * @param input  the stream
     * @param filter should return false for lines which should be excluded
     * @param limit  the maximum number of lines to read (the last x lines are kept)
     * @return the String that was read.
     * @throws IOException if the stream cannot be read.
     */
    @NonNull
    private String streamToString(@NonNull InputStream input, Predicate<String> filter, int limit) throws IOException {

            return IOUtils.streamToString(input, filter, limit);

    }
}

