package com.anju.yyk.main.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class PhotoEntity implements MultiItemEntity {

    public static final int NORMAL_TYPE = 0;
    public static final int ADD_TYPE = 1;

    private int mItemType;

    private String photoPath;
    private int spanSize;

    public PhotoEntity(int itemType){
        this.mItemType = itemType;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
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
