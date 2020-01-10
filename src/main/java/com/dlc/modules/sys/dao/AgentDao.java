package com.dlc.modules.sys.dao;


import com.dlc.modules.sys.entity.AgentEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
@Repository
public interface AgentDao extends BaseDao<AgentEntity>{
    List<Map<String,Object>> queryListByType(Map<String,Object> map);

    //更新代理商钱包
    int updateAgentWalletByUserId(Map<String,Object> param);

    Map<String,Object> queryAgentWalletByUserId(Long id);
}