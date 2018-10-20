package ir.mci.push.gcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import ir.mci.push.R;


/**
 * Created by MSaudi on 2/3/2015.
 */
public abstract class NotificationHandler {

    public abstract void activityCreated();


    private static final int NOTIFICATION_ID = 112233;
    private static NotificationManager mNotificationManager;

        public static void sendNotification(Context context, String msg) {



            mNotificationManager = (NotificationManager)
                    context.getSystemService(Context.NOTIFICATION_SERVICE);

            //TODO change this to the activity you want to open

            String mainActivityAddress = LocalSharedPrefManager.loadMainActivityAddress(context);
//            String activityToStart = "com.example.MainActivity";
            try {
                Class<?> c = Class.forName(mainActivityAddress);
//                Intent intent = new Intent(this, c);
//                startActivity(intent);


            Intent activityToOpen = new Intent().setClass(context, c);
            activityToOpen.putExtra(GCMConstants.EXTRA_NOTIFICATION_MSG,msg);

            PendingIntent contentIntent = PendingIntent.getActivity(context, 0,activityToOpen , PendingIntent.FLAG_UPDATE_CURRENT );
            Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            //-----

                //String currentString = "Fruit: they taste good";
                String[] separated = msg.split("Received: Bundle");


                String singleWord = "mode";
                boolean find;



                if (separated[1].toLowerCase().indexOf(singleWord.toLowerCase()) > -1) {

                    find = true;
                }

                //-----

            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(context)
                            .setSmallIcon(R.drawable.ic_launcher)
                            .setAutoCancel(true)
                            .setSound(uri)
                            .setContentTitle("Notification")
                            .setStyle(new NotificationCompat.BigTextStyle()
                                    .bigText(msg))
                            .setContentText(msg);

            mBuilder.setContentIntent(contentIntent);
            mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());

            } catch (ClassNotFoundException ignored) {
                Log.e("MrPush",ignored.toString());
            }
        }
}
