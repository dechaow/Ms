package com.example.wdc.loading;

import android.content.Context;
import android.view.View;

/**
 *
 */
public interface IVaryViewHelper {
    /**
     * 获得当前显示的view
     *
     * @return
     */
    View getCurrentLayout();

    /**
     * 恢复显示原来的view
     */
    void restoreView();

    /**
     * 目前要显示的view
     *
     * @param view
     */
    void showLayout(View view);

    /**
     * 加载布局id
     * @param layoutId
     * @return
     */
    View inflate(int layoutId);

    /**
     * 获取当前view的Context
     * @return
     */
    Context getContext();

    /**
     * 获得当期view
     * @return
     */
    View getView();

}