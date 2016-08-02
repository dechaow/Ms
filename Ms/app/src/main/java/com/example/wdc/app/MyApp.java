package com.example.wdc.app;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.example.wdc.ui.activity.MainActivity;
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

//        Thread.setDefaultUncaughtExceptionHandler(new MyUnCaughtExceptionHandler());
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

    private Thread.UncaughtExceptionHandler mDefaultHandler;
    /**
     * 闪退处理
     * 但是只是取消了崩溃的弹窗，并不会重新启动
     */
    class MyUnCaughtExceptionHandler implements Thread.UncaughtExceptionHandler{

        @Override
        public void uncaughtException(Thread thread, Throwable ex) {
            if(mDefaultHandler != null){
                //如果用户没有处理则让系统默认的异常处理器来处理
                mDefaultHandler.uncaughtException(thread, ex);
            }else{
                Intent i = new Intent(MyApp.this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        }
    }
}
