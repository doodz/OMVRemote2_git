package com.dev.doods.base;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.NativeExpressAdView;
//import com.microsoft.azure.mobile.MobileCenter;
//import com.microsoft.azure.mobile.analytics.Analytics;
//import com.microsoft.azure.mobile.crashes.Crashes;

import net.hockeyapp.android.CrashManager;
import net.hockeyapp.android.metrics.MetricsManager;
import net.hockeyapp.android.utils.HockeyLog;
import net.hockeyapp.android.utils.Util;

//import org.acra.ACRA;
//import org.acra.ReportField;
//import org.acra.ReportingInteractionMode;
//import org.acra.annotation.ReportsCrashes;

import OMV.Classe.MyCustomCrashManagerListener;
/**
 * Created by thiba on 21/10/2016.
 */

/*@ReportsCrashes(mailTo = "doods.dev@gmail.com",
        customReportContent = {ReportField.APP_VERSION_CODE, ReportField.APP_VERSION_NAME, ReportField.ANDROID_VERSION, ReportField.PHONE_MODEL, ReportField.CUSTOM_DATA, ReportField.STACK_TRACE, ReportField.LOGCAT },
        mode = ReportingInteractionMode.TOAST,
        resToastText = R.string.crash_toast_text)*/
public class MyApplicationBase extends Application {


    private static Context context;
    public static AdRequest mAdRequest;
    public static AdView mAdViewSmall;
    public static AdView mAdViewSmallHaeder;
    public static boolean light;
    public static NativeExpressAdView mNativeExpressAdView;
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override

    public void onCreate() {
        super.onCreate();


        //ACRA.init(this);
        //MobileCenter.setLogLevel(Log.VERBOSE);

        MyApplicationBase.context = getApplicationContext();
        checkForCrashes();
        HockeyLog.setLogLevel(Log.DEBUG);
        //MobileCenter.start(this, "e7034db4-8db6-474a-a022-66771fb1e3f6",
        //        Analytics.class, Crashes.class);
        MetricsManager.register(this,"7076800147a94538934dfa2e5e0ee2ad");
        MetricsManager.trackEvent("GET_LOGS_FILE");

        if(light) {
            MobileAds.initialize(getApplicationContext(), "ca-app-pub-4922361220283829/4711179794");
            MobileAds.initialize(getApplicationContext(), "ca-app-pub-4922361220283829/7664646197");
            mAdRequest = new AdRequest.Builder().build();

            mAdViewSmall = new AdView(this);
            mAdViewSmall.setAdSize(AdSize.BANNER);
            mAdViewSmall.setAdUnitId("ca-app-pub-4922361220283829/4711179794");
            mAdViewSmall.loadAd(new AdRequest.Builder().build());

            mAdViewSmallHaeder = new AdView(this);
            mAdViewSmallHaeder.setAdSize(AdSize.BANNER);
            mAdViewSmallHaeder.setAdUnitId("ca-app-pub-4922361220283829/3076009396");
            mAdViewSmallHaeder.loadAd(new AdRequest.Builder().build());

            mNativeExpressAdView = new NativeExpressAdView(this);
            mNativeExpressAdView.setAdSize(new AdSize(360, 132));
            mNativeExpressAdView.setAdUnitId("ca-app-pub-4922361220283829/7664646197");
            mNativeExpressAdView.loadAd(new AdRequest.Builder().build());
        }
    }


    private void checkForCrashes() {

        try {
            Bundle bundle = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA).metaData;
           String str = (String) bundle.get("omg.doods.dev.omremote.base.appIdentifier");
            light = str.equals("light");
        } catch (PackageManager.NameNotFoundException e) {
            light = false;
            e.printStackTrace();
        }
        String appIdentifier = Util.getAppIdentifier(context);

        CrashManager.register(context, "7076800147a94538934dfa2e5e0ee2ad", new MyCustomCrashManagerListener());
        //CrashManager.register(this);
    }


    public static Context getAppContext() {
        return MyApplicationBase.context;
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

}