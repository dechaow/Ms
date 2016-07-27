package com.example.wdc.interactor;

import android.animation.AnimatorSet;
import android.content.Context;
import android.view.animation.Animation;

/**
 * Created by wdc on 2016/7/21.
 * splashView 关联接口
 * 定义一些获取splashView接口中的方法参数的方法
 * animatorSet
 * background
 */
public interface SplashInteractor {

    int getResId();

    Animation getAnim(Context context);
}
