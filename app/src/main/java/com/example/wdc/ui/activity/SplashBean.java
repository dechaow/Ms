package com.example.wdc.ui.activity;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.android.databinding.library.baseAdapters.BR;

/**
 * Created by dechao on 2018/2/8.
 *
 * @Title: splash页面的字体model
 * @Description:
 * @Author:WangDeChao
 * @E-mail:mail2dechao@gmail.com
 */

public class SplashBean extends BaseObservable {

    private String text;

    public SplashBean(String text) {
        this.text = text;
    }

    @Bindable
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        notifyPropertyChanged(BR.text);
    }
}
