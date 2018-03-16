package com.example.wdc.interactor.impl;

import android.content.Context;

import com.example.wdc.bean.news.NewsDetailsBean;
import com.example.wdc.interactor.NewsDetailsInteractor;
import com.example.wdc.retrofit.manager.RetrofitManager;
import com.example.wdc.server.INetResult;
import com.example.wdc.utils.UrlUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


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
        RetrofitManager.builder(UrlUtils.BASE_NEWS_URL)
                .getNewsDetails(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<NewsDetailsBean>() {
                    @Override
                    public void accept(NewsDetailsBean newsDetailsBean) throws Exception {
                        result.onSuccess(newsDetailsBean);
                    }

                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        result.onErro(throwable.getMessage());
                    }
                });
    }
}
