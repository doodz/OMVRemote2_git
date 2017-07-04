package Client;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.JsonReader;
import android.util.Log;
import android.webkit.URLUtil;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.MalformedJsonException;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import Models.Errors;
import utils.Util;

/**
 * Created by thiba on 12/08/2016.
 */
public class JSONRPCClient implements Runnable {

    final String COOKIES_HEADER = "Set-Cookie";
    protected java.net.CookieManager msCookieManager = new java.net.CookieManager();
    private TrustManager localTrustmanager;
    protected String mBaseUrl;
    private String mJsonParams;
    private Host mHost;

    private static JSONRPCClient instance;

    Dispatcher mDispatcher;
    public Dispatcher Dispatcher(){return mDispatcher;}
    /**
     *
     * @return singleton
     */
    public static JSONRPCClient getInstance()
    {
        if(instance == null) instance = new JSONRPCClient();
        return instance;

    }
    private static JSONRPCClient instance4Tasks;
    public static JSONRPCClient getInstance4Tasks()
    {
        if(instance4Tasks == null) instance4Tasks = new JSONRPCClient();
        return instance4Tasks;

    }


    /**
     * Set current host for api
     * @param host
     */
    public void SetHost(Host host)
    {
        mHost = host;
        mBaseUrl = host.getBaseUrl();
        msCookieManager.getCookieStore().removeAll();
    }

    /**
     * Get current host for api
     * @return  host
     */
    public Host GetHost()
    {
        return mHost;
    }

    public boolean HaveHost()
    {
        return mHost != null;
    }



    protected JSONRPCClient()
    {
        mDispatcher = new Dispatcher();
        // Dummy trust manager that trusts all certificates
        localTrustmanager = new X509TrustManager() {

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain,
                                       String authType) throws CertificateException {
        }

        @Override
        public void checkClientTrusted(X509Certificate[] chain,
                                       String authType) throws CertificateException {
        }
    };
        // Create SSLContext and set the socket factory as default
        SSLContext sslc = null;
        try {
            sslc = SSLContext.getInstance("TLS");
            sslc.init(null, new TrustManager[] { localTrustmanager },
                    new SecureRandom());
        } catch (KeyManagementException e) {
            e.printStackTrace();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        HttpsURLConnection.setDefaultSSLSocketFactory(sslc
                .getSocketFactory());
    }

    public void setBaseUrl(String url) {
        mBaseUrl = url.concat(Host.API_PATH);
    }

    public String getBaseUrl() {
        return mBaseUrl;
    }
    public void setJsonParams(String jsonParams) {
        mJsonParams = jsonParams;
    }


