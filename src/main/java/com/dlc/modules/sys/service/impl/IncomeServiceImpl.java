package com.dlc.modules.sys.service.impl;

import com.dlc.modules.sys.dao.IncomeDao;
import com.dlc.modules.sys.service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/***********************************
 *Class by 王楚荣
 *2018/7/21/021
 * **********************************/
@Service
public class IncomeServiceImpl implements IncomeService {
    @Autowired
    private IncomeDao incomeDao;

    @Override
    public List<Map<String, Object>> queryList(Map<String, Object> map) {
        return incomeDao.queryListMapByMap(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return incomeDao.queryTotal(map);
    }
}
