package com.dlc.modules.sys.controller;

import com.dlc.common.utils.PageUtils;
import com.dlc.common.utils.Query;
import com.dlc.common.utils.R;
import com.dlc.modules.sys.entity.ParamSetEntity;
import com.dlc.modules.sys.service.ParamSetService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sys/paramset")
public class ParamSetController {

    @Autowired
    private ParamSetService paramSetService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:paramset:list")
    public R list(@RequestParam Map<String, Object> params){
        //查询列表数据
        Query query = new Query(params);
        List<ParamSetEntity> paramSetList = paramSetService.queryList(query);
        int total = paramSetService.queryTotal(query);
        PageUtils pageUtil = new PageUtils(paramSetList, total, query.getLimit(), query.getPage());

        return R.ok().put("page", pageUtil);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:paramset:info")
    public R info(@PathVariable("id") int id){
        ParamSetEntity paramset = paramSetService.queryObject(id);

        return R.ok().put("paramset", paramset);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:paramset:save")
    public R save(@RequestBody ParamSetEntity param){
        Map<String,Object> map = new HashMap<>();
        map.put("name",param.getName());
        if (!paramSetService.queryByConditions(map).isEmpty()){
            return R.error("该参数名称已经存在");
        }
        param.setCreateTime(new Date());
        paramSetService.save(param);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:paramset:update")
    public R update(@RequestBody ParamSetEntity param){
        if(!param.getName().equals(paramSetService.queryObject(param.getId()).getName())){
            Map<String,Object> map = new HashMap<>();
            map.put("name",param.getName());
            if (!paramSetService.queryByConditions(map).isEmpty()){
                return R.error("该参数名称已经存在");
            }
        }
        paramSetService.update(param);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:paramset:delete")
    public R delete(@RequestBody Long[] ids){
        paramSetService.deleteBatch(ids);

        return R.ok();
    }

}
