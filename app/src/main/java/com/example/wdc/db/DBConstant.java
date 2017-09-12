package com.example.wdc.db;

/**
 * Created by wdc on 2016/8/1.
 */
public class DBConstant {

    public static final String DB_NAME = "ms";

    public static final int VERSION = 1;

    public static final String TABLE_NEWS = "news";

    public static final String NEWS_ID = "newsId";

    public static final String CREATE_TABLE_NEWS = "create table " + TABLE_NEWS + "(" + NEWS_ID + " text" + ")";

}
