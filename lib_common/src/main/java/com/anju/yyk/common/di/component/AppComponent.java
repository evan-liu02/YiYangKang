package com.anju.yyk.common.di.component;

import com.anju.yyk.common.app.sp.AppSP;
import com.anju.yyk.common.app.sp.GlobalSP;
import com.anju.yyk.common.db.helper.DBHelper;
import com.anju.yyk.common.di.module.AppModule;
import com.anju.yyk.common.di.module.ClientModule;
import com.anju.yyk.common.http.NetManager;
import com.anju.yyk.common.imageloader.ImageLoader;
import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, ClientModule.class})
public interface AppComponent {
    AppSP getAppSP();
    GlobalSP getGlobalSP();
    Gson getGson();
    NetManager getRetrofitManager();
    DBHelper getDBHelper();
    ImageLoader getImageLoader();

}
