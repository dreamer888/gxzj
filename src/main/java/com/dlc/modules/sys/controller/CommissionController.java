package com.dlc.modules.sys.controller;

import com.dlc.common.utils.PageUtils;
import com.dlc.common.utils.Query;
import com.dlc.common.utils.R;
import com.dlc.modules.sys.entity.CommissionEntity;
import com.dlc.modules.sys.service.CommissionService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sys/commission")
public class CommissionController {

    @Autowired
    private CommissionService commissionService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:commission:list")
    public R list(@RequestParam Map<String, Object> params){
        Query query = new Query(params);
        List<CommissionEntity> commissionList = commissionService.queryList(query);
        int total = commissionService.queryTotal(query);
        PageUtils pageUtil = new PageUtils(commissionList, total, query.getLimit(), query.getPage());
        return R.ok().put("page", pageUtil);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:commission:info")
    public R info(@PathVariable("id") Long id){
        CommissionEntity commission = commissionService.queryObject(id);
        return R.ok().put("commission", commission);
    }
}
