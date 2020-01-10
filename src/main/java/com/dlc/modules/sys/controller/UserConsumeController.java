package com.dlc.modules.sys.controller;

import com.dlc.common.utils.PageUtils;
import com.dlc.common.utils.Query;
import com.dlc.common.utils.R;
import com.dlc.modules.sys.service.UserConsumeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/***********************************
 *Class by 王楚荣
 *2018/7/20/020
 * **********************************/
@RestController("sysUserConsumeController")
@RequestMapping("/sys/userConsume")
public class UserConsumeController {
    @Autowired
    private UserConsumeService userConsumeService;

    /**
     * 列表
     */
    @RequestMapping("/log")
    @RequiresPermissions("sys:userconsume:list")
    public R log(@RequestParam Map<String, Object> params){
        params.put("type",2);
        params.put("status",1);
        //查询列表数据
        Query query = new Query(params);

        List<Map<String,Object>> userConsumeList = userConsumeService.queryList(query);
        int total = userConsumeService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(userConsumeList, total, query.getLimit(), query.getPage());

        return R.ok().put("page", pageUtil);
    }
}
