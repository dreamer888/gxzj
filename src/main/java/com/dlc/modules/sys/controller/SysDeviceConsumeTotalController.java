package com.dlc.modules.sys.controller;

import com.dlc.common.utils.PageUtils;
import com.dlc.common.utils.Query;
import com.dlc.common.utils.R;
import com.dlc.modules.sys.entity.DeviceConsumeTotalEntity;
import com.dlc.modules.sys.entity.DeviceEntity;
import com.dlc.modules.sys.service.DeviceConsumeTotalService;
import com.dlc.modules.sys.service.DeviceService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sys/deviceconsumetotal")
public class SysDeviceConsumeTotalController {

@Autowired
private DeviceConsumeTotalService deviceConsumeTotalService;

@Autowired
private DeviceService deviceService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:deviceconsumetotal:list")
    public R list(@RequestParam Map<String, Object> params){
        //查询列表数据
        Query query = new Query(params);
        List<DeviceConsumeTotalEntity> deviceConsumeTotalList = deviceConsumeTotalService.queryList(query);
        int total = deviceConsumeTotalService.queryTotal(query);
        for (DeviceConsumeTotalEntity deviceConsumeTotal : deviceConsumeTotalList){
            int price = deviceConsumeTotal.getGoodsPrice() * deviceConsumeTotal.getPayPaperTotal();
                deviceConsumeTotal.setPayPaperSum(price);
        }

        /*for (DeviceConsumeTotalEntity deviceConsumeTotal : deviceConsumeTotalList){
            String imei = deviceConsumeTotal.getDeviceImei();
            List<DeviceEntity> device = deviceService.queryPrice(imei);
            if(device.size()>0){
                int price = device.get(0).getGoodsPrice()* deviceConsumeTotal.getPayPaperTotal();
                deviceConsumeTotal.setPayPaperSum(price);
            }
        }*/
        PageUtils pageUtil = new PageUtils(deviceConsumeTotalList, total, query.getLimit(), query.getPage());
        return R.ok().put("page", pageUtil);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:deviceconsumetotal:info")
    public R info(@PathVariable("id") Long id){
        DeviceConsumeTotalEntity deviceConsumeTotal  = deviceConsumeTotalService.queryObject(id);

        return R.ok().put("deviceConsumeTotal", deviceConsumeTotal);
    }

    @RequestMapping("/priceTotal")
    //@RequiresPermissions("sys:advtotal:list")
    public R moneyTotal(){
        int total = deviceConsumeTotalService.queryCountPrice();
        return R.reOk().put("priceTotal", total);
    }
}
