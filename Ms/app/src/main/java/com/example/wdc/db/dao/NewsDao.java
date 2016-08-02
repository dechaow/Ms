package com.example.wdc.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.wdc.db.DBConstant;
import com.example.wdc.db.DBOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wdc on 2016/8/1.
 * data access object
 * 数据访问对象是第一个面向对象的数据库接口
 */
public class NewsDao {

    private DBOpenHelper openHelper;

    public NewsDao(Context context){
        openHelper = DBOpenHelper.getInstance(context);
    }

    public void insertNews(String id){
        SQLiteDatabase database = openHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBConstant.NEWS_ID,id);
        long insertNum = database.insert(DBConstant.TABLE_NEWS,null,contentValues);
    }

    public void deleteNews(String id){
        SQLiteDatabase database = openHelper.getWritableDatabase();
        database.delete(DBConstant.TABLE_NEWS,DBConstant.NEWS_ID + "=?",new String[]{id});
    }

    public void updateNews(String id){
        SQLiteDatabase database = openHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBConstant.NEWS_ID,id);
        database.update(DBConstant.TABLE_NEWS,contentValues,DBConstant.NEWS_ID +"=?",new String[]{id});
    }

    public List<String> getAllNews(){
        List<String> list = new ArrayList<>();
        SQLiteDatabase database = openHelper.getReadableDatabase();
        String sqlQuery = "select * from " + DBConstant.TABLE_NEWS;
        Cursor cursor = database.rawQuery(DBConstant.TABLE_NEWS,null);
        while (cursor.moveToFirst()){
            do {
                String id = cursor.getString(0);
                list.add(id);
            }while (cursor.moveToNext());
        }
        return list;
    }

}
