package com.example.wdc.app;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.example.wdc.service.WebViewService;
import com.example.wdc.ui.activity.MainActivity;

import org.jetbrains.annotations.NotNull;

/**
 * Created by dechao on 2016/7/22.
 *
 * @Title:
 * @Description:
 * @Author:WangDeChao
 * @E-mail:mail2dechao@gmail.com
 */
public class MsApp extends Application {

    private static Context mContext;
@NotNull
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
//        Thread.setDefaultUncaughtExceptionHandler(new MyUnCaughtExceptionHandler());

        //预加载webview进程
        Intent intent = new Intent(mContext, WebViewService.class);
        mContext.startService(intent);
    }

    /**
     * 得到Application Context
     *
     * @return
     */
    public static Context getContext() {
        if (mContext == null) {
            throw new NullPointerException("the application context is null");
        }
        return mContext;
    }

    private Thread.UncaughtExceptionHandler mDefaultHandler;

    /**
     * 闪退处理
     * 但是只是取消了崩溃的弹窗，并不会重新启动
     */
    class MyUnCaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

        @Override
        public void uncaughtException(Thread thread, Throwable ex) {
            if (mDefaultHandler != null) {
                //如果用户没有处理则让系统默认的异常处理器来处理
                mDefaultHandler.uncaughtException(thread, ex);
            } else {
                Intent i = new Intent(MsApp.this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        }
    }

}
