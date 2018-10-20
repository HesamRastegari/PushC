package ir.mci.push.gcm;

/**
 * Created by MSaudi on 1/31/2015.
 */
public class GCMConstants {


    //TODO Change the following to match your server

    //modify this to be false
    public static final boolean USE_URLS_FROM_UI        = true;

    //put sender id here (the one you get from api console)
    public static String SENDER_ID                      = null;

    //put server url here
    public static final String SERVER_BASE_URL          = null;


    /** No need to change the following*/
    public static final String TAG = "MrPush";

    public static final String SERVER_API_USER          = "api-user";
    public static final String SERVER_API_USER_DELETE   = "api-user/delete";

    public static final String SERVER_REG_URL           = SERVER_BASE_URL + SERVER_API_USER;
    public static final String SERVER_UNREGISTER_URL    = SERVER_BASE_URL + SERVER_API_USER_DELETE;


    public static final String BACKEND_KEY_REG_ID       = "gcm_regid";
    public static final String BACKEND_KEY_NAME         = "name";
    public static final String BACKEND_KEY_EMAIL        = "email";
    public static final String BACKEND_KEY_PACKAGENAME  = "packagename";
    public static final String BACKEND_KEY_USERKEY      = "userKey";


    public static final String EXTRA_NOTIFICATION_MSG   = "extranptificationmsg";
    public static final String TEMP_PREF_NAME           = "tempprefname";
    public static final String PREF_KEY_SENDER_ID       = "tempprefname_senderId";
    public static final String PREF_MAIN_ACTIVITY_ADDRESS       = "tempprefname_mainActivity";
    public static final String PREF_KEY_BASE_URL        = "tempprefname_serverurl";
    public static final String PREF_KEY_User_Key        = "tempprefname_userkey";


}
