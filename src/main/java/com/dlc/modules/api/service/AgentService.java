package com.dlc.modules.api.service;

import com.alibaba.fastjson.JSONObject;
import com.dlc.modules.api.entity.Agent;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author chenyuexin
 * @version 1.0
 * @date 2018-07-18 09:23
 */
public interface AgentService {
    JSONObject login(Agent agent, HttpServletRequest req);
    /**
     * 根据手机号查找用户
     * @param phone
     * @return
     */
    Map<String, Object> getAgentByPhone(String phone);
    /**
     * 更新代理商信息
     * @param agent
     */
    void updateAgent(Agent agent);
    //查询余额
    BigDecimal findWallet(Long id);
    //我的账号
    List<Map<String,Object>> queryAgentInfo(Long id);
    //修改头像//保存用户信息
    int updataHeadImgOrInfo(Agent agent);

    List<Map<String, Object>> getOps(Map<String,Object> params);
    /**
     * 查询代理列表
     * @param map
     * @return
     */
    List<Map<String, Object>> listAgent(Map<String, Object> map);
    /**
     * 保存代理
     * @param agent
     */
    void saveAgent(Agent agent);
    /**
     * 假删除
     * @param agentId
     */
    void deleteAgent(Long agentId);
    //充值后添加余额
    int updateWallet(String orderNo, int wallet);

    Agent selectParent(Long id);

    /**
     * 根据代理ID 查找代理信息
     * @return
     */
    Map<String, Object> findAgent(Long agentId);

    Agent selectInfoById(Long id);
}
