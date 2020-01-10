package com.dlc.modules.api.dao;

import com.dlc.modules.api.entity.Advertising;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface AdvertisingMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Advertising record);

    int insertSelective(Map<String,Object> pamars);

    Advertising selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Advertising record);

    int updateByPrimaryKeyWithBLOBs(Advertising record);

    int updateByPrimaryKey(Advertising record);
    // 广告列表
    List<Advertising> queryAdvertisingList(Map<String, Object> params);
    //广告条数
    int queryTotal(Map<String, Object> params);

    Advertising selectAdvertisingId(Long advId);
    

    //轮播图
    List<Map<String,Object>> sowingMapList(Map<String, Object> params);

    List<Advertising> findAdByStatus();
    //查询广告
    Advertising selectAdById(Long advId);
    //查询该用户名下所有绑定的且状态为已结束且当前时间在广告的投放时间之内的 （status= 4） 的广告
    List<Advertising> selectAdList();

    Map<String,Object> selectAdvertising(Long id);

    List<Advertising> selectAdvByStatus(Long userId);

    List<Advertising> selectAllAdvList();
}