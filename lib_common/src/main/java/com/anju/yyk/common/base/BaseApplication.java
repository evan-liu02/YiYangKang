package com.anju.yyk.common.base;

import android.app.Application;
import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;
import com.anju.yyk.common.app.sp.AppSP;
import com.anju.yyk.common.di.component.AppComponent;
import com.anju.yyk.common.di.component.DaggerAppComponent;
import com.anju.yyk.common.di.module.AppModule;
import com.anju.yyk.common.di.module.ClientModule;
import com.anju.yyk.common.impl.App;
import com.anju.yyk.common.utils.klog.KLog;

import javax.inject.Inject;


/**
 *
 * @author LeoWang
 *
 * @Package com.leo.common.base
 *
 * @Description 空壳app项目Application继承此类
 *
 * @Date 2019/5/6 14:22
 *
 * @modify:
 */
public abstract class BaseApplication extends Application implements App {
    final String TAG = "Runman";

    boolean LOG_DEBUG = true;
    boolean isDebugArouter = true;

    private static BaseApplication instance;

    private AppComponent appComponent;

    @Inject
    AppSP mAppSP;

    public static BaseApplication getInstance() {
        return instance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        KLog.init(LOG_DEBUG, TAG);

        setUpApplicationComponent();

        // Arouter相关
        if (isDebugArouter) {
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this);
    }

    public abstract void setUpApplicationComponent();

    @Override
    public void onTerminate() {
        super.onTerminate();
        ARouter.getInstance().destroy();
    }

    @Override
    public AppComponent getAppComponent() {
        if (null == appComponent){
            appComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(this))
                    .clientModule(new ClientModule(this))
                    .build();
        }
        return appComponent;
    }

}
