package com.example.wdc.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.wdc.app.MsApp;
import com.example.wdc.ms.R;

/**
 * Created by wdc on 16/7/20.
 */
public class PrefUtil {


    private static final String PRE_NAME = "MS";
    private static final String PRE_THEME = "theme";

    private static SharedPreferences getSharedPreferences() {
        return MsApp.getContext()
                .getSharedPreferences(PRE_NAME, Context.MODE_PRIVATE);
    }

    /**
     * 将设置的 theme 保存起来
     * @param themeId
     */
    public static void setTheme(int themeId){
        getSharedPreferences().edit().putInt(PRE_THEME,themeId).commit();
    }

    public static int getTheme(){
        int themeId = getSharedPreferences().getInt(PRE_THEME,-1);
        if (themeId == -1){
            themeId = R.style.DefaultTheme;
        }
        return themeId;
    }

}
