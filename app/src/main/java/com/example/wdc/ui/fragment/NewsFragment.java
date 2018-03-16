package com.example.wdc.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.example.wdc.adapter.NewsListAdapter;
import com.example.wdc.bean.news.NewsBean;
import com.example.wdc.bean.news.NewsListBean;
import com.example.wdc.ms.R;
import com.example.wdc.ui.activity.NewsDetailsActivity;
import com.example.wdc.ui.fragment.base.BaseFragment;
import com.example.wdc.ui.fragment.news.NewsViewModel;
import com.example.wdc.utils.DateUtils;
import com.example.wdc.utils.PrefUtil;
import com.example.wdc.widgets.LoadMoreListener;
import com.example.wdc.widgets.SpacesItemDecoration;

import butterknife.BindView;

/**
 * Created by dechao on 2016/7/21.
 *
 * @Title: 新闻列表
 * @Description: 数据的获取展示，加载更多，列表的点击进入详情
 *
 * @Author:WangDeChao
 * @E-mail:mail2dechao@gmail.com
 */
public class NewsFragment extends BaseFragment {

    @BindView(R.id.news_recycler_view)
    protected RecyclerView mRecyclerView;

    @BindView(R.id.news_swipe_refresh_layout)
    protected SwipeRefreshLayout mRefreshLayout;

    private NewsListAdapter mNewsAdapter;
    private LinearLayoutManager manager;
    private NewsViewModel mNewsViewModel;
    private BottomSheetDialog mItemDialog;

    public static final String NEWS_DETAILS_KEY = "news";

    private Long dateNum = 0L;


    @Override
    protected void onFirstUserVisible() {
        mRefreshLayout.setRefreshing(true);
    }

    @Override
    protected void onUserVisible() {
    }

    @Override
    protected void onUserInvisible() {
    }

    @Override
    protected View getLoadingTargetView() {
        return mRefreshLayout;
    }

    @Override
    protected void initViewsAndEvents() {

        mNewsViewModel = new NewsViewModel(getActivity().getApplication());

        setUpTheme();

        initRecyclerView();

        mRecyclerView.addOnScrollListener(new LoadMoreListener() {
            @Override
            public void loadMoreData(int page, RecyclerView.ViewHolder holder) {
                if (mNewsViewModel.isCanLoad()) {
                    dateNum--;
                    mNewsViewModel.getData(DateUtils.getOtherDate(dateNum));
                }
            }
        });

        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mNewsViewModel.getData(DateUtils.getDate());
            }
        });

        mNewsViewModel.getNewsData().observe(this, new android.arch.lifecycle.Observer<NewsListBean>() {
            @Override
            public void onChanged(@Nullable NewsListBean newsBeans) {
                if (mRefreshLayout.isRefreshing()) {
                    mRefreshLayout.setRefreshing(false);
                    mNewsAdapter.getBean().clear();
                }
                mNewsAdapter.setBean(newsBeans.getStories());
                mNewsAdapter.notifyDataSetChanged();
            }
        });

        mNewsViewModel.getClickListener().observe(this, new android.arch.lifecycle.Observer<NewsBean>() {
            @Override
            public void onChanged(@Nullable NewsBean bean) {
                Bundle bundle = new Bundle();
                bundle.putParcelable(NEWS_DETAILS_KEY, bean);
                readyGo(NewsDetailsActivity.class, bundle);
            }
        });

        mNewsViewModel.getLongClickListener().observe(this, new android.arch.lifecycle.Observer<NewsBean>() {
            @Override
            public void onChanged(@Nullable NewsBean bean) {
                Toast.makeText(getActivity(), "long click", Toast.LENGTH_SHORT).show();

                initItemDialog();
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

    public static NewsFragment newInstance() {
        return new NewsFragment();
    }

    private void setUpTheme() {
        if (PrefUtil.getTheme() == R.style.DefaultTheme) {
            mRefreshLayout.setColorSchemeResources(R.color.colorBase);
        } else if (PrefUtil.getTheme() == R.style.NightTheme) {
            mRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        }
    }

    private void initRecyclerView() {
        manager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(20));
        mNewsAdapter = new NewsListAdapter(mNewsViewModel);
        mRecyclerView.setAdapter(mNewsAdapter);
    }

    private void initItemDialog(){
        if (mItemDialog == null){
            mItemDialog = new BottomSheetDialog(getActivity());
        }

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_news_item,null);
        view = View.inflate(getActivity(),R.layout.dialog_news_item,null);
        mItemDialog.setContentView(view);
        mItemDialog.show();
    }

}
