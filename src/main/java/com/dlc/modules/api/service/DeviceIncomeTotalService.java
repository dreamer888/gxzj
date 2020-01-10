package com.dlc.modules.api.service;

import java.util.List;
import java.util.Map;

/***********************************
 *Class by 王楚荣
 *2018/7/18/018
 * **********************************/
public interface DeviceIncomeTotalService {

    List<Map<String,Object>> queryList(Map<String,Object> params);

    Map<String,Object> queryTotal(Map<String, Object> params);

    int queryCount(Map<String,Object> params);
}
