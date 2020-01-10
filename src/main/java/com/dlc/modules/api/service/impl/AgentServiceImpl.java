/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: AgentServiceImpl
 * Author:   Administrator
 * Date:     2018/7/18 9:25
 * Description: 代理商服务类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.dlc.modules.api.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.dlc.common.utils.ConfigConstant;
import com.dlc.common.utils.R;
import com.dlc.common.utils.RedisUtils;
import com.dlc.modules.api.dao.AdvertisingMapper;
import com.dlc.modules.api.dao.AgentMapper;
import com.dlc.modules.api.dao.WalletDetailMapper;
import com.dlc.modules.api.entity.Advertising;
import com.dlc.modules.api.entity.Agent;
import com.dlc.modules.api.entity.WalletDetail;
import com.dlc.modules.api.service.AgentDeviceRelationService;
import com.dlc.modules.api.service.AgentService;
import com.dlc.modules.api.vo.AgentVo;
import com.dlc.modules.api.vo.UserInfoVo;
import com.dlc.modules.qd.utils.MD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.management.resources.agent;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chenyuexin
 * @date 2018-07-18 09:25
 * @version 1.0
 */
@Service
@Transactional
public class AgentServiceImpl implements AgentService{
    private final static Logger logger = LoggerFactory.getLogger(AgentServiceImpl.class);

    @Autowired
    private AgentMapper agentMapper;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private AgentDeviceRelationService relationService;
    @Autowired
    private WalletDetailMapper walletDetailMapper;
    @Autowired
    private AdvertisingMapper advertisingMapper;

    @Override
    public JSONObject login(Agent agent, HttpServletRequest req) {
        JSONObject obj = new JSONObject();
        agent.setPassword(MD5Util.MD5Encode(agent.getPassword(),"utf-8"));
        List<AgentVo> lists = this.agentMapper.findAgentByPhoneAndPwd(agent);
        if(lists.size()>0){
            AgentVo agentVo = lists.get(0);
            obj.put("agent",agentVo);
            String sessionId = redisUtils.get(ConfigConstant.AGENT+agentVo.getId());
            logger.info("sessionId1=="+sessionId);
            if(null != sessionId){
                redisUtils.delete(ConfigConstant.AGENT+agentVo.getId());
            }
            sessionId = req.getSession().getId();
            logger.info("sessionId2=="+sessionId);
            redisUtils.set(ConfigConstant.AGENT+agentVo.getId(),sessionId);
        }
        return obj;
    }

    @Override
    public Map<String, Object> getAgentByPhone(String phone) {
        return agentMapper.getAgentByPhone(phone);
    }

    @Override
    public void updateAgent(Agent agent) {
        this.agentMapper.updateByPrimaryKeySelective(agent);
    }

    //余额查询
    @Override
    public BigDecimal findWallet(Long id) {
        Agent ag = agentMapper.selectByPrimaryKey(id);
        if (ag.getType() == 4){
           Agent at = agentMapper.selectByPrimaryKey(ag.getParentId());
            BigDecimal money = BigDecimal.valueOf(Long.valueOf(at.getWallet())).divide(new BigDecimal(100));
            return money;
        }else {
            BigDecimal money = BigDecimal.valueOf(Long.valueOf(ag.getWallet())).divide(new BigDecimal(100));
            return money;
        }
    }
    //我的账户
    @Override
    public List<Map<String,Object>> queryAgentInfo(Long id) {
        return agentMapper.queryAgentInfo(id);
    }
    //修改头像或保存信息
    @Override
    public int updataHeadImgOrInfo(Agent agent) {
        return agentMapper.updataHeadImgOrInfo(agent);
    }

    @Override
    public List<Map<String, Object>> getOps(Map<String, Object> params) {
        return agentMapper.listEmployee(params);
    }

    @Override
    public List<Map<String, Object>> listAgent(Map<String, Object> map) {
        List<Map<String, Object>> listMap =this.agentMapper.listAgent(map);
        for(Map temp : listMap){
            Double wallet = Double.parseDouble(temp.get("wallet").toString());
            temp.put("wallet",wallet/100);
        }
        return listMap;
    }

    @Override
    public void saveAgent(Agent agent) {
        this.agentMapper.insertSelective(agent);
    }

    @Override
    public void deleteAgent(Long agentId) {
        this.agentMapper.deleteAgent(agentId);
        //下面是查询代理商的员工集合
        Map<String, Object> map = new HashMap<>();
        map.put("agentId", agentId);
        List<Map<String, Object>> listMap = this.agentMapper.listEmployee(map);
        for (Map<String, Object> temp : listMap){
            this.agentMapper.deleteAgent(Long.valueOf(temp.get("id").toString()));
        }
        //this.relationService.deleteRelation(agentId, 1);//type==1 表示代理商
        this.relationService.deleteRealRelation(agentId);
    }
    //充值后加余额
    @Override
    public int updateWallet(String orderNo, int wallet) {
        WalletDetail walletDetail = walletDetailMapper.selectByOrderNo(orderNo);
        Agent agent = agentMapper.selectByPrimaryKey(walletDetail.getUserId());
        agent.setWallet(agent.getWallet() + wallet);
        return agentMapper.updateByPrimaryKeySelective(agent);
    }

    @Override
    public Agent selectParent(Long id) {
        return agentMapper.selectParent(id);
    }

    @Override
    public Map<String, Object> findAgent(Long agentId) {
        Map<String, Object> map = this.agentMapper.findAgent(agentId);
        Double commissionValue = Double.parseDouble(map.get("commissionValue").toString());
        map.put("commissionValue",commissionValue/100);
        return this.agentMapper.findAgent(agentId);
    }
    /**
     *  @Auther:YD
     *  @parameters:查出代理商的信息
     */
    @Override
    public Agent selectInfoById(Long id) {
        return agentMapper.selectByPrimaryKey(id);
    }
}
