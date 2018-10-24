package affwl.com.teenpatti;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.percent.PercentLayoutHelper;
import android.support.percent.PercentRelativeLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dinuscxj.progressbar.CircleProgressBar;
import com.google.gson.JsonObject;
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
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import pl.droidsonroids.gif.GifImageView;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;
import static android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP;

@SuppressWarnings("deprecation")
@SuppressLint("WrongViewCast")

public class PrivateActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView player_blink_circle1, player_blink_circle2, player_blink_circle3, player_blink_circle4, handle_right, backbtn, imgVInfo, infoclosebtn, profile, profile1, profile2, profile3, profile4, plus_btn, minus_btn, myplayerbtn, ustatusclosebtn, dealerbtn, dealerclsbtn, oplayerbtn, oustatusclosebtn, msgclosebtn, chngdealerclosebtn, close_player_status, oplayer_status_circle, player_status_circle, card1, card2, card3, card4, card5, card6, card7, card8, card9, card10, card11, card12, card13, card14, card15, myplayer, winnerblinker1, winnerblinker2, player1, player2, player3, player4;
    TextView player_balance, player_name, other_player_name, other_player_balance, displayAmount, txtVBalanceMainPlayer, txtVTableAmt, txtVBootValue, txtVPotlimit, txtVMaxBlind, txtVChaalLimit, btn_see_cards, user_id, user_id1, user_id2, user_id3, user_id4, closebtn, tipsbtn, chngdbtn, canceltipbtn, plusbtn, minusbtn, backtolobby, nametext, nametext1, nametext2, nametext3, nametext4, code, blind_btn, chaal_btn, show_btn, pack_btn;
    TextView user_status1, user_status2, user_status3, user_status4, boot_value_player1, boot_value_player2, boot_value_player3, boot_value_player4, boot_value_player5;
    PopupWindow popupWindow, infopopupWindow, chatpopupWindow, ustatuspopupWindow, dealerpopupWindow, oustatuspopupWindow, sendmsgpopupWindow, chngdpopupWindow, PlayerStatusWindow, OPlayerStatusWindow;
    RelativeLayout relativeLayout, relativeLayout2, relativeLayout3, privatetble;

    LinearLayout below_layout;
    GifImageView blinkergif1, blinkergif2, blinkergif3, blinkergif4, blinkergif5;

    Animation bootvalueanimate1, bootvalueanimate2, bootvalueanimate3, bootvalueanimate4, bootvalueanimate5, animations, animatecard1, animatecard2, animatecard3, animatecard4, animatecard5, animatecard6, animatecard7, animatecard8, animatecard9, animatecard10, animatecard11, animatecard12, animatecard13, animatecard14, animatecard15, animBlink;
    PercentRelativeLayout rl_bottom_caption;
    View viewBlinkCircle;
    Handler handler;
    private CircleProgressBar progressBarChances;
    ScheduledExecutorService scheduleTaskExecutor;
    MediaPlayer mediaPlayer;
    String USER_NAME, USER_NAME1, USER_NAME2, USER_NAME3, USER_NAME4, BALANCE, BALANCE1, BALANCE2, BALANCE3, BALANCE4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private);

        new updateUserStatusAsyncTask().execute("http://213.136.81.137:8081/api/update_client_status", "online");

        scheduleTaskExecutor = Executors.newScheduledThreadPool(5);
        scheduleTaskExecutor.scheduleAtFixedRate(new Runnable() {
            public void run() {
                WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                int linkSpeed = wifiManager.getConnectionInfo().getRssi();
                int level = WifiManager.calculateSignalLevel(linkSpeed, 5);
                Log.i("SPEED", "WIFI level" + level);
                if (level <= 2) {
                    Toast.makeText(PrivateActivity.this, "Slow Internet level = " + level, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(PrivateActivity.this, "Internet Proper level = " + level, Toast.LENGTH_SHORT).show();
                }

            }
        }, 0, 1, TimeUnit.MINUTES);
        other_player_name = findViewById(R.id.other_player_name);
        other_player_balance = findViewById(R.id.other_player_balance);


        rl_bottom_caption = findViewById(R.id.rl_bottom_caption);
        below_layout = findViewById(R.id.below_layout);
        relativeLayout = findViewById(R.id.privaterecycler);
        txtVTableAmt = findViewById(R.id.txtVTableAmt);
        blinkergif1 = findViewById(R.id.blinkergif1);
        blinkergif2 = findViewById(R.id.blinkergif2);
        blinkergif3 = findViewById(R.id.blinkergif3);
        blinkergif4 = findViewById(R.id.blinkergif4);
        blinkergif5 = findViewById(R.id.blinkergif5);
        progressBarChances = findViewById(R.id.progressBarChances);
        viewBlinkCircle = new View(PrivateActivity.this);

        //  implemention of user Profile pic
        profile = findViewById(R.id.inner_player_img);
        profile.setOnClickListener(this);
        profile1 = findViewById(R.id.inner_player_img1);
        profile1.setOnClickListener(this);
        profile2 = findViewById(R.id.inner_player_img2);
        profile2.setOnClickListener(this);
        profile3 = findViewById(R.id.inner_player_img3);
        profile3.setOnClickListener(this);
        profile4 = findViewById(R.id.inner_player_img4);
        profile4.setOnClickListener(this);

        nametext = findViewById(R.id.nametext);
        nametext1 = findViewById(R.id.nametext1);
        nametext2 = findViewById(R.id.nametext2);
        nametext3 = findViewById(R.id.nametext3);
        nametext4 = findViewById(R.id.nametext4);

        user_id = findViewById(R.id.user_id);
        user_id1 = findViewById(R.id.user_id1);
        user_id2 = findViewById(R.id.user_id2);
        user_id3 = findViewById(R.id.user_id3);
        user_id4 = findViewById(R.id.user_id4);

        user_status1 = findViewById(R.id.user_status1);
        user_status2 = findViewById(R.id.user_status2);
        user_status3 = findViewById(R.id.user_status3);
        user_status4 = findViewById(R.id.user_status4);

        txtVBalanceMainPlayer = findViewById(R.id.txtVBalanceMainPlayer);


        blind_btn = findViewById(R.id.blind_btn);
        blind_btn.setOnClickListener(this);
        chaal_btn = findViewById(R.id.chaal_btn);
        chaal_btn.setOnClickListener(this);

        displayAmount = findViewById(R.id.start_amount);
        plus_btn = findViewById(R.id.plus_btn);
        plus_btn.setOnClickListener(this);
        minus_btn = findViewById(R.id.minus_btn);
        minus_btn.setOnClickListener(this);
        minus_btn.setEnabled(false);

        //Blink Circle Active User
        player_blink_circle1 = findViewById(R.id.player_blink_circle1);
        player_blink_circle2 = findViewById(R.id.player_blink_circle2);
        player_blink_circle3 = findViewById(R.id.player_blink_circle3);
        player_blink_circle4 = findViewById(R.id.player_blink_circle4);


//       Implementation of Pack Button
        pack_btn = findViewById(R.id.pack_btn);
        pack_btn.setOnClickListener(this);


        //see myplayer card
        btn_see_cards = findViewById(R.id.btn_see_cards);
        btn_see_cards.setOnClickListener(this);
        show_btn = findViewById(R.id.show_btn);
        show_btn.setOnClickListener(this);

        //////////////// Popup for Backbutton ///////////////////

        backbtn = findViewById(R.id.back);
        backbtn.setOnClickListener(this);

        //////////////// Popup for InfoButton ///////////////////
        imgVInfo = findViewById(R.id.imgVInfo);
        imgVInfo.setOnClickListener(this);

        myplayer = (ImageView) findViewById(R.id.myplayer);

        // load the animation
        animBlink = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);


        new GameRequestAyncTask().execute("http://213.136.81.137:8081/api/gameRequest");


    }

    public void distributeCards() {
        //card image
        card1 = findViewById(R.id.card1);
        card2 = findViewById(R.id.card2);
        card3 = findViewById(R.id.card3);
        card4 = findViewById(R.id.card4);
        card5 = findViewById(R.id.card5);
        card6 = findViewById(R.id.card6);
        card7 = findViewById(R.id.card7);
        card8 = findViewById(R.id.card8);
        card9 = findViewById(R.id.card9);
        card10 = findViewById(R.id.card10);
        card11 = findViewById(R.id.card11);
        card12 = findViewById(R.id.card12);
        card13 = findViewById(R.id.card13);
        card14 = findViewById(R.id.card14);
        card15 = findViewById(R.id.card15);

        card3.bringToFront();
        card8.bringToFront();
        card13.bringToFront();

        //shuffling card animation
        animatecard1 = AnimationUtils.loadAnimation(PrivateActivity.this, R.anim.translate_top_left1);
        animatecard2 = AnimationUtils.loadAnimation(PrivateActivity.this, R.anim.translate_bottom_left1);
        animatecard3 = AnimationUtils.loadAnimation(PrivateActivity.this, R.anim.translate_bottom1);
        animatecard4 = AnimationUtils.loadAnimation(PrivateActivity.this, R.anim.translate_bottom_right1);
        animatecard5 = AnimationUtils.loadAnimation(PrivateActivity.this, R.anim.translate_top_right1);

        animatecard6 = AnimationUtils.loadAnimation(PrivateActivity.this, R.anim.translate_top_left2);
        animatecard7 = AnimationUtils.loadAnimation(PrivateActivity.this, R.anim.translate_bottom_left2);
        animatecard8 = AnimationUtils.loadAnimation(PrivateActivity.this, R.anim.translate_bottom2);
        animatecard9 = AnimationUtils.loadAnimation(PrivateActivity.this, R.anim.translate_bottom_right2);
        animatecard10 = AnimationUtils.loadAnimation(PrivateActivity.this, R.anim.translate_top_right2);

        animatecard11 = AnimationUtils.loadAnimation(PrivateActivity.this, R.anim.translate_top_left3);
        animatecard12 = AnimationUtils.loadAnimation(PrivateActivity.this, R.anim.translate_bottom_left3);
        animatecard13 = AnimationUtils.loadAnimation(PrivateActivity.this, R.anim.translate_bottom3);
        animatecard14 = AnimationUtils.loadAnimation(PrivateActivity.this, R.anim.translate_bottom_right3);
        animatecard15 = AnimationUtils.loadAnimation(PrivateActivity.this, R.anim.translate_top_right3);

        //animate shuffle cards now here
        card1.startAnimation(animatecard1);
        animatecard2.setStartOffset(200);
        card2.startAnimation(animatecard2);
        animatecard3.setStartOffset(400);
        card3.startAnimation(animatecard3);
        animatecard4.setStartOffset(600);
        animatecard4.setFillAfter(true);
        card4.startAnimation(animatecard4);
        animatecard5.setStartOffset(800);
        card5.startAnimation(animatecard5);

        animatecard6.setStartOffset(1000);
        card6.startAnimation(animatecard6);
        animatecard7.setStartOffset(1200);
        card7.startAnimation(animatecard7);
        animatecard8.setStartOffset(1400);
        card8.startAnimation(animatecard8);
        animatecard9.setStartOffset(1600);
        animatecard9.setFillAfter(true);
        card9.startAnimation(animatecard9);
        animatecard10.setStartOffset(1800);
        card10.startAnimation(animatecard10);


        animatecard11.setStartOffset(2000);
        card11.startAnimation(animatecard11);
        animatecard12.setStartOffset(2200);
        card12.startAnimation(animatecard12);
        animatecard13.setStartOffset(2400);
        card13.startAnimation(animatecard13);
        animatecard14.setStartOffset(2600);
        animatecard14.setFillAfter(true);
        card14.startAnimation(animatecard14);
        animatecard15.setStartOffset(2800);
        card15.startAnimation(animatecard15);

        //display cards in position after animation overs

        animatecard3.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                // Pass the Intent to switch to other Activity

                View view1 = findViewById(R.id.card1);
                PercentRelativeLayout.LayoutParams params1 = (PercentRelativeLayout.LayoutParams) view1.getLayoutParams();
                PercentLayoutHelper.PercentLayoutInfo info1 = params1.getPercentLayoutInfo();
                info1.widthPercent = 0.15f;
                info1.heightPercent = 0.15f;
                view1.requestLayout();
                view1.layout(300, 0, view1.getWidth() + 300, view1.getHeight());
                view1.setRotation(-30.0f);

                View view2 = findViewById(R.id.card2);
                PercentRelativeLayout.LayoutParams params2 = (PercentRelativeLayout.LayoutParams) view2.getLayoutParams();
                PercentLayoutHelper.PercentLayoutInfo info2 = params2.getPercentLayoutInfo();
                info2.widthPercent = 0.15f;
                info2.heightPercent = 0.15f;
                view2.layout(300, 0, view2.getWidth() + 300, view2.getHeight());
                view2.setRotation(-30.0f);
                view2.requestLayout();

                View view3 = findViewById(R.id.card3);
                PercentRelativeLayout.LayoutParams params3 = (PercentRelativeLayout.LayoutParams) view3.getLayoutParams();
                PercentLayoutHelper.PercentLayoutInfo info3 = params3.getPercentLayoutInfo();
                info3.widthPercent = 0.18f;
                info3.heightPercent = 0.18f;
                view3.layout(300, 0, view3.getWidth() + 300, view3.getHeight());
                view3.setRotation(-30.0f);
                view3.requestLayout();

                btn_see_cards.bringToFront();
                //below_layout.setVisibility(View.GONE);
            }
        });

        animatecard6.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                // Pass the Intent to switch to other Activity

                View view4 = findViewById(R.id.card4);
                PercentRelativeLayout.LayoutParams params4 = (PercentRelativeLayout.LayoutParams) view4.getLayoutParams();
                PercentLayoutHelper.PercentLayoutInfo info4 = params4.getPercentLayoutInfo();
                info4.widthPercent = 0.15f;
                info4.heightPercent = 0.15f;
                view4.layout(300, 0, view4.getWidth() + 300, view4.getHeight());
                view4.setRotation(-30.0f);
                view4.requestLayout();

                View view5 = findViewById(R.id.card5);
                PercentRelativeLayout.LayoutParams params5 = (PercentRelativeLayout.LayoutParams) view5.getLayoutParams();
                PercentLayoutHelper.PercentLayoutInfo info5 = params5.getPercentLayoutInfo();
                info5.widthPercent = 0.15f;
                info5.heightPercent = 0.15f;
                view5.layout(300, 0, view5.getWidth() + 300, view5.getHeight());
                view5.setRotation(-30.0f);
                view5.requestLayout();

                View view6 = findViewById(R.id.card6);
                PercentRelativeLayout.LayoutParams params6 = (PercentRelativeLayout.LayoutParams) view6.getLayoutParams();
                PercentLayoutHelper.PercentLayoutInfo info6 = params6.getPercentLayoutInfo();
                info6.widthPercent = 0.15f;
                info6.heightPercent = 0.15f;
                view6.layout(200, 0, view6.getWidth() + 200, view6.getHeight());
                view6.setRotation(-10.0f);
                view6.requestLayout();
            }
        });

        animatecard9.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                // Pass the Intent to switch to other Activity

                View view7 = findViewById(R.id.card7);
                PercentRelativeLayout.LayoutParams params7 = (PercentRelativeLayout.LayoutParams) view7.getLayoutParams();
                PercentLayoutHelper.PercentLayoutInfo info7 = params7.getPercentLayoutInfo();
                info7.widthPercent = 0.15f;
                info7.heightPercent = 0.15f;
                view7.layout(200, 0, view7.getWidth() + 200, view7.getHeight());
                view7.setRotation(-10.0f);
                view7.requestLayout();

                View view8 = findViewById(R.id.card8);
                PercentRelativeLayout.LayoutParams params8 = (PercentRelativeLayout.LayoutParams) view8.getLayoutParams();
                PercentLayoutHelper.PercentLayoutInfo info8 = params8.getPercentLayoutInfo();
                info8.widthPercent = 0.18f;
                info8.heightPercent = 0.18f;
                view8.layout(200, 0, view8.getWidth() + 200, view8.getHeight());
                view8.setRotation(-10.0f);
                view8.requestLayout();

                View view9 = findViewById(R.id.card9);
                PercentRelativeLayout.LayoutParams params9 = (PercentRelativeLayout.LayoutParams) view9.getLayoutParams();
                PercentLayoutHelper.PercentLayoutInfo info9 = params9.getPercentLayoutInfo();
                info9.widthPercent = 0.15f;
                info9.heightPercent = 0.15f;
                view9.layout(200, 0, view9.getWidth() + 200, view9.getHeight());
                view9.setRotation(-10.0f);
                view9.requestLayout();

            }
        });

        animatecard12.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                // Pass the Intent to switch to other Activity

                View view10 = findViewById(R.id.card10);
                PercentRelativeLayout.LayoutParams params10 = (PercentRelativeLayout.LayoutParams) view10.getLayoutParams();
                PercentLayoutHelper.PercentLayoutInfo info10 = params10.getPercentLayoutInfo();
                info10.widthPercent = 0.15f;
                info10.heightPercent = 0.15f;
                view10.layout(200, 0, view10.getWidth() + 200, view10.getHeight());
                view10.setRotation(-10.0f);
                view10.requestLayout();

                View view11 = findViewById(R.id.card11);
                PercentRelativeLayout.LayoutParams params11 = (PercentRelativeLayout.LayoutParams) view11.getLayoutParams();
                PercentLayoutHelper.PercentLayoutInfo info11 = params11.getPercentLayoutInfo();
                info11.widthPercent = 0.15f;
                info11.heightPercent = 0.15f;
                view11.layout(100, 0, view11.getWidth() + 100, view11.getHeight());
                view11.setRotation(10.0f);
                view11.requestLayout();

                View view12 = findViewById(R.id.card12);
                PercentRelativeLayout.LayoutParams params12 = (PercentRelativeLayout.LayoutParams) view12.getLayoutParams();
                PercentLayoutHelper.PercentLayoutInfo info12 = params12.getPercentLayoutInfo();
                info12.widthPercent = 0.15f;
                info12.heightPercent = 0.15f;
                view12.layout(100, 0, view12.getWidth() + 100, view12.getHeight());
                view12.setRotation(10.0f);
                view12.requestLayout();
            }
        });

        animatecard15.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                // Pass the Intent to switch to other Activity

                View view13 = findViewById(R.id.card13);
                PercentRelativeLayout.LayoutParams params13 = (PercentRelativeLayout.LayoutParams) view13.getLayoutParams();
                PercentLayoutHelper.PercentLayoutInfo info13 = params13.getPercentLayoutInfo();
                info13.widthPercent = 0.18f;
                info13.heightPercent = 0.18f;
                view13.layout(100, 0, view13.getWidth() + 100, view13.getHeight());
                view13.setRotation(10.0f);
                view13.requestLayout();


                View view14 = findViewById(R.id.card14);
                PercentRelativeLayout.LayoutParams params14 = (PercentRelativeLayout.LayoutParams) view14.getLayoutParams();
                PercentLayoutHelper.PercentLayoutInfo info14 = params14.getPercentLayoutInfo();
                info14.widthPercent = 0.15f;
                info14.heightPercent = 0.15f;
                view14.layout(100, 0, view14.getWidth() + 100, view14.getHeight());
