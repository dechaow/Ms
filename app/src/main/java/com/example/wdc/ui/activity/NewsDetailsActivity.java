package com.example.wdc.ui.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.wdc.bean.news.NewsBean;
import com.example.wdc.bean.news.NewsDetailsBean;
import com.example.wdc.ms.R;
import com.example.wdc.presenter.NewsDetailsPresenter;
import com.example.wdc.presenter.impl.NewsDetailsPresenterImpl;
import com.example.wdc.ui.activity.base.BaseActivity;
import com.example.wdc.ui.fragment.NewsFragment;
import com.example.wdc.utils.NetUtils;
import com.example.wdc.view.NewsDetailsView;

import butterknife.BindView;

/**
 * Created by wdc on 2016/7/25.
 */
public class NewsDetailsActivity extends BaseActivity implements NewsDetailsView {

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
        toolbar.setBackgroundResource(android.R.color.transparent);
        toolbar.setTitle("");

        //view充满状态栏
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().setStatusBarColor(Color.TRANSPARENT);
            int id = getResources().getIdentifier("status_bar_height","dimen","android");
            if (id > 0){
                int height = getResources().getDimensionPixelSize(id);
                toolbar.setPadding(0,height,0,0);
            }
        }

//        setSupportActionBar(toolbar);
//
//        AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) toolbarLayout.getLayoutParams();
//        params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL| AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED);
//        toolbarLayout.setLayoutParams(params);

//        toolbar.getLayoutParams().height = AppBarLayout.LayoutParams.WRAP_CONTENT;

        toolbar.setTitleTextColor(getResources().getColor(R.color.colorTitleText));
        mPresenter = new NewsDetailsPresenterImpl(this, this);
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


    @Override
    public void setNewsDetails(NewsDetailsBean result) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle(result.getTitle());

        Glide.with(this)
                .load(result.getImage())
                .thumbnail(0.5f)
                .into(img);

        StringBuffer buffer = handleHtml(result.getBody());

        webView.setDrawingCacheEnabled(true);

        WebSettings settings = webView.getSettings();
        //允许js代码
        settings.setJavaScriptEnabled(true);
        //自动加载图片
        settings.setLoadsImagesAutomatically(true);
        //禁用文字缩放
        settings.setTextZoom(100);

        //        settings.setCacheMode(WebSettings.LOAD_CACHE_ONLY);


//        webView.getSettings().setBlockNetworkImage(true);
//        webView.getSettings().setLoadsImagesAutomatically(isLoadImg);
        webView.loadDataWithBaseURL("file:///android_asset/", buffer.toString(), "text/html", "utf-8", null);
//        webView.setWebViewClient(client);
    }

    private boolean isLoadImg = false;

    WebViewClient client = new WebViewClient() {
        @Override
        public void onPageFinished(WebView view, String url) {

        }

        @Override
        public void onLoadResource(WebView view, String url) {

        }
    };

    /**
     * 解析html文本
     *
     * @param body
     * @return
     */
    public static StringBuffer handleHtml(String body) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("<html><head><link rel=\"stylesheet\" type=\"text/css\" href=\"css/detail.css\" ><style type=\"text/css\">.content-image{width:100%;height:240px}</style></head>");
        stringBuffer.append("<body>");
        stringBuffer.append(body);
        stringBuffer.append("</body></html>");
        return stringBuffer;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("NewsDetailsActivity.onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("NewsDetailsActivity.onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("NewsDetailsActivity.onResume");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("NewsDetailsActivity.onDestroy");
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        System.out.println("NewsDetailsActivity.onSaveInstanceState");
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("NewsDetailsActivity.onPause");
    }

}
