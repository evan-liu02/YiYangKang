package com.anju.yyk.common.base;


import com.anju.yyk.common.app.sp.AppSP;

/**
 *
 * @author LeoWang
 *
 * @Package cn.com.drpeng.runman.common.base
 *
 * @Description 携带token请求的参数需要继承此类
 *
 * @Date 2019/6/6 15:41
 *
 * @modify:
 */
public class BaseParams {

    private String token;

    public BaseParams(){
        AppSP appSP = new AppSP(BaseApplication.getInstance());
        this.token = appSP.getToken();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
