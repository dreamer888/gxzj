package com.dlc.modules.sys.controller;

import com.dlc.common.utils.DateUtils;
import com.dlc.common.utils.PageUtils;
import com.dlc.common.utils.Query;
import com.dlc.common.utils.R;
import com.dlc.modules.sys.entity.AdvTotalEntity;
import com.dlc.modules.sys.service.AdvTotalService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sys/advtotal")
public class SysAdvTotalController {

    @Autowired
    private AdvTotalService advTotalService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:advtotal:list")
    public R list(@RequestParam Map<String, Object> params){
        //查询列表数据
        Query query = new Query(params);
        List<AdvTotalEntity> advTotalList = advTotalService.queryList(query);
        int total = advTotalService.queryTotal(query);
            /*for(AdvTotalEntity temp: advTotalList){
                if(temp.getType() == 1){
                    int sum = temp.getClickNum() * temp.getPrice();
                    temp.setCount(sum);
                }else{
                    int sum2 = temp.getShowNum()/1000;
                    int showSum = sum2*temp.getPrice();
                    temp.setCount(showSum);
                }

            }*/
        PageUtils pageUtil = new PageUtils(advTotalList, total, query.getLimit(), query.getPage());

        return R.ok().put("page", pageUtil);
    }

    @RequestMapping("/priceTotal")
    //@RequiresPermissions("sys:advtotal:list")
    public R moneyTotal(){
        int total = advTotalService.queryCountPrice();
        return R.reOk().put("priceTotal", total);
    }




    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:advtotal:info")
    public R info(@PathVariable("id") Long id){
        AdvTotalEntity AdvTotal  = advTotalService.queryObject(id);

        return R.ok().put("AdvTotal", AdvTotal);
    }
}
