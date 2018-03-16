package com.example.wdc.adapter;

import android.databinding.DataBindingUtil;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.wdc.bean.images.ImagesListBean;
import com.example.wdc.event.ImageOnClick;
import com.example.wdc.ms.R;
import com.example.wdc.ms.databinding.ItemImagesBinding;
import com.example.wdc.ui.fragment.images.ImagesViewModel;
import com.example.wdc.utils.CommonUtils;
import com.example.wdc.utils.NetUtils;
import com.example.wdc.widgets.MateialProgressBar;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by wdc on 2016/7/26.
 */
public class ImageListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Fragment context;
    private ArrayList<ImagesListBean> imagesBean;
    private int mScreenWidth;
    private ImagesViewModel mViewModel;

    private final int ITEM_TYPE = 1;
    private final int FOOTER_TYPE = 0;

    public ArrayList<ImagesListBean> getImagesBean() {
        return imagesBean;
    }

    public void setImagesBean(List<ImagesListBean> imagesBean) {
        this.imagesBean.clear();
        this.imagesBean.addAll(imagesBean);
    }

    public void addImageBean(List<ImagesListBean> imagesBean){
        this.imagesBean.addAll(imagesBean);
    }

    public ImageListAdapter(Fragment context, ImagesViewModel model, int mScreenWidth) {
        this.context = context;
        this.imagesBean = new ArrayList<>();
        this.mViewModel = model;
        this.mScreenWidth = mScreenWidth;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ITEM_TYPE:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_images,parent, false);
                ItemImagesBinding binding = DataBindingUtil.bind(view);
                ImagesViewHolder holder = new ImagesViewHolder(view,binding);
                return holder;
            case FOOTER_TYPE:
                View footerView = LayoutInflater.from(context.getActivity()).inflate(R.layout.view_loading, parent,false);
                return new FooterViewHolder(footerView);
            default:
                throw new NullPointerException("can't find viewType");
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ImagesViewHolder) {

            ImagesViewHolder viewHolder = (ImagesViewHolder) holder;

            ItemImagesBinding binding = viewHolder.binding;

            binding.setBean(imagesBean.get(position));

            binding.setModel(mViewModel);

        }
    }

    @Override
    public int getItemCount() {
        return imagesBean.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == (imagesBean.size() - 1)) {
            return FOOTER_TYPE;
        } else {
            return ITEM_TYPE;
        }
    }


    class ImagesViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        ItemImagesBinding binding;

        public ImagesViewHolder(View itemView,ItemImagesBinding binding) {
            super(itemView);
            this.binding = binding;
            img = (ImageView) itemView.findViewById(R.id.imagelist_img);
        }
    }

    class FooterViewHolder extends RecyclerView.ViewHolder {

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

        if (holder instanceof FooterViewHolder) {
            ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();

            if (lp != null
                    && lp instanceof StaggeredGridLayoutManager.LayoutParams) {

                StaggeredGridLayoutManager.LayoutParams p =
                        (StaggeredGridLayoutManager.LayoutParams) lp;

                p.setFullSpan(true);
            }
        }
    }
}
