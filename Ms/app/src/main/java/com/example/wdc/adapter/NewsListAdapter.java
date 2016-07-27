package com.example.wdc.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.wdc.bean.news.NewsBean;
import com.example.wdc.bean.news.NewsListBean;
import com.example.wdc.event.NewsOnItemClick;
import com.example.wdc.ms.R;
import com.example.wdc.widgets.GlideRoundTransform;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by wdc on 2016/7/25.
 */
public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.ViewHolder> {

    private Context context;
    private NewsListBean bean;

    public NewsListAdapter(Context context, NewsListBean bean) {
        this.context = context;
        this.bean = bean;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_newslist,null);
        ViewHolder holder = new ViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
            List<String> imgs = bean.getStories().get(position).getImages();
            if (imgs.size() > 0){
                Glide.with(context)
                        .load(imgs.get(0))
                        .centerCrop()
                        .placeholder(R.mipmap.app_icon)
                        .thumbnail(0.5f)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)//磁盘缓存
                        .error(R.mipmap.app_icon)
                        .skipMemoryCache(false)
                        .transform(new GlideRoundTransform(context,5))
                        .into(holder.img);
                holder.title.setText(bean.getStories().get(position).getTitle());
            }else{
                System.out.println("没有图片");
            }
        final NewsBean newsBean = bean.getStories().get(position);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new NewsOnItemClick(newsBean));
            }
        });
    }

    @Override
    public int getItemCount() {
        return bean.getStories() != null ? bean.getStories().size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        ImageView img;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.newslist_title);
            img = (ImageView) itemView.findViewById(R.id.newslist_img);
            cardView = (CardView) itemView.findViewById(R.id.newslist_cardview);
        }
    }
}
