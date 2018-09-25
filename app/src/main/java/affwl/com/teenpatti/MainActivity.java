package affwl.com.teenpatti;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import affwl.com.teenpatti.DataHolder;

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


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView showPopupBtn, closeRateus, closeHelpBtn, closeTrophyBtn, profile, orangechipsbtn, close312help, closesixpattihelp, short321info, tourney_shortinfo_closebtn, shortsixpattiinfo, bluechipsbtn, cyanchipsbtn, shortinfo_tourney, tourney_join_closebtn, ygreenchipsbtn, closebtn_create_table, mainlimegchipsbtn, variation_closebtn, facebook, whatsapp, general;
    PopupWindow RateuspopupWindow, HelpUspopupWindow, TrophypopupWindow, tounpopupWindow, howto321popup, sixpattipopup, howtosixpattipopup, join_tourney_popupWindow, shortinfo_tourney_popupwindow, create_table_private_popupwindow, join_table_popupwindow;
    RelativeLayout RelativeLayoutloader, relativelayout321, relativeLayoutsixpatti, relativeLayout_tourney, yellowchiplayout, orangechipslayout, limechipslayout, darkbluechiplayout, blackchipslayout, cyanchipslayout, ygreenchipslayout;
    TextView loaderbuychips, joinnowbtn, howtoplay321btn, howtoplaysixpattibtn, joinnowsixpattibtn, join_tourneybtn, create_table_btn, join_variation_btn, txtVUserNameMain, code;
    Session session;
    LinearLayout jokerlayout_btn, jokerinfo_layout, ak47_layout_btn, ak47info_layout, xboot_layout_btn, xboot_info_layout,
            hukum_layout_btn, hukum_info_layout, muflis_layout_btn, muflis_info_layout, faceoff_layout_btn, faceoff_info_layout,
            ljoker_layout_btn, ljoker_info_layout, nnnine_layout_btn, nnnine_info_layout;

    ImageView joker_img, ak_img, xboot_img, hukum_img, muflis_img, faceoff_img, ljoker_img, nnnine_img;
    ImageView mainychips, mainlimegchips, blackchips;
    Animation animatechips1, animatechips2, animatechips3, animatechips4, animatechips5, animatechips6, animatechips7;
    Handler handler, handlerLoad;


    int value = 0;
    int value1 = 0;
    int value2 = 0;
    int value3 = 0;
    int value4 = 0;
    int value5 = 0;
    int value6 = 0;
    int value7 = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teenpatti_activity_main);


//        ListView listView = (ListView) findViewById(R.id.ll);
//        listView.setAdapter(adapter);

        profile = findViewById(R.id.profile);
        txtVUserNameMain = findViewById(R.id.txtVUserNameMain);

        final Animation Animchipsright = AnimationUtils.loadAnimation(MainActivity.this, R.anim.translate_chips_right);
        final Animation Animchipsleft = AnimationUtils.loadAnimation(MainActivity.this, R.anim.translate_chips_left);
//        final Animation Animttoleft = AnimationUtils.loadAnimation(MainActivity.this, R.anim.translate_tto_left);

//        mainychips = findViewById(R.id.mainychips);

//        mainlimegchips = findViewById(R.id.mainlimegchips);
//        mainlimegchips.setOnClickListener(this);

//        blackchips = findViewById(R.id.blackchips);
        RelativeLayoutloader = findViewById(R.id.linearLayoutloader);

        code = findViewById(R.id.code);
        session = new Session(this);
        String encodedimage = session.getImage();
        if (!encodedimage.equalsIgnoreCase("")) {
            byte[] b = Base64.decode(encodedimage, Base64.DEFAULT);
            Bitmap bmp = BitmapFactory.decodeByteArray(b, 0, b.length);
            profile.setImageBitmap(bmp);
        }
        String name = session.getName();
        txtVUserNameMain.setText(DataHolder.first_name+" "+DataHolder.last_name);


//        yellowchiplayout = findViewById(R.id.yellowchiplayout);
//        orangechipslayout = findViewById(R.id.orangechipslayout);
//        limechipslayout = findViewById(R.id.limechipslayout);
//        blackchipslayout = findViewById(R.id.blackchipslayout);
//        cyanchipslayout = findViewById(R.id.cyanchipslayout);
//        darkbluechiplayout = findViewById(R.id.darkbluechiplayout);
        ygreenchipslayout = findViewById(R.id.ygreenchipslayout);


        // Popup for Help
        showPopupBtn = findViewById(R.id.help_btn_loader);
        RelativeLayoutloader = findViewById(R.id.linearLayoutloader);

        showPopupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //instantiate the popup.xml three_two_one_leaderboard file
                LayoutInflater layoutInflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View customView = layoutInflater.inflate(R.layout.help_popup, null);

                closeHelpBtn = customView.findViewById(R.id.close_helpus);

                //instantiate popup window
                HelpUspopupWindow = new PopupWindow(customView, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

                //display the popup window
                HelpUspopupWindow.showAtLocation(RelativeLayoutloader, Gravity.TOP, 0, 0);

                //close the popup window on button click
                closeHelpBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HelpUspopupWindow.dismiss();
                    }
                });
            }
        });

        //////////////// teen patti ////////////////

        RelativeLayoutloader = findViewById(R.id.linearLayoutloader);
