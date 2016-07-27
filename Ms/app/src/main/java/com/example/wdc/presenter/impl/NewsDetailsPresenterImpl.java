package com.example.wdc.presenter.impl;

import android.content.Context;

import com.example.wdc.bean.news.NewsDetailsBean;
import com.example.wdc.interactor.NewsDetailsInteractor;
import com.example.wdc.interactor.impl.NewsDetailsInteractorImpl;
import com.example.wdc.presenter.NewsDetailsPresenter;
import com.example.wdc.server.INetResult;
import com.example.wdc.view.NewsDetailsView;

/**
 * Created by wdc on 2016/7/25.
 */
public class NewsDetailsPresenterImpl implements NewsDetailsPresenter,INetResult<NewsDetailsBean> {

    private Context context;
    private NewsDetailsView detailsView;
    private NewsDetailsInteractor detailsInteractor;

    public NewsDetailsPresenterImpl(Context context, NewsDetailsView detailsView) {
        this.context = context;
        this.detailsView = detailsView;
        detailsInteractor = new NewsDetailsInteractorImpl(this);
    }

    @Override
    public void loadDetailsData(int id) {
        detailsInteractor.LoadDetailsData(context,id);
        detailsView.showLoading("正在加载...");
    }

    @Override
    public void onSuccess(NewsDetailsBean result) {
        detailsView.hideLoading();
        detailsView.setNewsDetails(result);
    }

    @Override
    public void onErro(String erro) {
        detailsView.showErro(erro);
    }

    @Override
    public void onException(String exception) {

    }
}
