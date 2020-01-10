package com.dlc.modules.sys.dao;

import com.dlc.modules.sys.entity.GzhIncomeDetailEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GzhIncomeDetailDao extends BaseDao<GzhIncomeDetailEntity> {
    int queryCountPrice();
}
