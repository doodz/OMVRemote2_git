package com.dev.doods.omvremote2;

import android.app.Application;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.dev.doods.omvremote2.Billing.IabBroadcastReceiver;
import com.dev.doods.omvremote2.Billing.IabHelper;
import com.dev.doods.omvremote2.Billing.IabResult;
import com.dev.doods.omvremote2.Billing.Inventory;
import com.dev.doods.omvremote2.Billing.Purchase;
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

import static com.dev.doods.omvremote2.Billing.IabHelper.SKU_PREMUIM_YEARLY;

/**
 * Created by thiba on 21/10/2016.
 */

/*@ReportsCrashes(mailTo = "doods.dev@gmail.com",
        customReportContent = {ReportField.APP_VERSION_CODE, ReportField.APP_VERSION_NAME, ReportField.ANDROID_VERSION, ReportField.PHONE_MODEL, ReportField.CUSTOM_DATA, ReportField.STACK_TRACE, ReportField.LOGCAT },
        mode = ReportingInteractionMode.TOAST,
        resToastText = R.string.crash_toast_text)*/
public class MyApplicationBase extends Application {

    // The helper object
    IabHelper mHelper;
    private static Context context;
    public static AdRequest mAdRequest;
    public static AdView mAdViewSmall;
    public static AdView mAdViewSmallHaeder;
    public static boolean light;
    public boolean mIsPremium;
    public static NativeExpressAdView mNativeExpressAdView;
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override

    public void onCreate() {
        super.onCreate();
        checkForPremium();

        //ACRA.init(this);
        //MobileCenter.setLogLevel(Log.VERBOSE);

        MyApplicationBase.context = getApplicationContext();
        checkForCrashes();
        HockeyLog.setLogLevel(Log.DEBUG);
        //MobileCenter.start(this, "e7034db4-8db6-474a-a022-66771fb1e3f6",
        //        Analytics.class, Crashes.class);
        MetricsManager.register(this);
        MetricsManager.trackEvent("GET_LOGS_FILE");

       // if(light) {
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
       // }
    }




    private void checkForPremium()
    {

        Log.d("MyApplicationBase", "Creating IAB helper.");
       mHelper = new IabHelper(this, IabHelper.base64EncodedPublicKey);

        // enable debug logging (for a production application, you should set this to false).
        mHelper.enableDebugLogging(false);

        // Start setup. This is asynchronous and the specified listener
        // will be called once setup completes.
        Log.d("MyApplicationBase", "Starting setup.");
        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {
                Log.d("MyApplicationBase", "Setup finished.");

                if (!result.isSuccess()) {
                    // Oh noes, there was a problem.
                    Log.i("MyApplicationBase", "Problem setting up in-app billing: " + result);

                    return;
                }

                // Have we been disposed of in the meantime? If so, quit.
                if (mHelper == null) return;



                // IAB is fully set up. Now, let's get an inventory of stuff we own.
                Log.d("MyApplicationBase", "Setup successful. Querying inventory.");
                try {
                    mHelper.queryInventoryAsync(mGotInventoryListener);
                } catch (IabHelper.IabAsyncInProgressException e) {
                    Log.i("MyApplicationBase","Error querying inventory. Another async operation in progress.");
                }
            }
        });
    }

    // Listener that's called when we finish querying the items and subscriptions we own
    IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
        public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
            Log.d("MyApplicationBase", "Query inventory finished.");

            // Have we been disposed of in the meantime? If so, quit.
            if (mHelper == null) return;

            // Is it a failure?
            if (result.isFailure()) {
                Log.i("MyApplicationBase","Failed to query inventory: " + result);
                return;
            }

            Log.d("MyApplicationBase", "Query inventory was successful.");

            /*
             * Check for items we own. Notice that for each purchase, we check
             * the developer payload to see if it's correct! See
             * verifyDeveloperPayload().
             */

            // Do we have the premium upgrade?
            Purchase premiumPurchaseYearly = inventory.getPurchase(SKU_PREMUIM_YEARLY);
            mIsPremium = (premiumPurchaseYearly != null);
            Log.d("MyApplicationBase", "User is " + (mIsPremium ? "PREMIUM" : "NOT PREMIUM"));

            light = !mIsPremium;

            Log.d("MyApplicationBase", "Initial inventory query finished; enabling main UI.");
        }
    };

    private void checkForCrashes() {

        try {
            Bundle bundle = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA).metaData;
           String str = (String) bundle.get("omg.doods.dev.omremote.base.appIdentifier");
            //light = str.equals("light");
        } catch (PackageManager.NameNotFoundException e) {
            //light = false;
            e.printStackTrace();
        }
        String appIdentifier = Util.getAppIdentifier(context);

        if (!BuildConfig.DEBUG) {
            CrashManager.register(context, appIdentifier, new MyCustomCrashManagerListener());
            //CrashManager.register(this);
        }


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