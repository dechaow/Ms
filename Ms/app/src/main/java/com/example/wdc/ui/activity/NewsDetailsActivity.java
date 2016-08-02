package com.example.wdc.ui.activity;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.wdc.bean.news.NewsBean;
import com.example.wdc.bean.news.NewsDetailsBean;
import com.example.wdc.ms.R;
import com.example.wdc.presenter.NewsDetailsPresenter;
import com.example.wdc.presenter.impl.NewsDetailsPresenterImpl;
import com.example.wdc.ui.activity.base.BaseActivity;
import com.example.wdc.ui.fragment.NewsFragment;
import com.example.wdc.utils.CommonUtils;
import com.example.wdc.utils.DensityUtil;
import com.example.wdc.utils.NetUtils;
import com.example.wdc.view.NewsDetailsView;

import butterknife.BindView;

/**
 * Created by wdc on 2016/7/25.
 */
public class NewsDetailsActivity extends BaseActivity implements NewsDetailsView{

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
    private NewsDetailsPresenter mPresenter;

    @Override
    protected void getBundleExtras(Bundle extras) {
        if (null != extras){
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
        toolbar.setBackgroundResource(android.R.color.transparent);
        toolbar.setTitle("");
        CollapsingToolbarLayout.LayoutParams params = (CollapsingToolbarLayout.LayoutParams) toolbar.getLayoutParams();
        params.height = CommonUtils.getToolbarHeight(this) + CommonUtils.getStatusBarHeight(this);
        toolbar.setPadding(0,(int)(DensityUtil.px2dip(this,CommonUtils.getStatusBarHeight(this))),0,0);
        toolbar.setLayoutParams(params);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorTitleText));
        mPresenter = new NewsDetailsPresenterImpl(this,this);
        mPresenter.loadDetailsData(bean.getId());
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
    protected boolean isBindEventBusHere() {
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
        return false;
    }


    @Override
    public void setNewsDetails(NewsDetailsBean result) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle(result.getTitle());
        Glide.with(this)
                .load(result.getImage())
                .centerCrop()
                .placeholder(R.mipmap.app_icon)
                .thumbnail(0.5f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)//磁盘缓存
                .error(R.mipmap.app_icon)
                .skipMemoryCache(false)
                .into(img);
        StringBuffer buffer = handleHtml(result.getBody());
        webView.setDrawingCacheEnabled(true);
        webView.loadDataWithBaseURL("file:///android_asset/",buffer.toString(),"text/html","utf-8",null);
    }

    /**
     *  解析html文本
     * @param body
     * @return
     */
    public static StringBuffer handleHtml(String body) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("<html><head><link rel=\"stylesheet\" type=\"text/css\" href=\"css/detail.css\" ></head>");
        stringBuffer.append("<body>");
        stringBuffer.append(body);
        stringBuffer.append("</body></html>");
        return stringBuffer;
    }
}
