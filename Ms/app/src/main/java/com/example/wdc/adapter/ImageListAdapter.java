package com.example.wdc.adapter;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.wdc.bean.images.ImagesBean;
import com.example.wdc.event.ImageOnClick;
import com.example.wdc.ms.R;
import com.example.wdc.utils.CommonUtils;
import com.example.wdc.widgets.GlideRoundTransform;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by wdc on 2016/7/26.
 */
public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.ImagesViewHolder> {

    private Fragment context;
    private ImagesBean imagesBean;
    private int mScreenWidth;

    public ImageListAdapter(Fragment context, ImagesBean imagesBean,int mScreenWidth) {
        this.context = context;
        this.imagesBean = imagesBean;
        this.mScreenWidth =  mScreenWidth;
    }


    @Override
    public ImagesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context.getActivity()).inflate(R.layout.item_images,null);
        ImagesViewHolder holder = new ImagesViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ImagesViewHolder holder, final int position){
            String url = imagesBean.getImgs().get(position).getThumbnailUrl();
            int width = imagesBean.getImgs().get(position).getThumbnailWidth();
            int height = imagesBean.getImgs().get(position).getThumbnailHeight();
            Glide.with(context)
                    .load(url)
                    .centerCrop()
                    .placeholder(R.mipmap.app_icon)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)//磁盘缓存
                    .error(R.mipmap.app_icon)
                    .skipMemoryCache(false)
                    .into(holder.img);
        RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (width != 0){
            params.height = mScreenWidth/width*height;
        }
        holder.img.setLayoutParams(params);
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int statusBarHeight = CommonUtils.getStatusBarHeight(context.getActivity());
                int[] location = new int[2];
                v.getLocationOnScreen(location);
                location[1] += statusBarHeight;
                int width = v.getWidth();
                int height = v.getHeight();
                if (position < imagesBean.getImgs().size()){
                    EventBus.getDefault().post(new ImageOnClick(imagesBean.getImgs().get(position),location[0],location[1],width,height));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return imagesBean.getImgs().size()-1;
    }

    class ImagesViewHolder extends RecyclerView.ViewHolder{

        ImageView img;

        public ImagesViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.imagelist_img);
        }
    }
}
