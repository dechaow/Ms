package com.example.wdc.ui.fragment.news;

import android.view.View;

import com.example.wdc.bean.news.NewsBean;

/**
 * Created by dechao on 2018/2/9.
 *
 * @Title:
 * @Description:
 * @Author:WangDeChao
 * @E-mail:mail2dechao@gmail.com
 */

public interface OnItemClickListener<T> {
    void onItemClick(View view,T bean);
}
