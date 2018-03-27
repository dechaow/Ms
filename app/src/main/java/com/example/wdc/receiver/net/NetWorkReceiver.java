package com.example.wdc.receiver.net;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;

import java.util.ArrayList;

/**
 * Created by dechao on 2018/3/23.
 *
 * @Title: observable
 * @Description:
 * @Author:WangDeChao
 * @E-mail:mail2dechao@gmail.com
 */

public class NetWorkReceiver extends BroadcastReceiver {

    ArrayList<INetWorkStateChange> observers = new ArrayList<>();

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent == null || intent.getAction() == null) {
            return;
        }

        if (intent.getAction() == WifiManager.NETWORK_STATE_CHANGED_ACTION) {
            NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
            NetworkInfo.State state = info.getState();

            if (state == NetworkInfo.State.DISCONNECTED) {
                for (INetWorkStateChange c :
                        observers) {
                    c.onNetworkDisConnected();
                }
            } else if (state == NetworkInfo.State.CONNECTED) {
                for (INetWorkStateChange c :
                        observers) {
                    c.onNetworkConnected();
                }
            }

        }
    }

    public void registerReceiver(Context context, INetWorkStateChange change) {
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        filter.addAction(WifiManager.SUPPLICANT_CONNECTION_CHANGE_ACTION);
        filter.addAction(WifiManager.SUPPLICANT_STATE_CHANGED_ACTION);
        context.registerReceiver(this, filter);
        observers.add(change);
    }

    public void unregisterReceiver(Context context, INetWorkStateChange change) {
        context.unregisterReceiver(this);
        if (observers.contains(change)) {
            observers.remove(change);
        }
    }
}
