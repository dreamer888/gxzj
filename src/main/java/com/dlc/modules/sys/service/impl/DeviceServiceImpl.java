package com.dlc.modules.sys.service.impl;

import com.dlc.common.utils.R;
import com.dlc.modules.sys.dao.AddressDao;
import com.dlc.modules.sys.entity.AddressEntity;
import com.dlc.modules.sys.entity.AgentDeviceRelationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.dlc.modules.sys.dao.DeviceDao;
import com.dlc.modules.sys.entity.DeviceEntity;
import com.dlc.modules.sys.service.DeviceService;
import org.springframework.transaction.annotation.Transactional;


@Service("deviceService")
@Transactional
public class DeviceServiceImpl implements DeviceService {
	@Autowired
	private DeviceDao deviceDao;
	@Autowired
	private AddressDao addressDao;
	
	@Override
	public DeviceEntity queryObject(Long deviceId){
		return deviceDao.queryObject(deviceId);
	}

	@Override
	public List<Map<String,Object>> queryList(Map<String, Object> map){
		return deviceDao.queryListMapByMap(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return deviceDao.queryTotal(map);
	}
	
	@Override
	public void save(DeviceEntity device){
	    device.setCreateTime(new Date());
		deviceDao.save(device);
	}

	@Override
	public List<DeviceEntity> queryPrice(String imei) {
		return deviceDao.queryPrice(imei);
	}


	@Override
	public R saveRelationInfo(AgentDeviceRelationEntity agentDeviceRelationEntity) {
		AgentDeviceRelationEntity adr = deviceDao.getRelationInfo(agentDeviceRelationEntity.getDeviceNo());
		if(adr!=null){
			return R.error("设备已被绑定");
		}
		DeviceEntity deviceEntity = new DeviceEntity();
		deviceEntity.setDeviceNo(agentDeviceRelationEntity.getDeviceNo());
		deviceEntity = deviceDao.getDeviceByNo(deviceEntity);
		if(deviceEntity==null){
			return R.error("设备不存在");
		}
		if(deviceEntity.getStatus()==2){
			R.reError("设备离线");
		}
		if(deviceEntity.getStatus()==3){
			R.reError("设备故障");
		}
		deviceEntity.setInventory(agentDeviceRelationEntity.getInventory());
		deviceDao.updateGoodsInfoByNo(deviceEntity);
		agentDeviceRelationEntity.setAddr(deviceEntity.getAddressDetail());
		deviceDao.saveRelationInfo(agentDeviceRelationEntity);
		return R.ok();
	}

	@Override
	public R modifyRelationInfo(Map<String, Object> params) {
		DeviceEntity deviceEntity = new DeviceEntity();
		deviceEntity.setDeviceNo((String)params.get("deviceNo"));
		deviceEntity.setDeviceId(Long.valueOf(String.valueOf(params.get("deviceId"))));
		deviceEntity.setAddressDetail((String)params.get("addr"));
		deviceDao.update(deviceEntity);
		AgentDeviceRelationEntity adr = deviceDao.getRelationInfo((String)params.get("deviceNo"));
		adr.setAddr((String)params.get("addr"));
		adr.setAgentId(Long.valueOf(String.valueOf(params.get("agentId"))));
		deviceDao.modifyRelationInfo(adr);
		return R.ok();
	}

	@Override
	public void update(DeviceEntity device){
		AgentDeviceRelationEntity adr = deviceDao.getRelationInfo(device.getDeviceNo());
		if(adr!=null){
			adr.setAddr(device.getAddressDetail());
			deviceDao.modifyRelationInfo(adr);
		}
		deviceDao.update(device);
	}
	
	@Override
	public void delete(Long deviceId){
		deviceDao.delete(deviceId);
	}
	
	@Override
	public void deleteBatch(Long[] deviceIds){
		deviceDao.deleteBatch(deviceIds);
	}

    @Override
    public Map<String,Object> queryDeviceNoById(Long id) {
        return deviceDao.queryDeviceNoById(id);
    }

	@Override
	public Map<String, Object> relationInfo(Long id) {
		DeviceEntity deviceEntity = deviceDao.queryObject(id);
		return null;
	}

	@Override
	public List<Map<String, Object>> select() {
		return null;
	}

	@Override
	public List<Map<String, Object>> exportExcel() {
		return deviceDao.exportExcel();
	}

	@Override
	public void saveBatch(List<DeviceEntity> device) {
		deviceDao.saveBatch(device);
	}

	@Override
	public List<Map<String, Object>> queryByCondition(DeviceEntity device) {
		return deviceDao.queryByCondition(device);
	}

	@Override
	public List<Map<String, Object>> queryByImeiCondition(DeviceEntity device) {
		return deviceDao.queryByImeiCondition(device);
	}


}
