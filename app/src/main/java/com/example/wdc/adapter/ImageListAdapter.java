package com.example.wdc.adapter;

import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.wdc.bean.images.ImagesBean;
import com.example.wdc.bean.images.ImagesListBean;
import com.example.wdc.event.ImageOnClick;
import com.example.wdc.ms.R;
import com.example.wdc.utils.CommonUtils;
import com.example.wdc.utils.NetUtils;
import com.example.wdc.widgets.MateialProgressBar;
import com.example.wdc.widgets.SmoothImageView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import uk.co.senab.photoview.PhotoView;

/**
 * Created by wdc on 2016/7/26.
 */
public class ImageListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Fragment context;
    private List<ImagesListBean> imagesBean;
    private int mScreenWidth;

    private final int ITEM_TYPE = 1;
    private final int FOOTER_TYPE = 0;

    public ImageListAdapter(Fragment context, List<ImagesListBean> imagesBean,int mScreenWidth) {
        this.context = context;
        this.imagesBean = imagesBean;
        this.mScreenWidth =  mScreenWidth;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case ITEM_TYPE:
                View view = LayoutInflater.from(context.getActivity()).inflate(R.layout.item_images,null);
                ImagesViewHolder holder = new ImagesViewHolder(view);
                return holder;
            case FOOTER_TYPE:
                View footerView = LayoutInflater.from(context.getActivity()).inflate(R.layout.view_loading,null);
                return new FooterViewHolder(footerView);
            default:
                throw new NullPointerException("can't find viewType");
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position){
            if (holder instanceof ImagesViewHolder){
                String url = imagesBean.get(position).getThumbnailUrl();
                int width = imagesBean.get(position).getThumbnailWidth();
                int height = imagesBean.get(position).getThumbnailHeight();
                Glide.with(context)
                        .load(url)
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)//磁盘缓存
                        .error(R.mipmap.app_icon)
                        .skipMemoryCache(false)
                        .into(((ImagesViewHolder)holder).img);
                CardView.LayoutParams params = new CardView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                if (width != 0){
                    params.height = mScreenWidth/width*height;
                }
                ((ImagesViewHolder)holder).img.setLayoutParams(params);
                ((ImagesViewHolder)holder).itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int statusBarHeight = CommonUtils.getStatusBarHeight(context.getActivity());
                        int[] location = new int[2];
                        v.getLocationOnScreen(location);
//                        location[1] += statusBarHeight;
                        int width = v.getWidth();
                        int height = v.getHeight();
                        if (position < imagesBean.size() && (imagesBean.get(position).getThumbnailUrl() != null) && NetUtils.isNetworkConnected(context.getContext())){
                            EventBus.getDefault().post(new ImageOnClick(imagesBean.get(position),location[0],location[1],width,height));
                        }

                    }
                });
            }
    }

    @Override
    public int getItemCount() {
        return imagesBean.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == (imagesBean.size()-1)){
            return FOOTER_TYPE;
        }else{
            return ITEM_TYPE;
        }
    }


    class ImagesViewHolder extends RecyclerView.ViewHolder{

        ImageView img;

        public ImagesViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.imagelist_img);
        }
    }

    class FooterViewHolder extends RecyclerView.ViewHolder{

        MateialProgressBar progressBar;

        public FooterViewHolder(View itemView) {
            super(itemView);
            RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            itemView.setLayoutParams(params);
            progressBar = (MateialProgressBar) itemView.findViewById(R.id.loading_pro);
        }
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {

        if (holder instanceof FooterViewHolder){
            ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();

            if (lp != null
                    && lp instanceof StaggeredGridLayoutManager.LayoutParams)
            {

                StaggeredGridLayoutManager.LayoutParams p =
                        (StaggeredGridLayoutManager.LayoutParams) lp;

                p.setFullSpan(true);
            }
        }
    }
}
