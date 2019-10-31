package com.anju.yyk.common.base;


import com.anju.yyk.common.utils.AppUtil;
import com.anju.yyk.common.utils.TimeUtil;

/**
 *
 * @author LeoWang
 *
 * @Package com.leo.common.base
 *
 * @Description 根据自己需要封装相应的请求格式
 *
 * @Date 2019/5/6 11:15
 *
 * @modify:
 */
public class BaseRequest<T> {
    /** 接口名称*/
    private String dispatch;
    /** API版本号*/
    private String version = "1";
    /** 本地时间戳*/
    private int timestamp;
    /** 请求参数*/
    private T params;
    /** 扩展参数*/
    private Extras extras;

    public BaseRequest(String dispatch, T params){
        this.dispatch = dispatch;
        this.params = params;
        this.timestamp = TimeUtil.getTimestamp();
        extras = new Extras();
    }

    public String getDispatch() {
        return dispatch;
    }

    public void setDispatch(String dispatch) {
        this.dispatch = dispatch;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public T getParams() {
        return params;
    }

    public void setParams(T params) {
        this.params = params;
    }

    public Extras getExtras() {
        return extras;
    }

    public void setExtras(Extras extras) {
        this.extras = extras;
    }

    private class Extras {
        /** app版本号*/
        private String app_version;

        private int type = 0;
        /** 1员工端*/
        private int side = 1;

        public Extras(){
            app_version = AppUtil.getVersionName(BaseApplication.getInstance());
        }

        public String getApp_version() {
            return app_version;
        }

        public void setApp_version(String app_version) {
            this.app_version = app_version;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getSide() {
            return side;
        }

        public void setSide(int side) {
            this.side = side;
        }
    }
}
