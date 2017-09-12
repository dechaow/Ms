package com.example.wdc.event;

import com.example.wdc.bean.images.ImagesListBean;

/**
 * Created by wdc on 2016/7/26.
 */
public class ImageOnClick {

    public ImagesListBean listBean;
    public int x;
    public int y;
    public int width;
    public int height;

    public ImageOnClick(ImagesListBean listBean, int x, int y, int width, int height) {
        this.listBean = listBean;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
}
