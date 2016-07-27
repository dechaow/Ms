package com.example.wdc.presenter;

import com.example.wdc.bean.news.NewsBean;

/**
 * Created by wdc on 2016/7/25.
 */
public interface NewsPresenter{

    void onItemClickListener(NewsBean newsBean);

    void loadListData(String date);

}
