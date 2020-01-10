package com.dlc.modules.api.service;

/**
 * @author chenyuexin
 * @version 1.0
 * @date 2018-07-21 13:35
 */
public interface CommissionDetailService {
    /**
     * 保存每个代理的佣金明细（单位分）
     */
    void saveCommissionDetail(Integer money, String deviceNo, String deviceImei, String orderNo, Integer goodsNum);
}
