package com.example.wdc.ui.activity.base;

import android.os.Bundle;
import android.os.PersistableBundle;

import com.example.wdc.view.base.BaseView;

/**
 * Created by wdc on 2016/7/20.
 */
public abstract class BaseActivity extends BaseAppCompatActivity implements BaseView{

    @Override
    public void showLoading(String msg) {
        toggleShowLoading(true,msg);
    }

    @Override
    public void hideLoading() {
        toggleShowLoading(false,null);
    }

    @Override
    public void showErro(String msg) {
        toggleShowError(true,msg,null);
    }

    @Override
    public void showException(String msg) {
        toggleShowError(true,msg,null);
    }

    @Override
    public void showNetErro() {
        toggleShowError(true,null,null);
    }
}
