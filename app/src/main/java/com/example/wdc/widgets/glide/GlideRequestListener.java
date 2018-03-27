package com.example.wdc.widgets.glide;

import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

/**
 * Created by dechao on 2018/3/26.
 *
 * @Title:
 * @Description:
 * @Author:WangDeChao
 * @E-mail:mail2dechao@gmail.com
 */

public interface GlideRequestListener extends RequestListener<Drawable> {

    /**
     *
     * @param progress 进度
     * @param isDone 是否加载完成
     */
    void onProgress(int progress,boolean isDone);

    void update(long bytesRead, long contentLength, boolean done);

}
