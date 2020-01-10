package com.dlc.modules.api.service;

import com.dlc.common.utils.Query;
import com.dlc.modules.api.entity.UserConsume;
import net.sf.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * @author chenyuexin
 * @version 1.0
 * @date 2018-07-14 15:14
 */
public interface UserConsumeService {

    //创建用户消费订单
    String save(UserConsume userConsume);

    //查询用户消费订单记录
    List<Map<String, Object>> queryUserOrderList(Map<String, Object> map);

    int queryUserOrderCount(Map<String, Object> map);

    //查詢用户订单详情
    Map<String, Object> queryUserOrderInfo(Long id);

    //修改订单状态
    Integer updateOrder(String orderNo, String tradeNo, Integer payStatus);

    Map<String,Object> queryDeviceImei(String orderNo);

    Integer queryUserOrderStatus(String orderNo);

    UserConsume queryUserOrderInfoOrderNo(String orderNo);
}
