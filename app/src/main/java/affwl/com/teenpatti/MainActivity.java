package affwl.com.teenpatti;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

import com.bumptech.glide.Glide;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import static affwl.com.teenpatti.DataHolder.encodeimage;
import static android.provider.Settings.Secure.ANDROID_ID;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView join_game, avatarimage, avatar1, avatar2, avatar3, avatar4, avatar5, avatar6, avatar7, avatar8, showPopupBtn, closeRateus, closeHelpBtn, closeTrophyBtn, profile, orangechipsbtn, close312help, closesixpattihelp, short321info, tourney_shortinfo_closebtn, shortsixpattiinfo, bluechipsbtn, cyanchipsbtn, shortinfo_tourney, tourney_join_closebtn, ygreenchipsbtn, closebtn_create_table, mainlimegchipsbtn, variation_closebtn, facebook, whatsapp, general;
    PopupWindow HelpUspopupWindow, TrophypopupWindow, tounpopupWindow, howto321popup, sixpattipopup, howtosixpattipopup, join_tourney_popupWindow, shortinfo_tourney_popupwindow, create_table_private_popupwindow, join_table_popupwindow;
    RelativeLayout RelativeLayoutloader, relativelayout321, relativeLayoutsixpatti, relativeLayout_tourney, yellowchiplayout, orangechipslayout, limechipslayout, darkbluechiplayout, blackchipslayout, cyanchipslayout, ygreenchipslayout;
    TextView txtVUserNameMain;
    Session session;
    String time_check, local_Time, tableid, table_name, table_time;
    MediaPlayer mediaPlayer;
    Drawable img;
    ImageView image;
    Date sdate, current_time;


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

        ScheduledExecutorService scheduleTaskExecutor = Executors.newScheduledThreadPool(5);
        scheduleTaskExecutor.scheduleAtFixedRate(new Runnable() {
            public void run() {
                WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                int linkSpeed = wifiManager.getConnectionInfo().getRssi();
                int level = WifiManager.calculateSignalLevel(linkSpeed, 5);
                Log.i("SPEED", "WIFI level " + level);
                if (level == 5 || level == 4 || level == 3) {
                    TastyToast.makeText(getApplicationContext(), "Internet Proper " + level, TastyToast.LENGTH_LONG, TastyToast.DEFAULT);
                } else if (level == 2 || level == 1 || level == 0) {
                    TastyToast.makeText(getApplicationContext(), "Internet Moderate " + level, TastyToast.LENGTH_LONG, TastyToast.DEFAULT);
                } else {
                    TastyToast.makeText(getApplicationContext(), "Internet Slow " + level, TastyToast.LENGTH_LONG, TastyToast.DEFAULT);
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
        join_game = findViewById(R.id.join_game);

        avatar1 = findViewById(R.id.avatar1);
        avatar2 = findViewById(R.id.avatar2);
        avatar3 = findViewById(R.id.avatar3);
        avatar4 = findViewById(R.id.avatar4);
        avatar5 = findViewById(R.id.avatar5);
        avatar6 = findViewById(R.id.avatar6);
        avatar7 = findViewById(R.id.avatar7);
        avatar8 = findViewById(R.id.avatar8);
        showPopupBtn = findViewById(R.id.help_btn_loader);

        profile = findViewById(R.id.profile);


        avatar1.setOnClickListener(this);
        avatar2.setOnClickListener(this);
        avatar3.setOnClickListener(this);
        avatar4.setOnClickListener(this);
        avatar5.setOnClickListener(this);
        avatar6.setOnClickListener(this);
        avatar7.setOnClickListener(this);
        avatar8.setOnClickListener(this);
        showPopupBtn.setOnClickListener(this);

        Glide.with(getApplicationContext()).load(DataHolder.getDataString(this, "user_image")).into(profile);
        txtVUserNameMain = findViewById(R.id.txtVUserNameMain);

        RelativeLayoutloader = findViewById(R.id.linearLayoutloader);
        session = new Session(this);
        encodeimage = session.getImage();
        if (!encodeimage.equalsIgnoreCase("")) {
            byte[] b = Base64.decode(encodeimage, Base64.DEFAULT);
            Bitmap bmp = BitmapFactory.decodeByteArray(b, 0, b.length);
            profile.setImageBitmap(bmp);
        }

        txtVUserNameMain.setText(DataHolder.first_name + " " + DataHolder.last_name);

        //////////////// Popup for Private ////////////////
        RelativeLayoutloader = findViewById(R.id.linearLayoutloader);


        RelativeLayoutloader = findViewById(R.id.linearLayoutloader);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.mainactivity_chips_rotate);

        findViewById(R.id.join_game).startAnimation(animation);
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
        new AvatarAsyncTask().execute("http://213.136.81.137:8081/api/getallavatar");
        new updateUserStatusAsyncTask().execute("http://213.136.81.137:8081/api/update_client_status");

    }

    String AVATAR;

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {

            case R.id.avatar1:
                image = findViewById(R.id.avatar1);
                img = image.getDrawable();
                mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.click);
                mediaPlayer.start();
                profile.setImageDrawable(img);
                new changeimageAsyncTask().execute("http://213.136.81.137:8081/api/changeProfile");
                DataHolder.imageURL = "http://213.136.81.137/Avatar/avatar1.png";
                break;

            case R.id.avatar2:
                image = findViewById(R.id.avatar2);
                img = image.getDrawable();
                mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.click);
                mediaPlayer.start();
                profile.setImageDrawable(img);
                new changeimageAsyncTask().execute("http://213.136.81.137:8081/api/changeProfile");
                DataHolder.imageURL = "http://213.136.81.137/Avatar/avatar2.png";
                break;

            case R.id.avatar3:
                image = findViewById(R.id.avatar3);
                img = image.getDrawable();
                mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.click);
                mediaPlayer.start();
                profile.setImageDrawable(img);
                new changeimageAsyncTask().execute("http://213.136.81.137:8081/api/changeProfile");
                DataHolder.imageURL = "http://213.136.81.137/Avatar/avatar3.png";
                break;

            case R.id.avatar4:
                image = findViewById(R.id.avatar4);
                img = image.getDrawable();
                mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.click);
                mediaPlayer.start();
                profile.setImageDrawable(img);
                new changeimageAsyncTask().execute("http://213.136.81.137:8081/api/changeProfile");
                DataHolder.imageURL = "http://213.136.81.137/Avatar/avatar4.png";
                break;

            case R.id.avatar5:
                image = findViewById(R.id.avatar5);
                img = image.getDrawable();
                mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.click);
                mediaPlayer.start();
                profile.setImageDrawable(img);
                new changeimageAsyncTask().execute("http://213.136.81.137:8081/api/changeProfile");
                DataHolder.imageURL = "http://213.136.81.137/Avatar/avatar5.png";
                break;

            case R.id.avatar6:
                image = findViewById(R.id.avatar6);
                img = image.getDrawable();
                mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.click);
                mediaPlayer.start();
                profile.setImageDrawable(img);
                new changeimageAsyncTask().execute("http://213.136.81.137:8081/api/changeProfile");
                DataHolder.imageURL = "http://213.136.81.137/Avatar/avatar6.png";
                break;

            case R.id.avatar7:
                image = findViewById(R.id.avatar7);
                img = image.getDrawable();
                mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.click);
                mediaPlayer.start();
                profile.setImageDrawable(img);
                new changeimageAsyncTask().execute("http://213.136.81.137:8081/api/changeProfile");
                DataHolder.imageURL = "http://213.136.81.137/Avatar/avatar7.png";
                break;

            case R.id.avatar8:
                image = findViewById(R.id.avatar8);
                img = image.getDrawable();
                mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.click);
                mediaPlayer.start();
                profile.setImageDrawable(img);
                new changeimageAsyncTask().execute("http://213.136.81.137:8081/api/changeProfile");
                DataHolder.imageURL = "http://213.136.81.137/Avatar/avatar8.png";
                break;

            case R.id.help_btn_loader:
                RelativeLayoutloader = findViewById(R.id.linearLayoutloader);

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
                break;
        }
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

    public void OpenPrivateTable(View view) {
        startActivity(new Intent(MainActivity.this, LoadingScreenPrivate.class));
//        new getTableAsyncTask().execute("http://213.136.81.137:8081/api/getTableinfo");
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.click);
        mediaPlayer.start();
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

                        DataHolder.tableid = key.getString("tableid");
                        DataHolder.table_name = key.getString("table_name");
                        DataHolder.table_time = key.getString("table_time");
                        Log.i("CURRENTTIME", "" + DataHolder.table_time);

                        {
                            try {
                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
                                sdate = format.parse(DataHolder.table_time);
                                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.US);
                                current_time = Calendar.getInstance().getTime();
                                local_Time = sdf.format(current_time);
                                time_check = sdf.format(sdate);

                                Log.i("CurrentTime", "" + local_Time);
                                Log.i("ServerTime", "" + time_check);

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            if (i == 0) {

                                if (Objects.equals(local_Time, time_check)) {
                                    startActivity(new Intent(MainActivity.this, LoadingScreenPrivate.class));
                                } else {
//                                    TastyToast.makeText(MainActivity.this, "Table Not Available, Next table at " + time_check, TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                    builder.setMessage("Table Not Available, Next table will be Available at " + time_check);
                                    builder.setCancelable(true);

                                    builder.setPositiveButton(
                                            "OK",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    dialog.cancel();
                                                }
                                            });
                                    AlertDialog alert = builder.create();
                                    alert.show();
                                }
                                long delaytime = current_time.getTime() - sdate.getTime();

                                Log.i("Delay_time", "" + delaytime);

                                Handler h = new Handler();
                                h.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (local_Time.equals(time_check)) {
                                            startActivity(new Intent(getApplicationContext(), LoadingScreenPrivate.class));
                                        }
                                    }
                                }, delaytime);
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
            jsonObject.accumulate("avatar_url", DataHolder.imageURL);

            json = jsonObject.toString();
            StringEntity se = new StringEntity(json);
            se.setContentType("application/json");

            httpPost.setEntity(new StringEntity(json));
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            httpPost.setHeader("Authorization", DataHolder.getDataString(MainActivity.this, "token"));
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

    private class changeimageAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            return changeImageApi(urls[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            Log.i("CheckImage", "" + s);
            try {
                JSONObject jsonObjMain = new JSONObject(s);

                String message = jsonObjMain.getString("message");
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public String getApi(String url) {
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

    String avatar_1, avatar_2, avatar_3, avatar_4, avatar_5, avatar_6, avatar_7, avatar_8;
    ArrayList<String> arrayListAvatar = new ArrayList<>();

    private class AvatarAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            return getApi(urls[0]);
        }

        @Override
        protected void onPostExecute(String result) {
//            Toast.makeText(MainActivity.this, "" + result, Toast.LENGTH_SHORT).show();

            try {
                JSONObject jsonObjMain = new JSONObject(result.toString());
                String message = jsonObjMain.getString("message");
                ArrayList<String> avatarId = new ArrayList<>();
                Log.i("TAGGAT", "" + message);


                JSONArray array = new JSONArray(jsonObjMain.getString("data"));
                for (int i = 0; i < array.length(); i++) {
                    JSONObject key = array.getJSONObject(i);

                    if (i == 0) {
                        DataHolder.Url1 = key.getString("avatar_url");
                        avatar_1 = DataHolder.Url1;
                        Glide.with(getApplicationContext()).load(avatar_1).into(avatar1);
                        Log.i("URL 1", i + " " + DataHolder.Url1);
                    } else if (i == 1) {
                        DataHolder.Url2 = key.getString("avatar_url");
                        avatar_2 = DataHolder.Url2;
                        Glide.with(getApplicationContext()).load(avatar_2).into(avatar2);
                        Log.i("URL 2", i + " " + DataHolder.Url2);
                    } else if (i == 2) {
                        DataHolder.Url3 = key.getString("avatar_url");
                        avatar_3 = DataHolder.Url3;
                        Glide.with(getApplicationContext()).load(avatar_3).into(avatar3);
                        Log.i("URL 3", i + " " + DataHolder.Url3);
                    } else if (i == 3) {
                        DataHolder.Url4 = key.getString("avatar_url");
                        avatar_4 = DataHolder.Url4;
                        Glide.with(getApplicationContext()).load(avatar_4).into(avatar4);
                        Log.i("URL 4", i + " " + DataHolder.Url4);
                    } else if (i == 4) {
                        DataHolder.Url5 = key.getString("avatar_url");
                        avatar_5 = DataHolder.Url5;
                        Glide.with(getApplicationContext()).load(avatar_5).into(avatar5);
                        Log.i("URL 5", i + " " + DataHolder.Url5);
                    } else if (i == 5) {
                        DataHolder.Url6 = key.getString("avatar_url");
                        avatar_6 = DataHolder.Url6;
                        Glide.with(getApplicationContext()).load(avatar_6).into(avatar6);
                        Log.i("URL 6", i + " " + DataHolder.Url6);
                    } else if (i == 6) {
                        DataHolder.Url7 = key.getString("avatar_url");
                        avatar_7 = DataHolder.Url7;
                        Glide.with(getApplicationContext()).load(avatar_7).into(avatar7);
                        Log.i("URL 7", i + " " + DataHolder.Url7);
                    } else if (i == 7) {
                        DataHolder.Url8 = key.getString("avatar_url");
                        avatar_8 = DataHolder.Url8;
                        Glide.with(getApplicationContext()).load(avatar_8).into(avatar8);
                        Log.i("URL 8", i + " " + DataHolder.Url8);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class updateUserStatusAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            DataHolder.setData(MainActivity.this, "userstatus", "offline");
            return DataHolder.updateUserStatusApi(urls[0], MainActivity.this, "offline");
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject jsonObjMain = new JSONObject(result.toString());

                String message = jsonObjMain.getString("message");
                if (message.equalsIgnoreCase("Client status successfully changed")) {
                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}

