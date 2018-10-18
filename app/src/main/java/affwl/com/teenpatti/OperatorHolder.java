package affwl.com.teenpatti;

import android.content.Context;
import android.telephony.TelephonyManager;

public class OperatorHolder {
    private TelephonyManager manager;
    private static final String TAG = "operatorholder";

    public OperatorHolder(Context context){
        manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
    }

    public String getOperatorName(){
        return manager.getNetworkOperatorName();
    }
}