//        yellowchiplayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//
//                    @Override
//                    public void run() {
//                        Intent i = new Intent(MainActivity.this, LoadingScreen_teenpatti.class);
//                        startActivity(i);
//                        yellowchiplayout.clearAnimation();
//                        //yellowchiplayout.startAnimation(Animchipsleft);
//                        orangechipslayout.startAnimation(Animchipsright);
//                        limechipslayout.startAnimation(Animchipsright);
//                        darkbluechiplayout.startAnimation(Animchipsright);
//                        ygreenchipslayout.startAnimation(Animchipsright);
//                        blackchipslayout.startAnimation(Animchipsright);
//                        ygreenchipslayout.startAnimation(Animchipsright);
//                    }
//                }, 500);
//            }
//        });


        //////////////// Popup for 321 tournament ////////////////

//        orangechipsbtn = findViewById(R.id.mainorgchips);
        RelativeLayoutloader = findViewById(R.id.linearLayoutloader);
//        orangechipslayout = findViewById(R.id.orangechipslayout);

//        orangechipslayout.setOnClickListener(new View.OnClickListener() {
//
//            public void onClick(View v) {
//
//                //Delays the click event by 5 seconds
//                Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        Intent i = new Intent(MainActivity.this, ThreetwooneTournament.class);
//                        startActivity(i);
//                        orangechipslayout.clearAnimation();
//                        orangechipslayout.startAnimation(Animchipsleft);
//                        limechipslayout.startAnimation(Animchipsleft);
//                    }
//                }, 500);
//
//            }
//        });

        //////////////// Popup for six patti ////////////////
//        bluechipsbtn = findViewById(R.id.darkbluechips);
        RelativeLayoutloader = findViewById(R.id.linearLayoutloader);

//        darkbluechiplayout.setOnClickListener(new View.OnClickListener() {
//
//            public void onClick(View v) {
//
//                //Delays the click event by 5 seconds
//                Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        Intent i = new Intent(MainActivity.this, LoadingScreen_tourney.class);
//                        startActivity(i);
//                        finish();
//                    }
//                }, 3000);
//
//            }
//        });


//        bluechipsbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, LoadingScreen_sixpatti.class);
//                startActivity(intent);
//                finish();
//            }
//        });

        //////////////// Popup for Tourney ////////////////
//        cyanchipsbtn = findViewById(R.id.cyanchips);
        RelativeLayoutloader = findViewById(R.id.linearLayoutloader);

//        cyanchipslayout.setOnClickListener(new View.OnClickListener() {
//
//            public void onClick(View v) {
//
//                //Delays the click event by  milliseconds
//                Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        Intent i = new Intent(MainActivity.this, LoadingScreen_tourney.class);
//                        startActivity(i);
//                        finish();
//                    }
//                }, 3000);
//
//            }
//        });


//        cyanchipsbtn.setOnClickListener(new View.OnClickListener() {
//            @SuppressLint("WrongViewCast")
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(MainActivity.this, LoadingScreen_tourney.class);
//                startActivity(intent);
//                finish();
//            }
//        });


        //////////////// Popup for Tourney2 ////////////////
//        cyanchipsbtn = findViewById(R.id.cyanchips);
//        RelativeLayoutloader = findViewById(R.id.linearLayoutloader);
//        relativeLayout_tourney = findViewById(R.id.relativelayout_tourney);
//
//
//        cyanchipsbtn.setOnClickListener(new View.OnClickListener() {
//            @SuppressLint("WrongViewCast")
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(MainActivity.this, LoadingScreen_tourney.class);
//                startActivity(intent);
//                finish();
//
//            }
//        });


        //////////////// Popup for Private ////////////////
        ygreenchipsbtn = findViewById(R.id.ygreenchips);
        RelativeLayoutloader = findViewById(R.id.linearLayoutloader);

        ygreenchipslayout.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                //Delays the click event by 5 seconds
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent i = new Intent(MainActivity.this, LoadingScreen_private.class);
                        startActivity(i);
                        finish();
                    }
                }, 1000);

            }
        });


//        ygreenchipsbtn.setOnClickListener(new View.OnClickListener() {
//            @SuppressLint("WrongViewCast")
//            @Override
//            public void onClick(View v) {
//
//
//                Intent intent = new Intent(MainActivity.this, LoadingScreen_private.class);
//                startActivity(intent);
//            }
//        });


        //////////////// Popup for Variation ////////////////
//        mainlimegchipsbtn = findViewById(R.id.mainlimegchips);
        RelativeLayoutloader = findViewById(R.id.linearLayoutloader);

