package com.example.wdc;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.NonNull;

import com.example.wdc.ui.fragment.images.ImagesViewModel;
import com.example.wdc.ui.fragment.news.NewsViewModel;

/**
 * Created by dechao on 2018/2/9.
 *
 * @Title: viewModel分发工厂
 * @Description: 这个类用来分发ViewModel，
 * @Author:WangDeChao
 * @E-mail:mail2dechao@gmail.com
 */

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private static volatile ViewModelFactory INSTANCE;

    private Application mApplication;

    public static ViewModelFactory getInstance(Application application) {

        if (INSTANCE == null) {
            synchronized (ViewModelFactory.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ViewModelFactory(application);
                }
            }
        }
        return INSTANCE;
    }

    private ViewModelFactory(Application application) {
        mApplication = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ImagesViewModel.class)){
            return (T) new ImagesViewModel(mApplication);
        }else if (modelClass.isAssignableFrom(NewsViewModel.class)){
            return (T) new NewsViewModel(mApplication);
        }
        throw new IllegalArgumentException("This ViewModel can't resolve : " + modelClass.getName());
    }
}
