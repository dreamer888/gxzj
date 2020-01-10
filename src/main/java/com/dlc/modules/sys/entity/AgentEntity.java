package com.dlc.modules.sys.entity;

import com.dlc.common.validator.group.AddGroup;
import com.dlc.common.validator.group.UpdateGroup;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

public class AgentEntity implements Serializable {
    private Long id;

    private Long parentId;
    /**用户类型*/
    @NotNull(message="类型不能为空",groups = {AddGroup.class})
    private Integer type;
    /**用户昵称*/
    @NotBlank(message="昵称不能为空",groups = {AddGroup.class,UpdateGroup.class})
    private String name;

    private String headImgUrl;
    /**手机号*/
    @NotBlank(message="手机号不能为空",groups = {AddGroup.class})
    private String phone;
    /**用户密码*/
    @NotBlank(message="密码不能为空",groups = {AddGroup.class})
    private String password;

    private Integer wallet = 0;
    /**用户状态*/
    @NotNull(message="状态不能为空",groups = {AddGroup.class})
    private Integer status;

    private Integer role = 0;

    private Integer deleteStatus = 0;//设置默认值

    private String city;
    /**佣金类型*/
    @NotNull(message="佣金类型不能为空",groups = {AddGroup.class})
    private Integer commissionType;
    /**佣金值*/
    @NotNull(message="佣金值不能为空",groups = {AddGroup.class})
    private Integer commissionValue;

    private Date createTime;

    private static final long serialVersionUID = 1L;

    public AgentEntity() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
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

    public Integer getWallet() {
        return wallet;
    }

    public void setWallet(Integer wallet) {
        this.wallet = wallet;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public Integer getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(Integer deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getCommissionType() {
        return commissionType;
    }

    public void setCommissionType(Integer commissionType) {
        this.commissionType = commissionType;
    }

    public Integer getCommissionValue() {
        return commissionValue;
    }

    public void setCommissionValue(Integer commissionValue) {
        this.commissionValue = commissionValue;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", parentId=").append(parentId);
        sb.append(", type=").append(type);
        sb.append(", name=").append(name);
        sb.append(", headImgUrl=").append(headImgUrl);
        sb.append(", phone=").append(phone);
        sb.append(", password=").append(password);
        sb.append(", wallet=").append(wallet);
        sb.append(", status=").append(status);
        sb.append(", role=").append(role);
        sb.append(", deleteStatus=").append(deleteStatus);
        sb.append(", city=").append(city);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}