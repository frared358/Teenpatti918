package affwl.com.teenpatti;

import android.content.Context;
import android.content.SharedPreferences;

public class Session {
    Context context;
    String data;
    SharedPreferences preferences;

    public Session(Context context) {
        preferences = context.getSharedPreferences("pref", Context.MODE_PRIVATE);
    }

    public String getName() {
        String name = preferences.getString("name", "");
        return name;
    }

    public void put(String image) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("image", image);
        editor.commit();
    }

    public String getImage() {
        String image = preferences.getString("image", "");
        return image;
    }

    public String getID() {
        String ID = preferences.getString("ID", "");
        return ID;
    }
}