//        mainlimegchipsbtn.setOnClickListener(new View.OnClickListener() {
//            @SuppressLint("WrongViewCast")
//            @Override
//            public void onClick(View v) {
//                //instantiate the popup.xml layout file
//                LayoutInflater layoutInflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                View customView = layoutInflater.inflate(R.layout.variation_join_table, null);
//
//                variation_closebtn = customView.findViewById(R.id.close_var_popup);
//
//                jokerlayout_btn = customView.findViewById(R.id.joker_layout);
//                jokerinfo_layout = customView.findViewById(R.id.jokerinfo);
//                joker_img = customView.findViewById(R.id.joker_img);
//
//                ak47_layout_btn = customView.findViewById(R.id.ak47_layout);
//                ak47info_layout = customView.findViewById(R.id.ak47info);
//                ak_img = customView.findViewById(R.id.ak_img);
//
//                xboot_layout_btn = customView.findViewById(R.id.xboot_layout);
//                xboot_info_layout = customView.findViewById(R.id.xboot_info);
//                xboot_img = customView.findViewById(R.id.xboot_img);
//
//                hukum_layout_btn = customView.findViewById(R.id.hukum_layout);
//                hukum_info_layout = customView.findViewById(R.id.hukum_info);
//                hukum_img = customView.findViewById(R.id.hukum_img);
//
//                muflis_layout_btn = customView.findViewById(R.id.muflis_layout);
//                muflis_info_layout = customView.findViewById(R.id.muflis_info);
//                muflis_img = customView.findViewById(R.id.muflis_img);
//
//                faceoff_layout_btn = customView.findViewById(R.id.faceoff_layout);
//                faceoff_info_layout = customView.findViewById(R.id.faceoff_info);
//                faceoff_img = customView.findViewById(R.id.faceoff_img);
//
//                ljoker_layout_btn = customView.findViewById(R.id.ljoker_layout);
//                ljoker_info_layout = customView.findViewById(R.id.ljoker_info);
//                ljoker_img = customView.findViewById(R.id.ljoker_img);
//
//                nnnine_layout_btn = customView.findViewById(R.id.nnnine_layout);
//                nnnine_info_layout = customView.findViewById(R.id.nnnine_info);
//                nnnine_img = customView.findViewById(R.id.nnnine_img);
//
//
//                final Animation Animleft = AnimationUtils.loadAnimation(MainActivity.this, R.anim.left_translate);
//                final Animation Animright = AnimationUtils.loadAnimation(MainActivity.this, R.anim.right_translate);
//
//
//                //instantiate popup window
//                join_table_popupwindow = new PopupWindow(customView, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
//
//                //display the popup window
//                join_table_popupwindow.showAtLocation(RelativeLayoutloader, Gravity.CENTER, 0, 0);
//
//                variation_closebtn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        join_table_popupwindow.dismiss();
//                    }
//                });
//
//
//                // joker variation on click
//
//                jokerlayout_btn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (value == 0) {
//                            ak47info_layout.clearAnimation();
//                            hukum_info_layout.clearAnimation();
//                            xboot_info_layout.clearAnimation();
//
//                            hukum_img.setImageDrawable(getResources().getDrawable(R.drawable.q));
//                            ak_img.setImageDrawable(getResources().getDrawable(R.drawable.q));
//                            xboot_img.setImageDrawable(getResources().getDrawable(R.drawable.q));
//
////                           ak_img.setVisibility(View.VISIBLE);
//                            jokerinfo_layout.setVisibility(View.VISIBLE);
//                            joker_img.setImageDrawable(getResources().getDrawable(R.drawable.circle_arrow));
//                            Toast.makeText(MainActivity.this, "joker out", Toast.LENGTH_SHORT).show();
//                            jokerinfo_layout.startAnimation(Animleft);
//                            Animleft.setFillAfter(true);
//                            value = 1;
//                            value1 = 0;
//                            value2 = 0;
//                            value3 = 0;
//                            value4 = 0;
//                            value5 = 0;
//                            value6 = 0;
//                            value7 = 0;
//
//                            return;
//                        } else if (value == 1) {
//                            ak47info_layout.clearAnimation();
//                            jokerinfo_layout.clearAnimation();
//                            xboot_info_layout.clearAnimation();
//                            hukum_info_layout.clearAnimation();
//
//                            jokerinfo_layout.setVisibility(View.INVISIBLE);
//                            ak47info_layout.setVisibility(View.INVISIBLE);
//                            hukum_info_layout.setVisibility(View.INVISIBLE);
//                            xboot_info_layout.setVisibility(View.INVISIBLE);
//
//                            joker_img.setImageDrawable(getResources().getDrawable(R.drawable.q));
//                            Toast.makeText(MainActivity.this, "joker in", Toast.LENGTH_SHORT).show();
//
//                            jokerinfo_layout.startAnimation(Animright);
//                            Animright.setFillAfter(true);
//                            value = 0;
//                            return;
//                        }
//                    }
//                });
//
//                // AK47 variation on click
//
//                ak47_layout_btn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (value1 == 0) {
//
//                            jokerinfo_layout.clearAnimation();
//                            ak47info_layout.clearAnimation();
//                            xboot_info_layout.clearAnimation();
//                            hukum_info_layout.clearAnimation();
//
//                            joker_img.setImageDrawable(getResources().getDrawable(R.drawable.q));
//                            xboot_img.setImageDrawable(getResources().getDrawable(R.drawable.q));
//                            hukum_img.setImageDrawable(getResources().getDrawable(R.drawable.q));
//
////                            xboot_info_layout.clearAnimation();
////                            xboot_img.setImageDrawable(getResources().getDrawable(R.drawable.q));
//
//                            ak47info_layout.setVisibility(View.VISIBLE);
//                            jokerinfo_layout.setVisibility(View.INVISIBLE);
//                            xboot_info_layout.setVisibility(View.INVISIBLE);
//                            hukum_info_layout.setVisibility(View.INVISIBLE);
//
//                            Toast.makeText(MainActivity.this, "ak out", Toast.LENGTH_SHORT).show();
//
//                            ak_img.setImageDrawable(getResources().getDrawable(R.drawable.circle_arrow));
//                            ak47info_layout.startAnimation(Animleft);
//                            Animleft.setFillAfter(true);
//                            value1 = 1;
//                            value2 = 0;
//                            value3 = 0;
//                            value = 0;
//                            value4 = 0;
//                            value5 = 0;
//                            value6 = 0;
//                            value7 = 0;
//
//                            return;
//                        } else if (value1 == 1) {
//                            jokerinfo_layout.clearAnimation();
//                            ak47info_layout.clearAnimation();
//                            xboot_info_layout.clearAnimation();
//                            hukum_info_layout.clearAnimation();
//
//                            ak47info_layout.setVisibility(View.INVISIBLE);
//                            jokerinfo_layout.setVisibility(View.INVISIBLE);
//                            xboot_info_layout.setVisibility(View.INVISIBLE);
//                            hukum_info_layout.setVisibility(View.INVISIBLE);
//
//                            Toast.makeText(MainActivity.this, "ak in", Toast.LENGTH_SHORT).show();
//
//                            ak_img.setImageDrawable(getResources().getDrawable(R.drawable.q));
//
//                            ak47info_layout.startAnimation(Animright);
//                            Animright.setFillAfter(true);
//                            value1 = 0;
//                            return;
//                        }
//                    }
//                });
//
//
//                // 4X boot variation on click
//
//                xboot_layout_btn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (value2 == 0) {
//
//                            jokerinfo_layout.clearAnimation();
//                            ak47info_layout.clearAnimation();
//                            hukum_info_layout.clearAnimation();
//
//                            joker_img.setImageDrawable(getResources().getDrawable(R.drawable.q));
//                            ak_img.setImageDrawable(getResources().getDrawable(R.drawable.q));
//                            hukum_img.setImageDrawable(getResources().getDrawable(R.drawable.q));
//
//                            xboot_info_layout.setVisibility(View.VISIBLE);
//                            ak47info_layout.setVisibility(View.INVISIBLE);
//                            jokerinfo_layout.setVisibility(View.INVISIBLE);
//                            hukum_info_layout.setVisibility(View.INVISIBLE);
//
//                            Toast.makeText(MainActivity.this, "xboot out", Toast.LENGTH_SHORT).show();
//
//                            xboot_img.setImageDrawable(getResources().getDrawable(R.drawable.circle_arrow));
//
//                            xboot_info_layout.startAnimation(Animleft);
//                            Animleft.setFillAfter(true);
//                            value2 = 1;
//                            value1 = 0;
//                            value = 0;
//                            value3 = 0;
//                            value4 = 0;
//                            value5 = 0;
//                            value6 = 0;
//                            value7 = 0;
//
//                            return;
//                        }
//                        if (value2 == 1) {
//                            jokerinfo_layout.clearAnimation();
//                            ak47info_layout.clearAnimation();
//                            xboot_info_layout.clearAnimation();
//                            hukum_info_layout.clearAnimation();
//
//                            xboot_info_layout.setVisibility(View.INVISIBLE);
//                            ak47info_layout.setVisibility(View.INVISIBLE);
//                            jokerinfo_layout.setVisibility(View.INVISIBLE);
//                            hukum_info_layout.setVisibility(View.INVISIBLE);
//
//                            Toast.makeText(MainActivity.this, "xboot in", Toast.LENGTH_SHORT).show();
//
//                            xboot_img.setImageDrawable(getResources().getDrawable(R.drawable.q));
//
//                            xboot_info_layout.startAnimation(Animright);
//                            value2 = 0;
//                            Animright.setFillAfter(true);
//                            return;
//                        }
//                    }
//                });
//
//                // hukum variation on click
//
//                hukum_layout_btn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (value3 == 0) {
//
//                            jokerinfo_layout.clearAnimation();
//                            ak47info_layout.clearAnimation();
//                            xboot_info_layout.clearAnimation();
//
//                            joker_img.setImageDrawable(getResources().getDrawable(R.drawable.q));
//                            ak_img.setImageDrawable(getResources().getDrawable(R.drawable.q));
//                            xboot_img.setImageDrawable(getResources().getDrawable(R.drawable.q));
//
//                            xboot_info_layout.setVisibility(View.INVISIBLE);
//                            ak47info_layout.setVisibility(View.INVISIBLE);
//                            jokerinfo_layout.setVisibility(View.INVISIBLE);
//                            hukum_info_layout.setVisibility(View.VISIBLE);
//
//                            Toast.makeText(MainActivity.this, "hukum out", Toast.LENGTH_SHORT).show();
//
//                            hukum_img.setImageDrawable(getResources().getDrawable(R.drawable.circle_arrow));
//
//                            hukum_info_layout.startAnimation(Animleft);
//                            Animleft.setFillAfter(true);
//                            value3 = 1;
//                            value1 = 0;
//                            value = 0;
//                            value2 = 0;
//                            value4 = 0;
//                            value5 = 0;
//                            value6 = 0;
//                            value7 = 0;
//
//                            return;
//                        }
//                        if (value3 == 1) {
//                            jokerinfo_layout.clearAnimation();
//                            ak47info_layout.clearAnimation();
//                            xboot_info_layout.clearAnimation();
//                            hukum_info_layout.clearAnimation();
//
//                            xboot_info_layout.setVisibility(View.INVISIBLE);
//                            hukum_info_layout.setVisibility(View.INVISIBLE);
//                            ak47info_layout.setVisibility(View.INVISIBLE);
//                            jokerinfo_layout.setVisibility(View.INVISIBLE);
//
//                            Toast.makeText(MainActivity.this, "hukum in", Toast.LENGTH_SHORT).show();
//
//                            hukum_img.setImageDrawable(getResources().getDrawable(R.drawable.q));
//
//                            hukum_info_layout.startAnimation(Animright);
//                            value3 = 0;
//                            Animright.setFillAfter(true);
//                            return;
//                        }
//                    }
//                });
//
//
//                // muflis variation on click
//
//                muflis_layout_btn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (value4 == 0) {
//
//                            jokerinfo_layout.clearAnimation();
//                            ak47info_layout.clearAnimation();
//                            xboot_info_layout.clearAnimation();
//                            hukum_info_layout.clearAnimation();
//
//                            joker_img.setImageDrawable(getResources().getDrawable(R.drawable.q));
//                            ak_img.setImageDrawable(getResources().getDrawable(R.drawable.q));
//                            xboot_img.setImageDrawable(getResources().getDrawable(R.drawable.q));
//                            hukum_img.setImageDrawable(getResources().getDrawable(R.drawable.q));
//
//                            xboot_info_layout.setVisibility(View.INVISIBLE);
//                            ak47info_layout.setVisibility(View.INVISIBLE);
//                            jokerinfo_layout.setVisibility(View.INVISIBLE);
//                            hukum_info_layout.setVisibility(View.INVISIBLE);
//                            muflis_info_layout.setVisibility(View.VISIBLE);
//
//                            Toast.makeText(MainActivity.this, "muflis out", Toast.LENGTH_SHORT).show();
//
//                            muflis_img.setImageDrawable(getResources().getDrawable(R.drawable.circle_arrow));
//
//                            muflis_info_layout.startAnimation(Animleft);
//                            Animleft.setFillAfter(true);
//                            value4 = 1;
//                            value1 = 0;
//                            value = 0;
//                            value2 = 0;
//                            value3 = 0;
//                            value5 = 0;
//                            value6 = 0;
//                            value7 = 0;
//
//                            return;
//                        }
//                        if (value4 == 1) {
//                            jokerinfo_layout.clearAnimation();
//                            ak47info_layout.clearAnimation();
//                            xboot_info_layout.clearAnimation();
//                            hukum_info_layout.clearAnimation();
//                            muflis_info_layout.clearAnimation();
//
//                            xboot_info_layout.setVisibility(View.INVISIBLE);
//                            hukum_info_layout.setVisibility(View.INVISIBLE);
//                            ak47info_layout.setVisibility(View.INVISIBLE);
//                            jokerinfo_layout.setVisibility(View.INVISIBLE);
//                            muflis_info_layout.setVisibility(View.INVISIBLE);
//
//                            Toast.makeText(MainActivity.this, "muflis in", Toast.LENGTH_SHORT).show();
//
//                            muflis_img.setImageDrawable(getResources().getDrawable(R.drawable.q));
//
//                            muflis_info_layout.startAnimation(Animright);
//                            value4 = 0;
//                            Animright.setFillAfter(true);
//                            return;
//                        }
//                    }
//                });
//
//
//                //  faceoff variation on click
//
//                faceoff_layout_btn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (value5 == 0) {
//
//                            jokerinfo_layout.clearAnimation();
//                            ak47info_layout.clearAnimation();
//                            xboot_info_layout.clearAnimation();
//                            hukum_info_layout.clearAnimation();
//                            muflis_info_layout.clearAnimation();
//
//                            joker_img.setImageDrawable(getResources().getDrawable(R.drawable.q));
//                            ak_img.setImageDrawable(getResources().getDrawable(R.drawable.q));
//                            xboot_img.setImageDrawable(getResources().getDrawable(R.drawable.q));
//                            hukum_img.setImageDrawable(getResources().getDrawable(R.drawable.q));
//                            muflis_img.setImageDrawable(getResources().getDrawable(R.drawable.q));
//
//                            xboot_info_layout.setVisibility(View.INVISIBLE);
//                            ak47info_layout.setVisibility(View.INVISIBLE);
//                            jokerinfo_layout.setVisibility(View.INVISIBLE);
//                            hukum_info_layout.setVisibility(View.INVISIBLE);
//                            muflis_info_layout.setVisibility(View.INVISIBLE);
//                            faceoff_info_layout.setVisibility(View.VISIBLE);
//
//                            Toast.makeText(MainActivity.this, "faceoff out", Toast.LENGTH_SHORT).show();
//
//                            faceoff_img.setImageDrawable(getResources().getDrawable(R.drawable.circle_arrow));
//
//                            faceoff_info_layout.startAnimation(Animleft);
//                            Animleft.setFillAfter(true);
//                            value5 = 1;
//                            value1 = 0;
//                            value = 0;
//                            value2 = 0;
//                            value3 = 0;
//                            value4 = 0;
//                            value6 = 0;
//                            value7 = 0;
//
//                            return;
//                        }
//                        if (value5 == 1) {
//                            jokerinfo_layout.clearAnimation();
//                            ak47info_layout.clearAnimation();
//                            xboot_info_layout.clearAnimation();
//                            hukum_info_layout.clearAnimation();
//                            muflis_info_layout.clearAnimation();
//                            faceoff_info_layout.clearAnimation();
//
//                            xboot_info_layout.setVisibility(View.INVISIBLE);
//                            hukum_info_layout.setVisibility(View.INVISIBLE);
//                            ak47info_layout.setVisibility(View.INVISIBLE);
//                            jokerinfo_layout.setVisibility(View.INVISIBLE);
//                            muflis_info_layout.setVisibility(View.INVISIBLE);
//                            faceoff_info_layout.setVisibility(View.INVISIBLE);
//
//                            Toast.makeText(MainActivity.this, "faceoff in", Toast.LENGTH_SHORT).show();
//
//                            faceoff_img.setImageDrawable(getResources().getDrawable(R.drawable.q));
//
//                            faceoff_info_layout.startAnimation(Animright);
//                            value5 = 0;
//                            Animright.setFillAfter(true);
//                            return;
//                        }
//                    }
//                });
//
//
//                //  ljoker variation on click
//
//                ljoker_layout_btn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (value6 == 0) {
//
//                            jokerinfo_layout.clearAnimation();
//                            ak47info_layout.clearAnimation();
//                            xboot_info_layout.clearAnimation();
//                            hukum_info_layout.clearAnimation();
//                            muflis_info_layout.clearAnimation();
//                            faceoff_info_layout.clearAnimation();
//
//
//                            joker_img.setImageDrawable(getResources().getDrawable(R.drawable.q));
//                            ak_img.setImageDrawable(getResources().getDrawable(R.drawable.q));
//                            xboot_img.setImageDrawable(getResources().getDrawable(R.drawable.q));
//                            hukum_img.setImageDrawable(getResources().getDrawable(R.drawable.q));
//                            muflis_img.setImageDrawable(getResources().getDrawable(R.drawable.q));
//                            faceoff_img.setImageDrawable(getResources().getDrawable(R.drawable.q));
//                            nnnine_img.setImageDrawable(getResources().getDrawable(R.drawable.q));
//
//                            xboot_info_layout.setVisibility(View.INVISIBLE);
//                            ak47info_layout.setVisibility(View.INVISIBLE);
//                            jokerinfo_layout.setVisibility(View.INVISIBLE);
//                            hukum_info_layout.setVisibility(View.INVISIBLE);
//                            muflis_info_layout.setVisibility(View.INVISIBLE);
//                            faceoff_info_layout.setVisibility(View.INVISIBLE);
//                            ljoker_info_layout.setVisibility(View.VISIBLE);
//
//                            Toast.makeText(MainActivity.this, "ljoker out", Toast.LENGTH_SHORT).show();
//
//                            ljoker_img.setImageDrawable(getResources().getDrawable(R.drawable.circle_arrow));
//
//                            ljoker_info_layout.startAnimation(Animleft);
//                            Animleft.setFillAfter(true);
//                            value6 = 1;
//                            value1 = 0;
//                            value = 0;
//                            value2 = 0;
//                            value3 = 0;
//                            value4 = 0;
//                            value5 = 0;
//                            value7 = 0;
//
//                            return;
//                        }
//                        if (value6 == 1) {
//                            jokerinfo_layout.clearAnimation();
//                            ak47info_layout.clearAnimation();
//                            xboot_info_layout.clearAnimation();
//                            hukum_info_layout.clearAnimation();
//                            muflis_info_layout.clearAnimation();
//                            faceoff_info_layout.clearAnimation();
//                            ljoker_info_layout.clearAnimation();
//
//                            xboot_info_layout.setVisibility(View.INVISIBLE);
//                            hukum_info_layout.setVisibility(View.INVISIBLE);
//                            ak47info_layout.setVisibility(View.INVISIBLE);
//                            jokerinfo_layout.setVisibility(View.INVISIBLE);
//                            muflis_info_layout.setVisibility(View.INVISIBLE);
//                            faceoff_info_layout.setVisibility(View.INVISIBLE);
//                            ljoker_info_layout.setVisibility(View.INVISIBLE);
//
//                            Toast.makeText(MainActivity.this, "ljoker in", Toast.LENGTH_SHORT).show();
//
//                            ljoker_img.setImageDrawable(getResources().getDrawable(R.drawable.q));
//
//                            ljoker_info_layout.startAnimation(Animright);
//                            value6 = 0;
//                            Animright.setFillAfter(true);
//                            return;
//                        }
//                    }
//                });
//
//
//                //  nnnine variation on click
//
//                nnnine_layout_btn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (value7 == 0) {
//
//                            jokerinfo_layout.clearAnimation();
//                            ak47info_layout.clearAnimation();
//                            xboot_info_layout.clearAnimation();
//                            hukum_info_layout.clearAnimation();
//                            muflis_info_layout.clearAnimation();
//                            faceoff_info_layout.clearAnimation();
//                            ljoker_info_layout.clearAnimation();
//
//                            joker_img.setImageDrawable(getResources().getDrawable(R.drawable.q));
//                            ak_img.setImageDrawable(getResources().getDrawable(R.drawable.q));
//                            xboot_img.setImageDrawable(getResources().getDrawable(R.drawable.q));
//                            hukum_img.setImageDrawable(getResources().getDrawable(R.drawable.q));
//                            muflis_img.setImageDrawable(getResources().getDrawable(R.drawable.q));
//                            faceoff_img.setImageDrawable(getResources().getDrawable(R.drawable.q));
//                            ljoker_img.setImageDrawable(getResources().getDrawable(R.drawable.q));
//
//                            xboot_info_layout.setVisibility(View.INVISIBLE);
//                            ak47info_layout.setVisibility(View.INVISIBLE);
//                            jokerinfo_layout.setVisibility(View.INVISIBLE);
//                            hukum_info_layout.setVisibility(View.INVISIBLE);
//                            muflis_info_layout.setVisibility(View.INVISIBLE);
//                            faceoff_info_layout.setVisibility(View.INVISIBLE);
//                            ljoker_info_layout.setVisibility(View.INVISIBLE);
//                            nnnine_info_layout.setVisibility(View.VISIBLE);
//
//
//                            Toast.makeText(MainActivity.this, "nnnine out", Toast.LENGTH_SHORT).show();
//
//                            nnnine_img.setImageDrawable(getResources().getDrawable(R.drawable.circle_arrow));
//
//                            nnnine_info_layout.startAnimation(Animleft);
//                            Animleft.setFillAfter(true);
//                            value7 = 1;
//                            value1 = 0;
//                            value = 0;
//                            value2 = 0;
//                            value3 = 0;
//                            value4 = 0;
//                            value5 = 0;
//                            value6 = 0;
//
//                            return;
//                        }
//                        if (value7 == 1) {
//                            jokerinfo_layout.clearAnimation();
//                            ak47info_layout.clearAnimation();
//                            xboot_info_layout.clearAnimation();
//                            hukum_info_layout.clearAnimation();
//                            muflis_info_layout.clearAnimation();
//                            faceoff_info_layout.clearAnimation();
//                            ljoker_info_layout.clearAnimation();
//                            nnnine_info_layout.clearAnimation();
//
//                            xboot_info_layout.setVisibility(View.INVISIBLE);
//                            hukum_info_layout.setVisibility(View.INVISIBLE);
//                            ak47info_layout.setVisibility(View.INVISIBLE);
//                            jokerinfo_layout.setVisibility(View.INVISIBLE);
//                            muflis_info_layout.setVisibility(View.INVISIBLE);
//                            faceoff_info_layout.setVisibility(View.INVISIBLE);
//                            ljoker_info_layout.setVisibility(View.INVISIBLE);
//                            nnnine_info_layout.setVisibility(View.INVISIBLE);
//
//                            Toast.makeText(MainActivity.this, "nnnine in", Toast.LENGTH_SHORT).show();
//
//                            nnnine_img.setImageDrawable(getResources().getDrawable(R.drawable.q));
//
//                            nnnine_info_layout.startAnimation(Animright);
//                            value7 = 0;
//                            Animright.setFillAfter(true);
//                            return;
//                        }
//                    }
//                });
//
//                //join now the popup window on button click
//                join_variation_btn = customView.findViewById(R.id.variation_jointble);
//
//                join_variation_btn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent intent = new Intent(MainActivity.this, LoadingScreen_variation.class);
//                        startActivity(intent);
//                    }
//                });
//            }
//        });


        // Animation of chips on main page

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.mainactivity_chips_rotate);
//        findViewById(R.id.mainychips).startAnimation(animation);
//        findViewById(R.id.mainlimegchips).startAnimation(animation);
//        findViewById(R.id.mainorgchips).startAnimation(animation);
//        findViewById(R.id.darkbluechips).startAnimation(animation);
//        findViewById(R.id.cyanchips).startAnimation(animation);
//        findViewById(R.id.blackchips).startAnimation(animation);
        findViewById(R.id.ygreenchips).startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        Animation antianimation = AnimationUtils.loadAnimation(this, R.anim.mainactivity_chips_rotate_anticlockwise);
