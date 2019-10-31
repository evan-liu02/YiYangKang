package com.anju.yyk.common.entity.response;

import java.util.List;

public class JisShuResponse {


    /**
     * status : 0
     * title : 查询成功
     * list : [{"id":"9","name":"孙英","guanxi":"夫妻","phone":"13505332155"},{"id":"10","name":"毕梦悦","guanxi":"父女","phone":"13869309920"}]
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
         * id : 9
         * name : 孙英
         * guanxi : 夫妻
         * phone : 13505332155
         */

        private String id;
        private String name;
        private String guanxi;
        private String phone;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getGuanxi() {
            return guanxi;
        }

        public void setGuanxi(String guanxi) {
            this.guanxi = guanxi;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }
}
