package affwl.com.teenpatti;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sdsmdg.tastytoast.TastyToast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static android.provider.Settings.Secure.ANDROID_ID;

public class MainActivity extends AppCompatActivity {

    ImageView showPopupBtn, closeRateus, closeHelpBtn, closeTrophyBtn, profile, orangechipsbtn, close312help, closesixpattihelp, short321info, tourney_shortinfo_closebtn, shortsixpattiinfo, bluechipsbtn, cyanchipsbtn, shortinfo_tourney, tourney_join_closebtn, ygreenchipsbtn, closebtn_create_table, mainlimegchipsbtn, variation_closebtn, facebook, whatsapp, general;
    PopupWindow RateuspopupWindow, HelpUspopupWindow, TrophypopupWindow, tounpopupWindow, howto321popup, sixpattipopup, howtosixpattipopup, join_tourney_popupWindow, shortinfo_tourney_popupwindow, create_table_private_popupwindow, join_table_popupwindow;
    RelativeLayout RelativeLayoutloader, relativelayout321, relativeLayoutsixpatti, relativeLayout_tourney, yellowchiplayout, orangechipslayout, limechipslayout, darkbluechiplayout, blackchipslayout, cyanchipslayout, ygreenchipslayout;
    TextView txtVUserNameMain, code;
    Session session;
    String time_check, local_Time, tableid, table_name, table_time, wait_time;
    int server_time, system_time;
    long wait;
    MediaPlayer mediaPlayer;

    private TextView user_id;
    private TextView deviceid;
    private TextView imei_number;
    private TextView imsi_number;
    private TextView uuid_number;
    private TextView device_name;
    private TextView sim_operator;
    private TextView network_type;
    private TextView software_version;
    private static final String TAG = "mainactivity";
    private ScheduledExecutorService scheduleTaskExecutor;

    private static final int PERMISSION_REQUEST_CODE = 200;
    private static final int PERMISSION_REQUEST_ACCESS_COARSE_LOCATION = 200;


    int value = 0;
    int value1 = 0;
    int value2 = 0;
    int value3 = 0;
    int value4 = 0;
    int value5 = 0;
    int value6 = 0;
    int value7 = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teenpatti_activity_main);

        scheduleTaskExecutor = Executors.newScheduledThreadPool(5);
        scheduleTaskExecutor.scheduleAtFixedRate(new Runnable() {
            public void run() {
                WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                int linkSpeed = wifiManager.getConnectionInfo().getRssi();
                int level = WifiManager.calculateSignalLevel(linkSpeed, 5);
                Log.i("SPEED", "WIFI level " + level);
                if (level == 5 || level == 4 || level == 3) {
                    TastyToast.makeText(getApplicationContext(), "Internet Proper " +level, TastyToast.LENGTH_LONG, TastyToast.DEFAULT);
                } else if (level == 2 || level == 1 || level == 0) {
                    TastyToast.makeText(getApplicationContext(), "Internet Moderate " +level, TastyToast.LENGTH_LONG, TastyToast.DEFAULT);
                } else {
                    TastyToast.makeText(getApplicationContext(), "Internet Slow " +level, TastyToast.LENGTH_LONG, TastyToast.DEFAULT);
                }

            }
        }, 0, 1, TimeUnit.MINUTES);


        user_id = findViewById(R.id.user_id);
        deviceid = findViewById(R.id.android_id);
        imei_number = findViewById(R.id.imei);
        imsi_number = findViewById(R.id.imsi);
        uuid_number = findViewById(R.id.uuid);
        network_type = findViewById(R.id.network_type);
        sim_operator = findViewById(R.id.sim_operator);
        device_name = findViewById(R.id.device_name);
        software_version = findViewById(R.id.software_version);

        profile = findViewById(R.id.profile);
        txtVUserNameMain = findViewById(R.id.txtVUserNameMain);

        RelativeLayoutloader = findViewById(R.id.linearLayoutloader);

        code = findViewById(R.id.code);
        session = new Session(this);
        String encodedimage = session.getImage();
        if (!encodedimage.equalsIgnoreCase("")) {
            byte[] b = Base64.decode(encodedimage, Base64.DEFAULT);
            Bitmap bmp = BitmapFactory.decodeByteArray(b, 0, b.length);
            profile.setImageBitmap(bmp);
        }
        String name = session.getName();
        txtVUserNameMain.setText(DataHolder.first_name + " " + DataHolder.last_name);

        ygreenchipslayout = findViewById(R.id.ygreenchipslayout);


        // Popup for Help
        showPopupBtn = findViewById(R.id.help_btn_loader);
        RelativeLayoutloader = findViewById(R.id.linearLayoutloader);

        showPopupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //instantiate the popup.xml three_two_one_leaderboard file
                LayoutInflater layoutInflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View customView = layoutInflater.inflate(R.layout.help_popup, null);

                closeHelpBtn = customView.findViewById(R.id.close_helpus);

                //instantiate popup window
                HelpUspopupWindow = new PopupWindow(customView, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT, true);

                //display the popup window
                HelpUspopupWindow.showAtLocation(RelativeLayoutloader, Gravity.TOP, 0, 0);

                //close the popup window on button click
                closeHelpBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HelpUspopupWindow.dismiss();
                        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.click);
                        mediaPlayer.start();
                    }
                });
                mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.click);
                mediaPlayer.start();
            }
        });

        //////////////// teen patti ////////////////

        RelativeLayoutloader = findViewById(R.id.linearLayoutloader);
