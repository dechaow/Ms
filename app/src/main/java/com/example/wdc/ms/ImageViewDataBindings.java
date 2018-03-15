package com.example.wdc.ms;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.RequestOptions;
import com.example.wdc.bean.images.ImagesListBean;

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
    public static void setImage(ImageView imageView,String url){

        RequestBuilder<Drawable> requestBuilder = Glide
                .with(imageView.getContext())
                .load(url);

//        requestBuilder.preload(200,200);

        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.pic_loading)
                .priority(Priority.HIGH);

        requestBuilder.apply(options);

        requestBuilder.thumbnail(0.5f).into(imageView);
    }
}
