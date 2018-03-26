package com.example.wdc.ui.activity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.wdc.bean.images.ImagesListBean;
import com.example.wdc.ms.R;
import com.example.wdc.ui.activity.base.BaseAppCompatActivity;
import com.example.wdc.widgets.photoview.Info;
import com.example.wdc.widgets.photoview.PhotoView;

import butterknife.BindView;

/**
 * Created by wdc on 2016/7/26.
 */
public class ImageDetailActivity extends BaseAppCompatActivity {

    @BindView(R.id.imgdetail_smoothimgview)
    protected PhotoView mPhotoView;
    @BindView(R.id.img_bg)
    protected View mViewBg;

    private ImagesListBean bean;

    private Info info;

    String url;

    private ValueAnimator mAnimator = null;

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

        if (bean != null) {
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

        //加载进度监听
        requestBuilder.listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                return false;
            }

        });

        //显示
        requestBuilder.load(url).into(mPhotoView);

        if (info != null) {
            mPhotoView.animaFrom(info);
            setImageBackgroundAnim(0f, 1f);
        }

        mPhotoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (info == null) {
                    finish();
                    return;
                }
                mPhotoView.animaTo(info, new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                });

                setImageBackgroundAnim(1f, 0f);
            }
        });

        mPhotoView.setScrollDismissListener(new PhotoView.IScrollDismiss() {
            @Override
            public void dismiss() {
                if (info == null) {
                    finish();
                    return;
                }
                mPhotoView.animaTo(info, new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                });

                setImageBackgroundAnim(1f, 0f);
            }
        });

    }

    @Override
    public void onNetworkConnected() {
    }

    @Override
    public void onNetworkDisConnected() {
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
        super.finish();
        if (info != null) {
            overridePendingTransition(0, 0);
        }
    }

    @Override
    public void onBackPressed() {
        if (info == null) {
            finish();
            return;
        }
        mPhotoView.animaTo(info, new Runnable() {
            @Override
            public void run() {
                finish();
            }
        });
        setImageBackgroundAnim(1f, 0f);
    }

    private void setImageBackgroundAnim(float start, float end) {
        mAnimator = ObjectAnimator.ofFloat(1f, 0f);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float current = (float) animation.getAnimatedValue();
                mViewBg.setAlpha(current);
            }
        });
        mAnimator.setDuration(mPhotoView.getAnimaDuring());
        mAnimator.start();
    }
}
