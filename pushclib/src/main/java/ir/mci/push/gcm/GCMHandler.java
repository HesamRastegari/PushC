package ir.mci.push.gcm;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.util.Log;


import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;

/**
 * Created by MSaudi on 1/31/2015.
 */
public class GCMHandler {

    //Constants
    public  static final String PROPERTY_REG_ID = "registration_id";
    private static final String PROPERTY_APP_VERSION = "appVersion";

    //class variables
    static GoogleCloudMessaging gcm;
    static GCMHandler mGSMHandler ;
    static String regId =null;
    static Context mContext;

    static String senderId="";
    private GCMHandler(){

    }

    public static  GCMHandler getInstance(Context context){

        mContext = context;
        if (mGSMHandler == null)
            mGSMHandler = new GCMHandler();

        return  mGSMHandler;
    }

    public  void    registerGCM(final TaskListener listener) {
        new AsyncTask<Void,Void,String>() {
            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(mContext);
                    }
                    if(GCMConstants.USE_URLS_FROM_UI) {
                        senderId = LocalSharedPrefManager.loadSenderIdFromPrefs(mContext);
                    }else
                        senderId = GCMConstants.SENDER_ID;

                    regId = gcm.register(senderId);
                    msg =regId;
                } catch (IOException ex) {
                    ex.printStackTrace();;
                    return null;
                }
                storeRegistrationId(mContext, regId);

                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {
                if (msg==null)
                    listener.onFail("Error happen while executing registerCGM");
                else
                    listener.onSuccess(msg);
            }
        }.execute(null, null, null);
    }

    public  void    unRegisterGCM() {

        if (gcm == null) {
            gcm = GoogleCloudMessaging.getInstance(mContext);
        }
    }

    public String getRegistrationId() {

        final SharedPreferences prefs = getGCMPreferences(mContext);

        String regId = prefs.getString(PROPERTY_REG_ID,null);
        return regId;

    }


    //Helper methods
    /**
     * Stores the registration ID and app versionCode in the application's
     * {@code SharedPreferences}.
     *
     * @param context application's context.
     * @param regId registration ID
     */
    private  void  storeRegistrationId(Context context, String regId) {
        final SharedPreferences prefs = getGCMPreferences(context);
        int appVersion = getAppVersion(context);
        Log.i(GCMConstants.TAG, "Saving regId on app version " + appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PROPERTY_REG_ID, regId);
        editor.putInt(PROPERTY_APP_VERSION, appVersion);
        editor.commit();
    }

    private SharedPreferences getGCMPreferences(Context context) {
        // This sample app persists the registration ID in shared preferences, but
        // how you store the regID in your app is up to you.
//        return context.getSharedPreferences(MainActivity.class.getSimpleName(),
//                Context.MODE_PRIVATE);
        Activity activity=(Activity)context;
        return context.getSharedPreferences(activity.getClass().getSimpleName(),
                Context.MODE_PRIVATE);
    }

    private  int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

}
