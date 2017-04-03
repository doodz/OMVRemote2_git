package widget;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.dev.doods.base.R;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

import Client.Host;
import DAL.HostsDAO;
import DAL.HotsDatabaseHelper;
import OMV.Classe.AppCompatBaseActivity;

public class SystemInformationWidgetConfigureActivity extends AppCompatBaseActivity implements AdapterView.OnItemSelectedListener  {

    public static void removeAlarm(Context c, int appWidgetId) {
        Log.d("WidgetConfigure","Removing alarm for Widget[ID={"+appWidgetId+"}].");
        final Uri intentUri = getPendingIntentUri(appWidgetId);
        final PendingIntent pendingIntent = getServicePendingIntent(c, appWidgetId, intentUri, ACTION_WIDGET_UPDATE_ONE);
        final AlarmManager alarmManager = (AlarmManager) c.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }

    static void deleteDevicePref(Context context, int appWidgetId) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.remove(PREF_PREFIX_KEY + appWidgetId);
        prefs.remove(prefKey(PREF_UPDATE_INTERVAL_SUFFIX, appWidgetId));
        prefs.remove(prefKey(PREF_SHOW_LOAD_SUFFIX, appWidgetId));
        prefs.remove(prefKey(PREF_SHOW_ARM_SUFFIX, appWidgetId));
        prefs.remove(prefKey(PREF_SHOW_TEMP_SUFFIX, appWidgetId));
        prefs.remove(prefKey(PREF_UPDATE_ONLY_ON_WIFI, appWidgetId));
        prefs.apply();
    }

    protected static PendingIntent getServicePendingIntent(Context context, int appWidgetId, Uri uri, String action) {
        final Intent intent = new Intent(context, WidgetUpdateService.class);
        intent.setAction(action);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(uri);
        return PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    static void settingScheduledAlarm(Context context, int appWidgetId) {
        Log.d("WidgetConfigure","Check if Widget[ID={"+appWidgetId+"}] needs a new Alarm.");
        if (isInitiated(appWidgetId, context)) {
            int updateIntervalInMinutes = SystemInformationWidgetConfigureActivity.loadUpdateInterval(context, appWidgetId);
            if (updateIntervalInMinutes > 0) {
                long updateIntervalMillis = updateIntervalInMinutes * 60 * 1000;
                // Setting alarm via AlarmManager
                AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                PendingIntent pintent = getServicePendingIntent(context, appWidgetId, getPendingIntentUri(appWidgetId), ACTION_WIDGET_UPDATE_ONE);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + updateIntervalMillis, updateIntervalMillis, pintent);
                Log.d("WidgetConfigure","Added alarm for periodic updates of Widget[ID={"+appWidgetId+"}], update interval: {"+updateIntervalMillis+"} ms.");
            } else {
                Log.d("WidgetConfigure","No periodic updates for Widget[ID={"+appWidgetId+"}].");
            }
        } else {
            Log.d("WidgetConfigure","Widget[ID={"+appWidgetId+"}] not fully initiated, no alarm needed.");
        }
    }
    static boolean loadShowStatus(Context context, String key, int appWidgetId) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        return prefs.getBoolean(prefKey(key, appWidgetId), true);
    }

    static Long loadDeviceId(Context context, int appWidgetId) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        Long deviceId = prefs.getLong(PREF_PREFIX_KEY + appWidgetId, 0);
        if (deviceId != 0) {
            return deviceId;
        } else {
            return null;
        }
    }

    static boolean loadOnlyOnWifi(Context context, int appWidgetId) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        return prefs.getBoolean(prefKey(PREF_UPDATE_ONLY_ON_WIFI, appWidgetId), false);
    }

    static int loadUpdateInterval(Context context, int appWidgetId) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        return prefs.getInt(prefKey(PREF_UPDATE_INTERVAL_SUFFIX, appWidgetId), 5);
    }
    /**
     * @param appWidgetId the app widget id
     * @param context     the context
     * @return if this widget has associated preferences (it it fully configured then)
     */
    static boolean isInitiated(int appWidgetId, Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        return prefs.contains(PREF_PREFIX_KEY + appWidgetId);
    }
    protected static Uri getPendingIntentUri(int appWidgetId) {
        return Uri.withAppendedPath(Uri.parse(URI_SCHEME + "://widget/id/"), String.valueOf(appWidgetId));
    }
    public static final String ACTION_WIDGET_UPDATE_ONE = "updateOneWidget";
    public static final String PREF_SHOW_TEMP_SUFFIX = "_temp";
    public static final String PREF_SHOW_ARM_SUFFIX = "_arm";
    public static final String PREF_SHOW_LOAD_SUFFIX = "_load";
    public static final String PREF_SHOW_UPTIME_SUFFIX = "_uptime";
    public static final String PREF_SHOW_CPU_SUFFIX = "_cpu";

    public static final String PREF_SHOW_MEMORY_SUFFIX = "_memory";
    private static final String PREFS_NAME = "com.dev.doods.omg.doods.dev.omremote.base.SystemInformationWidget";
    private static final String PREF_PREFIX_KEY = "appwidget_";
    private static final String PREF_UPDATE_ONLY_ON_WIFI = "_onlywifi";
    private static final String URI_SCHEME = "Plugins";

    private static final String PREF_UPDATE_INTERVAL_SUFFIX = "_interval";
    private static final String UPDATE_YES = "yes";
    private static final String UPDATE_WIFI = "wifi";
    private static final String UPDATE_NO = "no";
    // corresponds to array/widget_auto_update
    private static final String[] autoUpdate = {UPDATE_YES, UPDATE_WIFI, UPDATE_NO};
    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;



    private Spinner widgetPiSpinner;
    private EditText textEditUpdateInterval;
    private Spinner widgetUpdateSpinner;
    private Spinner widgetUpdateIntervalSpinner;
    private TextInputLayout linLayoutCustomInterval;
    private CheckBox checkBoxUptime;
    private CheckBox checkBoxCpu;
    private CheckBox checkBoxRam;
    private LinearLayout linLayoutUpdateInterval;

    private HotsDatabaseHelper hotsDatabaseHelper;

    private int[] updateIntervalsMinutes;

    private List<Host> _mLstHost;
    private HostsDAO datasource;
    private void bindViews()
    {

          widgetPiSpinner = (Spinner)findViewById(R.id.widgetPiSpinner);
          textEditUpdateInterval= (EditText)findViewById(R.id.textEditUpdateInterval);
          widgetUpdateSpinner= (Spinner)findViewById(R.id.widgetUpdateSpinner);
          widgetUpdateIntervalSpinner= (Spinner)findViewById(R.id.widgetUpdateIntervalSpinner);
          linLayoutCustomInterval= (TextInputLayout)findViewById(R.id.linLayoutCustomUpdateInterval);
          checkBoxUptime= (CheckBox)findViewById(R.id.checkBoxUptime);
          checkBoxCpu= (CheckBox)findViewById(R.id.checkBoxCpu);
          checkBoxRam = (CheckBox)findViewById(R.id.checkBoxRam);
          linLayoutUpdateInterval= (LinearLayout)findViewById(R.id.linLayoutUpdateInterval);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_information_widget_configure);
        bindViews();
        // Set the result to CANCELED.  This will cause the widget host to cancel
        // out of the widget placement if the user presses the back button.
        setResult(RESULT_CANCELED);

        // Find the widget id from the intent.
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        // If this activity was started with an intent without an app widget ID, finish with an error.
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
            return;
        }

        this.getSupportActionBar().setTitle(getString(R.string.widget_configure_title));
        hotsDatabaseHelper = new HotsDatabaseHelper(this);

        datasource = new HostsDAO(this);
        datasource.open();

        _mLstHost = datasource.getAllHosts();
        final int deviceCount = initSpinners();
        if (deviceCount == 0) {
            // show Toast to add a device first
            Toast.makeText(this, getString(R.string.widget_add_no_device), Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        linLayoutCustomInterval.setVisibility(View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_infosystem_widget_configure, menu);
        return true;
    }

    /**
     * @return the device count
     */
    private int initSpinners() {

        ArrayAdapter<Host> deviceSpinnerAdapter = new ArrayAdapter<Host>(SystemInformationWidgetConfigureActivity.this,
                android.R.layout.simple_spinner_item, _mLstHost);
        // Device Spinner
        //final DeviceSpinnerAdapter deviceSpinnerAdapter = new DeviceSpinnerAdapter(OverclockingWidgetConfigureActivity.this, deviceDbHelper.getFullDeviceCursor(), true);
        widgetPiSpinner.setAdapter(deviceSpinnerAdapter);
        // Auto update
        final ArrayAdapter<CharSequence> autoUpdateAdapter = ArrayAdapter.createFromResource(SystemInformationWidgetConfigureActivity.this, R.array.widget_auto_updates, android.R.layout.simple_spinner_item);
        autoUpdateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        widgetUpdateSpinner.setAdapter(autoUpdateAdapter);
        widgetUpdateSpinner.setOnItemSelectedListener(this);
        // Update interval
        this.updateIntervalsMinutes = this.getResources().getIntArray(R.array.widget_update_intervals_values);
        final ArrayAdapter<CharSequence> updateIntervalAdapter = ArrayAdapter.createFromResource(SystemInformationWidgetConfigureActivity.this, R.array.widget_update_intervals, android.R.layout.simple_spinner_item);
        updateIntervalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        widgetUpdateIntervalSpinner.setAdapter(updateIntervalAdapter);
        widgetUpdateIntervalSpinner.setOnItemSelectedListener(this);
        return deviceSpinnerAdapter.getCount();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() ==R.id.menu_save) {

            final Context context = SystemInformationWidgetConfigureActivity.this;

            Host selectedItem = (Host)widgetPiSpinner.getSelectedItem();
            Log.i("WidgetConfigure","Selected Device - Item ID = {"+selectedItem+"}");


            int updateIntervalInMinutes = 0;
            boolean onlyOnWifi = false;
            if (!autoUpdate[widgetUpdateSpinner.getSelectedItemPosition()].equals(UPDATE_NO)) {
                if (updateIntervalsMinutes[widgetUpdateIntervalSpinner.getSelectedItemPosition()] == -1) {
                    String s = textEditUpdateInterval.getText().toString().trim();
                    if (s== null ||  StringUtils.isEmpty(s) || StringUtils.isBlank(s)) {
                        textEditUpdateInterval.setError(getString(R.string.widget_update_interval_error));
                        return super.onOptionsItemSelected(item);
                    }
                    updateIntervalInMinutes = Integer.parseInt(s);
                    if (updateIntervalInMinutes == 0) {
                        textEditUpdateInterval.setError(getString(R.string.widget_update_interval_zero));
                        return super.onOptionsItemSelected(item);
                    }
                } else {
                    updateIntervalInMinutes = updateIntervalsMinutes[widgetUpdateIntervalSpinner.getSelectedItemPosition()];
                }
            }
            if (autoUpdate[widgetUpdateSpinner.getSelectedItemPosition()].equals(UPDATE_WIFI)) {
                onlyOnWifi = true;
            }
            // save Device ID in prefs
            saveChosenDevicePref(context, mAppWidgetId, selectedItem, updateIntervalInMinutes, onlyOnWifi,
                    checkBoxCpu.isChecked(), checkBoxRam.isChecked(), checkBoxUptime.isChecked());

            settingScheduledAlarm(context, mAppWidgetId);

            // It is the responsibility of the configuration activity to update the app widget
            SystemInformationWidget.updateAppWidget(context, mAppWidgetId, datasource, false);

            // Make sure we pass back the original appWidgetId
            Intent resultValue = new Intent();
            resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
            setResult(RESULT_OK, resultValue);
            finish();
        }
        else
        {
            return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        final int updateId = widgetUpdateSpinner.getId();
        final int updateIntervalId = widgetUpdateIntervalSpinner.getId();
        if (parent.getId() == updateId) {
            Log.d("WidgetConfigure","Update-Spinner: Item pos {"+position+"}, id {"+id+"} selected.");
            if (autoUpdate[position].equals(UPDATE_NO)) {
                linLayoutUpdateInterval.setVisibility(View.GONE);
            } else {
                linLayoutUpdateInterval.setVisibility(View.VISIBLE);
            }
        } else if (parent.getId() == updateIntervalId) {
            Log.d("WidgetConfigure","Interval-Spinner: Item pos {"+position+"}, id {"+id+"} selected.");
            int updateIntervalInMinutes = updateIntervalsMinutes[position];
            if (updateIntervalInMinutes == -1) {
                // custom time interval
                linLayoutCustomInterval.setVisibility(View.VISIBLE);
            } else {
                linLayoutCustomInterval.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // nothing to do
    }

    /**
     * @param context
     * @param appWidgetId
     * @param server    ID of the chosen server
     */
    static void saveChosenDevicePref(Context context, int appWidgetId, Host server, int updateInterval, boolean onlyOnWifi,
                                     boolean showCpu, boolean showUptime, boolean showMemory) {
        Log.i("SystemInformationWidget","Saving new OverclockingWidget. Settings - Update interval: {"+updateInterval+"} - Only on Wifi: {"+onlyOnWifi+"}" +
                " - CPU: {"+showCpu+"} - Uptime: {"+showUptime+"} - RAM: {"+showMemory+"}");
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.putLong(PREF_PREFIX_KEY + appWidgetId, server.getId());
        prefs.putInt(

                prefKey(FINGERPRINT_SERVICE, appWidgetId), updateInterval

        );
        prefs.putBoolean(

                prefKey(PREF_SHOW_CPU_SUFFIX, appWidgetId), showCpu

        );
        prefs.putBoolean(

                prefKey(PREF_SHOW_UPTIME_SUFFIX, appWidgetId), showUptime

        );

        prefs.putBoolean(

                prefKey(PREF_SHOW_MEMORY_SUFFIX, appWidgetId), showMemory

        );
        prefs.putBoolean(

                prefKey(PREF_UPDATE_ONLY_ON_WIFI, appWidgetId), onlyOnWifi

        );
        prefs.apply();
    }

    static String prefKey(String key, int appWidgetId) {
        return PREF_PREFIX_KEY + appWidgetId + key;
    }
}
