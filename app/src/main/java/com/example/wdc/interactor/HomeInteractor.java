package com.example.wdc.interactor;


import android.content.Context;
import android.support.v4.app.Fragment;

import java.util.List;

/**
 * Created by wdc on 2016/7/21.
 */
public interface HomeInteractor {

    List<Fragment> getViewPagerData();

    String[] getItemData(Context context);
}
