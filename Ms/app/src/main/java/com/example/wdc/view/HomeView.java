package com.example.wdc.view;

import android.support.v4.app.Fragment;

import java.util.List;

/**
 * Created by wdc on 2016/7/21.
 */
public interface HomeView {
    void addViewPagerData(List<Fragment> list,String[] itemTxt);
}
