package com.dlc.modules.api.service;

import com.dlc.modules.api.entity.UserInfo;

import java.util.Map;

/**
 * 用户表
 * 
 * @author dlc.dg.java
 * @email dlc.dg.java@163.com
 * @date 2018-07-13 10:36:23
 */
public interface UserInfoService {
	
	/*UserInfo queryObject(Long id);
	
	List<UserInfo> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);*/
	
	void save(Map<String,Object> map);

    Map<String,Object> queryUserInfo(String openId);

    UserInfo queryUserAppId(String appid, String openid);

    void updateUserAppId(String openid, String appid);

    void updateUserCount(String openid);

    void updateUserCountToZero();

	
/*	void update(UserInfo userInfo);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);*/
}
