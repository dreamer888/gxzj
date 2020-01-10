package com.dlc.modules.api.dao;

import com.dlc.modules.api.entity.OfficialAccounts;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface OfficialAccountsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OfficialAccounts record);

    int insertSelective(OfficialAccounts record);

    OfficialAccounts selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OfficialAccounts record);

    int updateByPrimaryKey(OfficialAccounts record);

    List<Map<String,Object>> queryOfficialAccountsList(Map<String,Object> map);

    int queryofficialAccountsCount(Map<String, Object> map);

    void updateStatus(Map<String, Object> map);

    OfficialAccounts queryOfficialCountInfo(Long id);

    Map<String, Object> findOfficialAccountByAppIdAndDeviceNo(@Param("appId") String appId, @Param("deviceNo") String deviceNo);
}