package com.example.wdc.ui.fragment;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.wdc.adapter.ImageListAdapter;
import com.example.wdc.bean.images.ImagesBean;
import com.example.wdc.bean.images.ImagesListBean;
import com.example.wdc.ms.R;
import com.example.wdc.presenter.ImagesPresenter;
import com.example.wdc.presenter.impl.ImagesPresenterImpl;
import com.example.wdc.ui.activity.ImageDetailActivity;
import com.example.wdc.ui.fragment.base.BaseFragment;
import com.example.wdc.ui.fragment.images.ImagesViewModel;
import com.example.wdc.ui.fragment.news.OnItemClickListener;
import com.example.wdc.utils.PrefUtil;
import com.example.wdc.utils.UrlUtils;
import com.example.wdc.view.ImagesView;
import com.example.wdc.widgets.LoadMoreListener;
import com.example.wdc.widgets.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by wdc on 2016/7/21.
 */
public class ImagesFragment extends BaseFragment implements OnItemClickListener<ImagesListBean>{

    @BindView(R.id.images_swipe_refresh_layout)
    protected SwipeRefreshLayout mReLayout;
    @BindView(R.id.images_recycler_view)
    protected RecyclerView mRecyclerView;

    private ImageListAdapter mAdapter;
    private StaggeredGridLayoutManager manager;
    private int page = 3;
    private int listSize;

    private ImagesViewModel mImagesViewModel;

    public static final String KEY_IMG_URL = "url";
    public static final String KEY_IMG_X = "x";
    public static final String KEY_IMG_Y = "y";
    public static final String KEY_IMG_WIDTH = "width";
    public static final String KEY_IMG_HEIGHT = "height";

    @Override
    protected void onFirstUserVisible() {

        setUpTheme();

        setUpRecyclerView();

    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    protected View getLoadingTargetView() {
        return mReLayout;
    }

    @Override
    protected void initViewsAndEvents() {
        mImagesViewModel = new ImagesViewModel(getActivity().getApplication());
        mImagesViewModel.setImageClick(this);

        mAdapter = new ImageListAdapter(this, mImagesViewModel, mScreenWidth);
        mRecyclerView.setAdapter(mAdapter);

        mImagesViewModel.getImagesData().observe(getActivity(), new Observer<ImagesBean>() {
            @Override
            public void onChanged(@Nullable ImagesBean imagesBean) {
                mAdapter.setImagesBean(imagesBean.getImgs());
                mAdapter.notifyDataSetChanged();
                mReLayout.setRefreshing(false);
            }
        });

        mReLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mImagesViewModel.loadData(1);
            }
        });

        mRecyclerView.addOnScrollListener(new LoadMoreListener() {
            @Override
            public void loadMoreData(int page, RecyclerView.ViewHolder holder) {
                System.out.println("ImagesFragment.loadMoreData   " + page);
                mImagesViewModel.loadData(page);
            }
        });
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_images;
    }

    @Override
    protected boolean isBindEventBusHere() {
        return true;
    }

    private void setUpTheme(){
        if (PrefUtil.getTheme() == R.style.DefaultTheme) {
            mReLayout.setColorSchemeResources(R.color.colorBase);
        } else if (PrefUtil.getTheme() == R.style.NightTheme) {
            mReLayout.setColorSchemeResources(R.color.colorPrimary);
        }
    }

    private void setUpRecyclerView(){
        manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(20));
    }

    public static ImagesFragment newInstance() {
        return new ImagesFragment();
    }

    @Override
    public void onItemClick(View v,ImagesListBean bean) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("data",bean);
        readyGo(ImageDetailActivity.class,bundle);
    }
}
