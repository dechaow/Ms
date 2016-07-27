package com.example.wdc.ui.fragment.base;

import com.example.wdc.view.base.BaseView;

/**
 * Created by wdc on 2016/7/21.
 */
public abstract class BaseFragment extends BaseLazyFragment implements BaseView {


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
        toggleNetworkError(true,null);
    }
}
