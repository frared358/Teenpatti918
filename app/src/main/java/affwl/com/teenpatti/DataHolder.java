package affwl.com.teenpatti;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
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

    public static String LOGIN_TOKEN;

    public static ProgressDialog progressDialog;
    public static void showProgress(Context context){
        progressDialog=new ProgressDialog(context);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please Wait ... ");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    public static void cancelProgress(){
        if(progressDialog != null){
            progressDialog.dismiss();
        }
    }

    //  Stack Share Prefrences
    private static SharedPreferences getPrefSTACK(Context context) {
        return context.getSharedPreferences("PREF_STACK", Context.MODE_PRIVATE);
    }

    public static String getSTACK(Context context,String Key) {
        return getPrefSTACK(context).getString(Key, "");
    }

    public static void setSTACK(Context context,String Key, String input) {
        SharedPreferences.Editor editor = getPrefSTACK(context).edit();
        editor.putString(Key, input);
        editor.commit();
    }

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

    Context context;

    public static String  setApiToken(String url,Context context){

        InputStream inputStream = null;

        String result = "";
        try {

            HttpClient httpclient = new DefaultHttpClient();

            HttpPost httpPost = new HttpPost(url);

            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            Toast.makeText(context, ""+getDataString(context,"token"), Toast.LENGTH_SHORT).show();
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


    public static String first_name, last_name, mobile_no, balance, emailaddress, user_id, tableid, table_name, table_time;

}
