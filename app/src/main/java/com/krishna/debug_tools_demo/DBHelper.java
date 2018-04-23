package com.krishna.debug_tools_demo;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by krishna on 05/02/18.
 */
public class DBHelper extends SQLiteOpenHelper {
    private static DBHelper sInstance;
    //SQL String for creating the table required
    private static final String CREATE_SETTINGS_TABLE
            = "CREATE TABLE tbl_settings(" +
            "_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
            "VOIPUSERNAME TEXT," +
            "VOIPAUTHID TEXT," +
            "PASSWORD TEXT," +
            "VOIPDISPLAYNAME TEXT," +
            "SIPPROXYSERVER TEXT," +
            "SIPREGISTRAR TEXT," +
            "SIPREALM TEXT," +
            "EXPIRESTIME INTEGER);";

    //constructor
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public static DBHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DBHelper(context, "dummy.db", null, 2);
        }
        return sInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_SETTINGS_TABLE);
        for (int i = 0; i < 500; i++) {
            ContentValues initialValues = new ContentValues();
            initialValues.put("VOIPUSERNAME", "xxxxx");
            initialValues.put("VOIPAUTHID", "xxxxxxxxxx");
            initialValues.put("PASSWORD", "xxxxxx");
            initialValues.put("VOIPDISPLAYNAME", "xxxxxxxxx");
            initialValues.put("SIPPROXYSERVER", "xxxxxxxxxxxxx");
            initialValues.put("SIPREGISTRAR", "xxxxxxxxxxx");
            initialValues.put("SIPREALM", "xxxxxxxxxx");
            initialValues.put("EXPIRESTIME", 876877587);
            db.insert("tbl_settings", null, initialValues);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + "tbl_settings");
        onCreate(db);
    }

}
