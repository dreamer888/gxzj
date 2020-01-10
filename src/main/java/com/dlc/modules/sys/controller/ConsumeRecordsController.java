package com.dlc.modules.sys.controller;

import com.dlc.common.utils.PageUtils;
import com.dlc.common.utils.Query;
import com.dlc.common.utils.R;
import com.dlc.modules.sys.entity.UserConsumeEntity;
import com.dlc.modules.sys.service.ConsumeRecordsService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController("ConsumeRecordsController")
@RequestMapping("/sys/consumerecords")
public class ConsumeRecordsController {

    @Autowired
    private ConsumeRecordsService consumeRecordsService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:consumerecords:list")
    public R list(@RequestParam Map<String, Object> params){
        //查询列表数据
        Query query = new Query(params);

        List<UserConsumeEntity> userConsumeList = consumeRecordsService.queryList(query);
        int total = consumeRecordsService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(userConsumeList, total, query.getLimit(), query.getPage());

        return R.ok().put("page", pageUtil);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:consumerecords:info")
    public R info(@PathVariable("id") Long id){
        UserConsumeEntity consumerecords = consumeRecordsService.queryObject(id);

        return R.ok().put("consumerecords", consumerecords);
    }


    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:consumerecords:delete")
    public R delete(@RequestBody Long[] ids){
        consumeRecordsService.deleteBatch(ids);

        return R.ok();
    }


}
