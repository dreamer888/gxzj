package com.dlc.modules.sys.service;

import java.util.List;
import java.util.Map;

/***********************************
 *Class by 王楚荣
 *2018/7/21/021
 * **********************************/
public interface AgentService {
    List<Map<String,Object>> queryListByType(Map<String,Object> map);
}
