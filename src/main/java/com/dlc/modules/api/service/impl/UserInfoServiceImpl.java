package com.dlc.modules.api.service.impl;

import com.dlc.modules.api.dao.UserInfoMapper;
import com.dlc.modules.api.entity.UserInfo;
import com.dlc.modules.api.service.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service("userInfoService")
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    private UserInfoMapper userInfoMapper;

    private static Logger log = LoggerFactory.getLogger(UserInfoServiceImpl.class);

/*	@Override
    public UserInfoEntity queryObject(Long id){
		return userInfoDao.queryObject(id);
	}
	
	@Override
	public List<UserInfoEntity> queryList(Map<String, Object> map){
		return userInfoDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return userInfoDao.queryTotal(map);
	}*/

    @Override
    public void save(Map<String, Object> jsonObject) {
        String openId = jsonObject.get("openid").toString();
        UserInfo userInfo = new UserInfo();
        userInfo.setCity(jsonObject.get("city").toString());
        userInfo.setHeadImgUrl(jsonObject.get("headimgurl").toString());
        userInfo.setName(jsonObject.get("nickname").toString());
        userInfo.setProvince(jsonObject.get("province").toString());
        userInfo.setOpenId(openId);
        userInfo.setSex((int) jsonObject.get("sex"));


        Map<String, Object> map = userInfoMapper.queryUserInfo(openId);
        if (null != map) {
            //每次进来对数据库进行更新
            int count = userInfoMapper.updateUserByOpenId(userInfo);
            if (count > 0){
                log.info("+++++++++++更新用户信息成功++++++++++++"+userInfo.toString());

            }else {
                log.info("-------------更新用户信息失败----------");
            }
            return;
        }
        userInfoMapper.insertSelective(userInfo);
    }

    @Override
    public Map<String, Object> queryUserInfo(String openId) {
        return userInfoMapper.queryUserInfo(openId);

    }

    @Override
    public UserInfo queryUserAppId(String appid, String openid) {
        Map<String, Object> map = new HashMap<>();
        map.put("appid", appid);
        map.put("openId", openid);
        return userInfoMapper.queryUserAppId(map);
    }

    @Override
    public void updateUserAppId(String openid, String appid) {
        Map<String, Object> map = new HashMap<>();
        map.put("openId", openid);
        map.put("appid", appid);
        userInfoMapper.updateUserAppId(map);
    }

    @Override
    public void updateUserCount(String openid) {
        //查询出当前用户领取数量
        Map<String, Object> map = userInfoMapper.queryUserInfo(openid);
        int count = Integer.parseInt(map.get("count").toString());
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("openId", openid);
        paramMap.put("count", count + 1);
        userInfoMapper.updateUserCount(paramMap);
    }

    @Override
    public void updateUserCountToZero() {
        userInfoMapper.updateUserCountZero();
    }

	/*@Override
    public void update(UserInfoEntity userInfo){
		userInfoDao.update(userInfo);
	}
	
	@Override
	public void delete(Long id){
		userInfoDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		userInfoDao.deleteBatch(ids);
	}*/

}
