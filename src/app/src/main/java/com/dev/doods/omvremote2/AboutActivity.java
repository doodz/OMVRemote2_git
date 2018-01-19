package com.dev.doods.omvremote2;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.dev.doods.omvremote2.Billing.IabBroadcastReceiver;
import com.dev.doods.omvremote2.Billing.IabHelper;
import com.dev.doods.omvremote2.Billing.IabResult;
import com.dev.doods.omvremote2.Billing.Inventory;
import com.dev.doods.omvremote2.Billing.Purchase;

import net.hockeyapp.android.FeedbackManager;
import net.hockeyapp.android.Tracking;
import net.hockeyapp.android.utils.Util;

//import org.acra.ACRA;
//import org.acra.collector.CrashReportData;
//import org.acra.sender.ReportSender;
//import org.acra.sender.ReportSenderException;
//import org.acra.util.IOUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import Models.Errors;
import OMV.Base.AppCompatBaseActivity;
import inappbilling.BillingManager;
import utils.CallBackAsyncTask;
import utils.CallBackTask;
import utils.SendLogsTask;
import utils.SnackBarError;
//import static org.acra.ACRA.LOG_TAG;

public class AboutActivity extends AppCompatBaseActivity implements IabBroadcastReceiver.IabBroadcastListener {

    // The helper object
    IabHelper mHelper;
    // Provides purchase notification while this app is running
    IabBroadcastReceiver mBroadcastReceiver;

    private Button tryToPurchaseBtn;
    // Does the user have the premium upgrade?
    boolean mIsPremium = false;

    // Tracks the currently owned infinite gas SKU, and the options in the Manage dialog
    String mPremiumSku = "";
    // Will the subscription auto-renew?
    boolean mAutoRenewEnabled = false;
    // (arbitrary) request code for the purchase flow
    static final int RC_REQUEST = 10001;


    private TextView mVersionView;
    private TextView mVersionCodeView;
    private Button mSendLogBtn;
    private Button mSendFeedbackBtn;
    private WebView mWebView;
    Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mIsPremium = !MyApplicationBase.light;
        super.onCreate(savedInstanceState);
        setContentView(com.dev.doods.omvremote2.R.layout.activity_about);

        FeedbackManager.register(this);
        init();
        mVersionView = (TextView)  findViewById(R.id.tvVersion);
        mVersionCodeView= (TextView)  findViewById(R.id.tvCodeVersion);
        mSendLogBtn = (Button) findViewById(R.id.SendLogBtn);
        mSendFeedbackBtn =(Button) findViewById(R.id.SendFeedbackBtn);

