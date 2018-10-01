package affwl.com.teenpatti;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.percent.PercentLayoutHelper;
import android.support.percent.PercentRelativeLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.bumptech.glide.Glide;
import com.dinuscxj.progressbar.CircleProgressBar;
import com.google.gson.JsonObject;

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

import de.hdodenhof.circleimageview.CircleImageView;

@SuppressWarnings("deprecation")
@SuppressLint("WrongViewCast")

public class PrivateActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView handle_right, backbtn, imgVInfo, infoclosebtn, profile, profile1, plus_btn, minus_btn, myplayerbtn, ustatusclosebtn, dealerbtn, dealerclsbtn, oplayerbtn, oustatusclosebtn, msgclosebtn, chngdealerclosebtn;
    TextView closebtn, tipsbtn, chngdbtn, canceltipbtn, plusbtn, minusbtn, backtolobby, nametext, nametext1, nametext2, nametext3, nametext4, code, blind_btn, chaal_btn, show_btn, pack_btn;
    TextView txtVBootValue, txtVPotlimit, txtVMaxBlind, txtVChaalLimit;
    PopupWindow popupWindow, infopopupWindow, chatpopupWindow, ustatuspopupWindow, dealerpopupWindow, oustatuspopupWindow, sendmsgpopupWindow, chngdpopupWindow;
    RelativeLayout relativeLayout, relativeLayout2, relativeLayout3, privatetble;
    Session session;
    LinearLayout below_layout;
    TextView display_myplayer_bind, txtVBalanceMainPlayer, txtVTableAmt;
    Animation animations;
    CircleImageView inner_player_img;
    ImageView card1, card2, card3, card4, card5, card6, card7, card8, card9, card10, card11, card12, card13, card14, card15, myplayer, winnerblinker1, winnerblinker2, player1;

    Animation animatecard1, animatecard2, animatecard3, animatecard4, animatecard5, animatecard6, animatecard7, animatecard8,
            animatecard9, animatecard10, animatecard11, animatecard12, animatecard13, animatecard14, animatecard15, animBlink;

    TextView btn_see_cards, user_id, user_id1, user_id2, user_id3, user_id4;
    PercentRelativeLayout rl_bottom_caption;
    SharedPreferences sharedPreferences;
    String Username;
    Handler handler, handlerLoad;
    int minteger = 0;
    TextView displayAmount;
    private CircleProgressBar mCustomProgressBar2;

    RoundCornerProgressBar progressChaalTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private);

        new UserDataAsyncTask().execute("http://213.136.81.137:8081/api/getclientdesk?user_id=" + DataHolder.getDataString(this, "userid"));

        new getCardDataAsyncTask().execute("http://213.136.81.137:8081/api/get_desk_cards?desk_id=43&round_number=1");
//        handle_right = findViewById(R.id.handle_right);
//        handle_right.setOnClickListener(this);
//
//
//        privatetble = (DrawerLayout) findViewById(R.id.privatetble);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, privatetble, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        privatetble.addDrawerListener(toggle);
//        toggle.syncState();
//
//        navigationView = (NavigationView) findViewById(R.id.teen_nav_view);
//        navigationView.setNavigationItemSelectedListener(this);
        //private List<UserData> UserDataList = new ArrayList<>();

//        progressChaalTimer = findViewById(R.id.progressChaalTimer);

//        progressChaalTimer.setMax(100);

        display_myplayer_bind = findViewById(R.id.display_myplayer_bind);
        rl_bottom_caption = findViewById(R.id.rl_bottom_caption);
        below_layout = findViewById(R.id.below_layout);
        relativeLayout = findViewById(R.id.privaterecycler);
        txtVTableAmt = findViewById(R.id.txtVTableAmt);
        mCustomProgressBar2 = (CircleProgressBar) findViewById(R.id.custom_progress2);

        //        implemention of user profile pic
        profile = findViewById(R.id.inner_player_img);
        profile1 = findViewById(R.id.inner_player_img1);
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

        txtVBalanceMainPlayer = findViewById(R.id.txtVBalanceMainPlayer);

//        code = findViewById(R.id.code);
        session = new Session(this);
        String encodedimage = session.getImage();
        if (!encodedimage.equalsIgnoreCase("")) {
            byte[] b = Base64.decode(encodedimage, Base64.DEFAULT);
            Bitmap bmp = BitmapFactory.decodeByteArray(b, 0, b.length);
            profile.setImageBitmap(bmp);
            profile1.setImageBitmap(bmp);
        }
        String name = session.getName();
        String ID = session.getID();
        String Image = session.getImage();


//        nametext.setText(name);
//        nametext1.setText(name);
//        nametext2.setText(name);
//        nametext3.setText(name);
//        nametext4.setText(name);

//        user_id.setText(ID);
//        user_id1.setText(ID);
//        user_id2.setText(ID);
//        user_id3.setText(ID);
//        user_id4.setText(ID);

        blind_btn = findViewById(R.id.blind_btn);
        blind_btn.setOnClickListener(this);
        chaal_btn = findViewById(R.id.chaal_btn);
        chaal_btn.setOnClickListener(this);

//        CountDownTimer countDownTimer =  new CountDownTimer(20 * 1000, 20) {
//            @Override
//            public void onTick(long millisUntilFinished) {
//                //long progress = (61000 - millisUntilFinished) / 1000;
//                long progress = (millisUntilFinished) / 1000;
//                int p = (int)progress;
//
//                //Log.i("COUNT TAG",p+" "+String.valueOf(millisUntilFinished));
//
//                progressChaalTimer.setProgress(p*5);
//            }
//
//            @Override
//            public void onFinish() {
//                //the progressBar will be invisible after 60 000 miliseconds ( 1 minute)
//            }
//        };
//        countDownTimer.start();

//        Animation on blindshow_layout
//        LinearLayout blindshow_layout=findViewById(R.id.blindshow_layout);
//        TranslateAnimation animationsb = new TranslateAnimation(0.0f, 0.0f,
//                0.0f, 0.0f);          //  new TranslateAnimation(xFrom,xTo, yFrom,yTo)
//        animationsb.setDuration(1000);  // animation duration
//        animationsb.setFillAfter(true);
//        blind_btn.startAnimation(animationsb);

//        Implementation of increament amount
        displayAmount = findViewById(R.id.start_amount);
        plus_btn = findViewById(R.id.plus_btn);
        plus_btn.setOnClickListener(this);
        minus_btn = findViewById(R.id.minus_btn);
        minus_btn.setOnClickListener(this);
        minus_btn.setEnabled(false);

//        Implementation of Blind


