package com.example.alarm_clock.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by artem on 08.10.16.
 */
public class Db {

    public static final int VERSION_DB = 1;
    public static final String NAME_DB = "alarmDB";

    public static final String TABLE_NAME = "alarm";
    public static final String ID = "_id";
    public static final String TYPE_IMAGE = "type_image";
    public static final String COMPLEXITY_IMAGE = "complexity_image";
    public static final String DATE = "date";
    public static final String DESCRIPTION = "description";
    public static final String WEEKDAY = "weekday";
    public static final String SW = "sw";

    private SQLiteDatabase sqLiteDatabase;
    private DBHelper dbHelper;

    public Db(Context context) {
        dbHelper = new DBHelper(context);
    }

    public void close() {
        if (sqLiteDatabase != null) sqLiteDatabase.close();
        if (dbHelper != null) dbHelper.close();
    }

    public Cursor getMaxId() {
        sqLiteDatabase = dbHelper.getReadableDatabase();
        String query = "SELECT MAX(_id) AS id FROM " + TABLE_NAME;
        return sqLiteDatabase.rawQuery(query, null);
    }

    public Cursor getAllAlarms() {
        sqLiteDatabase = dbHelper.getReadableDatabase();
        return sqLiteDatabase.query(TABLE_NAME, null, null, null, null, null, null);
    }

    public void addAlarm(int typeImage, int complexityImage, String date, String description, String weekday, String sw) {
        sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TYPE_IMAGE, typeImage);
        values.put(COMPLEXITY_IMAGE, complexityImage);
        values.put(DATE, date);
        values.put(DESCRIPTION, description);
        values.put(WEEKDAY, weekday);
        values.put(SW, sw);
        sqLiteDatabase.insert(TABLE_NAME, null, values);
        values.clear();
    }

    public void updateSwitch(boolean checked, int id) {
        sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SW, checked);
        sqLiteDatabase.update(TABLE_NAME, values, "_id = " + id, null);
        close();
        values.clear();
    }

    public class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, NAME_DB, null, VERSION_DB);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME +
                    "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    TYPE_IMAGE + " INTEGER, " +
                    COMPLEXITY_IMAGE + " INTEGER, " +
                    DATE + " TEXT, " +
                    DESCRIPTION + " TEXT, " +
                    WEEKDAY + " TEXT, " +
                    SW + " TEXT);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            String query = "DROP TABLE IF EXISTS " + TABLE_NAME;
            sqLiteDatabase.execSQL(query);
            this.onCreate(sqLiteDatabase);
        }
    }

}
