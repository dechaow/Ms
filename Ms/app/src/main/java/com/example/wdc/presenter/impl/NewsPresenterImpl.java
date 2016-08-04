package com.example.wdc.presenter.impl;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.example.wdc.bean.news.NewsBean;
import com.example.wdc.bean.news.NewsListBean;
import com.example.wdc.event.NewsPushClick;
import com.example.wdc.event.NewsStartRefresh;
import com.example.wdc.event.NewsStopRefresh;
import com.example.wdc.interactor.NewsInteractor;
import com.example.wdc.interactor.impl.NewsInteractorImpl;
import com.example.wdc.presenter.NewsPresenter;
import com.example.wdc.server.INetResult;
import com.example.wdc.view.NewsView;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by wdc on 2016/7/25.
 */
public class NewsPresenterImpl implements NewsPresenter,INetResult<NewsListBean> {

    private Context context;
    private NewsView mNewsView;
    private NewsInteractor mNewsInteractor;

    private Boolean isRefresh;

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
    public void loadListData(String date,Boolean isRefresh) {
        mNewsInteractor.loadDate(context,date);
        this.isRefresh = isRefresh;
    }


    @Override
    public void onSuccess(NewsListBean result) {
        if (isRefresh){
            mNewsView.addRefreshData(result);
        }else{
            mNewsView.addLoadMoreData(result);
        }
    }

    @Override
    public void onErro(String erro) {
        EventBus.getDefault().post(new NewsStopRefresh());
        Snackbar.make(((Activity)context).getCurrentFocus(),"网络链接失败！",Snackbar.LENGTH_INDEFINITE).setAction("刷新", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new NewsStartRefresh());
                EventBus.getDefault().post(new NewsPushClick());
            }
        }).show();
    }

    @Override
    public void onException(String exception) {
    }
}
