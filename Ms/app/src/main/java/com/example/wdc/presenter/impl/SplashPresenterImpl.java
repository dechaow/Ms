package com.example.wdc.presenter.impl;

import android.content.Context;
import android.view.animation.Animation;

import com.example.wdc.interactor.SplashInteractor;
import com.example.wdc.interactor.impl.SplashInteractorImpl;
import com.example.wdc.presenter.Presenter;
import com.example.wdc.view.SplashView;

/**
 * Created by wdc on 2016/7/21.
 */
public class SplashPresenterImpl implements Presenter {

    private Context context;
    private SplashView splashView;
    private SplashInteractor interactor;

    public SplashPresenterImpl(Context context,SplashView view) {
        this.context = context;
        this.splashView = view;
        interactor = new SplashInteractorImpl();
    }

    @Override
    public void initialized() {
        splashView.setBackground(interactor.getResId());
        Animation set = interactor.getAnim(context);
        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
               splashView.startMainPage();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        splashView.showAnimation(set);
    }
}
