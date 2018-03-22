package com.example.wdc.ui.activity.newsdetail;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.wdc.bean.news.NewsDetailsBean;
import com.example.wdc.retrofit.manager.RetrofitManager;
import com.example.wdc.utils.UrlUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dechao on 2018/3/22.
 *
 * @Title:
 * @Description:
 * @Author:WangDeChao
 * @E-mail:mail2dechao@gmail.com
 */

public class NewsDetailViewModel extends AndroidViewModel {

    MutableLiveData<NewsDetailsBean> mDetailBean = new MutableLiveData<>();

    public NewsDetailViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<NewsDetailsBean> getNewsData(){
        return mDetailBean;
    }

    public void getDetailBean(int id){
        RetrofitManager.builder(UrlUtils.BASE_NEWS_URL)
                .getNewsDetails(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<NewsDetailsBean>() {
                    @Override
                    public void accept(NewsDetailsBean newsDetailsBean) throws Exception {
                        mDetailBean.setValue(newsDetailsBean);
                    }

                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        //error
                    }
                });
    }

}
