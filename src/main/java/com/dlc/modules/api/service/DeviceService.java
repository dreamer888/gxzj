package com.dlc.modules.api.service;

import com.dlc.common.utils.R;
import com.dlc.modules.api.entity.Device;

import java.util.List;
import java.util.Map;

/**
 * @author chenyuexin
 * @version 1.0
 * @date 2018-07-13 19:26
 */
public interface DeviceService {
    /**
     * 获得该设备下的商品信息
     * @param deviceNo
     * @return
     */
    R queryDeviceGoods(String deviceNo);

    //减库存
    R updateGoodsNum(String deviceNo,int num);

    List<Map<String,Object>> queryList(Map<String,Object> params);

    List<Map<String,Object>> queryDeviceDetail(Map<String,Object> params);
    /**
     * 根据imei 查找 设备地址
     * @param imei
     * @return
     */
    List<Map<String,Object>> findDeviceAddrByImei(String imei);

    List<Map<String,Object>> queryOfficialDeviceList(Integer type, Long agentId);

    void deleteR(String deviceNo,Long agentId);

    R updateDeviceInfo(Device device, Long opsId, String opsPhone);

    String queryDeviceAddress(String deviceNo);

    void update(Device device);

    int queryDeviceStatus(String deviceNo);
}
