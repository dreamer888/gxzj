package com.dlc.modules.sys.service;

import java.util.List;
import java.util.Map;

/***********************************
 *Class by 王楚荣
 *2018/7/20/020
 * **********************************/
public interface GlWalletService {

    List<Map<String,Object>> queryList(Map<String, Object> map);

    int queryTotal(Map<String, Object> map);

    int moneyTotal();

    List<Map<String,Object>> payment(Map<String,Object> params);

    int paymentTotal(Map<String, Object> map);
    //广告端充值时个联钱包余额添加 新的余额 = 余额 + 充值的金额
    void updateGlWallet(int wallet);
}
