package com.example.wdc.retrofit.manager;

import android.content.Context;

import com.example.wdc.app.MyApp;
import com.example.wdc.bean.images.ImagesBean;
import com.example.wdc.bean.news.NewsDetailsBean;
import com.example.wdc.bean.news.NewsListBean;
import com.example.wdc.retrofit.server.RetrofitServer;
import com.example.wdc.utils.NetUtils;
import com.example.wdc.utils.UrlUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * Created by wdc on 2016/7/22.
 */
public class RetrofitManager {

    private OkHttpClient mOkHttpClient;
    private RetrofitServer mRetrofitServer;

    private Context context;

    public static RetrofitManager builder(Context context,String baseUrl){
       return new RetrofitManager(context,baseUrl);
    }

    /**
     * 配置retrofit
     * @param context
     */
    private RetrofitManager(Context context,String baseUrl) {
        this.context = context;
        initOkhttp();
        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(mOkHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mRetrofitServer = mRetrofit.create(RetrofitServer.class);
    }

    private void initOkhttp(){

        HttpLoggingInterceptor mHttpLoggingInterceptor = new HttpLoggingInterceptor();
        //设置级别为body
        mHttpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        //设置cache path time

        //避免多次初始化
        if (mOkHttpClient == null){
            synchronized (RetrofitManager.class){
                if (mOkHttpClient == null){
                    //缓存
                    Cache mCache = new Cache(new File(MyApp.getContext().getCacheDir(),"/httpCache"),1024*1024*100);

                    mOkHttpClient = new OkHttpClient.Builder()
                            .cache(mCache)
                            .addInterceptor(mInterceptor)
                            .addNetworkInterceptor(mInterceptor)
                            .addInterceptor(mHttpLoggingInterceptor)
                            .retryOnConnectionFailure(true)
                            .connectTimeout(10, TimeUnit.SECONDS)
                            .build();
                }
            }
        }

    }

    Interceptor mInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {

            Request request = chain.request();
            if (!NetUtils.isNetworkConnected(context)){
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
            }

            Response response = chain.proceed(request);
            //有网的时候脸上@Headers里面的配置
            if (NetUtils.isNetworkConnected(context)){
                String cache = response.cacheControl().toString();
                return response.newBuilder()
                        .header("Cache-Control",cache)
                        .removeHeader("Pragma")
                        .build();
            }else{
                return response.newBuilder()
                        .header("Cache-Control","public,only-if-cached,max-stale=" + 1000*3600*24)
                        .removeHeader("Pragma")
                        .build();
            }
        }
    };

    /**
     * 获取新闻列表
     * @param date
     * @return
     */
    public Observable<NewsListBean> getNewsData(String date){
        return mRetrofitServer.getNews(date);
    }

    public Observable<NewsDetailsBean> getNewsDetails(int id){
        return mRetrofitServer.getNewsDetails(id);
    }

    public Observable<ImagesBean> getImages(String col,String tag,int pn,int rn,int from){
        return mRetrofitServer.getImages(col,tag,pn,rn,from);
    }

}
