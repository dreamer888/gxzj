package com.dlc.modules.api.dao;

import com.dlc.modules.api.entity.UserInfo;
import org.springframework.stereotype.Repository;

import java.util.Map;
@Repository
public interface UserInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserInfo record);

    int insertSelective(UserInfo record);

    UserInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserInfo record);

    int updateByPrimaryKey(UserInfo record);

    Map<String,Object> queryUserInfo(String openId);

    UserInfo queryUserAppId(Map<String, Object> map);

    void updateUserAppId(Map<String, Object> map);

    void updateUserCount(Map<String, Object> paramMap);

    void updateUserCountZero();

    int updateUserByOpenId(UserInfo userInfo);
}