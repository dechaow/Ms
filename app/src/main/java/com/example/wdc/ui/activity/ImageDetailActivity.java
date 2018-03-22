package com.example.wdc.ui.activity;

import android.databinding.DataBindingUtil;
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
import com.example.wdc.ms.ImageViewDataBindings;
import com.example.wdc.ms.R;
import com.example.wdc.ms.databinding.ActivityImgdetailBinding;
import com.example.wdc.ui.activity.base.BaseAppCompatActivity;
import com.example.wdc.ui.fragment.ImagesFragment;
import com.example.wdc.utils.NetUtils;
import com.example.wdc.widgets.photoview.Info;
import com.example.wdc.widgets.photoview.PhotoView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wdc on 2016/7/26.
 */
public class ImageDetailActivity extends BaseAppCompatActivity {

    @BindView(R.id.imgdetail_smoothimgview)
    protected PhotoView mPhotoView;

    private ImagesListBean bean;

    private Info info;

    String url;

    @Override
    protected void getBundleExtras(Bundle extras) {
        if (extras != null) {
            bean = extras.getParcelable("data");
            info = extras.getParcelable("info");

            url = extras.getString("url");
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

        //这里image的加载方式还需要修改

        mPhotoView.enable();

        if (bean != null){
            url = bean.getThumbnailUrl();
        }

//        ActivityImgdetailBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_imgdetail);
//        binding.setModel(bean);

        //glide 4.x 使用 RequestBuilder 构建处理
        RequestBuilder<Drawable> requestBuilder = Glide.with(this).load(url);

        //使用 RequestOptions 来处理 centerCrop() placeholder()等
        RequestOptions requestOptions = new RequestOptions()
                .centerCrop()
//                .placeholder(R.drawable.pic_loading)
                .error(R.drawable.pic_loading)
                .fitCenter()
                .priority(Priority.HIGH);

        //然后将 options 应用到 builder上
        requestBuilder.apply(requestOptions);

        //添加动画
        requestBuilder.transition(DrawableTransitionOptions.withCrossFade(300));

        //显示
        requestBuilder.load(url).into(mPhotoView);

        if (info != null){
            mPhotoView.animaFrom(info);

            mPhotoView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPhotoView.animaTo(mPhotoView.getInfo(), new Runnable() {
                        @Override
                        public void run() {
                            finish();
                        }
                    });
                }
            });
        }

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

    @Override
    public void finish() {
//        mPhotoView.animaTo(info,null);
        super.finish();
        if (info != null){
            overridePendingTransition(0,0);
        }
    }
}
