package affwl.com.teenpatti;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
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
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;

@SuppressWarnings("deprecation")
@SuppressLint("WrongViewCast")

public class PrivateActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView player_blink_circle1,player_blink_circle2,player_blink_circle3,player_blink_circle4, handle_right, backbtn, imgVInfo, infoclosebtn, profile, profile1, profile2, profile3, profile4, plus_btn, minus_btn, myplayerbtn, ustatusclosebtn, dealerbtn, dealerclsbtn, oplayerbtn, oustatusclosebtn, msgclosebtn, chngdealerclosebtn, close_player_status, oplayer_status_circle, player_status_circle, card1, card2, card3, card4, card5, card6, card7, card8, card9, card10, card11, card12, card13, card14, card15, myplayer, winnerblinker1, winnerblinker2, player1, player2, player3, player4;
    TextView player_balance, player_name, other_player_name, other_player_balance, displayAmount, display_myplayer_bind, txtVBalanceMainPlayer, txtVTableAmt, txtVBootValue, txtVPotlimit, txtVMaxBlind, txtVChaalLimit, btn_see_cards, user_id, user_id1, user_id2, user_id3, user_id4, closebtn, tipsbtn, chngdbtn, canceltipbtn, plusbtn, minusbtn, backtolobby, nametext, nametext1, nametext2, nametext3, nametext4, code, blind_btn, chaal_btn, show_btn, pack_btn;
    PopupWindow popupWindow, infopopupWindow, chatpopupWindow, ustatuspopupWindow, dealerpopupWindow, oustatuspopupWindow, sendmsgpopupWindow, chngdpopupWindow, PlayerStatusWindow, OPlayerStatusWindow;
    RelativeLayout relativeLayout, relativeLayout2, relativeLayout3, privatetble, playerlayout;
    Session session;
    LinearLayout below_layout;
    CircleImageView inner_player_img;
    Animation animations, animatecard1, animatecard2, animatecard3, animatecard4, animatecard5, animatecard6, animatecard7, animatecard8, animatecard9, animatecard10, animatecard11, animatecard12, animatecard13, animatecard14, animatecard15, animBlink;
    PercentRelativeLayout rl_bottom_caption;
    View  viewBlinkCircle;
    Handler handler;
    int minteger = 0;
    private CircleProgressBar progressBarChances;
    ScheduledExecutorService scheduleTaskExecutor;
    MediaPlayer mediaPlayer;
    RoundCornerProgressBar progressChaalTimer;
    String USER_NAME, USER_NAME1, USER_NAME2, USER_NAME3, USER_NAME4, BALANCE, BALANCE1, BALANCE2, BALANCE3, BALANCE4;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private);


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

        new UserDataAsyncTask().execute("http://213.136.81.137:8081/api/getclientdesk?user_id=" + DataHolder.getDataString(this, "userid"));

        //new getCardDataAsyncTask().execute("http://213.136.81.137:8081/api/get_desk_cards?desk_id="+DeskId);


        //new GetChanceAsyncTask().execute("");


        //  new GetChanceAsyncTask().execute("http://213.136.81.137:8081/api/getchancedetail?desk_id="+DeskId+"&round=1&turn=1");


//        ImageView myplayer = findViewById(R.id.myplayer);
//        myplayer.setOnClickListener(this);
//        ImageView player1 = findViewById(R.id.player1);
//        player1.setOnClickListener(this);
//        ImageView player2 = findViewById(R.id.player2);
//        player2.setOnClickListener(this);
//        ImageView player3 = findViewById(R.id.player3);
//        player3.setOnClickListener(this);
//        ImageView player4 = findViewById(R.id.player4);
//        player4.setOnClickListener(this);

        other_player_name = findViewById(R.id.other_player_name);
        other_player_balance = findViewById(R.id.other_player_balance);

        display_myplayer_bind = findViewById(R.id.display_myplayer_bind);
        rl_bottom_caption = findViewById(R.id.rl_bottom_caption);
        below_layout = findViewById(R.id.below_layout);
        relativeLayout = findViewById(R.id.privaterecycler);
        txtVTableAmt = findViewById(R.id.txtVTableAmt);
        progressBarChances = findViewById(R.id.progressBarChances);
        viewBlinkCircle = new View(PrivateActivity.this);

        //        implemention of user profile pic
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
        btn_see_cards.setOnClickListener(this);
        show_btn = findViewById(R.id.show_btn);
        show_btn.setOnClickListener(this);

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
                btn_see_cards.setVisibility(View.VISIBLE);
