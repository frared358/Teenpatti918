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
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
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

    //selecting avatar
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
                DataHolder.setData(LoginActivity.this,"token",jsonObjMain.getString("token").toString());


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