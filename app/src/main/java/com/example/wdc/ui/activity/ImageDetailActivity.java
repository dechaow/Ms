package com.example.wdc.ui.activity;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.TransitionOptions;
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.example.wdc.bean.images.ImagesListBean;
import com.example.wdc.ms.R;
import com.example.wdc.ui.activity.base.BaseAppCompatActivity;
import com.example.wdc.ui.fragment.ImagesFragment;
import com.example.wdc.utils.NetUtils;
import com.example.wdc.widgets.photoview.PhotoView;

import java.net.MalformedURLException;
import java.net.URL;

import butterknife.BindView;

/**
 * Created by wdc on 2016/7/26.
 */
public class ImageDetailActivity extends BaseAppCompatActivity {

    @BindView(R.id.imgdetail_smoothimgview)
    protected PhotoView mPhotoView;
    @BindView(R.id.imagedetail_root)
    protected LinearLayout imagedetail_root;

//    private int x;
//    private int y;
//    private int width;
//    private int height;

    private ImagesListBean bean;

    @Override
    protected void getBundleExtras(Bundle extras) {
        if (extras != null) {
            bean = extras.getParcelable("data");
        }
        setTheme(R.style.DefaultTheme_ImageDetailTheme);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_imgdetail;
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void initViewsAndEvents() {
        imagedetail_root.setBackgroundResource(android.R.color.transparent);

        System.out.println("ImageDetailActivity.initViewsAndEvents" + bean.getThumbnailUrl());
        String url = bean.getThumbnailUrl();

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
//            getWindow().setStatusBarColor(getResources().getColor(android.R.color.black));

//        Glide.with(this).load(url).into(mPhotoView);

        //glide 4.x 使用 RequestBuilder 构建处理
        RequestBuilder<Drawable> requestBuilder = Glide.with(this).load(url);

        //使用 RequestOptions 来处理 centerCrop() placeholder()等
        RequestOptions requestOptions = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.pic_loading)
                .error(R.drawable.pic_loading)
                .priority(Priority.HIGH);

        //然后将 options 应用到 builder上
        requestBuilder.apply(requestOptions);

        //添加动画
        requestBuilder.transition(DrawableTransitionOptions.withCrossFade(300));

        //显示
        requestBuilder.load(url).thumbnail(0.8f).into(mPhotoView);

    }

    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return false;
    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return null;
    }

    @Override
    protected Boolean isAddStatusHeight() {
        return false;
    }

}
