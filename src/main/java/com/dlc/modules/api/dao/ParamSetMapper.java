package com.dlc.modules.api.dao;

import com.dlc.modules.api.entity.ParamSet;

public interface ParamSetMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ParamSet record);

    int insertSelective(ParamSet record);

    ParamSet selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ParamSet record);

    int updateByPrimaryKey(ParamSet record);
}