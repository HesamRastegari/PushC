package ir.mci.push.gcm;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by MSaudi on 2/7/2015.
 */
public class LocalSharedPrefManager {

    public static  void saveSenderId(Context context, String senderId) {
        SharedPreferences prefs = context.getSharedPreferences(
                GCMConstants.TEMP_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(GCMConstants.PREF_KEY_SENDER_ID, senderId);
        editor.commit();

    }
    public static  void saveBaseUrl(Context context, String baseUrl) {
        if (!baseUrl.endsWith("\\") && !baseUrl.endsWith("/")){
            baseUrl +="/";
        }
        if (!baseUrl.startsWith("http://")){
            baseUrl ="http://" + baseUrl;
        }
        SharedPreferences prefs = context.getSharedPreferences(
                GCMConstants.TEMP_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(GCMConstants.PREF_KEY_BASE_URL, baseUrl);
        editor.commit();

    }

    public static  void saveUserKey(Context context, String userKey) {

        SharedPreferences prefs = context.getSharedPreferences(
                GCMConstants.TEMP_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(GCMConstants.PREF_KEY_User_Key, userKey);
        editor.commit();

    }

    public static  void saveMainActivityAddress(Context context, String strMainActivityAddress) {
        SharedPreferences prefs = context.getSharedPreferences(
                GCMConstants.TEMP_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(GCMConstants.PREF_MAIN_ACTIVITY_ADDRESS, strMainActivityAddress);
        editor.commit();

    }

    public static String loadSenderIdFromPrefs(Context context) {

        SharedPreferences prefs = context.getSharedPreferences(
                GCMConstants.TEMP_PREF_NAME, Context.MODE_PRIVATE);

        String storedSender = prefs.getString(GCMConstants.PREF_KEY_SENDER_ID,null);

        return storedSender;
    }

    public static String loadServerBaseURLFromPrefs(Context context) {

        SharedPreferences prefs = context.getSharedPreferences(
                GCMConstants.TEMP_PREF_NAME, Context.MODE_PRIVATE);

        String storedSever = prefs.getString(GCMConstants.PREF_KEY_BASE_URL,null);
        return storedSever;
    }

    public static String loadMainActivityAddress(Context context) {

        SharedPreferences prefs = context.getSharedPreferences(
                GCMConstants.TEMP_PREF_NAME, Context.MODE_PRIVATE);

        String storedSever = prefs.getString(GCMConstants.PREF_MAIN_ACTIVITY_ADDRESS,null);
        return storedSever;
    }

    public static String loadUserKey(Context context) {

        SharedPreferences prefs = context.getSharedPreferences(
                GCMConstants.TEMP_PREF_NAME, Context.MODE_PRIVATE);

        String storedSever = prefs.getString(GCMConstants.PREF_KEY_User_Key,null);
        return storedSever;
    }

}
