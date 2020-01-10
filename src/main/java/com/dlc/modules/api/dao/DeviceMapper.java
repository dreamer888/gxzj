package com.dlc.modules.api.dao;

import com.dlc.modules.api.entity.Device;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface DeviceMapper {
    int deleteByPrimaryKey(Long deviceId);

    int insert(Device record);

    int insertSelective(Device record);

    Device selectByPrimaryKey(Long deviceId);

    int updateByPrimaryKeySelective(Device record);

    int updateByPrimaryKeyWithBLOBs(Device record);

    int updateByPrimaryKey(Device record);

    List<Map<String,Object>> queryDeviceGoods(String deviceNo);

    void updateGoodsInfoByNo(Map<String, Object> map);

    List<Map<String,Object>> queryList(Map<String,Object> params);

    List<Map<String,Object>> queryDeviceDetail(Map<String,Object> params);

    Device queryObjectByDeviceNoAndImei(Device device);

    int offline(List<String> deviceNoList);

    List<Map<String, Object>> findDeviceAddrByImei(String imei);

    List<Map<String,Object>> queryOfficialDeviceList(Map<String, Object> paramMap);

    String queryDeviceAddress(String deviceNo);

    int queryDeviceStatus(String deviceNo);

    List<Map<String, Object>> checkOnline(Double min);
}