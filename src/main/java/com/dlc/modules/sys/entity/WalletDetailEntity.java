package com.dlc.modules.sys.entity;

import java.io.Serializable;
import java.util.Date;


/**
 * 钱包明细表
 * 
 * @author dlc.dg.java
 * @email dlc.dg.java@163.com
 * @date 2018-07-24 13:42:27
 */
public class WalletDetailEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//明细id
	private Long id;
	//用户ID
	private Long userId;
	//明细类型（1 提现 2销售收入 3充值 4广告消费）
	private Integer type;
	//金额数
	private Integer money;
	//提现到的微信号
	private String wxCount;
	//流水号
	private String transactionNumber;
	//失败原因
	private String reason;
	//状态（1为审核中 2 审核失败 3 已完成）
	private Integer status;
	//创建时间
	private Date createTime;

	private Date checkedTime;

	private Date transactionTime;
    //用户账号(代理商)
	private String phone;

    private String orderNo;

    private String openId;

    private Integer userType;

    private String username;
    /**
	 * 设置：明细id
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：明细id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：用户ID
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * 获取：用户ID
	 */
	public Long getUserId() {
		return userId;
	}
	/**
	 * 设置：明细类型（1 提现 2销售收入 3充值 4广告消费）
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * 获取：明细类型（1 提现 2销售收入 3充值 4广告消费）
	 */
	public Integer getType() {
		return type;
	}
	/**
	 * 设置：金额数
	 */
	public void setMoney(Integer money) {
		this.money = money;
	}
	/**
	 * 获取：金额数
	 */
	public Integer getMoney() {
		return money;
	}
	/**
	 * 设置：提现到的微信号
	 */
	public void setWxCount(String wxCount) {
		this.wxCount = wxCount;
	}
	/**
	 * 获取：提现到的微信号
	 */
	public String getWxCount() {
		return wxCount;
	}
	/**
	 * 设置：流水号
	 */
	public void setTransactionNumber(String transactionNumber) {
		this.transactionNumber = transactionNumber;
	}
	/**
	 * 获取：流水号
	 */
	public String getTransactionNumber() {
		return transactionNumber;
	}
	/**
	 * 设置：失败原因
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}
	/**
	 * 获取：失败原因
	 */
	public String getReason() {
		return reason;
	}
	/**
	 * 设置：状态（1为审核中 2 审核失败 3 已完成）
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：状态（1为审核中 2 审核失败 3 已完成）
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置：创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Date getCheckedTime() {
        return checkedTime;
    }

    public void setCheckedTime(Date checkedTime) {
        this.checkedTime = checkedTime;
    }

    public Date getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(Date transactionTime) {
        this.transactionTime = transactionTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }
}
