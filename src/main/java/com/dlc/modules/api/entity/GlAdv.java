package com.dlc.modules.api.entity;

import java.io.Serializable;
import java.util.Date;

public class GlAdv implements Serializable {
    private Long id;

    private String name;

    private String content;

    private String imgUrl;

    private Integer sort;

    private Integer status;

    private String linkUrl;

    private Date createTime;

    private static final long serialVersionUID = 1L;

    public GlAdv(Long id, String name, String content, String imgUrl, Integer sort, Integer status, String linkUrl, Date createTime) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.imgUrl = imgUrl;
        this.sort = sort;
        this.status = status;
        this.linkUrl = linkUrl;
        this.createTime = createTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    @Override
    public String toString() {
        return "GlAdv{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", content='" + content + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", sort=" + sort +
                ", status=" + status +
                ", linkUrl='" + linkUrl + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}