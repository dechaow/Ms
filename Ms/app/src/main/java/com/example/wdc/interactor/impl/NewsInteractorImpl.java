package com.example.wdc.interactor.impl;

import android.content.Context;

import com.example.wdc.bean.news.NewsListBean;
import com.example.wdc.interactor.NewsInteractor;
import com.example.wdc.retrofit.manager.RetrofitManager;
import com.example.wdc.server.INetResult;
import com.example.wdc.utils.UrlUtils;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by wdc on 2016/7/22.
 */
public class NewsInteractorImpl implements NewsInteractor {

    private INetResult<NewsListBean> mINetResult = null;

    public NewsInteractorImpl(INetResult<NewsListBean> mINetResult) {
        this.mINetResult = mINetResult;
    }

    @Override
    public void loadDate(Context context,String date) {
        RetrofitManager.builder(context, UrlUtils.BASE_NEWS_URL)
                .getNewsData(date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() { //加载动画
                    @Override
                    public void call() {
                    }
                })
                .subscribe(new Action1<NewsListBean>() {
                    @Override
                    public void call(NewsListBean newsListBean) {
                            mINetResult.onSuccess(newsListBean);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mINetResult.onErro(throwable.getMessage());
                    }
                });
    }

}
