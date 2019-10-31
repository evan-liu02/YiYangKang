package com.anju.yyk.common.base;

import com.anju.yyk.common.app.sp.AppSP;

import javax.inject.Inject;


/**
 *
 * @author LeoWang
 *
 * @Package cn.com.drpeng.runman.common.base
 *
 * @Description 涉及到网络请求的Model需要继承此类，使用getCurrentTail()方法获取当前BaseUrl所对应的tail
 *
 * @Date 2019/6/3 13:41
 *
 * @modify:
 */
public abstract class BaseModel {

    @Inject
    AppSP appSP;

    public BaseModel(){
        if (isSetupModelComponent()){
            setupModelComponent();
        }
    }

    /**
     * 如果子Model需要DI提供的实例，则在实现此注入
     */
    public abstract void setupModelComponent();

    /**
     * 子Model是否需要注入
     * @return 是否注入
     */
    public abstract boolean isSetupModelComponent();
}
