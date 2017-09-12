package com.example.wdc.view;

import android.support.v4.app.Fragment;

/**
 * Created by wdc on 2016/7/21.
 * mainactivity 只需要展示出来mainfragment
 */
public interface MainView {

    void showFragment(Fragment fragment);

    void addDrawerFragment(Fragment fragment);

}
