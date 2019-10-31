package com.anju.yyk.common.di.module;

import android.app.Application;

import com.anju.yyk.common.app.sp.AppSP;
import com.anju.yyk.common.app.sp.GlobalSP;
import com.anju.yyk.common.db.helper.DBHelper;
import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 *
 * @author wcs
 *
 * @Package com.leo.common.di.module
 *
 * @Description 提供app常用的实例
 *
 * @Date 2019/5/5 16:50
 *
 * @modify:
 */
@Module
public class AppModule {
    public Application mApplication;

    public AppModule(Application application){
        this.mApplication = application;
    }

    @Singleton
    @Provides
    public Application getmApplication(){
        return mApplication;
    }

    @Singleton
    @Provides
    public AppSP providerAppSP(){
        AppSP appSP = new AppSP(mApplication);
        return appSP;
    }

    @Singleton
    @Provides
    public GlobalSP providerGlobalSP(){
        GlobalSP globalSP = new GlobalSP(mApplication);
        return globalSP;
    }

    @Singleton
    @Provides
    public Gson providerGson(){
        Gson gson = new Gson();
        return gson;
    }

    @Singleton
    @Provides
    public DBHelper providerDBHelper(){
        DBHelper dbHelper = new DBHelper(mApplication);
        return dbHelper;
    }
}
