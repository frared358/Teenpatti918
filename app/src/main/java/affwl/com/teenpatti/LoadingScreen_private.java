package affwl.com.teenpatti;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class LoadingScreen_private extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen_private);
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);


        Animation animation = AnimationUtils.loadAnimation(this, R.anim.splash2);
        findViewById(R.id.loadingouter).startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                finish();
                startActivity(new Intent(LoadingScreen_private.this, PrivateActivity.class));

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        Animation myAnimation;
        myAnimation = AnimationUtils.loadAnimation(this, R.anim.inner_load);
        findViewById(R.id.loadinginner).startAnimation(myAnimation);
        myAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation myAnimation) {

            }

            @Override
            public void onAnimationEnd(Animation myAnimation) {
                finish();
                startActivity(new Intent(LoadingScreen_private.this, PrivateActivity.class));
            }

            @Override
            public void onAnimationRepeat(Animation myAnimation) {

            }
        });
    }
}
