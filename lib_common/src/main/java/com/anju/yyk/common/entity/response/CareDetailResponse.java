package com.anju.yyk.common.entity.response;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

public class CareDetailResponse {

    public static final int NORMAL_TYPE = 0;

    /**
     * status : 0
     * title : 成功
     * list : [{"xiangqing":"皮肤护理","name":"马秀凤","time":"2019-09-18 08:23:52"},{"xiangqing":"送餐","name":"马秀凤","time":"2019-09-18 08:23:50"},{"xiangqing":"服药","name":"马秀凤","time":"2019-09-18 08:23:48"},{"xiangqing":"口腔护理","name":"马秀凤","time":"2019-09-18 08:23:46"},{"xiangqing":"协助大、小便","name":"马秀凤","time":"2019-09-18 08:23:43"},{"xiangqing":"喂饭、水","name":"马秀凤","time":"2019-09-18 08:23:41"},{"xiangqing":"协助翻身","name":"马秀凤","time":"2019-09-18 08:23:39"},{"xiangqing":"足部清洁","name":"马秀凤","time":"2019-09-18 08:23:37"},{"xiangqing":"协助上下床","name":"马秀凤","time":"2019-09-18 08:23:35"},{"xiangqing":"面部清洁、洗漱1","name":"马秀凤","time":"2019-09-18 08:23:33"},{"xiangqing":"测量生命体征","name":"马秀凤","time":"2019-09-18 08:23:31"}]
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
         * xiangqing : 皮肤护理
         * name : 马秀凤
         * time : 2019-09-18 08:23:52
         */

        private String xiangqing;
        private String name;
        private String time;
        private String chuangwei = "";
        private String hulijibie = "";
        private String nianling = "";
        private String sex;

        public String getXiangqing() {
            return xiangqing;
        }

        public void setXiangqing(String xiangqing) {
            this.xiangqing = xiangqing;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getChuangwei() {
            return chuangwei;
        }

        public void setChuangwei(String chuangwei) {
            this.chuangwei = chuangwei;
        }

        public String getHulijibie() {
            return hulijibie;
        }

        public void setHulijibie(String hulijibie) {
            this.hulijibie = hulijibie;
        }

        public String getNianling() {
            return nianling;
        }

        public void setNianling(String nianling) {
            this.nianling = nianling;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        @Override
        public int getItemType() {
            return NORMAL_TYPE;
        }
    }
}
