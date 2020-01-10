package com.dlc.modules.sys.controller;

import com.dlc.common.utils.PageUtils;
import com.dlc.common.utils.Query;
import com.dlc.common.utils.R;
import com.dlc.modules.sys.entity.AgencyEntity;
import com.dlc.modules.sys.service.AgencyCommService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sys/agencycomm")
public class AgencyCommController {

    @Autowired
    private AgencyCommService agencyCommService;


    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:agencycomm:list")
    public R list(@RequestParam Map<String, Object> params){
        Query query = new Query(params);
        List<AgencyEntity> advertisingList = agencyCommService.queryList(query);
        int total = agencyCommService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(advertisingList, total, query.getLimit(), query.getPage());

        return R.ok().put("pages", pageUtil);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:agencycomm:info")
    public R info(@PathVariable("id") Long id){
        AgencyEntity agencycomm = agencyCommService.queryObject(id);

        return R.ok().put("agencycomm", agencycomm);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:agencycomm:save")
    public R save(@RequestBody AgencyEntity agencycomm){
        agencycomm.setCreateTime(new Date());
        agencyCommService.save(agencycomm);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:agencycomm:update")
    public R update(@RequestBody AgencyEntity agencycomm){
        agencyCommService.update(agencycomm);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:agencycomm:delete")
    public R delete(@RequestBody Long ids){
        agencyCommService.delete(ids);

        return R.ok();
    }


}
