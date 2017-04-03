package widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import com.dev.doods.omvremote2.HomeActivity;
import com.dev.doods.omvremote2.R;

import Client.Host;

/**
 * Created by Ividata7 on 04/02/2017.
 */

public class SystemInformationWidgetView {

    private static final String ACTION_WIDGET_UPDATE_ONE_MANUAL = "updateOneWidgetManual";
    private static final String URI_SCHEME = "Plugins";

    public static RemoteViews initDefaultView(Context context, int appWidgetId, Host host, boolean showCpu, boolean showUptime, boolean showMemory) {
        Log.d("SystemInfoWidgetView ","Initiating default view for Widget[ID={"+appWidgetId+"}].");
        final AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        final RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.system_information_widget);
        views.setOnClickPendingIntent(R.id.buttonRefresh, getSelfPendingIntent(context, appWidgetId, ACTION_WIDGET_UPDATE_ONE_MANUAL));
        PendingIntent activityIntent = getActivityIntent(context);
        views.setOnClickPendingIntent(R.id.linLayoutName, activityIntent);
        views.setOnClickPendingIntent(R.id.linLayoutCpu, activityIntent);
        views.setOnClickPendingIntent(R.id.linLayoutInfo, activityIntent);
        views.setOnClickPendingIntent(R.id.linLayoutMem, activityIntent);
        views.setTextViewText(R.id.textDeviceValue, host.getName());
        views.setTextViewText(R.id.textDeviceUserHost, String.format("%s@%s", host.getUser(), host.getAddr()));

        views.setViewVisibility(R.id.linLayoutCpu, showCpu ? View.VISIBLE : View.GONE);
        views.setViewVisibility(R.id.linLayoutInfo, showUptime ? View.VISIBLE : View.GONE);
        views.setViewVisibility(R.id.linLayoutMem, showMemory ? View.VISIBLE : View.GONE);

        views.setProgressBar(R.id.progressBarMemory, 100, 0, false);
        views.setProgressBar(R.id.progressBarCpuValue, 100, 0, false);
        appWidgetManager.updateAppWidget(appWidgetId, views);
        return views;
    }

    public static void startRefreshing(RemoteViews views, Context context, int appWidgetId) {
        Log.d("SystemInfoWidgetView ","Showing refresh view for Widget[ID={"+appWidgetId+"}].");
        final AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        views.setViewVisibility(R.id.textStatusValue, View.GONE);
        views.setViewVisibility(R.id.buttonRefresh, View.GONE);
        views.setViewVisibility(R.id.refreshProgressBar, View.VISIBLE);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    public static RemoteViews initNoPermissionView(Context context, int appWidgetId) {
        Log.d("SystemInfoWidgetView ","Showing no permission view for Widget[ID={"+appWidgetId+"}]." );
        final AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        final RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.system_information_widget_no_read_permission);
        appWidgetManager.updateAppWidget(appWidgetId, views);
        return views;
    }

    public static void stopRefreshing(RemoteViews views, Context context, int appWidgetId) {
        Log.d("SystemInfoWidgetView ","Stopping refresh view for Widget[ID={"+appWidgetId+"}]." );
        final AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        views.setViewVisibility(R.id.buttonRefresh, View.VISIBLE);
        views.setViewVisibility(R.id.refreshProgressBar, View.GONE);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    public static RemoteViews initRemovedView(Context context, int appWidgetId) {
        Log.d("SystemInfoWidgetView ","Showing removed view for Widget[ID={"+appWidgetId+"}].");
        final AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        final RemoteViews removedView = new RemoteViews(context.getPackageName(), R.layout.system_information_widget);
        appWidgetManager.updateAppWidget(appWidgetId, removedView);
        return removedView;
    }

    private static PendingIntent getSelfPendingIntent(Context context, int appWidgetId, String action) {
        final Uri data = getPendingIntentUri(appWidgetId);
        return getSelfPendingIntent(context, appWidgetId, data, action);
    }

    private static PendingIntent getSelfPendingIntent(Context context, int appWidgetId, Uri uri, String action) {
        final Intent intent = new Intent(context, SystemInformationWidget.class);
        intent.setAction(action);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(uri);
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private static Uri getPendingIntentUri(int appWidgetId) {
        return Uri.withAppendedPath(Uri.parse(URI_SCHEME + "://widget/id/"), String.valueOf(appWidgetId));
    }

    private static PendingIntent getActivityIntent(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        return pendingIntent;
    }
}
