package com.dlc.modules.sys.controller;

import com.dlc.common.utils.PageUtils;
import com.dlc.common.utils.Query;
import com.dlc.common.utils.R;
import com.dlc.modules.qd.utils.MD5Util;
import com.dlc.modules.sys.entity.AgencyEntity;
import com.dlc.modules.sys.service.AgencyService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sys/agency")
public class AgencyController {

    @Autowired
    private AgencyService agencyService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:agency:list")
    public R list(@RequestParam Map<String, Object> params){
        Query query = new Query(params);
        List<AgencyEntity> agencyList = agencyService.queryList(query);
        int total = agencyService.queryTotal(query);
        PageUtils pageUtil = new PageUtils(agencyList, total, query.getLimit(), query.getPage());
        return R.ok().put("page", pageUtil);
    }



    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:agency:info")
    public R info(@PathVariable("id") Long id){
        AgencyEntity agency = agencyService.queryObject(id);

        return R.ok().put("agency", agency);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:agency:save")
    public R save(@RequestBody AgencyEntity agency){
        Map<String,Object> map = new HashMap<>();
        map.put("phone",agency.getPhone());
        if (!agencyService.queryByCondition(map).isEmpty()){
            return R.error("该电话号码已经存在");
        }
        if (StringUtils.isBlank(agency.getPassword())){
            return R.error("密码不能为空");
        }
        agency.setCreateTime(new Date());
        agency.setPassword(MD5Util.MD5Encode(agency.getPassword(),"utf-8"));
        agencyService.save(agency);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:agency:update")
    public R update(@RequestBody AgencyEntity agency){
        if(!agency.getPhone().equals(agencyService.queryObject(agency.getId()).getPhone())){
            Map<String,Object> map = new HashMap<>();
            map.put("phone",agency.getPhone());
            if (!agencyService.queryByCondition(map).isEmpty()){
                return R.error("该电话号码已经存在");
            }
        }

        if(agency.getDeleteStatus() == 1){
            return R.error("该用户已经删除，不可再修改数据");
        }
        AgencyEntity temp = this.agencyService.queryObject(agency.getId());
        if(!temp.getPassword().equals(agency.getPassword())){
            agency.setPassword(MD5Util.MD5Encode(agency.getPassword(),"utf-8"));
        }
        /*if(agency.getCommissionValue() != 0){
            int cValue= (agency.getCommissionValue())*100;
            agency.setCommissionValue(cValue);
        }*/
        agencyService.update(agency);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:agency:delete")
    public R delete(@RequestBody Long[] ids){
        agencyService.deleteBatch(ids);

        return R.ok();
    }


}
