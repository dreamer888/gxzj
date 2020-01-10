package com.dlc.modules.api.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.dlc.common.utils.Constant;
import com.dlc.common.utils.DateUtils;
import com.dlc.modules.api.dao.MyOrderMapper;
import com.dlc.modules.api.dao.OrderDetailMapper;
import com.dlc.modules.api.entity.MyOrder;
import com.dlc.modules.api.entity.OrderDetail;
import com.dlc.modules.api.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author 廖修坤
 * @date 2018/7/20 21:27
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    MyOrderMapper myOrderMapper;
    @Autowired
    OrderDetailMapper orderDetailMapper;

    /**
    * 管理端：查询我的订单
    * */
    public List<Map<String, Object>> queryList(Map<String, Object> params) {
        List<Map<String,Object>> list =new ArrayList<>();
        //Map<String,Object> goodsInfo = new HashMap<>();
        List<MyOrder>  myOrderList = myOrderMapper.queryMyOrder(params);//查询我的订单
        if(myOrderList!=null){
            for(int i = 0;i<myOrderList.size();i++){
                Map<String,Object> map = new HashMap<>();
                MyOrder myOrder = myOrderList.get(i);
                if(myOrder.getGoodsSum()!=0){
                    map.put("goodsSum",(myOrder.getGoodsSum().doubleValue())/100);
                }
                if(myOrder.getRealPayment()!=0){
                    map.put("realPayment",(myOrder.getRealPayment().doubleValue())/100);
                }
                map.put("orderNum",myOrder.getOrderNum());
                map.put("orderNo",myOrder.getOrderNo());
                map.put("status",myOrder.getStatus());
                map.put("company",myOrder.getCompany());
                map.put("deliveryTime",myOrder.getDeliveryTime());
                map.put("tradeTime",myOrder.getTradeTime());
                //map.put("orderNum",myOrder.getStatus());
                map.put("logisticsName",myOrder.getLogisticsName());
                map.put("logisticsNo",myOrder.getLogisticsNo());
                map.put("transactionNo",myOrder.getTransactionNo());
                map.put("createTime",myOrder.getCreateTime());
                map.put("payTime",myOrder.getPayTime());
                map.put("producer",myOrder.getProducer());
                map.put("customer",myOrder.getCustomer());
                map.put("addrId",myOrder.getAddrId());
                String orderNo = myOrder.getOrderNo();

                //List<OrderDetail> detailList = myOrder.setOrderDetailList(orderDetailListTemp);
                List<OrderDetail> orderDetailListTemp = orderDetailMapper.queryMyOrderDetailList(orderNo);//根据orderNo查询订单详情
                List<Map<String,Object>> goodsMap = new ArrayList();
                //OrderDetailList
                for(int x = 0;x<orderDetailListTemp.size();x++) {
                    Map<String,Object> OrderDetailList = new HashMap<>();
                    OrderDetail orderDetail = orderDetailListTemp.get(x);
                    Integer money = orderDetail.getGoodsPrice();
                    Double goodsPrice = money.doubleValue()/100;
                    OrderDetailList.put("goodsPrice",goodsPrice);
                    OrderDetailList.put("goodsId",orderDetail.getGoodsId());
                    OrderDetailList.put("imgUrl",orderDetail.getImgUrl());
                    OrderDetailList.put("goodsNum",orderDetail.getGoodsNum());
                    OrderDetailList.put("goodsName",orderDetail.getGoodsName());
                    OrderDetailList.put("categoryId",orderDetail.getCategoryId());
                    OrderDetailList.put("cateName",orderDetail.getCateName());
                    OrderDetailList.put("freight",((orderDetail.getFreight().doubleValue()/100)));
                    goodsMap.add(OrderDetailList);//JsonObject
                }
                map.put("goodsMap",goodsMap);
                list.add(map);
        }
            }
        /*List<Map<String, Object>> list = myOrderMapper.queryList(params);
        for (Map<String, Object> orderMap : list){
            Integer price = (Integer) orderMap.get("goodsPrice");
            goodsMap = new JSONObject();
            goodsInfo = new ArrayList<>();

            Integer realPayment1 = (Integer) orderMap.get("realPayment");
            Double realPayment2 = realPayment1.doubleValue();
            realPayment2 = realPayment2 / 100;

            Integer goods_sum1 = (Integer) orderMap.get("goodsSum");
            Double goodsSumTemp1 = goods_sum1.doubleValue();
            goodsSumTemp1 = goodsSumTemp1 / 100;

            goodsMap.put("name",orderMap.get("name"));
            if(price!=null){goodsMap.put("goodsPrice",price.doubleValue()/100);}
            goodsMap.put("imgUrl",orderMap.get("imgUrl"));
            goodsMap.put("goodsId",orderMap.get("goodsId"));
            goodsMap.put("orderNum",orderMap.get("orderNum"));
            //goodsMap.put("goodsSum",goodsSumTemp1);
            goodsInfo.add(goodsMap);
            orderMap.put("isUserfull",true);
            orderMap.put("goodsInfo",goodsInfo);
            orderMap.put("orderNum",goodsInfo.size());
            orderMap.put("realPayment",realPayment2);
            orderMap.put("goodsSum",goodsSumTemp1);
        }

        for (int i = 0; i < list.size(); i++) {
            String orderNo = list.get(i).get("orderNo").toString();
            Double nowPay = (Double)list.get(i).get("realPayment");
            for (int j = i+1; j <list.size() ; j++) {
                String nextOrderNo = list.get(j).get("orderNo").toString();
                *//*if (nextOrderNo.equals(orderNo)){
                    List<Map<String,Object>> preGoodsInfo = (List<Map<String,Object>>)list.get(i).get("goodsInfo");
                    //将同一个订单好的商品加进来
                    List<Map<String,Object>> nextGoodsInfo = (List<Map<String,Object>>)list.get(j).get("goodsInfo");
                    preGoodsInfo.add(nextGoodsInfo.get(0));
                    //标识当前map为被合并的
                    list.get(j).put("isUserfull",false);
                    //订单头总价相加
                    //nowPay  =  (int)list.get(j).get("realPayment")+nowPay;
                    int  orderNum = preGoodsInfo.size();

                    //list.get(i).put("orderNum",orderNum);

                }*//*
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
        }*/
        return list;
    }

    /**
     * 在线订货：订单详情
     * */
    public List<Map<String, Object>> queryOrderInfo(String orderNo) {
        List<Map<String,Object>> goodsInfo = new ArrayList<>();
        List<Map<String,Object>> list = myOrderMapper.queryOrderInfoById(orderNo);
        if(!CollectionUtils.isEmpty(list)){
           Map<String,Object> map = list.get(0);
           Integer realPayment = (Integer) map.get("realPayment");
           Integer goods_sum1 = (Integer) map.get("goodsSum");
           Double goodsSumTemp1 = (goods_sum1.doubleValue())/100;
           Double realPaymentTemp = realPayment.doubleValue()/100;

            Integer freightTemp = (Integer) map.get("freight");
            Double freight = (freightTemp.doubleValue())/100;


          String province = map.get("province").toString();//省
          String provinceString = "";
          String proTemp[] = province.split(",");
          for (int i =0 ;i<proTemp.length;i++){
             provinceString += proTemp[i].toString();
          }
          map.put("addr",provinceString+map.get("addr"));//省市区详细地址
            //处理之后再存值
            map.put("realPayment",realPaymentTemp);
            map.put("freight",freight);
            map.put("imgUrl",map.get("imgUrl"));
            map.put("goodsSum",goodsSumTemp1);
            int status = Integer.parseInt(map.get("status").toString());

            //待收货
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
        for (Map<String, Object> objectMap : list) {
            goodsMap = new JSONObject();
            goodsMap.put("goodsName",objectMap.get("goodsName"));
            goodsMap.put("imgUrl",objectMap.get("imgUrl"));
            goodsMap.put("goodsId",objectMap.get("goodsId"));
            goodsMap.put("orderNum",objectMap.get("goodsNum"));
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
     * 在线订货：更改订单状态
     * */
    public void update(Map<String, Object> map) {
        myOrderMapper.updateOrderStatus(map);
    }

    /**
     * 总记录条数
     * */
    public int queryListCount(Map<String, Object> map) {
        return orderDetailMapper.queryListCount(map);
    }

}
