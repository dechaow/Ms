package com.example.wdc.ui.fragment.news;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;

import com.example.wdc.bean.news.NewsBean;
import com.example.wdc.bean.news.NewsListBean;
import com.example.wdc.retrofit.manager.RetrofitManager;
import com.example.wdc.utils.DateUtils;
import com.example.wdc.utils.UrlUtils;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dechao on 2018/2/8.
 *
 * @Title: newsData
 * @Description: 负责数据逻辑的处理并把数据传给ui
 * 避免在此持有Activity的引用
 * DataBinding提供了一系列的observable对象，使用这些代替我们普通的类，
 * 可以做到数据改变的时候自动更新
 *
 * 但是我这里是拿不到activity的context的，所以我如果触发onItemClick的事件的时候我还得通知activity
 * 那么这里应该还需要使用一个liveData
 * @Author:WangDeChao
 * @E-mail:mail2dechao@gmail.com
 */

public class NewsViewModel extends AndroidViewModel implements LifecycleObserver,OnItemClickListener,OnItemLongClickListener{

    //observer对象，是databinding中的东西，如果我们在layout中引用了此viewModel,并且使用的是该数据，那么这个数据改变的时候就会对应的view状态就会改变
    private final ObservableList<NewsBean> mNewsListData = new ObservableArrayList<>();

    //继承liveData使其成为一个可观察的对象，这样我们在activity中使用.observer方法即可观察到这个数据的变化
    private MutableLiveData<NewsListBean> newsData = new MutableLiveData<>();
    //使用其来触发点击事件,设置值的时候有两种方式，set会在mainThread中进行
    private MutableLiveData<NewsBean> clickBoolean = new MutableLiveData<>();
    //长按事件
    private MutableLiveData<NewsBean> longClickBoolean = new MutableLiveData<>();

    private Context mContext;

    private boolean mCanLoad;

    //这里使用application的引用为什么可以防止内存泄露？
    public NewsViewModel(@NonNull Application application) {
        super(application);
        System.out.println("NewsViewModel.NewsViewModel  " + application + "      " + application.getApplicationContext());
        mContext = application.getApplicationContext();
        getData(DateUtils.getDate());
    }

    @Override
    public void onItemClick(NewsBean bean) {
        clickBoolean.postValue(bean);
    }

    public LiveData<NewsListBean> getNewsData() {
        return newsData;
    }

    public LiveData<NewsBean> getClickListener(){
        return clickBoolean;
    }

    public LiveData<NewsBean> getLongClickListener(){
        return longClickBoolean;
    }

    public void getData(final String date){
        setCanLoad(false);
        RetrofitManager.builder(UrlUtils.BASE_NEWS_URL)
                .getNewsData(date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<NewsListBean>() {
                    @Override
                    public void accept(NewsListBean newsListBean) throws Exception {
//                        mNewsListData.addAll(newsListBean.getStories());
                        if (date.equals(DateUtils.getDate())){
                            newsData.setValue(newsListBean);
                        }else{
                            newsData.postValue(newsListBean);
                        }
                        setCanLoad(true);
                    }

                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        setCanLoad(true);
                    }
                });
    }


    public boolean isCanLoad() {
        return mCanLoad;
    }

    public void setCanLoad(boolean mCanLoad) {
        this.mCanLoad = mCanLoad;
    }

    @Override
    public boolean longClick(NewsBean bean) {
        longClickBoolean.postValue(bean);
        return true;
    }
}
