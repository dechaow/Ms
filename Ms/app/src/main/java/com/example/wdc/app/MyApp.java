package com.example.wdc.app;

import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by wdc on 2016/7/22.
 */
public class MyApp extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;

        ImageLoader.getInstance().init(new ImageLoaderConfiguration.Builder(mContext).build());

    }

    /**
     * 得到Application Context
     * @return
     */
    public static Context getContext(){
        if (mContext == null){
            throw new NullPointerException("the application context is null");
        }
        return mContext;
    }
}
