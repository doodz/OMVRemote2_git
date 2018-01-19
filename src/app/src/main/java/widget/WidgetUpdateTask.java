package widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import android.os.Handler;
import android.widget.TextView;


import com.dev.doods.omvremote2.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import Client.Call;
import Client.Callback;
import Client.Host;
import Client.JSONRPCClient;
import Client.Response;
import Controllers.HomeController;
import Deserializers.ValueDeserializer;
import Models.Errors;
import Models.SystemInformation;
import Models.Value;

/**
 * Created by Ividata7 on 05/02/2017.
 */

public class WidgetUpdateTask extends AsyncTask<Host, Void, Map<String, String>> {

    public static final String STATUS = "status";
    public static final String STATUS_ONLINE = "online";
    public static final String STATUS_OFFLINE = "offline";
    public static final String KEY_TEMP = "temp";
    public static final String KEY_ARM_FREQ = "armFreq";
    public static final String KEY_LOAD_AVG = "loadAvg";
    public static final String KEY_MEM_USED = "memUsed";
    public static final String KEY_MEM_TOTAL = "memTotal";
    public static final String KEY_MEM_USED_PERCENT = "memUsedPercent";


    private Context context;
    private RemoteViews widgetView;
    private boolean showArm;
    private boolean showTemp;
    private boolean showMemory;
    private boolean showLoad;
    private boolean useFahrenheit;
    private int appWidgetId;
    private JSONRPCClient client;
    private HomeController mController;
    private Handler mHandler = new Handler();

    public WidgetUpdateTask(Context context, RemoteViews widgetView, boolean showCpu, boolean showUptime, boolean showMemory, int appWidgetId) {

        mController = new HomeController(null);
        this.context = context;
        this.widgetView = widgetView;
        this.showArm = showArm;
        this.showTemp = showTemp;
        this.showMemory = showMemory;
        this.showLoad = showLoad;
        this.useFahrenheit = useFahrenheit;
        this.appWidgetId = appWidgetId;
    }

    private void connect(Host host) {


        client = JSONRPCClient.getInstance4Tasks();
        client.SetHost(host);
        mController.SetClient(client);

        mController.GetSystemInformation(new Callback() {
            @Override
            public void onFailure(Call call, Exception e) {
                mHandler.post(new Runnable(){
                    public void run() {
                        widgetView.setTextViewText(R.id.textStatusValue, "error" + " - " + SimpleDateFormat.getTimeInstance(DateFormat.SHORT).format(new Date()));
                        widgetView.setViewVisibility(R.id.textStatusValue, View.VISIBLE);
                        widgetView.setViewVisibility(R.id.buttonRefresh, View.VISIBLE);
                        widgetView.setViewVisibility(R.id.refreshProgressBar, View.GONE);
                    }
                });
                Log.d("WidgetUpdateTask","Query failed, showing device as offline.");
            }

            @Override
            public void OnOMVServeurError(Call call,final Errors error) {
                mHandler.post(new Runnable(){
                    public void run() {
                        widgetView.setTextViewText(R.id.textStatusValue, error.getMessage() + " - " + SimpleDateFormat.getTimeInstance(DateFormat.SHORT).format(new Date()));
                        widgetView.setViewVisibility(R.id.textStatusValue, View.VISIBLE);
                        widgetView.setViewVisibility(R.id.buttonRefresh, View.VISIBLE);
                        widgetView.setViewVisibility(R.id.refreshProgressBar, View.GONE);
                    }
                });
                Log.d("WidgetUpdateTask","Query failed, showing device as offline.");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException, InterruptedException {

                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.registerTypeAdapter(Value.class, new ValueDeserializer());
                //gsonBuilder.registerTypeAdapter(SystemInformation.class, new SystemInformationDeserializer());
                Gson gson = gsonBuilder.create();
                JsonElement j = response.GetJsonResult();

                TypeToken tt = new TypeToken<ArrayList<SystemInformation>>(){};
                Type t =  tt.getType();
                // ArrayList<LinkedTreeMap<String,String>> oo =Util.FromJson(j,Object.class);
                final  ArrayList<SystemInformation> res = gson.fromJson(j,t);
                /*mHandler.post(new Runnable(){
                    public void run() {

                        try {*/
                            formatres(res);
                       /* }catch (Exception ex)
                        {
                            ex.printStackTrace();
                        }
                    }
                });*/
            }
        });
    }


    private void formatres( ArrayList<SystemInformation> res)
    {
        String status = STATUS_ONLINE;
        widgetView.setTextViewText(R.id.textStatusValue, status + " - " + SimpleDateFormat.getTimeInstance(DateFormat.SHORT).format(new Date()));
        widgetView.setViewVisibility(R.id.textStatusValue, View.VISIBLE);
        widgetView.setViewVisibility(R.id.buttonRefresh, View.VISIBLE);
        widgetView.setViewVisibility(R.id.refreshProgressBar, View.GONE);

        for (SystemInformation d: res) {
            toto(d);
        }
        // Instruct the widget manager to update the widget
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        Log.d("WidgetUpdateTask","Updating widget[ID={"+appWidgetId+"}] view after AsyncTask finished.");
        appWidgetManager.updateAppWidget(appWidgetId, widgetView);
    }

    private void toto(SystemInformation res)
    {


        String val = res.getValue().getText();
        TextView tv = null;
        switch ( res.getName().replace(' ','_'))
        {
            case "Hostname":

                break;
            case "Version":

                break;
            case "Processor":

                break;
            case "Kernel":

                break;
            case "System_time":

                break;
            case "Uptime":
                widgetView.setTextViewText(R.id.textUptimeValue, val);
                break;
            case "Load_average":

                break;
            case "CPU_usage":
                widgetView.setTextViewText(R.id.textCpuValue, val);
                widgetView.setProgressBar( R.id.progressBarCpuValue, 100,res.getValue().getValue(), false);

                break;
            case "Memory_usage":
                widgetView.setTextViewText(R.id.textMemoryValue, val);
                widgetView.setProgressBar( R.id.progressBarMemory,100, res.getValue().getValue(),false);
                break;
            default:
                Log.i("WidgetUpdateTask","unknown property name : "+res.getName().replace(' ','_'));
        }
    }



    @Override
    protected Map<String, String> doInBackground(Host... params) {
        final Map<String, String> result = new HashMap<>();
        Host host = params[0];
        connect(host);
        return result;
    }

    @Override
    protected void onPostExecute(Map<String, String> stringStringMap) {


    }

}
