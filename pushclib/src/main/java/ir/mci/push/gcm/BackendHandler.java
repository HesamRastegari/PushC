package ir.mci.push.gcm;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.text.TextUtils;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by MSaudi on 2/3/2015.
 */
public class BackendHandler {
    private static final int TIMEOUT_MILLISEC = 10000;
    private static Context mContext;
    private static BackendHandler mBackEndHandler;
    String name = "name";
    String email = "email@server.com";
    private String mRegId;
    private TaskListener listener;

    private BackendHandler() {
    }

    //Use this method if you have a name for users. Add listener as a parameter (check register method below)
    public void sendRegistrationIdToBackend(String regId, String name) {
        this.mRegId = regId;
        this.name = name;

    }

    //Use this method if you have a name and email for users. Add listener as a parameter (check register method below)
    public void sendRegistrationIdToBackend(String regId, String name, String email) {
        this.mRegId = regId;
        this.name = name;
        this.email = email;

    }

    public static BackendHandler getInstance(Context context) {

        mContext = context;

        if (mBackEndHandler == null)
            mBackEndHandler = new BackendHandler();

        return mBackEndHandler;

    }

    public void registerBackend(String regId, TaskListener listener) {

        new SendRegistrationIdTask(regId, listener).execute();
    }

    public void unregisterBackend(String regId, TaskListener listener) {

        new SendUnRegisterTask(regId,listener).execute();

    }


    //UNRegister user in backend
    class SendUnRegisterTask extends
            AsyncTask<String, Void, String> {
        private String mRegId;
        TaskListener listener;
        public SendUnRegisterTask(String regId, TaskListener listener) {
            mRegId = regId;
            this.listener= listener;
        }

        @Override
        protected String doInBackground(String... regIds) {

            JSONObject obj = new JSONObject();
            try {
                obj.put(GCMConstants.BACKEND_KEY_REG_ID, mRegId);

                HttpParams httpParams = new BasicHttpParams();
                HttpConnectionParams.setConnectionTimeout(httpParams, TIMEOUT_MILLISEC);
                HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_MILLISEC);
                HttpClient client = new DefaultHttpClient(httpParams);
                StringEntity se = new StringEntity( obj.toString());
                se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));

                String regURL = "";
                if (GCMConstants.USE_URLS_FROM_UI) {
                    regURL = LocalSharedPrefManager.loadServerBaseURLFromPrefs(mContext)+GCMConstants.SERVER_API_USER_DELETE;
                }else
                    regURL = GCMConstants.SERVER_UNREGISTER_URL;

                HttpDeleteWithBody httpDeleteWithBody = new HttpDeleteWithBody(regURL);
                httpDeleteWithBody.setEntity(se);

                HttpResponse response = client.execute(httpDeleteWithBody);

                String responseTest=null;
                try {
                    responseTest = EntityUtils.toString(response.getEntity());
                } catch (IOException e) {
                }
                return responseTest;
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;

        }

        @Override
        protected void onPostExecute(String response) {

            listener.onSuccess(response);
        }
    }


    //Register new user with backend
    class SendRegistrationIdTask extends
            AsyncTask<String, Void, String> {
        private final TaskListener listener;
        private String mRegId;

        public SendRegistrationIdTask(String regId, TaskListener listener) {
            mRegId = regId;
            this.listener =listener;

        }

        @Override
        protected String doInBackground(String... regIds) {

            JSONObject obj = new JSONObject();
            try {
                obj.put(GCMConstants.BACKEND_KEY_REG_ID, mRegId);
                obj.put(GCMConstants.BACKEND_KEY_NAME, getDeviceName());
                obj.put(GCMConstants.BACKEND_KEY_EMAIL, "Android V "+android.os.Build.VERSION.RELEASE);
                obj.put(GCMConstants.BACKEND_KEY_PACKAGENAME, mContext.getPackageName());
                obj.put(GCMConstants.BACKEND_KEY_USERKEY, LocalSharedPrefManager.loadSenderIdFromPrefs(mContext));

                HttpParams httpParams = new BasicHttpParams();
                HttpConnectionParams.setConnectionTimeout(httpParams, TIMEOUT_MILLISEC);
                HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_MILLISEC);
                HttpClient client = new DefaultHttpClient(httpParams);
                StringEntity se = new StringEntity(obj.toString());
                se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));

                String regURL = "";
                if (GCMConstants.USE_URLS_FROM_UI) {
                    regURL = LocalSharedPrefManager.loadServerBaseURLFromPrefs(mContext)+GCMConstants.SERVER_API_USER;
                }else
                    regURL = GCMConstants.SERVER_REG_URL;

                HttpPost request = new HttpPost(regURL);

                request.setEntity(se);
                HttpResponse response = client.execute(request);
                String responseTest = null;
                try {
                    responseTest = EntityUtils.toString(response.getEntity());
                } catch (IOException e) {
                }
                return responseTest;
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;

        }

        @Override
        protected void onPostExecute(String response) {
            listener.onSuccess(response);
        }
    }

    //Helper methods
    public static String getDeviceName() {
        final String manufacturer = Build.MANUFACTURER;
        final String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        }
        if (manufacturer.equalsIgnoreCase("HTC")) {
            // make sure "HTC" is fully capitalized.
            return "HTC " + model;
        }
        return capitalize(manufacturer) + " " + model;
    }
    private static final String capitalize(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        final char[] arr = str.toCharArray();
        boolean capitalizeNext = true;
        String phrase = "";
        for (final char c : arr) {
            if (capitalizeNext && Character.isLetter(c)) {
                phrase += Character.toUpperCase(c);
                capitalizeNext = false;
                continue;
            } else if (Character.isWhitespace(c)) {
                capitalizeNext = true;
            }
            phrase += c;
        }
        return phrase;
    }



}
