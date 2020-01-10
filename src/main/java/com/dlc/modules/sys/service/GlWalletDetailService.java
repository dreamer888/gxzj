package com.dlc.modules.sys.service;

import com.dlc.modules.sys.entity.GlWalletDetailEntity;

import java.util.List;
import java.util.Map;

/***********************************
 *Class by 王楚荣
 *2018/7/20/020
 * **********************************/
public interface GlWalletDetailService {

    GlWalletDetailEntity queryObject(Long agencyId);

    List<Map<String,Object>> queryList(Map<String, Object> map);

    int queryTotal(Map<String, Object> map);

    int moneyTotal();

    List<Map<String,Object>> payment(Map<String, Object> params);

    int paymentTotal(Map<String, Object> map);
    //个联钱包充值明细
    void insertGwInfo(int wallet);
}
