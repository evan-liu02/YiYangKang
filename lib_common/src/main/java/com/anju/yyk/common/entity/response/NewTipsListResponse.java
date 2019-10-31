package com.anju.yyk.common.entity.response;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

public class NewTipsListResponse {

    public static final int TYPE_TITLE = 0;
    public static final int TYPE_INFO = 1;

    /**
     * status : 0
     * title : 查询成功！
     * list : [{"date":"2019-04-27","records":[{"id":"101","chuangwei":"12","name":"张孝太","content":"注意督促老人多喝水","hugong":"徐翠华","time":"09:42","status":"1","yidurenyuan":"毛霞,耿昕,袁秀娟,董年果,郝秀群"}]},{"date":"2019-04-26","records":[{"id":"58","chuangwei":"12","name":"张孝太","content":"注意翻身","hugong":"徐翠华","time":"08:47","status":"1","yidurenyuan":"毛霞,耿昕,张久霞,袁秀娟,董年果,郝秀群"}]}]
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
         * date : 2019-04-27
         * records : [{"id":"101","chuangwei":"12","name":"张孝太","content":"注意督促老人多喝水","hugong":"徐翠华","time":"09:42","status":"1","yidurenyuan":"毛霞,耿昕,袁秀娟,董年果,郝秀群"}]
         */

        private String date;
        private List<RecordsBean> records;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public List<RecordsBean> getRecords() {
            return records;
        }

        public void setRecords(List<RecordsBean> records) {
            this.records = records;
        }

        public static class RecordsBean extends AbstractExpandableItem<ListBean.RecordsBean.LuyinBean> implements MultiItemEntity{
            /**
             * id : 101
             * chuangwei : 12
             * name : 张孝太
             * content : 注意督促老人多喝水
             * hugong : 徐翠华
             * time : 09:42
             * status : 1
             * yidurenyuan : 毛霞,耿昕,袁秀娟,董年果,郝秀群
             */

            private String id;
            private String chuangwei;
            private String name;
            private String content;
            private String hugong;
            private String time;
            private int status;
            private String yidurenyuan;
            private List<LuyinBean> luyin;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

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
}
