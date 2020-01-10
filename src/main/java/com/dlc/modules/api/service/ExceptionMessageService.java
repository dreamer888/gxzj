package com.dlc.modules.api.service;

import com.dlc.modules.api.entity.ExceptionMessage;

import java.util.List;
import java.util.Map;

/***********************************
 *Class by 王楚荣
 *2018/7/19/019
 * **********************************/
public interface ExceptionMessageService {

    List<Map<String,Object>> queryList(Map<String,Object> parmas);

    /**
     * 保存异常消息
     * @param exceptionMessage
     */
    void save(ExceptionMessage exceptionMessage);

    /**
     * 根据设备号查找 异常消息记录
     * @return
     */
    ExceptionMessage findEM(String imei);

    /**
     *  跟新异常消息
     */
    void updateExceptionMessage(ExceptionMessage em);
}
