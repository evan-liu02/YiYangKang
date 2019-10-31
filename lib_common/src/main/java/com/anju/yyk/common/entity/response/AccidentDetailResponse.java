package com.anju.yyk.common.entity.response;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

public class AccidentDetailResponse {

    public static final int NORMAL_TYPE = 0;

    /**
     * status : 0
     * title : 查询成功！
     * list : [{"id":"463","content":"","name":"阎乃香","chuangwei":"59","hulijibie":"全护Ⅰ双人间","nianling":"71"}]
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
         * id : 463
         * content :
         * name : 阎乃香
         * chuangwei : 59
         * hulijibie : 全护Ⅰ双人间
         * nianling : 71
         */

        private String id;
        private String content;
        private String name;
        private String chuangwei = "";
        private String hulijibie = "";
        private String nianling = "";

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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
