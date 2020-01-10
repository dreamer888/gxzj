package com.dlc.modules.api.service.impl;

import com.baomidou.mybatisplus.toolkit.CollectionUtil;
import com.dlc.common.utils.R;
import com.dlc.modules.api.dao.AgentDeviceRelationMapper;
import com.dlc.modules.api.dao.DeviceMapper;
import com.dlc.modules.api.dao.OfficialAccountsMapper;
import com.dlc.modules.api.entity.Device;
import com.dlc.modules.api.service.DeviceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chenyuexin
 * @version 1.0
 * @date 2018-07-13 19:29
 */
@Service
@Transactional
public class DeviceServiceImpl implements DeviceService {
    private Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private DeviceMapper deviceMapper;

    @Autowired
    private AgentDeviceRelationMapper agentDeviceRelationMapper;

    @Autowired
    private OfficialAccountsMapper officialAccountsMapper;

    @Override
    public R queryDeviceGoods(String deviceNo) {
        List<Map<String, Object>> maps = deviceMapper.queryDeviceGoods(deviceNo);
        if (CollectionUtils.isEmpty(maps)) {
            return R.reError("设备编码不存在!");
        }
        Map<String, Object> map = maps.get(0);
        Integer goodsPrice = (Integer) map.get("goodsPrice");
        double price = goodsPrice.doubleValue()/100;
        map.put("goodsPrice",price);
        return R.reOk(map);
    }

    @Override
    public R updateGoodsNum(String deviceNo, int num) {
        int inventory=0;
        Device device = new Device();
        Map<String,Object> map = new HashMap<>();
        List<Map<String, Object>> list = deviceMapper.queryDeviceGoods(deviceNo);
        if (!CollectionUtils.isEmpty(list)) {
            inventory = (int) list.get(0).get("inventory")-num;
            map.put("inventory",inventory);
            map.put("deviceNo",deviceNo);
            /*device.setInventory(inventory);
            device.setDeviceNo(deviceNo);*/
            deviceMapper.updateGoodsInfoByNo(map);
            //更新关系
            agentDeviceRelationMapper.updateOpsByDeviceNo(map);
            log.info("库存扣减成功"+num);
            return R.reOk();
        }
        log.info("库存扣减失败");
        return R.reError("减库存失败");
    }

    @Override
    public List<Map<String, Object>> queryList(Map<String, Object> params) {
        return deviceMapper.queryList(params);
    }

    @Override
    public List<Map<String, Object>> queryDeviceDetail(Map<String, Object> params) {
        List<Map<String,Object>> listMap = deviceMapper.queryDeviceDetail(params);
        for(Map<String,Object> map : listMap){
            Double price = Double.valueOf(map.get("goodsPrice").toString());
            log.info("price------"+price/100);
            map.put("goodsPrice", price/100);
        }
        return listMap;
    }

    @Override
    public List<Map<String, Object>> findDeviceAddrByImei(String imei) {
        return this.deviceMapper.findDeviceAddrByImei(imei);
    }

    @Override
    public List<Map<String, Object>> queryOfficialDeviceList(Integer type, Long agentId) {
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("type",type);
        paramMap.put("agentId",agentId);

        List<Map<String, Object>> mapList = deviceMapper.queryOfficialDeviceList(paramMap);
        paramMap.clear();
        for (Map<String, Object> map : mapList) {
            String deviceNo = map.get("deviceNo").toString();

            paramMap.put("deviceNo",deviceNo);
            int count = officialAccountsMapper.queryofficialAccountsCount(paramMap);
            map.put("count",count);
        }

        return mapList;
    }

    @Override
    public void deleteR(String deviceNo, Long agentId) {
        agentDeviceRelationMapper.deleteRByAgentAndDevice(deviceNo,agentId);
    }

    @Override
    public R updateDeviceInfo(Device device, Long opsId, String opsPhone) {
        Map<String,Object> paprmMap = new HashMap<>();
        paprmMap.put("opsId",opsId);
        paprmMap.put("opsPhone",opsPhone);
        paprmMap.put("deviceNo",device.getDeviceNo());
        paprmMap.put("inventory",device.getInventory());
        //更新设备表
        int deviceRes = deviceMapper.updateByPrimaryKeySelective(device);
        //更新设备关系表
        int relatRes =  agentDeviceRelationMapper.updateOpsByDeviceNo(paprmMap);

        if (relatRes>0&&deviceRes>0){
            return R.reOk();
        }
        return R.reError("更新失败");
    }

    @Override
    public String queryDeviceAddress(String deviceNo) {
        return deviceMapper.queryDeviceAddress(deviceNo);

    }

    @Override
    public void update(Device device) {
        this.deviceMapper.updateByPrimaryKeySelective(device);
    }

    @Override
    public int queryDeviceStatus(String deviceNo) {
        return deviceMapper.queryDeviceStatus(deviceNo);

    }
}
