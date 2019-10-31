package com.anju.yyk.common.entity.response;

public class HealthResposne {

    /**
     * id : 13
     * guominshi : 无
     * mailv : 76
     * tiwen : 36.3
     * status : 0
     */

    private String id;
    private String guominshi;
    private String mailv;
    private String tiwen;
    private int status;
    private String title = "";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGuominshi() {
        return guominshi;
    }

    public void setGuominshi(String guominshi) {
        this.guominshi = guominshi;
    }

    public String getMailv() {
        return mailv;
    }

    public void setMailv(String mailv) {
        this.mailv = mailv;
    }

    public String getTiwen() {
        return tiwen;
    }

    public void setTiwen(String tiwen) {
        this.tiwen = tiwen;
    }

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
}
