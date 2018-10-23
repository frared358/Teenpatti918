package affwl.com.teenpatti;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
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
        Log.i("GITA","hiiiiiiiiiiii");

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                new NextChanceAsyncTask().execute("http://213.136.81.137:8081/api/deskNextChance?desk_id=" + DataHolder.getDataInt(ServiceLastUserData.this, "deskid"));
            }
        });


        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                if (mTimer != null) // Cancel if already existed
                    mTimer.cancel();
                else
                    mTimer = new Timer();   //recreate new
                mTimer.scheduleAtFixedRate(new TimeDisplay(), 0, notify);   //Schedule task
            }
        });
        thread2.start();
        thread1.start();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mTimer.cancel();    //For Cancel Timer
        this.stopSelf();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_NOT_STICKY;
    }

    private class GetChanceLastDataAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            Log.i("GITA","1hiiiiiiiiiiii");
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

    private class GeteLast5ChancAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            return DataHolder.getApi(urls[0],ServiceLastUserData.this);
        }

        @Override
        protected void onPostExecute(String result) {
            Intent intent = new Intent(DataHolder.ACTION_LAST_5_DATA);
            intent.putExtra(DataHolder.KEY_LAST_5_DATA, result);
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
                    new GetChanceLastDataAsyncTask().execute("http://213.136.81.137:8081/api/getlastchance?desk_id="+DataHolder.getDataInt(ServiceLastUserData.this, "deskid"));
                    //new GeteLast5ChancAsyncTask().execute("http://213.136.81.137:8081/api/getEachChance?desk_id="+DataHolder.getDataString(ServiceLastUserData.this, "deskid"));
                    //new UserDataAsyncTask().execute("http://213.136.81.137:8081/api/getclientdesk?user_id=" + DataHolder.getDataString(ServiceLastUserData.this, "userid"));
                }
            });
        }
    }

//    new NextChanceAsyncTask().execute("http://213.136.81.137:8081/api/deskNextChance?desk_id="+DeskId);

    private class NextChanceAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            Log.i("APIRUN",""+urls[0]);
            return DataHolder.setApi(urls[0],ServiceLastUserData.this);
        }

        @Override
        protected void onPostExecute(String result) {
            Log.i("Check123nextx", "" + result);
            try {
                JSONObject jsonObjMain = new JSONObject(result);
                JSONArray arr = new JSONArray(jsonObjMain.getString("data"));

                for (int i = 0; i < arr.length(); i++) {
                    String s = arr.getString(i);
                    Log.i("TTYY", "" + s);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