//                rl_bottom_caption.setVisibility(View.VISIBLE);
                below_layout.setVisibility(View.GONE);
            }
        });

        //////////////// Popup for Backbutton ///////////////////

        backbtn = findViewById(R.id.back);
        privatetble = findViewById(R.id.privatetble);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backtolobby();
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


//        winnerblinker1.setVisibility(View.INVISIBLE);
//        winnerblinker1.postDelayed(new Runnable() {
//            public void run() {
//                winnerblinker1.setVisibility(View.VISIBLE);
//                profile.setOnClickListener(new View.OnClickListener() {
//
//                    @Override
//                    public void onClick(View v) {
//                        winnerblinker1.setVisibility(View.VISIBLE);
////                        winnerblinker2.setVisibility(View.VISIBLE);
//
//                        // start the animation
//                        winnerblinker1.startAnimation(animBlink);
////                        winnerblinker2.startAnimation(animBlink);
//                    }
//                });
//                winnerblinker1.clearAnimation();
//                winnerblinker1.setVisibility(View.GONE);
//            }
//        }, 600);
    }


    @Override
    public void onBackPressed() {
        backtolobby();
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
                DataHolder.setData(PrivateActivity.this, "userstatus", "offline");
                new updateUserStatusAsyncTask().execute("http://213.136.81.137:8081/api/update_client_status");
                stopService(new Intent(PrivateActivity.this, ServiceLastUserData.class));
                try {
                    if (broadcastReceiver != null) {
                        unregisterReceiver(broadcastReceiver);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(PrivateActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        DataHolder.getDataString(PrivateActivity.this,"userstatus");
    }

    @Override
    public void onClick(View v) {
        String sub;
        Handler handler = new Handler();
        session = new Session(this);
        String encodedimage = session.getImage();

        playerlayout = findViewById(R.id.privatetble);
        LayoutInflater inflater = (LayoutInflater) PrivateActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        View customView = inflater.inflate(R.layout.player_status_popup, null, false);
        View customView2 = inflater.inflate(R.layout.other_player_status, null, false);

        switch (v.getId()) {
            case R.id.plus_btn:
                ChaalAmount = ChaalAmount * 2;
                displayAmount.setText(String.valueOf(ChaalAmount));
                displayAmount.setBackgroundResource(R.drawable.emptybtn);
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
                displayAmount.setText(String.valueOf(ChaalAmount));
                displayAmount.setBackgroundResource(R.drawable.emptybtn);
                minus_btn.setImageResource(R.drawable.minus_disabled);
                break;

            case R.id.blind_btn:
                chaalBlind();
                break;

            case R.id.chaal_btn:
                chaalBlind();
                break;

            case R.id.pack_btn:
                packOperation();
                break;

            case R.id.show_btn:
                Handler handlerDisp = new Handler();
                Show_Status = "1";
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
                break;

            case R.id.imgVInfo:
                popupLimitedData(BootValue, PotLimit, MaxBlind, chaalLimit);
                break;

            case R.id.btn_see_cards:
                seeCardOperation();
                break;


            case R.id.inner_player_img:

                Toast.makeText(this, "Click", Toast.LENGTH_SHORT).show();

                mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.click);
                mediaPlayer.start();

                PlayerStatusWindow = new PopupWindow(customView, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT, true);

                player_name = customView.findViewById(R.id.player_name);
                player_balance = customView.findViewById(R.id.player_balance);
                player_status_circle = customView.findViewById(R.id.player_status_circle);

                player_name.setText(USER_NAME);
                player_balance.setText(BALANCE);

                PlayerStatusWindow.showAtLocation(playerlayout, Gravity.CENTER, 0, 0);

                if (!encodedimage.equalsIgnoreCase("")) {
                    byte[] b = Base64.decode(encodedimage, Base64.DEFAULT);
                    Bitmap bmp = BitmapFactory.decodeByteArray(b, 0, b.length);
                    player_status_circle.setImageBitmap(bmp);
                }
                break;

            case R.id.inner_player_img1:

                mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.click);
                mediaPlayer.start();

                OPlayerStatusWindow = new PopupWindow(customView2, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT, true);

                other_player_name = customView2.findViewById(R.id.other_player_name);
                other_player_balance = customView2.findViewById(R.id.other_player_balance);
                oplayer_status_circle = customView2.findViewById(R.id.oplayer_status_circle);

                other_player_name.setText(USER_NAME1);
                other_player_balance.setText(BALANCE1);

                OPlayerStatusWindow.showAtLocation(playerlayout, Gravity.CENTER, 0, 0);

                if (!encodedimage.equalsIgnoreCase("")) {
                    byte[] b = Base64.decode(encodedimage, Base64.DEFAULT);
                    Bitmap bmp = BitmapFactory.decodeByteArray(b, 0, b.length);
                    oplayer_status_circle.setImageBitmap(bmp);
                }
                break;

            case R.id.inner_player_img2:

                mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.click);
                mediaPlayer.start();

                OPlayerStatusWindow = new PopupWindow(customView2, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT, true);

                other_player_name = customView2.findViewById(R.id.other_player_name);
                other_player_balance = customView2.findViewById(R.id.other_player_balance);
                oplayer_status_circle = customView2.findViewById(R.id.oplayer_status_circle);

                other_player_name.setText(USER_NAME2);
                other_player_balance.setText(BALANCE2);

                OPlayerStatusWindow.showAtLocation(playerlayout, Gravity.CENTER, 0, 0);

                if (!encodedimage.equalsIgnoreCase("")) {
                    byte[] b = Base64.decode(encodedimage, Base64.DEFAULT);
                    Bitmap bmp = BitmapFactory.decodeByteArray(b, 0, b.length);
                    oplayer_status_circle.setImageBitmap(bmp);
                }
                break;

            case R.id.inner_player_img3:

                mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.click);
                mediaPlayer.start();

                OPlayerStatusWindow = new PopupWindow(customView2, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT, true);

                other_player_name = customView2.findViewById(R.id.other_player_name);
                other_player_balance = customView2.findViewById(R.id.other_player_balance);
                oplayer_status_circle = customView2.findViewById(R.id.oplayer_status_circle);

                other_player_name.setText(USER_NAME3);
                other_player_balance.setText(BALANCE3);

                OPlayerStatusWindow.showAtLocation(playerlayout, Gravity.CENTER, 0, 0);

                if (!encodedimage.equalsIgnoreCase("")) {
                    byte[] b = Base64.decode(encodedimage, Base64.DEFAULT);
                    Bitmap bmp = BitmapFactory.decodeByteArray(b, 0, b.length);
                    oplayer_status_circle.setImageBitmap(bmp);
                }
                break;

            case R.id.inner_player_img4:

                mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.click);
                mediaPlayer.start();

                OPlayerStatusWindow = new PopupWindow(customView2, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT, true);

                other_player_name = customView2.findViewById(R.id.other_player_name);
                other_player_balance = customView2.findViewById(R.id.other_player_balance);
                oplayer_status_circle = customView2.findViewById(R.id.oplayer_status_circle);

                other_player_name.setText(USER_NAME4);
                other_player_balance.setText(BALANCE4);

                OPlayerStatusWindow.showAtLocation(playerlayout, Gravity.CENTER, 0, 0);

                if (!encodedimage.equalsIgnoreCase("")) {
                    byte[] b = Base64.decode(encodedimage, Base64.DEFAULT);
                    Bitmap bmp = BitmapFactory.decodeByteArray(b, 0, b.length);
                    oplayer_status_circle.setImageBitmap(bmp);
                }
        }
    }

    //PACK USER
    public void packOperation(){
        rl_bottom_caption.setVisibility(View.GONE);
        btn_see_cards.setVisibility(View.GONE);
        below_layout.setVisibility(View.VISIBLE);
        card3.setVisibility(View.GONE);
        card8.setVisibility(View.GONE);
        card13.setVisibility(View.GONE);
        inner_player_img.setImageResource(R.drawable.fade_inner_img);
        chance_Status = "packed";
        CHECK_TIME_OUT = true;
        new ChanceAsyncTask().execute("http://213.136.81.137:8081/api/insertChance");
    }

    //Seen Card Operation
    public void seeCardOperation(){
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
        show_btn.setVisibility(View.VISIBLE);
        blind_btn.setVisibility(View.GONE);
        chaal_btn.setVisibility(View.VISIBLE);
        Seen_Blind="seen";

        ChaalAmount = ChaalAmount * 2;
        displayAmount.setText(String.valueOf(ChaalAmount));
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
        display_myplayer_bind.setText(String.valueOf(ChaalAmount));
        display_myplayer_bind.bringToFront();
        int TablelayAmtc = Integer.parseInt(txtVTableAmt.getText().toString().replaceAll("₹", "").replace(" ", ""));
        int AMOUNT = ChaalAmount + TablelayAmtc;
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
                    String userid = key.getString("cardsallocatedusers_id");
                    Log.i("CHECk", "" + arrayListUserIdSequence.get(i)+ "  "+key.getString("first_name"));
                    if (userid.equalsIgnoreCase(arrayListUserIdSequence.get(0))) {
                        String Url1 = key.getString("cardone");
                        String Url2 = key.getString("cardtwo");
                        String Url3 = key.getString("cardthree");
                        cardUrl1 = Url1;
                        cardUrl2 = Url2;
                        cardUrl3 = Url3;
                        Log.i("URL 1", i + " " + Url1);
                        Log.i("URL 2", i + " " + Url2);
                        Log.i("URL 3", i + " " + Url3);
                        //nametext1.setText(key.getString("first_name"));
                    } else if (userid.equalsIgnoreCase(arrayListUserIdSequence.get(1))) {
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
                    } else if (userid.equalsIgnoreCase(arrayListUserIdSequence.get(2))) {
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
                    } else if (userid.equalsIgnoreCase(arrayListUserIdSequence.get(3))) {
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
                    } else if (userid.equalsIgnoreCase(arrayListUserIdSequence.get(4))) {
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

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    private ArrayList<String> arrayListUserId = new ArrayList<>();
    private ArrayList<String> arrayListUserIdSequence = new ArrayList<>();
    private boolean sequence = false;
    public static String BootValue, PotLimit, MaxBlind, chaalLimit,DeskId;
    int PotLimitInt,ChaalAmount;

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
                ChaalAmount = Integer.parseInt(jsonObjMain.getString("desk_limit"));//Start Chaal
                displayAmount.setText(String.valueOf(ChaalAmount));
                DeskId = jsonObjMain.getString("desk_id");

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
                        USER_NAME = user_name;
                        BALANCE = key.getString("balance");
                        user_id.setText(userid);
                        txtVBalanceMainPlayer.setText(key.getString("balance"));

                    } else if (userid.equals(arrayListUserIdSequence.get(1))) {
                        USER_NAME1 = user_name;
                        BALANCE1 = key.getString("balance");
                        nametext1.setText(user_name);
                        user_id1.setText(userid);

                    } else if (userid.equals(arrayListUserIdSequence.get(2))) {
                        USER_NAME2 = user_name;
                        BALANCE2 = key.getString("balance");
                        nametext2.setText(user_name);
                        user_id2.setText(userid);

                    } else if (userid.equals(arrayListUserIdSequence.get(3))) {
                        USER_NAME3 = user_name;
                        BALANCE3 = key.getString("balance");
                        nametext3.setText(user_name);
                        user_id3.setText(userid);

                    } else if (userid.equals(arrayListUserIdSequence.get(4))) {
                        USER_NAME4 = user_name;
                        BALANCE4 = key.getString("balance");
                        nametext4.setText(user_name);
                        user_id4.setText(userid);

                    }

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            //LAST CHANCES DATA
            Intent intentService = new Intent(PrivateActivity.this, ServiceLastUserData.class);
            startService(intentService);
            DataHolder.setData(PrivateActivity.this,"CHECK_SERVICE",true);

            //BroadcastReceiver LAST DATA
            broadcastReceiver = new BroadcastReceiverDATA();
            IntentFilter intentFilter = new IntentFilter(DataHolder.ACTION_USER_LAST_DATA);
            intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
            registerReceiver(broadcastReceiver, intentFilter);

            new getCardDataAsyncTask().execute("http://213.136.81.137:8081/api/get_desk_cards?desk_id="+DeskId);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private boolean CHECK_TIME_OUT = false;
    ValueAnimator animator;
    private void simulateProgress() {
        progressBarChances.setVisibility(View.VISIBLE);
        animator = ValueAnimator.ofInt(0, 100);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int progress = (int) animation.getAnimatedValue();
                progressBarChances.setProgress(progress+1);
                Log.i("TAGTAGA","hi "+progress);
                if (CHECK_TIME_OUT){
                    animator.cancel();
                    animator.end();
                    CHECK_TIME_OUT = false;
                    progressBarChances.setVisibility(View.GONE);
                }

                if (99==progress){
                    animator.cancel();
                    packOperation();
                    progressBarChances.setVisibility(View.GONE);
                }

            }
        });
        Log.i("TAGTAGA1","hello");
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setDuration(15000);
        animator.start();
    }

    String Seen_Blind="blind",chance_Status="active",Show_Status="0",Next_User;

    public String chanceApi(String url,String mSeen_Blind,String mchance_Status,String mShow_Status,String mNext_User) {
        InputStream inputStream = null;
        String result = "";
        try {

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);

            String json = "";
            JSONObject jsonObject = new JSONObject();

            jsonObject.accumulate("deskid", DeskId);
            jsonObject.accumulate("userid", DataHolder.getDataString(PrivateActivity.this,"userid"));
            jsonObject.accumulate("chaalamount", ChaalAmount);//pev
            jsonObject.accumulate("chance_status", mchance_Status);
            jsonObject.accumulate("potvalue", txtVTableAmt.getText().toString());//pev
            jsonObject.accumulate("balance", txtVBalanceMainPlayer.getText().toString());
            jsonObject.accumulate("show", mShow_Status);//user count
            jsonObject.accumulate("seen_blind", mSeen_Blind);
            jsonObject.accumulate("next_user", mNext_User);
            jsonObject.accumulate("dealer_id", 1);
//            jsonObject.accumulate("round", "1");
//            jsonObject.accumulate("tip", "10");
//            jsonObject.accumulate("turn", TurnCount++);

            json = jsonObject.toString();
            StringEntity se = new StringEntity(json);
            se.setContentType("application/json");

            httpPost.setEntity(new StringEntity(json));
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            httpPost.setHeader("Authorization", DataHolder.getDataString(PrivateActivity.this,"token"));

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
            return chanceApi(urls[0],Seen_Blind,chance_Status,Show_Status,Next_User);
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(PrivateActivity.this, "" + result, Toast.LENGTH_SHORT).show();
            Log.i("Check123", "" + result);
            try {
                JSONObject jsonObjMain = new JSONObject(result.toString());

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class GetChanceAsyncTask extends AsyncTask<String, Void, String> {

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

                JSONArray arr = new JSONArray(jsonObjMain.getString("data"));

                int len = arr.length();

                for (int i=0;i<len;i++){
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

                    Log.i("CHANCESID",""+chanceid);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    String lastChanceid,lastDesk_id,lastChaal_amount,lastUser_id,lastChance_status,lastPot_value,lastBalance,
            lastShow,lastSeen_blind,lastDealer_id,lastTip,lastTurn,lastNext_user,lastWin_lose,lastDatetime;

    String storeNextValue="";
    private void getLastChanceData(String result){
        try {
            JSONObject jsonObjMain = new JSONObject(result);

            JSONArray arr = new JSONArray(jsonObjMain.getString("data"));

            int len = arr.length();

            for (int i=0;i<len;i++){
                JSONObject key = arr.getJSONObject(i);

                lastChanceid = key.getString("chanceid");
                lastDesk_id = key.getString("desk_id");
                lastChaal_amount = key.getString("chaal_amount");
                lastUser_id = key.getString("user_id");
                lastChance_status = key.getString("chance_status");
                lastPot_value = key.getString("pot_value");
                lastBalance = key.getString("balance");
                lastShow = key.getString("show");
                lastSeen_blind = key.getString("seen_blind");
                lastDealer_id = key.getString("dealer_id");
                lastTip = key.getString("tip");
                lastTurn = key.getString("turn");
                lastNext_user = key.getString("next_user");
                lastWin_lose = key.getString("win_lose");
                lastDatetime = key.getString("datetime");

//                    Toast.makeText(this, DataHolder.getDataString(this,"userid")+" "+lastUser_id, Toast.LENGTH_SHORT).show();
                Log.i("CHKIL",DataHolder.getDataString(this,"userid")+" "+lastNext_user+"  "+arrayListUserIdSequence.size());
                Log.i("CHKIL",storeNextValue+"-"+lastNext_user);
                if (!storeNextValue.equalsIgnoreCase(lastNext_user)) {
                    try {
                        viewBlinkCircle.clearAnimation();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                if (DataHolder.getDataString(this,"userid").equalsIgnoreCase(lastNext_user)){
                    Log.i("ChkilIN0","-"+lastNext_user);
                    ChaalAmount = Integer.parseInt(lastChaal_amount);
                    simulateProgress();
                    Log.i("CHKIL1",ChaalAmount+"");
                    rl_bottom_caption.setVisibility(View.VISIBLE);
                }else if (arrayListUserIdSequence.get(1).equalsIgnoreCase(lastNext_user)){
                    Log.i("ChkilIN1","-"+lastNext_user);
                    viewBlinkCircle = player_blink_circle1;
                    player_blink_circle1.startAnimation(animBlink);

                }else if (arrayListUserIdSequence.get(2).equalsIgnoreCase(lastNext_user)){
                    Log.i("ChkilIN2","-"+lastNext_user);
                    viewBlinkCircle = player_blink_circle2;
                    player_blink_circle2.startAnimation(animBlink);

                }else if (arrayListUserIdSequence.get(3).equalsIgnoreCase(lastNext_user)){
                    Log.i("ChkilIN3","-"+lastNext_user);
                    viewBlinkCircle = player_blink_circle3;
                    player_blink_circle3.startAnimation(animBlink);

                }else if (arrayListUserIdSequence.get(4).equalsIgnoreCase(lastNext_user)){
                    Log.i("ChkilIN4","-"+lastNext_user);
                    viewBlinkCircle = player_blink_circle4;
                    player_blink_circle4.startAnimation(animBlink);
                }

                Log.i("CHANCESID",""+lastChanceid);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        storeNextValue = lastNext_user;
    }

    public String updateUserStatusApi(String url) {
        InputStream inputStream = null;
        String result = "";
        try {

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);

            String json = "";
            JSONObject jsonObject = new JSONObject();

            jsonObject.accumulate("userid", DataHolder.getDataString(PrivateActivity.this,"userid"));
            jsonObject.accumulate("user_status", "offline");//pev

            json = jsonObject.toString();
            StringEntity se = new StringEntity(json);
            se.setContentType("application/json");

            httpPost.setEntity(new StringEntity(json));
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            httpPost.setHeader("Authorization", DataHolder.getDataString(PrivateActivity.this,"token"));

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
    private class updateUserStatusAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            return updateUserStatusApi(urls[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject jsonObjMain = new JSONObject(result.toString());

                String message = jsonObjMain.getString("message");
                if (message.equalsIgnoreCase("Client status successfully changed")){
                    Toast.makeText(PrivateActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    BroadcastReceiverDATA broadcastReceiver;
    public class BroadcastReceiverDATA extends BroadcastReceiver {
        @Override
        public void onReceive(Context context,final Intent intent) {
            String action = intent.getAction();

            if (action.equalsIgnoreCase(DataHolder.ACTION_USER_LAST_DATA)) {
                String result = intent.getStringExtra(DataHolder.KEY_USER_LAST_DATA);
                getLastChanceData(result);
                Log.i("TAG124 result",result);
            }
        }
    }


}