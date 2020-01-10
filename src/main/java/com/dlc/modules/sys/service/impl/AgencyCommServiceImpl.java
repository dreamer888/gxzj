package com.dlc.modules.sys.service.impl;

import com.dlc.modules.sys.dao.AgencyCommDao;
import com.dlc.modules.sys.entity.AgencyEntity;
import com.dlc.modules.sys.service.AgencyCommService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("agencyCommService")
public class AgencyCommServiceImpl implements AgencyCommService {

    @Autowired
    private AgencyCommDao agencyCommDao;

    @Override
    public AgencyEntity queryObject(Long agencycommId) {
        return agencyCommDao.queryObject(agencycommId);
    }

    @Override
    public List<AgencyEntity> queryList(Map<String, Object> map) {
        return agencyCommDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return agencyCommDao.queryTotal(map);
    }

    @Override
    public void save(AgencyEntity agencycomm) {
        agencyCommDao.save(agencycomm);
    }

    @Override
    public void update(AgencyEntity agencycomm) {
        agencyCommDao.update(agencycomm);
    }

    @Override
    public void delete(Long agencycommId) {
        agencyCommDao.delete(agencycommId);
    }

    @Override
    public void deleteBatch(Long[] agencycommIds) {
        agencyCommDao.deleteBatch(agencycommIds);
    }


}
