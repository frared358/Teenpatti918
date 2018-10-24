package affwl.com.teenpatti;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import static affwl.com.teenpatti.DataHolder.USERS_COUNT;

public class ServiceUserInfo extends Service {

    private Handler mHandler;
    public static final long DEFAULT_SYNC_INTERVAL = 2000;

    public ServiceUserInfo() {}


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }



    // task to be run here
    private Runnable runnableService = new Runnable() {
        @Override
        public void run() {
            syncData();
            mHandler.postDelayed(runnableService, DEFAULT_SYNC_INTERVAL);
        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Create the Handler object
        mHandler = new Handler();
        // Execute a runnable task as soon as possible
        mHandler.post(runnableService);

        return START_STICKY;
    }

    private synchronized void syncData() {
        new userInfoAyncTask().execute("http://213.136.81.137:8081/api/get_desk_cards?desk_id=" + DataHolder.getDataInt(ServiceUserInfo.this, "deskid"));
    }

    private class userInfoAyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            return DataHolder.getApi(urls[0], ServiceUserInfo.this);
        }

        @Override
        protected void onPostExecute(String result) {

            Log.i("ServiceOrderChancex", "" + result);
            Intent intent = new Intent(DataHolder.ACTION_USER_DATA);
            intent.putExtra(DataHolder.KEY_USER_DATA, result);
            sendBroadcast(intent);
        }
    }

}