    public Response Execute(Call call) throws IOException,MovedPermanentlyException,HttpPageException
    {
        JsonElement jsonResponse = null;
        HttpURLConnection urlConnection = null;
        int responseCode=HttpsURLConnection.HTTP_OK;
        try {
        URL url = new URL(mBaseUrl);
            urlConnection = OpenConnection(url);

            Log.i("JSONRPCClient", "POST : ".concat(mBaseUrl));
            Log.i("JSONRPCClient", "PARAMS : ".concat(call.params().toString()));

            StringBuilder result = new StringBuilder();
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type","application/json");
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

            OutputStreamWriter dStream = new OutputStreamWriter(urlConnection.getOutputStream());
            dStream.write(call.params().toString());
            //dStream.writeBytes(entity); //Writes out the string to the underlying output stream as a sequence of bytes
            dStream.flush(); // Flushes the data output stream.
            dStream.close(); // Closing the output stream.

            //urlConnection.connect();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            responseCode = urlConnection.getResponseCode();
            Log.i("JSONRPCClient", result.toString());
            if (responseCode == HttpsURLConnection.HTTP_OK)
            {
                Log.i("JSONRPCClient", " responseCode HTTP_OK");


                Map<String, List<String>> headerFields = urlConnection.getHeaderFields();
                List<String> cookiesHeader = headerFields.get(COOKIES_HEADER);

                if(cookiesHeader != null)
                {
                    for (String cookie : cookiesHeader)
                    {
                        Log.i("JSONRPCClient","cookie "+cookie);
                        msCookieManager.getCookieStore().add(null, HttpCookie.parse(cookie).get(0));
                    }
                }
            }

            if(responseCode == HttpsURLConnection.HTTP_MOVED_PERM)
            {
                throw new MovedPermanentlyException("Moved Permanently");
                //String erorlog =  "{\"response\":{\"authenticated\":false,\"username\":\""+ mHost.getUser()+"\"},\"error\":{\"code\":301,\"message\":\"Moved Permanently\"}}";
                //jsonResponse = new JsonParser().parse(erorlog);
                //return new Response(jsonResponse);
            }
            if (result.toString().contains("<html>")){
                throw new HttpPageException(result.toString());
            }
            jsonResponse = new JsonParser().parse(result.toString());

        }
        catch (MalformedURLException urlexp)
        {
            Log.i("JSONRPCClient","MalformedURLException :" + mBaseUrl);
            urlexp.printStackTrace();
        }
        catch (ProtocolException e) {
            Log.i("JSONRPCClient","ProtocolException");
            e.printStackTrace();
        }
        catch (MalformedJsonException e)
        {
            Log.i("JSONRPCClient","MalformedJsonException");
            e.printStackTrace();


        }
        finally {
            if(urlConnection != null)
                urlConnection.disconnect();
        }




        Response resp = new Response(jsonResponse);

       if( resp.Error())
       {
           JsonElement j =  resp.GetJsonError();
           Errors err = Util.FromJson(j, Errors.class);
           Log.i("JSONRPCClient","Error :" + err.getCode());
           if(err.getCode() == 5000) {
               Log.i("JSONRPCClient", "Session not authenticated, try to connect");
           }
           if(err.getCode() == 5001) {
               Log.i("JSONRPCClient", "Session expired, try to connect");
           }
           if(err.getCode() == 5001 || err.getCode() == 5000)
           {
               JSONRPCParamsBuilder params = new JSONRPCParamsBuilder();
               //set parameters
               params.setService("Session");
               params.setMethod("login");

               params.addParam("username", mHost.getUser());
               params.addParam("password",mHost.getPass());

               String jsonParams = params.toString();
               try {

                   JSONObject jsonObj = connect(jsonParams);
                    if(jsonObj != null)
                   if(!jsonObj.getBoolean("authenticated")) {
                       Log.i("JSONRPCClient", "Incorrect username or password");

                        String erorlog =  "{\"response\":{\"authenticated\":false,\"username\":\""+ mHost.getUser()+"\"},\"error\":{\"code\":5000,\"message\":\"Incorrect username or password\"}}";
                       jsonResponse = new JsonParser().parse(erorlog);
                       resp = new Response(jsonResponse);
                       mDispatcher.Clear();
                       mDispatcher = new Dispatcher();

                   }
                    else {

                       return Execute(call);
                   }
               } catch (Exception e) {
                   e.printStackTrace();
               }
           }

           if((err.getCode() == 0 || err.getCode() == 2003) && err.getMessage().contains("Invalid context"))
           {
               Errors errr =  resp.GetErrorObject();
               errr.setMessage("Invalid context. The user must be admin");

           }

       }

        return resp;
    }

    // Verifier that verifies all hosts
    public static final HostnameVerifier DUMMY_VERIFIER = new HostnameVerifier() {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    };


    protected HttpURLConnection OpenConnection(URL url)throws IOException
    {
        HttpURLConnection urlConnection = null;
        if(URLUtil.isHttpsUrl(mBaseUrl))
        {
            HttpsURLConnection urlConnectionSSL =(HttpsURLConnection) url.openConnection();
            urlConnectionSSL.setHostnameVerifier(DUMMY_VERIFIER);
            urlConnection = urlConnectionSSL;
        }
        else
            urlConnection = (HttpURLConnection)url.openConnection();
        return urlConnection;
    }

