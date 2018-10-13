package affwl.com.teenpatti;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.Glide;

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
import java.util.List;

import microsoft.aspnet.signalr.client.hubs.HubConnection;
import microsoft.aspnet.signalr.client.hubs.HubProxy;


public class DataHolder {

    //Data Share Prefrences
    private static SharedPreferences getPrefData(Context context) {
        return context.getSharedPreferences("PREF_DATA", Context.MODE_PRIVATE);
    }


    public static String getDataString(Context context,String Key) {
        return getPrefData(context).getString(Key, "");
    }


    public static int getDataInt(Context context,String Key) {
        return getPrefData(context).getInt(Key, 0);
    }


    public static Long getDataLong(Context context,String Key) {
        return getPrefData(context).getLong(Key, -1);
    }


    public static boolean getDataBoolean(Context context,String Key) {
        return getPrefData(context).getBoolean(Key, false);
    }

    //String setData
    public static void setData(Context context,String Key, String input) {
        SharedPreferences.Editor editor = getPrefData(context).edit();
        editor.putString(Key, input);
        editor.commit();
    }

    //Long setData
    public static void setData(Context context,String Key, Long input) {
        SharedPreferences.Editor editor = getPrefData(context).edit();
        editor.putLong(Key, input);
        editor.commit();
    }

    //Int setData
    public static void setData(Context context,String Key, int input) {
        SharedPreferences.Editor editor = getPrefData(context).edit();
        editor.putInt(Key, input);
        editor.commit();
    }

    //Boolean setData
    public static void setData(Context context,String Key, boolean input) {
        SharedPreferences.Editor editor = getPrefData(context).edit();
        editor.putBoolean(Key, input);
        editor.commit();
    }

    public static String getAvatarImage(Context context,String Key) {
        return getPrefData(context).getString(Key, "");
    }


    public static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null){
            result += line;
            //Log.e("Line",result);
        }

        inputStream.close();
        return result;
    }


    public static String  getApi(String url){
        InputStream inputStream = null;
        String result = "";
        try {

            HttpClient httpclient = new DefaultHttpClient();
            HttpGet Httpget = new HttpGet(url);

            Httpget.setHeader("Accept", "application/json");
            Httpget.setHeader("Content-type", "application/json");
            //Httpget.setHeader("Token", DataHolder.LOGIN_TOKEN);

            HttpResponse httpResponse = httpclient.execute(Httpget);
            inputStream = httpResponse.getEntity().getContent();

            if(inputStream != null){
                try {
                    result = convertInputStreamToString(inputStream);
                }
                catch (Exception e){
                    Log.e("ERROR ",""+e);
                }
            }
            else
                result = "Did not work!";


        } catch (Exception e) {
            Log.d("ERROR ", ""+e);
        }
        return result;
    }


    public static String  setApiToken(String url,Context context){

        InputStream inputStream = null;

        String result = "";
        try {

            HttpClient httpclient = new DefaultHttpClient();

            HttpPost httpPost = new HttpPost(url);

            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            httpPost.setHeader("Authorization", getDataString(context,"token"));
            Log.e("Check","rtuyty");

            HttpResponse httpResponse = httpclient.execute(httpPost);
            Log.e("Check","tjhttjj");

            inputStream = httpResponse.getEntity().getContent();

            if(inputStream != null){
                try {
                    result = convertInputStreamToString(inputStream);
                    Log.e("Check","hey");

                }
                catch (Exception e){
                    Log.e("ERROR ",""+e);
                }
            }
            else
                result = "Did not work!";
            Log.e("Check","how "+result);



        } catch (Exception e) {

            Log.d("ERROR ", ""+e);
        }

        Log.e("result",result+"");
        //Toast.makeText(MainActivity.this, ""+result, Toast.LENGTH_SHORT).show();
        return result;
    }


    public static String getUserApi(String url, Context context) {
        InputStream inputStream = null;
        String result = "";
        try {

            HttpClient httpclient = new DefaultHttpClient();
            HttpGet Httpget = new HttpGet(url);

            Httpget.setHeader("Accept", "application/json");
            Httpget.setHeader("Content-type", "application/json");
            Httpget.setHeader("Authorization", DataHolder.getDataString(context,"token"));

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


    public static void unAuthorized(Context context,String result){
        try {
            JSONObject jsonObjMain = new JSONObject(result.toString());
            JSONObject jsonDes = new JSONObject(jsonObjMain.getString("description"));
            String UnAuthorized = jsonDes.getString("result");

            if(UnAuthorized.equalsIgnoreCase("UnAuthorized access found")){
//                Toast.makeText(context, "Token Expire", Toast.LENGTH_SHORT).show();
                context.startActivity(new Intent(context, LoginActivity.class));
            }
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
    }

    public static String first_name, last_name, mobile_no, balance, emailaddress, user_id, tableid, table_name, table_time, imageURL, avatar_url;
    public static String ACTION_USER_LAST_DATA="affwl.com.teenpatti.LASTDATA";
    public static String KEY_USER_LAST_DATA="teenpatti.LASTDATA";
    public static String ACTION_LAST_5_DATA="affwl.com.teenpatti.LAST5DATA";
    public static String KEY_LAST_5_DATA="teenpatti.LAST5DATA";
    public static String ACTION_USER_DATA="affwl.com.teenpatti.USERDATA";
    public static String KEY_USER_DATA="teenpatti.USERDATA";

    public static String updateUserStatusApi(String url,Context context,String status) {
        InputStream inputStream = null;
        String result = "";
        try {

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);

            String json = "";
            JSONObject jsonObject = new JSONObject();

            jsonObject.accumulate("userid", DataHolder.getDataString(context,"userid"));
            jsonObject.accumulate("user_status", status);

            json = jsonObject.toString();
            StringEntity se = new StringEntity(json);
            se.setContentType("application/json");

            httpPost.setEntity(new StringEntity(json));
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            httpPost.setHeader("Authorization", DataHolder.getDataString(context,"token"));

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

    static String Url1;
    static String Url2;
    static String Url3;
    static String Url4;
    static String Url5;
    static String Url6;
    static String Url7;
    static String Url8;
}
