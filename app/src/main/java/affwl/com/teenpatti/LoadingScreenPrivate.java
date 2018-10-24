package affwl.com.teenpatti;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;

public class LoadingScreenPrivate extends AppCompatActivity {

    DataHolder dataHolder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen_private);
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        dataHolder = new DataHolder();
        dataHolder.setContext(LoadingScreenPrivate.this);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.splash2);
        findViewById(R.id.loadingouter).startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                new DataHolder.updateUserStatusAsyncTask().execute("http://213.136.81.137:8081/api/update_client_status", "online");
                new GameRequestAsyncTask().execute("http://213.136.81.137:8081/api/gameRequest");
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
            }

            @Override
            public void onAnimationRepeat(Animation myAnimation) {

            }
        });
    }

    public String gameRequestApi(String url) {
        InputStream inputStream = null;
        String result = "";
        try {

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);

            String json = "";
            JSONObject jsonObject = new JSONObject();

            Log.i("KARAN", DataHolder.getDataInt(LoadingScreenPrivate.this, "deskid") + "\n" + DataHolder.getDataString(LoadingScreenPrivate.this, "userid"));
            jsonObject.accumulate("desk_id", DataHolder.getDataInt(LoadingScreenPrivate.this, "deskid"));
            jsonObject.accumulate("userid", DataHolder.getDataString(LoadingScreenPrivate.this, "userid"));
            jsonObject.accumulate("request_next", "next");//pev

            json = jsonObject.toString();
            StringEntity se = new StringEntity(json);
            se.setContentType("application/json");

            httpPost.setEntity(new StringEntity(json));
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            httpPost.setHeader("Authorization", DataHolder.getDataString(LoadingScreenPrivate.this, "token"));

            HttpResponse httpResponse = httpclient.execute(httpPost);
            inputStream = httpResponse.getEntity().getContent();

            if (inputStream != null) {
                try {
                    result = DataHolder.convertInputStreamToString(inputStream);
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
    private class GameRequestAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            return gameRequestApi(urls[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            Log.i("CheckGamex", "" + result);

            try {
                JSONObject jsonObjMain = new JSONObject(result.toString());
                String msg = jsonObjMain.getString("message");
                if (msg.equalsIgnoreCase("Cards generated sucessfully") || msg.equalsIgnoreCase("Game request successfully registered. Wait for second user")) {
                    startActivity(new Intent(LoadingScreenPrivate.this, PrivateActivity.class));
                }else {
                    Toast.makeText(LoadingScreenPrivate.this, msg, Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            finish();
        }
    }

}
