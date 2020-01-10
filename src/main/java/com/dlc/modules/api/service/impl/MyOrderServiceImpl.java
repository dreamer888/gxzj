package com.dlc.modules.api.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.dlc.common.utils.Constant;
import com.dlc.common.utils.DateUtils;
import com.dlc.modules.api.dao.MyOrderMapper;
import com.dlc.modules.api.entity.Address;
import com.dlc.modules.api.entity.MyOrder;
import com.dlc.modules.api.entity.OrderDetail;
import com.dlc.modules.api.service.MyOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author 廖修坤
 * @date 2018/7/17 18:42
 */
@Service("MyOrderService")
public class MyOrderServiceImpl  implements MyOrderService {
    @Autowired
    MyOrderMapper myOrderMapper;
    private Logger log = LoggerFactory.getLogger(getClass());
    /**
     * 供货端：订单管理查询
     * */
    public List<Map<String,Object>> queryMyOrderList(Map<String, Object> params) {
        String orderNo = (String) params.get("orderNo");
        JSONObject goodsMap = null;
        List<Map<String,Object>> goodsInfo =null;
        List<Map<String,Object>> list=myOrderMapper.queryMyOrderList(params);
        //return myOrderMapper.queryMyOrderList(userId,status,pageNum,pageSize);
        for (Map<String, Object> orderMap : list) {
            log.info("orderMap---->"+orderMap);
            Integer price = (Integer) orderMap.get("goodsPrice");
            goodsMap = new JSONObject();
            goodsInfo = new ArrayList<>();
            //数据处理
            //商品总价
            Integer goods_sum1 = (Integer) orderMap.get("goodsSum");
            Double goodsSumTemp1 = goods_sum1.doubleValue();
            goodsSumTemp1 = goodsSumTemp1 / 100;
            //实付款
            Integer real_Payment = (Integer) orderMap.get("realPayment");
            Double realPayment = (real_Payment.doubleValue())/100;

            goodsMap.put("goodsName",orderMap.get("goodsName"));//商品名称
            if(price!=null){
                goodsMap.put("price",price.doubleValue()/100);//单价
            }
            goodsMap.put("imgUrl",orderMap.get("imgUrl"));//图片路径
            goodsMap.put("goodsId",orderMap.get("id"));//商品Id
            goodsMap.put("goodsNum",orderMap.get("goodsNum"));//商品数量
            goodsMap.put("goodsSum",goodsSumTemp1);//商品总价

            goodsInfo.add(goodsMap); //存入了orderMap信息
            orderMap.put("isUserfull",true);
            orderMap.put("goodsInfo",goodsInfo);
            orderMap.put("orderNum",goodsInfo.size());
            if(price!=null){
                orderMap.put("goodsPrice",price.doubleValue()/100);
            }
            orderMap.put("goodsSum",goodsSumTemp1);
            orderMap.put("realPayment",realPayment);
            orderMap.remove("img_url");
        }
        for (int i = 0; i < list.size(); i++) {
            orderNo = list.get(i).get("orderNo").toString();
            Double nowPay = (Double) list.get(i).get("realPayment");
            for (int j = i+1; j <list.size() ; j++) {
                String nextOrderNo = list.get(j).get("orderNum").toString();
                if (nextOrderNo.equals(orderNo)){
                    List<Map<String,Object>> preGoodsInfo = (List<Map<String,Object>>)list.get(i).get("goodsInfo");
                    //将同一个订单号的商品加进来
                    List<Map<String,Object>> nextGoodsInfo = (List<Map<String,Object>>)list.get(j).get("goodsInfo");
                    preGoodsInfo.add(nextGoodsInfo.get(0));
                    //标识当前map为被合并的
                    list.get(j).put("isUserfull",false);
                    //订单头总价相加
                    nowPay  =  (int)list.get(j).get("realPayment")+nowPay;
                    int  orderNum = preGoodsInfo.size();

                    list.get(i).put("orderNum",orderNum);

                }
            }
            list.get(i).put("realPayment",nowPay.doubleValue());
        }

        //去掉重复的订单
        ListIterator<Map<String, Object>> iterator = list.listIterator();
        while (iterator.hasNext()){
            Map<String, Object> nextMap = iterator.next();
            if (!(Boolean)nextMap.get("isUserfull")){
                iterator.remove();
            }
        }
        return list;
         //return orderMap;
    }

    /**
     * 根据addrId查询地址详情
     * */
    /*public List<Address> queryAddressByUserId(Long addrId,Byte status) {
        return myOrderMapper.queryAddressByUserId(addrId,status);
    }*/

    /**
     * 根据userId查询订单详情
     * */
    /*public List<OrderDetail> queryDetailList(String orderNo) {
        return myOrderMapper.queryDetailList(orderNo);
    }*/

    /**
     * 保存物流信息
     * */
    public int AddLogistics(MyOrder myOrder) {
        return myOrderMapper.AddLogistics(myOrder);
    }

    /**
     * 修改订单状态
     * */
    public int ChangeOrderStatusByOrderNo(String orderNo,Byte status) {
        //判断status为1时，增加发货时间deliveryTime
        Date deliveryTime = null;
        if(status==2){
             deliveryTime = new Date();
        }
        return myOrderMapper.ChangeOrderStatusByOrderNo(orderNo,status,deliveryTime);
    }