//                params14.setMargins(-60, 350, 0, 0);
                view14.setRotation(10.0f);
                view14.requestLayout();

                View view15 = findViewById(R.id.card15);
                PercentRelativeLayout.LayoutParams params15 = (PercentRelativeLayout.LayoutParams) view15.getLayoutParams();
                PercentLayoutHelper.PercentLayoutInfo info15 = params15.getPercentLayoutInfo();
                info15.widthPercent = 0.15f;
                info15.heightPercent = 0.15f;
                view15.layout(100, 0, view15.getWidth() + 100, view15.getHeight());
                view15.setRotation(10.0f);
                view15.requestLayout();

            }
        });

        /*animatecard15.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {

                        // Pass the Intent to switch to other Activity

                        View view1 = findViewById(R.id.card1);
                        PercentRelativeLayout.LayoutParams params1 = (PercentRelativeLayout.LayoutParams) view1.getLayoutParams();
                        PercentLayoutHelper.PercentLayoutInfo info1 = params1.getPercentLayoutInfo();
                        info1.widthPercent = 0.15f;
                        info1.heightPercent = 0.15f;
                        view1.requestLayout();
                        view1.layout(300, 0, view1.getWidth() + 300, view1.getHeight());
                        view1.setRotation(-30.0f);

                        View view2 = findViewById(R.id.card2);
                        PercentRelativeLayout.LayoutParams params2 = (PercentRelativeLayout.LayoutParams) view2.getLayoutParams();
                        PercentLayoutHelper.PercentLayoutInfo info2 = params2.getPercentLayoutInfo();
                        info2.widthPercent = 0.15f;
                        info2.heightPercent = 0.15f;
                        view2.layout(300, 0, view2.getWidth() + 300, view2.getHeight());
                        view2.setRotation(-30.0f);
                        view2.requestLayout();

                        View view3 = findViewById(R.id.card3);
                        PercentRelativeLayout.LayoutParams params3 = (PercentRelativeLayout.LayoutParams) view3.getLayoutParams();
                        PercentLayoutHelper.PercentLayoutInfo info3 = params3.getPercentLayoutInfo();

                        info3.widthPercent = 0.18f;
                        info3.heightPercent = 0.18f;
                        view3.layout(300, 0, view3.getWidth() + 300, view3.getHeight());
                        view3.setRotation(-30.0f);
                        view3.requestLayout();

                        View view4 = findViewById(R.id.card4);
                        PercentRelativeLayout.LayoutParams params4 = (PercentRelativeLayout.LayoutParams) view4.getLayoutParams();
                        PercentLayoutHelper.PercentLayoutInfo info4 = params4.getPercentLayoutInfo();
                        info4.widthPercent = 0.15f;
                        info4.heightPercent = 0.15f;
                        view4.layout(300, 0, view4.getWidth() + 300, view4.getHeight());
                        view4.setRotation(-30.0f);
                        view4.requestLayout();

                        View view5 = findViewById(R.id.card5);
                        PercentRelativeLayout.LayoutParams params5 = (PercentRelativeLayout.LayoutParams) view5.getLayoutParams();
                        PercentLayoutHelper.PercentLayoutInfo info5 = params5.getPercentLayoutInfo();
                        info5.widthPercent = 0.15f;
                        info5.heightPercent = 0.15f;
                        view5.layout(300, 0, view5.getWidth() + 300, view5.getHeight());
                        view5.setRotation(-30.0f);
                        view5.requestLayout();

                        View view6 = findViewById(R.id.card6);
                        PercentRelativeLayout.LayoutParams params6 = (PercentRelativeLayout.LayoutParams) view6.getLayoutParams();
                        PercentLayoutHelper.PercentLayoutInfo info6 = params6.getPercentLayoutInfo();
                        info6.widthPercent = 0.15f;
                        info6.heightPercent = 0.15f;
                        view6.layout(200, 0, view6.getWidth() + 200, view6.getHeight());
                        view6.setRotation(-10.0f);
                        view6.requestLayout();

                        View view7 = findViewById(R.id.card7);
                        PercentRelativeLayout.LayoutParams params7 = (PercentRelativeLayout.LayoutParams) view7.getLayoutParams();
                        PercentLayoutHelper.PercentLayoutInfo info7 = params7.getPercentLayoutInfo();
                        info7.widthPercent = 0.15f;
                        info7.heightPercent = 0.15f;
                        view7.layout(200, 0, view7.getWidth() + 200, view7.getHeight());
                        view7.setRotation(-10.0f);
                        view7.requestLayout();

                        View view8 = findViewById(R.id.card8);
                        PercentRelativeLayout.LayoutParams params8 = (PercentRelativeLayout.LayoutParams) view8.getLayoutParams();
                        PercentLayoutHelper.PercentLayoutInfo info8 = params8.getPercentLayoutInfo();
                        info8.widthPercent = 0.18f;
                        info8.heightPercent = 0.18f;
                        view8.layout(200, 0, view8.getWidth() + 200, view8.getHeight());
                        view8.setRotation(-10.0f);
                        view8.requestLayout();

                        View view9 = findViewById(R.id.card9);
                        PercentRelativeLayout.LayoutParams params9 = (PercentRelativeLayout.LayoutParams) view9.getLayoutParams();
                        PercentLayoutHelper.PercentLayoutInfo info9 = params9.getPercentLayoutInfo();
                        info9.widthPercent = 0.15f;
                        info9.heightPercent = 0.15f;
                        view9.layout(200, 0, view9.getWidth() + 200, view9.getHeight());
                        view9.setRotation(-10.0f);
                        view9.requestLayout();

                        View view10 = findViewById(R.id.card10);
                        PercentRelativeLayout.LayoutParams params10 = (PercentRelativeLayout.LayoutParams) view10.getLayoutParams();
                        PercentLayoutHelper.PercentLayoutInfo info10 = params10.getPercentLayoutInfo();
                        info10.widthPercent = 0.15f;
                        info10.heightPercent = 0.15f;
                        view10.layout(200, 0, view10.getWidth() + 200, view10.getHeight());
                        view10.setRotation(-10.0f);
                        view10.requestLayout();

                        View view11 = findViewById(R.id.card11);
                        PercentRelativeLayout.LayoutParams params11 = (PercentRelativeLayout.LayoutParams) view11.getLayoutParams();
                        PercentLayoutHelper.PercentLayoutInfo info11 = params11.getPercentLayoutInfo();
                        info11.widthPercent = 0.15f;
                        info11.heightPercent = 0.15f;
                        view11.layout(100, 0, view11.getWidth() + 100, view11.getHeight());
                        view11.setRotation(10.0f);
                        view11.requestLayout();

                        View view12 = findViewById(R.id.card12);
                        PercentRelativeLayout.LayoutParams params12 = (PercentRelativeLayout.LayoutParams) view12.getLayoutParams();
                        PercentLayoutHelper.PercentLayoutInfo info12 = params12.getPercentLayoutInfo();
                        info12.widthPercent = 0.15f;
                        info12.heightPercent = 0.15f;
                        view12.layout(100, 0, view12.getWidth() + 100, view12.getHeight());
                        view12.setRotation(10.0f);
                        view12.requestLayout();


                        View view13 = findViewById(R.id.card13);
                        PercentRelativeLayout.LayoutParams params13 = (PercentRelativeLayout.LayoutParams) view13.getLayoutParams();
                        PercentLayoutHelper.PercentLayoutInfo info13 = params13.getPercentLayoutInfo();
                        info13.widthPercent = 0.18f;
                        info13.heightPercent = 0.18f;
                        view13.layout(100, 0, view13.getWidth() + 100, view13.getHeight());
                        view13.setRotation(10.0f);
                        view13.requestLayout();


                        View view14 = findViewById(R.id.card14);
                        PercentRelativeLayout.LayoutParams params14 = (PercentRelativeLayout.LayoutParams) view14.getLayoutParams();
                        PercentLayoutHelper.PercentLayoutInfo info14 = params14.getPercentLayoutInfo();
                        info14.widthPercent = 0.15f;
                        info14.heightPercent = 0.15f;
                        view14.layout(100, 0, view14.getWidth() + 100, view14.getHeight());
//                params14.setMargins(-60, 350, 0, 0);
                        view14.setRotation(10.0f);
                        view14.requestLayout();

                        View view15 = findViewById(R.id.card15);
                        PercentRelativeLayout.LayoutParams params15 = (PercentRelativeLayout.LayoutParams) view15.getLayoutParams();
                        PercentLayoutHelper.PercentLayoutInfo info15 = params15.getPercentLayoutInfo();
                        info15.widthPercent = 0.15f;
                        info15.heightPercent = 0.15f;
                        view15.layout(100, 0, view15.getWidth() + 100, view15.getHeight());
                        view15.setRotation(10.0f);
                        view15.requestLayout();

                        btn_see_cards.bringToFront();
                        //below_layout.setVisibility(View.GONE);
                    }
                });*/
    }

    public void bootCollection() {
        boot_value_player1 = findViewById(R.id.boot_value_player1);
        boot_value_player2 = findViewById(R.id.boot_value_player2);
        boot_value_player3 = findViewById(R.id.boot_value_player3);
        boot_value_player4 = findViewById(R.id.boot_value_player4);
        boot_value_player5 = findViewById(R.id.boot_value_player5);

        bootvalueanimate1 = AnimationUtils.loadAnimation(PrivateActivity.this, R.anim.boot_anim1);
        bootvalueanimate2 = AnimationUtils.loadAnimation(PrivateActivity.this, R.anim.boot_anim2);
        bootvalueanimate3 = AnimationUtils.loadAnimation(PrivateActivity.this, R.anim.boot_anim3);
        bootvalueanimate4 = AnimationUtils.loadAnimation(PrivateActivity.this, R.anim.boot_anim4);
        bootvalueanimate5 = AnimationUtils.loadAnimation(PrivateActivity.this, R.anim.boot_anim5);

        boot_value_player1.startAnimation(bootvalueanimate1);
        bootvalueanimate1.setStartOffset(3000);

        boot_value_player2.startAnimation(bootvalueanimate2);
        bootvalueanimate2.setStartOffset(3000);

        boot_value_player3.startAnimation(bootvalueanimate3);
        bootvalueanimate3.setStartOffset(3000);

        boot_value_player4.startAnimation(bootvalueanimate4);
        bootvalueanimate4.setStartOffset(3000);

        boot_value_player5.startAnimation(bootvalueanimate5);
        bootvalueanimate5.setStartOffset(3000);

//        blinkergif1.setVisibility(View.VISIBLE);
//        blinkergif2.setVisibility(View.VISIBLE);
//        blinkergif3.setVisibility(View.VISIBLE);
//        blinkergif4.setVisibility(View.VISIBLE);
//        blinkergif5.setVisibility(View.VISIBLE);
    }

    /**
     * Separate User Data
     * Start
     */
    public void userOne() {

        boot_value_player1 = findViewById(R.id.boot_value_player1);

        card1 = findViewById(R.id.card1);
        card6 = findViewById(R.id.card6);
        card11 = findViewById(R.id.card11);

        bootvalueanimate1 = AnimationUtils.loadAnimation(PrivateActivity.this, R.anim.boot_anim1);

        boot_value_player1.startAnimation(bootvalueanimate1);
        bootvalueanimate1.setStartOffset(3000);

        animatecard1 = AnimationUtils.loadAnimation(PrivateActivity.this, R.anim.translate_top_left1);
        animatecard6 = AnimationUtils.loadAnimation(PrivateActivity.this, R.anim.translate_top_left2);
        animatecard11 = AnimationUtils.loadAnimation(PrivateActivity.this, R.anim.translate_top_left3);

        card1.startAnimation(animatecard1);
        animatecard6.setStartOffset(1000);
        card6.startAnimation(animatecard6);
        animatecard11.setStartOffset(2000);
        card11.startAnimation(animatecard11);


        animatecard3.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                // Pass the Intent to switch to other Activity

                View view1 = findViewById(R.id.card1);
                PercentRelativeLayout.LayoutParams params1 = (PercentRelativeLayout.LayoutParams) view1.getLayoutParams();
                PercentLayoutHelper.PercentLayoutInfo info1 = params1.getPercentLayoutInfo();
                info1.widthPercent = 0.15f;
                info1.heightPercent = 0.15f;
                view1.requestLayout();
                view1.layout(300, 0, view1.getWidth() + 300, view1.getHeight());
                view1.setRotation(-30.0f);

                View view2 = findViewById(R.id.card2);
                PercentRelativeLayout.LayoutParams params2 = (PercentRelativeLayout.LayoutParams) view2.getLayoutParams();
                PercentLayoutHelper.PercentLayoutInfo info2 = params2.getPercentLayoutInfo();
                info2.widthPercent = 0.15f;
                info2.heightPercent = 0.15f;
                view2.layout(300, 0, view2.getWidth() + 300, view2.getHeight());
                view2.setRotation(-30.0f);
                view2.requestLayout();

                View view3 = findViewById(R.id.card3);
                PercentRelativeLayout.LayoutParams params3 = (PercentRelativeLayout.LayoutParams) view3.getLayoutParams();
                PercentLayoutHelper.PercentLayoutInfo info3 = params3.getPercentLayoutInfo();
                info3.widthPercent = 0.18f;
                info3.heightPercent = 0.18f;
                view3.layout(300, 0, view3.getWidth() + 300, view3.getHeight());
                view3.setRotation(-30.0f);
                view3.requestLayout();

                btn_see_cards.bringToFront();
                //below_layout.setVisibility(View.GONE);
            }
        });
    }

    public void userTwo() {

        boot_value_player2 = findViewById(R.id.boot_value_player2);

        card2 = findViewById(R.id.card2);
        card7 = findViewById(R.id.card7);
        card12 = findViewById(R.id.card12);

        boot_value_player2.startAnimation(bootvalueanimate2);
        bootvalueanimate2.setStartOffset(3000);

        bootvalueanimate2 = AnimationUtils.loadAnimation(PrivateActivity.this, R.anim.boot_anim2);

        animatecard2 = AnimationUtils.loadAnimation(PrivateActivity.this, R.anim.translate_bottom_left1);
        animatecard7 = AnimationUtils.loadAnimation(PrivateActivity.this, R.anim.translate_bottom_left2);
        animatecard12 = AnimationUtils.loadAnimation(PrivateActivity.this, R.anim.translate_bottom_left3);

        //animate shuffle cards now here
        animatecard2.setStartOffset(200);
        card2.startAnimation(animatecard2);

        animatecard7.setStartOffset(1200);
        card7.startAnimation(animatecard7);

        animatecard12.setStartOffset(2200);
        card12.startAnimation(animatecard12);

        animatecard6.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                // Pass the Intent to switch to other Activity

                View view4 = findViewById(R.id.card4);
                PercentRelativeLayout.LayoutParams params4 = (PercentRelativeLayout.LayoutParams) view4.getLayoutParams();
                PercentLayoutHelper.PercentLayoutInfo info4 = params4.getPercentLayoutInfo();
                info4.widthPercent = 0.15f;
                info4.heightPercent = 0.15f;
                view4.layout(300, 0, view4.getWidth() + 300, view4.getHeight());
                view4.setRotation(-30.0f);
                view4.requestLayout();

                View view5 = findViewById(R.id.card5);
                PercentRelativeLayout.LayoutParams params5 = (PercentRelativeLayout.LayoutParams) view5.getLayoutParams();
                PercentLayoutHelper.PercentLayoutInfo info5 = params5.getPercentLayoutInfo();
                info5.widthPercent = 0.15f;
                info5.heightPercent = 0.15f;
                view5.layout(300, 0, view5.getWidth() + 300, view5.getHeight());
                view5.setRotation(-30.0f);
                view5.requestLayout();

                View view6 = findViewById(R.id.card6);
                PercentRelativeLayout.LayoutParams params6 = (PercentRelativeLayout.LayoutParams) view6.getLayoutParams();
                PercentLayoutHelper.PercentLayoutInfo info6 = params6.getPercentLayoutInfo();
                info6.widthPercent = 0.15f;
                info6.heightPercent = 0.15f;
                view6.layout(200, 0, view6.getWidth() + 200, view6.getHeight());
                view6.setRotation(-10.0f);
                view6.requestLayout();
            }
        });

    }

    public void userThree() {

        boot_value_player3 = findViewById(R.id.boot_value_player3);

        //card image
        card3 = findViewById(R.id.card3);
        card8 = findViewById(R.id.card8);
        card13 = findViewById(R.id.card13);

        boot_value_player3.startAnimation(bootvalueanimate3);
        bootvalueanimate3.setStartOffset(3000);

        card3.bringToFront();
        card8.bringToFront();
        card13.bringToFront();

        bootvalueanimate3 = AnimationUtils.loadAnimation(PrivateActivity.this, R.anim.boot_anim3);

        //shuffling card animation
        animatecard3 = AnimationUtils.loadAnimation(PrivateActivity.this, R.anim.translate_bottom1);
        animatecard8 = AnimationUtils.loadAnimation(PrivateActivity.this, R.anim.translate_bottom2);
        animatecard13 = AnimationUtils.loadAnimation(PrivateActivity.this, R.anim.translate_bottom3);

        //animate shuffle cards now here
        animatecard3.setStartOffset(400);
        card3.startAnimation(animatecard3);
        animatecard8.setStartOffset(1400);
        card8.startAnimation(animatecard8);
        animatecard13.setStartOffset(2400);
        card13.startAnimation(animatecard13);

        animatecard9.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                // Pass the Intent to switch to other Activity

                View view7 = findViewById(R.id.card7);
                PercentRelativeLayout.LayoutParams params7 = (PercentRelativeLayout.LayoutParams) view7.getLayoutParams();
                PercentLayoutHelper.PercentLayoutInfo info7 = params7.getPercentLayoutInfo();
                info7.widthPercent = 0.15f;
                info7.heightPercent = 0.15f;
                view7.layout(200, 0, view7.getWidth() + 200, view7.getHeight());
                view7.setRotation(-10.0f);
                view7.requestLayout();

                View view8 = findViewById(R.id.card8);
                PercentRelativeLayout.LayoutParams params8 = (PercentRelativeLayout.LayoutParams) view8.getLayoutParams();
                PercentLayoutHelper.PercentLayoutInfo info8 = params8.getPercentLayoutInfo();
                info8.widthPercent = 0.18f;
                info8.heightPercent = 0.18f;
                view8.layout(200, 0, view8.getWidth() + 200, view8.getHeight());
                view8.setRotation(-10.0f);
                view8.requestLayout();

                View view9 = findViewById(R.id.card9);
                PercentRelativeLayout.LayoutParams params9 = (PercentRelativeLayout.LayoutParams) view9.getLayoutParams();
                PercentLayoutHelper.PercentLayoutInfo info9 = params9.getPercentLayoutInfo();
                info9.widthPercent = 0.15f;
                info9.heightPercent = 0.15f;
                view9.layout(200, 0, view9.getWidth() + 200, view9.getHeight());
                view9.setRotation(-10.0f);
                view9.requestLayout();

            }
        });

    }

    public void userFour() {

        boot_value_player4 = findViewById(R.id.boot_value_player4);

        //card image0
        card4 = findViewById(R.id.card4);
        card9 = findViewById(R.id.card9);
        card14 = findViewById(R.id.card14);

        boot_value_player4.startAnimation(bootvalueanimate4);
        bootvalueanimate4.setStartOffset(3000);

        bootvalueanimate4 = AnimationUtils.loadAnimation(PrivateActivity.this, R.anim.boot_anim4);

        //shuffling card animation
        animatecard4 = AnimationUtils.loadAnimation(PrivateActivity.this, R.anim.translate_bottom_right1);
        animatecard9 = AnimationUtils.loadAnimation(PrivateActivity.this, R.anim.translate_bottom_right2);
        animatecard14 = AnimationUtils.loadAnimation(PrivateActivity.this, R.anim.translate_bottom_right3);

        //animate shuffle cards now here
        animatecard4.setStartOffset(600);
        animatecard4.setFillAfter(true);
        card4.startAnimation(animatecard4);
        animatecard9.setStartOffset(1600);
        animatecard9.setFillAfter(true);
        animatecard14.setStartOffset(2600);
        animatecard14.setFillAfter(true);
        card14.startAnimation(animatecard14);

        animatecard12.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                // Pass the Intent to switch to other Activity

                View view10 = findViewById(R.id.card10);
                PercentRelativeLayout.LayoutParams params10 = (PercentRelativeLayout.LayoutParams) view10.getLayoutParams();
                PercentLayoutHelper.PercentLayoutInfo info10 = params10.getPercentLayoutInfo();
                info10.widthPercent = 0.15f;
                info10.heightPercent = 0.15f;
                view10.layout(200, 0, view10.getWidth() + 200, view10.getHeight());
                view10.setRotation(-10.0f);
                view10.requestLayout();

                View view11 = findViewById(R.id.card11);
                PercentRelativeLayout.LayoutParams params11 = (PercentRelativeLayout.LayoutParams) view11.getLayoutParams();
                PercentLayoutHelper.PercentLayoutInfo info11 = params11.getPercentLayoutInfo();
                info11.widthPercent = 0.15f;
                info11.heightPercent = 0.15f;
                view11.layout(100, 0, view11.getWidth() + 100, view11.getHeight());
                view11.setRotation(10.0f);
                view11.requestLayout();

                View view12 = findViewById(R.id.card12);
                PercentRelativeLayout.LayoutParams params12 = (PercentRelativeLayout.LayoutParams) view12.getLayoutParams();
                PercentLayoutHelper.PercentLayoutInfo info12 = params12.getPercentLayoutInfo();
                info12.widthPercent = 0.15f;
                info12.heightPercent = 0.15f;
                view12.layout(100, 0, view12.getWidth() + 100, view12.getHeight());
                view12.setRotation(10.0f);
                view12.requestLayout();
            }
        });

    }

    public void userFive() {

        boot_value_player5 = findViewById(R.id.boot_value_player5);

        //card image
        card5 = findViewById(R.id.card5);
        card10 = findViewById(R.id.card10);
        card15 = findViewById(R.id.card15);

        boot_value_player5.startAnimation(bootvalueanimate5);
        bootvalueanimate5.setStartOffset(3000);

        bootvalueanimate5 = AnimationUtils.loadAnimation(PrivateActivity.this, R.anim.boot_anim5);

        //shuffling card animation
        animatecard5 = AnimationUtils.loadAnimation(PrivateActivity.this, R.anim.translate_top_right1);
        animatecard10 = AnimationUtils.loadAnimation(PrivateActivity.this, R.anim.translate_top_right2);
        animatecard15 = AnimationUtils.loadAnimation(PrivateActivity.this, R.anim.translate_top_right3);

        //animate shuffle cards now here
        animatecard5.setStartOffset(800);
        card5.startAnimation(animatecard5);
        animatecard10.setStartOffset(1800);
        card10.startAnimation(animatecard10);
        animatecard15.setStartOffset(2800);
        card15.startAnimation(animatecard15);

        animatecard15.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                // Pass the Intent to switch to other Activity

                View view13 = findViewById(R.id.card13);
                PercentRelativeLayout.LayoutParams params13 = (PercentRelativeLayout.LayoutParams) view13.getLayoutParams();
                PercentLayoutHelper.PercentLayoutInfo info13 = params13.getPercentLayoutInfo();
                info13.widthPercent = 0.18f;
                info13.heightPercent = 0.18f;
                view13.layout(100, 0, view13.getWidth() + 100, view13.getHeight());
                view13.setRotation(10.0f);
                view13.requestLayout();


                View view14 = findViewById(R.id.card14);
                PercentRelativeLayout.LayoutParams params14 = (PercentRelativeLayout.LayoutParams) view14.getLayoutParams();
                PercentLayoutHelper.PercentLayoutInfo info14 = params14.getPercentLayoutInfo();
                info14.widthPercent = 0.15f;
                info14.heightPercent = 0.15f;
                view14.layout(100, 0, view14.getWidth() + 100, view14.getHeight());
//                params14.setMargins(-60, 350, 0, 0);
                view14.setRotation(10.0f);
                view14.requestLayout();

                View view15 = findViewById(R.id.card15);
                PercentRelativeLayout.LayoutParams params15 = (PercentRelativeLayout.LayoutParams) view15.getLayoutParams();
                PercentLayoutHelper.PercentLayoutInfo info15 = params15.getPercentLayoutInfo();
                info15.widthPercent = 0.15f;
                info15.heightPercent = 0.15f;
                view15.layout(100, 0, view15.getWidth() + 100, view15.getHeight());
                view15.setRotation(10.0f);
                view15.requestLayout();

            }
        });
    }

    /**
     * Separate User Data
     * End
     */

    @Override
    public void onBackPressed() {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        } else {
            backtolobby();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        backPress();
    }

    /////////// Onclick for Backtolobby /////////////
    public void backtolobby() {
        LayoutInflater layoutInflater = (LayoutInflater) PrivateActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView = layoutInflater.inflate(R.layout.backbutton_popup, null);

        closebtn = customView.findViewById(R.id.close);
        backtolobby = customView.findViewById(R.id.backtolobby);

        //instantiate popup window
        popupWindow = new PopupWindow(customView, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);

        //display the popup window
        popupWindow.showAtLocation(relativeLayout, Gravity.CENTER, 0, 0);
        popupWindow.setFocusable(false);
        //close the popup window on button click
        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        backtolobby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backPress();
                Intent intent = new Intent(PrivateActivity.this, MainActivity.class);
                intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP | FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
    }

    String encodedimage0, encodedimage1, encodedimage2, encodedimage3, encodedimage4;

    @Override
    public void onClick(View v) {

        privatetble = findViewById(R.id.privatetble);
        LayoutInflater inflater = (LayoutInflater) PrivateActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        View customView = inflater.inflate(R.layout.player_status_popup, null, false);
        View customView2 = inflater.inflate(R.layout.other_player_status, null, false);

        switch (v.getId()) {
            case R.id.plus_btn:
                ChaalAmount = ChaalAmount * 2;
//                displayAmount.setText(String.valueOf(ChaalAmount));
                boot_value_player3.setText(String.valueOf(ChaalAmount));
                plus_btn.setEnabled(false);
                plus_btn.setImageResource(R.drawable.disabled);
                minus_btn.setImageResource(R.drawable.minus_btn);
                minus_btn.setEnabled(true);
                break;

            case R.id.minus_btn:
                plus_btn.setImageResource(R.drawable.plus_btn);
                plus_btn.setEnabled(true);
                minus_btn.setEnabled(false);
                ChaalAmount = ChaalAmount / 2;
//                displayAmount.setText(String.valueOf(ChaalAmount));
                boot_value_player3.setText(String.valueOf(ChaalAmount));
                minus_btn.setImageResource(R.drawable.minus_disabled);
                break;

            case R.id.blind_btn:
                maxBlindCount++;
                chaalBlind();
//                boot_value_player3.setText(String.valueOf(ChaalAmount));
                bootvalueanimate3 = AnimationUtils.loadAnimation(this, R.anim.boot_anim3);
                boot_value_player3.startAnimation(bootvalueanimate3);
                break;

            case R.id.chaal_btn:
                chaalBlind();
                break;

            case R.id.pack_btn:
                packOperation();
                break;

            case R.id.show_btn:
                showCards();
                break;

            case R.id.imgVInfo:
                popupLimitedData(BootValue, PotLimit, MaxBlind, chaalLimit);
                break;

            case R.id.btn_see_cards:
                seeCardOperation();
                break;

            case R.id.inner_player_img:

                mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.click);
                mediaPlayer.start();

                PlayerStatusWindow = new PopupWindow(customView, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT, true);

                player_name = customView.findViewById(R.id.player_name);
                player_balance = customView.findViewById(R.id.player_balance);
                player_status_circle = customView.findViewById(R.id.player_status_circle);

                player_name.setText(USER_NAME);
                player_balance.setText(BALANCE);

                PlayerStatusWindow.showAtLocation(privatetble, Gravity.CENTER, 0, 0);
                Glide.with(getApplicationContext()).load(encodedimage0).into(player_status_circle);
                /*if (!encodedimage0.equalsIgnoreCase("")) {
                    byte[] b = Base64.decode(encodedimage0, Base64.DEFAULT);
                    Bitmap bmp = BitmapFactory.decodeByteArray(b, 0, b.length);
                    player_status_circle.setImageBitmap(bmp);
                }*/
                break;

            case R.id.inner_player_img1:
                individualUserInfo(customView2, USER_NAME1, BALANCE1, encodedimage1);
                break;

            case R.id.inner_player_img2:
                individualUserInfo(customView2, USER_NAME2, BALANCE2, encodedimage2);
                break;

            case R.id.inner_player_img3:
                individualUserInfo(customView2, USER_NAME3, BALANCE3, encodedimage3);
                break;

            case R.id.inner_player_img4:
                individualUserInfo(customView2, USER_NAME4, BALANCE4, encodedimage4);
                break;

            case R.id.back:
                backtolobby();
                break;
        }
    }

    //On BackPress
    public void backPress() {
        DataHolder.setData(PrivateActivity.this, "userstatus", "offline");
        new updateUserStatusAsyncTask().execute("http://213.136.81.137:8081/api/update_client_status", "offline");
        stopService(new Intent(PrivateActivity.this, ServiceLastUserData.class));
        try {
            if (broadcastReceiver != null) {
                unregisterReceiver(broadcastReceiver);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //SHOW Cards
    public void showCards() {
        Handler handlerDisp = new Handler();
        //Show_Status = "1";
        new showDeskCardsAsyncTask().execute("http://213.136.81.137:8081/api/showDeskCards");
        handlerDisp.post(new Runnable() {
            @Override
            public void run() {
                Glide.with(PrivateActivity.this).load(cardUrl7).into(card1);
                Glide.with(PrivateActivity.this).load(cardUrl8).into(card6);
                Glide.with(PrivateActivity.this).load(cardUrl9).into(card11);

                Glide.with(PrivateActivity.this).load(cardUrl4).into(card2);
                Glide.with(PrivateActivity.this).load(cardUrl5).into(card7);
                Glide.with(PrivateActivity.this).load(cardUrl6).into(card12);

                Glide.with(PrivateActivity.this).load(cardUrl13).into(card4);
                Glide.with(PrivateActivity.this).load(cardUrl14).into(card9);
                Glide.with(PrivateActivity.this).load(cardUrl15).into(card14);

                Glide.with(PrivateActivity.this).load(cardUrl10).into(card5);
                Glide.with(PrivateActivity.this).load(cardUrl11).into(card10);
                Glide.with(PrivateActivity.this).load(cardUrl12).into(card15);
            }
        });
        stopService(new Intent(PrivateActivity.this, ServiceLastUserData.class));
        try {
            if (broadcastReceiver != null) {
                unregisterReceiver(broadcastReceiver);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //individual User Info
    public void individualUserInfo(View view, String username, String Balance, String oencodedimage) {
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.click);
        mediaPlayer.start();

        OPlayerStatusWindow = new PopupWindow(view, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT, true);

        other_player_name = view.findViewById(R.id.other_player_name);
        other_player_balance = view.findViewById(R.id.other_player_balance);
        oplayer_status_circle = view.findViewById(R.id.oplayer_status_circle);

//         Glide.with(getApplicationContext()).load(user_image).into(oplayer_status_circle);

        other_player_name.setText(username);
        other_player_balance.setText(Balance);

        OPlayerStatusWindow.showAtLocation(privatetble, Gravity.CENTER, 0, 0);
        Glide.with(getApplicationContext()).load(oencodedimage).into(oplayer_status_circle);
        /*if (!oencodedimage.equalsIgnoreCase("")) {
            byte[] b = Base64.decode(oencodedimage, Base64.DEFAULT);
            Bitmap bmp = BitmapFactory.decodeByteArray(b, 0, b.length);
            oplayer_status_circle.setImageBitmap(bmp);
        }*/
    }

    //PACK USER
    public void packOperation() {
        rl_bottom_caption.setVisibility(View.GONE);
        btn_see_cards.setVisibility(View.GONE);
        //below_layout.setVisibility(View.VISIBLE);
        card3.setVisibility(View.GONE);
        card8.setVisibility(View.GONE);
        card13.setVisibility(View.GONE);
        profile.setImageResource(R.drawable.fade_inner_img);
        chance_Status = "packed";
        CHECK_TIME_OUT = true;
        new ChanceAsyncTask().execute("http://213.136.81.137:8081/api/insertChance");
        //new NextChanceAsyncTask().execute("http://213.136.81.137:8081/api/deskNextChance?desk_id=" + DataHolder.getDataInt(PrivateActivity.this, "deskid"));
    }

    //Seen Card Operation
    public void seeCardOperation() {
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                Glide.with(PrivateActivity.this).load(cardUrl1).into(card3);
                Glide.with(PrivateActivity.this).load(cardUrl2).into(card8);
                Glide.with(PrivateActivity.this).load(cardUrl3).into(card13);
            }
        });
        btn_see_cards.setVisibility(View.GONE);
        //show_btn.setVisibility(View.VISIBLE);
        blind_btn.setVisibility(View.GONE);
        chaal_btn.setVisibility(View.VISIBLE);
        Seen_Blind = "seen";

        ChaalAmount = ChaalAmount * 2;
//        boot_value_player3.setText(String.valueOf(ChaalAmount));
//        displayAmount.setText(String.valueOf(ChaalAmount));
    }

    //INFO POPUP DATA
    public void popupLimitedData(String BootValue, String PotLimit, String mMaxBlind, String chaalLimit) {

        LayoutInflater layoutInflater = (LayoutInflater) PrivateActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView = layoutInflater.inflate(R.layout.private_gameinfo_popup, null);

        txtVBootValue = customView.findViewById(R.id.txtVBootValue);
        txtVPotlimit = customView.findViewById(R.id.txtVPotlimit);
        txtVMaxBlind = customView.findViewById(R.id.txtVMaxBlind);
        txtVChaalLimit = customView.findViewById(R.id.txtVChaalLimit);

        txtVBootValue.setText(BootValue);
        txtVPotlimit.setText(PotLimit);
        txtVMaxBlind.setText(mMaxBlind);
        txtVChaalLimit.setText(chaalLimit);

        infoclosebtn = customView.findViewById(R.id.infoclose);

        //instantiate popup window
        infopopupWindow = new PopupWindow(customView, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);

        //display the popup window
        infopopupWindow.showAtLocation(relativeLayout, Gravity.CENTER, 0, 0);

        //close the popup window on button click
        infoclosebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                infopopupWindow.dismiss();
            }
        });
    }

    //CHAAL & BLIND
    public void chaalBlind() {
        Handler handler1 = new Handler();
        Log.i("ChaalAmountx", String.valueOf(ChaalAmount));

        int TablelayAmtc = 0;
        try {
            TablelayAmtc = Integer.parseInt(txtVTableAmt.getText().toString().replace(" ", ""));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            TablelayAmtc = 0;
        }
        int AMOUNT = ChaalAmount + TablelayAmtc;
        txtVTableAmt.setText(String.valueOf(AMOUNT));
        maxBlindCount = 0;

        if (AMOUNT >= PotLimitInt) {
            TastyToast.makeText(this, "Pot Limit Exceeded", TastyToast.LENGTH_LONG, TastyToast.INFO);
            showCards();
        }
        handler = new Handler();
        handler1 = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //below_layout.setVisibility(View.VISIBLE);
            }
        }, 2000);

        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                rl_bottom_caption.setVisibility(View.GONE);
                blind_btn.setEnabled(false);
            }
        }, 1000);
        CHECK_TIME_OUT = true;
        new ChanceAsyncTask().execute("http://213.136.81.137:8081/api/insertChance");
        //new NextChanceAsyncTask().execute("http://213.136.81.137:8081/api/deskNextChance?desk_id=" + DataHolder.getDataInt(PrivateActivity.this, "deskid"));
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

    public String getUserApi(String url) {
        InputStream inputStream = null;
        String result = "";
        try {

            HttpClient httpclient = new DefaultHttpClient();
            HttpGet Httpget = new HttpGet(url);

            Httpget.setHeader("Accept", "application/json");
            Httpget.setHeader("Content-type", "application/json");
            Httpget.setHeader("Authorization", DataHolder.getDataString(PrivateActivity.this, "token"));

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

    private ArrayList<String> arrayListUserId = new ArrayList<>();
    private ArrayList<String> arrayListUserIdSequence = new ArrayList<>();
    private ArrayList<String> arrayListUnPackedUser = new ArrayList<>();
    private boolean sequence = false;
    public static String BootValue, PotLimit, MaxBlind, chaalLimit;
    int maxBlindCount = 0;
    int PotLimitInt, ChaalAmount;

    private boolean CHECK_TIME_OUT = false;

    ValueAnimator animator;

    private void simulateProgress() {
        progressBarChances.setVisibility(View.VISIBLE);
        animator = ValueAnimator.ofInt(0, 100);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int progress = (int) animation.getAnimatedValue();
                progressBarChances.setProgress(progress + 1);
                Log.i("TAGTAGA", "hi " + progress);
                if (CHECK_TIME_OUT) {
                    animator.cancel();
                    CHECK_TIME_OUT = false;
                    progressBarChances.setVisibility(View.GONE);
                }

                if (99 == progress) {
                    animator.cancel();
                    packOperation();
                    progressBarChances.setVisibility(View.GONE);
                }

            }
        });
        Log.i("TAGTAGA1", "hello");
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setDuration(20000);
        animator.start();
    }

    String Seen_Blind = "blind", chance_Status = "active", Show_Status = "0", Next_User;

    public String chanceApi(String url, String mSeen_Blind, String mchance_Status) {
        InputStream inputStream = null;
        String result = "";
        try {

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);

            String json = "";
            JSONObject jsonObject = new JSONObject();

            jsonObject.accumulate("deskid", DataHolder.getDataInt(this, "deskid"));
            Log.i("useridx", DataHolder.getDataString(PrivateActivity.this, "userid"));
            jsonObject.accumulate("userid", DataHolder.getDataString(PrivateActivity.this, "userid"));
            jsonObject.accumulate("chaalamount", ChaalAmount);
            jsonObject.accumulate("chance_status", mchance_Status);
            jsonObject.accumulate("seen_blind", mSeen_Blind);
            jsonObject.accumulate("dealer_id", 1);
            //jsonObject.accumulate("show", mShow_Status);//user count
            //jsonObject.accumulate("next_user", mNext_User);
            //jsonObject.accumulate("potvalue", txtVTableAmt.getText().toString());


            json = jsonObject.toString();
            StringEntity se = new StringEntity(json);
            se.setContentType("application/json");

            httpPost.setEntity(new StringEntity(json));
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            httpPost.setHeader("Authorization", DataHolder.getDataString(PrivateActivity.this, "token"));

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

    private class ChanceAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            return chanceApi(urls[0], Seen_Blind, chance_Status);
        }

        @Override
        protected void onPostExecute(String result) {
            Log.i("Check123ssp", "" + result);
            try {
                JSONObject jsonObjMain = new JSONObject(result);
                JSONArray arr = new JSONArray(jsonObjMain.getString("data"));

                for (int i = 0; i < arr.length(); i++) {
                    String s = arr.getString(i);
                    Log.i("TTYYp", "" + s);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class GetChanceAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            return DataHolder.getApi(urls[0], PrivateActivity.this);
        }

        @Override
        protected void onPostExecute(String result) {
//            Toast.makeText(PrivateActivity.this, "" + result, Toast.LENGTH_SHORT).show();
            Log.i("Check123", "" + result);
            try {
                JSONObject jsonObjMain = new JSONObject(result.toString());

                JSONArray arr = new JSONArray(jsonObjMain.getString("data"));

                int len = arr.length();

                for (int i = 0; i < len; i++) {
                    JSONObject key = arr.getJSONObject(i);

                    String chanceid = key.getString("chanceid");
                    String desk_id = key.getString("desk_id");
                    String chaal_amount = key.getString("chaal_amount");
                    String user_id = key.getString("user_id");
                    String chance_status = key.getString("chance_status");
                    String pot_value = key.getString("pot_value");
                    String balance = key.getString("balance");
                    String round = key.getString("round");
                    String show = key.getString("show");
                    String seen_blind = key.getString("seen_blind");
                    String dealer_id = key.getString("dealer_id");
                    String tip = key.getString("tip");
                    String turn = key.getString("turn");
                    String game_status = key.getString("game_status");
                    String win_lose = key.getString("win_lose");
                    String datetime = key.getString("datetime");

                    Log.i("CHANCESID", "" + chanceid);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    String lastChanceid, lastDesk_id, lastChaal_amount, lastUser_id, lastChance_status, lastPot_value, lastBalance, lastShow, lastSeen_blind, lastDealer_id, lastTip, lastTurn, lastNext_user, lastWin_lose, lastDatetime;

    String storeNextValue = "";

    boolean TIMER_ROTATION = true;
    boolean STARTING_TURN = true;

    private class updateUserStatusAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            return DataHolder.updateUserStatusApi(urls[0], PrivateActivity.this, urls[1]);
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject jsonObjMain = new JSONObject(result.toString());

                String message = jsonObjMain.getString("message");
                DataHolder.setData(PrivateActivity.this, "userstatus", "online");
                if (message.equalsIgnoreCase("Client status successfully changed")) {
//                    Toast.makeText(PrivateActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    public String gameRequestApi(String url) {
        InputStream inputStream = null;
        String result = "";
        try {

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);

            String json = "";
            JSONObject jsonObject = new JSONObject();

            Log.i("KARAN", DataHolder.getDataInt(PrivateActivity.this, "deskid") + "\n" + DataHolder.getDataString(PrivateActivity.this, "userid"));
            jsonObject.accumulate("desk_id", DataHolder.getDataInt(PrivateActivity.this, "deskid"));
            jsonObject.accumulate("userid", DataHolder.getDataString(PrivateActivity.this, "userid"));
            jsonObject.accumulate("request_next", "next");//pev

            json = jsonObject.toString();
            StringEntity se = new StringEntity(json);
            se.setContentType("application/json");

            httpPost.setEntity(new StringEntity(json));
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            httpPost.setHeader("Authorization", DataHolder.getDataString(PrivateActivity.this, "token"));

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

    private class GameRequestAyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            return gameRequestApi(urls[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            Log.i("CheckGamex", "" + result);
            try {
                JSONObject jsonObjMain = new JSONObject(result.toString());
                if (jsonObjMain.getString("message").equalsIgnoreCase("Cards generated sucessfully")) {
                }
                //Toast.makeText(PrivateActivity.this, "Please Wait till boot amount is collected", Toast.LENGTH_SHORT).show();
                new getBootValueAsyncTask().execute("http://213.136.81.137:8081/api/collectBootValue");
                new orderChanceAyncTask().execute("http://213.136.81.137:8081/api/orderChance?desk_id=" + DataHolder.getDataInt(PrivateActivity.this, "deskid"));
                bootCollection();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    public String setBootValueApi(String url) {
        InputStream inputStream = null;
        String result = "";
        try {

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);

            String json = "";
            JSONObject jsonObject = new JSONObject();
            Log.i("SARITA", "" + DataHolder.getDataInt(this, "deskid"));
            jsonObject.accumulate("desk_id", DataHolder.getDataInt(this, "deskid"));

            json = jsonObject.toString();
            StringEntity se = new StringEntity(json);
            se.setContentType("application/json");

            httpPost.setEntity(new StringEntity(json));
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            httpPost.setHeader("Authorization", DataHolder.getDataString(PrivateActivity.this, "token"));

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

    private class getBootValueAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            return setBootValueApi(urls[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            Log.i("Check123545", "" + result);
            try {
                JSONObject jsonObjMain = new JSONObject(result.toString());
                if (jsonObjMain.getString("message").equalsIgnoreCase("Updated sucessfully")) {
//
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class orderChanceAyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            return DataHolder.getApi(urls[0], PrivateActivity.this);
        }

        @Override
        protected void onPostExecute(String result) {
//            Toast.makeText(PrivateActivity.this, "orderChanceAyncTas\n\n\n" + result, Toast.LENGTH_SHORT).show();
            Log.i("CheckGameOrderChance", "" + result);
            try {
                JSONObject jsonObjMain = new JSONObject(result.toString());
                if (jsonObjMain.getString("message").equalsIgnoreCase("Sucessfully")) {

                    JSONArray arr = new JSONArray(jsonObjMain.getString("data"));
                    int len = arr.length();

                    for (int i = 0; i < len; i++) {
                        String s = arr.getString(i);
                        Log.i("TTYY", "" + s);
                    }


                    for (int i = 0; i < len; i++) {

                        String userid = arr.getString(i);

                        StartBlink = arr.getString(0);
                        Log.i("TTYY", "" + userid);
                        if (userid.equals(DataHolder.getDataString(PrivateActivity.this, "userid"))) {
                            sequence = true;
                        }
                        if (sequence) {
                            arrayListUserIdSequence.add(userid);
                        } else {
                            arrayListUserId.add(userid);
                        }
                    }

                    if (arrayListUserId.size() > 0) {
                        for (int j = 0; j < arrayListUserId.size(); j++) {
                            arrayListUserIdSequence.add(arrayListUserId.get(j));
                        }
                    }
                }
                new getCardDataAsyncTask().execute("http://213.136.81.137:8081/api/get_desk_cards?desk_id=" + DataHolder.getDataInt(PrivateActivity.this, "deskid"));
                //new UserDataAsyncTask().execute("http://213.136.81.137:8081/api/getclientdesk?user_id=" + DataHolder.getDataString(PrivateActivity.this, "userid"));
                distributeCards();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private class UserDataAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            return DataHolder.getApi(urls[0], PrivateActivity.this);
        }

        @Override
        protected void onPostExecute(String result) {
            //Toast.makeText(PrivateActivity.this, "" + result, Toast.LENGTH_SHORT).show();
            Log.i("Check123", "" + result);
            try {
                JSONObject jsonObjMain = new JSONObject(result.toString());

                /*BootValue = jsonObjMain.getString("boot_value");
                MaxBlind = jsonObjMain.getString("max_no_blinds");
                PotLimit = jsonObjMain.getString("pot_limit");
                PotLimitInt = Integer.parseInt(PotLimit);
                chaalLimit = jsonObjMain.getString("chaal_limit");
                Log.i("ChaalAmountx", String.valueOf(ChaalAmount));
                ChaalAmount = Integer.parseInt(jsonObjMain.getString("boot_value"));//Start Chaal
                displayAmount.setText(String.valueOf(ChaalAmount));

                boot_value_player1 = findViewById(R.id.boot_value_player1);
                boot_value_player2 = findViewById(R.id.boot_value_player2);
                boot_value_player3 = findViewById(R.id.boot_value_player3);
                boot_value_player4 = findViewById(R.id.boot_value_player4);
                boot_value_player5 = findViewById(R.id.boot_value_player5);

                boot_value_player1.setText(String.valueOf(ChaalAmount));
                boot_value_player2.setText(String.valueOf(ChaalAmount));
                boot_value_player3.setText(String.valueOf(ChaalAmount));
                boot_value_player4.setText(String.valueOf(ChaalAmount));
                boot_value_player5.setText(String.valueOf(ChaalAmount));*/

                JSONArray arr = new JSONArray(jsonObjMain.getString("data"));

                int len = arr.length();

                /*for (int i = 0; i < len; i++) {

                    JSONObject key = arr.getJSONObject(i);
                    String userid = key.getString("user_id");
                    if (userid.equals(DataHolder.getDataString(PrivateActivity.this, "userid"))) {
                        sequence = true;
                    }
                    if (sequence) {
                        arrayListUserIdSequence.add(userid);
                    } else {
                        arrayListUserId.add(userid);
                    }
                }

                if (arrayListUserId.size() > 0) {
                    for (int j = 0; j < arrayListUserId.size(); j++) {
                        arrayListUserIdSequence.add(arrayListUserId.get(j));
                    }
                }*/
                //arrayListUnPackedUser = arrayListUserIdSequence;

                /*for (int i = 0; i < len; i++) {

                    JSONObject key = arr.getJSONObject(i);
                    String userid = key.getString("user_id");
                    String user_name = key.getString("user_name");
                    String user_status = key.getString("user_status");
                    String user_image = key.getString("user_image");

                    for (int a = 0; a < arrayListUserIdSequence.size(); a++) {
                        try {
                            Log.i("arrayListSequencex", arrayListUserIdSequence.get(a));
                            if (userid.equals(arrayListUserIdSequence.get(a))) {
                                if (a == 0) {
                                    nametext.setText(user_name);
                                    USER_NAME = user_name;
                                    BALANCE = key.getString("balance");
                                    user_id.setText(userid);
                                    txtVBalanceMainPlayer.setText(key.getString("balance"));
                                    arrayListUnPackedUser.add(userid);
                                    encodedimage0 = user_image;
                                    Glide.with(getApplicationContext()).load(user_image).into(profile);

                                } else if (a == 1) {
                                    USER_NAME1 = user_name;
                                    BALANCE1 = key.getString("balance");
                                    nametext1.setText(user_name);
                                    user_status1.setText(user_status);
                                    encodedimage1 = user_image;
                                    Glide.with(getApplicationContext()).load(user_image).into(profile1);
                                    if (user_status.equalsIgnoreCase("online")) {
                                        arrayListUnPackedUser.add(userid);
                                        user_status1.setTextColor(Color.GREEN);
                                    } else if (user_status.equalsIgnoreCase("offline")) {
                                        user_status1.setTextColor(Color.RED);
                                    }
                                } else if (a == 2) {
                                    USER_NAME2 = user_name;
                                    BALANCE2 = key.getString("balance");
                                    nametext2.setText(user_name);
                                    user_status2.setText(user_status);
                                    encodedimage2 = user_image;
                                    Glide.with(getApplicationContext()).load(user_image).into(profile2);
                                    if (user_status.equalsIgnoreCase("online")) {
                                        arrayListUnPackedUser.add(userid);
                                        user_status2.setTextColor(Color.GREEN);
                                    } else if (user_status.equalsIgnoreCase("offline")) {
                                        user_status2.setTextColor(Color.RED);
                                    }
                                } else if (a == 3) {
                                    USER_NAME3 = user_name;
                                    BALANCE3 = key.getString("balance");
                                    nametext3.setText(user_name);
                                    user_status3.setText(user_status);
                                    encodedimage3 = user_image;
                                    Glide.with(getApplicationContext()).load(user_image).into(profile3);
                                    if (user_status.equalsIgnoreCase("online")) {
                                        arrayListUnPackedUser.add(userid);
                                        user_status3.setTextColor(Color.GREEN);
                                    } else if (user_status.equalsIgnoreCase("offline")) {
                                        user_status3.setTextColor(Color.RED);
                                    }
                                } else if (a == 4) {
                                    USER_NAME4 = user_name;
                                    BALANCE4 = key.getString("balance");
                                    nametext4.setText(user_name);
                                    user_status4.setText(user_status);
                                    encodedimage4 = user_image;
                                    Glide.with(getApplicationContext()).load(user_image).into(profile4);
                                    if (user_status.equalsIgnoreCase("online")) {
                                        arrayListUnPackedUser.add(userid);
                                        user_status4.setTextColor(Color.GREEN);
                                    } else if (user_status.equalsIgnoreCase("offline")) {
                                        user_status4.setTextColor(Color.RED);
                                    }
                                }
                            }
                        } catch (ArrayIndexOutOfBoundsException e) {
                            e.printStackTrace();
                        }
                    }
                }*/
            } catch (JSONException e) {
                e.printStackTrace();
            }

            new getCardDataAsyncTask().execute("http://213.136.81.137:8081/api/get_desk_cards?desk_id=" + DataHolder.getDataInt(PrivateActivity.this, "deskid"));

        }
    }

    String cardUrl1, cardUrl2, cardUrl3, cardUrl4, cardUrl5, cardUrl6, cardUrl7, cardUrl8, cardUrl9, cardUrl10, cardUrl11, cardUrl12, cardUrl13, cardUrl14, cardUrl15;

    private class getCardDataAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            return DataHolder.getApi(urls[0], PrivateActivity.this);
        }

        @Override
        protected void onPostExecute(String result) {

            Log.i("Check", "" + result);
//            Toast.makeText(PrivateActivity.this, "" + DataHolder.getDataString(PrivateActivity.this,"token"), Toast.LENGTH_SHORT).show();

            try {
                JSONObject jsonObjMain = new JSONObject(result);
                String message = jsonObjMain.getString("message");
                Log.i("TAG", "" + message);

                BootValue = jsonObjMain.getString("boot_value");
                MaxBlind = jsonObjMain.getString("max_no_blinds");
                PotLimit = jsonObjMain.getString("pot_limit");
                PotLimitInt = Integer.parseInt(PotLimit);
                chaalLimit = jsonObjMain.getString("chaal_limit");
                Log.i("ChaalAmountx", String.valueOf(ChaalAmount));
                ChaalAmount = Integer.parseInt(jsonObjMain.getString("boot_value"));//Start Chaal
                displayAmount.setText(String.valueOf(ChaalAmount));

                boot_value_player1 = findViewById(R.id.boot_value_player1);
                boot_value_player2 = findViewById(R.id.boot_value_player2);
                boot_value_player3 = findViewById(R.id.boot_value_player3);
                boot_value_player4 = findViewById(R.id.boot_value_player4);
                boot_value_player5 = findViewById(R.id.boot_value_player5);

                boot_value_player1.setText(String.valueOf(ChaalAmount));
                boot_value_player2.setText(String.valueOf(ChaalAmount));
                boot_value_player3.setText(String.valueOf(ChaalAmount));
                boot_value_player4.setText(String.valueOf(ChaalAmount));
                boot_value_player5.setText(String.valueOf(ChaalAmount));

                JSONArray arr = new JSONArray(jsonObjMain.getString("data"));
                int len = arr.length();
                for (int i = 0; i < len; i++) {

                    JSONObject key = arr.getJSONObject(i);
                    String userid = key.getString("user_id");
                    String user_name = key.getString("user_name");
                    String user_status = key.getString("user_status");
                    String user_image = key.getString("user_image");
                    String bal = key.getString("balance");

                    Log.i("CHECk", "" + arrayListUserIdSequence.size() + "  " + key.getString("first_name"));
                    for (int a = 0; a < arrayListUserIdSequence.size(); a++) {
                        if (userid.equalsIgnoreCase(arrayListUserIdSequence.get(a))) {
                            if (a == 0) {
                                nametext.setText(user_name);
                                USER_NAME = user_name;
                                BALANCE = bal;
                                user_id.setText(userid);
                                txtVBalanceMainPlayer.setText(key.getString("balance"));
                                arrayListUnPackedUser.add(userid);
                                encodedimage0 = user_image;
                                Glide.with(getApplicationContext()).load(user_image).apply(new RequestOptions().override(100, 100)).into(profile);
                                String Url1 = key.getString("cardone");
                                String Url2 = key.getString("cardtwo");
                                String Url3 = key.getString("cardthree");
                                cardUrl1 = Url1;
                                cardUrl2 = Url2;
                                cardUrl3 = Url3;
                                Log.i("URL_1", i + " " + Url1);
                                Log.i("URL_2", i + " " + Url2);
                                Log.i("URL_3", i + " " + Url3);
                                //nametext1.setText(key.getString("first_name"));
                            } else if (a == 1) {

                                USER_NAME1 = user_name;
                                BALANCE1 = bal;
                                nametext1.setText(user_name);
                                user_status1.setText(user_status);
                                encodedimage1 = user_image;
                                Glide.with(getApplicationContext()).load(user_image).apply(new RequestOptions().override(100, 100)).into(profile1);
                                if (user_status.equalsIgnoreCase("online")) {
                                    arrayListUnPackedUser.add(userid);
                                    user_status1.setTextColor(Color.GREEN);
                                } else if (user_status.equalsIgnoreCase("offline")) {
                                    user_status1.setTextColor(Color.RED);
                                }

                                String Url4 = key.getString("cardone");
                                String Url5 = key.getString("cardtwo");
                                String Url6 = key.getString("cardthree");
                                cardUrl4 = Url4;
                                cardUrl5 = Url5;
                                cardUrl6 = Url6;
                                Log.i("URL 4", i + " " + Url4);
                                Log.i("URL 5", i + " " + Url5);
                                Log.i("URL 6", i + " " + Url6);
                                //nametext2.setText(key.getString("first_name"));
                            } else if (a == 2) {

                                USER_NAME2 = user_name;
                                BALANCE2 = bal;
                                nametext2.setText(user_name);
                                user_status2.setText(user_status);
                                encodedimage2 = user_image;
                                Glide.with(getApplicationContext()).load(user_image).apply(new RequestOptions().override(100, 100)).into(profile2);
                                if (user_status.equalsIgnoreCase("online")) {
                                    arrayListUnPackedUser.add(userid);
                                    user_status2.setTextColor(Color.GREEN);
                                } else if (user_status.equalsIgnoreCase("offline")) {
                                    user_status2.setTextColor(Color.RED);
                                }

                                String Url7 = key.getString("cardone");
                                String Url8 = key.getString("cardtwo");
                                String Url9 = key.getString("cardthree");
                                cardUrl7 = Url7;
                                cardUrl8 = Url8;
                                cardUrl9 = Url9;
                                Log.i("URL 7", i + " " + Url7);
                                Log.i("URL 8", i + " " + Url8);
                                Log.i("URL 9", i + " " + Url9);
                                //nametext.setText(key.getString("first_name"));
                            } else if (a == 3) {

                                USER_NAME3 = user_name;
                                BALANCE3 = bal;
                                nametext3.setText(user_name);
                                user_status3.setText(user_status);
                                encodedimage3 = user_image;
                                Glide.with(getApplicationContext()).load(user_image).apply(new RequestOptions().override(100, 100)).into(profile3);
                                if (user_status.equalsIgnoreCase("online")) {
                                    arrayListUnPackedUser.add(userid);
                                    user_status3.setTextColor(Color.GREEN);
                                } else if (user_status.equalsIgnoreCase("offline")) {
                                    user_status3.setTextColor(Color.RED);
                                }

                                String Url10 = key.getString("cardone");
                                String Url11 = key.getString("cardtwo");
                                String Url12 = key.getString("cardthree");
                                cardUrl10 = Url10;
                                cardUrl11 = Url11;
                                cardUrl12 = Url12;
                                Log.i("URL 10", i + " " + Url10);
                                Log.i("URL 11", i + " " + Url11);
                                Log.i("URL 12", i + " " + Url12);
                                //nametext3.setText(key.getString("first_name"));
                            } else if (a == 4) {

                                USER_NAME4 = user_name;
                                BALANCE4 = key.getString("balance");
                                nametext4.setText(user_name);
                                user_status4.setText(user_status);
                                encodedimage4 = user_image;
                                Glide.with(getApplicationContext()).load(user_image).apply(new RequestOptions().override(100, 100)).into(profile4);
                                if (user_status.equalsIgnoreCase("online")) {
                                    arrayListUnPackedUser.add(userid);
                                    user_status4.setTextColor(Color.GREEN);
                                } else if (user_status.equalsIgnoreCase("offline")) {
                                    user_status4.setTextColor(Color.RED);
                                }

                                String Url13 = key.getString("cardone");
                                String Url14 = key.getString("cardtwo");
                                String Url15 = key.getString("cardthree");
                                cardUrl13 = Url13;
                                cardUrl14 = Url14;
                                cardUrl15 = Url15;
                                Log.i("URL 13", i + " " + Url13);
                                Log.i("URL 14", i + " " + Url14);
                                Log.i("URL 15", i + " " + Url15);
                                //nametext4.setText(key.getString("first_name"));
                            }
                        }
                    }
                }


                /*Thread thread1 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //NEXT CHANCES DATA
                        Intent intentnext = new Intent(PrivateActivity.this, ServiceNextChance.class);
                        startService(intentnext);
                    }
                });
                thread1.start();*/

                /*Thread thread2 = new Thread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
                thread2.start();*/

                //LAST CHANCES DATA
                Intent intentService = new Intent(PrivateActivity.this, ServiceLastUserData.class);
                startService(intentService);
                DataHolder.setData(PrivateActivity.this, "CHECK_SERVICE", true);
                Log.i("GITA", "Hellooooooooooooooo");

                //BroadcastReceiver LAST DATA
                broadcastReceiver = new BroadcastReceiverDATA();
                IntentFilter intentFilter = new IntentFilter(DataHolder.ACTION_USER_LAST_DATA);
                intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
                registerReceiver(broadcastReceiver, intentFilter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    //Single parametere DESK_ID
    public String ShowDeskApi(String url) {
        InputStream inputStream = null;
        String result = "";
        try {

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);

            String json = "";
            JSONObject jsonObject = new JSONObject();
            Log.i("SARITA", "" + DataHolder.getDataInt(this, "deskid"));
            jsonObject.accumulate("deskid", DataHolder.getDataInt(this, "deskid"));

            json = jsonObject.toString();
            StringEntity se = new StringEntity(json);
            se.setContentType("application/json");

            httpPost.setEntity(new StringEntity(json));
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            httpPost.setHeader("Authorization", DataHolder.getDataString(PrivateActivity.this, "token"));

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

    private class showDeskCardsAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            return ShowDeskApi(urls[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            Log.i("Check123545", "" + result);
            try {
                JSONObject jsonObjMain = new JSONObject(result.toString());
                if (jsonObjMain.getString("message").equalsIgnoreCase("Updated sucessfully")) {
                    new setWinnersAsyncTask().execute("http://213.136.81.137:8081/api/setWinners?desk_id=" + DataHolder.getDataInt(PrivateActivity.this, "deskid"));

//                    blinkergif2.setVisibility(View.VISIBLE);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class setWinnersAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            return DataHolder.setApi(urls[0], PrivateActivity.this);
        }

        @Override
        protected void onPostExecute(String result) {
            Log.i("Check123545", "" + result);
            try {
                JSONObject jsonObjMain = new JSONObject(result.toString());
                JSONArray array = jsonObjMain.getJSONArray("WinnerTeenpatti");
                for (int r = 0; r < array.length(); r++) {
                    JSONObject key = array.getJSONObject(r);
                    String user_id = key.getString("user_id");
                    String winner_type = key.getString("winner_type");
                    String winner_rank = key.getString("winner_rank");

                    if (user_id.equalsIgnoreCase(arrayListUserIdSequence.get(0))) {
                        blinkergif1.setVisibility(View.VISIBLE);
                    } else if (user_id.equalsIgnoreCase(arrayListUserIdSequence.get(1))) {
                        blinkergif2.setVisibility(View.VISIBLE);
                    } else if (user_id.equalsIgnoreCase(arrayListUserIdSequence.get(2))) {
                        blinkergif3.setVisibility(View.VISIBLE);
                    } else if (user_id.equalsIgnoreCase(arrayListUserIdSequence.get(3))) {
                        blinkergif4.setVisibility(View.VISIBLE);
                    } else if (user_id.equalsIgnoreCase(arrayListUserIdSequence.get(4))) {
                        blinkergif5.setVisibility(View.VISIBLE);
                    }

                    TastyToast.makeText(PrivateActivity.this, winner_type, 5000, TastyToast.INFO);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    BroadcastReceiverDATA broadcastReceiver;

    public class BroadcastReceiverDATA extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, final Intent intent) {
            String action = intent.getAction();
            if (action.equalsIgnoreCase(DataHolder.ACTION_USER_LAST_DATA)) {
                String result = intent.getStringExtra(DataHolder.KEY_USER_LAST_DATA);
                getLastChanceData(result);
                Log.i("TAG124 result", result);
            } else if (action.equalsIgnoreCase(DataHolder.ACTION_USER_DATA)) {
                String resultDATA = intent.getStringExtra(DataHolder.KEY_USER_DATA);
                Log.i("TAG124 result", resultDATA);
            } else if (action.equalsIgnoreCase(DataHolder.ACTION_LAST_5_DATA)) {
                String resultDATA = intent.getStringExtra(DataHolder.KEY_LAST_5_DATA);
                Log.i("TAG124 result", resultDATA);
            }
        }
    }

    String StartBlink;

    private void getLastChanceData(String result) {
        try {
            Log.e("TADAG==::", "" + result);
            JSONObject jsonObjMain = new JSONObject(result);
            JSONArray arr = new JSONArray(jsonObjMain.getString("data"));
            int len = arr.length();

            Log.i("TAGGGi", "" + len);
//            if (len == 0) {
//                try {
//                    if (DataHolder.getDataString(PrivateActivity.this, "userid").equalsIgnoreCase(StartBlink)) {
//                        if (STARTING_TURN) {
//                            simulateProgress();
//                            Log.i("TAGGGin", "" + arrayListUserIdSequence.get(0));
//                            btn_see_cards.setVisibility(View.VISIBLE);
//                            rl_bottom_caption.setVisibility(View.VISIBLE);
//                            Log.i("CHKIL Start", ChaalAmount + "");
//                            STARTING_TURN = false;
//                            Log.i("CHKIL Start", DataHolder.getDataString(PrivateActivity.this, "userid") + "");
//                        }
//                    } else if (arrayListUserIdSequence.get(1).equalsIgnoreCase(StartBlink)) {
//                        viewBlinkCircle = player_blink_circle1;
//                        player_blink_circle1.startAnimation(animBlink);
//                        TIMER_ROTATION = true;
//                        user_status1.setText("online");
//                        Log.i("CHKIL Start", arrayListUserIdSequence.get(1) + "");
//                    }else if (arrayListUserIdSequence.get(2).equalsIgnoreCase(StartBlink)){
//                        viewBlinkCircle = player_blink_circle2;
//                        player_blink_circle2.startAnimation(animBlink);
//                        TIMER_ROTATION = true;
//                        user_status2.setText("online");
//                        Log.i("CHKIL Start", arrayListUserIdSequence.get(2) + "");
//                    }else if (arrayListUserIdSequence.get(3).equalsIgnoreCase(StartBlink)){
//
//                        viewBlinkCircle = player_blink_circle3;
//                        player_blink_circle3.startAnimation(animBlink);
//                        TIMER_ROTATION = true;
//                        user_status3.setText("online");
//                        Log.i("CHKIL Start", arrayListUserIdSequence.get(3) + "");
//                    }else if (arrayListUserIdSequence.get(4).equalsIgnoreCase(StartBlink)){
//
//                        viewBlinkCircle = player_blink_circle4;
//                        player_blink_circle4.startAnimation(animBlink);
//                        TIMER_ROTATION = true;
//                        user_status4.setText("online");
//                        Log.i("CHKIL Start", arrayListUserIdSequence.get(4) + "");
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }

            for (int i = 0; i < len; i++) {
                JSONObject key = arr.getJSONObject(i);

                lastChanceid = key.getString("chanceid");
                lastDesk_id = key.getString("desk_id");
                lastChaal_amount = key.getString("chaal_amount");
                lastUser_id = key.getString("user_id");
                lastChance_status = key.getString("chance_status");
                lastPot_value = key.getString("pot_value");
                lastShow = key.getString("show");
                lastSeen_blind = key.getString("seen_blind");
                lastDealer_id = key.getString("dealer_id");
                lastTip = key.getString("tip");
                lastTurn = key.getString("turn");
                lastNext_user = key.getString("next_user");
                lastWin_lose = key.getString("win_lose");
                lastDatetime = key.getString("datetime");

                Log.i("CHKIL", storeNextValue + "-" + lastNext_user);
                if (!storeNextValue.equalsIgnoreCase(lastNext_user)) {
                    try {
                        viewBlinkCircle.clearAnimation();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                storeNextValue = lastNext_user;

                try {
                    if (DataHolder.getDataString(this, "userid").equalsIgnoreCase(lastNext_user)) {
                        Log.i("ChkilIN0", "-" + lastNext_user);

                        if (TIMER_ROTATION) {
                            TIMER_ROTATION = false;
                            simulateProgress();
                            if (btn_see_cards.getVisibility() != View.VISIBLE) {
                                btn_see_cards.setVisibility(View.VISIBLE);
                            }

                            if (maxBlindCount == Integer.parseInt(MaxBlind)) {
                                seeCardOperation();
                            }
                            rl_bottom_caption.setVisibility(View.VISIBLE);
                            ChaalAmount = Integer.parseInt(lastChaal_amount);

                            Log.i("CHKIL1", ChaalAmount + "");
                        }
                    } else if (arrayListUserIdSequence.get(1).equalsIgnoreCase(lastNext_user)) {

                        viewBlinkCircle = player_blink_circle1;
                        player_blink_circle1.startAnimation(animBlink);
                        TIMER_ROTATION = true;
                        user_status1.setText(lastSeen_blind);
                        Log.i("ChkilIN1", "-" + lastNext_user);
                    } else if (arrayListUserIdSequence.get(2).equalsIgnoreCase(lastNext_user)) {
                        viewBlinkCircle = player_blink_circle2;
                        player_blink_circle2.startAnimation(animBlink);
                        TIMER_ROTATION = true;
                        user_status2.setText(lastSeen_blind);

                        Log.i("ChkilIN2", "-" + lastNext_user);
                    } else if (arrayListUserIdSequence.get(3).equalsIgnoreCase(lastNext_user)) {
                        Log.i("ChkilIN3", "-" + lastNext_user);
                        viewBlinkCircle = player_blink_circle3;
                        player_blink_circle3.startAnimation(animBlink);
                        TIMER_ROTATION = true;
                        user_status3.setText(lastSeen_blind);
                        Log.i("ChkilIN3", "-" + lastNext_user);
                    } else if (arrayListUserIdSequence.get(4).equalsIgnoreCase(lastNext_user)) {

                        viewBlinkCircle = player_blink_circle4;
                        player_blink_circle4.startAnimation(animBlink);
                        TIMER_ROTATION = true;
                        user_status4.setText(lastSeen_blind);
                        Log.i("ChkilIN4", "-" + lastNext_user);
                    }
                } catch (IndexOutOfBoundsException e) {
                    e.printStackTrace();
                }

                try {
                    if (arrayListUserIdSequence.get(1).equalsIgnoreCase(lastUser_id)) {
                        if (!lastChance_status.equalsIgnoreCase("packed")) {
                            user_status1.setText(lastSeen_blind);
                        } else {
                            user_status1.setText(lastChance_status);
                            arrayListUnPackedUser.remove(lastChance_status);
                            player1.setImageResource(R.drawable.fade_inner_img);
                            card2.setVisibility(View.GONE);
                            card7.setVisibility(View.GONE);
                            card12.setVisibility(View.GONE);
                        }
                    } else if (arrayListUserIdSequence.get(2).equalsIgnoreCase(lastUser_id)) {
                        if (!lastChance_status.equalsIgnoreCase("packed")) {
                            user_status2.setText(lastSeen_blind);
                        } else {
                            user_status2.setText(lastChance_status);
                            arrayListUnPackedUser.remove(lastChance_status);
                            player1.setImageResource(R.drawable.fade_inner_img);
                            card1.setVisibility(View.GONE);
                            card6.setVisibility(View.GONE);
                            card11.setVisibility(View.GONE);
                        }
                    } else if (arrayListUserIdSequence.get(3).equalsIgnoreCase(lastUser_id)) {
                        if (!lastChance_status.equalsIgnoreCase("packed")) {
                            user_status3.setText(lastSeen_blind);
                        } else {
                            user_status3.setText(lastChance_status);
                            arrayListUnPackedUser.remove(lastChance_status);
                            player1.setImageResource(R.drawable.fade_inner_img);
                            card5.setVisibility(View.GONE);
                            card10.setVisibility(View.GONE);
                            card15.setVisibility(View.GONE);
                        }
                    } else if (arrayListUserIdSequence.get(4).equalsIgnoreCase(lastUser_id)) {
                        if (!lastChance_status.equalsIgnoreCase("packed") && !lastChance_status.equalsIgnoreCase("Timeout")) {
                            user_status4.setText(lastSeen_blind);
                        } else {
                            user_status4.setText(lastChance_status);
                            arrayListUnPackedUser.remove(lastChance_status);
                            player1.setImageResource(R.drawable.fade_inner_img);
                            card4.setVisibility(View.GONE);
                            card9.setVisibility(View.GONE);
                            card11.setVisibility(View.GONE);
                        }
                    }

                    if (chance_Status.equalsIgnoreCase("packed")) {
                        arrayListUnPackedUser.remove(lastChance_status);
                    }
                } catch (IndexOutOfBoundsException e) {
                    e.printStackTrace();
                }
            }
            txtVTableAmt.setText(lastPot_value);

            if (arrayListUnPackedUser.size() == 2) {
                show_btn.setVisibility(View.VISIBLE);
            }

            /*if (arrayListUnPackedUser.size() == 1) {
                //ShowData Set Winner
                showCards();
                Toast.makeText(this, "1 user is left", Toast.LENGTH_SHORT).show();
            }*/
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}