package com.example.wdc.ui.fragment;

import android.support.design.widget.NavigationView;
import android.support.v7.app.AlertDialog;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.wdc.ms.R;
import com.example.wdc.ui.activity.qrcode.CaptureActivity;
import com.example.wdc.ui.fragment.base.BaseFragment;
import com.example.wdc.widgets.CircleImageView;
//import com.example.wdc.widgets.CircleImageView;

import butterknife.BindView;

/**
 * Created by wdc on 2016/7/22.
 */
public class DrawerFragment extends BaseFragment implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.main_nav_view)
    protected NavigationView mNavigationView;


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
        CircleImageView img = (CircleImageView) mHeaderView.findViewById(R.id.nav_icon);
        mNavigationView.setNavigationItemSelectedListener(this);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("更换头像").setNegativeButton("拍照",null).setPositiveButton("相册",null).create().show();
            }
        });
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

    public static DrawerFragment newInstance(){
      return new DrawerFragment();
    }
}
