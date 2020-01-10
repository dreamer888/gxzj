package com.dlc.modules.api.service.impl;

import com.dlc.modules.api.dao.UserLikeGzhMapper;
import com.dlc.modules.api.entity.UserLikeGzh;
import com.dlc.modules.api.service.UserLikeGzhService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/***********************************
 *Class by 王楚荣
 *2018/7/20/020
 * **********************************/
public class UserLikeGzhServiceImpl implements UserLikeGzhService {
    @Autowired
    private UserLikeGzhMapper userLikeGzhMapper;

    @Override
    public void save(UserLikeGzh userLikeGzh) {
        userLikeGzh.setCreateTime(new Date());
        userLikeGzhMapper.insertSelective(userLikeGzh);
    }
}
