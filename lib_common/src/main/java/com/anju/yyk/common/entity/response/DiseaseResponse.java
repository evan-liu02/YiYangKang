package com.anju.yyk.common.entity.response;

import java.util.List;

public class DiseaseResponse {

    /**
     * status : 0
     * title : 查询成功
     * list : [{"id":"7","name":"高血压","time":""},{"id":"8","name":"糖尿病","time":""},{"id":"9","name":"脑卒中","time":""}]
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
         * id : 7
         * name : 高血压
         * time :
         */

        private String id;
        private String name;
        private String time;

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

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}
