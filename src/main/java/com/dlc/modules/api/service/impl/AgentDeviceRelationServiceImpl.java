package com.dlc.modules.api.service.impl;

import com.dlc.common.utils.R;
import com.dlc.modules.api.dao.AgentDeviceRelationMapper;
import com.dlc.modules.api.dao.DeviceConsumeTotalMapper;
import com.dlc.modules.api.dao.DeviceIncomeTotalMapper;
import com.dlc.modules.api.dao.DeviceMapper;
import com.dlc.modules.api.entity.AgentDeviceRelation;
import com.dlc.modules.api.entity.Device;
import com.dlc.modules.api.entity.DeviceConsumeTotal;
import com.dlc.modules.api.entity.DeviceIncomeTotal;
import com.dlc.modules.api.service.AgentDeviceRelationService;
import com.dlc.modules.api.service.DeviceConsumeTotalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***********************************
 *Class by 王楚荣
 *2018/7/17/017
 * **********************************/
@Service
@Transactional
public class AgentDeviceRelationServiceImpl implements AgentDeviceRelationService{

    @Autowired
    private AgentDeviceRelationMapper agentDeviceRelationMapper;
    @Autowired
    private DeviceConsumeTotalMapper deviceConsumeTotalMapper;
    @Autowired
    private DeviceIncomeTotalMapper deviceIncomeTotalMapper;
    @Autowired
    private DeviceMapper deviceMapper;

    private Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public List<Map<String, Object>> queryList(Map<String,Object> params) {
        return agentDeviceRelationMapper.query(params);
    }

    /**
     * 添加 免费领取总量、购买纸巾总量、公众号关注量、公众号收益、购买纸巾收益、佣金收益
     * 参数：new AgentDeviceRelation(agentId,deviceNo,0,0,0,0,0,0)
     *freePaperTotal` int(11) NOT NULL DEFAULT '0' COMMENT '免费领取总量',
     `payPaperTotal` int(11) NOT NULL DEFAULT '0' COMMENT '购买纸巾总量',
     `gzhLikeTotal` int(11) NOT NULL DEFAULT '0' COMMENT '公众号关注量',
     `gzhIncomeSum` int(11) NOT NULL DEFAULT '0' COMMENT '公众号收益',
     `payPaperSum` int(11) NOT NULL DEFAULT '0' COMMENT '购买纸巾收益',
     `commissionSum` int(11) NOT NULL DEFAULT '0' COMMENT '佣金收益',
     * @param agentDeviceRelation
     */
    @Override
    public R update(AgentDeviceRelation agentDeviceRelation) {
        if(agentDeviceRelation.getAgentId()==null||agentDeviceRelation.getDeviceNo()==null){
            log.info("--------设备号、代理商Id不能为空---------");
            return R.reError("设备号、代理商Id不能为空");
        }
        AgentDeviceRelation adr = agentDeviceRelationMapper.queryObjectByAgentIdAndDeviceNo(agentDeviceRelation);
        if(adr==null){
            log.info("--------设备号、代理商没有绑定---------");
            return R.reError("设备号、代理商没有绑定");
        }
        //查询设备当天数据，为空则新增
        DeviceConsumeTotal deviceConsumeTotal = deviceConsumeTotalMapper.queryObjectToday(new DeviceConsumeTotal(adr.getDeviceNo()));
        if(deviceConsumeTotal==null){deviceConsumeTotalMapper.insertSelective(deviceConsumeTotal = new DeviceConsumeTotal(adr.getDeviceNo(),adr.getDeviceImei())); }
       DeviceIncomeTotal deviceIncomeTotal = deviceIncomeTotalMapper.queryObjectToday(new DeviceIncomeTotal(adr.getDeviceNo()));
        if(deviceIncomeTotal==null){deviceIncomeTotalMapper.insertSelective(deviceIncomeTotal = new DeviceIncomeTotal(adr.getDeviceNo(),adr.getDeviceImei()));}
        //-- deviceConsumeTotal --
        agentDeviceRelation.setId(deviceConsumeTotal.getId());
        //判断是否添加免费领取数量
        if(agentDeviceRelation.getFreePaperTotal()>0){
            adr.setFreePaperTotal(adr.getFreePaperTotal()+agentDeviceRelation.getFreePaperTotal());
        }
        //判断是否添加购买纸巾领取数量
        if(agentDeviceRelation.getPayPaperTotal()>0){
            adr.setPayPaperTotal(adr.getPayPaperTotal()+agentDeviceRelation.getPayPaperTotal());
        }
        //判断是否添加公众号关注量
        if(agentDeviceRelation.getGzhLikeTotal()>0){
            adr.setGzhLikeTotal(adr.getGzhLikeTotal()+agentDeviceRelation.getGzhLikeTotal());
        }
        deviceConsumeTotalMapper.updateTotal(agentDeviceRelation);
        //-- deviceIncomeTotal --
        agentDeviceRelation.setId(deviceIncomeTotal.getId());
        //判断是否添加公众号收益
        if(agentDeviceRelation.getGzhIncomeSum()>0){
            adr.setGzhIncomeSum(adr.getGzhIncomeSum()+agentDeviceRelation.getGzhIncomeSum());
        }
        //判断是否添加纸巾收益
        if(agentDeviceRelation.getPayPaperSum()>0){
            adr.setPayPaperSum(adr.getPayPaperSum()+agentDeviceRelation.getPayPaperSum());
        }
        //判断是否添加佣金收益
        if(agentDeviceRelation.getCommissionSum()>0){
            adr.setCommissionSum(adr.getCommissionSum()+agentDeviceRelation.getCommissionSum());
        }
        deviceIncomeTotalMapper.updateTotal(agentDeviceRelation);

        agentDeviceRelationMapper.updateByPrimaryKey(adr);
        return R.reOk();
    }

