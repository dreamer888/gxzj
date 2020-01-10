package com.dlc.modules.sys.dao;


import com.dlc.modules.api.entity.GlWallet;
import com.dlc.modules.sys.entity.GlWalletEntity;

import java.util.List;
import java.util.Map;

public interface GlWalletDao extends BaseDao<GlWalletEntity>{

    Integer moneyTotal();

    List<Map<String,Object>> payment(Map<String,Object> params);

    int paymentTotal(Map<String, Object> map);

    //更新个联钱包余额
    int updateBalance(Integer nowBalance);
}