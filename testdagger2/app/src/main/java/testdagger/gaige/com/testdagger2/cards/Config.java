package testdagger.gaige.com.testdagger2.cards;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by gaige on 17/3/23.
 */

public class Config {
    public static int MAX_SHOW_COUNT;
    public static float SCALE_GAP;
    public static int TRANS_Y_GAP;

    public Config() {
    }

    public static void initConfig(Context context) {
        MAX_SHOW_COUNT = 3;
        SCALE_GAP = 0.1F;
        TRANS_Y_GAP = (int) TypedValue.applyDimension(1, 3.0F, context.getResources().getDisplayMetrics());
    }
}
