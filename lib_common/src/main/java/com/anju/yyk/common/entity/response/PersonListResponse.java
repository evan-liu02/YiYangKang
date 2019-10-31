package com.anju.yyk.common.entity.response;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;
import java.util.List;

public class PersonListResponse {
    /**
     * status : 0
     * title : 查询成功
     * list : [{"id":"46","name":"毕曙光","nianling":"59","chuangwei":"1","hulijibie":"全护Ⅱ双人间"},{"id":"123","name":"伊若全","nianling":"55","chuangwei":"2","hulijibie":"全护Ⅱ双人间"}]
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

    public static class ListBean implements MultiItemEntity, Serializable {
        /**
         * id : 46
         * name : 毕曙光
         * nianling : 59
         * chuangwei : 1
         * hulijibie : 全护Ⅱ双人间
         */

        private String id;
        private String name;
        private String nianling;
        private int chuangwei;
        private String hulijibie;
        /**  1男 2女*/
        private int sex;
        /** 0是不置顶   1是置顶*/
        private int istop;

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

        public String getNianling() {
            return nianling;
        }

        public void setNianling(String nianling) {
            this.nianling = nianling;
        }

        public int getChuangwei() {
            return chuangwei;
        }

        public void setChuangwei(int chuangwei) {
            this.chuangwei = chuangwei;
        }

        public String getHulijibie() {
            return hulijibie;
        }

        public void setHulijibie(String hulijibie) {
            this.hulijibie = hulijibie;
        }

        /**
         *
         * @return 1男 2女
         */
        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        /**
         *
         * @return 0是不置顶   1是置顶
         */
        public int getIstop() {
            return istop;
        }

        public void setIstop(int istop) {
            this.istop = istop;
        }

        @Override
        public int getItemType() {
            return 1;
        }
    }
}
