package com.dlc.modules.sys.service.impl;

import com.dlc.modules.sys.dao.AgencyDao;
import com.dlc.modules.sys.entity.AgencyEntity;
import com.dlc.modules.sys.service.AgencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("agencyService")
public class AgencyServiceImpl implements AgencyService {

    @Autowired
    private AgencyDao agencyDao;

    @Override
    public AgencyEntity queryObject(Long agencyId) {
        return agencyDao.queryObject(agencyId);
    }

    @Override
    public List<AgencyEntity> queryList(Map<String, Object> map) {
        return agencyDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return agencyDao.queryTotal(map);
    }

    @Override
    public void save(AgencyEntity agency) {
        agencyDao.save(agency);
    }

    @Override
    public void update(AgencyEntity agency) {
        agencyDao.update(agency);
    }

    @Override
    public void delete(Long agencyId) {
        agencyDao.delete(agencyId);
    }

    @Override
    public void deleteBatch(Long[] agencyIds) {
        agencyDao.deleteBatch(agencyIds);
    }

    @Override
    public List<Map<String, Object>> queryByCondition(Map<String, Object> map) {
        return agencyDao.queryByCondition(map);
    }

}
