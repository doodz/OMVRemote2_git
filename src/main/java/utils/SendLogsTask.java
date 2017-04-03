package utils;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.android.internal.util.Predicate;
import com.dev.doods.omvremote2.R;

import net.hockeyapp.android.Constants;
import net.hockeyapp.android.utils.*;

import org.acra.util.IOUtils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

import Models.Errors;

/**
 * Created by Ividata7 on 31/01/2017.
 */

public class SendLogsTask  extends AsyncTask<CallBackTask, Void, Errors> {

    private Context mContext;
    private Errors mError = new Errors();
    //private String mHostName;

    public SendLogsTask(Context context)
    {
        mContext = context;
    }

    private CallBackTask mCallBackTask;
    @Override
    protected Errors doInBackground(CallBackTask... params) {

        mCallBackTask = params[0];

        final String subject = mContext.getString(R.string.send_logs_btn);
        final String token = PrefsUtil.getInstance().getFeedbackTokenFromPrefs(mContext);
        String appIdentifier = net.hockeyapp.android.utils.Util.getAppIdentifier(mContext);
        final String mUrl = Constants.BASE_URL + "api/2/apps/" + appIdentifier + "/feedback/";
        final String text = Util.getlogs();
        doPostPut(mUrl, "", "", subject, text, token);
        return null;
    }

    private HashMap<String, String> doPostPut(String url, String name, String email, String subject, String text, String token) {
        HashMap<String, String> result = new HashMap<String, String>();
        result.put("type", "send");

        HttpURLConnection urlConnection = null;
        try {
            Map<String, String> parameters = new HashMap<String, String>();
            parameters.put("name", name);
            parameters.put("email", email);
            parameters.put("subject", subject);
            parameters.put("text", text);
            parameters.put("bundle_identifier", Constants.APP_PACKAGE);
            parameters.put("bundle_short_version", Constants.APP_VERSION_NAME);
            parameters.put("bundle_version", Constants.APP_VERSION);
            parameters.put("os_version", Constants.ANDROID_VERSION);
            parameters.put("oem", Constants.PHONE_MANUFACTURER);
            parameters.put("model", Constants.PHONE_MODEL);
            parameters.put("sdk_version", Constants.SDK_VERSION);

            if (token != null) {
                url += token + "/";
            }

            urlConnection = new HttpURLConnectionBuilder(url)
                    .setRequestMethod(token != null ? "PUT" : "POST")
                    .writeFormFields(parameters)
                    .build();

            urlConnection.connect();

            result.put("status", String.valueOf(urlConnection.getResponseCode()));
            result.put("response", getStringFromConnection(urlConnection));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

        return result;
    }

    private String getStringFromConnection(HttpURLConnection connection) throws IOException {
        InputStream inputStream = new BufferedInputStream(connection.getInputStream());
        String jsonString = convertStreamToString(inputStream);
        inputStream.close();

        return jsonString;
    }

    private String convertStreamToString(InputStream inputStream) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream), 1024);
        StringBuilder stringBuilder = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return stringBuilder.toString();
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
    @Override
    protected void onPostExecute(Errors res) {

        if(res!= null)
            mCallBackTask.handleMessageError(res);
        else
            mCallBackTask.handleFinich();
    }


}