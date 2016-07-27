package com.example.wdc.event;

import com.example.wdc.bean.news.NewsBean;

/**
 * Created by wdc on 2016/7/25.
 */
public class NewsOnItemClick {
    public NewsBean newsBean;

    public NewsOnItemClick(NewsBean newsBean) {
        this.newsBean = newsBean;
    }
}
