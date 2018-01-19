package Client;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import Models.Errors;
import utils.Util;

import static android.R.attr.bitmap;

/**
 * Created by thiba on 15/11/2016.
 */

public class HttpGetClient extends JSONRPCClient {


    private HttpGetClient() {
        super();
    }


    public void setCookieManager(java.net.CookieManager cookieManager)
    {
        msCookieManager = cookieManager;
    }

    @Override
    public void setBaseUrl(String url) {
        mBaseUrl = url.concat(Host.RDD_PATH);
    }


    public Bitmap getBitMap(String urlStr) {
        JsonElement jsonResponse = null;
        HttpURLConnection urlConnection = null;
        Bitmap bmp = null;
        try {
            URL url = new URL(mBaseUrl);
            urlConnection = OpenConnection(url);

            Log.i("HttpGetClient", "GET : ".concat(mBaseUrl));
            Log.i("HttpGetClient", "QUERY : ".concat(urlStr));

            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Content-Type","image/png");
            //urlConnection.setRequestProperty("Content-length", "0");
            urlConnection.setUseCaches(false);
            urlConnection.setDoOutput(true);
            urlConnection.setAllowUserInteraction(false);
            urlConnection.setConnectTimeout(10000);
            urlConnection.setReadTimeout(10000);

            if(msCookieManager.getCookieStore().getCookies().size() > 0)
            {
                //While joining the Cookies, use ',' or ';' as needed. Most of the server are using ';'
                urlConnection.setRequestProperty("Cookie",
                        TextUtils.join(";",  msCookieManager.getCookieStore().getCookies()));
            }

            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

            bmp = BitmapFactory.decodeStream(in);

            int responseCode = urlConnection.getResponseCode();
            if (responseCode == HttpsURLConnection.HTTP_OK)
            {
                Log.i("HttpGetClient", " responseCode HTTP_OK");


                Map<String, List<String>> headerFields = urlConnection.getHeaderFields();
                List<String> cookiesHeader = headerFields.get(COOKIES_HEADER);

                if(cookiesHeader != null)
                {
                    for (String cookie : cookiesHeader)
                    {
                        Log.i("HttpGetClient","cookie "+cookie);
                        msCookieManager.getCookieStore().add(null, HttpCookie.parse(cookie).get(0));
                    }
                }
            }

        }
        catch (MalformedURLException urlexp)
        {
            Log.i("HttpGetClient","MalformedURLException :" + "mBaseUrl");
            urlexp.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }

        return bmp;
    }
}
