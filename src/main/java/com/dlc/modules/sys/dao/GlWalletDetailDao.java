package com.dlc.modules.sys.dao;


import com.dlc.modules.api.entity.GlWalletDetail;
import com.dlc.modules.sys.entity.GlWalletDetailEntity;

import java.util.List;
import java.util.Map;

public interface GlWalletDetailDao extends BaseDao<GlWalletDetailEntity>{

    Integer moneyTotal();

    List<Map<String,Object>> payment(Map<String, Object> params);

    int paymentTotal(Map<String, Object> map);
    //广告充值记录
    void insertGwInfo(GlWalletDetail gw);
}