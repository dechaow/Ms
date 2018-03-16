package com.example.wdc.ui.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.wdc.adapter.HomePagerAdapter;
import com.example.wdc.ms.R;
import com.example.wdc.presenter.Presenter;
import com.example.wdc.presenter.impl.HomePresenterImpl;
import com.example.wdc.ui.fragment.base.BaseFragment;
import com.example.wdc.view.HomeView;

import java.util.List;

import butterknife.BindView;

/**
 * Created by wdc on 2016/7/21.
 */
public class HomeFragment extends BaseFragment implements HomeView{

    @BindView(R.id.common_toolbar)
    protected Toolbar toolbar;
    @BindView(R.id.home_viewpager)
    protected ViewPager mHomeViewPager;
    @BindView(R.id.home_tablayout)
    protected TabLayout mHomeTabLayout;
//    @BindView(R.id.home_tabitem_left)
//    protected TabItem mItemLeft;
//    @BindView(R.id.home_tabitem_right)
//    protected TabItem mItemRight;

    private HomePagerAdapter mHomePagerAdapter;
    private Presenter mPresenter;
    @Override
    protected void onFirstUserVisible() {

    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void initViewsAndEvents() {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getDelegate().getSupportActionBar().setDisplayShowCustomEnabled(true);
        activity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        activity.getDelegate().getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mPresenter = new HomePresenterImpl(getActivity(),this);
        mPresenter.initialized();

        toolbar.getChildAt(0).animate().scaleX(1.2f).scaleY(1.2f).setDuration(800);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_home;
    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }


    @Override
    public void addViewPagerData(List<Fragment> list,String[] itemTxt) {

        mHomePagerAdapter = new HomePagerAdapter(getChildFragmentManager(),list,itemTxt);
        mHomeViewPager.setAdapter(mHomePagerAdapter);
        mHomeTabLayout.setupWithViewPager(mHomeViewPager);


        mHomeViewPager.setCurrentItem(0);
        mHomeViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        mHomeTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        mHomeViewPager.setCurrentItem(0);
                        break;
                    case 1:
                        mHomeViewPager.setCurrentItem(1);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }
    public static HomeFragment newInstance(){
        return new HomeFragment();
    }
}
