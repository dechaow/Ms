package com.example.wdc.interactor.impl;



import android.content.Context;
import android.support.v4.app.Fragment;

import com.example.wdc.interactor.HomeInteractor;
import com.example.wdc.ms.R;
import com.example.wdc.ui.fragment.ImagesFragment;
import com.example.wdc.ui.fragment.NewsFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wdc on 2016/7/21.
 */
public class HomeInteractorImpl implements HomeInteractor{
    @Override
    public List<Fragment> getViewPagerData() {
        List<Fragment> mList = new ArrayList<>();
        mList.add(new NewsFragment());
        mList.add(new ImagesFragment());
        return mList;
    }

    @Override
    public String[] getItemData(Context context) {
        String[] itemData = context.getResources().getStringArray(R.array.tabItemData);
        return itemData;
    }
}
