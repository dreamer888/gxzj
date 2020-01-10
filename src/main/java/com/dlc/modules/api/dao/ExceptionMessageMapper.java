package com.dlc.modules.api.dao;

import com.dlc.modules.api.entity.ExceptionMessage;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
@Repository
public interface ExceptionMessageMapper {
    int deleteByPrimaryKey(Long messageId);

    int insert(ExceptionMessage record);

    int insertSelective(ExceptionMessage record);

    int insertAll(List<ExceptionMessage> list);

    ExceptionMessage selectByPrimaryKey(Long messageId);

    int updateByPrimaryKeySelective(ExceptionMessage record);

    int updateByPrimaryKey(ExceptionMessage record);

    List<Map<String,Object>> queryList(Map<String,Object> parmas);

    ExceptionMessage findEM(String imei);
}