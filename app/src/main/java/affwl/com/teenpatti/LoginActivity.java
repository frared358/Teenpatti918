package affwl.com.teenpatti;

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
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
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

import com.bumptech.glide.Glide;
import com.google.gson.JsonArray;
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
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_PHONE_STATE;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView selectimage, avatarimage, avatar1, avatar2, avatar3, avatar4, avatar5, avatar6, avatar7, avatar8, camera, choosepic;
    Session session;
    TextView playNow;
    CheckBox rememberMeCheckBox;
    LoginDatabaseHelper loginDatabaseHelper;
    DBHandler dbHandler;
    EditText edittextusername, edittextpassword;
    String username, password;
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

        selectimage = findViewById(R.id.selectimage);
        avatarimage = findViewById(R.id.avatarimage);
        avatar1 = findViewById(R.id.avatar1);
        avatar2 = findViewById(R.id.avatar2);
        avatar3 = findViewById(R.id.avatar3);
        avatar4 = findViewById(R.id.avatar4);
        avatar5 = findViewById(R.id.avatar5);
        avatar6 = findViewById(R.id.avatar6);
        avatar7 = findViewById(R.id.avatar7);
        avatar8 = findViewById(R.id.avatar8);
        camera = findViewById(R.id.camera);
        choosepic = findViewById(R.id.choosepic);

        avatar1.setOnClickListener(this);
        avatar2.setOnClickListener(this);
        avatar3.setOnClickListener(this);
        avatar4.setOnClickListener(this);
        avatar5.setOnClickListener(this);
        avatar6.setOnClickListener(this);
        avatar7.setOnClickListener(this);
        avatar8.setOnClickListener(this);
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

        new AvatarAsyncTask().execute("http://213.136.81.137:8081/api/getallavatar");
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
        ImageView image;
        int id = v.getId();
        Drawable img;
        if (id == R.id.avatar1) {
            image = findViewById(R.id.avatar1);
            img = image.getDrawable();
            mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.click);
            mediaPlayer.start();
            avatarimage.setImageDrawable(img);

        } else if (id == R.id.avatar2) {
            image = findViewById(R.id.avatar2);
            img = image.getDrawable();
            mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.click);
            mediaPlayer.start();
            avatarimage.setImageDrawable(img);

        } else if (id == R.id.avatar3) {
            image = findViewById(R.id.avatar3);
            img = image.getDrawable();
            mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.click);
            mediaPlayer.start();
            avatarimage.setImageDrawable(img);

        } else if (id == R.id.avatar4) {
            image = findViewById(R.id.avatar4);
            img = image.getDrawable();
            mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.click);
            mediaPlayer.start();
            avatarimage.setImageDrawable(img);

        } else if (id == R.id.avatar5) {
            image = findViewById(R.id.avatar5);
            img = image.getDrawable();
            mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.click);
            mediaPlayer.start();
            avatarimage.setImageDrawable(img);

        } else if (id == R.id.avatar6) {
            image = findViewById(R.id.avatar6);
            img = image.getDrawable();
            mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.click);
            mediaPlayer.start();
            avatarimage.setImageDrawable(img);

        } else if (id == R.id.avatar7) {
            image = findViewById(R.id.avatar7);
            img = image.getDrawable();
            mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.click);
            mediaPlayer.start();
            avatarimage.setImageDrawable(img);

        } else if (id == R.id.avatar8) {
            image = findViewById(R.id.avatar8);
            img = image.getDrawable();
            mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.click);
            mediaPlayer.start();
            avatarimage.setImageDrawable(img);

        } else if (id == R.id.camera) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 1);

        } else if (id == R.id.choosepic) {
            mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.click);
            mediaPlayer.start();
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
                new HttpAsyncTask().execute("http://213.136.81.137:8081/api/authClient");
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

            mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.click);
            mediaPlayer.start();

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

                        DataHolder.setData(LoginActivity.this, "userstatus", "offline");
                        DataHolder.setData(LoginActivity.this, "userid", key.getString("userid"));
                        Log.i("TAGTAGTAG", " " + DataHolder.first_name + " " + DataHolder.last_name + " " + DataHolder.mobile_no + " " + DataHolder.balance + " " + DataHolder.emailaddress);
                    }

                    if (rememberMeCheckBox.isChecked()) {
                        saveLoginDetails(username, password);
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

    String avatar_1, avatar_2, avatar_3, avatar_4, avatar_5, avatar_6, avatar_7, avatar_8;

    private class AvatarAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            return getApi(urls[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(LoginActivity.this, "" + result, Toast.LENGTH_SHORT).show();

            try {
                JSONObject jsonObjMain = new JSONObject(result.toString());
                String message = jsonObjMain.getString("message");
                ArrayList<String> avatarId = new ArrayList<>();
                Log.i("TAG", "" + message);


                JSONArray array = new JSONArray(jsonObjMain.getString("data"));
                for (int i = 0; i < array.length(); i++) {
                    JSONObject key = array.getJSONObject(i);

                    if (i == 0) {
                        String Url1 = key.getString("avatar_url");
                        avatar_1 = Url1;
                        Glide.with(getApplicationContext()).load(avatar_1).into(avatar1);
                        Log.i("URL 1", i + " " + Url1);
                    } else if (i == 1) {
                        String Url2 = key.getString("avatar_url");
                        avatar_2 = Url2;
                        Glide.with(getApplicationContext()).load(avatar_2).into(avatar2);
                        Log.i("URL 2", i + " " + Url2);
                    } else if (i == 2) {
                        String Url3 = key.getString("avatar_url");
                        avatar_3 = Url3;
                        Glide.with(getApplicationContext()).load(avatar_3).into(avatar3);
                        Log.i("URL 3", i + " " + Url3);
                    } else if (i == 3) {
                        String Url4 = key.getString("avatar_url");
                        avatar_4 = Url4;
                        Glide.with(getApplicationContext()).load(avatar_4).into(avatar4);
                        Log.i("URL 4", i + " " + Url4);
                    } else if (i == 4) {
                        String Url5 = key.getString("avatar_url");
                        avatar_5 = Url5;
                        Glide.with(getApplicationContext()).load(avatar_5).into(avatar5);
                        Log.i("URL 5", i + " " + Url5);
                    } else if (i == 5) {
                        String Url6 = key.getString("avatar_url");
                        avatar_6 = Url6;
                        Glide.with(getApplicationContext()).load(avatar_6).into(avatar6);
                        Log.i("URL 6", i + " " + Url6);
                    } else if (i == 6) {
                        String Url7 = key.getString("avatar_url");
                        avatar_7 = Url7;
                        Glide.with(getApplicationContext()).load(avatar_7).into(avatar7);
                        Log.i("URL 7", i + " " + Url7);
                    } else if (i == 7) {
                        String Url8 = key.getString("avatar_url");
                        avatar_8 = Url8;
                        Glide.with(getApplicationContext()).load(avatar_8).into(avatar8);
                        Log.i("URL 8", i + " " + Url8);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
//    public boolean checkNetworkConnection() {
//        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
//        boolean isConnected = false;
//        if (networkInfo != null && (isConnected = networkInfo.isConnected())) {
//            Toast.makeText(this, "Net Connected", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(this, "Net Not Connected", Toast.LENGTH_SHORT).show();
//        }
//        return isConnected;
//    }
}