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

/**
 * Created by Ividata7 on 15/03/2017.
 */

public class BillingManager {
    private static BillingManager ourInstance = new BillingManager();
    public static BillingManager getInstance() {
        return ourInstance;
    }

    private BillingManager() {
    }

    public void Initialize() {
        // Payment etc
        ServiceConnection mServiceConn = new ServiceConnection() {
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

    protected void tryToPurchase(String idProduct) {
        Bundle buyIntentBundle = null;
        try {
            buyIntentBundle = mService.getBuyIntent(5, activity.getPackageName(), idProduct, "inapp", "bGoa+V7g/yqDXvKRqq+JTFn4uQZbPiQJo4pf9RzJ");
            PendingIntent pendingIntent = buyIntentBundle.getParcelable("BUY_INTENT");
            if (pendingIntent == null) {
                return;
            }

            activity.startIntentSenderForResult(pendingIntent.getIntentSender(), 1001, new Intent(), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0));
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
        }
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

    private void getPurchaseStatus(String idProduct) {
        try {
            Bundle ownedItems = null;
            ownedItems = mService.getPurchases(3, activity.getPackageName(), "inapp", null);
            int response = ownedItems.getInt("RESPONSE_CODE");
            if (response == 0) {
                ArrayList<String> ownedSkus = ownedItems.getStringArrayList("INAPP_PURCHASE_ITEM_LIST");

                for (int i = 0; i < ownedSkus.size(); ++i) {
                    String sku = ownedSkus.get(i);
                    if (sku.equalsIgnoreCase(idProduct)) {
                        // Do something here...
                    }
                }
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
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

                    if (sku.equalsIgnoreCase("myItem1")) {
                        // Do something here...
                        //int response = BillingManager.getInstance().getmService().consumePurchase(3, packageName, purchaseToken);
                    }
                    else if (sku.equalsIgnoreCase("myItem2")) {
                        // Do something here...
                        //int response = BillingManager.getInstance().getmService().consumePurchase(3, packageName, purchaseToken);
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