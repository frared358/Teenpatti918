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
            return DataHolder.getApi(urls[0],ServiceLastUserData.this);
        }

        @Override
        protected void onPostExecute(String result) {
            Log.i("Check1235", "" + result);

                /*if (DataHolder.getDataBoolean(ServiceLastUserData.this,"CHECK_SERVICE")){

                }*/
            Intent intent = new Intent(DataHolder.ACTION_USER_LAST_DATA);
            intent.putExtra(DataHolder.KEY_USER_LAST_DATA, result);
            sendBroadcast(intent);
        }
    }

    private class UserDataAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            return DataHolder.getApi(urls[0],ServiceLastUserData.this);
        }

        @Override
        protected void onPostExecute(String result) {
            Log.i("Check123", "" + result);
            Intent intent = new Intent(DataHolder.ACTION_USER_DATA);
            intent.putExtra(DataHolder.KEY_USER_DATA, result);
            sendBroadcast(intent);
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
                    new UserDataAsyncTask().execute("http://213.136.81.137:8081/api/getclientdesk?user_id=" + DataHolder.getDataString(ServiceLastUserData.this, "userid"));
                }
            });
        }
    }

}
