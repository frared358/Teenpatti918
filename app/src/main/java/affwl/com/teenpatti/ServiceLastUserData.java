package affwl.com.teenpatti;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

public class ServiceLastUserData extends Service {

    public static final int notify = 1000;  //interval between two services(Here Service run every 5 Minute)
    private Handler mHandler = new Handler();   //run on another Thread to avoid crash
    private Timer mTimer = null;    //timer handling

    public ServiceLastUserData() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (mTimer != null) // Cancel if already existed
            mTimer.cancel();
        else
            mTimer = new Timer();   //recreate new
        mTimer.scheduleAtFixedRate(new TimeDisplay(), 0, notify);   //Schedule task
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mTimer.cancel();    //For Cancel Timer
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    private class GetChanceLastDataAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            return DataHolder.getUserApi(urls[0],ServiceLastUserData.this);
        }

        @Override
        protected void onPostExecute(String result) {
            Log.i("Check1235", "" + result);
            try {
                JSONObject jsonObjMain = new JSONObject(result.toString());

                JSONArray arr = new JSONArray(jsonObjMain.getString("data"));

                int len = arr.length();

                Intent intent = new Intent(DataHolder.ACTION_USER_LAST_DATA);
                intent.putExtra(DataHolder.KEY_USER_LAST_DATA, result);
                sendBroadcast(intent);

                /*for (int i=0;i<len;i++){
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
                    lastNext_user = key.getString("game_status");
                    lastWin_lose = key.getString("win_lose");
                    lastDatetime = key.getString("datetime");

                    Log.i("CHANCESID",""+lastChanceid);
                }*/

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    //class TimeDisplay for handling task
    class TimeDisplay extends TimerTask {
        @Override
        public void run() {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    new GetChanceLastDataAsyncTask().execute("http://213.136.81.137:8081/api/getlastchance");
                }
            });
        }
    }

}
