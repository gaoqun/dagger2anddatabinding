package testdagger.gaige.com.testdagger2;

import android.app.Application;
import android.content.Context;

/**
 * Created by asus on 2017-03-11 09:17.
 */

public class MyApplication extends Application {
    public static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
    }
}
