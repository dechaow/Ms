package com.example.wdc.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;

import com.example.wdc.adapter.ImageListAdapter;
import com.example.wdc.bean.images.ImagesBean;
import com.example.wdc.bean.images.ImagesListBean;
import com.example.wdc.event.ImageOnClick;
import com.example.wdc.event.ImagePushClick;
import com.example.wdc.event.ImagesStartRefresh;
import com.example.wdc.event.ImagesStopRefresh;
import com.example.wdc.event.NewsStartRefresh;
import com.example.wdc.event.NewsStopRefresh;
import com.example.wdc.ms.R;
import com.example.wdc.presenter.ImagesPresenter;
import com.example.wdc.presenter.impl.ImagesPresenterImpl;
import com.example.wdc.ui.activity.ImageDetailActivity;
import com.example.wdc.ui.fragment.base.BaseFragment;
import com.example.wdc.utils.PrefUtil;
import com.example.wdc.utils.UrlUtils;
import com.example.wdc.view.ImagesView;
import com.example.wdc.widgets.SpacesItemDecoration;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by wdc on 2016/7/21.
 */
public class ImagesFragment extends BaseFragment implements ImagesView{

    @BindView(R.id.images_swipe_refresh_layout)
    protected SwipeRefreshLayout mReLayout;
    @BindView(R.id.images_recycler_view)
    protected RecyclerView mRecyclerView;

    private ImagesPresenter mPresenter;
    private ImageListAdapter mAdapter;
    private List<ImagesListBean> mImagesBean;
    private StaggeredGridLayoutManager manager;
    private int page = 3;
    private int listSize;

    public static final String KEY_IMG_URL = "url";
    public static final String KEY_IMG_X = "x";
    public static final String KEY_IMG_Y = "y";
    public static final String KEY_IMG_WIDTH = "width";
    public static final String KEY_IMG_HEIGHT = "height";

    @Override
    protected void onFirstUserVisible() {
        manager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(20));
        mPresenter = new ImagesPresenterImpl(getActivity(),this);
        try {
            mReLayout.setRefreshing(true);
            mPresenter.loadImages(UrlUtils.IMAGES_URL_COL,UrlUtils.IMAGES_URL_TAG,0, UrlUtils.IMAGES_URL_RN,UrlUtils.IMAGES_URL_FROM,true);
        }catch (Exception e){
            Log.d("load image",e.getMessage());
        }
        if (PrefUtil.getTheme() == R.style.DefaultTheme){
            mReLayout.setColorSchemeResources(R.color.colorBase);
        } else if (PrefUtil.getTheme() == R.style.NightTheme) {
            mReLayout.setColorSchemeResources(R.color.colorPrimary);
        }
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
        mImagesBean = new ArrayList<>();
        mAdapter = new ImageListAdapter(this,mImagesBean,mScreenWidth);
        mRecyclerView.setAdapter(mAdapter);
        mReLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    mPresenter.loadImages(UrlUtils.IMAGES_URL_COL,UrlUtils.IMAGES_URL_TAG,page, UrlUtils.IMAGES_URL_RN,UrlUtils.IMAGES_URL_FROM,true);
                }catch (Exception e){
                    Log.d("load image",e.getMessage());
                }
            }
        });
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                int count = findMax(manager.findLastVisibleItemPositions(new int[manager.getSpanCount()]));
                if ( (recyclerView.getScrollState() == RecyclerView.SCROLL_STATE_IDLE) &&  (count == (listSize-1)) && (listSize!=0)){
                    Snackbar.make(getActivity().getCurrentFocus(),"加载更多",Snackbar.LENGTH_SHORT).show();
                    page ++;
                    mPresenter.loadImages(UrlUtils.IMAGES_URL_COL,UrlUtils.IMAGES_URL_TAG,page, UrlUtils.IMAGES_URL_RN,UrlUtils.IMAGES_URL_FROM,false);
                }
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

    @Override
    public void showImagesData(ImagesBean bean) {
        mImagesBean.clear();
        mImagesBean.addAll(bean.getImgs());
        listSize = mImagesBean.size();
        mReLayout.setRefreshing(false);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showMoreImagesData(ImagesBean bean) {
        mImagesBean.addAll(bean.getImgs());
        mAdapter.notifyDataSetChanged();
        listSize = mImagesBean.size();
    }

    @Override
    public void ToDetails(ImagesListBean images, int x, int y, int width, int height) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_IMG_URL,images.getThumbnailUrl());
        bundle.putInt(KEY_IMG_X,x);
        bundle.putInt(KEY_IMG_Y,y);
        bundle.putInt(KEY_IMG_WIDTH,width);
        bundle.putInt(KEY_IMG_HEIGHT,height);
        readyGo(ImageDetailActivity.class,bundle);
        getActivity().overridePendingTransition(0, 0);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ImagesStopRefresh refresh){
        if (mReLayout != null){
            mReLayout.setRefreshing(false);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ImagesStartRefresh refresh){
        if (mReLayout != null){
            mReLayout.setRefreshing(true);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ImageOnClick click){
        mPresenter.onItemClickListener(click.listBean,click.x,click.y,click.width,click.height);
    }
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onEvent(ImagePushClick click){
        mReLayout.setRefreshing(true);
        mPresenter.loadImages(UrlUtils.IMAGES_URL_COL,UrlUtils.IMAGES_URL_TAG,page, UrlUtils.IMAGES_URL_RN,UrlUtils.IMAGES_URL_FROM,true);
    }

    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }
    public static ImagesFragment newInstance(){
        return new ImagesFragment();
    }
}
