package com.dlc.modules.api.service.impl;

import com.dlc.modules.api.dao.ExceptionMessageMapper;
import com.dlc.modules.api.entity.ExceptionMessage;
import com.dlc.modules.api.service.ExceptionMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/***********************************
 *Class by 王楚荣
 *2018/7/19/019
 * **********************************/
@Service
@Transactional
public class ExceptionMessageServiceImpl implements ExceptionMessageService{
    @Autowired
    private ExceptionMessageMapper exceptionMessageMapper;

    @Override
    public List<Map<String, Object>> queryList(Map<String, Object> parmas) {
        return exceptionMessageMapper.queryList(parmas);
    }

    @Override
    public void save(ExceptionMessage exceptionMessage) {
        exceptionMessage.setCreateTime(new Date());
        exceptionMessageMapper.insertSelective(exceptionMessage);
    }

    @Override
    public ExceptionMessage findEM(String imei) {
        return this.exceptionMessageMapper.findEM(imei);
    }

    @Override
    public void updateExceptionMessage(ExceptionMessage em) {
        this.exceptionMessageMapper.updateByPrimaryKeySelective(em);
    }
}
