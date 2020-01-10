package com.dlc.modules.sys.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/***********************************
 *Class by 王楚荣
 *2018/7/20/020
 * **********************************/

public interface UserConsumeService {
    List<Map<String,Object>> queryList(Map<String, Object> map);

    int queryTotal(Map<String, Object> map);
}
