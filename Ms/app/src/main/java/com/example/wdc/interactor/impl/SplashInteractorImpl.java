package com.example.wdc.interactor.impl;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.view.ViewAnimationUtils;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.wdc.interactor.SplashInteractor;
import com.example.wdc.ms.R;

/**
 * Created by wdc on 2016/7/21.
 */
public class SplashInteractorImpl implements SplashInteractor {
    @Override
    public int getResId() {
        return R.color.colorBase;
    }

    @Override
    public Animation getAnim(Context context) {
        return AnimationUtils.loadAnimation(context,R.anim.anim_splash);
    }
}
