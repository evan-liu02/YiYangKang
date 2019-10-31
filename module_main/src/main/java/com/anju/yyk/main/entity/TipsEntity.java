package com.anju.yyk.main.entity;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * 
 * @author LeoWang
 * 
 * @Package com.anju.yyk.main.entity
 * 
 * @Description 重新修改结构后的提醒列表实体
 * 
 * @Date 2019/10/30 11:18
 * 
 * @modify:
 */
public class TipsEntity extends AbstractExpandableItem<TipsEntity.LuyinBean> implements MultiItemEntity{

    public static final int TYPE_TITLE = 0;
    public static final int TYPE_INFO = 1;

    /** 日期*/
    private String date;
    private String id;
    private String chuangwei;
    private String name;
    private String content;
    private String hugong;
    private String time;
    private int status;
    private String yidurenyuan;
    private List<LuyinBean> luyin;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getHugong() {
        return hugong;
    }

    public void setHugong(String hugong) {
        this.hugong = hugong;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getYidurenyuan() {
        return yidurenyuan;
    }

    public void setYidurenyuan(String yidurenyuan) {
        this.yidurenyuan = yidurenyuan;
    }

    public List<LuyinBean> getLuyin() {
        return luyin;
    }

    public void setLuyin(List<LuyinBean> luyin) {
        this.luyin = luyin;
    }

    @Override
    public int getItemType() {
        return TYPE_TITLE;
    }

    @Override
    public int getLevel() {
        return 0;
    }


    public static class LuyinBean implements MultiItemEntity {
        /**
         * lujing : ../admin/fileDown/sound1/20191004_204232.mp3
         */

        private String lujing;

        public String getLujing() {
            return lujing;
        }

        public void setLujing(String lujing) {
            this.lujing = lujing;
        }

        @Override
        public int getItemType() {
            return TYPE_INFO;
        }
    }
}
