/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: GZHIncomeDetailServiceImpl
 * Author:   Administrator
 * Date:     2018/7/20 16:25
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.dlc.modules.api.service.impl;

import com.dlc.common.utils.DateUtils;
import com.dlc.modules.api.dao.AgentDeviceRelationMapper;
import com.dlc.modules.api.dao.GzhIncomeDetailMapper;
import com.dlc.modules.api.dao.OfficialAccountsMapper;
import com.dlc.modules.api.entity.AgentDeviceRelation;
import com.dlc.modules.api.entity.GzhIncomeDetail;
import com.dlc.modules.api.service.GZHIncomeDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author chenyuexin
 * @date 2018-07-20 16:25
 * @version 1.0
 */
@Service
@Transactional
public class GZHIncomeDetailServiceImpl implements GZHIncomeDetailService{
    private final static Logger logger = LoggerFactory.getLogger(GZHIncomeDetailServiceImpl.class);

    @Autowired
    private GzhIncomeDetailMapper gzhIncomeDetailMapper;
    @Autowired
    private AgentDeviceRelationMapper relationMapper;
    @Autowired
    private AgentDeviceRelationServiceImpl relationService;
    @Autowired
    private OfficialAccountsMapper officialAccountsMapper;

    /**
     * 必须填的字段
     *  appId;
     *  likeNum;
     *  deviceImei;
     *  deviceNo;
     */
    @Override
    public void saveIncome(GzhIncomeDetail incomeDetail) {
        logger.info("incomeDetail-->"+incomeDetail.toString());
        int tempLikeNum = incomeDetail.getLikeNum();
        GzhIncomeDetail temp = this.gzhIncomeDetailMapper.findIncomeDetail(incomeDetail);
        logger.info("GzhIncomeDetail----"+temp);
        if(temp != null){
            //取到记录时间与当前时间，然后做对比
            Calendar calendar = Calendar.getInstance();
            int nowDay = calendar.get(calendar.DATE);
            calendar.setTime(temp.getCreateTime());
            int oldDay = calendar.get(calendar.DATE);
            if(nowDay == oldDay){
                incomeDetail.setId(temp.getId());
                int likeNum = temp.getLikeNum()+1;
                incomeDetail.setLikeNum(likeNum);
                this.gzhIncomeDetailMapper.updateByPrimaryKeySelective(incomeDetail);
            }else{
                this.gzhIncomeDetailMapper.insertSelective(incomeDetail);//直接保存记录
            }

        }else{
            this.gzhIncomeDetailMapper.insertSelective(incomeDetail);
        }
        //下面是更新设备公众号收益，消耗
        List<Map<String, Object>> listMap = this.relationMapper.findAgentDeviceRelationByImei(incomeDetail.getDeviceImei());
        logger.info("listMap--->"+listMap);
        if(listMap.size() > 0){
            Map<String, Object> map = listMap.get(0);

            Map<String, Object> appMap = this.officialAccountsMapper.
                    findOfficialAccountByAppIdAndDeviceNo(incomeDetail.getAppId(),incomeDetail.getDeviceNo());
            logger.info("appMap--->"+appMap);
            if(null == appMap){//如果appMap 为空  继续查是不是个联添加的公众号
                appMap = this.officialAccountsMapper.findOfficialAccountByAppIdAndDeviceNo(incomeDetail.getAppId(), null);
                logger.info("appMap1--->"+appMap);
                if(null != appMap){
                    Long agentId = Long.valueOf(appMap.get("agentId").toString());
                    logger.info("appMap-->agentId ===="+agentId);
                    if(agentId != 0){
                        appMap = null;
                    }
                }
            }
            if(null != appMap){
                int deductPrice = Integer.valueOf(appMap.get("deductPrice").toString());
                AgentDeviceRelation relation = new AgentDeviceRelation();
                relation.setDeviceNo(incomeDetail.getDeviceNo());
                relation.setDeviceImei(incomeDetail.getDeviceImei());
                relation.setAgentId(Long.valueOf(map.get("agentId").toString()));
                relation.setPayPaperTotal(0);
                relation.setPayPaperSum(0);
                relation.setFreePaperTotal(0);
                relation.setGzhLikeTotal(tempLikeNum);
                relation.setCommissionSum(0);
                relation.setGzhIncomeSum(tempLikeNum*deductPrice);
                this.relationService.update(relation);
            }
        }
    }

    @Override
    public int queryOfficialAccountsListCount(String deviceNo, String appId) {
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("deviceNo",deviceNo);
        paramMap.put("appId",appId);
        paramMap.put("createTime", DateUtils.format(new Date()));
        String count = gzhIncomeDetailMapper.queryOfficialAccountsListCount(paramMap);

        if (null ==count){
            return 0;
        }
        Integer count2 = Integer.parseInt(count);
        return count2;
    }
}
