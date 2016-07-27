package com.example.wdc.view;

import android.animation.AnimatorSet;
import android.app.Activity;
import android.view.animation.Animation;

/**
 * Created by bmzm-1020 on 2016/7/20.
 *logo页：
 * 加载背景
 * 动画
 * 跳转到主页
 *
 */
public interface SplashView{

    void setBackground(int resId);

    void startMainPage();

    void showAnimation(Animation set);
}