//        yellowchiplayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//
//                    @Override
//                    public void run() {
//                        Intent i = new Intent(MainActivity.this, LoadingScreen_teenpatti.class);
//                        startActivity(i);
//                        yellowchiplayout.clearAnimation();
//                        //yellowchiplayout.startAnimation(Animchipsleft);
//                        orangechipslayout.startAnimation(Animchipsright);
//                        limechipslayout.startAnimation(Animchipsright);
//                        darkbluechiplayout.startAnimation(Animchipsright);
//                        ygreenchipslayout.startAnimation(Animchipsright);
//                        blackchipslayout.startAnimation(Animchipsright);
//                        ygreenchipslayout.startAnimation(Animchipsright);
//                    }
//                }, 500);
//            }
//        });


        //////////////// Popup for 321 tournament ////////////////

//        orangechipsbtn = findViewById(R.id.mainorgchips);
        RelativeLayoutloader = findViewById(R.id.linearLayoutloader);
//        orangechipslayout = findViewById(R.id.orangechipslayout);

//        orangechipslayout.setOnClickListener(new View.OnClickListener() {
//
//            public void onClick(View v) {
//
//                //Delays the click event by 5 seconds
//                Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        Intent i = new Intent(MainActivity.this, ThreetwooneTournament.class);
//                        startActivity(i);
//                        orangechipslayout.clearAnimation();
//                        orangechipslayout.startAnimation(Animchipsleft);
//                        limechipslayout.startAnimation(Animchipsleft);
//                    }
//                }, 500);
//
//            }
//        });

        //////////////// Popup for six patti ////////////////
//        bluechipsbtn = findViewById(R.id.darkbluechips);
        RelativeLayoutloader = findViewById(R.id.linearLayoutloader);

//        darkbluechiplayout.setOnClickListener(new View.OnClickListener() {
//
//            public void onClick(View v) {
//
//                //Delays the click event by 5 seconds
//                Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        Intent i = new Intent(MainActivity.this, LoadingScreen_tourney.class);
//                        startActivity(i);
//                        finish();
//                    }
//                }, 3000);
//
//            }
//        });


//        bluechipsbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, LoadingScreen_sixpatti.class);
//                startActivity(intent);
//                finish();
//            }
//        });

        //////////////// Popup for Tourney ////////////////
//        cyanchipsbtn = findViewById(R.id.cyanchips);
        RelativeLayoutloader = findViewById(R.id.linearLayoutloader);

//        cyanchipslayout.setOnClickListener(new View.OnClickListener() {
//
//            public void onClick(View v) {
//
//                //Delays the click event by  milliseconds
//                Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        Intent i = new Intent(MainActivity.this, LoadingScreen_tourney.class);
//                        startActivity(i);
//                        finish();
//                    }
//                }, 3000);
//
//            }
//        });


