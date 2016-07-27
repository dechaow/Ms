package com.example.wdc.loading;


import android.view.View;
import android.widget.TextView;

import com.example.wdc.ms.R;
import com.example.wdc.utils.CommonUtils;


/**
 * 切换不同的view的管理类
 * 网络异常layout，加载layout，
 */
public class VaryViewHelperController {

    private IVaryViewHelper helper;

    public VaryViewHelperController(View view) {
        this(new VaryViewHelper(view));
    }

    public VaryViewHelperController(IVaryViewHelper helper) {
        super();
        this.helper = helper;
    }

    /**
     * x显示网络异常的layout
     * @param onClickListener
     */
    public void showNetworkError(View.OnClickListener onClickListener) {
//        View layout = helper.inflate(R.layout.message);
//        TextView textView = (TextView) layout.findViewById(R.id.message_info);
//        textView.setText(helper.getContext().getResources().getString(R.string.common_no_network_msg));
//
//        ImageView imageView = (ImageView) layout.findViewById(R.id.message_icon);
//        imageView.setImageResource(R.drawable.ic_exception);
//
//        if (null != onClickListener) {
//            layout.setOnClickListener(onClickListener);
//        }
//
//        helper.showLayout(layout);
    }

    /**
     * textview显示错误信息
     * @param errorMsg
     * @param onClickListener
     */
    public void showError(String errorMsg, View.OnClickListener onClickListener) {
        View layout = helper.inflate(R.layout.view_erro);
        helper.showLayout(layout);
    }

    public void showEmpty(String emptyMsg, View.OnClickListener onClickListener) {
//        View layout = helper.inflate(R.layout.message);
//        TextView textView = (TextView) layout.findViewById(R.id.message_info);
//        if (!CommonUtils.isEmpty(emptyMsg)) {
//            textView.setText(emptyMsg);
//        } else {
//            textView.setText(helper.getContext().getResources().getString(R.string.common_empty_msg));
//        }
//
//        ImageView imageView = (ImageView) layout.findViewById(R.id.message_icon);
//        imageView.setImageResource(R.drawable.ic_exception);
//
//        if (null != onClickListener) {
//            layout.setOnClickListener(onClickListener);
//        }
//
//        helper.showLayout(layout);
    }

    public void showLoading(String msg) {
        View layout = helper.inflate(R.layout.view_loading);
        if (!CommonUtils.isEmpty(msg)) {
            TextView textView = (TextView) layout.findViewById(R.id.loading_txt);
            textView.setText(msg);
        }
        helper.showLayout(layout);
    }

    public void restore() {
        helper.restoreView();
    }
}
