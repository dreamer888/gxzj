package com.dlc.modules.sys.service.impl;

import com.dlc.modules.sys.dao.GzhIncomeDetailDao;
import com.dlc.modules.sys.entity.GzhIncomeDetailEntity;
import com.dlc.modules.sys.service.GzhIncomeDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("gzhIncomeDetailService")
public class GzhIncomeDetailServiceImpl implements GzhIncomeDetailService {

    @Autowired
    private GzhIncomeDetailDao gzhIncomeDetailDao;

    @Override
    public GzhIncomeDetailEntity queryObject(Long gzhId) {
        return gzhIncomeDetailDao.queryObject(gzhId);
    }

    @Override
    public List<GzhIncomeDetailEntity> queryList(Map<String, Object> map) {
        return gzhIncomeDetailDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return gzhIncomeDetailDao.queryTotal(map);
    }

    @Override
    public int queryCountPrice() {
        return gzhIncomeDetailDao.queryCountPrice();
    }
}
