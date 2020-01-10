package com.dlc.modules.api.dao;

import com.dlc.modules.api.entity.Result;
import com.dlc.modules.api.entity.ResultItem;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author 廖修坤
 * @date 2018/8/15 8:57
 */
@Mapper
@Repository
public interface ExpressMapper {

    Result queryExpress(Map<String,Object> map);//查询快递

    int updateExpressByExpressNu(Map<String,Object> map);//更新快递表

    int addExpressDetail(Map<String,Object> mapList);//新增快递详情表

    int addExpress(Map<String,Object> map);//新增快递表记录

    List<Map<String,Object>> queryExpressList(Result result);//查询快递详情

    List<ResultItem> queryExpressLastMsg(String nu);//根据订单号查询快递最新内容

    int delExpressDetail(Map<String, Object> mapList);//删除快递详情
}
