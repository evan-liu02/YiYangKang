package com.anju.yyk.common.base;

import java.io.Serializable;

/**
 * 
 * @author LeoWang
 * 
 * @Package com.leo.common.base
 * 
 * @Description 根据服务器返回格式自己封装
 * 
 * @Date 2019/5/6 11:17
 * 
 * @modify:
 */
public class BaseResponse implements Serializable {

    private int status;
    private String title;
    private String msg;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
