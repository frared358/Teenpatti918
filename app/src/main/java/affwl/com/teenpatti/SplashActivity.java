package affwl.com.teenpatti;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class SplashActivity extends AppCompatActivity {

    View decorView;
    int uiOptions;
    Thread t;
    private static int SPLASH_TIME_OUT = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);


        decorView = this.getWindow().getDecorView();
        uiOptions = decorView.getSystemUiVisibility();
        uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;

        decorView.setSystemUiVisibility(uiOptions);

        t=new Thread(){

            @Override
            public void run(){
                while(!isInterrupted()){
                    try {
                        Thread.sleep(4000);  //1000ms = 1 sec

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.i("ani","out "+uiOptions);
                                if (decorView.getSystemUiVisibility() != uiOptions)  {
                                    decorView.setSystemUiVisibility(uiOptions);
                                    Log.i("ani","in "+uiOptions);
                                }
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


//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        t.interrupt();
//    }
}
