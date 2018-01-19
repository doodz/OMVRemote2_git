package Client;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.FileProvider;
import android.webkit.MimeTypeMap;

import com.dev.doods.base.R;

import java.io.File;
import java.util.Random;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by thiba on 15/12/2016.
 */

public class DownloadLogsAsyncTask extends AsyncTask<String, Integer, Void> {

    private boolean running = true;
    private  File testDirectory;
    private Context mContext;
    private  NotificationCompat.Builder mNotification;
    private String mFileName;
    private File mFile;
    int notifyID = 1;

   public  DownloadLogsAsyncTask(Context context,String fileName)
   {
       mContext = context;
       mFileName = fileName;
       Random randomGenerator = new Random();
       notifyID = randomGenerator.nextInt();

   }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        testDirectory = new File(Environment.getExternalStorageDirectory() + "/Plugins");
        if (!testDirectory.exists())
        {
            testDirectory.mkdir();
        }

        running = true;

        mNotification = new NotificationCompat.Builder(mContext) .setContentTitle("Dowloading file"+mFileName)
                .setContentText("Dowloading file")
                .setProgress(100, 0, true)
                .setSmallIcon(R.drawable.logo3);

        NotificationManager notificationManager =
                (NotificationManager) mContext.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(notifyID, mNotification.build());

    }


    @Override
    protected Void doInBackground(String... params) {
        mFile = JSONRPCClient.getInstance().Download("/download.php",testDirectory,params[0]);
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {

        NotificationManager notificationManager =
                (NotificationManager) mContext.getSystemService(NOTIFICATION_SERVICE);

        mNotification.setContentText("File downloaded");


        MimeTypeMap mime = MimeTypeMap.getSingleton();
        //String ext=file.getName().substring(file.getName().indexOf(".")+1);
        String ext="txt";
        String type = mime.getMimeTypeFromExtension(ext);
        Intent openFile = new Intent(Intent.ACTION_VIEW, Uri.fromFile(mFile));
        //openFile.setDataAndType(Uri.fromFile(mFile), type);

        Uri apkURI = FileProvider.getUriForFile(
                mContext,
                mContext.getApplicationContext()
                        .getPackageName() + ".provider", mFile);
        openFile.setDataAndType(apkURI, type);
        openFile.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);


        openFile.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent p = PendingIntent.getActivity(mContext, 0, openFile, 0);
        mNotification.setContentIntent(p);
        mNotification.setProgress(100, 100, false);
        notificationManager.notify(notifyID, mNotification.build());
    }
}
