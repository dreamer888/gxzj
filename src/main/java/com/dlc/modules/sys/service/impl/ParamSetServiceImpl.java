package com.dlc.modules.sys.service.impl;

import com.dlc.modules.api.entity.ParamSet;
import com.dlc.modules.sys.dao.ParamSetDao;
import com.dlc.modules.sys.entity.ParamSetEntity;
import com.dlc.modules.sys.service.ParamSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("paramSetService")
public class ParamSetServiceImpl implements ParamSetService {

    @Autowired
    private ParamSetDao paramSetDao;

    @Override
    public ParamSetEntity queryObject(int paramId) {
        return paramSetDao.queryObject(paramId);
    }

    @Override
    public List<ParamSetEntity> queryList(Map<String, Object> map) {
        return paramSetDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return paramSetDao.queryTotal(map);
    }

    @Override
    public void save(ParamSetEntity param) {
        paramSetDao.save(param);
    }

    @Override
    public void update(ParamSetEntity param) {
        paramSetDao.update(param);
    }

    @Override
    public void delete(Long paramId) {
        paramSetDao.delete(paramId);
    }

    @Override
    public void deleteBatch(Long[] paramIds) {
        paramSetDao.deleteBatch(paramIds);
    }

    @Override
    public ParamSetEntity queryParamSetInfo() {
     return paramSetDao.queryParamSetInfo();
    }

    @Override
    public List<Map<String, Object>> queryByConditions(Map<String, Object> map) {
        return paramSetDao.queryByConditions(map);
    }
}
