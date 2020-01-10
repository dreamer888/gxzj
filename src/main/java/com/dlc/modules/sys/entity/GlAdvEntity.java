package com.dlc.modules.sys.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 个联广告表
 */
public class GlAdvEntity implements Serializable {
    //个联广告表ID
    private Long id;
    //标题
    private String name;
    //内容
    private String content;
    //图片地址
    private String imgUrl;
    //排序
    private int sort;
    //上下架1.上架0下架
    private Integer status;
    //广告跳转的地址
    private String linkUrl;
    //创建时间
    private Date createTime;

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

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public int getSort() {
        return sort;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
