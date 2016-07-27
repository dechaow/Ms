package com.example.wdc.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by wdc on 2016/7/21.
 */
public class HomePagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> list;
    private String[] itemTxt;
    public HomePagerAdapter(FragmentManager fm,List<Fragment> list,String[] itemTxt) {
        super(fm);
        this.list = list;
        this.itemTxt = itemTxt;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return itemTxt[position];
    }
}
