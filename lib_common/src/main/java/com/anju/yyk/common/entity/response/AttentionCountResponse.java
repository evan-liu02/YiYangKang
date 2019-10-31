package com.anju.yyk.common.entity.response;

public class AttentionCountResponse {

    /**
     * status : 0
     * title : 查询成功！
     * count : 51
     */

    private int status;
    private String title;
    private String count;

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

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
