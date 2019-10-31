package com.anju.yyk.common.entity.response;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

public class PatrolResponse {

    public static final int TITLE_TYPE = 0;
    public static final int DEVICE_TYPE = 1;
    public static final int PATROL_TYPE = 2;
    public static final int PHOTO_TYPE = 3;
    public static final int ADD_PHOTO_TYPE = 4;

    /**
     * status : 0
     * title : 查询成功！
     * list : [{"lieming":"dianyuanxian","leixing":"2","title":"电源线路情况"},{"lieming":"keranwu","leixing":"2","title":"是否有遗留火种、可燃物"},{"lieming":"xiaofangshuan","leixing":"2","title":"消防栓完好情况"},{"lieming":"miehuoqi","leixing":"2","title":"灭火器完好情况"},{"lieming":"anquanzhishi","leixing":"2","title":"安全指示标识"},{"lieming":"anquanchukou","leixing":"2","title":"安全出口"},{"lieming":"shusantongdao","leixing":"2","title":"疏散通道是否畅通"},{"lieming":"jiankongshebei","leixing":"2","title":"监控设备完好情况"}]
     */

    private int status;
    private String title;
    private List<ListBean> list;

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

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements MultiItemEntity {
        /**
         * lieming : dianyuanxian
         * leixing : 2
         * title : 电源线路情况
         */

        private String lieming;
        private String leixing;
        private String title;
        private boolean isRight = true;
        private String photoPath = "";
        private int spanSize;
        private String des;

        private int mItemType;

        public String getLieming() {
            return lieming;
        }

        public void setLieming(String lieming) {
            this.lieming = lieming;
        }

        public String getLeixing() {
            return leixing;
        }

        public void setLeixing(String leixing) {
            this.leixing = leixing;
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

        public int getmItemType() {
            return mItemType;
        }

        public void setmItemType(int mItemType) {
            this.mItemType = mItemType;
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

        public String getDes() {
            return des;
        }

        public void setDes(String des) {
            this.des = des;
        }

        @Override
        public int getItemType() {
            return getmItemType();
        }
    }
}
