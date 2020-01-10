package com.dlc.modules.api.service;

import com.dlc.common.utils.R;
import com.dlc.modules.api.entity.Agent;
import com.dlc.modules.api.entity.MyOrder;
import com.dlc.modules.api.entity.OrderDetail;
import com.dlc.modules.api.entity.WalletDetail;
import sun.management.resources.agent;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

/**
 * @Auther:YD
 * @Date: Creat in  2018/7/19/019
 */
public interface WalletDetailService {
    //钱包明细列表
    List<Map<String,Object>> wdList(Map<String, Object> params);
    //列表条数统计
    int queryTotal(Map<String, Object> params);
    //钱包明细详情
    Map<String,Object> queryWdInfo(Long id);
    //充值
    SortedMap<String,String> rechargePay(String openId, BigDecimal money, String orderNo) throws Exception;

    //储存充值状态
    int updateWalletDetail(String orderNo, int wallet, String transaction_id);
    //创建充值订单
    String creatRcharge(Long userId);

    Map<String,Object> balancePayment(MyOrder myOrder,String notify_url);//增加供货端余额，减少管理端余额

    int addMoneyById(Map<String,Object> maps);//供货端增加余额

    int addMsgToglWallet(BigDecimal money);//个联钱包明细增加条记录

    int addWalletDetail(Map<String,Object> temp);//钱包明细增加条记录
    //提现
    int withdrawDeposit(Map<String,Object> params);

    int editMsgToglWallet(BigDecimal money);//个联钱包修改金额

    int addMoneyById2(Map<String, Object> map);//供货端增加余额（微信支付）

    List<OrderDetail> queryOrderDetailByOderNo(String out_trade_no);

    int addMonthlySales(Map<String, Object> goodsMap);//月销量增加
}
