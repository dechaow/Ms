package com.example.wdc.ui.fragment.images;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.view.View;

import com.example.wdc.bean.images.ImagesBean;
import com.example.wdc.bean.images.ImagesListBean;
import com.example.wdc.retrofit.manager.RetrofitManager;
import com.example.wdc.ui.fragment.news.OnItemClickListener;
import com.example.wdc.utils.UrlUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dechao on 2018/3/15.
 *
 * @Title:
 * @Description:
 * @Author:WangDeChao
 * @E-mail:mail2dechao@gmail.com
 */

public class ImagesViewModel extends AndroidViewModel {

    private MutableLiveData<ImagesBean> mImagesBean = new MutableLiveData<>();

    private OnItemClickListener mImageClick;

    public ImagesViewModel(@NonNull Application application) {
        super(application);
        loadData(0);
    }

    public LiveData<ImagesBean> getImagesData(){
        return mImagesBean;
    }

    public void loadData(int page){
        RetrofitManager.builder(UrlUtils.BASE_IMAGES_URL)
                .getImages(UrlUtils.IMAGES_URL_COL,UrlUtils.IMAGES_URL_TAG,page, UrlUtils.IMAGES_URL_RN,UrlUtils.IMAGES_URL_FROM)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ImagesBean>() {
                    @Override
                    public void accept(ImagesBean imagesBean) throws Exception {
                        mImagesBean.setValue(imagesBean);
                    }

                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }

    public void setImageClick(OnItemClickListener mImageClick) {
        this.mImageClick = mImageClick;
    }

    public void onImageClick(View view,ImagesListBean bean){
        if (mImageClick != null){
            mImageClick.onItemClick(view,bean);
        }
    }


}
