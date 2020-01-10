package com.dlc.modules.sys.controller;

import com.dlc.common.utils.PageUtils;
import com.dlc.common.utils.Query;
import com.dlc.common.utils.R;
import com.dlc.modules.sys.entity.GlAdvEntity;
import com.dlc.modules.sys.service.GlAdvService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sys/gladv")
public class SysGlAdvController {

    @Autowired
    private GlAdvService glAdvService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:gladv:list")
    public R list(@RequestParam Map<String, Object> params){
        Query query = new Query(params);
        List<GlAdvEntity> gladvList = glAdvService.queryList(query);
        int total = glAdvService.queryTotal(query);
        PageUtils pageUtil = new PageUtils(gladvList, total, query.getLimit(), query.getPage());

        return R.ok().put("page", pageUtil);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:gladv:info")
    public R info(@PathVariable("id") Long id){
        GlAdvEntity gladvs = glAdvService.queryObject(id);
        return R.ok().put("gladv", gladvs);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:gladv:save")
    public R save(@RequestBody GlAdvEntity gladv){
/*        if (StringUtils.isBlank(gladv.getImgUrl())){
            return R.error("请选择广告图");
        }*/
        gladv.setCreateTime(new Date());
        glAdvService.save(gladv);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:gladv:update")
    public R update(@RequestBody GlAdvEntity gladv){
/*        if (StringUtils.isBlank(gladv.getImgUrl())){
            return R.error("请选择广告图");
        }*/
        glAdvService.update(gladv);
        return R.ok();
    }


    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:gladv:delete")
    public R delete(@RequestBody Long[] ids){
        glAdvService.deleteBatch(ids);

        return R.ok();
    }

}
