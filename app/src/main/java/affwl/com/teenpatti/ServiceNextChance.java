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

public class ServiceNextChance extends Service {
    public ServiceNextChance() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) { return START_NOT_STICKY; }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("GITAxx","hiiiiiiiiiiii");
        new NextChanceAsyncTask().execute("http://213.136.81.137:8081/api/deskNextChance?desk_id=" + DataHolder.getDataInt(this, "deskid"));
    }

    private class NextChanceAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            Log.i("APIRUNx",""+urls[0]);
            return DataHolder.setApi(urls[0],ServiceNextChance.this);
        }

        @Override
        protected void onPostExecute(String result) {
            Log.i("Check123next", "" + result);
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
