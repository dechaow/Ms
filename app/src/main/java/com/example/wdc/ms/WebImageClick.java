package com.example.wdc.ms;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.JavascriptInterface;

import com.example.wdc.ui.activity.ImageDetailActivity;

/**
 * Created by dechao on 2018/3/22.
 *
 * @Title:
 * @Description:
 * @Author:WangDeChao
 * @E-mail:mail2dechao@gmail.com
 */

public class WebImageClick {

    private Context mContext;

    public WebImageClick(Context mContext) {
        this.mContext = mContext;
    }

    @JavascriptInterface
    public void click(String url){

        Intent intent = new Intent(mContext, ImageDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("url",url);
        intent.putExtras(bundle);
        mContext.startActivity(intent);

        System.out.println("WebImageClick.click  " + mContext);
    }
//
//    @JavascriptInterface
//    public String test();


}
