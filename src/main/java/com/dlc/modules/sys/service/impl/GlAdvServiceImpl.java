package com.dlc.modules.sys.service.impl;

import com.dlc.modules.sys.dao.GlAdvDao;
import com.dlc.modules.sys.entity.GlAdvEntity;
import com.dlc.modules.sys.service.GlAdvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("glAdvService")
public class GlAdvServiceImpl implements GlAdvService {

    @Autowired
    private GlAdvDao glAdvDao;

    @Override
    public GlAdvEntity queryObject(Long gladvId) {
        return glAdvDao.queryObject(gladvId);
    }

    @Override
    public List<GlAdvEntity> queryList(Map<String, Object> map) {
        return glAdvDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return glAdvDao.queryTotal(map);
    }

    @Override
    public void save(GlAdvEntity gladv) {
        glAdvDao.save(gladv);
    }

    @Override
    public void update(GlAdvEntity gladv) {
        glAdvDao.update(gladv);
    }

    @Override
    public void delete(Long gladvId) {
        glAdvDao.delete(gladvId);
    }

    @Override
    public void deleteBatch(Long[] gladvIds) {
        glAdvDao.deleteBatch(gladvIds);
    }

}
