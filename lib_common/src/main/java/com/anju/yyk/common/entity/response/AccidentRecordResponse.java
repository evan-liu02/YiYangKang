package com.anju.yyk.common.entity.response;

import java.util.List;

public class AccidentRecordResponse {

    /**
     * status : 0
     * title : 查询成功！
     * list : [{"chuangwei":"1","name":"毕曙光","shijian":"2019/5/8 14:23:09","id":"226"}]
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
         * chuangwei : 1
         * name : 毕曙光
         * shijian : 2019/5/8 14:23:09
         * id : 226
         */

        private String chuangwei;
        private String name;
        private String shijian;
        private String id;

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

        public String getShijian() {
            return shijian;
        }

        public void setShijian(String shijian) {
            this.shijian = shijian;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
