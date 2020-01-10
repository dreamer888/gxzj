package com.dlc.modules.sys.service.impl;

import com.dlc.modules.sys.dao.AdvTotalDao;
import com.dlc.modules.sys.entity.AdvTotalEntity;
import com.dlc.modules.sys.service.AdvTotalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("advTotalService")
public class AdvTotalServiceImpl implements AdvTotalService {

    @Autowired
    private AdvTotalDao advTotalDao;

    @Override
    public AdvTotalEntity queryObject(Long Id) {
        return advTotalDao.queryObject(Id);
    }

    @Override
    public List<AdvTotalEntity> queryList(Map<String, Object> map) {
        return advTotalDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return advTotalDao.queryTotal(map);
    }

    @Override
    public int queryCountPrice() {
        return advTotalDao.queryCountPrice();
    }
}
