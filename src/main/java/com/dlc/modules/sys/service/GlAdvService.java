package com.dlc.modules.sys.service;

import com.dlc.modules.sys.entity.GlAdvEntity;

import java.util.List;
import java.util.Map;

public interface GlAdvService {

    GlAdvEntity queryObject(Long gladvId);

    List<GlAdvEntity> queryList(Map<String, Object> map);

    int queryTotal(Map<String, Object> map);

    void save(GlAdvEntity gladv);

    void update(GlAdvEntity gladv);

    void delete(Long gladvId);

    void deleteBatch(Long[] gladvIds);

}
