package com.example.wdc.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.wdc.adapter.NewsListAdapter;
import com.example.wdc.bean.news.NewsBean;
import com.example.wdc.bean.news.NewsListBean;
import com.example.wdc.event.NewsOnItemClick;
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
    private NewsListBean listData;
    private LinearLayoutManager manager;

    public static final String NEWS_DETAILS_KEY = "news";

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
        mPresenter.loadListData(DateUtils.getDate());
        listData = new NewsListBean();
        manager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                System.out.println("recyclerView newState --------------------" + newState);
                /**
                 * newState
                 * 0 停止滑动
                 * 1 手按住滑动
                 * 2 手松开了但是还在滑动
                 */
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                /**
                 * dx
                 * dy
                 */
                System.out.println("recyclerView manager -----------------" + manager.findFirstCompletelyVisibleItemPosition() + "/////   " + manager.getChildCount());
                System.out.println("recyclerView dx = " + dx + "dy = " + dy + "  --------------------------");
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
                mPresenter.loadListData(DateUtils.getDate());
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
        listData = data;
        adapter = new NewsListAdapter(getActivity(),listData);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void addLoadMoreData(NewsListBean data) {

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
}
