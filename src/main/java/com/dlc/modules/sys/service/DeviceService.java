package com.dlc.modules.sys.service;

import com.dlc.common.utils.R;
import com.dlc.modules.sys.entity.AgentDeviceRelationEntity;
import com.dlc.modules.sys.entity.DeviceEntity;

import java.util.List;
import java.util.Map;

/**
 * 纸巾机设备表
 * 
 * @author dlc.dg.java
 * @email dlc.dg.java@163.com
 * @date 2018-07-15 18:06:09
 */
public interface DeviceService {
	
	DeviceEntity queryObject(Long deviceId);

	List<DeviceEntity> queryPrice(String imei);
	
	List<Map<String,Object>> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(DeviceEntity device);

	R saveRelationInfo(AgentDeviceRelationEntity agentDeviceRelationEntity);

	R modifyRelationInfo(Map<String,Object> params);

	void update(DeviceEntity device);
	
	void delete(Long deviceId);
	
	void deleteBatch(Long[] deviceIds);

    Map<String,Object> queryDeviceNoById(Long id);

	Map<String,Object> relationInfo(Long id);

	List<Map<String,Object>> select();

	/**
	 * 导出Excel表格
	 * @return
	 */
	List<Map<String, Object>> exportExcel();

	/**
	 * 导入Excel表格数据到数据库
	 * @param device
	 */
	void saveBatch(List<DeviceEntity> device);

	/**
	 * 查询数据的数据和导入的数据是否有相同数据
	 * @param device
	 * @return
	 */
	List<Map<String,Object>> queryByCondition(DeviceEntity device);

	/**
	 * 查询数据的IMEI否有相同数据
	 * @param device
	 * @return
	 */
	List<Map<String,Object>> queryByImeiCondition(DeviceEntity device);
}
