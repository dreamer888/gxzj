package com.dlc.modules.sys.dao;

import com.dlc.modules.sys.entity.GlAdvEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface GlAdvDao extends BaseDao<GlAdvEntity> {

    List<Map<String,Object>> queryInfo();

}
