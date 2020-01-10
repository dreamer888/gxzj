/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: CommissionDetailServiceImpl
 * Author:   Administrator
 * Date:     2018/7/21 13:36
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.dlc.modules.api.service.impl;

import com.dlc.common.utils.R;
import com.dlc.modules.api.dao.*;
import com.dlc.modules.api.entity.*;
import com.dlc.modules.api.service.CommissionDetailService;
import com.dlc.modules.api.service.GLService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author chenyuexin
 * @date 2018-07-21 13:36
 * @version 1.0
 */
@Service
@Transactional
public class CommissionDetailServiceImpl implements CommissionDetailService{
    private final static Logger logger = LoggerFactory.getLogger(CommissionDetailServiceImpl.class);
    @Autowired
    private CommissionDetailMapper detailMapper;
    @Autowired
    private AgentDeviceRelationMapper relationMapper;
    @Autowired
    private AgentMapper agentMapper;
    @Autowired
    private WalletDetailMapper walletDetailMapper;
    @Autowired
    private AgentDeviceRelationServiceImpl relationService;
    @Autowired
    private GlWalletMapper glWalletMapper;
    @Autowired
    private GlWalletDetailMapper glWalletDetailMapper;

    @Override
    public void saveCommissionDetail(Integer money,String deviceNo, String deviceImei,String orderNo,Integer goodsNum) {
        logger.info("money-->"+money+"--deviceImei-->"+deviceImei+"--orderNo-->"+orderNo);
        List<Map<String, Object>> listMap = this.relationMapper.findAgentDeviceRelationByImei(deviceImei);
        logger.info("saveCommissionDetail--->listMap--->"+listMap);
        if(listMap.size()>0){
            Map<String, Object> map = listMap.get(0);
            //取到代理商ID
            Long agentId = Long.valueOf(map.get("agentId").toString());
            //取到分成类型
            int type = Integer.valueOf(map.get("commissionType").toString());
            //取到分成值
            int value = Integer.valueOf(map.get("commissionValue").toString());
            //取到父ID
            Long parentId = Long.valueOf(map.get("parentId").toString());
            logger.info("agentId--->"+agentId+"---parentId--->"+parentId);
            //代理商赚取的收益
            int agentMoney = 0;
            if(type ==1){//type=1 按比例,百分比
                agentMoney = (money*value)/100;
            }else{//否则按金额
                agentMoney = value;
            }
            //上级收取的佣金
            int commissionVlue = money-agentMoney;
            if(commissionVlue < 0){//如果出现佣金值大于 纸巾原价 则做判断佣金值最大等于纸巾原件
                logger.info("上级佣金小于0----commissionVlue===》"+commissionVlue);
                commissionVlue = 0;
                agentMoney = money;
            }
            //下面判断是否有当日记录
            CommissionDetail temp = this.detailMapper.findCommissionDetailByAgentId(parentId);
            if(null != temp){
                //取到记录时间与当前时间，然后做对比
                Calendar calendar = Calendar.getInstance();
                int nowDay = calendar.get(calendar.DATE);
                calendar.setTime(temp.getCreateTime());
                int oldDay = calendar.get(calendar.DATE);
                if(nowDay == oldDay){
                    int tempMoney = temp.getMoney()+commissionVlue;
                    temp.setMoney(tempMoney);
                    this.detailMapper.updateByPrimaryKeySelective(temp);
                }else{
                    temp.setId(null);
                    temp.setMoney(commissionVlue);
                    temp.setAgentId(parentId);
                    temp.setCreateTime(new Date());
                    this.detailMapper.insertSelective(temp);
                }
            }else{
                temp = new CommissionDetail();
                temp.setAgentId(parentId);
                temp.setMoney(commissionVlue);
                temp.setCreateTime(new Date());
                this.detailMapper.insertSelective(temp);
            }
            //下面是更新代理商的钱包明细信息
            WalletDetail walletDetail = new WalletDetail();
            walletDetail.setUserId(agentId);
            walletDetail.setMoney(agentMoney);
            walletDetail.setType(2);//2 销售收入
            walletDetail.setStatus(3);// 3完成
            walletDetail.setOrderNo(orderNo);
            walletDetail.setCreateTime(new Date());
            this.walletDetailMapper.insertSelective(walletDetail);
            //下面更新代理商的钱包余额
            Agent agent = this.agentMapper.findAgentByAgentId(agentId);
            agent.setWallet(agent.getWallet()+agentMoney);
            this.agentMapper.updateByPrimaryKeySelective(agent);
            //更新上级代理商钱包余额
            if(parentId > 0){
                agent = this.agentMapper.findAgentByAgentId(parentId);
                agent.setWallet(agent.getWallet()+commissionVlue);
                this.agentMapper.updateByPrimaryKeySelective(agent);
            }else{//如果等于0  表示个联的钱包明细增加一条记录
                GlWalletDetail glWalletDetail = new GlWalletDetail();
                glWalletDetail.setMoney(commissionVlue);
                glWalletDetail.setType(0); //类型：0 个联的佣金，1用户购买纸巾，2 广告商充值，3 代理商购买商品，4 提现
                glWalletDetail.setCreateTime(new Date());
                this.glWalletDetailMapper.insertSelective(glWalletDetail);
            }
            //下面是给个联钱包明细插入一条购买纸巾记录
            GlWalletDetail glWalletDetail = new GlWalletDetail();
            glWalletDetail.setMoney(money);
            glWalletDetail.setType(1);//购买纸巾
            glWalletDetail.setCreateTime(new Date());
            this.glWalletDetailMapper.insertSelective(glWalletDetail);
            //下面更新个联钱包余额
            GlWallet glWallet = this.glWalletMapper.findGlWallet();
            if(null == glWallet){
                glWallet = new GlWallet();
                glWallet.setBalance(Long.valueOf(money));
                this.glWalletMapper.insertSelective(glWallet);
            }else{
                glWallet.setBalance(glWallet.getBalance()+money);
                this.glWalletMapper.updateByPrimaryKeySelective(glWallet);
            }
            //下面是更新设备收益，消耗
            AgentDeviceRelation relation = new AgentDeviceRelation();
            relation.setDeviceNo(deviceNo);
            relation.setDeviceImei(deviceImei);
            relation.setAgentId(agentId);
            relation.setPayPaperTotal(goodsNum);
            relation.setPayPaperSum(money);
            relation.setFreePaperTotal(0);
            relation.setGzhLikeTotal(0);
            relation.setCommissionSum(0);
            relation.setGzhIncomeSum(0);
            R result = this.relationService.update(relation);
            logger.info("saveCommissionDetail---R-->"+result.toString());

        }

    }

}