//        Implementation of Pack Button
        inner_player_img = findViewById(R.id.inner_player_img);
        pack_btn = findViewById(R.id.pack_btn);
        pack_btn.setOnClickListener(this);
        //shuffling card animation
        animatecard1 = AnimationUtils.loadAnimation(this, R.anim.translate_top_left1);
        animatecard2 = AnimationUtils.loadAnimation(this, R.anim.translate_bottom_left1);
        animatecard3 = AnimationUtils.loadAnimation(this, R.anim.translate_bottom1);
        animatecard4 = AnimationUtils.loadAnimation(this, R.anim.translate_bottom_right1);
        animatecard5 = AnimationUtils.loadAnimation(this, R.anim.translate_top_right1);

        animatecard6 = AnimationUtils.loadAnimation(this, R.anim.translate_top_left2);
        animatecard7 = AnimationUtils.loadAnimation(this, R.anim.translate_bottom_left2);
        animatecard8 = AnimationUtils.loadAnimation(this, R.anim.translate_bottom2);
        animatecard9 = AnimationUtils.loadAnimation(this, R.anim.translate_bottom_right2);
        animatecard10 = AnimationUtils.loadAnimation(this, R.anim.translate_top_right2);

        animatecard11 = AnimationUtils.loadAnimation(this, R.anim.translate_top_left3);
        animatecard12 = AnimationUtils.loadAnimation(this, R.anim.translate_bottom_left3);
        animatecard13 = AnimationUtils.loadAnimation(this, R.anim.translate_bottom3);
        animatecard14 = AnimationUtils.loadAnimation(this, R.anim.translate_bottom_right3);
        animatecard15 = AnimationUtils.loadAnimation(this, R.anim.translate_top_right3);


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


        //see myplayer card
        btn_see_cards = findViewById(R.id.btn_see_cards);
        show_btn = findViewById(R.id.show_btn);
        show_btn.setOnClickListener(this);
        btn_see_cards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Glide.with(PrivateActivity.this).load(cardUrl7).into(card3);
                Glide.with(PrivateActivity.this).load(cardUrl8).into(card8);
                Glide.with(PrivateActivity.this).load(cardUrl9).into(card13);
//                new getUserDataAsyncTask().execute("http://213.136.81.137:8080/api/adminData");
                btn_see_cards.setVisibility(View.GONE);
                show_btn.setVisibility(View.VISIBLE);
                blind_btn.setVisibility(View.GONE);
                chaal_btn.setVisibility(View.VISIBLE);

                String sub1 = displayAmount.getText().toString();
                minteger = Integer.parseInt(sub1) * 2;
                displayAmount.setText(String.valueOf(minteger));
            }
        });

        card3.bringToFront();
        card8.bringToFront();
        card13.bringToFront();

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

//                RelativeLayout.LayoutParams params1 = (RelativeLayout.LayoutParams) card1.getLayoutParams();
//                params1.height=getResources().getDimensionPixelSize(R.dimen.card_height);
//                params1.width=getResources().getDimensionPixelSize(R.dimen.card_width);
//                                params1.setMargins(-100, 350, 0, 0);
//
//                card1.setLayoutParams(params1);
//                card1.setRotation(-30.0f);

                View view1 = findViewById(R.id.card1);
                PercentRelativeLayout.LayoutParams params1 = (PercentRelativeLayout.LayoutParams) view1.getLayoutParams();
                PercentLayoutHelper.PercentLayoutInfo info1 = params1.getPercentLayoutInfo();
                info1.widthPercent = 0.15f;
                info1.heightPercent = 0.15f;
//                info1.topMarginPercent = 0.20f;
//                info1.leftMarginPercent = 0.10f;
//                view1.setRotation(-30.0f);
//                params1.addRule(RelativeLayout.RIGHT_OF, R.id.rl_playerbg1);
//                params1.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                view1.requestLayout();
                view1.layout(300, 0, view1.getWidth() + 300, view1.getHeight());
                view1.setRotation(-30.0f);

                View view2 = findViewById(R.id.card2);
                PercentRelativeLayout.LayoutParams params2 = (PercentRelativeLayout.LayoutParams) view2.getLayoutParams();
                PercentLayoutHelper.PercentLayoutInfo info2 = params2.getPercentLayoutInfo();
                info2.widthPercent = 0.15f;
                info2.heightPercent = 0.15f;
//                info2.topMarginPercent = 0.60f;
//                info2.leftMarginPercent = 0.10f;
//                params2.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
//                params2.addRule(RelativeLayout.BELOW, R.id.rl_playerbg1);
//                params2.addRule(RelativeLayout.RIGHT_OF, R.id.rl_playerbg2);
                view2.layout(300, 0, view2.getWidth() + 300, view2.getHeight());
                view2.setRotation(-30.0f);
                view2.requestLayout();

                View view3 = findViewById(R.id.card3);
                PercentRelativeLayout.LayoutParams params3 = (PercentRelativeLayout.LayoutParams) view3.getLayoutParams();
                PercentLayoutHelper.PercentLayoutInfo info3 = params3.getPercentLayoutInfo();
//                info3.topMarginPercent = 0.65f;
                info3.widthPercent = 0.18f;
                info3.heightPercent = 0.18f;
//                info3.leftMarginPercent = 0.52f;
//                params3.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
//                params3.addRule(RelativeLayout.RIGHT_OF, R.id.rl_myplayer);
                view3.layout(300, 0, view3.getWidth() + 300, view3.getHeight());
                view3.setRotation(-30.0f);
                view3.requestLayout();

                View view4 = findViewById(R.id.card4);
                PercentRelativeLayout.LayoutParams params4 = (PercentRelativeLayout.LayoutParams) view4.getLayoutParams();
                PercentLayoutHelper.PercentLayoutInfo info4 = params4.getPercentLayoutInfo();
                info4.widthPercent = 0.15f;
                info4.heightPercent = 0.15f;
//                info4.topMarginPercent = 0.25f;
//                info4.rightMarginPercent = 0.44f;
//                params4.addRule(RelativeLayout.BELOW, R.id.rl_playerbg3);
//                params4.addRule(RelativeLayout.ALIGN_PARENT_END);
//                params4.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                view4.layout(300, 0, view4.getWidth() + 300, view4.getHeight());
                view4.setRotation(-30.0f);
                view4.requestLayout();

                View view5 = findViewById(R.id.card5);
                PercentRelativeLayout.LayoutParams params5 = (PercentRelativeLayout.LayoutParams) view5.getLayoutParams();
                PercentLayoutHelper.PercentLayoutInfo info5 = params5.getPercentLayoutInfo();
                info5.widthPercent = 0.15f;
                info5.heightPercent = 0.15f;
//                info5.topMarginPercent = 0.20f;
//                info5.rightMarginPercent = 0.14f;
//                params5.addRule(RelativeLayout.ALIGN_PARENT_END);
//                params5.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                view5.layout(300, 0, view5.getWidth() + 300, view5.getHeight());
                view5.setRotation(-30.0f);
                view5.requestLayout();

                View view6 = findViewById(R.id.card6);
                PercentRelativeLayout.LayoutParams params6 = (PercentRelativeLayout.LayoutParams) view6.getLayoutParams();
                PercentLayoutHelper.PercentLayoutInfo info6 = params6.getPercentLayoutInfo();
                info6.widthPercent = 0.15f;
                info6.heightPercent = 0.15f;
//                info6.topMarginPercent = 0.20f;
//                info6.leftMarginPercent = 0.12f;
//                params6.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
//               params6.setMargins(-80, 350, 0, 0);
//                params6.addRule(RelativeLayout.RIGHT_OF, R.id.card1);
                view6.layout(200, 0, view6.getWidth() + 200, view6.getHeight());
                view6.setRotation(-10.0f);
                view6.requestLayout();

                View view7 = findViewById(R.id.card7);
                PercentRelativeLayout.LayoutParams params7 = (PercentRelativeLayout.LayoutParams) view7.getLayoutParams();
                PercentLayoutHelper.PercentLayoutInfo info7 = params7.getPercentLayoutInfo();
                info7.widthPercent = 0.15f;
                info7.heightPercent = 0.15f;
