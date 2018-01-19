package widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.RemoteViews;

import Client.Host;
import DAL.HostsDAO;


/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link SystemInformationWidgetConfigureActivity }
 */
public class SystemInformationWidget extends AppWidgetProvider {


    private static final String ACTION_WIDGET_UPDATE_ONE_MANUAL = "updateOneWidgetManual";

    private HostsDAO mHostsDAO;


    static void updateAppWidget(Context context, final int appWidgetId, HostsDAO hostsDAO, boolean triggeredByAlarm) {
        Log.d("OverclockingWidget","Starting refresh of Widget[ID={"+appWidgetId+"}].");
        Long deviceId = SystemInformationWidgetConfigureActivity.loadDeviceId(context, appWidgetId);
        Log.d("OverclockingWidget","Refreshing widget for device[ID={"+deviceId+"}]");
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (deviceId != null) {
            // get update interval
            final boolean showCpu = SystemInformationWidgetConfigureActivity.loadShowStatus(context, SystemInformationWidgetConfigureActivity.PREF_SHOW_CPU_SUFFIX, appWidgetId);
            final boolean showUptime = SystemInformationWidgetConfigureActivity.loadShowStatus(context, SystemInformationWidgetConfigureActivity.PREF_SHOW_UPTIME_SUFFIX, appWidgetId);
            final boolean showLoad = SystemInformationWidgetConfigureActivity.loadShowStatus(context, SystemInformationWidgetConfigureActivity.PREF_SHOW_LOAD_SUFFIX, appWidgetId);
            final boolean showMemory = SystemInformationWidgetConfigureActivity.loadShowStatus(context, SystemInformationWidgetConfigureActivity.PREF_SHOW_MEMORY_SUFFIX, appWidgetId);
            final boolean onlyOnWlan = SystemInformationWidgetConfigureActivity.loadOnlyOnWifi(context, appWidgetId);
            Host host = hostsDAO.read(deviceId);

            if (host == null) {
                // device has been deleted
                Log.d("OverclockingWidget","Device has been deleted, showing alternate view with message.");
                SystemInformationWidgetView.initRemovedView(context, appWidgetId);
                return;
            }

           /* if (deviceBean.usesAuthentificationMethod(RaspberryDeviceBean.AUTH_PUBLIC_KEY) || deviceBean.usesAuthentificationMethod(RaspberryDeviceBean.AUTH_PUBLIC_KEY_WITH_PASSWORD)) {
                // need permission to read private key file
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    Log.d("OverclockingWidget","Skipping widget update process because permission to read private key file is not granted. Showing alternate view.");
                    OverclockingWidgetView.initNoPermissionView(context, appWidgetId);
                    return;
                }
            }*/

            // Construct the RemoteViews object
            final RemoteViews views = SystemInformationWidgetView.initDefaultView(context, appWidgetId, host, showCpu, showUptime, showMemory);
            if (isNetworkAvailable(context)) {
                if (!triggeredByAlarm || !(onlyOnWlan && !isWiFiAvailable(context))) {
                    Log.d("OverclockingWidget","Starting async update task for Widget[ID={"+appWidgetId+"}]...");
                    SystemInformationWidgetView.startRefreshing(views, context, appWidgetId);
                    // query in AsyncTask
                    new WidgetUpdateTask(context, views, showCpu, showUptime, showMemory, appWidgetId).execute(host);
                } else {
                    Log.d("OverclockingWidget","Skipping update - no WiFi connected.");
                }
            } else {
                Log.d("OverclockingWidget","No network for update of Widget[ID={"+appWidgetId+"}] - resetting widget view.");
                            }
        } else {
            Log.d("OverclockingWidget","No device with id={"+deviceId+"} present in database!");
        }
    }

    private static boolean isNetworkAvailable(Context context) {
        Log.d("OverclockingWidget","Checking if network is available");
        final ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            Log.d("OverclockingWidget","Network is available and connected.");
            return true;
        }
        return false;
    }

    private static boolean isWiFiAvailable(Context context) {
        final ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo wifiInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return wifiInfo != null && wifiInfo.isConnected();
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        if (this.mHostsDAO == null) {
            this.mHostsDAO = new HostsDAO(context);
            mHostsDAO.open();
        }
        // There may be multiple widgets active, so update all of them
        final int N = appWidgetIds.length;
        for (int i = 0; i < N; i++) {
            updateAppWidget(context, appWidgetIds[i], mHostsDAO, false);
        }
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        // handle hiding/showing of information based on available screen size here...
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // When the user deletes the widget, delete the preference associated with it.
        final int N = appWidgetIds.length;
        for (int i = 0; i < N; i++) {
            SystemInformationWidgetConfigureActivity.deleteDevicePref(context, appWidgetIds[i]);
            SystemInformationWidgetConfigureActivity.removeAlarm(context, appWidgetIds[i]);
        }
    }

    @Override
    public void onEnabled(Context context) {
        ComponentName thisAppWidget = new ComponentName(context.getPackageName(), SystemInformationWidget.class.getName());
        int[] appWidgetIds = AppWidgetManager.getInstance(context).getAppWidgetIds(thisAppWidget);

        for (int appWidgetId : appWidgetIds) {
            // Restoring scheduled alarm after reboot for every widget
            SystemInformationWidgetConfigureActivity.settingScheduledAlarm(context, appWidgetId);
        }
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();
        Log.d("OverclockingWidget","Receiving Intent: action={"+action+"}");
        if (action.equals(ACTION_WIDGET_UPDATE_ONE_MANUAL)) {
            int widgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
            if (widgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
                super.onReceive(context, intent);
            } else {
                if (this.mHostsDAO == null) {
                    this.mHostsDAO = new HostsDAO(context);
                    mHostsDAO.open();
                }
                updateAppWidget(context, widgetId, this.mHostsDAO, false);
            }
        } else {
            super.onReceive(context, intent);
        }
    }


}

