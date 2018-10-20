package ir.mci.push.gcm;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

public class GcmIntentService extends IntentService {

    public GcmIntentService() {
        super("GcmIntentService");
    }

    //public abstract void activityCreated(String msg);


    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);

        String messageType = gcm.getMessageType(intent);

        if (!extras.isEmpty()) {

            if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
                NotificationHandler.sendNotification(this, "Send error: " + extras.toString());

            } else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType)) {

                NotificationHandler.sendNotification(this,"Deleted messages on server: " +
                        extras.toString());

            } else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {

                msgReceive(extras.toString());

                //NotificationHandler.sendNotification(this,"Received: " + extras.toString());
                //Log.i(GCMConstants.TAG, "Received: " + extras.toString());



                //activityCreated(extras.toString());
            }
        }
        // Release the wake lock provided by the WakefulBroadcastReceiver.
        GCMBroadcastReceiver.completeWakefulIntent(intent);
    }

    public void msgReceive(String msg){
        //Log.i("MrPush",msg);
    }


}