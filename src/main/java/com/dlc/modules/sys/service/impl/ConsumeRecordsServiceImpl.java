package com.dlc.modules.sys.service.impl;

import com.dlc.modules.sys.dao.ConsumeRecordsDao;
import com.dlc.modules.sys.entity.UserConsumeEntity;
import com.dlc.modules.sys.service.ConsumeRecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("consumeRecordsService")
public class ConsumeRecordsServiceImpl implements ConsumeRecordsService {

    @Autowired
    private ConsumeRecordsDao consumeRecordsDao;

    @Override
    public UserConsumeEntity queryObject(Long userConsumeId) {
        return consumeRecordsDao.queryObject(userConsumeId);
    }

    @Override
    public List<UserConsumeEntity> queryList(Map<String, Object> map) {
        return consumeRecordsDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return consumeRecordsDao.queryTotal(map);
    }

    @Override
    public void delete(Long userConsumeId) {
        consumeRecordsDao.delete(userConsumeId);
    }

    @Override
    public void deleteBatch(Long[] userConsumeIds) {
            consumeRecordsDao.deleteBatch(userConsumeIds);
    }
}
