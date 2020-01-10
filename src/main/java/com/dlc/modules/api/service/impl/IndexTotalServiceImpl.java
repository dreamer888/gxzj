package com.dlc.modules.api.service.impl;

import com.dlc.modules.api.dao.IndexTotalMapper;
import com.dlc.modules.api.service.IndexTotalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/***********************************
 *Class by 王楚荣
 *2018/7/17/017
 * **********************************/
@Service
public class IndexTotalServiceImpl implements IndexTotalService {
   @Autowired
    private IndexTotalMapper indexTotalMapper;

    @Override
    public Map<String, Object> queryIndexTotal(Map<String,Object> params) {
        return indexTotalMapper.queryTotal(params);
    }
}
