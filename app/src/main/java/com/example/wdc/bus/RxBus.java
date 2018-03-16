package com.example.wdc.bus;

import java.util.LinkedHashMap;

import io.reactivex.Observable;
import io.reactivex.Observer;

/**
 * Created by dechao on 2018/2/8.
 *
 * @Title:
 * @Description:
 * @Author:WangDeChao
 * @E-mail:mail2dechao@gmail.com
 */

public class RxBus{

    private static LinkedHashMap<String,Observable> mBusMap;

    private static LinkedHashMap<String,Observer> mObserverMap;

    static {
        mBusMap = new LinkedHashMap<>();
        mObserverMap = new LinkedHashMap<>();
    }

    private RxBus(){

    }

    private static RxBus rxBus;

    public static RxBus getInstance(){
        if (rxBus == null){
            synchronized (RxBus.class){
                if (rxBus == null){
                    rxBus = new RxBus();
                }
            }
        }
        return rxBus;
    }

    public Observable getObserver(String key){

        Observable observable = mBusMap.get(key);
        return observable;
    }

    public int addObservable(String key,Observable observable){
        if (mBusMap.containsKey(key)){
            return 0;
        }else{
            mBusMap.put(key,observable);
            return 1;
        }
    }

    public void addObserver(String key,Observer observable){
        if (mObserverMap.containsKey(key)){
            mBusMap.get(key).subscribe(observable);
        }
    }
}
