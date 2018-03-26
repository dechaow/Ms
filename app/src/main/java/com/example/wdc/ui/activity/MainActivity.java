package com.example.wdc.ui.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.example.wdc.ms.R;
import com.example.wdc.presenter.Presenter;
import com.example.wdc.presenter.impl.MainPresenterImpl;
import com.example.wdc.ui.activity.base.BaseActivity;
import com.example.wdc.ui.fragment.DrawerFragment;
import com.example.wdc.ui.fragment.HomeFragment;
import com.example.wdc.utils.NetUtils;
import com.example.wdc.utils.PrefUtil;
import com.example.wdc.view.MainView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * Created by wdc on 2016/7/20.
 */
public class MainActivity extends BaseActivity implements MainView {

    @BindView(R.id.main_drawer_layout)
    protected DrawerLayout mDrawerLayout;
    @BindView(R.id.main_drawer)
    protected FrameLayout drawer;
    @BindView(R.id.main_frame)
    protected FrameLayout main;

    HomeFragment homeFragment;
    DrawerFragment drawerFragment;
    private Presenter mPresenter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_settings:
                if (PrefUtil.getTheme() == R.style.NightTheme) {
                    setTheme(R.style.DefaultTheme);
                    PrefUtil.setTheme(R.style.DefaultTheme);
                } else if (PrefUtil.getTheme() == R.style.DefaultTheme) {
                    setTheme(R.style.NightTheme);
                    PrefUtil.setTheme(R.style.NightTheme);
                }
                showFragment(new HomeFragment());
                addDrawerFragment(new DrawerFragment());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(getResources().getColor(getColorByAttributeId(R.attr.status_bar_color)));
                }
                Snackbar.make(mDrawerLayout, "主题已切换", Snackbar.LENGTH_SHORT).show();
                setTaskDesc();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //根据属性attrId获取对应的resId
    private int getColorByAttributeId(int attrIdForColor) {
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = getTheme();
        theme.resolveAttribute(attrIdForColor, typedValue, true);
        return typedValue.resourceId;
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void initViewsAndEvents() {
        mPresenter = new MainPresenterImpl(this, this);
        mPresenter.initialized();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.TRANSPARENT);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

    }

    @Override
    public void onNetworkConnected() {
    }

    @Override
    public void onNetworkDisConnected() {
    }

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return false;
    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return TransitionMode.RIGHT;
    }

    @Override
    protected Boolean isAddStatusHeight() {
        return true;
    }

    @Override
    public void showFragment(Fragment fragment) {
        FragmentTransaction mTransaction = getSupportFragmentManager().beginTransaction();
        if (homeFragment != null) {
            mTransaction.remove(homeFragment);
        }
        homeFragment = HomeFragment.newInstance();
        mTransaction.replace(R.id.main_frame, homeFragment);
        mTransaction.commit();

    }

    @Override
    public void addDrawerFragment(Fragment fragment) {
        FragmentTransaction mTransaction = getSupportFragmentManager().beginTransaction();
        if (drawerFragment != null) {
            mTransaction.remove(drawerFragment);
        }
        drawerFragment = DrawerFragment.newInstance();
        mTransaction.replace(R.id.main_drawer, drawerFragment);
        mTransaction.commit();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        System.out.println("MainActivity.onNewIntent");
    }
}
