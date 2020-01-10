package com.dlc.modules.sys.dao;

import com.dlc.modules.sys.entity.AdvTotalEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface AdvTotalDao extends BaseDao<AdvTotalEntity> {

    int queryCountPrice();

}
