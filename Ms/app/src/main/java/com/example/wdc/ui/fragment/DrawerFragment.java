package com.example.wdc.ui.fragment;

import android.support.design.widget.NavigationView;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.wdc.ms.R;
import com.example.wdc.ui.activity.qrcode.CaptureActivity;
import com.example.wdc.ui.fragment.base.BaseFragment;
//import com.example.wdc.widgets.CircleImageView;

import butterknife.BindView;

/**
 * Created by wdc on 2016/7/22.
 */
public class DrawerFragment extends BaseFragment implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.main_nav_view)
    protected NavigationView mNavigationView;
//    @BindView(R.id.nav_icon)
//    protected CircleImageView img;

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
        View mHeaderView = mNavigationView.inflateHeaderView(R.layout.layout_nav_header);
        RelativeLayout mHeader = (RelativeLayout) mHeaderView.findViewById(R.id.nav_header);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,mScreenHeight/3);
        mHeader.setLayoutParams(params);
        mNavigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_drawer;
    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_drawer_1:
                readyGo(CaptureActivity.class);
                break;
        }

        return true;
    }
}
