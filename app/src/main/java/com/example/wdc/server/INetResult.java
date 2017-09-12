package com.example.wdc.server;

/**
 * Created by wdc on 2016/7/22.
 * 网络请求的统一回执接口
 */
public interface INetResult<T> {

    void onSuccess(T result);

    void onErro(String erro);

    void onException(String exception);

}
