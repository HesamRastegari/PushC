package ir.example.hessam.pushc;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;


import ir.mci.push.gcm.GCMBroadcastReceiver;

public class pushListener extends GCMBroadcastReceiver {



    @Override
    public void onMessageReceive(String msg,Context context) {


        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        Log.i("MrPush",msg);
    }
}
