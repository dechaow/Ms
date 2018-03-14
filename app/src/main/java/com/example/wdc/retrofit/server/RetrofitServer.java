package com.example.wdc.retrofit.server;

import com.example.wdc.bean.images.ImagesBean;
import com.example.wdc.bean.news.NewsDetailsBean;
import com.example.wdc.bean.news.NewsListBean;

import org.reactivestreams.Subscriber;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by wdc on 2016/7/22.
 */
public interface RetrofitServer {

    public static final String CACHE_CONTROL_AGE = "Cache-Control: public, max-age=";
    public static final int CACHE_TIME = 60 * 60 * 24 * 7;

    /**
     * 添加缓存
     *
     * @param date
     * @return
     */
    @Headers(CACHE_CONTROL_AGE + CACHE_TIME)
    @GET("stories/before/{date}")
    Observable<NewsListBean> getNews(@Path("date") String date);

    @Headers(CACHE_CONTROL_AGE + CACHE_TIME)
    @GET("story/{id}")
    Observable<NewsDetailsBean> getNewsDetails(@Path("id") int id);

    @Headers(CACHE_CONTROL_AGE + CACHE_TIME)
    @GET("imgs")
//?col={col}&tag={tag}&pn={pn}&rn=20&from=1
    Observable<ImagesBean> getImages(@Query("col") String col, @Query("tag") String tag, @Query("pn") int pn, @Query("rn") int rn, @Query("from") int from);

}
