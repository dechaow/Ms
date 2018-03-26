package com.example.wdc.receiver.net;

/**
 * Created by dechao on 2018/3/23.
 *
 * @Title:
 * @Description:
 * @Author:WangDeChao
 * @E-mail:mail2dechao@gmail.com
 */

public interface INetWorkStateChange {
    void onNetworkConnected();
    void onNetworkDisConnected();
}
