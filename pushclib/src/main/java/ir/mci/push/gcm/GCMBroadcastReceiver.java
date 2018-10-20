package ir.mci.push.gcm;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.WakefulBroadcastReceiver;

import com.google.android.gms.gcm.GoogleCloudMessaging;

public class GCMBroadcastReceiver extends WakefulBroadcastReceiver {

    Context mContext;

    Intent intent;
    public GCMBroadcastReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        this.mContext=context;
        this.intent=intent;

        ComponentName comp = new ComponentName(context.getPackageName(),
                GcmIntentService.class.getName());

        startWakefulService(context, (intent.setComponent(comp)));
        setResultCode(Activity.RESULT_OK);
        onMessageReceive(intent.getExtras().toString().replace("from=1084419058854",""),context);


    }


    public void onMessageReceive(String msg,Context context){
//        ComponentName comp = new ComponentName(this.mContext.getPackageName(),
//                GcmIntentService.class.getName());
//
//        startWakefulService(this.mContext, (this.intent.setComponent(comp)));
//        setResultCode(Activity.RESULT_OK);
    }
}
