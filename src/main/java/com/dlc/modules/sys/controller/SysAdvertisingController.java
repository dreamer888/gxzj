package com.dlc.modules.sys.controller;

import com.dlc.common.utils.DateUtils;
import com.dlc.common.utils.PageUtils;
import com.dlc.common.utils.Query;
import com.dlc.common.utils.R;
import com.dlc.modules.sys.entity.AdvertisingEntity;
import com.dlc.modules.sys.service.AdvertisingService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.SimpleFormatter;

@RestController
@RequestMapping("/sys/advertising")
public class SysAdvertisingController {

    @Autowired
    private AdvertisingService advertisingService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:advertising:list")
    public R list(@RequestParam Map<String, Object> params){
        //查询列表数据
        Query query = new Query(params);
        List<AdvertisingEntity> advertisingList = advertisingService.queryList(query);
        int total = advertisingService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(advertisingList, total, query.getLimit(), query.getPage());

        return R.ok().put("page", pageUtil);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:advertising:info")
    public R info(@PathVariable("id") Long id){
        AdvertisingEntity advertising = advertisingService.queryObject(id);
        Date start = advertising.getStartTime();
        Date end = advertising.getEndTime();
        SimpleDateFormat sft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        advertising.setStrStartDate(sft.format(start));
        advertising.setStrEndDate(sft.format(end));
        return R.ok().put("advertising", advertising);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:advertising:save")
    public R save(@RequestBody AdvertisingEntity advertising){
        advertisingService.save(advertising);

        return R.ok();
    }

    /**
     * 审核通过
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:advertising:update")
    public R update(@RequestBody AdvertisingEntity advertising){
        if(advertising.getStatus() != 1){
            return R.error("请选择审核中的广告操作");
        }
        advertising.setAuditFailTime(new Date());
        advertising.setStatus(2);
        advertisingService.update(advertising);

        return R.ok();
    }

    /**
     * 审核不通过
     */
    @RequestMapping("/updates")
    @RequiresPermissions("sys:advertising:updates")
    public R updates(@RequestBody AdvertisingEntity advertising){
        if(advertising.getStatus() != 1){
            return R.error("请选择审核中的广告操作");
        }
        if(StringUtils.isBlank(advertising.getAuditFailRemark())){
            return R.error("审核失败原因不能为空");
        }
        advertising.setAuditFailTime(new Date());
        advertising.setStatus(5);
        advertisingService.updates(advertising);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:advertising:delete")
    public R delete(@RequestBody Long[] ids){
        advertisingService.deleteBatch(ids);

        return R.ok();
    }
}
