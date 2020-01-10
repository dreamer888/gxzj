package com.dlc.modules.sys.dao;


import com.dlc.modules.sys.entity.ExceptionMessageEntity;
import com.dlc.modules.sys.entity.UserLikeGzh;

public interface ExceptionMessageDao extends BaseDao<UserLikeGzh>{
    void insertSelective(ExceptionMessageEntity exceptionMessageEntity);

}