package com.dlc.modules.sys.controller;

import com.dlc.common.utils.R;
import com.dlc.modules.qd.utils.MD5Util;
import com.dlc.modules.sys.entity.AgencyEntity;
import com.dlc.modules.sys.service.ProxyListService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sys/proxylist")
public class ProxyListController {

    @Autowired
    private ProxyListService proxyListService;

    /**
     * 所有菜单列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:proxylist:list")
    public List<AgencyEntity> list(){
        List<AgencyEntity> menuList = proxyListService.queryList(new HashMap<>());

        return menuList;
    }


    /**
     * 选择菜单(添加、修改菜单)
     */
/*    @RequestMapping("/select")
    //@RequiresPermissions("sys:proxylist:select")
    public R select(){
        //查询列表数据
        List<AgencyEntity> menuList = proxyListService.queryNotButtonList();

        return R.ok().put("menuList", menuList);
    }*/


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:proxylist:info")
    public R info(@PathVariable("id") Long id){
        AgencyEntity proxy = proxyListService.queryObject(id);

        return R.ok().put("proxylist", proxy);
    }

    /**
     * 保存
     */
/*    @RequestMapping("/save")
    @RequiresPermissions("sys:proxylist:save")
    public R save(@RequestBody AgencyEntity agency){
        Map<String,Object> map = new HashMap<>();
        map.put("phone",agency.getPhone());
        if (!supplierService.queryByCondition(map).isEmpty()){
            return R.error("该电话号码已经存在");
        }
        agency.setDeleteStatus(0);
        agency.setCreateTime(new Date());
        agency.setPassword(MD5Util.MD5Encode(agency.getPassword(),"utf-8"));
        supplierService.save(agency);

        return R.ok();
    }*/

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:proxylist:update")
    public R update(@RequestBody AgencyEntity agency){
        if(!agency.getPhone().equals(proxyListService.queryObject(agency.getId()).getPhone())){
            Map<String,Object> map = new HashMap<>();
            map.put("phone",agency.getPhone());
            if (!proxyListService.queryByConditionProxy(map).isEmpty()){
                return R.error("该电话号码已经存在");
            }
        }

        if(agency.getDeleteStatus() == 1){
            return R.error("该用户已经删除，不可再修改数据");
        }
        AgencyEntity temp = this.proxyListService.queryObject(agency.getId());
        if(!temp.getPassword().equals(agency.getPassword())){
            agency.setPassword(MD5Util.MD5Encode(agency.getPassword(),"utf-8"));
        }
        /*if(agency.getCommissionValue() != 0){
            Integer cValue= (agency.getCommissionValue())*100;
            agency.setCommissionValue(cValue);
        }*/
        proxyListService.update(agency);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:proxylist:delete")
    public R delete(@RequestBody Long ids){

        List<AgencyEntity> menuList = proxyListService.queryListParentId(ids);
        if(menuList.size() > 0){
            return R.error("请先删除子代，再删除父代");
        }
        proxyListService.delete(ids);

        return R.ok();
    }


}
