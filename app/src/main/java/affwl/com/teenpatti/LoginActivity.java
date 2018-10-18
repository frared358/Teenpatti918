package affwl.com.teenpatti;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
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
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_PHONE_STATE;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Session session;
    TextView playNow;
    CheckBox rememberMeCheckBox;

    EditText edittextusername, edittextpassword;
    String username, password, avatar;
    MediaPlayer mediaPlayer;

    private static final String TAG = "loginactivity";
    private ScheduledExecutorService scheduleTaskExecutor;

    private static final int PERMISSION_REQUEST_CODE = 200;
    private static final int PERMISSION_REQUEST_ACCESS_COARSE_LOCATION = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teenpatti_activity_login);

        edittextusername = findViewById(R.id.edittextusername);
        edittextpassword = findViewById(R.id.edittextpassword);
        rememberMeCheckBox = findViewById(R.id.rememberMeCheckBox);
        playNow = findViewById(R.id.playNow);
        playNow.setOnClickListener(this);
        rememberMeCheckBox.setOnClickListener(this);
        session = new Session(this);



        if (DataHolder.getDataBoolean(this, "remember_me") && DataHolder.getDataLong(this, "ExpiredDate") > System.currentTimeMillis()) {

            rememberMeCheckBox.setChecked(true);
            edittextusername.setText(DataHolder.getDataString(this, "username"));
            edittextpassword.setText(DataHolder.getDataString(this, "password"));
        }

//        new AvatarAsyncTask().execute("http://213.136.81.137:8081/api/getallavatar");
        checkPermission();
        requestPermission();

        scheduleTaskExecutor = Executors.newScheduledThreadPool(5);
        scheduleTaskExecutor.scheduleAtFixedRate(new Runnable() {
            public void run() {
                WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                int linkSpeed = wifiManager.getConnectionInfo().getRssi();
                int level = WifiManager.calculateSignalLevel(linkSpeed, 5);
                Log.i("SPEED", "WIFI level" + level);
                if (level <= 2) {
                    Toast.makeText(LoginActivity.this, "Slow Internet level = " + level, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "Internet Proper level = " + level, Toast.LENGTH_SHORT).show();
                }

            }
        }, 0, 1, TimeUnit.MINUTES);


    }

    //selecting avatar
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.playNow) {
            //get USERNAME and PASSWORD
            username = edittextusername.getText().toString();
            password = edittextpassword.getText().toString();
            if (username.equals("") || username == null) {
                edittextusername.setError("Enter User Name");
            } else if (password.equals("") || password == null) {
                edittextpassword.setError("Enter Password");
            } else {
                new HttpAsyncTask().execute("http://213.136.81.137:8081/api/authClient");
            }

        } else if (v.getId() == R.id.rememberMeCheckBox) {
            mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.click);
            mediaPlayer.start();
            if (rememberMeCheckBox.isChecked()) {
                DataHolder.setData(this, "remember_me", true);
            } else {
                DataHolder.setData(this, "remember_me", false);
            }
        }
    }

    public void saveLoginDetails(String username, String password, String avatar) {
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

                    if (locationAccepted && cameraAccepted && coarselocationAccepted && readphonestate) {
                        Log.i("TAG", "Permission Granted");
                    } else {
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

                DataHolder.setData(LoginActivity.this, "token", jsonObjMain.getString("token").toString());

                if (message.equalsIgnoreCase("successfully authenticated")) {
                    TastyToast.makeText(LoginActivity.this, "Login Successful", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                    JSONArray array = new JSONArray(jsonObjMain.getString("data"));
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject key = array.getJSONObject(i);
                        DataHolder.first_name = key.getString("first_name");
                        DataHolder.last_name = key.getString("last_name");
                        DataHolder.mobile_no = key.getString("mobile_no");
                        DataHolder.balance = key.getString("balance");
                        DataHolder.emailaddress = key.getString("emailaddress");
                        DataHolder.imageURL = key.getString("user_image");

                        DataHolder.setData(LoginActivity.this, "user_image", DataHolder.imageURL);
                        DataHolder.setData(LoginActivity.this, "deskid", key.getString("desk_id"));
                        DataHolder.setData(LoginActivity.this, "userstatus", key.getString("user_status"));
                        DataHolder.setData(LoginActivity.this, "userid", key.getString("userid"));

                        //Toast.makeText(LoginActivity.this, key.getString("desk_id")+" \n" + DataHolder.imageURL, Toast.LENGTH_SHORT).show();

                        Log.i("TAGTAGTAG", " " + DataHolder.first_name + " " + DataHolder.last_name + " " + DataHolder.mobile_no + " " + DataHolder.balance + " " + DataHolder.emailaddress);
                    }

                    if (rememberMeCheckBox.isChecked()) {
                        saveLoginDetails(username, password, avatar);
                    }
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));

                }
                Log.i("result", " Status " + message);

            } catch (JSONException e) {
                e.printStackTrace();
                try {
                    JSONObject jsonObjMain = new JSONObject(result.toString());
                    TastyToast.makeText(LoginActivity.this, jsonObjMain.getString("message"), TastyToast.LENGTH_LONG, TastyToast.DEFAULT);
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.click);
        mp.start();
        finish();
    }

    public String getApi(String url) {
        InputStream inputStream = null;
        String result = "";
        try {

            HttpClient httpclient = new DefaultHttpClient();
            HttpGet Httpget = new HttpGet(url);

            Httpget.setHeader("Accept", "application/json");
            Httpget.setHeader("Content-type", "application/json");
            Httpget.setHeader("Authorization", DataHolder.getDataString(LoginActivity.this, "token"));

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
}