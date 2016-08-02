package com.example.wdc.ui.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Toast;

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
        //申请权限
        applicationPermission();
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

    protected void applicationPermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA},1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == 1){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,"permissions",Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this,"权限不足",Toast.LENGTH_SHORT).show();
            }
            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

}
