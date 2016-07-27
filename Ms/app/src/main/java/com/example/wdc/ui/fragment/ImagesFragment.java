package com.example.wdc.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;

import com.example.wdc.adapter.ImageListAdapter;
import com.example.wdc.bean.images.ImagesBean;
import com.example.wdc.bean.images.ImagesListBean;
import com.example.wdc.event.ImageOnClick;
import com.example.wdc.ms.R;
import com.example.wdc.presenter.ImagesPresenter;
import com.example.wdc.presenter.impl.ImagesPresenterImpl;
import com.example.wdc.ui.activity.ImageDetailActivity;
import com.example.wdc.ui.fragment.base.BaseFragment;
import com.example.wdc.utils.CommonUtils;
import com.example.wdc.utils.PrefUtil;
import com.example.wdc.utils.UrlUtils;
import com.example.wdc.view.ImagesView;
import com.example.wdc.widgets.SpacesItemDecoration;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.net.URLEncoder;

import butterknife.BindView;

/**
 * Created by wdc on 2016/7/21.
 */
public class ImagesFragment extends BaseFragment implements ImagesView {

    @BindView(R.id.images_swipe_refresh_layout)
    protected SwipeRefreshLayout mReLayout;
    @BindView(R.id.images_recycler_view)
    protected RecyclerView mRecyclerView;

    private ImagesPresenter mPresenter;
    private ImageListAdapter mAdapter;
    private ImagesBean mImagesBean;
    int page = 3;

    public static final String KEY_IMG_URL = "url";
    public static final String KEY_IMG_X = "x";
    public static final String KEY_IMG_Y = "y";
    public static final String KEY_IMG_WIDTH = "width";
    public static final String KEY_IMG_HEIGHT = "height";

    @Override
    protected void onFirstUserVisible() {
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(5));
        mPresenter = new ImagesPresenterImpl(getActivity(),this);
        try {
            mPresenter.loadImages(UrlUtils.IMAGES_URL_COL,UrlUtils.IMAGES_URL_TAG,0, UrlUtils.IMAGES_URL_RN,UrlUtils.IMAGES_URL_FROM);
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
        mReLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    page ++;
                    mPresenter.loadImages(UrlUtils.IMAGES_URL_COL,UrlUtils.IMAGES_URL_TAG,page, UrlUtils.IMAGES_URL_RN,UrlUtils.IMAGES_URL_FROM);
                }catch (Exception e){
                    Log.d("load image",e.getMessage());
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
        Boolean isFrist = true;
        if (mAdapter != null){
            isFrist = false;
        }
        mImagesBean = bean;
        mReLayout.setRefreshing(false);
        mAdapter = new ImageListAdapter(this,mImagesBean,mScreenWidth);
        if (!isFrist){
            mAdapter = new ImageListAdapter(this,mImagesBean,mScreenWidth);
            mAdapter.notifyDataSetChanged();
        }else{
            mRecyclerView.setAdapter(mAdapter);
        }

    }

    @Override
    public void showMoreImagesData(ImagesBean bean) {

    }

    @Override
    public void ToDetails(ImagesListBean images, int x, int y, int width, int height) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_IMG_URL,images.getImageUrl());
        bundle.putInt(KEY_IMG_X,x);
        bundle.putInt(KEY_IMG_Y,y);
        bundle.putInt(KEY_IMG_WIDTH,width);
        bundle.putInt(KEY_IMG_HEIGHT,height);
        readyGo(ImageDetailActivity.class,bundle);
        getActivity().overridePendingTransition(0, 0);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ImageOnClick click){
        mPresenter.onItemClickListener(click.listBean,click.x,click.y,click.width,click.height);
    }

}