    private JSONObject connect( ) throws Exception{
        URL url = new URL(mBaseUrl);
        HttpsURLConnection urlConnectionSSL = null;
        HttpURLConnection urlConnection = null;
        if(URLUtil.isHttpsUrl(mBaseUrl)) {
            try {
                 urlConnectionSSL = (HttpsURLConnection) url
                        .openConnection();
                urlConnectionSSL.setHostnameVerifier(DUMMY_VERIFIER);
                urlConnection = urlConnectionSSL;

            }
            catch (Exception e) {
                e.printStackTrace();
            } finally {
                if(urlConnectionSSL != null)
                    urlConnectionSSL.disconnect();
            }
        }
        else
         urlConnection = (HttpURLConnection)url.openConnection();

        JSONObject jsonResult = null;

        try {

            Log.i("JSONRPCClient", "POST ".concat(mBaseUrl));
            Log.i("JSONRPCClient", "Data: ".concat(mJsonParams));

            StringBuilder result = new StringBuilder();
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type","application/json");
            //urlConnection.setRequestProperty("Content-length", "0");
            urlConnection.setUseCaches(false);
            urlConnection.setDoOutput(true);
            urlConnection.setAllowUserInteraction(false);
            urlConnection.setConnectTimeout(5000);
            urlConnection.setReadTimeout(5000);

            if(msCookieManager.getCookieStore().getCookies().size() > 0)
            {
                //While joining the Cookies, use ',' or ';' as needed. Most of the server are using ';'
                urlConnection.setRequestProperty("Cookie",
                        TextUtils.join(";",  msCookieManager.getCookieStore().getCookies()));
            }

            OutputStreamWriter dStream = new OutputStreamWriter(urlConnection.getOutputStream());
            dStream.write(mJsonParams);
            //dStream.writeBytes(entity); //Writes out the string to the underlying output stream as a sequence of bytes
            dStream.flush(); // Flushes the data output stream.
            dStream.close(); // Closing the output stream.

            //urlConnection.connect();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            Log.i("JSONRPCClient", result.toString());

            int responseCode = urlConnection.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_MOVED_PERM)
            {
                Log.i("JSONRPCClient", " responseCode HTTP_MOVED_PERM");
                result = new StringBuilder();


                result.append( "{\"response\":{\"authenticated\":false,\"responseCode\":\"HTTP_MOVED_PERM\"},\"error\":null}");

            }

            if (responseCode == HttpsURLConnection.HTTP_OK)
            {
                Log.i("JSONRPCClient", " responseCode HTTP_OK");


                Map<String, List<String>> headerFields = urlConnection.getHeaderFields();
                List<String> cookiesHeader = headerFields.get(COOKIES_HEADER);

                if(cookiesHeader != null)
                {
                    for (String cookie : cookiesHeader)
                    {
                        Log.i("JSONRPCClient","cookie "+cookie);
                        msCookieManager.getCookieStore().add(null, HttpCookie.parse(cookie).get(0));
                    }
                }
            }


            JSONObject jsonResponse = (JSONObject) new JSONTokener(result.toString()).nextValue();
            if (!jsonResponse.isNull("response")) {
                jsonResult = jsonResponse.getJSONObject("response");
            }
            else
            {
                Log.i("JSONRPCClient","jsonResponse is null");
                Log.i("JSONRPCClient","Result =>"+result.toString());

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(urlConnection != null)
                urlConnection.disconnect();
        }
        return jsonResult;
    }


    public JSONObject connect(String jsonParams ) throws Exception {

        Log.i("JSONRPCClient", "connect");
        mJsonParams = jsonParams;
        JSONObject jsonResult = connect();
        return jsonResult;
    }