//                info7.topMarginPercent = 0.60f;
//                info7.leftMarginPercent = 0.12f;
//                params7.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
//                params7.addRule(RelativeLayout.RIGHT_OF, R.id.card2);
//                params7.setMargins(-80, 350, 0, 0);
                view7.layout(200, 0, view7.getWidth() + 200, view7.getHeight());
                view7.setRotation(-10.0f);
                view7.requestLayout();

                View view8 = findViewById(R.id.card8);
                PercentRelativeLayout.LayoutParams params8 = (PercentRelativeLayout.LayoutParams) view8.getLayoutParams();
                PercentLayoutHelper.PercentLayoutInfo info8 = params8.getPercentLayoutInfo();
                info8.widthPercent = 0.18f;
                info8.heightPercent = 0.18f;
//                info8.topMarginPercent = 0.65f;
//                info8.leftMarginPercent = 0.54f;
//                params8.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
//                params8.addRule(RelativeLayout.CENTER_HORIZONTAL);
//                info8.rightMarginPercent=0.70f;
//                params8.addRule(RelativeLayout.CENTER_HORIZONTAL);
//                params8.addRule(RelativeLayout.RIGHT_OF,R.id.card3);
//                params8.addRule(RelativeLayout.RIGHT_OF, R.id.rl_myplayer);
//                params8.setMargins(-95, 350, 0, 0);
                view8.layout(200, 0, view8.getWidth() + 200, view8.getHeight());
                view8.setRotation(-10.0f);
                view8.requestLayout();

                View view9 = findViewById(R.id.card9);
                PercentRelativeLayout.LayoutParams params9 = (PercentRelativeLayout.LayoutParams) view9.getLayoutParams();
                PercentLayoutHelper.PercentLayoutInfo info9 = params9.getPercentLayoutInfo();
                info9.widthPercent = 0.15f;
                info9.heightPercent = 0.15f;
//                info9.topMarginPercent = 0.25f;
//                info9.rightMarginPercent = 0.42f;
//                params9.addRule(RelativeLayout.ALIGN_PARENT_END);
//                params9.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//                params9.addRule(RelativeLayout.BELOW, R.id.rl_playerbg3);
//                params9.addRule(RelativeLayout.RIGHT_OF,R.id.card4);
//                params9.setMargins(-80, 350, 0, 0);
                view9.layout(200, 0, view9.getWidth() + 200, view9.getHeight());
                view9.setRotation(-10.0f);
                view9.requestLayout();

                View view10 = findViewById(R.id.card10);
                PercentRelativeLayout.LayoutParams params10 = (PercentRelativeLayout.LayoutParams) view10.getLayoutParams();
                PercentLayoutHelper.PercentLayoutInfo info10 = params10.getPercentLayoutInfo();
                info10.widthPercent = 0.15f;
                info10.heightPercent = 0.15f;
//                info10.topMarginPercent = 0.20f;
//                info10.rightMarginPercent = 0.12f;
//                params10.addRule(RelativeLayout.ALIGN_PARENT_END);
//                params10.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//                params10.setMargins(-80, 350, 0, 0);
//                params10.addRule(RelativeLayout.RIGHT_OF,R.id.card5);
                view10.layout(200, 0, view10.getWidth() + 200, view10.getHeight());
                view10.setRotation(-10.0f);
                view10.requestLayout();

                View view11 = findViewById(R.id.card11);
                PercentRelativeLayout.LayoutParams params11 = (PercentRelativeLayout.LayoutParams) view11.getLayoutParams();
                PercentLayoutHelper.PercentLayoutInfo info11 = params11.getPercentLayoutInfo();
                info11.widthPercent = 0.15f;
                info11.heightPercent = 0.15f;
////                info11.topMarginPercent = 0.20f;
////                info11.leftMarginPercent = 0.14f;
////                params11.addRule(RelativeLayout.RIGHT_OF, R.id.card6);
////                params11.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
////                params11.setMargins(-60, 350, 0, 0);
                view11.layout(100, 0, view11.getWidth() + 100, view11.getHeight());
                view11.setRotation(10.0f);
                view11.requestLayout();

                View view12 = findViewById(R.id.card12);
                PercentRelativeLayout.LayoutParams params12 = (PercentRelativeLayout.LayoutParams) view12.getLayoutParams();
                PercentLayoutHelper.PercentLayoutInfo info12 = params12.getPercentLayoutInfo();
                info12.widthPercent = 0.15f;
                info12.heightPercent = 0.15f;
//                info12.topMarginPercent = 0.60f;
//                info12.leftMarginPercent = 0.14f;
//                params12.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
//                params12.addRule(RelativeLayout.RIGHT_OF, R.id.card7);
//                params12.setMargins(-60, 350, 0, 0);
                view12.layout(100, 0, view12.getWidth() + 100, view12.getHeight());
                view12.setRotation(10.0f);
                view12.requestLayout();


                View view13 = findViewById(R.id.card13);
                PercentRelativeLayout.LayoutParams params13 = (PercentRelativeLayout.LayoutParams) view13.getLayoutParams();
                PercentLayoutHelper.PercentLayoutInfo info13 = params13.getPercentLayoutInfo();
                info13.widthPercent = 0.18f;
                info13.heightPercent = 0.18f;
//                info13.topMarginPercent = 0.65f;
//                info13.leftMarginPercent = 0.56f;
//                params13.addRule(RelativeLayout.ALIGN_PARENT_LEFT);

//                params13.addRule(RelativeLayout.CENTER_HORIZONTAL);
//                params13.addRule(RelativeLayout.RIGHT_OF,R.id.card8);
//                params13.setMargins(-90, 350, 0, 0);
                view13.layout(100, 0, view13.getWidth() + 100, view13.getHeight());
                view13.setRotation(10.0f);
                view13.requestLayout();


                View view14 = findViewById(R.id.card14);
                PercentRelativeLayout.LayoutParams params14 = (PercentRelativeLayout.LayoutParams) view14.getLayoutParams();
                PercentLayoutHelper.PercentLayoutInfo info14 = params14.getPercentLayoutInfo();
                info14.widthPercent = 0.15f;
                info14.heightPercent = 0.15f;
//                info14.topMarginPercent = 0.25f;
//                info14.rightMarginPercent = 0.40f;
//                params14.addRule(RelativeLayout.ALIGN_PARENT_END);
//                params14.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//                params14.addRule(RelativeLayout.BELOW, R.id.rl_playerbg3);
                view14.layout(100, 0, view14.getWidth() + 100, view14.getHeight());
//                params14.setMargins(-60, 350, 0, 0);
                view14.setRotation(10.0f);
                view14.requestLayout();


//                PercentLayoutHelper.PercentLayoutParams params15 = (PercentLayoutHelper.PercentLayoutParams) card15.getLayoutParams();
//                PercentLayoutHelper.PercentLayoutInfo info15 = params15.getPercentLayoutInfo();
//                info15.widthPercent = 0.15f;
//                info15.heightPercent=0.15f;
//                info15.topMarginPercent=0.20f;
//                info15.endMarginPercent=0.55f;
////                params15.addRule(RelativeLayout.RIGHT_OF,R.id.card10);
////                params15.setMargins(-60, 350, 0, 0);
//                card15.setLayoutParams((ViewGroup.LayoutParams) params15);
//                card15.setRotation(10.0f);

                View view15 = findViewById(R.id.card15);
                PercentRelativeLayout.LayoutParams params15 = (PercentRelativeLayout.LayoutParams) view15.getLayoutParams();
                PercentLayoutHelper.PercentLayoutInfo info15 = params15.getPercentLayoutInfo();
                info15.widthPercent = 0.15f;
                info15.heightPercent = 0.15f;
