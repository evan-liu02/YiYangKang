package com.anju.yyk.common.entity.response;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

public class CheckRoomListResponse {


    /**
     * status : 0
     * title : 查询成功！
     * list : [{"lieming":"lifa","leixing":"2","title":"理发"},{"lieming":"bianqi","leixing":"2","title":"清洗消毒便器"},{"lieming":"fangjianxiaodu","leixing":"2","title":"房间消毒"},{"lieming":"maojin","leixing":"2","title":"清洗消毒毛巾"},{"lieming":"shuibeicanju","leixing":"2","title":"清洗消毒水杯餐具"},{"lieming":"genghuanyifu","leixing":"2","title":"更换衣服"},{"lieming":"genghuanzhentao","leixing":"2","title":"更换枕套"},{"lieming":"genghuanbeizhao","leixing":"2","title":"更换被罩"},{"lieming":"genghuanchuangdan","leixing":"2","title":"更换床单"},{"lieming":"xizao","leixing":"2","title":"洗澡"},{"lieming":"rushui","leixing":"2","title":"老人是否入睡"},{"lieming":"jingshen","leixing":"1","title":"精神状态判断","option":[{"mingcheng":"良好","zhi":"良好"},{"mingcheng":"较差","zhi":"较差"},{"mingcheng":"差","zhi":"差"}]},{"lieming":"xueya","leixing":"0","title":"血压测量"},{"lieming":"mailv","leixing":"0","title":" \t脉率测量"},{"lieming":"tiwen","leixing":"0","title":"体温测量"},{"lieming":"yichang","leixing":"2","title":"综合判断老人是否有异常"},{"lieming":"xinli","leixing":"1","title":"心理状态判断","option":[{"mingcheng":"良好","zhi":"良好"},{"mingcheng":"抑郁","zhi":"抑郁"},{"mingcheng":"兴奋","zhi":"兴奋"},{"mingcheng":"易激动","zhi":"易激动"}]}]
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
         * lieming : lifa
         * leixing : 2
         * title : 理发
         * option : [{"mingcheng":"良好","zhi":"良好"},{"mingcheng":"较差","zhi":"较差"},{"mingcheng":"差","zhi":"差"}]
         */

        private String lieming;
        private int leixing;
        private String title;
        private int toggleStatus = 1;
        private List<OptionBean> option;

        public String getLieming() {
            return lieming;
        }

        public void setLieming(String lieming) {
            this.lieming = lieming;
        }

        public int getLeixing() {
            return leixing;
        }

        public void setLeixing(int leixing) {
            this.leixing = leixing;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<OptionBean> getOption() {
            return option;
        }

        public void setOption(List<OptionBean> option) {
            this.option = option;
        }

        public int getToggleStatus() {
            return toggleStatus;
        }

        public void setToggleStatus(int toggleStatus) {
            this.toggleStatus = toggleStatus;
        }

        @Override
        public int getItemType() {
            return getLeixing();
        }

        public static class OptionBean {
            /**
             * mingcheng : 良好
             * zhi : 良好
             */

            private String mingcheng;
            private String zhi;

            public String getMingcheng() {
                return mingcheng;
            }

            public void setMingcheng(String mingcheng) {
                this.mingcheng = mingcheng;
            }

            public String getZhi() {
                return zhi;
            }

            public void setZhi(String zhi) {
                this.zhi = zhi;
            }
        }
    }
}