    @Override
    public void run() {
        try {
            connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public File Download(String urlStr,File path,String fileName)
    {
        // /download.php
        File yourFile = new File(path,fileName+".log");
        Map<String, String> params = new HashMap<String, String>(2);
        params.put("service", "LogFile");
        params.put("method", "getContent");
        params.put("params","%7B%26quot%3Bid%26quot%3B%3A%26quot%3B"+fileName+"%26quot%3B%7D");
        HttpURLConnection urlConnection = null;

        try {
            URL url = new URL(mHost.getUrl().concat(urlStr));
            urlConnection = OpenConnection(url);

            Log.i("HttpGetClient", "POST : ".concat(mHost.getUrl().concat(urlStr)));
            Log.i("HttpGetClient", "QUERY : ".concat(urlStr));

            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            urlConnection.setRequestProperty("Accept","atext/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
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
            OutputStream outputStream = urlConnection.getOutputStream();
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream),
                    true);

            String boundary = "===" + System.currentTimeMillis() + "===";
            StringBuilder parameters = new StringBuilder();

            parameters.append("service=" + URLEncoder.encode("LogFile", "UTF-8"));
            parameters.append("&");
            parameters.append("method=" + URLEncoder.encode("getContent", "UTF-8"));
            parameters.append("&");
            parameters.append("params=" + URLEncoder.encode("{&quot;id&quot;:&quot;"+fileName+"&quot;}", "UTF-8"));
            Log.i("JSONRPCClient", " params : "+parameters.toString());
            writer.println(parameters);
            writer.close();

            for (Map.Entry<String, String> entry : params.entrySet())
            {


               // writer.append("--" + boundary).append("\r\n");
               // writer.append("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"")
               //         .append("\r\n");
               // writer.append("Content-Type: text/plain; charset=" + "UTF-8").append(
                //        "\r\n");
                // writer.append("\r\n");
                // writer.append(entry.getValue()).append("\r\n");
                // writer.flush();
            }

            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

            yourFile.createNewFile(); // if file already exists will do nothing
            OutputStream stream = new BufferedOutputStream(new FileOutputStream(yourFile, false));
            int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];
            int len = 0;
            while ((len = in.read(buffer)) != -1) {
                //Log.i("JSONRPCClient", buffer.toString());
                stream.write(buffer, 0, len);
            }
            if(stream!=null)
                stream.close();

            int responseCode = urlConnection.getResponseCode();
            if (responseCode == HttpsURLConnection.HTTP_OK)
            {
                Log.i("JSONRPCClient", " responseCode HTTP_OK");


                Map<String, List<String>> headerFields = urlConnection.getHeaderFields();
                List<String> cookiesHeader = headerFields.get(COOKIES_HEADER);

                if(cookiesHeader != null)
                {
                    for (String cookie : cookiesHeader)
                    {
                        Log.i("JSONRPCClient","cookie "+cookie);
                        msCookieManager.getCookieStore().add(null, HttpCookie.parse(cookie).get(0));
                    }
                }
            }

        }
        catch (MalformedURLException urlexp)
        {
            Log.i("JSONRPCClient","MalformedURLException :" + "mBaseUrl");
            urlexp.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(urlConnection !=null)
                urlConnection.disconnect();
        }
        return  yourFile;
    }

    public Bitmap getBitMap(String urlStr) {

        HttpURLConnection urlConnection = null;
        Bitmap bmp = null;
        try {
            URL url = new URL(mHost.getUrl().concat(urlStr));
            urlConnection = OpenConnection(url);

            Log.i("HttpGetClient", "GET : ".concat(mHost.getUrl().concat(urlStr)));
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

            String str = urlConnection.getResponseMessage();

            int responseCode = urlConnection.getResponseCode();
            if (responseCode == HttpsURLConnection.HTTP_OK)
            {
                Log.i("JSONRPCClient", " responseCode HTTP_OK");


                Map<String, List<String>> headerFields = urlConnection.getHeaderFields();
                List<String> cookiesHeader = headerFields.get(COOKIES_HEADER);

                if(cookiesHeader != null)
                {
                    for (String cookie : cookiesHeader)
                    {
                        Log.i("JSONRPCClient","cookie "+cookie);
                        msCookieManager.getCookieStore().add(null, HttpCookie.parse(cookie).get(0));
                    }
                }
            }

        }
        catch (MalformedURLException urlexp)
        {
            Log.i("JSONRPCClient","MalformedURLException :" + "mBaseUrl");
            urlexp.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(urlConnection !=null)
                urlConnection.disconnect();
        }

        return bmp;
    }

}