//                info15.topMarginPercent = 0.20f;
//                info15.rightMarginPercent = 0.10f;
//                params15.addRule(RelativeLayout.ALIGN_PARENT_END);
//                params15.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//                params15.addRule(RelativeLayout.RIGHT_OF,R.id.card10);
                view15.layout(100, 0, view15.getWidth() + 100, view15.getHeight());
                view15.setRotation(10.0f);
                view15.requestLayout();

//                new getUserDataAsyncTask().execute("http://213.136.81.137:8080/api/adminData");

                btn_see_cards.bringToFront();
                btn_see_cards.setVisibility(View.VISIBLE);
                rl_bottom_caption.setVisibility(View.VISIBLE);
                below_layout.setVisibility(View.GONE);
            }
        });

        //////////////// Popup for Backbutton ///////////////////

        backbtn = findViewById(R.id.back);
        privatetble = findViewById(R.id.privatetble);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //instantiate the popup.xml layout file
                LayoutInflater layoutInflater = (LayoutInflater) PrivateActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View customView = layoutInflater.inflate(R.layout.backbutton_popup, null);

                closebtn = customView.findViewById(R.id.close);
                backtolobby = customView.findViewById(R.id.backtolobby);

                //instantiate popup window
                popupWindow = new PopupWindow(customView, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);

                //display the popup window
                popupWindow.showAtLocation(relativeLayout, Gravity.CENTER, 0, 0);

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
                        Intent intent = new Intent(PrivateActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });

        //////////////// Popup for InfoButton ///////////////////

        imgVInfo = findViewById(R.id.imgVInfo);
        imgVInfo.setOnClickListener(this);
        privatetble = findViewById(R.id.privatetble);

        //////////////// Popup for Userstatus ///////////////////

//        myplayerbtn=(ImageView) findViewById(R.id.myplayer);
//        privatetble = (DrawerLayout) findViewById(R.id.privatetble);
//
//        myplayerbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //instantiate the popup.xml layout file
//                LayoutInflater layoutInflater = (LayoutInflater) PrivateActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                View customView = layoutInflater.inflate(R.layout.player_status_popup, null);
//
//                ustatusclosebtn = (ImageView) customView.findViewById(R.id.userstatusclose);
//                //instantiate popup window
//                ustatuspopupWindow = new PopupWindow(customView,RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
//
//                //display the popup window
//                ustatuspopupWindow.showAtLocation(relativeLayout, Gravity.CENTER_HORIZONTAL, 0, 0);
//
//                //close the popup window on button click
//                ustatusclosebtn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        ustatuspopupWindow.dismiss();
//                    }
//                });
//            }
//        });


        //////////////// Popup for Otheruserstatus ///////////////////

//        oplayerbtn=(ImageView) findViewById(R.id.playerbg2);
//
//
//        oplayerbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //instantiate the popup.xml layout file
//                LayoutInflater layoutInflater = (LayoutInflater) PrivateActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                View customView = layoutInflater.inflate(R.layout.other_player_status, null);
//
//                msgbtn=customView.findViewById(R.id.msg);
//
//                // onclick event for message button
//                msgbtn.setOnClickListener(new View.OnClickListener() {
//                    public void onClick(View v) {
//                        LayoutInflater layoutInflater = (LayoutInflater) PrivateActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                        View customView1 = layoutInflater.inflate(R.layout.send_message_popup, null);
//                        msgclosebtn=customView1.findViewById(R.id.msgclose);
//                        //instantiate popup window
//                        sendmsgpopupWindow = new PopupWindow(customView1,RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//
//                        //display the popup window
//                        sendmsgpopupWindow.showAtLocation(relativeLayout, Gravity.TOP, 0, 0);
//
//                        //close the popup window on button click
//                        msgclosebtn.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                sendmsgpopupWindow.dismiss();
//                            }
//                        });
//
//                        oustatuspopupWindow.dismiss();
//                    }
//
//                });
//
//                oustatusclosebtn = (ImageView) customView.findViewById(R.id.ouserstatusclose);
//                //instantiate popup window
//                oustatuspopupWindow = new PopupWindow(customView,RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
//
//                //display the popup window
//                oustatuspopupWindow.showAtLocation(relativeLayout, Gravity.CENTER_HORIZONTAL, 0, 0);
//
//                //close the popup window on button click
//                oustatusclosebtn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        oustatuspopupWindow.dismiss();
//                    }
//                });
//            }
//        });

        //////////////// Popup for Dealer ///////////////////

        dealerbtn = findViewById(R.id.dealer);
        privatetble = findViewById(R.id.privatetble);

        dealerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //instantiate the popup.xml layout file
                LayoutInflater layoutInflater = (LayoutInflater) PrivateActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View customView = layoutInflater.inflate(R.layout.dealer_popup, null);

                relativeLayout2 = customView.findViewById(R.id.inctip_layout);
                relativeLayout3 = customView.findViewById(R.id.bottombtns);
                tipsbtn = customView.findViewById(R.id.tipbtn);
                canceltipbtn = customView.findViewById(R.id.canceltip);
                chngdbtn = customView.findViewById(R.id.chngdealer);
                // onclick event for tip button to hide and show layout
                tipsbtn.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View v) {
                        if (relativeLayout2.getVisibility() == View.INVISIBLE) {
                            relativeLayout2.setVisibility(View.VISIBLE);
                        }
                        if (relativeLayout3.getVisibility() == View.VISIBLE) {
                            relativeLayout3.setVisibility(View.INVISIBLE);
                        }
                    }

                });
                // onclick event for change dealer button
                chngdbtn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        LayoutInflater layoutInflater = (LayoutInflater) PrivateActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View customView2 = layoutInflater.inflate(R.layout.change_dealer, null);
                        chngdealerclosebtn = customView2.findViewById(R.id.chngdealerclose);
                        //instantiate popup window
                        chngdpopupWindow = new PopupWindow(customView2, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);

                        //display the popup window
                        chngdpopupWindow.showAtLocation(relativeLayout, Gravity.CENTER, 0, 0);

                        //close the popup window on button click
                        chngdealerclosebtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                chngdpopupWindow.dismiss();
                            }
                        });
                        dealerpopupWindow.dismiss();
                    }

                });
                // onclick event for cancel button of tip
                canceltipbtn.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View v) {
                        if (relativeLayout2.getVisibility() == View.VISIBLE) {
                            relativeLayout2.setVisibility(View.INVISIBLE);
                        }
                        if (relativeLayout3.getVisibility() == View.INVISIBLE) {
                            relativeLayout3.setVisibility(View.VISIBLE);
                        }
                    }
                });

                //Implementation of increament tip button
                final TextView displayInteger = customView.findViewById(R.id.tipamount);
                plusbtn = customView.findViewById(R.id.plus);
                plusbtn.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View v) {
                        String sub = displayInteger.getText().toString().substring(1);
                        minteger = Integer.parseInt(sub) * 2;
                        displayInteger.setText("₹" + minteger);
                        displayInteger.setBackgroundResource(R.drawable.empty_btn);

                    }

                });

                //Implementation of decreament tip
                minusbtn = customView.findViewById(R.id.minus);
                minusbtn.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View v) {
                        String sub = displayInteger.getText().toString().substring(1);
                        minteger = Integer.parseInt(sub) / 2;
                        displayInteger.setText("₹" + minteger);
                        displayInteger.setBackgroundResource(R.drawable.empty_btn);
                    }
                });
                dealerclsbtn = customView.findViewById(R.id.dealerclose);
                //instantiate popup window
                dealerpopupWindow = new PopupWindow(customView, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);

                //display the popup window
                dealerpopupWindow.showAtLocation(relativeLayout, Gravity.CENTER_HORIZONTAL, 0, 0);

                //close the popup window on button click
                dealerclsbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dealerpopupWindow.dismiss();
                    }
                });
            }
        });

