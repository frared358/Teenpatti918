package affwl.com.teenpatti;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class Splash2Activity extends AppCompatActivity implements Runnable{

    private static int TIMEOUT_POLL_PERIOD=15000; // 15 seconds
    private static int TIMEOUT_PERIOD=5*60*1000; // 5 minutes
    private View content=null;
    private long lastActivity=SystemClock.uptimeMillis();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash2);
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        content=findViewById(android.R.id.content);
        content.setKeepScreenOn(true);
        run();


        Animation animation = AnimationUtils.loadAnimation(this, R.anim.splash2);
        findViewById(R.id.loadingouter).startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startActivity(new Intent(Splash2Activity.this, LoginActivity.class));
                finish();

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        Animation myani;
        myani = AnimationUtils.loadAnimation(this, R.anim.inner_load);
        findViewById(R.id.loadinginner).startAnimation(myani);
        myani.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation myanim) {

            }

            @Override
            public void onAnimationEnd(Animation myanim) {
                startActivity(new Intent(Splash2Activity.this, LoginActivity.class));
                finish();

            }

            @Override
            public void onAnimationRepeat(Animation myanim) {

            }
        });
        lastActivity=SystemClock.uptimeMillis();
    }

    @Override
    public void onDestroy() {
        content.removeCallbacks(this);
        super.onDestroy();
    }

    @Override
    public void run() {
        if ((SystemClock.uptimeMillis() - lastActivity) > TIMEOUT_PERIOD) {
            content.setKeepScreenOn(false);
        }
        content.postDelayed(Splash2Activity.this, TIMEOUT_POLL_PERIOD);
    }
}
