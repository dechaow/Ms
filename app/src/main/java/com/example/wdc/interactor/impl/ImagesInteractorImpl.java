package com.example.wdc.interactor.impl;

import android.content.Context;

import com.example.wdc.bean.images.ImagesBean;
import com.example.wdc.bean.images.ImagesListBean;
import com.example.wdc.interactor.ImagesInteractor;
import com.example.wdc.retrofit.manager.RetrofitManager;
import com.example.wdc.server.INetResult;
import com.example.wdc.utils.UrlUtils;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by wdc on 2016/7/26.
 */
public class ImagesInteractorImpl implements ImagesInteractor {

    private INetResult<ImagesBean> result;

    public ImagesInteractorImpl(INetResult<ImagesBean> result) {
        this.result = result;
    }

    @Override
    public void loadImagesData(Context context,String col,String tag,int pn,int rn,int from) {

        RetrofitManager.builder(UrlUtils.BASE_IMAGES_URL)
                .getImages(col,tag,pn,rn,from)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ImagesBean>() {
                    @Override
                    public void accept(ImagesBean imagesBean) throws Exception {
                        result.onSuccess(imagesBean);
                    }

                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        result.onErro(throwable.getMessage());
                    }
                });
    }

}