    /**
     * //添加订单
     * */
    public int AddMyOrder(MyOrder myOrder) {
        Integer goodsSum =myOrder.getGoodsSum();
        Integer realPayment = myOrder.getRealPayment();
        myOrder.setGoodsSum(goodsSum);
        myOrder.setRealPayment(realPayment);
        return myOrderMapper.AddMyOrder(myOrder);
    }

    /**
     * 添加订单详情
     * */
    public int addOrderDetails(OrderDetail orderDetail) {
        //price
        Integer price = orderDetail.getGoodsPrice();
        orderDetail.setGoodsPrice(price);
        return myOrderMapper.addOrderDetails(orderDetail);
    }

    /**
     * 订单详情
     * */
    public List<Map<String,Object>> queryOrderInfo(String orderNo){
        //金额转换：分（数据库）转换成元（用户）

        List<Map<String,Object>> goodsInfo = new ArrayList<>();

        List<Map<String, Object>> list = myOrderMapper.queryOrderInfoById(orderNo);

        if (!CollectionUtils.isEmpty(list)) {
            Map<String, Object> map = list.get(0);

            Integer real_payment = (Integer) map.get("realPayment");
            Double realPaymentTemp = real_payment.doubleValue();
            realPaymentTemp = realPaymentTemp / 100;
            map.put("realPayment", realPaymentTemp);

            String province = map.get("province").toString();//省
            String provinceString = "";
            String proTemp[] = province.split(",");
            for (int i =0 ;i<proTemp.length;i++){
                provinceString += proTemp[i].toString();
            }
            map.put("addr",provinceString+map.get("addr"));//省市区详细地址

            Integer goods_sum1 = (Integer) map.get("goodsSum");
            Double goodsSumTemp1 = goods_sum1.doubleValue();
            goodsSumTemp1 = goodsSumTemp1 / 100;
            map.put("goodsSum",goodsSumTemp1);

            map.put("imgUrl",map.get("imgUrl"));
            int status = Integer.parseInt(map.get("status").toString());
            // 待付款
            if (status== Constant.OrderStatus.WAIT_PAY.getValue()){
                SimpleDateFormat format = new SimpleDateFormat("yyyy-M-dd HH:mm:ss");
                long canceTime = 0;
                try {
                    canceTime = format.parse(map.get("createTime").toString()).getTime()+24*60*60*1000;
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //  int hours = DateUtils.getHours(new Date().getTime(), canceTime);
                int totalMin = DateUtils.getMin(new Date().getTime(), canceTime);
                int hour = (totalMin%(24*60))/60;
                int minute = (totalMin%(24*60))%60;
                map.put("waitCanceHours",hour);
                map.put("waitCanceMin",minute);
            }
            //待确认收货
            if (status==Constant.OrderStatus.WAIT_RECIVE.getValue()){
                SimpleDateFormat format = new SimpleDateFormat("yyyy-M-dd HH:mm:ss");
                long canceTime = 0;
                try {
                    //15天后
                    canceTime = format.parse(map.get("createTime").toString()).getTime()+24*60*60*1000*15;
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                int totalMin = DateUtils.getMin(new Date().getTime(), canceTime);
                int hour = (totalMin%(24*60))/60;
                int minute = (totalMin%(24*60))%60;
                int day = totalMin/(24*60);
                map.put("waitConfirmHours",hour);
                map.put("waitConfirmMin",minute);
                map.put("waitConfirmDay",day);
            }

            JSONObject goodsMap = null;

            Integer realPay = 0;
            for (Map<String, Object> objectMap : list) {
                goodsMap = new JSONObject();
                //Integer price = (Integer) objectMap.get("price");goodsPrice
                Integer price = (Integer) objectMap.get("goodsPrice");
                goodsMap.put("goodsName",objectMap.get("goodsName"));
                goodsMap.put("price",price.doubleValue()/100);
                goodsMap.put("imgUrl",objectMap.get("imgUrl"));
                goodsMap.put("goodsId",objectMap.get("goodsId"));
                goodsMap.put("orderNum",objectMap.get("goodsNum"));
                goodsMap.put("categoryName",objectMap.get("categoryName"));
                goodsInfo.add(goodsMap);
            }
            map.put("goodsInfo",goodsInfo);
            map.put("realPayment",realPaymentTemp);
            list.clear();
            map.put("orderNum",goodsInfo.size());
            list.add(map);


        }


        return list;
    }

    /**
     * 根据订单号查询出订单详情
     * */
    public List<Map<String,Object>> queryOrderInfoByOrderNo(String out_trade_no) {
        return myOrderMapper.queryOrderInfoByOrderNo(out_trade_no);
    }

    /**
     * 总记录数
     * */
    public int queryMyOrderListCount(Map<String, Object> map) {
        return myOrderMapper.queryMyOrderListCount(map);
    }

    /**
     * 修改订单
     * */
    public int editOrder(MyOrder myOrder) {
        return myOrderMapper.editOrder(myOrder);
    }
}
