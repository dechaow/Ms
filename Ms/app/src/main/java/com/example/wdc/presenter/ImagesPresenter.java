package com.example.wdc.presenter;

import com.example.wdc.bean.images.ImagesListBean;

/**
 * Created by wdc on 2016/7/26.
 */
public interface ImagesPresenter {

    void loadImages(String col,String tag,int pn,int rn,int from,boolean isRefresh);

    void onItemClickListener(ImagesListBean images,int x,int y,int width,int height);

}
