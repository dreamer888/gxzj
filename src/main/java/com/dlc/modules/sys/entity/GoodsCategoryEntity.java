package com.dlc.modules.sys.entity;

import java.io.Serializable;
import java.util.Date;

public class GoodsCategoryEntity implements Serializable{

    //分类id
    private int id;
    //分类名称
    private String name;
    //排序
    private int sort;
    //创建时间
    private Date createTime;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getSort() {
        return sort;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
