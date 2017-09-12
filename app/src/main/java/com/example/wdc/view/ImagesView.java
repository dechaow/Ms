package com.example.wdc.view;

import com.example.wdc.bean.images.ImagesBean;
import com.example.wdc.bean.images.ImagesListBean;
import com.example.wdc.view.base.BaseView;

/**
 * Created by wdc on 2016/7/26.
 */
public interface ImagesView extends BaseView {

    void showImagesData(ImagesBean bean);

    void showMoreImagesData(ImagesBean bean);

    void ToDetails(ImagesListBean images, int x, int y, int width, int height);
}
