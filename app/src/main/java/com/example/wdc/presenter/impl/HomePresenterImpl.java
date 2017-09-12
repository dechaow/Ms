package com.example.wdc.presenter.impl;

import android.content.Context;

import com.example.wdc.interactor.HomeInteractor;
import com.example.wdc.interactor.impl.HomeInteractorImpl;
import com.example.wdc.presenter.Presenter;
import com.example.wdc.view.HomeView;

/**
 * Created by wdc on 2016/7/21.
 */
public class HomePresenterImpl implements Presenter {

    private Context mContext;
    private HomeView mHomeView;
    private HomeInteractor mHomeInteractor;

    public HomePresenterImpl(Context mContext, HomeView mHomeView) {
        this.mContext = mContext;
        this.mHomeView = mHomeView;
        mHomeInteractor = new HomeInteractorImpl();
    }

    @Override
    public void initialized() {
        mHomeView.addViewPagerData(mHomeInteractor.getViewPagerData(),mHomeInteractor.getItemData(mContext));
    }
}
