package com.example.wdc.presenter.impl;

import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;

import com.example.wdc.interactor.MainInteractor;
import com.example.wdc.interactor.impl.MainInteractorImpl;
import com.example.wdc.presenter.Presenter;
import com.example.wdc.view.MainView;

/**
 * Created by wdc on 2016/7/21.
 */
public class MainPresenterImpl implements Presenter {

    private Activity mActivity;
    private MainView mMainView;
    private MainInteractor mMainInteractor;

    public MainPresenterImpl(Activity mActivity, MainView mMainView) {
        this.mActivity = mActivity;
        this.mMainView = mMainView;
        mMainInteractor = new MainInteractorImpl();
    }

    @Override
    public void initialized() {
        Fragment fragment = mMainInteractor.getFragment();
        mMainView.showFragment(fragment);
        mMainView.addDrawerFragment(mMainInteractor.getDrawerFragment());

    }
}