    @Override
    public R addRelation(AgentDeviceRelation agentDeviceRelation) {
        Device device =  deviceMapper.queryObjectByDeviceNoAndImei(new Device(agentDeviceRelation.getDeviceNo(),agentDeviceRelation.getDeviceImei()));
        if(device==null){
            return R.reError("设备不存在");
        }
        if(device.getStatus()==2){
            return R.reError("设备离线");
        }
        if(device.getStatus()==3){
            return R.reError("设备故障");
        }
        Map<String,Object> map = new HashMap<>();
        map.put("deviceNo",agentDeviceRelation.getDeviceNo());
        map.put("inventory",agentDeviceRelation.getInventory());
        map.put("addressDetail",agentDeviceRelation.getAddr());
        deviceMapper.updateGoodsInfoByNo(map);
        if(agentDeviceRelationMapper.queryObjectBydeviceImeiAndDeviceNo(agentDeviceRelation)!=null){
            return R.reError("该设备已被绑定");
        }

        //进行绑定
        agentDeviceRelation.setStatus(device.getStatus());
        agentDeviceRelationMapper.insertSelective(agentDeviceRelation);
        return R.reOk();
    }

    @Override
    public void deleteRelation(Long agentId, int type) {
        this.agentDeviceRelationMapper.deleteRelation(agentId, type);
    }

    @Override
    public void updateRelationByImei(String imei, Integer status) {
        this.agentDeviceRelationMapper.updateRelationByImei(imei, status);
    }

    @Override
    public List<Map<String, Object>> findAgentDeviceRelationByImei(String imei) {
        return this.agentDeviceRelationMapper.findAgentDeviceRelationByImei(imei);
    }

    @Override
    public List<Map<String, Object>> findRelationByImei(String imei) {
        return this.agentDeviceRelationMapper.findRelationByImei(imei);
    }

    @Override
    public void updateRelation(AgentDeviceRelation relation) {
        this.agentDeviceRelationMapper.updateByPrimaryKeySelective(relation);
    }

    @Override
    public void deleteRealRelation(Long agentId) {
        this.agentDeviceRelationMapper.deleteRealRelation(agentId);
    }
}
