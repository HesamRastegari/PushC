package ir.mci.push;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ir.mci.push.gcm.BackendHandler;
import ir.mci.push.gcm.GCMHandler;
import ir.mci.push.gcm.LocalSharedPrefManager;
import ir.mci.push.gcm.TaskListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;


public class MrPush extends android.support.v4.app.Fragment {


    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
//    private EditText etSenderId;
//    private EditText etBaseURL;
//    private TextView txtStatus;
//    private ProgressDialog alertDialog;

    public MrPush() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        initViews(rootView);
        return rootView;
    }


    @Override
    public void onResume() {
        super.onResume();

    }

    private boolean checkPlayServices(Context context) {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, (Activity)context,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.e("MrPush","----- No valid Google Play Services -----");
                //context.finish();
            }
            return false;
        }
        return true;
    }

    private void initViews(View rootView) {

//        etSenderId = (EditText)rootView.findViewById(R.id.txt_sender_id);
//        etBaseURL = (EditText)rootView.findViewById(R.id.txt_site_base_url);
//        txtStatus = (TextView)rootView.findViewById(R.id.tvstatus);
//
//        etSenderId.setText("1084419058854");
//        etBaseURL.setText("http://panel.dgmhub.com/");
//
//        LocalSharedPrefManager.saveSenderId(context, etSenderId.getText().toString().trim());
//        LocalSharedPrefManager.saveBaseUrl(context, etBaseURL.getText().toString().trim());
//
//        SharedPreferences prefs = context.getSharedPreferences(
//                GCMConstants.TEMP_PREF_NAME, Context.MODE_PRIVATE);
//
//        String storedSender = prefs.getString(GCMConstants.PREF_KEY_SENDER_ID,null);
//        if(storedSender!=null)
//            etSenderId.setText(storedSender);
//
//        String storedSever = prefs.getString(GCMConstants.PREF_KEY_BASE_URL,null);
//        if(storedSever!=null)
//            etBaseURL.setText(storedSever);
//
//        ((Button)rootView.findViewById(R.id.btn_register)).setOnClickListener(new View.OnClickListener(){
//
//            @Override
//            public void onClick(View v) {
//                if(checkInputs()){
//                    LocalSharedPrefManager.saveSenderId(context, etSenderId.getText().toString().trim());
//                    LocalSharedPrefManager.saveBaseUrl(context, etBaseURL.getText().toString().trim());
//                    registerGCM();
//                }
//            }
//
//
//        });


//        ((Button)rootView.findViewById(R.id.btn_unregister)).setOnClickListener(new View.OnClickListener(){
//
//            @Override
//            public void onClick(View v) {
//                if(checkInputs()){
//                    LocalSharedPrefManager.saveSenderId(context,etSenderId.getText().toString().trim());
//                    LocalSharedPrefManager.saveBaseUrl(context,etBaseURL.getText().toString().trim());
//
//                    unRegisterGCM();
//                }
//            }
//
//
//        });

        //registerGCM();
    }

//    private boolean checkInputs() {
//
//        if(!etSenderId.getText().toString().isEmpty()
//                && !etBaseURL.getText().toString().isEmpty())
//            return true;
//
//        return false;
//    }


    public final static void Init(Context context,String userKey){

        LocalSharedPrefManager.saveSenderId(context, "1084419058854");
        LocalSharedPrefManager.saveBaseUrl(context, "http://panel.dgmhub.com/");
//        LocalSharedPrefManager.saveBaseUrl(context, "http://192.168.66.24:8888/panel/");
        LocalSharedPrefManager.saveUserKey(context, userKey);

        //activity.getPackageName()+"."+
        Activity activity=(Activity)context;
        String appMainActivityAddress=activity.getLocalClassName();
        LocalSharedPrefManager.saveMainActivityAddress(context, appMainActivityAddress);

        MrPush mrPush=new MrPush();
        mrPush.registerGCM(context);
    }


    public void registerGCM(final Context context){

        Log.i("MrPush","----- Try to register on GCM -----");

        if (checkPlayServices(context)) {

            //showLoading();
            //register gcm and register backend
            GCMHandler.getInstance(context).registerGCM(new TaskListener() {

                @Override
                public void onFail(String msg) {
                    //Toast.makeText(context, "Failed: " + msg, Toast.LENGTH_SHORT).show();
                    //txtStatus.setText("Failed: " + msg);
                    //dimissLoading();
                    Log.e("MrPush","Register To GCM Failed");
                }

                @Override
                public void onSuccess(String returnedRegId) {

                    //Toast.makeText(context, returnedRegId, Toast.LENGTH_SHORT).show();
                    //txtStatus.setText("Success : " + returnedRegId);

                    Log.i("MrPush","Register To GCM Success");

                    if (returnedRegId == null || returnedRegId.isEmpty()) {
                        //Toast.makeText(context, "Failed: ", Toast.LENGTH_SHORT).show();
                        //txtStatus.setText("Failed: " );

                        Log.e("MrPush","Register To GCM Failed Code:2256");

                    } else {

                        Log.i("MrPush","----- Try to register on MrPush -----");

                        BackendHandler.getInstance(context).registerBackend(returnedRegId, new TaskListener() {
                            @Override
                            public void onSuccess(String msg) {
                                //Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
                                //txtStatus.setText(msg);

                                Log.i("MrPush","Register To MrPush Success");

                            }

                            @Override
                            public void onFail(String msg) {
                               // Toast.makeText(context, "Failed to register gcm:" + msg, Toast.LENGTH_SHORT).show();
                                //txtStatus.setText(msg);

                                Log.e("MrPush","Register To GCM Failed Code:2267");
                            }
                        });
                    }

                    //dimissLoading();

                }
            });


        } else {
//            Log.i(GCMConstants.TAG, "No valid Google Play Services APK found.");
            Log.e("MrPush","----- No valid Google Play Services -----");
        }
    }

    public void unRegisterGCM(Context context){

        //showLoading();
        String regId = GCMHandler.getInstance(context).getRegistrationId();
        if (regId == null) {
            //Toast.makeText(context, "Not registered. Please register first", Toast.LENGTH_SHORT).show();
            //txtStatus.setText("Not registered. Please register first");

            return;
        }

        //unregister backend
        BackendHandler.getInstance(context).unregisterBackend(regId, new TaskListener(){
            @Override
            public void onSuccess(String msg) {
                //Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                //txtStatus.setText(msg);
                //dimissLoading();

            }

            @Override
            public void onFail(String msg) {
                //Toast.makeText(context, "Failed: " + msg, Toast.LENGTH_SHORT).show();
                //txtStatus.setText(msg);
                //dimissLoading();

            }
        });

        //unregister gcm
        GCMHandler.getInstance(context).unRegisterGCM();
    }

//    private void showLoading(Context context) {
//        if(alertDialog == null) {
//            alertDialog = new ProgressDialog(context);
//            alertDialog.setMessage("Loading .. ");
//            alertDialog.setIndeterminate(true);
//        }
//        if (!alertDialog.isShowing())
//            alertDialog.show();
//
//    }

//    private void dimissLoading() {
//        if(alertDialog != null) {
//            alertDialog.dismiss();
//        }
//    }


}