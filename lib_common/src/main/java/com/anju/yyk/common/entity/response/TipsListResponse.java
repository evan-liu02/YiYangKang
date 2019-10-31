package com.anju.yyk.common.entity.response;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

public class TipsListResponse {

    public static final int TYPE_TITLE = 0;
    public static final int TYPE_INFO = 1;

    /**
     * status : 0
     * title : 查询成功！
     * list : [{"chuangwei":"1","name":"毕曙光","content":"","luyin":[{"lujing":"../admin/fileDown/sound1/20191004_204232.mp3"}],"hugong":"徐翠华","time":"2019/10/4 20:42:48","status":"1","yidurenyuan":""},{"chuangwei":"1","name":"毕曙光","content":"","luyin":[{"lujing":"../admin/fileDown/sound1/20190828_160348.mp3"}],"hugong":"徐翠华","time":"2019/8/28 16:04:00","status":"1","yidurenyuan":"徐翠华"}]
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

    public static class ListBean extends AbstractExpandableItem<ListBean.LuyinBean> implements MultiItemEntity {

        /**
         * chuangwei : 1
         * name : 毕曙光
         * content :
         * luyin : [{"lujing":"../admin/fileDown/sound1/20191004_204232.mp3"}]
         * hugong : 徐翠华
         * time : 2019/10/4 20:42:48
         * status : 1
         * yidurenyuan :
         */

        private String chuangwei;
        private String name;
        private String content;
        private String hugong;
        private String time;
        private int status;
        private String yidurenyuan;
        private List<LuyinBean> luyin;

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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getHugong() {
            return hugong;
        }

        public void setHugong(String hugong) {
            this.hugong = hugong;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getYidurenyuan() {
            return yidurenyuan;
        }

        public void setYidurenyuan(String yidurenyuan) {
            this.yidurenyuan = yidurenyuan;
        }

        public List<LuyinBean> getLuyin() {
            return luyin;
        }

        public void setLuyin(List<LuyinBean> luyin) {
            this.luyin = luyin;
        }

        @Override
        public int getItemType() {
            return TYPE_TITLE;
        }

        @Override
        public int getLevel() {
            return 0;
        }

        public static class LuyinBean implements MultiItemEntity{
            /**
             * lujing : ../admin/fileDown/sound1/20191004_204232.mp3
             */

            private String lujing;

            public String getLujing() {
                return lujing;
            }

            public void setLujing(String lujing) {
                this.lujing = lujing;
            }

            @Override
            public int getItemType() {
                return TYPE_INFO;
            }
        }
    }
}
