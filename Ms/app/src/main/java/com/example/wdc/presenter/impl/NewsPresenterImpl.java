package com.example.wdc.presenter.impl;

import android.content.Context;

import com.example.wdc.bean.news.NewsBean;
import com.example.wdc.bean.news.NewsListBean;
import com.example.wdc.interactor.NewsInteractor;
import com.example.wdc.interactor.impl.NewsInteractorImpl;
import com.example.wdc.presenter.NewsPresenter;
import com.example.wdc.server.INetResult;
import com.example.wdc.view.NewsView;

/**
 * Created by wdc on 2016/7/25.
 */
public class NewsPresenterImpl implements NewsPresenter,INetResult<NewsListBean> {

    private Context context;
    private NewsView mNewsView;
    private NewsInteractor mNewsInteractor;

    public NewsPresenterImpl(Context context, NewsView mNewsView) {
        this.context = context;
        this.mNewsView = mNewsView;
        mNewsInteractor = new NewsInteractorImpl(this);
    }

    @Override
    public void onItemClickListener(NewsBean newsBean) {
        mNewsView.ToDetails(newsBean);
    }

    @Override
    public void loadListData(String date) {
        mNewsInteractor.getRefreshDate(context,date);
        mNewsView.showLoading("正在加载...");
    }

    @Override
    public void onSuccess(NewsListBean result) {
        mNewsView.hideLoading();
        mNewsView.addRefreshData(result);
    }

    @Override
    public void onErro(String erro) {
        mNewsView.showErro(erro);
    }

    @Override
    public void onException(String exception) {
        mNewsView.showException(exception);
    }
}
