package com.example.wdc.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.widget.LinearLayout;

import com.example.wdc.ms.R;
import com.example.wdc.presenter.Presenter;
import com.example.wdc.presenter.impl.SplashPresenterImpl;
import com.example.wdc.ui.activity.base.BaseAppCompatActivity;
import com.example.wdc.utils.NetUtils;
import com.example.wdc.view.SplashView;

import butterknife.BindView;

/**
 * Created by wdc on 2016/7/20.
 */
public class SplashActivity extends BaseAppCompatActivity implements SplashView {

    @BindView(R.id.splash_rootview)
    LinearLayout splashRootView;
    Presenter presenter;
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
        presenter = new SplashPresenterImpl(this,this);
        presenter.initialized();
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

    @Override
    public void setBackground(int resId) {

    }

    @Override
    public void startMainPage() {
        readyGoThenKill(MainActivity.class);
    }

    @Override
    public void showAnimation(Animation set) {
//        this.getWindow().getDecorView().startAnimation(set);
        splashRootView.startAnimation(set);
    }
}
