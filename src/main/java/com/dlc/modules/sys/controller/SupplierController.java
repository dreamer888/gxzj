package com.dlc.modules.sys.controller;

import com.dlc.common.utils.PageUtils;
import com.dlc.common.utils.Query;
import com.dlc.common.utils.R;
import com.dlc.modules.qd.utils.MD5Util;
import com.dlc.modules.sys.entity.AgencyEntity;
import com.dlc.modules.sys.service.SupplierService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sys/supplier")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:supplier:list")
    public R list(@RequestParam Map<String, Object> params){
        Query query = new Query(params);
        List<AgencyEntity> agencyList = supplierService.queryList(query);
        int total = supplierService.queryTotal(query);
        PageUtils pageUtil = new PageUtils(agencyList, total, query.getLimit(), query.getPage());
        return R.ok().put("page", pageUtil);
    }



    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:supplier:info")
    public R info(@PathVariable("id") Long id){
        AgencyEntity supplier = supplierService.queryObject(id);

        return R.ok().put("supplier", supplier);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:supplier:save")
    public R save(@RequestBody AgencyEntity agency){
        Map<String,Object> map = new HashMap<>();
        map.put("phone",agency.getPhone());
        if (!supplierService.queryByCondition(map).isEmpty()){
            return R.error("该电话号码已经存在");
        }
        if (StringUtils.isBlank(agency.getPassword())){
            return R.error("密码不能为空");
        }
        agency.setDeleteStatus(0);
        agency.setCreateTime(new Date());
        agency.setPassword(MD5Util.MD5Encode(agency.getPassword(),"utf-8"));
        supplierService.save(agency);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:supplier:update")
    public R update(@RequestBody AgencyEntity agency){
        if(!agency.getPhone().equals(supplierService.queryObject(agency.getId()).getPhone())){
            Map<String,Object> map = new HashMap<>();
            map.put("phone",agency.getPhone());

            if (!supplierService.queryByCondition(map).isEmpty()){
                return R.error("该电话号码已经存在");
            }
        }
        AgencyEntity temp = this.supplierService.queryObject(agency.getId());
        if(!temp.getPassword().equals(agency.getPassword())){
            agency.setPassword(MD5Util.MD5Encode(agency.getPassword(),"utf-8"));
        }
        supplierService.update(agency);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:agency:delete")
    public R delete(@RequestBody Long[] ids){
        supplierService.deleteBatch(ids);

        return R.ok();
    }


}
