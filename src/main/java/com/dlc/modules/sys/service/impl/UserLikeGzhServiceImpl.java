package com.dlc.modules.sys.service.impl;

import com.dlc.modules.sys.dao.UserLikeGzhDao;
import com.dlc.modules.sys.entity.UserLikeGzh;
import com.dlc.modules.sys.service.UserLikeGzhService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/***********************************
 *Class by 王楚荣
 *2018/7/20/020
 * **********************************/
@Service
public class UserLikeGzhServiceImpl implements UserLikeGzhService {
    @Autowired
    private UserLikeGzhDao userLikeGzhDao;

    @Override
    public List<Map<String,Object>> queryList(Map<String, Object> map) {
        return userLikeGzhDao.queryListMapByMap(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return userLikeGzhDao.queryTotal(map);
    }
}
