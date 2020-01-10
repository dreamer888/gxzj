package com.dlc.modules.api.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.dlc.common.utils.Constant;
import com.dlc.common.utils.Query;
import com.dlc.common.utils.R;
import com.dlc.modules.api.dao.AddressMapper;
import com.dlc.modules.api.entity.Address;
import com.dlc.modules.api.service.AddressService;
import com.dlc.modules.qd.utils.PhoneCodeVer;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class AddressServiceImpl implements AddressService{
    @Autowired
    private AddressMapper addressMapper;

    /**
     * 收货地址列表
     * */
    public List<Map<String,Object>> queryList(Map<String, Object> params) {
        return addressMapper.queryList(params);
    }

    /**
     * 添加地址/修改地址
     * */
    public R saveOrUpdate(Address address,Long userId,Long oldId) {
        int x=0;
        int y=0;
        if (StringUtils.isBlank(address.getName()) || address.getName().length() > 50) {
            return R.reError("请输入正确的姓名！");
        }
        if (StringUtils.isBlank(address.getPhone()) || !PhoneCodeVer.isPhoneNum(address.getPhone())) {
            return R.reError("请输入正确的手机号");
        }
        if (StringUtils.isBlank(address.getProvince())) {
            return R.reError("请输入省市区");
        }
        if (StringUtils.isBlank(address.getAddr()) || address.getAddr().length() > 50) {
            return R.reError("请输入正确的详细地址");
        }

        if (address.getAddressId() != null) {
            //更新地址(修改)
          y = addressMapper.updateByPrimaryKeySelective(address);
            if(address.getIsDefault()==1){
                //该地址是默认地址,则将该用户其余的地址设为非默认
                address.setUserId(userId);
                x = addressMapper.updateIsdefaultId(address);//修改之前默认地址
            }

        }else {
            //判断修改或者保存过来的是否是默认地址
            if(address.getIsDefault()==1){
                //该地址是默认地址,则将该用户其余的地址设为非默认
                address.setUserId(userId);
             x = addressMapper.updateIsdefaultId(address);//修改之前默认地址
            }

                //为空新增地址
                address.setCreateTime(new Date());
                address.setUserId(userId);
            address.setStatus((byte) Constant.AddressStatus.ADDRESS_USABLE.getValue());
                addressMapper.insertSelective(address);
                JSONObject object = new JSONObject();
                object.put("addressId",address.getAddressId());
                return R.reOk(object);

        }
        return R.reOk();
    }

    /**
     * 修改默认地址
     * */
    public int changeAddressStatus(Long addressId,Long userId,Byte isDefault) {
        //查询出之前的默认地址，将其改为普通地址
        addressMapper.chkAddressById(userId);
        return addressMapper.changeAddressStatus(addressId,userId,isDefault);
    }

    /**
     * 假删除收货地址
     * */
    public void updateStatus(Long addressId) {
        Address address = new Address();
        address.setAddressId(addressId);
        address.setStatus((byte) Constant.AddressStatus.ADDRESS_UNUSABLE.getValue());
        addressMapper.updateByPrimaryKeySelective(address);
    }

    /**
     * 总记录条数
     * */
    public int queryAddressCount(Map<String, Object> map) {
        return addressMapper.queryAddressCount(map);
    }


}
