package com.anju.yyk.common.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

@Entity
public class CommonOrderConfig {

    @Id
    private Long id;
    @Property
    private Integer order_type;
    @Property
    private String service_url;
    @Property
    private String explain;
    @Property
    private Integer is_must;
    @Property
    private String acceptance_url;
    @Property
    private String acceptance_extra;
    @Property
    private Integer acceptance_is_must;
    @Generated(hash = 597952817)
    public CommonOrderConfig(Long id, Integer order_type, String service_url,
            String explain, Integer is_must, String acceptance_url,
            String acceptance_extra, Integer acceptance_is_must) {
        this.id = id;
        this.order_type = order_type;
        this.service_url = service_url;
        this.explain = explain;
        this.is_must = is_must;
        this.acceptance_url = acceptance_url;
        this.acceptance_extra = acceptance_extra;
        this.acceptance_is_must = acceptance_is_must;
    }
    @Generated(hash = 82210139)
    public CommonOrderConfig() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Integer getOrder_type() {
        return this.order_type;
    }
    public void setOrder_type(Integer order_type) {
        this.order_type = order_type;
    }
    public String getService_url() {
        return this.service_url;
    }
    public void setService_url(String service_url) {
        this.service_url = service_url;
    }
    public String getExplain() {
        return this.explain;
    }
    public void setExplain(String explain) {
        this.explain = explain;
    }
    public Integer getIs_must() {
        return this.is_must;
    }
    public void setIs_must(Integer is_must) {
        this.is_must = is_must;
    }
    public String getAcceptance_url() {
        return this.acceptance_url;
    }
    public void setAcceptance_url(String acceptance_url) {
        this.acceptance_url = acceptance_url;
    }
    public String getAcceptance_extra() {
        return this.acceptance_extra;
    }
    public void setAcceptance_extra(String acceptance_extra) {
        this.acceptance_extra = acceptance_extra;
    }
    public Integer getAcceptance_is_must() {
        return this.acceptance_is_must;
    }
    public void setAcceptance_is_must(Integer acceptance_is_must) {
        this.acceptance_is_must = acceptance_is_must;
    }
}