//        cyanchipsbtn.setOnClickListener(new View.OnClickListener() {
//            @SuppressLint("WrongViewCast")
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(MainActivity.this, LoadingScreen_tourney.class);
//                startActivity(intent);
//                finish();
//            }
//        });


        //////////////// Popup for Tourney2 ////////////////
//        cyanchipsbtn = findViewById(R.id.cyanchips);
//        RelativeLayoutloader = findViewById(R.id.linearLayoutloader);
//        relativeLayout_tourney = findViewById(R.id.relativelayout_tourney);
//
//
//        cyanchipsbtn.setOnClickListener(new View.OnClickListener() {
//            @SuppressLint("WrongViewCast")
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(MainActivity.this, LoadingScreen_tourney.class);
//                startActivity(intent);
//                finish();
//
//            }
//        });


        //////////////// Popup for Private ////////////////
        ygreenchipsbtn = findViewById(R.id.ygreenchips);
        RelativeLayoutloader = findViewById(R.id.linearLayoutloader);

        ygreenchipslayout.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoadingScreen_private.class));
//                new getTableAsyncTask().execute("http://213.136.81.137:8081/api/getTableinfo");
                mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.click);
                mediaPlayer.start();
            }
        });

        RelativeLayoutloader = findViewById(R.id.linearLayoutloader);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.mainactivity_chips_rotate);

        findViewById(R.id.ygreenchips).startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {}

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });

        Animation antianimation = AnimationUtils.loadAnimation(this, R.anim.mainactivity_chips_rotate_anticlockwise);
//        findViewById(R.id.innerlime).startAnimation(antianimation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        getUUID();
        getAndroidId();
        getDeviceName();
        getNetworkType();
        getSimOperator();
        getSoftwareVersion();
        getImei();
