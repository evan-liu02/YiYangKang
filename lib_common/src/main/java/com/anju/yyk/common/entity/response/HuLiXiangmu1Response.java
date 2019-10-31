package com.anju.yyk.common.entity.response;

import java.util.List;

public class HuLiXiangmu1Response {

    /**
     * status : 0
     * title : 成功
     * list : [{"title":"洗头、擦浴","cishu":"1"},{"title":"协助翻身","cishu":"6"}]
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
         * title : 洗头、擦浴
         * cishu : 1
         */

        private String title;
        private String cishu;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCishu() {
            return cishu;
        }

        public void setCishu(String cishu) {
            this.cishu = cishu;
        }
    }
}
