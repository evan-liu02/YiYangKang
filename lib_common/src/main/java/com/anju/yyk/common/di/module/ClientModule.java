package com.anju.yyk.common.di.module;



import android.app.Application;

import com.anju.yyk.common.http.NetManager;
import com.anju.yyk.common.imageloader.ImageLoader;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 *
 * @author LeoWang
 *
 * @Package com.leo.common.di.module
 *
 * @Description 三方SDK实例
 *
 * @Date 2019/5/5 17:13
 *
 * @modify:
 */
@Module
public class ClientModule {

    private Application mApplication;

    public ClientModule(Application application){
        this.mApplication = application;
    }

    /**
     * 提供网络框架管理类
     * @return {@link NetManager}
     */
    @Singleton
    @Provides
    public NetManager providerRetrofitManager(){
        return new NetManager(mApplication);
    }

    /**
     * 提供图片加载框架
     * @return {@link ImageLoader}
     */
    @Singleton
    @Provides
    public ImageLoader providerImageLoader(){
        return new ImageLoader(mApplication);
    }
}
