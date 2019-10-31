package com.anju.yyk.main.entity;

import com.anju.yyk.main.adapter.RecordAdapter;
import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;

public class RecordTitleEntity extends AbstractExpandableItem<RecordInfoEntity> implements MultiItemEntity {
    public String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int getItemType() {
        return RecordAdapter.TYPE_TITLE;
    }

    @Override
    public int getLevel() {
        return 0;
    }
}
