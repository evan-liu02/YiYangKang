package com.anju.yyk.common.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

@Entity
public class CommonType {
    @Id
    private Long id;

    @Property
    private String type_id;

    @Property
    private String name;

    @Generated(hash = 2064126151)
    public CommonType(Long id, String type_id, String name) {
        this.id = id;
        this.type_id = type_id;
        this.name = name;
    }

    @Generated(hash = 1811727925)
    public CommonType() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType_id() {
        return this.type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
