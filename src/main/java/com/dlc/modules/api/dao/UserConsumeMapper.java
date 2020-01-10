package com.dlc.modules.api.dao;

import com.dlc.modules.api.entity.UserConsume;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UserConsumeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserConsume record);

    int insertSelective(UserConsume record);

    UserConsume selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserConsume record);

    int updateByPrimaryKey(UserConsume record);

    List<Map<String,Object>> queryUserOrderList(Map<String, Object> map);

    int queryUserOrderCount(Map<String, Object> map);

    Map<String,Object> queryUserOrderInfo(Long id);

    Map<String,Object> queryDeviceImei(String orderNo);

    int updateStatusAndTransactionNo(UserConsume userConsume);

    Integer queryUserOrderStatus(String orderNo);

    UserConsume queryUserOrderInfoOrderNo(String orderNo);
}