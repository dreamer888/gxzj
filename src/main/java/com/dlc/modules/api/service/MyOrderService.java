package com.dlc.modules.api.service;

import com.dlc.common.utils.Query;
import com.dlc.modules.api.entity.Address;
import com.dlc.modules.api.entity.MyOrder;
import com.dlc.modules.api.entity.OrderDetail;

import java.util.List;
import java.util.Map;

public interface MyOrderService {
    List<Map<String,Object>> queryMyOrderList(Map<String, Object> params);//订单管理

    //List<Address> queryAddressByUserId(Long addrId,Byte status);//根据addrId查询地址详情

    //List<OrderDetail> queryDetailList(String orderNo);//订单详情

    int AddLogistics(MyOrder myOrder);//保存物流信息

    int ChangeOrderStatusByOrderNo(String orderNo,Byte status);//修改订单状态

    int AddMyOrder(MyOrder myOrder);//订单管理添加

    int addOrderDetails(OrderDetail orderDetail);//添加订单详情

    List<Map<String,Object>> queryOrderInfo(String orderNo);//订单详情

    List<Map<String,Object>> queryOrderInfoByOrderNo(String out_trade_no);//根据订单号查询出订单详情

    int queryMyOrderListCount(Map<String, Object> map);//总记录数

    int editOrder(MyOrder myOrder);//修改订单
}
