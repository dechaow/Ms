package com.example.wdc.utils;

import java.text.DateFormat;
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

}
