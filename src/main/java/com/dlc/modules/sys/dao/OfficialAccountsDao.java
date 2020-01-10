package com.dlc.modules.sys.dao;

import com.dlc.modules.sys.entity.OfficialAccountsEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OfficialAccountsDao extends BaseDao<OfficialAccountsEntity> {

    List<OfficialAccountsEntity>queryAppId(String AppId);
}
