package com.dlc.modules.api.dao;

import com.dlc.modules.api.entity.PublicFans;
import org.springframework.stereotype.Repository;

@Repository
public interface PublicFansMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PublicFans record);

    int insertSelective(PublicFans record);

    PublicFans selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PublicFans record);

    int updateByPrimaryKey(PublicFans record);
}