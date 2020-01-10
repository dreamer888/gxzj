package com.dlc.modules.sys.controller;

import com.dlc.common.utils.PageUtils;
import com.dlc.common.utils.Query;
import com.dlc.common.utils.R;
import com.dlc.modules.sys.entity.GzhIncomeDetailEntity;
import com.dlc.modules.sys.entity.OfficialAccountsEntity;
import com.dlc.modules.sys.service.GzhIncomeDetailService;
import com.dlc.modules.sys.service.OfficialAccountsService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sys/gzhincomedetail")
public class SysGzhIncomeDetailController {

    @Autowired
    private GzhIncomeDetailService gzhIncomeDetailService;

    @Autowired
    private OfficialAccountsService officialAccountsService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:gzhincomedetail:list")
    public R list(@RequestParam Map<String, Object> params){
        Query query = new Query(params);
        List<GzhIncomeDetailEntity> gzhIncomeDetailList = gzhIncomeDetailService.queryList(query);
        int total = gzhIncomeDetailService.queryTotal(query);

        /*for (GzhIncomeDetailEntity gzhIncomeDetail : gzhIncomeDetailList){
            String appids = gzhIncomeDetail.getAppId();
            List<OfficialAccountsEntity> officialAccounts = officialAccountsService.queryAppId(appids);
            if(officialAccounts.size()>0){
                int price = officialAccounts.get(0).getDeductPrice() * gzhIncomeDetail.getLikeNum();
                gzhIncomeDetail.setGzhcount(price);
            }
        }*/

        for (GzhIncomeDetailEntity gzhIncomeDetail : gzhIncomeDetailList){
                int price = gzhIncomeDetail.getDeductPrice() * gzhIncomeDetail.getLikeNum();
                gzhIncomeDetail.setGzhcount(price);
        }
        PageUtils pageUtil = new PageUtils(gzhIncomeDetailList, total, query.getLimit(), query.getPage());
        return R.ok().put("page", pageUtil);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:gzhincomedetail:info")
    public R info(@PathVariable("id") Long id){
        GzhIncomeDetailEntity gzhincomedetail  = gzhIncomeDetailService.queryObject(id);

        return R.ok().put("gzhincomedetail", gzhincomedetail);
    }

    @RequestMapping("/priceTotal")
    //@RequiresPermissions("sys:advtotal:list")
    public R moneyTotal(){
        int total = gzhIncomeDetailService.queryCountPrice();
        return R.reOk().put("priceTotal", total);
    }
}
