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
    public static int navigationForTab=0;
    public static String LOGIN_TOKEN;
    public static Double STACK_VALUE;

    public static HubConnection _connection;
    public static HubProxy _hub;

    public static String SPORT_NAME;
    public static String TOURNAMENT_NAME;
    public static String MATCH_NAME;
    public static String MATCH_DATE;

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

    public static String getData(Context context,String Key) {
        return getPrefData(context).getString(Key, "");
    }

    public static void setData(Context context,String Key, String input) {
        SharedPreferences.Editor editor = getPrefData(context).edit();
        editor.putString(Key, input);
        editor.commit();
    }

    public  static boolean SIGNALR=false;

    public static double increment(double val){

        if(val >=0.00 && val <1.00){
            val = val+0.02;
        }
        else if(val >=1.0 && val <10.0){
            val = val+0.5;
        }
        else if(val >=10.0 && val <100.0) {
            val = val+5.0;
        }
        else if(val >=100.0 && val <1000.0) {
            val = val+20.0;
        }
        else if(val >=1000.0 ) {
            val = 0;
        }
        return val;
    }

    public static double decrement(double val){

        if(val >0.05 && val <1.00){
            val = val-0.02;
        }
        else if(val >=1.0 && val <10.0){
            val = val-0.5;
        }
        else if(val >=10.0 && val <100.0) {
            val = val-5.0;
        }
        else if(val >=10.0 && val <1000.0) {
            val = val-5.0;
        }
        else if(val <= 0.05) {
            val = 0;
        }
        return val;
    }

    public static double profit(double odd,double stack){
        return (odd-1)*stack;
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
            Httpget.setHeader("Token", DataHolder.LOGIN_TOKEN);

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

    public static String  setApi(String url){

        InputStream inputStream = null;

        String result = "";
        try {

            HttpClient httpclient = new DefaultHttpClient();

            HttpPost httpPost = new HttpPost(url);

            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            httpPost.setHeader("Token", DataHolder.LOGIN_TOKEN);
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

    public static ArrayList<String> ArrayListCustomeName = new ArrayList<String>();
    public static List <Boolean> ListDeleted = new ArrayList<Boolean>();

    public static final String ACTION_SEND_ACTIVE = "com.affwl.exchange.sport.RESPONSE";
    public static final String ACTION_SEND_FANCY_BOOKMAKING = "com.affwl.exchange.sport.FANCY_BOOKMAKING";
    public static final String keySIGNALR = "SIGNALR";
    public static final String keyFANCY_BOOKMAKING = "FANCY_BOOKMAKING";

}
