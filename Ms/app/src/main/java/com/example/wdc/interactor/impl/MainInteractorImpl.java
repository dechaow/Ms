package com.example.wdc.interactor.impl;


import android.support.v4.app.Fragment;

import com.example.wdc.interactor.MainInteractor;
import com.example.wdc.ms.R;
import com.example.wdc.ui.fragment.DrawerFragment;
import com.example.wdc.ui.fragment.HomeFragment;

/**
 * Created by wdc on 2016/7/21.
 */
public class MainInteractorImpl implements MainInteractor {
    @Override
    public Fragment getFragment() {
        return new HomeFragment();
    }

    @Override
    public Fragment getDrawerFragment() {
        return new DrawerFragment();
    }

}
