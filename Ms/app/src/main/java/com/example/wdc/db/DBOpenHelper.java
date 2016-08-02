package com.example.wdc.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by wdc on 2016/8/1.
 */
public class DBOpenHelper extends SQLiteOpenHelper {

    private static DBOpenHelper openHelper;

    private DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public static DBOpenHelper getInstance(Context context){
        if (openHelper == null){
            synchronized (DBOpenHelper.class){
                if (openHelper == null){
                    openHelper = new DBOpenHelper(context.getApplicationContext(),DBConstant.DB_NAME,null,DBConstant.VERSION);
                }
            }
        }
        return openHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DBConstant.CREATE_TABLE_NEWS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
