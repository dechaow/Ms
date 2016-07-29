package com.example.wdc.presenter.impl;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.example.wdc.bean.images.ImagesBean;
import com.example.wdc.bean.images.ImagesListBean;
import com.example.wdc.event.ImagePushClick;
import com.example.wdc.interactor.ImagesInteractor;
import com.example.wdc.interactor.impl.ImagesInteractorImpl;
import com.example.wdc.presenter.ImagesPresenter;
import com.example.wdc.server.INetResult;
import com.example.wdc.view.ImagesView;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by wdc on 2016/7/26.
 */
public class ImagesPresenterImpl implements ImagesPresenter,INetResult<ImagesBean>{

    private Context context;
    private ImagesView mImagesView;
    private ImagesInteractor mInteractor;
    private Boolean isRefresh = false;


    public ImagesPresenterImpl(Context context, ImagesView mImagesView) {
        this.context = context;
        this.mImagesView = mImagesView;
        mInteractor = new ImagesInteractorImpl(this);
    }

    @Override
    public void loadImages(String col, String tag, int pn,int rn,int from,boolean isRefresh) {
        mInteractor.loadImagesData(context,col,tag,pn,rn,from);
        this.isRefresh = isRefresh;
    }

    @Override
    public void onItemClickListener(ImagesListBean images, int x, int y, int width, int height) {
        mImagesView.ToDetails(images,x,y,width,height);
    }

    @Override
    public void onSuccess(ImagesBean result) {
//        mImagesView.hideLoading();
        if (isRefresh){
            mImagesView.showImagesData(result);
        }else{
            mImagesView.showMoreImagesData(result);
        }

    }

    @Override
    public void onErro(String erro) {
//        mImagesView.hideLoading();
        mImagesView.showErro(erro);
        Snackbar.make(((Activity)context).getWindow().getDecorView(),"网络链接失败！",Snackbar.LENGTH_INDEFINITE).setAction("刷新", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new ImagePushClick());
            }
        }).show();

    }

    @Override
    public void onException(String exception) {
        mImagesView.showException(exception);
    }
}
