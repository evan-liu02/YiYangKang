package com.anju.yyk.main.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class PatrolEntity implements MultiItemEntity {

    public static final int TITLE_TYPE = 0;
    public static final int DEVICE_TYPE = 1;
    public static final int PATROL_TYPE = 2;
    public static final int PHOTO_TYPE = 3;
    public static final int ADD_PHOTO_TYPE = 4;

    private int mItemType;

    private String title;
    private boolean isRight;
    private String filePath;
    private String imgUrl;
    private String des;
    private int spanSize;

    public PatrolEntity(int itemType){
        this.mItemType = itemType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isRight() {
        return isRight;
    }

    public void setRight(boolean right) {
        isRight = right;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public int getSpanSize() {
        return spanSize;
    }

    public void setSpanSize(int spanSize) {
        this.spanSize = spanSize;
    }

    @Override
    public int getItemType() {
        return mItemType;
    }
}
