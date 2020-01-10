package com.dlc.modules.sys.service.impl;

import com.dlc.modules.sys.dao.AddressDao;
import com.dlc.modules.sys.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/***********************************
 *Class by 王楚荣
 *2018/7/23/023
 * **********************************/
@Service("addressService")
public class AddressServiceImpl implements AddressService {
    @Autowired
    private AddressDao addressDao;

    @Override
    public List<Map<String, Object>> select(Map<String, Object> map) {
        return addressDao.select(map);
    }
}