//        findViewById(R.id.innerlime).startAnimation(antianimation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        handler = new Handler();
        //DataHolder.showProgress(getApplicationContext());
        handlerLoad = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                //new getUserDataAsyncTask().execute("http://213.136.81.137:8080/api/adminData");
            }
        });
        //txtVUserNameMain.setText(DataHolder.getSTACK(MainActivity.this, "username"));
    }

    public String setUserApi(String url) {
        InputStream inputStream = null;
        String result = "";
        try {

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost Httppost = new HttpPost(url);

            String json = "";
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("username", txtVUserNameMain);

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
                result = "Does Not Work";
            Log.e("Check", "HOW" + result);
        } catch (Exception e) {
            Log.d("InputStream", "" + e);
        }
        Log.d("Check", result + "");
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

    private class setUserDataAsyncTask extends AsyncTask<String, Void , String> {


        @Override
        protected String doInBackground(String... urls) {
            return setUserApi(urls[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            Log.i("Check", "" + result);
            try{
                JSONObject jsonObjMain = new JSONObject(result.toString());
                String message = jsonObjMain.getString("message");
                if (message.equalsIgnoreCase("successfully authenticated")) {
                    DataHolder.setSTACK(MainActivity.this, "username", txtVUserNameMain.getText().toString());
                }
//                Toast.makeText(getApplicationContext(), ""+message, Toast.LENGTH_SHORT).show();
                Log.i("result", "Status" + message);
            }catch (JSONException e){
                e.printStackTrace();
                DataHolder.unAuthorized(MainActivity.this, result);
            }
            DataHolder.cancelProgress();
        }
    }

    public String getUserApi(String url){
        InputStream inputStream = null;
        String result = "";
        try {

            HttpClient httpclient = new DefaultHttpClient();
            HttpGet Httpget = new HttpGet(url);

            Httpget.setHeader("Accept", "application/json");
            Httpget.setHeader("Content-type", "application/json");

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

    private class getUserDataAsyncTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... urls) {
            return getUserApi(urls[0]);
        }

        @Override
        protected void onPostExecute(String result) {
//            Toast.makeText(MainActivity.this, "" + result, Toast.LENGTH_SHORT).show();
            Log.i("Check", "" + result);
            try {
                JSONObject jsonObjMain = new JSONObject(result.toString());
                String message = jsonObjMain.getString("message");
                Log.i("TAG", "" + message);

                JSONArray arr = new JSONArray(jsonObjMain.getString("data"));

                int len = arr.length();

                for (int i = 0; i < len; i++) {

                    JSONObject key = arr.getJSONObject(i);

                    if (i == 0) {
//                        nametext1.setText(key.getString("username"));
                    } else if (i == 1) {
//                        nametext2.setText(key.getString("username"));
                    } else if (i == 2) {
                        //txtVUserNameMain.setText(key.getString("username"));
                    } else if (i == 3) {
//                        nametext3.setText(key.getString("username"));
                    } else if (i == 4) {
//                        nametext4.setText(key.getString("username"));
                    }
                }
//                JSONObject jsonObjMain = new JSONObject(result.toString());
//                String message = jsonObjMain.getString("message");
//                Log.i("TAG", "" + message);
//
//
//                JSONObject jsonObj = new JSONObject(jsonObjMain.getString("data"));
//
//                txtVUserNameMain.setText(jsonObj.getString("username"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }





    //////////// Onclick method for teenpatti table /////////////
    @Override
    public void onBackPressed() {
        displayExitAlert("Alert","Do you want to Exit?");
    }

    private void displayExitAlert(String title, String message) {

        TextView tv_alert_ok,tv_alert_title,tv_alert_message,tv_alert_cancel;
        ImageView alert_box_close;

        final Dialog myAlertDialog = new Dialog(this);
        myAlertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        myAlertDialog.setCanceledOnTouchOutside(false);
        myAlertDialog.setContentView(R.layout.alert_box);

        tv_alert_ok = myAlertDialog.findViewById(R.id.tv_alert_ok);
        tv_alert_cancel=myAlertDialog.findViewById(R.id.tv_alert_cancel);
        alert_box_close=myAlertDialog.findViewById(R.id.alert_box_close);
        tv_alert_title=myAlertDialog.findViewById(R.id.tv_alert_title);
        tv_alert_message=myAlertDialog.findViewById(R.id.tv_alert_message);

        tv_alert_title.setText(title);
        tv_alert_message.setText(message);
        tv_alert_ok.setText("Yes");
        tv_alert_cancel.setText("No");

        alert_box_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myAlertDialog.dismiss();
            }
        });

        tv_alert_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
            }
        });

        tv_alert_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myAlertDialog.dismiss();
            }
        });
        myAlertDialog.show();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }
}

