package com.dlc.modules.sys.dao;

import com.dlc.modules.sys.entity.AdvertisingEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdvertisingDao extends BaseDao<AdvertisingEntity> {
    void updates(AdvertisingEntity advertising);
}
