package com.dlc.modules.sys.dao;

import com.dlc.modules.sys.entity.AgentDeviceRelationEntity;
import com.dlc.modules.sys.entity.DeviceEntity;
import com.dlc.modules.sys.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 纸巾机设备表
 * 
 * @author dlc.dg.java
 * @email dlc.dg.java@163.com
 * @date 2018-07-15 18:06:09
 */
@Mapper
public interface DeviceDao extends BaseDao<DeviceEntity> {

    Map<String,Object> queryDeviceNoById(Long id);

    List<DeviceEntity> queryPrice(String imei);

    Map<String,Object> relationInfo(String DeviceNo);

    void saveRelationInfo(AgentDeviceRelationEntity agentDeviceRelationEntity);

    void modifyRelationInfo(AgentDeviceRelationEntity agentDeviceRelationEntity);

    AgentDeviceRelationEntity  getRelationInfo(String deviceNo);

    void updateGoodsInfoByNo(DeviceEntity deviceEntity);

    List<Map<String,Object>> select();

    DeviceEntity getDeviceByNo(DeviceEntity deviceEntity);

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
