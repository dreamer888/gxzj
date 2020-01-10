package com.dlc.modules.api.controller;
import com.dlc.common.utils.PageUtils;
import com.dlc.common.utils.Query;
import com.dlc.common.utils.R;
import com.dlc.modules.api.entity.Address;
import com.dlc.modules.api.service.AddressService;

import com.dlc.modules.api.service.OfficialAccountsService;
import com.dlc.modules.api.vo.AgentVo;
import com.dlc.modules.api.vo.UserInfoVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


/**
 * @author 廖修坤
 * @date 2018/7/20
 */
@RestController
@RequestMapping("/api/address")//address
public class ApiAddressController extends BaseController{
    @Autowired
    private AddressService addressService;

    /**
     * 收货地址列表
     * @param
     *
    */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,HttpServletRequest request){
        Long userId = getAgentVo(request).getId();
        params.put("userId",userId);
        Query query =new Query(params);
        List<Map<String, Object>> list = addressService.queryList(query);//数据查询
        int total = addressService.queryAddressCount(query);//总记录数查询

        PageUtils pageUtil = new PageUtils(list,total,query.getLimit(), query.getPage());
        return R.reOk(pageUtil);
    }

    /***
     * 添加地址/修改地址
     * *
     */
    @RequestMapping("/saveOrUpdate")
    public R saveOrUpdate(HttpServletRequest request,Address address, Long oldId){
        if (StringUtils.isBlank(address.getName()) || StringUtils.isBlank(address.getAddr()) || StringUtils.isBlank(address.getPhone()) || StringUtils.isBlank(address.getProvince())){
            return R.reError("缺少参数");
        }
        AgentVo userInfo = getAgentVo(request);
        return addressService.saveOrUpdate(address,userInfo.getId(),oldId);
    }

    /**
     * 设为默认地址
     * */
    @RequestMapping("/changeAddressStatus")
    public R changeAddressStatus(Long addressId,HttpServletRequest request,Byte isDefault){
        AgentVo userInfo = getAgentVo(request);
        int x = addressService.changeAddressStatus(addressId,userInfo.getId(),isDefault);
        if(x<=0){
            return  R.reError("修改失败");
        }
        return R.reOk();
    }


    /**
     * 删除地址
     * */
    @RequestMapping("/delete")
    public R deleteAddress(Long addressId) {
        if (addressId == null) {
            R.reError("请添加参数");
        }
        //状态值改变(假删除)
        addressService.updateStatus(addressId);
        return R.reOk();
    }
}