//        handler = new Handler();
//        //DataHolder.showProgress(getApplicationContext());
//        handlerLoad = new Handler();
//        handler.post(new Runnable() {
//            @Override
//            public void run() {
//            }
//        });

        user_id.setText(DataHolder.getSTACK(PrivateActivity.this, "users_id"));
        user_id1.setText(DataHolder.getSTACK(PrivateActivity.this, "users_id"));
        user_id2.setText(DataHolder.getSTACK(PrivateActivity.this, "users_id"));
        user_id3.setText(DataHolder.getSTACK(PrivateActivity.this, "users_id"));
        user_id4.setText(DataHolder.getSTACK(PrivateActivity.this, "users_id"));


//        groupname.setText(DataHolder.getSTACK(PrivateActivity.this,"groupname"));
        nametext.setText(DataHolder.getSTACK(PrivateActivity.this, "username"));
        nametext1.setText(DataHolder.getSTACK(PrivateActivity.this, "username"));
        nametext2.setText(DataHolder.getSTACK(PrivateActivity.this, "username"));
        nametext3.setText(DataHolder.getSTACK(PrivateActivity.this, "username"));
        nametext4.setText(DataHolder.getSTACK(PrivateActivity.this, "username"));


//        round_name.setText(DataHolder.getSTACK(PrivateActivity.this,"round_name"));
//        cardseen.setText(DataHolder.getSTACK(PrivateActivity.this, "cardseen"));
//        chal_limit.setText(DataHolder.getSTACK(PrivateActivity.this, "chal_limit"));
//        chance.setText(DataHolder.getSTACK(PrivateActivity.this, "chance"));
//        cards.setText(DataHolder.getSTACK(PrivateActivity.this, "cards"));


        //User Turn indicator

        player1 = findViewById(R.id.player1);
        player1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player1.startAnimation(animBlink);
            }
        });


        //Winner Animation

        winnerblinker1 = (ImageView) findViewById(R.id.winnerblinker1);
//        winnerblinker2 = (ImageView) findViewById(R.id.winnerblinker2);
        myplayer = (ImageView) findViewById(R.id.myplayer);

        // load the animation
        animBlink = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);

        // set animation listener
        animBlink.setAnimationListener(new Animation.AnimationListener() {
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
                if (animation == animBlink) {
                }
            }

        });
