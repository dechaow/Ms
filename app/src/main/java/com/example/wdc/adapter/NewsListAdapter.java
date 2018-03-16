package com.example.wdc.adapter;

import android.databinding.DataBindingUtil;
import android.os.HandlerThread;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.wdc.bean.news.NewsBean;
import com.example.wdc.ms.R;
import com.example.wdc.ms.databinding.ItemNewslistBinding;
import com.example.wdc.ui.fragment.news.NewsViewModel;
import com.example.wdc.widgets.MateialProgressBar;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

/**
 * Created by wdc on 2016/7/25.
 */
public class NewsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //    private Activity context;
    private ArrayList<NewsBean> bean = new ArrayList<>();
    private final int FOOTER = 1;
    private final int ITEM = 0;

    private NewsViewModel mNewsViewModel;

    public List<NewsBean> getBean() {
        return bean;
    }

    public void setBean(List<NewsBean> bean) {
        if (getItemCount() == 0) {
            this.bean = (ArrayList<NewsBean>) bean;
        } else {
            this.bean.addAll(bean);
        }
    }

    //所有的数据操作都从 viewModel中来取
    public NewsListAdapter(NewsViewModel newsViewModel) {
        this.mNewsViewModel = newsViewModel;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case ITEM:
                LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

                View itemView = layoutInflater.inflate(R.layout.item_newslist, parent, false);
                ItemNewslistBinding binding = DataBindingUtil.bind(itemView);
                ItemViewHolder holder = new ItemViewHolder(itemView, binding);
                return holder;
            case FOOTER:
                View footerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_loading, parent, false);
                return new FooterViewHolder(footerView);
            default:
                throw new NullPointerException("can't find itemType");
        }

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ItemViewHolder) {

            final ItemViewHolder itemViewHolder = (ItemViewHolder) holder;

            final NewsBean newsBean = bean.get(position);

            final List<String> imgs = newsBean.getImages();
            itemViewHolder.mBinding.setNews(newsBean);

            itemViewHolder.mBinding.setModel(mNewsViewModel);

        }

    }

    @Override
    public int getItemCount() {
        return bean != null ? (bean.size() + 1) : 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == (bean.size())) {
            return FOOTER;
        } else {
            return ITEM;
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        ImageView img;
        CardView cardView;
        ItemNewslistBinding mBinding;

        public ItemViewHolder(View itemView, ItemNewslistBinding binding) {
            super(itemView);
            mBinding = binding;
            title =  itemView.findViewById(R.id.newslist_title);
            img =  itemView.findViewById(R.id.newslist_img);
            cardView = itemView.findViewById(R.id.newslist_cardview);
        }
    }

    class FooterViewHolder extends RecyclerView.ViewHolder {

        MateialProgressBar loadingPro;

        public FooterViewHolder(View itemView) {
            super(itemView);

            //这里不操作的话，progressbar就会一直显示在左边，只好重新设置一下了。。。
            RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            itemView.setLayoutParams(params);
            loadingPro = itemView.findViewById(R.id.loading_pro);
        }
    }
}
