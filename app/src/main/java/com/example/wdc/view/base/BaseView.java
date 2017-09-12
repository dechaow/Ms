package com.example.wdc.view.base;

/**
 * Created by wdc on 2016/7/20.
 *
 * baseview
 *
 */
public interface BaseView {

    void showLoading(String msg);

    void hideLoading();

    void showErro(String msg);

    void showException(String msg);

    void showNetErro();


}
