package com.example.wdc.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.wdc.bean.news.NewsBean;
import com.example.wdc.event.NewsOnItemClick;
import com.example.wdc.ms.R;
import com.example.wdc.widgets.GlideRoundTransform;
import com.example.wdc.widgets.MateialProgressBar;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by wdc on 2016/7/25.
 */
public class NewsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<NewsBean> bean;
    private final int FOOTER = 1;
    private final int ITEM = 0;

    public NewsListAdapter(Context context, List<NewsBean> bean) {
        this.context = context;
        this.bean = bean;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType){
            case ITEM:
                View itemView = LayoutInflater.from(context).inflate(R.layout.item_newslist,null);
                ItemViewHolder holder = new ItemViewHolder(itemView);
                return holder;
            case FOOTER:
                View footerView = LayoutInflater.from(context).inflate(R.layout.view_loading,null);
                return new FooterViewHolder(footerView);
            default:
                throw new NullPointerException("can't find itemType");
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder){
            List<String> imgs = bean.get(position).getImages();
            if (imgs.size() > 0){
                Glide.with(context)
                        .load(imgs.get(0))
                        .centerCrop()
                        .placeholder(R.mipmap.app_icon)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)//磁盘缓存
                        .error(R.mipmap.app_icon)
                        .skipMemoryCache(false)
                        .transform(new GlideRoundTransform(context,5))
                        .into(((ItemViewHolder) holder).img);
                ((ItemViewHolder) holder).title.setText(bean.get(position).getTitle());
            }else{
               throw new NullPointerException("img size = 0");
            }
            final NewsBean newsBean = bean.get(position);
            ((ItemViewHolder) holder).cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBus.getDefault().post(new NewsOnItemClick(newsBean));
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return bean != null ? (bean.size()+1) : 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == (bean.size())){
            return FOOTER;
        }else{
            return ITEM;
        }

    }

    class ItemViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        ImageView img;
        CardView cardView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.newslist_title);
            img = (ImageView) itemView.findViewById(R.id.newslist_img);
            cardView = (CardView) itemView.findViewById(R.id.newslist_cardview);
        }
    }

    class FooterViewHolder extends RecyclerView.ViewHolder{

        MateialProgressBar loadingPro;

        public FooterViewHolder(View itemView) {
            super(itemView);
            //这里不操作的话，progressbar就会一直显示在左边，不知道咋回事，只好重新设置一下了。。。
            RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            itemView.setLayoutParams(params);
            loadingPro = (MateialProgressBar) itemView.findViewById(R.id.loading_pro);
        }
    }
}
