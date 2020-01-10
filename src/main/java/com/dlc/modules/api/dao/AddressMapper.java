package com.dlc.modules.api.dao;

import com.dlc.modules.api.entity.Address;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface AddressMapper {
    int deleteByPrimaryKey(Long addressId);

    int insert(Address record);

    int insertSelective(Address record);

    Address selectByPrimaryKey(Long addressId);

    int updateByPrimaryKeySelective(Address record);

    int updateByPrimaryKey(Address record);

    List<Map<String,Object>> queryList(Map<String, Object> params);//收货地址列表

    int Add(Address address);//新增地址

    int updateIsdefaultId(Address record);//修改之前默认地址为非默认地址

    int chkAddressById(Long userId);//查询默认地址信息

    int changeAddressStatus(@Param("addressId")Long addressId,@Param("userId")Long userId,@Param("isDefault")Byte isDefault);//设置为默认地址

    int queryAddressCount(Map<String, Object> map);//总记录条数

    //Long selectAddressId(Address address);//查询addressId
}