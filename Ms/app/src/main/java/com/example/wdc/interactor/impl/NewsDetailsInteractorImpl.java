package com.example.wdc.interactor.impl;

import android.content.Context;

import com.example.wdc.bean.news.NewsDetailsBean;
import com.example.wdc.interactor.NewsDetailsInteractor;
import com.example.wdc.retrofit.manager.RetrofitManager;
import com.example.wdc.server.INetResult;
import com.example.wdc.utils.UrlUtils;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by wdc on 2016/7/25.
 */
public class NewsDetailsInteractorImpl implements NewsDetailsInteractor {

    private INetResult<NewsDetailsBean> result = null;

    public NewsDetailsInteractorImpl(INetResult<NewsDetailsBean> result) {
        this.result = result;
    }

    @Override
    public void LoadDetailsData(Context context,int id) {
        RetrofitManager.builder(context, UrlUtils.BASE_NEWS_URL)
                .getNewsDetails(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<NewsDetailsBean>() {
                    @Override
                    public void call(NewsDetailsBean newsDetailsBean) {
                        result.onSuccess(newsDetailsBean);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        result.onErro(throwable.getMessage());
                    }
                });
    }
}
