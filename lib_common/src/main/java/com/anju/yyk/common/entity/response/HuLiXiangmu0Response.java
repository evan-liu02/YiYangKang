package com.anju.yyk.common.entity.response;

import java.util.List;

public class HuLiXiangmu0Response {

    /**
     * status : 0
     * title : 成功
     * list : [{"title":"协助翻身","id":"9","jifen":"1"},{"title":"送餐","id":"7","jifen":"1"},{"title":"协助大、小便","id":"8","jifen":"3"},{"title":"面部清洁、洗漱1","id":"11","jifen":"1"},{"title":"协助上下床","id":"12","jifen":"1"},{"title":"测量生命体征","id":"13","jifen":"5"},{"title":"服药","id":"14","jifen":"2"},{"title":"足部清洁","id":"18","jifen":"1"},{"title":"医师查房诊疗费","id":"19","jifen":"10"}]
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

    public static class ListBean {
        /**
         * title : 协助翻身
         * id : 9
         * jifen : 1
         */

        private String title;
        private String id;
        private String jifen;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getJifen() {
            return jifen;
        }

        public void setJifen(String jifen) {
            this.jifen = jifen;
        }
    }
}
