package com.dlc.modules.api.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.dlc.common.utils.MapUtil;
import com.dlc.common.utils.PageUtils;
import com.dlc.common.utils.R;
import com.dlc.modules.api.dao.AdvertisingMapper;
import com.dlc.modules.api.dao.AgentMapper;
import com.dlc.modules.api.dao.UserInfoMapper;
import com.dlc.modules.api.entity.Advertising;
import com.dlc.modules.api.entity.Agent;
import com.dlc.modules.api.entity.UserInfo;
import com.dlc.modules.api.service.AdvertisingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.BinaryClient;
import sun.management.resources.agent;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * @Auther:YD
 * @Date: Creat in 10:55 2018/7/17/017
 */
@Service
@Transactional
public class AdvertisingServiceImpl implements AdvertisingService{
    @Autowired
    private AdvertisingMapper advertisingMapper;
    @Autowired
    private AgentMapper agentMapper;

    //广告列表
    @Override
    public List<Advertising> queryAdvertisingList(Map<String, Object> params) {
        //查询所有状态待投放的广告
        List<Advertising> list = advertisingMapper.findAdByStatus();
        Agent ag = agentMapper.selectByPrimaryKey((Long) params.get("userId"));
        for (Advertising ad : list){
            //根据广告userId 查出广告主
            Date date = new Date();
            Long nowTime = date.getTime();
            Long startTime = ad.getStartTime().getTime();
            Long endTime = ad.getEndTime().getTime();
            if (startTime <= nowTime && endTime >= nowTime && ad.getPrice() <=ag.getWallet()){
                ad.setStatus(3);
                advertisingMapper.updateByPrimaryKeySelective(ad);
            } else if(nowTime >= endTime) {
                ad.setStatus(4);
                advertisingMapper.updateByPrimaryKeySelective(ad);
            }
        }
        //查询所有投放的广告
        List<Advertising> advList = advertisingMapper.selectAdvByStatus((Long) params.get("userId"));
        for (Advertising adv :advList) {
            Date date = new Date();
            Long nowTime = date.getTime();
            Long endTime = adv.getEndTime().getTime();
            if ((adv.getPrice() > ag.getWallet()) || (endTime < nowTime)){
                adv.setStatus(4);
                advertisingMapper.updateByPrimaryKeySelective(adv);
            }
        }

        return advertisingMapper.queryAdvertisingList(params);
    }
    //广告条数
    @Override
    public int queryTotal(Map<String, Object> params) {
        return advertisingMapper.queryTotal(params);
    }

    //广告详情
//    BigDecimal money = BigDecimal.valueOf((Integer) map.get("money")).divide(new BigDecimal(100));
    @Override
    public Map<String, Object> AdvertisingInfo(Long id) {
        Map<String, Object> map = advertisingMapper.selectAdvertising(id);
        BigDecimal price = BigDecimal.valueOf((Integer) map.get("price")).divide(new BigDecimal(100));
        map.put("price",price);
        return map;
    }

    //添加广告
    @Override
    public R addAdversiting(Map<String,Object> pamars) {
        Boolean flag = true;
        String msg = "";
        //广告名称校验
        List<Advertising> list = advertisingMapper.selectAllAdvList();
        for (Advertising adv : list) {
            if (adv.getName().equals(pamars.get("name"))) {
                flag = false;
                msg = "该广告名称已存在！";
            }
        }
        if (flag) {
            Agent agent = agentMapper.selectByPrimaryKey(Long.valueOf((String) pamars.get("userId")));
            int result = BigDecimal.valueOf(Long.valueOf((String) pamars.get("price"))).compareTo(BigDecimal.valueOf(agent.getWallet()));
            if (result == 1) {
                return R.reError("需要保证钱包余额充足的情况下才能投放广告哦");
            } else {
                pamars.put("status", 1);
                pamars.put("cpmUseNum", 0);
                pamars.put("createTime", new Date());
                advertisingMapper.insertSelective(pamars);
                return R.reOk();
            }
        }else {
            return R.reError(msg);
        }
    }
    //轮播图
    @Override
    public List<Map<String, Object>> sowingMapList(Map<String, Object> params) {
        //查询所有状态待投放的广告
        List<Advertising> list = advertisingMapper.findAdByStatus();
        for (Advertising ad : list){
            //根据广告userId 查出广告主
            Agent ag = agentMapper.selectByPrimaryKey(ad.getUserId());
            Date date = new Date();
            Long nowTime = date.getTime();
            Long startTime = ad.getStartTime().getTime();
            Long endTime = ad.getEndTime().getTime();
            if (startTime <= nowTime && endTime >= nowTime && ad.getPrice() <= ag.getWallet()){
                ad.setStatus(3);
                advertisingMapper.updateByPrimaryKeySelective(ad);
            }
        }
        return advertisingMapper.sowingMapList(params);
    }
    // 根据广告id 查询广告的类型
    @Override
    public Advertising selectById(Long advId) {
        return advertisingMapper.selectByPrimaryKey(advId);
    }

}
