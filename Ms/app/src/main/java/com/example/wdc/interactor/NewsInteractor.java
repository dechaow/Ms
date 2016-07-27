package com.example.wdc.interactor;

import android.content.Context;

/**
 * Created by wdc on 2016/7/22.
 */
public interface NewsInteractor {

    void getRefreshDate(Context context,String date);

    void getLoadData(Context context,String date);

}
