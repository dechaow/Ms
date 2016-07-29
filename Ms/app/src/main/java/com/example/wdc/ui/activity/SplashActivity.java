package com.example.wdc.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.example.wdc.ms.R;
import com.example.wdc.ui.activity.base.BaseAppCompatActivity;
import com.example.wdc.utils.NetUtils;
import com.example.wdc.widgets.TitanicTextView.Titanic;
import com.example.wdc.widgets.TitanicTextView.TitanicTextView;

import butterknife.BindView;

/**
 * Created by wdc on 2016/7/20.
 */
public class SplashActivity extends BaseAppCompatActivity{

    @BindView(R.id.titanic_tv)
    protected TitanicTextView myTitanicTextView;
    private Titanic titanic;
    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_splash;
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void initViewsAndEvents() {
        titanic = new Titanic();
        titanic.start(myTitanicTextView);
        new Thread(runnable).start();
    }

    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return false;
    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return null;
    }

    @Override
    protected Boolean isAddStatusHeight() {
        return false;
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1){
                readyGoThenKill(MainActivity.class);
            }
        }
    };
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            try {
                Thread.sleep(3000);
                handler.sendEmptyMessage(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };
    @Override
    protected void onPause() {
        super.onPause();
        titanic.cancel();
    }
}