//        getWifiLevel();
        getImsi();
        new DevicePost().execute("http://213.136.81.137:8081/api/adevice");
        new changeimageAsyncTask().execute("http://213.136.81.137:8081/api/changeProfile");
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null) {
            result += line;
            Log.e("Line", result);
        }
        inputStream.close();
        return result;
    }

    //////////// Onclick method for teenpatti table /////////////
    @Override
    public void onBackPressed() {
        displayExitAlert("Alert", "Do you want to Exit?");
        MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.click);
        mp.start();
    }

    private void displayExitAlert(String title, String message) {

        TextView tv_alert_ok, tv_alert_title, tv_alert_message, tv_alert_cancel;
        ImageView alert_box_close;

        final Dialog myAlertDialog = new Dialog(this);
        myAlertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        myAlertDialog.setCanceledOnTouchOutside(false);
        myAlertDialog.setContentView(R.layout.alert_box);

        tv_alert_ok = myAlertDialog.findViewById(R.id.tv_alert_ok);
        tv_alert_cancel = myAlertDialog.findViewById(R.id.tv_alert_cancel);
        alert_box_close = myAlertDialog.findViewById(R.id.alert_box_close);
        tv_alert_title = myAlertDialog.findViewById(R.id.tv_alert_title);
        tv_alert_message = myAlertDialog.findViewById(R.id.tv_alert_message);

        tv_alert_title.setText(title);
        tv_alert_message.setText(message);
        tv_alert_ok.setText("Yes");
        tv_alert_cancel.setText("No");

        alert_box_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myAlertDialog.dismiss();
            }
        });

        tv_alert_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
            }
        });

        tv_alert_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myAlertDialog.dismiss();
            }
        });
        myAlertDialog.show();

    }

    /*Get Information About User Device*/

    private String getUUID() {
        // TODO Auto-generated method stub
        final TelephonyManager telephonyManager = (TelephonyManager) getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);
        final String tmDevice, tmSerial, androidId;
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_ACCESS_COARSE_LOCATION);
        }
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, PERMISSION_REQUEST_CODE);
        } else {
            //TODO
        }
        tmDevice = "" + telephonyManager.getDeviceId();
        tmSerial = "" + telephonyManager.getSimSerialNumber();
        androidId = "" + android.provider.Settings.Secure.getString(getContentResolver(), ANDROID_ID);
        UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
        uuid_number.setText(deviceUuid.toString());
        return deviceUuid.toString();
    }

    private String getImsi() {
        // TODO Auto-generated method stub
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_ACCESS_COARSE_LOCATION);
        }
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, PERMISSION_REQUEST_CODE);
        } else {
            //TODO
        }
        TelephonyManager telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        imsi_number.setText(telephonyManager.getSubscriberId());
        return telephonyManager.getSubscriberId();
    }

    private String getImei() {
        // TODO Auto-generated method stub
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_ACCESS_COARSE_LOCATION);
        }
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, PERMISSION_REQUEST_CODE);
        } else {
            //TODO
        }
        TelephonyManager telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        imei_number.setText(telephonyManager.getDeviceId());
        return telephonyManager.getDeviceId();
    }

    private String getAndroidId() {
        // TODO Auto-generated method stub
        deviceid.setText(Settings.Secure.getString(getContentResolver(), ANDROID_ID));
        return Settings.Secure.getString(getContentResolver(), ANDROID_ID);
    }

    private String getDeviceName() {
        String str = android.os.Build.MODEL;
        device_name.setText(str);
        return str;
    }

    private String getSimOperator() {
        OperatorHolder operatorholder = new OperatorHolder(this);
        sim_operator.setText(operatorholder.getOperatorName());
        Log.i(TAG, operatorholder.getOperatorName());
        return operatorholder.getOperatorName();
    }

    private String getNetworkType() {
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        int networkType = telephonyManager.getNetworkType();
        switch (networkType) {
            case TelephonyManager.NETWORK_TYPE_1xRTT:
                return "1xRTT";
            case TelephonyManager.NETWORK_TYPE_CDMA:
                return "CDMA";
            case TelephonyManager.NETWORK_TYPE_EDGE:
                return "EDGE";
            case TelephonyManager.NETWORK_TYPE_EHRPD:
                return "eHRPD";
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
                return "EVDO rev. 0";
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
                return "EVDO rev. A";
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
                return "EVDO rev. B";
            case TelephonyManager.NETWORK_TYPE_GPRS:
                return "GPRS";
            case TelephonyManager.NETWORK_TYPE_HSDPA:
                return "HSDPA";
            case TelephonyManager.NETWORK_TYPE_HSPA:
                return "HSPA";
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return "HSPA+";
            case TelephonyManager.NETWORK_TYPE_HSUPA:
                return "HSUPA";
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return "iDen";
            case TelephonyManager.NETWORK_TYPE_LTE:
                return "LTE";
            case TelephonyManager.NETWORK_TYPE_UMTS:
                return "UMTS";
            case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                return "Unknown";
        }
        network_type.setText(getNetworkType());
        throw new RuntimeException("New type of network");
    }

    private String getSoftwareVersion() {
        String versionRelease = Build.VERSION.RELEASE;
        software_version.setText(versionRelease);
        return versionRelease;
    }

    public String DeviceApi(String url) {
        InputStream inputStream = null;
        String result = "";
        try {

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);

            String json = "";
            JSONObject jsonObject = new JSONObject();

            jsonObject.accumulate("user_id", DataHolder.getDataString(MainActivity.this, "userid"));
            jsonObject.accumulate("deviceid", getAndroidId());
            jsonObject.accumulate("IMEI", getImei());
            jsonObject.accumulate("IMSI", getImsi());
            jsonObject.accumulate("UUID", getUUID());
            jsonObject.accumulate("network_type", getNetworkType());
            jsonObject.accumulate("operator_name", getSimOperator());
            jsonObject.accumulate("device_name", getDeviceName());
            jsonObject.accumulate("softwareversion", getSoftwareVersion());

            json = jsonObject.toString();
            StringEntity se = new StringEntity(json);
            se.setContentType("application/json");

            httpPost.setEntity(new StringEntity(json));
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            HttpResponse httpResponse = httpclient.execute(httpPost);
            inputStream = httpResponse.getEntity().getContent();

            if (inputStream != null) {
                try {
                    result = convertInputStreamToString(inputStream);
                } catch (Exception e) {
                    Log.e("Check", "" + e);
                }
            } else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", "" + e);
        }

        return result;
    }

    public String getTimeApi(String url) {
        InputStream inputStream = null;
        String result = "";
        try {

            HttpClient httpclient = new DefaultHttpClient();
            HttpGet Httpget = new HttpGet(url);

            Httpget.setHeader("Accept", "application/json");
            Httpget.setHeader("Content-type", "application/json");
            Httpget.setHeader("Authorization", DataHolder.getDataString(MainActivity.this, "token"));

            HttpResponse httpResponse = httpclient.execute(Httpget);
            inputStream = httpResponse.getEntity().getContent();

            if (inputStream != null) {
                try {
                    result = convertInputStreamToString(inputStream);
                } catch (Exception e) {
                    Log.e("Check", "" + e);
                }
            } else
                result = "Did not work!";
            Log.e("Check", "how " + result);

        } catch (Exception e) {
            Log.d("InputStream", "" + e);
        }
        return result;
    }

    private class DevicePost extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            return DeviceApi(urls[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            Log.i("CheckDevice", "" + result);
            try {
                JSONObject jsonObjMain = new JSONObject(result.toString());

                String message = jsonObjMain.getString("message");
//                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class getTableAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            return getTimeApi(urls[0]);
        }

        @Override
        protected void onPostExecute(String result) {
//            Toast.makeText(MainActivity.this, "" + result, Toast.LENGTH_SHORT).show();
            Log.i("CheckTable", "" + result);
            {
                try {
                    JSONObject jsonObjMain = new JSONObject(result);
                    String message = jsonObjMain.getString("message");
                    Log.i("TAG", "" + message);

                    JSONArray array = new JSONArray(jsonObjMain.getString("data"));
                    for (int i = 0; i < array.length(); i++) {


                        JSONObject key = array.getJSONObject(i);

                        tableid = key.getString("tableid");
                        table_name = key.getString("table_name");
                        table_time = key.getString("table_time");

                        {
                            try {
                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
                                Date date = format.parse(table_time);
                                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.US);
                                Date current_time = Calendar.getInstance().getTime();
                                local_Time = sdf.format(current_time);
                                time_check = sdf.format(date);

                                Log.i("CurrentTime", "" + local_Time);
                                Log.i("ServerTime", "" + time_check);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            if (i == 0) {

                                if (Objects.equals(local_Time, time_check)) {
                                    startActivity(new Intent(MainActivity.this, LoadingScreen_private.class));
                                } else {
                                    TastyToast.makeText(MainActivity.this, "Table Not Available, Next table at " + time_check, TastyToast.LENGTH_LONG, TastyToast.ERROR);
//                                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//                                    builder.setMessage("Table Not Available, Next table will be Available at " + time_check);
//                                    builder.setCancelable(true);
//
//                                    builder.setPositiveButton(
//                                            "OK",
//                                            new DialogInterface.OnClickListener() {
//                                                public void onClick(DialogInterface dialog, int id) {
//                                                    dialog.cancel();
//                                                }
//                                            });
//
//                                    builder.setNegativeButton(
//                                            "No",
//                                            new DialogInterface.OnClickListener() {
//                                        public void onClick(DialogInterface dialog, int id) {
//                                            dialog.cancel();
//                                        }
//                                    });
//                                    AlertDialog alert = builder.create();
//                                    alert.show();
                                }
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String changeImageApi(String url) {
        InputStream inputStream = null;
        String result = "";
        try {

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);

            String json = "";
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("user_id", (DataHolder.getDataString(MainActivity.this, "userid")));
            jsonObject.accumulate("avatar_url", (DataHolder.Url1));

            json = jsonObject.toString();
            StringEntity se = new StringEntity(json);
            se.setContentType("application/json");

            httpPost.setEntity(new StringEntity(json));
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            HttpResponse httpResponse = httpclient.execute(httpPost);
            inputStream = httpResponse.getEntity().getContent();

            if (inputStream != null) {
                try {
                    result = convertInputStreamToString(inputStream);
                } catch (Exception e) {
                    Log.e("Check", "" + e);
                }
            } else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", "" + e);
        }

        return result;
    }

    private class changeimageAsyncTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... urls) {
            return changeImageApi(urls[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            Log.i("CheckImage", "" + s);
            try {
                JSONObject jsonObjMain = new JSONObject(s.toString());

                String message = jsonObjMain.getString("message");
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}

