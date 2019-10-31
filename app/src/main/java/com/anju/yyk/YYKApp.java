package com.anju.yyk;

import android.content.Context;

import androidx.multidex.MultiDex;

import com.anju.yyk.common.base.BaseApplication;
import com.anju.yyk.di.component.DaggerYYKComponent;

public class YYKApp extends BaseApplication {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void setUpApplicationComponent() {
        DaggerYYKComponent.builder()
                .appComponent(BaseApplication.getInstance().getAppComponent())
                .build()
                .inject(this);
    }
}
