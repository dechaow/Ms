package com.example.wdc.view;

import com.example.wdc.bean.news.NewsBean;
import com.example.wdc.bean.news.NewsListBean;
import com.example.wdc.view.base.BaseView;

/**
 * Created by wdc on 2016/7/22.
 */
public interface NewsView extends BaseView{

    void addRefreshData(NewsListBean data);

    void addLoadMoreData(NewsListBean data);

    void ToDetails(NewsBean newsBean);

}
