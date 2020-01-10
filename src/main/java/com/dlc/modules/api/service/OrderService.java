package com.dlc.modules.api.service;

import com.dlc.common.utils.Query;

import java.util.List;
import java.util.Map;

/**
 * @author 廖修坤
 * @date 2018/7/20 21:26
 */
public interface OrderService {

    List<Map<String,Object>> queryList(Map<String, Object> params);//查询我的订单

    List<Map<String,Object>> queryOrderInfo(String orderNo);//在线订货：订单详情

    void update(Map<String, Object> map);//在线订货：更改订单状态

    int queryListCount(Map<String, Object> map);//总记录数查询
}
