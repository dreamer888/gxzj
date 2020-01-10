package com.dlc.modules.sys.service.impl;

import com.dlc.modules.sys.dao.UserConsumeDao;
import com.dlc.modules.sys.service.UserConsumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/***********************************
 *Class by 王楚荣
 *2018/7/20/020
 * **********************************/
@Service("userConsumeService")
public class UserConsumeServiceImpl implements UserConsumeService {
    @Autowired
    private UserConsumeDao userConsumeDao;

    @Override
    public List<Map<String, Object>> queryList(Map<String, Object> map) {
        return userConsumeDao.queryListMapByMap(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return userConsumeDao.queryTotal(map);
    }
}
