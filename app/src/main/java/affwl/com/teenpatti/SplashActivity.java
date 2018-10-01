package affwl.com.teenpatti;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SplashActivity extends AppCompatActivity {

    View decorView;
    int uiOptions;
    Thread t;
    String IMEI;
    private static int SPLASH_TIME_OUT = 2000;
    private static final int PERMISSION_CALLBACK_CONSTANT = 100;
    private static final int REQUEST_PERMISSION_SETTING = 101;
    String[] permissionsRequired = new String[]{Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION};
    private TextView txtPermissions;
    private Button btnCheckPermissions;
    private SharedPreferences permissionStatus;
    private boolean sentToSettings = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);


        decorView = this.getWindow().getDecorView();
        uiOptions = decorView.getSystemUiVisibility();
        uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;

        permissionStatus = getSharedPreferences("permissionStatus",MODE_PRIVATE);

        decorView.setSystemUiVisibility(uiOptions);

        t = new Thread() {

            @Override
            public void run() {
                while (!isInterrupted()) {
                    try {
                        Thread.sleep(4000);  //1000ms = 1 sec

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
//                                Log.i("ani", "out " + uiOptions);
//                                if (decorView.getSystemUiVisibility() != uiOptions) {
//                                    decorView.setSystemUiVisibility(uiOptions);
//                                    Log.i("ani", "in " + uiOptions);
//                                }
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        t.start();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                startActivity(new Intent(SplashActivity.this, Splash2Activity.class));

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);


    }
}
