package com.dlc.modules.api.service;

import com.dlc.modules.api.entity.Result;
import com.dlc.modules.api.entity.ResultItem;
import com.dlc.modules.api.entity.TaskRequest;
import com.dlc.modules.api.entity.TaskResponse;

import java.util.List;
import java.util.Map;

public interface expressService {

    TaskResponse queryExpressByOrderNo(TaskRequest taskRequest);//物流跟踪

    Result queryExpress(Map<String,Object> map);//查询快递

    void updateExpressByExpressNu(Map<String,Object> map,Map<String,Object> mapList);//更新快递

    int addExpress(Map<String,Object> map,Map<String,Object> mapList);//新增快递

    List<Map<String,Object>> queryExpressList(Result result);//查询快递详情

    List<ResultItem> queryExpressLastMsg(String nu);//根据订单号查询快递最新列表

    void addExpressDetail(Map<String, Object> mapList);//新增快递详情表
    void uppExpressDetail(Map<String, Object> mapList);//更新快递详情表
}
