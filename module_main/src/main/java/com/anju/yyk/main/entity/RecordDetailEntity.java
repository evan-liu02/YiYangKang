package com.anju.yyk.main.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class RecordDetailEntity implements MultiItemEntity {

    public static final int NORMAL_TYPE = 0;

    private String event;
    private String operater;
    private String time;

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getOperater() {
        return operater;
    }

    public void setOperater(String operater) {
        this.operater = operater;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public int getItemType() {
        return NORMAL_TYPE;
    }
}
