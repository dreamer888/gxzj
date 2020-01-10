package com.dlc.modules.api.service.impl;

import com.dlc.common.utils.OrderNoGenerator;
import com.dlc.modules.api.dao.UserConsumeMapper;
import com.dlc.modules.api.entity.UserConsume;
import com.dlc.modules.api.service.UserConsumeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author chenyuexin
 * @version 1.0
 * @date 2018-07-14 15:18
 */
@Service
@Transactional
public class UserConsumeServiceImpl implements UserConsumeService {
    private final Logger logger = LoggerFactory.getLogger(UserConsumeServiceImpl.class);
    @Autowired
    private UserConsumeMapper userConsumeMapper;

    @Override
    public String save(UserConsume userConsume) {
        String orderNo = OrderNoGenerator.getOrderIdByTime();
        userConsume.setOrderNo(orderNo);

        userConsume.setCreateTime(new Date());
        userConsumeMapper.insertSelective(userConsume);
        return orderNo;
    }

    @Override
    public List<Map<String, Object>> queryUserOrderList(Map<String, Object> map) {
        List<Map<String, Object>> list = userConsumeMapper.queryUserOrderList(map);
        for (Map<String, Object> order : list) {
            Integer price = (Integer) order.get("price");
            Integer totalPrice = (Integer) order.get("totalPrice");
            order.put("totalPrice", totalPrice.doubleValue() / 100);
            order.put("price", price.doubleValue() / 100);
        }

        return list;
    }

    @Override
    public int queryUserOrderCount(Map<String, Object> map) {
        return userConsumeMapper.queryUserOrderCount(map);
    }

    @Override
    public Map<String, Object> queryUserOrderInfo(Long id) {
        return userConsumeMapper.queryUserOrderInfo(id);
    }

    @Override
    public Integer updateOrder(String orderNo, String tradeNo, Integer payStatus) {
        UserConsume userConsume = new UserConsume();
        userConsume.setOrderNo(orderNo);
        userConsume.setStatus(payStatus);
        userConsume.setTransactionNo(tradeNo);
       return userConsumeMapper.updateStatusAndTransactionNo(userConsume);
    }

    @Override
    public Map<String, Object> queryDeviceImei(String orderNo) {
        return userConsumeMapper.queryDeviceImei(orderNo);
    }

    @Override
    public Integer queryUserOrderStatus(String orderNo) {
        return userConsumeMapper.queryUserOrderStatus(orderNo);

    }

    @Override
    public UserConsume queryUserOrderInfoOrderNo(String orderNo) {
        return userConsumeMapper.queryUserOrderInfoOrderNo(orderNo);
    }
}
