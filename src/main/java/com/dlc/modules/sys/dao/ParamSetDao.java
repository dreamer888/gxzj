package com.dlc.modules.sys.dao;

import com.dlc.modules.api.entity.ParamSet;
import com.dlc.modules.sys.entity.ParamSetEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ParamSetDao extends BaseDao<ParamSetEntity> {

    ParamSetEntity queryParamSetInfo();

    List<Map<String,Object>> queryByConditions(Map<String, Object> map);
}
