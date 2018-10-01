package affwl.com.teenpatti;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_PHONE_STATE;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView selectimage, avatarimage, avatar1, avatar2, avatar3, avatar4, avatar5, avatar6, avatar7, avatar8, camera, choosepic;
    Session session;
    TextView playNow, balancetext;
    CheckBox rememberMeCheckBox;
    LoginDatabaseHelper loginDatabaseHelper;
    DBHandler dbHandler;
    EditText edittextusername, edittextpassword;
    String username, password, userid, deviceid, devicename, softwareversion, operatorname, networktype, imei, imsi, uuid;

    private TextView user_id;
    private TextView android_id;
    private TextView imei_number;
    private TextView imsi_number;
    private TextView uuid_number;
    private TextView device_name;
    private TextView sim_operator;
    private TextView network_type;
    private TextView software_version;
    private static final String TAG = "loginactivity";

    private static final int PERMISSION_REQUEST_CODE = 200;
    private static final int PERMISSION_REQUEST_ACCESS_COARSE_LOCATION = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teenpatti_activity_login);

        user_id = findViewById(R.id.user_id);
        android_id = findViewById(R.id.android_id);
        imei_number = findViewById(R.id.imei);
        imsi_number = findViewById(R.id.imsi);
        uuid_number = findViewById(R.id.uuid);
        network_type = findViewById(R.id.network_type);
        sim_operator = findViewById(R.id.sim_operator);
        device_name = findViewById(R.id.device_name);
        software_version = findViewById(R.id.software_version);


        edittextusername = findViewById(R.id.edittextusername);
        edittextpassword = findViewById(R.id.edittextpassword);
        rememberMeCheckBox = findViewById(R.id.rememberMeCheckBox);
        playNow = findViewById(R.id.playNow);

        selectimage = findViewById(R.id.selectimage);
        avatarimage = findViewById(R.id.avatarimage);
        avatar1 = findViewById(R.id.avatar1);
        avatar2 = findViewById(R.id.avatar2);
        avatar3 = findViewById(R.id.avatar3);
        avatar4 = findViewById(R.id.avatar4);
        avatar5 = findViewById(R.id.avatar5);
        avatar6 = findViewById(R.id.avatar6);
        avatar7 = findViewById(R.id.avatar7);
        camera = findViewById(R.id.camera);
        choosepic = findViewById(R.id.choosepic);

        avatar1.setOnClickListener(this);
        avatar2.setOnClickListener(this);
        avatar3.setOnClickListener(this);
        avatar4.setOnClickListener(this);
        avatar5.setOnClickListener(this);
        avatar6.setOnClickListener(this);
        avatar7.setOnClickListener(this);
        camera.setOnClickListener(this);
        choosepic.setOnClickListener(this);
        playNow.setOnClickListener(this);
        rememberMeCheckBox.setOnClickListener(this);
        session = new Session(this);
        loginDatabaseHelper = new LoginDatabaseHelper(this);
        dbHandler = new DBHandler(this, "UserInfo", null, 1);

        if (DataHolder.getDataBoolean(this, "remember_me") && DataHolder.getDataLong(this, "ExpiredDate") > System.currentTimeMillis()) {

            rememberMeCheckBox.setChecked(true);
            edittextusername.setText(DataHolder.getDataString(this, "username"));
            edittextpassword.setText(DataHolder.getDataString(this, "password"));

        }

        checkPermission();
        requestPermission();

    }

    //Implementation of selecting avatar
    @Override
    public void onClick(View v) {
        ImageView image;
        int id = v.getId();
        Drawable img;
        if (id == R.id.avatar1) {
            image = findViewById(R.id.avatar1);
            img = image.getDrawable();
            avatarimage.setImageDrawable(img);
        } else if (id == R.id.avatar2) {
            image = findViewById(R.id.avatar2);
            img = image.getDrawable();
            avatarimage.setImageDrawable(img);
        } else if (id == R.id.avatar3) {
            image = findViewById(R.id.avatar3);
            img = image.getDrawable();
            avatarimage.setImageDrawable(img);
        } else if (id == R.id.avatar4) {
            image = findViewById(R.id.avatar4);
            img = image.getDrawable();
            avatarimage.setImageDrawable(img);
        } else if (id == R.id.avatar5) {
            image = findViewById(R.id.avatar5);
            img = image.getDrawable();
            avatarimage.setImageDrawable(img);
        } else if (id == R.id.avatar6) {
            image = findViewById(R.id.avatar6);
            img = image.getDrawable();
            avatarimage.setImageDrawable(img);
        } else if (id == R.id.avatar7) {
            image = findViewById(R.id.avatar7);
            img = image.getDrawable();
            avatarimage.setImageDrawable(img);
        } else if (id == R.id.camera) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 1);
        } else if (id == R.id.choosepic) {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 6);
        } else if (id == R.id.playNow) {

            //get USERNAME and PASSWORD
            username = edittextusername.getText().toString();
            password = edittextpassword.getText().toString();
            if (username.equals("") || username == null) {
                edittextusername.setError("Enter User Name");
            } else if (password.equals("") || password == null) {
                edittextpassword.setError("Enter Password");
            } else {
                new HttpAsyncTask().execute("http://213.136.81.137:8081/api/authenticate");
            }

            userid = user_id.getText().toString();
            deviceid = android_id.getText().toString();
            devicename = device_name.getText().toString();
            softwareversion = software_version.getText().toString();
            operatorname = sim_operator.getText().toString();
            networktype = network_type.getText().toString();
            imei = imei_number.getText().toString();
            imsi = imsi_number.getText().toString();
            uuid = uuid_number.getText().toString();
            if (userid == null || deviceid == null || devicename == null || operatorname == null || softwareversion == null || networktype == null || imei == null || imsi == null || uuid == null ) {
                Toast.makeText(this, "Data Not Sent", Toast.LENGTH_SHORT).show();
            } else {
                new DevicePost().execute("http://213.136.81.137:8081/api/adevice/");
            }

            //Add Avatar
            if (avatarimage.getDrawable() == null) {
                displayAlertMessage("Teenpatti", "Please select a image");
            } else {
                Bitmap bmp = ((BitmapDrawable) avatarimage.getDrawable()).getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] b = baos.toByteArray();
                String encodeimage = Base64.encodeToString(b, Base64.DEFAULT);
                String setNameHere = edittextusername.getText().toString().trim();
                session.put(encodeimage, setNameHere);
                long result = loginDatabaseHelper.add(encodeimage, setNameHere);
            }

        } else if (v.getId() == R.id.rememberMeCheckBox) {
            if (rememberMeCheckBox.isChecked()) {
                DataHolder.setData(this, "remember_me", true);
            } else {
                DataHolder.setData(this, "remember_me", false);
            }
        }
    }

    public void saveLoginDetails(String username, String password) {
        DataHolder.setData(this, "username", username);
        DataHolder.setData(this, "password", password);
        DataHolder.setData(this, "ExpiredDate", System.currentTimeMillis() + TimeUnit.HOURS.toMillis(24));
    }

    private void displayAlertMessage(String title, String message) {

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

        alert_box_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myAlertDialog.dismiss();
            }
        });

        tv_alert_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myAlertDialog.dismiss();
            }
        });

        tv_alert_cancel.setVisibility(View.GONE);
        myAlertDialog.show();

    }

    //Camera Implementation
    @Override
    protected void onActivityResult(int requestCode, int resultcode, Intent data) {
        if (requestCode == 1) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            avatarimage.setImageBitmap(bitmap);

        }
        if (requestCode == 6) {
            // Implementaion of Gallary
            Bitmap bitmap = null;
            if (resultcode == RESULT_OK) {
                Uri selectedimageuri = data.getData();
                String selectedimagepath = getPath(selectedimageuri);
                avatarimage.setImageURI(selectedimageuri);
            }
        }
    }

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_FINE_LOCATION);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_COARSE_LOCATION);
        int result2 = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);
        int result3 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_PHONE_STATE);

        return result == PackageManager.PERMISSION_GRANTED &&
                result1 == PackageManager.PERMISSION_GRANTED &&
                result2 == PackageManager.PERMISSION_GRANTED &&
                result3 == PackageManager.PERMISSION_GRANTED;
    }

    public void requestPermission() {

        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION, CAMERA, READ_PHONE_STATE}, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {

                    boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean cameraAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean coarselocationAccepted = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                    boolean readphonestate = grantResults[3] == PackageManager.PERMISSION_GRANTED;

                    if (locationAccepted && cameraAccepted && coarselocationAccepted && readphonestate)
                        Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                    else {
                        Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION)) {
                                showMessageOKCancel("You need to allow access to the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION, CAMERA, READ_PHONE_STATE}, PERMISSION_REQUEST_CODE);
                                                }
                                            }
                                        });
                                return;
                            }
                        }
                    }
                }
                break;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(LoginActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
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

    public String loginApi(String url) {
        InputStream inputStream = null;
        String result = "";
        try {

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);

            String json = "";
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("user_name", username);
            jsonObject.accumulate("password", password);
            jsonObject.accumulate("userid",userid);

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

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            return loginApi(urls[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            Log.i("CheckLogin", "" + result);
            try {
                JSONObject jsonObjMain = new JSONObject(result.toString());

                String message = jsonObjMain.getString("message");

                if (message.equalsIgnoreCase("successfully authenticated")) {

                    JSONArray array = new JSONArray(jsonObjMain.getString("data"));
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject key = array.getJSONObject(i);
                        DataHolder.user_id = key.getString("userid");
                        DataHolder.first_name = key.getString("first_name");
                        DataHolder.last_name = key.getString("last_name");
                        DataHolder.mobile_no = key.getString("mobile_no");
                        DataHolder.balance = key.getString("balance");
                        DataHolder.emailaddress = key.getString("emailaddress");
                        Log.i("TAGTAGTAG", DataHolder.user_id + " " + DataHolder.first_name + " " + DataHolder.last_name + " " + DataHolder.mobile_no + " " + DataHolder.balance + " " + DataHolder.emailaddress);
                    }
                    DataHolder.setData(LoginActivity.this, "userid", DataHolder.user_id);

                    if (rememberMeCheckBox.isChecked()) {
                        saveLoginDetails(username, password);
                    }
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));

                } else {
                    Toast.makeText(getApplicationContext(), "Enter Correct Details", Toast.LENGTH_SHORT).show();
                }
                Log.i("result", " Status " + message);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public String deviceApi(String url) {
        InputStream inputStream = null;
        String result = "";
        try {

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);

            String json = "";
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("user_id", user_id);
            jsonObject.accumulate("deviceid", deviceid);
            jsonObject.accumulate("device_name",devicename);
            jsonObject.accumulate("softwareversion",softwareversion);
            jsonObject.accumulate("operator_name",operatorname);
            jsonObject.accumulate("network_type",networktype);
            jsonObject.accumulate("IMEI",imei);
            jsonObject.accumulate("IMSI",imsi);
            jsonObject.accumulate("UUID",uuid);

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

    private class DevicePost extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            return deviceApi(urls[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            Log.i("CheckDevice", "" + result);
            try {
                JSONObject jsonObjMain = new JSONObject(result.toString());

                String message = jsonObjMain.getString("message");

                if (message.equalsIgnoreCase("Device information registered sucessfully")) {

                    JSONArray array = new JSONArray(jsonObjMain.getString("data"));
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject key = array.getJSONObject(i);
                        DataHolder.user_id = key.getString("user_id");
                        DataHolder.deviceid = key.getString("deviceid");
                        DataHolder.device_name = key.getString("device_name");
                        DataHolder.softwareversion = key.getString("softwareversion");
                        DataHolder.operator_name = key.getString("operator_name");
                        DataHolder.network_type = key.getString("network_type");
                        DataHolder.IMEI = key.getString("IMEI");
                        DataHolder.IMSI = key.getString("IMSI");
                        DataHolder.UUID = key.getString("UUID");

                        Log.i("TAGTAGTAG", DataHolder.user_id + " " + DataHolder.deviceid + " " + DataHolder.device_name + " " + DataHolder.softwareversion + " " + DataHolder.operator_name + " " + DataHolder.network_type + " " + DataHolder.IMEI + " " + DataHolder.IMSI + " " + DataHolder.UUID);
                    }
                    DataHolder.setData(LoginActivity.this, "user_id", DataHolder.user_id);
                    DataHolder.setData(LoginActivity.this, "deviceid", DataHolder.deviceid);
                    DataHolder.setData(LoginActivity.this, "device_name", DataHolder.device_name);
                    DataHolder.setData(LoginActivity.this, "softwareversion", DataHolder.softwareversion);
                    DataHolder.setData(LoginActivity.this, "operator_name", DataHolder.operator_name);
                    DataHolder.setData(LoginActivity.this, "network_type", DataHolder.network_type);
                    DataHolder.setData(LoginActivity.this, "IMEI", DataHolder.IMEI);
                    DataHolder.setData(LoginActivity.this, "IMSI", DataHolder.IMSI);
                    DataHolder.setData(LoginActivity.this, "UUID", DataHolder.UUID);

                    Toast.makeText(LoginActivity.this, "Success", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getApplicationContext(), "Registration Unsuccessful", Toast.LENGTH_SHORT).show();
                }
                Log.i("result", " Status " + message);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

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
        androidId = "" + android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
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
        return telephonyManager.getDeviceId();
    }

    private String getAndroidId() {
        // TODO Auto-generated method stub
        return Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
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
        throw new RuntimeException("New type of network");
    }

    private String getSoftwareVersion() {
        String versionRelease = Build.VERSION.RELEASE;
        software_version.setText(versionRelease);
        return versionRelease;
    }
}