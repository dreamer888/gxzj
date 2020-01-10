package com.dlc.modules.sys.service.impl;

import com.dlc.modules.sys.dao.ProxyListDao;
import com.dlc.modules.sys.entity.AgencyEntity;
import com.dlc.modules.sys.service.ProxyListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("proxyListService")
public class ProxyListServiceImpl implements ProxyListService {

    @Autowired
    private ProxyListDao proxyListDao;

    @Override
    public AgencyEntity queryObject(Long agencyId) {
        return proxyListDao.queryObject(agencyId);
    }

    @Override
    public List<AgencyEntity> queryList(Map<String, Object> map) {
        return proxyListDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return proxyListDao.queryTotal(map);
    }

    @Override
    public void save(AgencyEntity agency) {
        proxyListDao.save(agency);
    }

    @Override
    public void update(AgencyEntity agency) {
        proxyListDao.update(agency);
    }

    @Override
    public void delete(Long agencyId) {
        proxyListDao.delete(agencyId);
    }

    @Override
    public void deleteBatch(Long[] agencyIds) {
        proxyListDao.deleteBatch(agencyIds);
    }

    @Override
    public List<Map<String, Object>> queryByConditionProxy(Map<String, Object> map) {
        return proxyListDao.queryByConditionProxy(map);
    }

    @Override
    public List<AgencyEntity> queryListParentId(Long parentId) {
        return proxyListDao.queryListParentId(parentId);
    }

}
