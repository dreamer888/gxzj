package com.dlc.modules.sys.service;

import com.dlc.common.utils.R;
import com.dlc.modules.api.entity.ExceptionMessage;
import com.dlc.modules.sys.entity.ExceptionMessageEntity;

import java.util.List;
import java.util.Map;

/***********************************
 *Class by 王楚荣
 *2018/7/23/023
 * **********************************/
public interface ExceptionMessageService {

    List<Map<String,Object>> queryList(Map<String, Object> map);

    int queryTotal(Map<String, Object> map);

    R save(ExceptionMessageEntity exceptionMessage);
}
