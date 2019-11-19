package com.anju.yyk.main.entity;

import com.anju.yyk.main.adapter.RecordAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;

public class RecordInfoEntity implements MultiItemEntity, Serializable {

    private boolean isFiexTop;
    private String name;
    private int age;
    private String bedId;
    private String careType;
    /** 0女，1男*/
    private int sex;
    private String laoren;
    private String bedid;
    private String id;
    private String chuangwei;
    private String shijian;
    private String date;

    public boolean isFiexTop() {
        return isFiexTop;
    }

    public void setFiexTop(boolean fiexTop) {
        isFiexTop = fiexTop;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBedId() {
        return bedId;
    }

    public void setBedId(String bedId) {
        this.bedId = bedId;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCareType() {
        return careType;
    }

    public void setCareType(String careType) {
        this.careType = careType;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getLaoren() {
        return laoren;
    }

    public void setLaoren(String laoren) {
        this.laoren = laoren;
    }

    public String getBedid() {
        return bedid;
    }

    public void setBedid(String bedid) {
        this.bedid = bedid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChuangwei() {
        return chuangwei;
    }

    public void setChuangwei(String chuangwei) {
        this.chuangwei = chuangwei;
    }

    public String getShijian() {
        return shijian;
    }

    public void setShijian(String shijian) {
        this.shijian = shijian;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int getItemType() {
        return RecordAdapter.TYPE_INFO;
    }
}
