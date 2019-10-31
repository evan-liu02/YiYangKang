package com.anju.yyk.main.entity;

import com.anju.yyk.common.entity.response.PersonListResponse;
import com.anju.yyk.main.adapter.BedListAdapter;
import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 *
 * @author LeoWang
 *
 * @Package com.anju.yyk.main.entity
 *
 * @Description 首页列表标题
 *
 * @Date 2019/9/5 18:46
 *
 * @modify:
 */
public class BedTitleEntity extends AbstractExpandableItem<PersonListResponse.ListBean> implements MultiItemEntity {

    public String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int getItemType() {
        return BedListAdapter.TYPE_TITLE;
    }

    @Override
    public int getLevel() {
        return 0;
    }
}
