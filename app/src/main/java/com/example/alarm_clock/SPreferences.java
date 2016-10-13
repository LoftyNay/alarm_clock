package com.example.alarm_clock;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by artem on 09.10.16.
 */

public class SPreferences {

    public static final String SP_NAME = "mysp";
    public static final String COMPLEXITY = "cmp";
    public static final String TYPE = "type";
    public static final String COMPLEXITY_BUTTON_ID = "compl_b_id";
    public static final String TYPE_BUTTON_ID = "type_b_id";
    private static SharedPreferences sharedPreferences = null;
    private static Context context;

    public static void init(Context c) {
        context = c;
        sharedPreferences = c.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }

    private static void init() {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }
    }

    public static void clear() {
        init();
        sharedPreferences.edit().clear().apply();
    }

    public static int getInt(String spCode, int defValue) {
        init();
        return sharedPreferences.getInt(spCode, defValue);
    }

    public static void saveTypeAndComplexity(int cmpImageId, int typeImageId, int cmpButtonId, int typeButtonId) {
        init();
        sharedPreferences.edit().putInt(SPreferences.COMPLEXITY, cmpImageId)
                .putInt(SPreferences.TYPE, typeImageId)
                .putInt(COMPLEXITY_BUTTON_ID, cmpButtonId)
                .putInt(TYPE_BUTTON_ID, typeButtonId)
                .apply();
    }
}
