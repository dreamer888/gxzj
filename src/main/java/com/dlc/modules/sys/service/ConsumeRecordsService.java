package com.dlc.modules.sys.service;

import com.dlc.modules.sys.entity.UserConsumeEntity;

import java.util.List;
import java.util.Map;

public interface ConsumeRecordsService {

    UserConsumeEntity queryObject(Long userConsumeId);

    List<UserConsumeEntity> queryList(Map<String, Object> map);

    int queryTotal(Map<String, Object> map);

    void delete(Long userConsumeId);

    void deleteBatch(Long[] userConsumeIds);
}
