package com.example.wdc.ms;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

/**
 * Created by dechao on 2018/3/15.
 *
 * @Title:
 * @Description:
 * @Author:WangDeChao
 * @E-mail:mail2dechao@gmail.com
 */
public class ImageViewDataBindings {

    @SuppressWarnings("unchecked")
    @BindingAdapter("app:imageUrl")
    public static void setImage(ImageView imageView, String url) {

        RequestBuilder<Drawable> requestBuilder = Glide
                .with(imageView.getContext())
                .load(url);

//        requestBuilder.preload(200,200);

        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.pic_loading)
                .centerCrop()
                .priority(Priority.HIGH);

        requestBuilder.apply(options);

        requestBuilder.into(imageView);
    }


    @SuppressWarnings("unchecked")
    @BindingAdapter("app:imageUrlD")
    public static void setImageD(ImageView imageView, String url) {
        //glide 4.x 使用 RequestBuilder 构建处理
        RequestBuilder<Drawable> requestBuilder = Glide.with(imageView.getContext()).load(url);

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
        requestBuilder.load(url).into(imageView);
    }
}
