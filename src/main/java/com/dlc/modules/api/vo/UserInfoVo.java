package com.dlc.modules.api.vo;

import com.dlc.common.validator.group.AddGroup;
import com.dlc.common.validator.group.LoginGroup;
import com.dlc.common.validator.group.UpdateGroup;

import javax.validation.constraints.NotNull;
import java.io.Serializable;


public class UserInfoVo implements Serializable {
    private Long id;
    /**用户昵称*/
    private String name;
    /**手机号*/
    private String phone;
    @NotNull(message="密码不能为空",groups = {AddGroup.class,UpdateGroup.class, LoginGroup.class})
    private String password;
    /**性别，默认0，未知性别，1男，2女*/
    private Integer sex;
    /**头像*/
    private String headImgUrl;
    /**审核状态*/
    private Integer auditStatus;
    /**状态*/
    private Integer status;


    private static final long serialVersionUID = 1L;

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}