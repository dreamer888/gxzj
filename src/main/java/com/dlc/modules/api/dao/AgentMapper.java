package com.dlc.modules.api.dao;

import com.dlc.modules.api.entity.Agent;
import com.dlc.modules.api.vo.AgentVo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
@Repository
public interface AgentMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Agent record);

    int insertSelective(Agent record);

    Agent selectByPrimaryKey(Long id);

    Agent selectParent(Long id);

    int updateByPrimaryKeySelective(Agent record);

    int updateByPrimaryKey(Agent record);

    int updataHeadImgOrInfo(Agent record);

    List<AgentVo> findAgentByPhoneAndPwd(Agent agent);

    Map<String, Object> getAgentByPhone(String phone);

    Map<String, Object> findAgentById(Long userId);

    List<Map<String,Object>> queryAgentInfo(Long id);

    List<Map<String, Object>> listEmployee(Map<String, Object> map);

    List<Map<String, Object>> listAgent(Map<String, Object> map);
    /**
     * 假 删除
     * @param agentId
     */
    void deleteAgent(Long agentId);

    Agent findAgentByAgentId(Long agentId);

    //获取用户openId
    String getOpenId(Long userId);

    /**
     * 根据员工ID查找员工信息
     * @param agentId
     * @return
     */
    Map<String, Object> findEmployee(Long agentId);
    /**
     * 根据代理id查找代理信息
     * @param agentId
     * @return
     */
    Map<String, Object> findAgent(Long agentId);

}