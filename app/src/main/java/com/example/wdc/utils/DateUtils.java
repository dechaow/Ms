package com.example.wdc.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wdc on 2016/7/25.
 */
public class DateUtils {

    public static String getDate(){

        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String dateS = format.format(date);
        return dateS;
    }

    /**
     * 获得其他天的日期
     * @param Num 加减的天数
     * @return
     */
    public static String getOtherDate(long Num){
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Date getDate = new Date(System.currentTimeMillis() + Num * 24 * 60 * 60 * 1000);
        String resultDate = format.format(getDate);

        return resultDate;
    }

}
