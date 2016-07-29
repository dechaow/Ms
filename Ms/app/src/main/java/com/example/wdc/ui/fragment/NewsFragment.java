package com.example.wdc.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.wdc.adapter.NewsListAdapter;
import com.example.wdc.bean.news.NewsBean;
import com.example.wdc.bean.news.NewsListBean;
import com.example.wdc.event.NewsOnItemClick;
import com.example.wdc.event.ImagePushClick;
import com.example.wdc.event.NewsPushClick;
import com.example.wdc.ms.R;
import com.example.wdc.presenter.NewsPresenter;
import com.example.wdc.presenter.impl.NewsPresenterImpl;
import com.example.wdc.ui.activity.NewsDetailsActivity;
import com.example.wdc.ui.fragment.base.BaseFragment;
import com.example.wdc.utils.DateUtils;
import com.example.wdc.utils.PrefUtil;
import com.example.wdc.view.NewsView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by wdc on 2016/7/21.
 */
public class NewsFragment extends BaseFragment implements NewsView{

    @BindView(R.id.news_recycler_view)
    protected RecyclerView mRecyclerView;
    @BindView(R.id.news_swipe_refresh_layout)
    protected SwipeRefreshLayout mLayout;

    public static NewsListAdapter adapter;
    private NewsPresenter mPresenter;
    private List<NewsBean> listData;
    private LinearLayoutManager manager;

    public static final String NEWS_DETAILS_KEY = "news";

    private Boolean isScroll = false;//记录recyclerview是否在滑动
    private int listSize;
    private Long dateNum = 0L;

    @Override
    protected void onFirstUserVisible() {
    }

    @Override
    protected void onUserVisible() {
    }

    @Override
    protected void onUserInvisible() {
    }

    @Override
    protected View getLoadingTargetView() {
        return mLayout;
    }

    @Override
    protected void initViewsAndEvents() {
        mPresenter = new NewsPresenterImpl(getActivity(),this);
        mPresenter.loadListData(DateUtils.getDate(),true);
        listData = new ArrayList<>();
        manager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            /**
             * newState
             * 0 停止滑动 SCROLL_STATE_IDLE
             * 1 手按住滑动 SCROLL_STATE_DRAGGING
             * 2 手松开了但是还在滑动 SCROLL_STATE_SETTLING
             */
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                int count = manager.getChildCount() + manager.findFirstVisibleItemPosition();
                if ( (recyclerView.getScrollState() == RecyclerView.SCROLL_STATE_IDLE) &&  (count == (listSize+1)) && (listSize!=0)){
                    Snackbar.make(getActivity().getWindow().getDecorView(),"加载更多",Snackbar.LENGTH_SHORT).show();
                    dateNum--;
                    mPresenter.loadListData(DateUtils.getOtherDate(dateNum),false);
                }
            }
        });

        if (PrefUtil.getTheme() == R.style.DefaultTheme){
            mLayout.setColorSchemeResources(R.color.colorBase);
        } else if (PrefUtil.getTheme() == R.style.NightTheme) {
            mLayout.setColorSchemeResources(R.color.colorPrimary);
        }
        mLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.loadListData(DateUtils.getDate(),true);
            }
        });

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_news;
    }

    @Override
    protected boolean isBindEventBusHere() {
        return true;
    }

    //NewsView

    @Override
    public void addRefreshData(NewsListBean data) {
        mLayout.setRefreshing(false);
        listData = data.getStories();
        listSize = data.getStories().size();
        adapter = new NewsListAdapter(getActivity(),listData);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void addLoadMoreData(NewsListBean data) {
        if(data.getStories().size() == 0){
            mRecyclerView.removeViewAt(mRecyclerView.getChildCount()-1);
        }
        listData.addAll(data.getStories());
        listSize += data.getStories().size();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void ToDetails(NewsBean newsBean) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(NEWS_DETAILS_KEY,newsBean);
        readyGo(NewsDetailsActivity.class,bundle);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(NewsOnItemClick click){
        mPresenter.onItemClickListener(click.newsBean);
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onEvent(NewsPushClick click){
        mPresenter.loadListData(DateUtils.getDate(),true);
    }
}