//        myplayer.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                winnerblinker1.setVisibility(View.VISIBLE);
//                winnerblinker2.setVisibility(View.VISIBLE);
//
//                // start the animation
//                winnerblinker1.startAnimation(animBlink);
//                winnerblinker2.startAnimation(animBlink);
//                Toast.makeText(PrivateActivity.this, "Click barabar hai!", Toast.LENGTH_LONG).show();
//            }
//        });

        winnerblinker1.setVisibility(View.INVISIBLE);
        winnerblinker1.postDelayed(new Runnable() {
            public void run() {
                winnerblinker1.setVisibility(View.VISIBLE);
                myplayer.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        winnerblinker1.setVisibility(View.VISIBLE);
//                        winnerblinker2.setVisibility(View.VISIBLE);

                        // start the animation
                        winnerblinker1.startAnimation(animBlink);
//                        winnerblinker2.startAnimation(animBlink);
                    }
                });
                winnerblinker1.clearAnimation();
                winnerblinker1.setVisibility(View.GONE);
            }
        }, 6000);
    }


    @Override
    public void onBackPressed() {
        LayoutInflater layoutInflater = (LayoutInflater) PrivateActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView = layoutInflater.inflate(R.layout.backbutton_popup, null);

        closebtn = customView.findViewById(R.id.close);
        backtolobby = customView.findViewById(R.id.backtolobby);

        //instantiate popup window
        popupWindow = new PopupWindow(customView, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);

        //display the popup window
        popupWindow.showAtLocation(relativeLayout, Gravity.CENTER, 0, 0);

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
                Intent intent = new Intent(PrivateActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    /////////// Onclick for Backtolobby /////////////

    public void backtolobby(View view) {
        Intent intent = new Intent(PrivateActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        String sub;
        Handler handler = new Handler();

        switch (v.getId()) {
            case R.id.plus_btn:
                sub = displayAmount.getText().toString();
                minteger = Integer.parseInt(sub) * 2;
                displayAmount.setText(String.valueOf(minteger));
                displayAmount.setBackgroundResource(R.drawable.emptybtn);
                plus_btn.setEnabled(false);
                plus_btn.setImageResource(R.drawable.disabled);
                minus_btn.setImageResource(R.drawable.minus_btn);
                minus_btn.setEnabled(true);
                Log.i("AmtSUb", "" + minteger);
                break;

            case R.id.minus_btn:
                plus_btn.setImageResource(R.drawable.plus_btn);
                plus_btn.setEnabled(true);
                minus_btn.setEnabled(false);
                sub = displayAmount.getText().toString();
                Toast.makeText(this, "" + sub, Toast.LENGTH_SHORT).show();
                minteger = Integer.parseInt(sub) / 2;
                displayAmount.setText(String.valueOf(minteger));
                displayAmount.setBackgroundResource(R.drawable.emptybtn);
                minus_btn.setImageResource(R.drawable.minus_disabled);
                Log.i("AmtSUb", "" + minteger);
                break;

            case R.id.blind_btn:
//                display_myplayer_bind.setText(displayAmount.getText().toString());
//                display_myplayer_bind.bringToFront();
//                int DisplayAmt = Integer.parseInt(displayAmount.getText().toString().replaceAll("₹","").replace(" ",""));
//                int TablelayAmt = Integer.parseInt(txtVTableAmt.getText().toString().replaceAll("₹","").replace(" ",""));
//                int AMOUNT = DisplayAmt+TablelayAmt;
//                txtVTableAmt.setText(String.valueOf(AMOUNT));
//
//                if (AMOUNT >= PotLimitInt){
//                    Toast.makeText(this, "Pot Limit Exceeded", Toast.LENGTH_SHORT).show();
//                }
//                txtVTableAmt.setText(String.valueOf(DisplayAmt+TablelayAmt));
//                animations = AnimationUtils.loadAnimation(PrivateActivity.this, R.anim.translate_text_up);
//                display_myplayer_bind.startAnimation(animations);
//                display_myplayer_bind.setVisibility(View.GONE);
//
//                handler = new Handler();
//                handler1 = new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        below_layout.setVisibility(View.VISIBLE);
////              Animation animation = AnimationUtils.loadAnimation(PrivateActivity.this, R.anim.translate_up_below_layout);
////              animation.setDuration(1000);
////              animation.setFillAfter(true);
////              below_layout.startAnimation(animation);
//                    }
//                }, 2000);
//
//                handler1.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
////          Animation animation1 =  AnimationUtils.loadAnimation(PrivateActivity.this, R.anim.translate_bottom_blind_chaal);
////          animation1.setDuration(1000);
////          animation1.setFillAfter(true);
////          rl_bottom_caption.startAnimation(animation1);
//                        rl_bottom_caption.setVisibility(View.GONE);
//                        blind_btn.setEnabled(false);
//                    }
//                }, 1000);
                chaalBlind();
                break;

            case R.id.chaal_btn:
                chaalBlind();
                break;

            case R.id.pack_btn:
                rl_bottom_caption.setVisibility(View.GONE);
                btn_see_cards.setVisibility(View.GONE);
                below_layout.setVisibility(View.VISIBLE);
                card3.setVisibility(View.GONE);
                card8.setVisibility(View.GONE);
                card13.setVisibility(View.GONE);
                inner_player_img.setImageResource(R.drawable.fade_inner_img);
                break;

            case R.id.show_btn:
                Glide.with(PrivateActivity.this).load(cardUrl4).into(card1);
                Glide.with(PrivateActivity.this).load(cardUrl5).into(card6);
                Glide.with(PrivateActivity.this).load(cardUrl6).into(card11);

                Glide.with(PrivateActivity.this).load(cardUrl1).into(card2);
                Glide.with(PrivateActivity.this).load(cardUrl2).into(card7);
                Glide.with(PrivateActivity.this).load(cardUrl3).into(card12);

                Glide.with(PrivateActivity.this).load(cardUrl13).into(card4);
                Glide.with(PrivateActivity.this).load(cardUrl14).into(card9);
                Glide.with(PrivateActivity.this).load(cardUrl15).into(card14);

                Glide.with(PrivateActivity.this).load(cardUrl10).into(card5);
                Glide.with(PrivateActivity.this).load(cardUrl11).into(card10);
                Glide.with(PrivateActivity.this).load(cardUrl12).into(card15);
                break;

            case R.id.imgVInfo:
                popupLimitedData(BootValue, PotLimit, MaxBlind, chaalLimit);
                break;
        }

        return;
    }

    //INFO POPUP DATA
    public void popupLimitedData(String BootValue, String PotLimit, String MaxBlind, String chaalLimit) {

        LayoutInflater layoutInflater = (LayoutInflater) PrivateActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView = layoutInflater.inflate(R.layout.private_gameinfo_popup, null);

        txtVBootValue = customView.findViewById(R.id.txtVBootValue);
        txtVPotlimit = customView.findViewById(R.id.txtVPotlimit);
        txtVMaxBlind = customView.findViewById(R.id.txtVMaxBlind);
        txtVChaalLimit = customView.findViewById(R.id.txtVChaalLimit);

        txtVBootValue.setText(BootValue);
        txtVPotlimit.setText(PotLimit);
        txtVMaxBlind.setText(MaxBlind);
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

    //CHAAl & BLIND
    public void chaalBlind() {
        Handler handler1 = new Handler();
        display_myplayer_bind.setText(displayAmount.getText().toString());
        display_myplayer_bind.bringToFront();
        int DisplayAmtc = Integer.parseInt(displayAmount.getText().toString().replaceAll("₹", "").replace(" ", ""));
        int TablelayAmtc = Integer.parseInt(txtVTableAmt.getText().toString().replaceAll("₹", "").replace(" ", ""));
        int AMOUNT = DisplayAmtc + TablelayAmtc;
        txtVTableAmt.setText(String.valueOf(AMOUNT));

        if (AMOUNT >= PotLimitInt) {
            Toast.makeText(this, "Pot Limit Exceeded", Toast.LENGTH_SHORT).show();
        }
        animations = AnimationUtils.loadAnimation(PrivateActivity.this, R.anim.translate_text_up);
        display_myplayer_bind.startAnimation(animations);
        display_myplayer_bind.setVisibility(View.GONE);

        handler = new Handler();
        handler1 = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                below_layout.setVisibility(View.VISIBLE);
//              Animation animation = AnimationUtils.loadAnimation(PrivateActivity.this, R.anim.translate_up_below_layout);
//              animation.setDuration(1000);
//              animation.setFillAfter(true);
//              below_layout.startAnimation(animation);
            }
        }, 2000);

        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
//          Animation animation1 =  AnimationUtils.loadAnimation(PrivateActivity.this, R.anim.translate_bottom_blind_chaal);
//          animation1.setDuration(1000);
//          animation1.setFillAfter(true);
//          rl_bottom_caption.startAnimation(animation1);
                rl_bottom_caption.setVisibility(View.GONE);
                blind_btn.setEnabled(false);
            }
        }, 1000);
    }


    public String setUserApi(String url) {
        InputStream inputStream = null;
        String result = "";
        try {

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost Httppost = new HttpPost(url);

            String json = "";
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("users_id", user_id);
            jsonObject.accumulate("users_id", user_id1);
            jsonObject.accumulate("users_id", user_id2);
            jsonObject.accumulate("users_id", user_id3);
            jsonObject.accumulate("users_id", user_id4);

            jsonObject.accumulate("username", nametext);
            jsonObject.accumulate("username", nametext1);
            jsonObject.accumulate("username", nametext2);
            jsonObject.accumulate("username", nametext3);
            jsonObject.accumulate("username", nametext4);


            json = jsonObject.toString();
            StringEntity se = new StringEntity(json);
            se.setContentType("application/json");

            Httppost.setEntity(new StringEntity(json));
            Httppost.setHeader("Accept", "application/json");
            Httppost.setHeader("Content-type", "application/json");

            HttpResponse httpResponse = httpclient.execute(Httppost);
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
        Log.e("result", result + "");
        return result;
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

    private class setUserDataAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            return setUserApi(urls[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            Log.i("Check", "" + result);
            try {
                JSONObject jsonObjMain = new JSONObject(result.toString());
                String message = jsonObjMain.getString("message");
                if (message.equalsIgnoreCase("successfully authenticated")) {
                    DataHolder.setSTACK(PrivateActivity.this, "users_id", user_id.getText().toString());
                    DataHolder.setSTACK(PrivateActivity.this, "users_id", user_id1.getText().toString());
                    DataHolder.setSTACK(PrivateActivity.this, "users_id", user_id2.getText().toString());
                    DataHolder.setSTACK(PrivateActivity.this, "users_id", user_id3.getText().toString());
                    DataHolder.setSTACK(PrivateActivity.this, "users_id", user_id4.getText().toString());


                    DataHolder.setSTACK(PrivateActivity.this, "username", nametext.getText().toString());
                    DataHolder.setSTACK(PrivateActivity.this, "username", nametext1.getText().toString());
                    DataHolder.setSTACK(PrivateActivity.this, "username", nametext2.getText().toString());
                    DataHolder.setSTACK(PrivateActivity.this, "username", nametext3.getText().toString());
                    DataHolder.setSTACK(PrivateActivity.this, "username", nametext4.getText().toString());
                }
//                Toast.makeText(getApplicationContext(), "" + message, Toast.LENGTH_SHORT).show();

                Log.i("result", " Status " + message);
            } catch (JSONException e) {
                e.printStackTrace();
                DataHolder.unAuthorized(PrivateActivity.this, result);
            }
            DataHolder.cancelProgress();
        }
    }

    public String getUserApi(String url) {
        InputStream inputStream = null;
        String result = "";
        try {

            HttpClient httpclient = new DefaultHttpClient();
            HttpGet Httpget = new HttpGet(url);

            Httpget.setHeader("Accept", "application/json");
            Httpget.setHeader("Content-type", "application/json");
            Httpget.setHeader("Authorization", DataHolder.getDataString(PrivateActivity.this,"token"));

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


    String cardUrl1, cardUrl2, cardUrl3, cardUrl4, cardUrl5, cardUrl6, cardUrl7, cardUrl8, cardUrl9, cardUrl10, cardUrl11, cardUrl12, cardUrl13, cardUrl14, cardUrl15;

    private class getUserDataAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            return getUserApi(urls[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            //Toast.makeText(PrivateActivity.this, "" + result, Toast.LENGTH_SHORT).show();
            Log.i("Check", "" + result);
            try {
                JSONObject jsonObjMain = new JSONObject(result.toString());
                String message = jsonObjMain.getString("message");
                Log.i("TAG", "" + message);

                JSONArray arr = new JSONArray(jsonObjMain.getString("data"));

                int len = arr.length();

                for (int i = 0; i < len; i++) {

                    JSONObject key = arr.getJSONObject(i);
//
//                    Log.i("Card","" + card1);
//                    Log.i("Card","" + card2);
//                    Log.i("Card","" + card3);
//
//                    nametext.setText(key.getString("username"));
//                    user_id.setText(key.getString("users_id"));
                    {
                        if (i == 0) {
                            String Url1 = key.getString("card1");
                            String Url2 = key.getString("card2");
                            String Url3 = key.getString("card3");

                            cardUrl1 = Url1;
                            cardUrl2 = Url2;
                            cardUrl3 = Url3;

                            Log.i("URL 1", i + " " + Url1);
                            Log.i("URL 2", i + " " + Url2);
                            Log.i("URL 3", i + " " + Url3);

                            nametext1.setText(key.getString("username"));
                        } else if (i == 1) {
                            String Url4 = key.getString("card1");
                            String Url5 = key.getString("card2");
                            String Url6 = key.getString("card3");

                            cardUrl4 = Url4;
                            cardUrl5 = Url5;
                            cardUrl6 = Url6;

                            Log.i("URL 4", i + " " + Url4);
                            Log.i("URL 5", i + " " + Url5);
                            Log.i("URL 6", i + " " + Url6);

                            nametext2.setText(key.getString("username"));

                        } else if (i == 2) {
                            String Url7 = key.getString("card1");
                            String Url8 = key.getString("card2");
                            String Url9 = key.getString("card3");
                            cardUrl7 = Url7;
                            cardUrl8 = Url8;
                            cardUrl9 = Url9;

                            Log.i("URL 7", i + " " + Url7);
                            Log.i("URL 8", i + " " + Url8);
                            Log.i("URL 9", i + " " + Url9);

                            nametext.setText(key.getString("username"));

                        } else if (i == 3) {
                            String Url10 = key.getString("card1");
                            String Url11 = key.getString("card2");
                            String Url12 = key.getString("card3");
                            cardUrl10 = Url10;
                            cardUrl11 = Url11;
                            cardUrl12 = Url12;

                            Log.i("URL 10", i + " " + Url10);
                            Log.i("URL 11", i + " " + Url11);
                            Log.i("URL 12", i + " " + Url12);

                            nametext3.setText(key.getString("username"));
                        } else if (i == 4) {
                            String Url13 = key.getString("card1");
                            String Url14 = key.getString("card2");
                            String Url15 = key.getString("card3");
                            cardUrl13 = Url13;
                            cardUrl14 = Url14;
                            cardUrl15 = Url15;

                            Log.i("URL 13", i + " " + Url13);
                            Log.i("URL 14", i + " " + Url14);
                            Log.i("URL 15", i + " " + Url15);

                            nametext4.setText(key.getString("username"));
                        }
                    }
                }

                JSONArray u_arr = new JSONArray(jsonObjMain.getString("data"));

                int u_len = u_arr.length();

                for (int u = 0; u < u_len; u++) {

                    JSONObject id = arr.getJSONObject(u);

                    if (u == 0) {
                        user_id1.setText(id.getString("users_id"));
                    } else if (u == 1) {
                        user_id2.setText(id.getString("users_id"));
                    } else if (u == 2) {
                        user_id.setText(id.getString("users_id"));
                    } else if (u == 3) {
                        user_id3.setText(id.getString("users_id"));
                    } else if (u == 4) {
                        user_id4.setText(id.getString("users_id"));
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class getCardDataAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            return getUserApi(urls[0]);
        }

        @Override
        protected void onPostExecute(String result) {

            Log.i("Check", "" + result);
            Toast.makeText(PrivateActivity.this, "" + DataHolder.getDataString(PrivateActivity.this,"token"), Toast.LENGTH_SHORT).show();

            try {
                JSONObject jsonObjMain = new JSONObject(result);
                String message = jsonObjMain.getString("message");
                Log.i("TAG", "" + message);

                JSONArray arr = new JSONArray(jsonObjMain.getString("data"));
                int len = arr.length();
                for (int i = 0; i < len; i++) {
                    JSONObject key = arr.getJSONObject(i);
                    /* TODO "cardsallocatedusers_id": "20189210004",
                       TODO "first_name": "Ravi",
                       TODO "cardone": "http://213.136.81.137/Cards/2_Hearts.png",
                       TODO "cardtwo": "http://213.136.81.137/Cards/2_Spades.png",
                       TODO "cardthree": "http://213.136.81.137/Cards/7_Spades.png",
                       TODO "given_cards": "3,4,24",
                       TODO "cardseen": "seen",
                       TODO "round_number": "1",
                       TODO "winner_type": "Pair",
                       TODO "winner_rank": ""*/

                    if (i == 0) {
                        String Url1 = key.getString("cardone");
                        String Url2 = key.getString("cardtwo");
                        String Url3 = key.getString("cardthree");
                        cardUrl1 = Url1;
                        cardUrl2 = Url2;
                        cardUrl3 = Url3;
                        Log.i("URL 1", i + " " + Url1);
                        Log.i("URL 2", i + " " + Url2);
                        Log.i("URL 3", i + " " + Url3);
                        nametext1.setText(key.getString("first_name"));
                    } else if (i == 1) {
                        String Url4 = key.getString("cardone");
                        String Url5 = key.getString("cardtwo");
                        String Url6 = key.getString("cardthree");
                        cardUrl4 = Url4;
                        cardUrl5 = Url5;
                        cardUrl6 = Url6;
                        Log.i("URL 4", i + " " + Url4);
                        Log.i("URL 5", i + " " + Url5);
                        Log.i("URL 6", i + " " + Url6);
                        nametext2.setText(key.getString("first_name"));
                    } else if (i == 2) {
                        String Url7 = key.getString("cardone");
                        String Url8 = key.getString("cardtwo");
                        String Url9 = key.getString("cardthree");
                        cardUrl7 = Url7;
                        cardUrl8 = Url8;
                        cardUrl9 = Url9;
                        Log.i("URL 7", i + " " + Url7);
                        Log.i("URL 8", i + " " + Url8);
                        Log.i("URL 9", i + " " + Url9);
                        nametext.setText(key.getString("first_name"));
                    } else if (i == 3) {
                        String Url10 = key.getString("cardone");
                        String Url11 = key.getString("cardtwo");
                        String Url12 = key.getString("cardthree");
                        cardUrl10 = Url10;
                        cardUrl11 = Url11;
                        cardUrl12 = Url12;
                        Log.i("URL 10", i + " " + Url10);
                        Log.i("URL 11", i + " " + Url11);
                        Log.i("URL 12", i + " " + Url12);
                        nametext3.setText(key.getString("first_name"));
                    } else if (i == 4) {
                        String Url13 = key.getString("cardone");
                        String Url14 = key.getString("cardtwo");
                        String Url15 = key.getString("cardthree");
                        cardUrl13 = Url13;
                        cardUrl14 = Url14;
                        cardUrl15 = Url15;
                        Log.i("URL 13", i + " " + Url13);
                        Log.i("URL 14", i + " " + Url14);
                        Log.i("URL 15", i + " " + Url15);
                        nametext4.setText(key.getString("first_name"));
                    }

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

//            try {
//                JSONObject jsonObjMain = new JSONObject(result.toString());
//                String message = jsonObjMain.getString("message");
//                Log.i("TAG", "" + message);
//
//                JSONArray arr = new JSONArray(jsonObjMain.getString("data"));
//
//                int len = arr.length();
//
//                for (int i = 0; i < len; i++) {
//
//                    JSONObject key = arr.getJSONObject(i);
////
////                    Log.i("Card","" + card1);
////                    Log.i("Card","" + card2);
////                    Log.i("Card","" + card3);
////
////                    nametext.setText(key.getString("username"));
////                    user_id.setText(key.getString("users_id"));
//                    {
//                        if (i == 0) {
//                            String Url1 = key.getString("card1");
//                            String Url2 = key.getString("card2");
//                            String Url3 = key.getString("card3");
//
//                            cardUrl1 = Url1;
//                            cardUrl2 = Url2;
//                            cardUrl3 = Url3;
//
//                            Log.i("URL 1", i + " " + Url1);
//                            Log.i("URL 2", i + " " + Url2);
//                            Log.i("URL 3", i + " " + Url3);
//
//                            nametext1.setText(key.getString("username"));
//                        } else if (i == 1) {
//                            String Url4 = key.getString("card1");
//                            String Url5 = key.getString("card2");
//                            String Url6 = key.getString("card3");
//
//                            cardUrl4 = Url4;
//                            cardUrl5 = Url5;
//                            cardUrl6 = Url6;
//
//                            Log.i("URL 4", i + " " + Url4);
//                            Log.i("URL 5", i + " " + Url5);
//                            Log.i("URL 6", i + " " + Url6);
//
//                            nametext2.setText(key.getString("username"));
//
//                        } else if (i == 2) {
//                            String Url7 = key.getString("card1");
//                            String Url8 = key.getString("card2");
//                            String Url9 = key.getString("card3");
//                            cardUrl7 = Url7;
//                            cardUrl8 = Url8;
//                            cardUrl9 = Url9;
//
//                            Log.i("URL 7", i + " " + Url7);
//                            Log.i("URL 8", i + " " + Url8);
//                            Log.i("URL 9", i + " " + Url9);
//
//                            nametext.setText(key.getString("username"));
//
//                        } else if (i == 3) {
//                            String Url10 = key.getString("card1");
//                            String Url11 = key.getString("card2");
//                            String Url12 = key.getString("card3");
//                            cardUrl10 = Url10;
//                            cardUrl11 = Url11;
//                            cardUrl12 = Url12;
//
//                            Log.i("URL 10", i + " " + Url10);
//                            Log.i("URL 11", i + " " + Url11);
//                            Log.i("URL 12", i + " " + Url12);
//
//                            nametext3.setText(key.getString("username"));
//                        } else if (i == 4) {
//                            String Url13 = key.getString("card1");
//                            String Url14 = key.getString("card2");
//                            String Url15 = key.getString("card3");
//                            cardUrl13 = Url13;
//                            cardUrl14 = Url14;
//                            cardUrl15 = Url15;
//
//                            Log.i("URL 13", i + " " + Url13);
//                            Log.i("URL 14", i + " " + Url14);
//                            Log.i("URL 15", i + " " + Url15);
//
//                            nametext4.setText(key.getString("username"));
//                        }
//                    }
//                }
//
//                JSONArray u_arr = new JSONArray(jsonObjMain.getString("data"));
//
//                int u_len = u_arr.length();
//
//                for (int u = 0; u < u_len; u++) {
//
//                    JSONObject id = arr.getJSONObject(u);
//
//                    if (u == 0) {
//                        user_id1.setText(id.getString("users_id"));
//                    } else if (u == 1) {
//                        user_id2.setText(id.getString("users_id"));
//                    } else if (u == 2) {
//                        user_id.setText(id.getString("users_id"));
//                    } else if (u == 3) {
//                        user_id3.setText(id.getString("users_id"));
//                    } else if (u == 4) {
//                        user_id4.setText(id.getString("users_id"));
//                    }
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
        }
    }
    //setApiToken
    private ArrayList<String> arrayListUserId = new ArrayList<>();
    private ArrayList<String> arrayListUserIdSequence = new ArrayList<>();
    private boolean sequence = false;
    public static String BootValue, PotLimit, MaxBlind, chaalLimit;
    int PotLimitInt;

    private class UserDataAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            return getUserApi(urls[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(PrivateActivity.this, "" + result, Toast.LENGTH_SHORT).show();
            Log.i("Check123", "" + result);
            try {
                JSONObject jsonObjMain = new JSONObject(result.toString());

                BootValue = jsonObjMain.getString("boot_value");
                MaxBlind = jsonObjMain.getString("max_no_blinds");
                PotLimit = jsonObjMain.getString("pot_limit");
                PotLimitInt = Integer.parseInt(PotLimit);
                chaalLimit = jsonObjMain.getString("chaal_limit");

                JSONArray arr = new JSONArray(jsonObjMain.getString("data"));

                int len = arr.length();

                for (int i = 0; i < len; i++) {

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
                }

                for (int i = 0; i < len; i++) {

                    JSONObject key = arr.getJSONObject(i);
                    String userid = key.getString("user_id");
                    String user_name = key.getString("user_name");

                    Log.i("CHECk", "" + arrayListUserIdSequence.get(i));

                    if (userid.equals(arrayListUserIdSequence.get(0))) {
                        nametext.setText(user_name);
                        user_id.setText(userid);
                        txtVBalanceMainPlayer.setText(key.getString("balance"));

                    } else if (userid.equals(arrayListUserIdSequence.get(1))) {
                        nametext1.setText(user_name);
                        user_id1.setText(userid);

                    } else if (userid.equals(arrayListUserIdSequence.get(2))) {
                        nametext2.setText(user_name);
                        user_id2.setText(userid);

                    } else if (userid.equals(arrayListUserIdSequence.get(3))) {
                        nametext3.setText(user_name);
                        user_id3.setText(userid);

                    } else if (userid.equals(arrayListUserIdSequence.get(4))) {
                        nametext4.setText(user_name);
                        user_id4.setText(userid);

                    }

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    //User Play Time


    @Override
    protected void onResume() {
        super.onResume();
        simulateProgress();
    }

    private void simulateProgress() {
        ValueAnimator animator = ValueAnimator.ofInt(0, 100);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int progress = (int) animation.getAnimatedValue();
                mCustomProgressBar2.setProgress(progress);
            }
        });
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setDuration(10000);
        animator.start();
    }
}