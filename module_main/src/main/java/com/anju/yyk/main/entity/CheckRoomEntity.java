package com.anju.yyk.main.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class CheckRoomEntity implements MultiItemEntity {

    /** 文本*/
    public static final int CHECKROOM_TXT_TYPE = 0;
    /** 下拉*/
    public static final int SPINNER_TYPE = 1;
    /** 开关*/
    public static final int TOGGLE_TYPE = 2;


    private int id;
    /** （0：文本框，1：下拉框，2：复选框）*/
    private int leixing;
    private String title;
    private int status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getLeixing() {
        return leixing;
    }

    public void setLeixing(int leixing) {
        this.leixing = leixing;
    }

    @Override
    public int getItemType() {
        return getLeixing();
    }
}
