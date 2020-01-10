package com.dlc.modules.sys.service;

import com.dlc.modules.sys.entity.OfficialAccountsEntity;

import java.util.List;
import java.util.Map;

public interface OfficialAccountsService {

    List<OfficialAccountsEntity> queryAppId(String AppId);

    OfficialAccountsEntity queryObject(Long id);

    List<OfficialAccountsEntity> queryList(Map<String, Object> map);

    int queryTotal(Map<String, Object> map);

    void save(OfficialAccountsEntity officialAccounts);

    void update(OfficialAccountsEntity officialAccounts);

    void delete(Long id);

    void deleteBatch(Long[] ids);
}