        tryToPurchaseBtn =(Button) findViewById(R.id.tryToPurchaseBtn);
        tryToPurchaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tryToPurchaseUnlocker();
               //String str = BillingManager.getInstance().tryToPurchase(BillingManager.SKU_PREMIUM,true);
                //new SnackBarError(tryToPurchaseBtn,str);
            }
        });

        mSendLogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ACRA.getErrorReporter().handleException(null);
                sendFeedback();
            }
        });

        mSendFeedbackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FeedbackManager.showFeedbackActivity(AboutActivity.this);
            }
        });

        mWebView = (WebView) findViewById(R.id.webView);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);


        setClickableSpan();
        FindVersionCode();
        ParsingChangeLogs();
    }

    /***
     * User clicked the "Buy premium" button
     */
    public void tryToPurchaseUnlocker() {
        Log.d(TAG, "Buy unlocker button clicked.");

        if (mIsPremium) {
            complain("No need! You're subscribed to unlocker. Isn't that awesome?");
            return;
        }

        Log.d(TAG, "Launching purchase flow for unlocker.");

        /* TODO: for security, generate your payload here for verification. See the comments on
         *        verifyDeveloperPayload() for more info. Since this is a SAMPLE, we just use
         *        an empty string, but on a production app you should carefully generate this. */
        String payload = "";

        try {
            mHelper.launchPurchaseFlow(this,IabHelper.SKU_PREMUIM_YEARLY , RC_REQUEST,
                    mPurchaseFinishedListener, payload);
        } catch (IabHelper.IabAsyncInProgressException e) {
            complain("Error launching purchase flow. Another async operation in progress.");

        }
    }

    // Callback for when a purchase is finished
    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
        public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
            Log.d(TAG, "Purchase finished: " + result + ", purchase: " + purchase);

            // if we were disposed of in the meantime, quit.
            if (mHelper == null) return;

            if (result.isFailure()) {
                complain("Error purchasing: " + result);

                return;
            }
            if (!verifyDeveloperPayload(purchase)) {
                complain("Error purchasing. Authenticity verification failed.");

                return;
            }

            Log.d(TAG, "Purchase successful.");
             if (purchase.getSku().equals(IabHelper.SKU_PREMUIM_YEARLY)) {
                // bought the infinite gas subscription
                Log.d(TAG, "Unlocker subscription purchased.");
                alert("Thank you for subscribing to unlocker!");
                 mIsPremium = true;
                 MyApplicationBase.light = false;
                mAutoRenewEnabled = purchase.isAutoRenewing();
                mPremiumSku = purchase.getSku();

            }
        }
    };


    private void setClickableSpan()
    {
        TextView translate = (TextView)  findViewById(R.id.click_translate);


        SpannableString spannableString = new SpannableString(getString(R.string.action_click_translate));
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.getlocalization.com/OMV_REMOTE")));
            }
        };
        spannableString.setSpan(clickableSpan, spannableString.length() -20,
                spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        translate.setText(spannableString, TextView.BufferType.SPANNABLE);
        translate.setMovementMethod(LinkMovementMethod.getInstance());


        translate = (TextView)  findViewById(R.id.click_forum);


        spannableString = new SpannableString(getString(R.string.action_click_forum));
        clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://forum.openmediavault.org/index.php/Thread/15658-unofficial-OMV-APP-for-Android/")));
            }
        };
        spannableString.setSpan(clickableSpan, spannableString.length() -16,
                spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        translate.setText(spannableString, TextView.BufferType.SPANNABLE);
        translate.setMovementMethod(LinkMovementMethod.getInstance());


    }

    private static String getValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = nodeList.item(0);
        return node.getNodeValue();
    }

    private void FindVersionCode()
    {
        try {

            PackageInfo pinfo = getPackageManager().getPackageInfo(getPackageName(), 0);

           String code = ""+pinfo.versionCode;
            String name = pinfo.versionName;

            mVersionView.setText(name);
            mVersionCodeView.setText(code);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


    }

    private void ParsingChangeLogs()
    {
        try {

            InputStream is = getAssets().open("changeslogs.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(is);

            Element element=doc.getDocumentElement();
            element.normalize();

            NodeList nList = doc.getElementsByTagName("release");

            StringBuilder sb = new StringBuilder();
            sb.append("<html><body>");
            sb.append("<head>");
            sb.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"bootstrap.css\" />");
            sb.append("</head>");


            for (int i=0; i<nList.getLength(); i++) {

                Node node = nList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element2 = (Element) node;

                    String version = element2.getAttribute("version");

                    sb.append("<h1>VERSION ");
                    sb.append(version);
                    sb.append("</h1>");
                    NodeList nodeList = element2.getElementsByTagName("change");
                    sb.append("<ul>");
                    for (int j=0; j<nodeList.getLength(); j++) {

                        Node changeNode = nodeList.item(j);
                        sb.append("<li>");
                        sb.append(changeNode.getChildNodes().item(0).getNodeValue());
                        sb.append("</li>");

                    }
                    sb.append(" </ul>");
                    element2.getAttribute("versioncode");
                    //tv1.setText(tv1.getText()+"\nName : " + getValue("name", element2)+"\n");
                    //tv1.setText(tv1.getText()+"Surname : " + getValue("surname", element2)+"\n");
                    //tv1.setText(tv1.getText()+"-----------------------");
                }
            }

            sb.append("<script src=\"bootstrap.js\"></script>");
            sb.append("</body></html>");
            mWebView.loadDataWithBaseURL("file:///assets/", sb.toString(), "text/html", "UTF-8", null);
        }
        catch (Exception e) {e.printStackTrace();}

    }
    protected void init() {
        // Create the helper, passing it our context and the public key to verify signatures with
        Log.d(TAG, "Creating IAB helper.");
        mHelper = new IabHelper(this, IabHelper.base64EncodedPublicKey);

        // enable debug logging (for a production application, you should set this to false).
        mHelper.enableDebugLogging(false);

        // Start setup. This is asynchronous and the specified listener
        // will be called once setup completes.
        Log.d(TAG, "Starting setup.");
        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {
                Log.d(TAG, "Setup finished.");

                if (!result.isSuccess()) {
                    // Oh noes, there was a problem.
                    complain("Problem setting up in-app billing: " + result);
                    return;
                }

                // Have we been disposed of in the meantime? If so, quit.
                if (mHelper == null) return;

                // Important: Dynamically register for broadcast messages about updated purchases.
                // We register the receiver here instead of as a <receiver> in the Manifest
                // because we always call getPurchases() at startup, so therefore we can ignore
                // any broadcasts sent while the app isn't running.
                // Note: registering this listener in an Activity is a bad idea, but is done here
                // because this is a SAMPLE. Regardless, the receiver must be registered after
                // IabHelper is setup, but before first call to getPurchases().
                mBroadcastReceiver = new IabBroadcastReceiver(AboutActivity.this);
                IntentFilter broadcastFilter = new IntentFilter(IabBroadcastReceiver.ACTION);
                registerReceiver(mBroadcastReceiver, broadcastFilter);

                // IAB is fully set up. Now, let's get an inventory of stuff we own.
                Log.d(TAG, "Setup successful. Querying inventory.");
                try {
                    mHelper.queryInventoryAsync(mGotInventoryListener);
                } catch (IabHelper.IabAsyncInProgressException e) {
                    complain("Error querying inventory. Another async operation in progress.");
                }
            }
        });
    }


    // Listener that's called when we finish querying the items and subscriptions we own
    IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
        public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
            Log.d(TAG, "Query inventory finished.");

            // Have we been disposed of in the meantime? If so, quit.
            if (mHelper == null) return;

            // Is it a failure?
            if (result.isFailure()) {
                complain("Failed to query inventory: " + result);
                return;
            }

            Log.d(TAG, "Query inventory was successful.");

            /*
             * Check for items we own. Notice that for each purchase, we check
             * the developer payload to see if it's correct! See
             * verifyDeveloperPayload().
             */

            // Do we have the premium upgrade?
            Purchase premiumPurchaseYearly = inventory.getPurchase(IabHelper.SKU_PREMUIM_YEARLY);
            mIsPremium = (premiumPurchaseYearly != null && verifyDeveloperPayload(premiumPurchaseYearly));
            Log.d(TAG, "User is " + (mIsPremium ? "PREMIUM" : "NOT PREMIUM"));
            MyApplicationBase.light = !mIsPremium;

            if (premiumPurchaseYearly != null && premiumPurchaseYearly.isAutoRenewing()) {
                mPremiumSku = IabHelper.SKU_PREMUIM_YEARLY;
                mAutoRenewEnabled = true;
            }

            Log.d(TAG, "Initial inventory query finished; enabling main UI.");
        }
    };

    /** Verifies the developer payload of a purchase. */
    boolean verifyDeveloperPayload(Purchase p) {
        String payload = p.getDeveloperPayload();

        /*
         * TODO: verify that the developer payload of the purchase is correct. It will be
         * the same one that you sent when initiating the purchase.
         *
         * WARNING: Locally generating a random string when starting a purchase and
         * verifying it here might seem like a good approach, but this will fail in the
         * case where the user purchases an item on one device and then uses your app on
         * a different device, because on the other device you will not have access to the
         * random string you originally generated.
         *
         * So a good developer payload has these characteristics:
         *
         * 1. If two different users purchase an item, the payload is different between them,
         *    so that one user's purchase can't be replayed to another user.
         *
         * 2. The payload must be such that you can verify it even when the app wasn't the
         *    one who initiated the purchase flow (so that items purchased by the user on
         *    one device work on other devices owned by the user).
         *
         * Using your own server to store and verify developer payloads across app
         * installations is recommended.
         */

        return true;
    }

    public void launchPurchase() {
        BillingManager.getInstance().setActivity(this);
        BillingManager.getInstance().Initialize();
        BillingManager.getInstance().tryToPurchase(BillingManager.SKU_PREMIUM,true);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        BillingManager.getInstance().onActivityResult(requestCode, resultCode, data);
    }


    private void sendFeedback()
    {
        if (!Util.isConnectedToNetwork(this)) {
            Snackbar.make(mSendLogBtn,R.string.error_no_network_message, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

            return;
        }


        AsyncTask task = new SendLogsTask(this);
        new CallBackAsyncTask(task, new CallBackTask() {
            @Override
            public void handleMessageError(@Nullable Errors error) {
                new SnackBarError(mSendLogBtn,error.getMessage(),false);
            }

            @Override
            public void handleFinich() {
                Snackbar.make(mSendLogBtn,R.string.send, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }).run();

    }

    @Override
    public void receivedBroadcast() {
        // Received a broadcast notification that the inventory of items has changed
        Log.d(TAG, "Received broadcast notification. Querying inventory.");
        try {
            mHelper.queryInventoryAsync(mGotInventoryListener);
        } catch (IabHelper.IabAsyncInProgressException e) {
            complain("Error querying inventory. Another async operation in progress.");
        }
    }
    // We're being destroyed. It's important to dispose of the helper here!
    @Override
    public void onDestroy() {
        super.onDestroy();

        // very important:
        if (mBroadcastReceiver != null) {
            unregisterReceiver(mBroadcastReceiver);
        }

        // very important:
        Log.d(TAG, "Destroying helper.");
        if (mHelper != null) {
            mHelper.disposeWhenFinished();
            mHelper = null;
        }
    }

}
