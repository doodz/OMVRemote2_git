package inappbilling;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;

import com.android.vending.billing.IInAppBillingService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import utils.SnackBarError;

/**
 * Created by Ividata7 on 15/03/2017.
 */

public class BillingManager {


    private String ApplicationLicenseKey="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEArZ2SPJvpez+aBB4ByCnELEqcCZhNMEHpb5EwoxpjZBstnVl+bUFvNAwgFO7HSoqGCGGUnsD5COqJwqv96nEeyUxlQ0rjVQ6Vf1ZvbKTElQsN8sYskZpkvIo4KLadq6pJnNc8dOwCvnwUqKSYwBiP+Cbw1Y0SRQ14duXfILUAhhpTAjAfPIQN1uj5gNExU57e0R5kgZpOzVTdF90BN/0+4xz1f1OJKqFlYJ0INneTXLNMwNLLA3QIaC/zx7UC0mi590l2bl+xP702babu1+bsQ08z+dsvzMIR/962BVTliWXuGksUdYgXgHBH4ErU2m8kKoUDUtYJ2ex6pqHWYMtYWQIDAQAB";
    public static final String SKU_PREMIUM = "unlocker_pro";
    private static BillingManager ourInstance = new BillingManager();
    public static BillingManager getInstance() {
        return ourInstance;
    }
    private ServiceConnection mServiceConn;

    private BillingManager() {
    }

    public void Initialize() {
        // Payment etc
        mServiceConn = new ServiceConnection() {
            @Override
            public void onServiceDisconnected(ComponentName name) {
                mService = null;
            }
            @Override
            public void onServiceConnected(ComponentName name,
                                           IBinder service) {
                mService = IInAppBillingService.Stub.asInterface(service);
                // Do something here
            }
        };

        Intent serviceIntent = new Intent("com.android.vending.billing.InAppBillingService.BIND");
        serviceIntent.setPackage("com.android.vending");
        activity.bindService(serviceIntent, mServiceConn, Context.BIND_AUTO_CREATE);
    }

    /** A/D
     *
     */
    IInAppBillingService mService;


    //éditeur non autorisé a acheter cette article;
    public String tryToPurchase(String idProduct, boolean sub) {
        Bundle buyIntentBundle = null;
        try {

            String strType =sub? "subs":"inapp";
            buyIntentBundle = mService.getBuyIntent(5, activity.getPackageName(), idProduct, strType, "bGoa+V7g/yqDXvKRqq+JTFn4uQZbPiQJo4pf9RzJ");
            PendingIntent pendingIntent = buyIntentBundle.getParcelable("BUY_INTENT");
            if (pendingIntent == null) {
                return "null";
            }

            activity.startIntentSenderForResult(pendingIntent.getIntentSender(), 1001, new Intent(), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0));
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
        }

        return "purshased";
    }

/** Getter/Setter
 *
 */
    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    /** A/D
     *
     */
    private Activity activity;

    public String getPurchaseStatus(String idProduct, boolean sub) {
        String str = "";
        try {
            Bundle ownedItems = null;
            String strType =sub? "subs":"inapp";
            ownedItems = mService.getPurchases(3, activity.getPackageName(), strType, null);
            int response = ownedItems.getInt("RESPONSE_CODE");
            if (response == 0) {
                ArrayList<String> ownedSkus = ownedItems.getStringArrayList("INAPP_PURCHASE_ITEM_LIST");
                String skustr = "";
                for (int i = 0; i < ownedSkus.size(); ++i) {
                    String sku = ownedSkus.get(i);
                    if (sku.equalsIgnoreCase(idProduct)) {
                        skustr += ";"+sku;
                    }
                }
                str="You have purchased the " + skustr + ". Excellent choice,adventurer!";

            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return str;
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == 1001) {
            int responseCode = data.getIntExtra("RESPONSE_CODE", 0);
            String purchaseData = data.getStringExtra("INAPP_PURCHASE_DATA");
            String dataSignature = data.getStringExtra("INAPP_DATA_SIGNATURE");

            if (resultCode == Activity.RESULT_OK) {
                try {
                    JSONObject jo = new JSONObject(purchaseData);
                    String sku = jo.optString("productId");
                    String packageName = jo.optString("packageName");
                    String purchaseToken = jo.optString("purchaseToken");

                    if (sku.equalsIgnoreCase(SKU_PREMIUM)) {
                        // Do something here...
                        //int response = BillingManager.getInstance().getmService().consumePurchase(3, packageName, purchaseToken);
                        new SnackBarError(activity.getCurrentFocus(),"You have bought the " + sku + ". Excellent choice,adventurer!");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }


}