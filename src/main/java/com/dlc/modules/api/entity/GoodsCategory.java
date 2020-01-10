package com.dlc.modules.api.entity;

import java.io.Serializable;
import java.util.Date;

public class GoodsCategory implements Serializable {
    private Integer id;

    private String name;

    private Integer sort;

    private Date create_time;

    private static final long serialVersionUID = 1L;

    public GoodsCategory(Integer id, String name, Integer sort, Date create_time) {
        this.id = id;
        this.name = name;
        this.sort = sort;
        this.create_time = create_time;
    }

    public GoodsCategory() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", sort=").append(sort);
        sb.append(", create_time=").append(create_time);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}