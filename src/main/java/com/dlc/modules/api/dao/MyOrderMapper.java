package com.dlc.modules.api.dao;

import com.dlc.modules.api.entity.Address;
import com.dlc.modules.api.entity.Goods;
import com.dlc.modules.api.entity.MyOrder;
import com.dlc.modules.api.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;
@Mapper
@Repository
public interface MyOrderMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MyOrder record);

    int insertSelective(MyOrder record);

    MyOrder selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MyOrder record);

    int updateByPrimaryKey(MyOrder record);

    List<Map<String,Object>> queryMyOrderList(Map<String, Object> map);//订单管理查询

    //List<Address> queryAddressByUserId(@Param("addrId")Long addrId,@Param("status") Byte status);//根据userId查询地址详情

    //List<OrderDetail> queryDetailList(String orderNo);//根据UserId查询订单详情

    int AddLogistics(MyOrder myOrder);//保存物流信息

    int ChangeOrderStatusByOrderNo(@Param("orderNo")String orderNo,@Param("status") Byte status,@Param("deliveryTime") Date deliveryTime);//修改订单状态

    int addOrderDetails(OrderDetail orderDetail);//添加订单详情

    int AddMyOrder(MyOrder myOrder);

    /*++++++++++++++++++++++++++++++++++++++++++*/
    /**
     * @author: 廖修坤
     * @date 2018/7/20

     * @return
     * @throws
     * @since
     * @description:管理端：订单
    */

    List<Map<String,Object>> queryList(Map<String, Object> params);//查询我的订单

    List<Map<String,Object>> queryOrderInfoById(String orderNo);

    void updateOrderStatus(Map<String, Object> map);//在线订货：更改订单状态

    List<Map<String,Object>> queryOrderInfoByOrderNo(String out_trade_no);//根据订单号查询出订单详情

    int queryMyOrderListCount(Map<String, Object> map);//总记录数

    int queryListCount(Map<String, Object> map);//总记录条数

    int editOrder(MyOrder myOrder);//修改订单

    int editGoodsById(Goods goods);//编辑商品:根据商品ID编辑商品内容

    int delShoppingCarById(@Param("goodsId") Long goodsId,@Param("customer")Long customer);//删除购物车

    int upOrdreStatus(@Param("orderNo") String orderNo,@Param("transactionNumber") String transactionNumber);//余额支付成功,更改status

    List<MyOrder>  queryMyOrder(Map<String, Object> params);

    List<OrderDetail> queryMyOrderDetailList(String orderNo);//根据orderNo查询订单详情

}