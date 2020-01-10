package com.dlc.modules.sys.service.impl;

import com.dlc.common.utils.R;
import com.dlc.modules.api.entity.ExceptionMessage;
import com.dlc.modules.qd.utils.PhoneCodeVer;
import com.dlc.modules.sys.dao.DeviceDao;
import com.dlc.modules.sys.dao.ExceptionMessageDao;
import com.dlc.modules.sys.entity.AgentDeviceRelationEntity;
import com.dlc.modules.sys.entity.DeviceEntity;
import com.dlc.modules.sys.entity.ExceptionMessageEntity;
import com.dlc.modules.sys.service.ExceptionMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/***********************************
 *Class by 王楚荣
 *2018/7/23/023
 * **********************************/
@Service("exceptionMessageService")
public class ExceptionMessageServiceImpl implements ExceptionMessageService {
    @Autowired
    private ExceptionMessageDao exceptionMessageDao;
    @Autowired
    private DeviceDao deviceDao;

    @Override
    public List<Map<String, Object>> queryList(Map<String, Object> map) {
        return exceptionMessageDao.queryListMapByMap(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return exceptionMessageDao.queryTotal(map);
    }

    @Override
    public R save(ExceptionMessageEntity exceptionMessage) {
        AgentDeviceRelationEntity adr = deviceDao.getRelationInfo(exceptionMessage.getDeviceNo());
        String phone = adr.getOpsPhone();
        if(phone==null){
            return R.error("设备没有绑定运维人员电话");
        }
        exceptionMessage.setAddressId(adr.getAddr());
        exceptionMessage.setCreateTime(new Date());
        PhoneCodeVer.sendErrorMsg(phone,"设备号【"+exceptionMessage.getDeviceNo()+"】"+adr.getAddr()+","+exceptionMessage.getContext());
        exceptionMessageDao.insertSelective(exceptionMessage);
        return R.ok();
    }
}
