package com.example.wdc.ui.activity;

import android.arch.lifecycle.Observer;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import com.example.wdc.bean.news.NewsBean;
import com.example.wdc.bean.news.NewsDetailsBean;
import com.example.wdc.ms.R;
import com.example.wdc.ms.databinding.ActivityNewsdetailsBinding;
import com.example.wdc.ui.activity.base.BaseActivity;
import com.example.wdc.ui.activity.newsdetail.NewsDetailViewModel;
import com.example.wdc.ui.fragment.NewsFragment;
import com.example.wdc.utils.NetUtils;

import butterknife.BindView;

/**
 * Created by wdc on 2016/7/25.
 */
public class NewsDetailsActivity extends BaseActivity {

    @BindView(R.id.details_collapsingToolbarLayout)
    protected CollapsingToolbarLayout toolbarLayout;
    @BindView(R.id.details_img)
    protected ImageView img;
    @BindView(R.id.details_nested_view)
    protected NestedScrollView nestedScrollView;
    @BindView(R.id.details_webview)
    protected WebView webView;
    @BindView(R.id.common_toolbar)
    protected Toolbar toolbar;

    private NewsBean bean;

    private NewsDetailViewModel mViewModel;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.onBackPressed();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        if (null != extras) {
            bean = extras.getParcelable(NewsFragment.NEWS_DETAILS_KEY);
        }
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_newsdetails;
    }

    @Override
    protected View getLoadingTargetView() {
        return webView;
    }

    @Override
    protected void initViewsAndEvents() {

        mViewModel = new NewsDetailViewModel(getApplication());
        mViewModel.getDetailBean(bean.getId());

        final ActivityNewsdetailsBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_newsdetails);

        toolbar.setBackgroundResource(android.R.color.transparent);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorTitleText));
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //view充满状态栏
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.TRANSPARENT);
            int id = getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (id > 0) {
                int height = getResources().getDimensionPixelSize(id);
                toolbar.setPadding(0, height, 0, 0);
            }
        }

        mViewModel.getNewsData().observe(this, new Observer<NewsDetailsBean>() {
            @Override
            public void onChanged(@Nullable NewsDetailsBean newsDetailsBean) {
                binding.setModel(newsDetailsBean);
            }
        });

    }

    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return false;
    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return true;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return TransitionMode.RIGHT;
    }

    @Override
    protected Boolean isAddStatusHeight() {
        return true;
    }

}
