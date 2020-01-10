package com.dlc.modules.api.service;

import com.dlc.common.utils.Query;
import com.dlc.common.utils.R;
import com.dlc.modules.api.entity.Address;

import java.util.List;
import java.util.Map;

public interface AddressService {
    List<Map<String,Object>> queryList(Map<String, Object> params);//收货地址列表

    R saveOrUpdate(Address address,Long userId,Long oldId);//saveOrUpdate

    int changeAddressStatus(Long addressId,Long userId,Byte isDefault);//设为默认地址

    void updateStatus(Long addressId);//假删除收货地址

    int queryAddressCount(Map<String, Object> map);//总记录条数
}
