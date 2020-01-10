package com.dlc.modules.api.dao;

import com.dlc.modules.api.entity.UserLikeGzh;

public interface UserLikeGzhMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserLikeGzh record);

    int insertSelective(UserLikeGzh record);

    UserLikeGzh selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserLikeGzh record);

    int updateByPrimaryKey(UserLikeGzh record);
}