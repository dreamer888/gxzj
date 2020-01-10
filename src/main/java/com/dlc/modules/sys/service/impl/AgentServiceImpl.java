package com.dlc.modules.sys.service.impl;

import com.dlc.modules.sys.dao.AgentDao;
import com.dlc.modules.sys.service.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/***********************************
 *Class by 王楚荣
 *2018/7/21/021
 * **********************************/
@Service("agentService")
public class AgentServiceImpl implements AgentService {
    @Autowired
    private AgentDao agentDao;

    @Override
    public List<Map<String, Object>> queryListByType(Map<String, Object> map) {
        return agentDao.queryListByType(map);
    }
}
