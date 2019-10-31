package com.anju.yyk.main.entity;

import com.anju.yyk.main.adapter.BedListAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
/**
 *
 * @author LeoWang
 *
 * @Package com.anju.yyk.main.entity
 *
 * @Description 首页列表展开信息
 *
 * @Date 2019/9/5 18:46
 *
 * @modify:
 */
public class BedInfoEntity implements MultiItemEntity {

    private boolean isFiexTop;
    /** 0女，1男*/
    private int sex;
    private int age;
    private String name;
    private int bedId;
    private int roomTag;

    public boolean isFiexTop() {
        return isFiexTop;
    }

    public void setFiexTop(boolean fiexTop) {
        isFiexTop = fiexTop;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBedId() {
        return bedId;
    }

    public void setBedId(int bedId) {
        this.bedId = bedId;
    }

    public int getRoomTag() {
        return roomTag;
    }

    public void setRoomTag(int roomTag) {
        this.roomTag = roomTag;
    }

    @Override
    public int getItemType() {
        return BedListAdapter.TYPE_INFO;
    }
}
