package utils;

import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;

import com.android.internal.util.Predicate;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

//import org.acra.ACRA;
//import org.acra.ACRAConstants;
//import org.acra.collections.BoundedLinkedList;
import org.acra.util.IOUtils;
//import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import static org.acra.ACRA.LOG_TAG;
//import static org.acra.ACRA.log;

/**
 * Created by thiba on 29/08/2016.
 */
public  class Util {

    public static ThreadFactory threadFactory(final String name, final boolean daemon) {
        return new ThreadFactory() {
            @Override public Thread newThread(Runnable runnable) {
                Thread result = new Thread(runnable, name);
                result.setDaemon(daemon);
                return result;
            }
        };
    }

    public static String ToJson(Object o)
    {
        Gson gson = new Gson();
        return gson.toJson(o);
    }


    public static <T> T FromJson(JsonElement json)
    {
        Type type = new TypeToken<T>(){}.getType();
        return FromJson(json,type);
    }


    public static <T> T FromJson(JsonElement json,Class<T> type)
    {
        Gson gson = new Gson();
        T var = gson.fromJson(json,type);
        return var;
    }

    public static <T> T FromJson(JsonElement json,Type T)
    {
        Gson gson = new Gson();
        T var = gson.fromJson(json,T);
        return var;
    }

    public static String humanReadableByteCount(long bytes, boolean si) {
        int unit = si ? 1000 : 1024;
        if (bytes < unit) return bytes + " B";
        int exp = (int) (Math.log10(bytes) / Math.log10(unit));
        String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp-1) + (si ? "" : "i");
        return String.format("%.2f %sB", bytes / Math.pow(unit, exp), pre);
    }

    public static String humanReadableByteCount(Double bytes, boolean si) {
        int unit = si ? 1000 : 1024;
        if (bytes < unit) return bytes + " B";
        int exp = (int) (Math.log10(bytes) / Math.log10(unit));
        String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp-1) + (si ? "" : "i");
        return String.format("%.2f %sB", bytes / Math.pow(unit, exp), pre);
    }

    public static String intToIp(int i) {
        return (i & 0xFF) + "." +
                ((i >> 8 ) & 0xFF) + "." +
                ((i >> 16) & 0xFF) + "." +
                ((i >> 24) & 0xFF);
    }

    /**
     * Try to extract a hardware MAC address from a given IP address using the
     * ARP cache (/proc/net/arp).<br>
     * <br>
     * We assume that the file has this structure:<br>
     * <br>
     * IP address       HW type     Flags       HW address            Mask     Device
     * 192.168.18.11    0x1         0x2         00:04:20:06:55:1a     *        eth0
     * 192.168.18.36    0x1         0x2         00:22:43:ab:2a:5b     *        eth0
     *
     * @param ip
     * @return the MAC from the ARP cache
     */
    public static String getMacFromArpCache(String ip) {
        if (ip == null)
            return null;
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("/proc/net/arp"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] splitted = line.split(" +");
                if (splitted != null && splitted.length >= 4 && ip.equals(splitted[0])) {
                    // Basic sanity check
                    String mac = splitted[3];
                    if (mac.matches("..:..:..:..:..:..")) {
                        return mac;
                    } else {
                        return null;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }



    public static String NewUUID()
    {
        UUID myuuid = UUID.randomUUID();

        return myuuid.toString();
    }

    private static String  pattern = "[0-9]{2}-[0-9]{2}\\s[0-9]{2}:[0-9]{2}:[0-9]{2}.[0-9]{3}(.*)";

    private static String  res = "";
    private static int count = 0;
    public static String getlogs() {

        final List<String> commandLine = new ArrayList<String>();
        commandLine.add("logcat");
        commandLine.add("-t");
        commandLine.add("100");
        commandLine.add("-v");
        commandLine.add("time");

        final int myPid = android.os.Process.myPid();
        final String myPidStr = "("+Integer.toString(myPid)+"):";

        String logcat = "N/A";
        try {
            final Process process =  new ProcessBuilder().command(commandLine).redirectErrorStream(true).start();
            final Pattern p = Pattern.compile(pattern);


            logcat = streamToString(process.getInputStream(), new Predicate<String>() {
                @Override
                public boolean apply(String s) {

                    try {
                        //if(s.contains("Adreno-GSL"))return false;
                        if (myPidStr == null || s.contains(myPidStr)) {

                            Matcher m = p.matcher(s);
                            if(m.matches())
                            {
                                String res2 = m.group(m.groupCount());

                                if(res2.equals(res))
                                {
                                    count++;
                                    return false;
                                }
                                else if (count != 0) {
                                    s = "doods => x" + count + "\r\n"+ s ;
                                    count = 0;
                                }
                                else
                                    res = res2;

                            }
                            return true;
                        }
                    }catch (Exception ex)
                    {
                        String msg = ex.getMessage();

                    }
                    return false;
                }
            }, 100);
            process.destroy();

        } catch (IOException e) {
            Log.e("Util", "Util.getlogs() could not retrieve data.", e);
        }

        return logcat;
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
    private static String streamToString(@NonNull InputStream input, Predicate<String> filter, int limit) throws IOException {

        return IOUtils.streamToString(input, filter, limit);

    }


    public static boolean containsCaseInsensitive(String s, List<String> l){
        for (String string : l){
            if (string.equalsIgnoreCase(s)){
                return true;
            }
        }
        return false;
    }

    public static void SetInteger(EditText view, int val)
    {
        view.setText(Integer.toString(val));
    }

    public static int GetInteger(EditText view)
    {
        return Integer.parseInt(view.getText().toString());
    }
}
