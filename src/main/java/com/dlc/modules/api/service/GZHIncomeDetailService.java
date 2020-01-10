package com.dlc.modules.api.service;

import com.dlc.modules.api.entity.GzhIncomeDetail;

/**
 * @author chenyuexin
 * @version 1.0
 * @date 2018-07-20 16:22
 */
public interface GZHIncomeDetailService {
    void saveIncome(GzhIncomeDetail incomeDetail);

    //查询某个设备下某个公众号每日关注数量
    int queryOfficialAccountsListCount(String deviceNo, String appId);
}
