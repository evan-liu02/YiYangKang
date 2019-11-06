package com.anju.yyk.common.entity.response;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

public class CheckRoomDetailResponse {

    public static final int NORMAL_TYPE = 0;

    /**
     * status : 0
     * title : 查询成功！
     * list : [{"id":"12986","title":"理发","content":"是"},{"id":"12987","title":"清洗消毒便器","content":"是"},{"id":"12988","title":"房间消毒","content":"是"},{"id":"12989","title":"清洗消毒毛巾","content":"是"},{"id":"12990","title":"清洗消毒水杯餐具","content":"否"},{"id":"12991","title":"更换衣服","content":"是"},{"id":"12992","title":"更换枕套","content":"是"},{"id":"12993","title":"更换被罩","content":"是"},{"id":"12994","title":"更换床单","content":"是"},{"id":"12995","title":"洗澡","content":"是"},{"id":"12996","title":"老人是否入睡","content":"是"},{"id":"12997","title":"精神状态判断","content":"良好"},{"id":"12998","title":"血压测量","content":";120/78"},{"id":"12999","title":" 脉率测量","content":"76"},{"id":"13000","title":"体温测量","content":"37"},{"id":"13001","title":"综合判断老人是否有异常","content":"否"},{"id":"13002","title":"心理状态判断","content":"良好"}]
     */

    private int status;
    private String title;
    private List<ListBean> list;
    private String chuangwei = "";
    private String hulijibie = "";
    private String nianling = "";

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

    public static class ListBean implements MultiItemEntity {
        /**
         * id : 12986
         * title : 理发
         * content : 是
         */

        private String id;
        private String title;
        private String content;
        private String chuangwei = "";
        private String hulijibie = "";
        private String nianling = "";

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
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

        @Override
        public int getItemType() {
            return NORMAL_TYPE;
        }
    }
}
