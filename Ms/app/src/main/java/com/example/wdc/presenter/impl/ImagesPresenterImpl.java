package com.example.wdc.presenter.impl;

import android.content.Context;

import com.example.wdc.bean.images.ImagesBean;
import com.example.wdc.bean.images.ImagesListBean;
import com.example.wdc.interactor.ImagesInteractor;
import com.example.wdc.interactor.impl.ImagesInteractorImpl;
import com.example.wdc.presenter.ImagesPresenter;
import com.example.wdc.server.INetResult;
import com.example.wdc.view.ImagesView;

/**
 * Created by wdc on 2016/7/26.
 */
public class ImagesPresenterImpl implements ImagesPresenter,INetResult<ImagesBean>{

    private Context context;
    private ImagesView mImagesView;
    private ImagesInteractor mInteractor;

    public ImagesPresenterImpl(Context context, ImagesView mImagesView) {
        this.context = context;
        this.mImagesView = mImagesView;
        mInteractor = new ImagesInteractorImpl(this);
    }

    @Override
    public void loadImages(String col, String tag, int pn,int rn,int from) {
//        mImagesView.showLoading("正在加载...");
        mInteractor.loadImagesData(context,col,tag,pn,rn,from);
    }

    @Override
    public void onItemClickListener(ImagesListBean images, int x, int y, int width, int height) {
        mImagesView.ToDetails(images,x,y,width,height);
    }

    @Override
    public void onSuccess(ImagesBean result) {
//        mImagesView.hideLoading();
        mImagesView.showImagesData(result);
    }

    @Override
    public void onErro(String erro) {
        mImagesView.showErro(erro);
    }

    @Override
    public void onException(String exception) {
        mImagesView.showException(exception);
    }
}
