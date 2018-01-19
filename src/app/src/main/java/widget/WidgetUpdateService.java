package widget;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import DAL.HostsDAO;

import static android.appwidget.AppWidgetManager.EXTRA_APPWIDGET_ID;
import static android.appwidget.AppWidgetManager.INVALID_APPWIDGET_ID;

/**
 * Created by Ividata7 on 05/02/2017.
 */

public class WidgetUpdateService extends IntentService {


    public WidgetUpdateService() {
        super("WidgetUpdateService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        int appWidgetId = intent.getIntExtra(EXTRA_APPWIDGET_ID,
                INVALID_APPWIDGET_ID);
        if (appWidgetId != INVALID_APPWIDGET_ID) {
            Log.d("WidgetUpdateService","Received alarm intent for update of Widget[ID={"+appWidgetId+"}].");

            HostsDAO hostsDAO =  new HostsDAO(getApplicationContext());
            hostsDAO.open();
            SystemInformationWidget.updateAppWidget(getApplicationContext(), appWidgetId,hostsDAO, true);
        }
    }
}
