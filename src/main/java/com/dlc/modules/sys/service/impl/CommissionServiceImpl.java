package com.dlc.modules.sys.service.impl;

import com.dlc.modules.sys.dao.CommissionDao;
import com.dlc.modules.sys.entity.CommissionEntity;
import com.dlc.modules.sys.service.CommissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("commissionService")
public class CommissionServiceImpl implements CommissionService {

    @Autowired
    private CommissionDao commissionDao;

    @Override
    public CommissionEntity queryObject(Long Id) {
        return commissionDao.queryObject(Id);
    }

    @Override
    public List<CommissionEntity> queryList(Map<String, Object> map) {
        return commissionDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return commissionDao.queryTotal(map);
    }
}
