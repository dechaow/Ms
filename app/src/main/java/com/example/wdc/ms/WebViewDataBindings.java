package com.example.wdc.ms;

import android.databinding.BindingAdapter;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by dechao on 2018/3/22.
 *
 * @Title:
 * @Description:
 * @Author:WangDeChao
 * @E-mail:mail2dechao@gmail.com
 */

public class WebViewDataBindings {

    @SuppressWarnings("unchecked")
    @BindingAdapter("app:body")
    public static void setWebViewData(WebView webView, String body) {

        StringBuffer buffer = handleHtml(body);

        webView.setDrawingCacheEnabled(true);

        WebSettings settings = webView.getSettings();
        //允许js代码
        settings.setJavaScriptEnabled(true);
        //自动加载图片
        settings.setLoadsImagesAutomatically(true);
        //禁用文字缩放
        settings.setTextZoom(100);
        //添加js接口
        webView.addJavascriptInterface(new WebImageClick(webView.getContext()), "imageClick");

        //
        WebViewClient client = new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {

            }

            @Override
            public void onLoadResource(WebView view, String url) {

            }
        };
        webView.setWebViewClient(client);

        webView.loadDataWithBaseURL("file:///android_asset/", buffer.toString(), "text/html", "utf-8", null);

    }

    /**
     * 解析html文本
     *
     * @param body
     * @return
     */
    public static StringBuffer handleHtml(String body) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("<html>" +
                "<head>" +
                "<link rel=\"stylesheet\" type=\"text/css\" href=\"css/detail.css\" >" +
                "<style type=\"text/css\">" +
                ".content-image{width:100%;height:240px} " +
                "\n p{font-size:17px; \n color:#444444; \n letter-spacing:1.5px} \n " +
                "*{padding-left:1.5px; \n padding-right:2px; \n background-color:#efefef} \n" +
                ".question-title{font-size:20px;} \n" +
                ".a{ background-color:#ccc; \n width:100%; \n text-decoration:none; \n padding:4px;} " +
                "</style>" +
                "</head>");

        stringBuffer.append("<body>");
        stringBuffer.append(body);
        stringBuffer.append("</body>");

        stringBuffer.append("<script type=\"text/javascript\">");
        String js = "load(); \n " +
                "javascript:function load(){var imgArray = document.getElementsByTagName(\"img\"); \n " +
                "for(var i = 0; i < imgArray.length; i++){ \n " +
                "imgArray[i].onclick = function(){ \n " +
                "imageClick.click(this.src); \n" +
                "} \n" +
                "} \n" +
                "}";
        stringBuffer.append(js);
        stringBuffer.append("</script>");

        stringBuffer.append("</html>");
        return stringBuffer;
    }

    public interface IImageClick {

        @JavascriptInterface
        void click(String url);
    }

}
