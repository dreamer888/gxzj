package com.dlc.modules.sys.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.dlc.common.utils.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dlc.modules.sys.entity.WalletDetailEntity;
import com.dlc.modules.sys.service.WalletDetailService;


/**
 * 钱包明细表
 *
 * @author dlc.dg.java
 * @email dlc.dg.java@163.com
 * @date 2018-07-24 13:42:27
 */
@RestController("sysWalletdetail")
@RequestMapping("/sys/walletdetail")
public class WalletDetailController {
    @Autowired
    private WalletDetailService walletDetailService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:walletdetail:list")
    public R list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);

        List<WalletDetailEntity> walletDetailList = walletDetailService.queryList(query);
        int total = walletDetailService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(walletDetailList, total, query.getLimit(), query.getPage());

        return R.ok().put("page", pageUtil);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:walletdetail:info")
    public R info(@PathVariable("id") Long id) {
        WalletDetailEntity walletDetail = walletDetailService.queryObject(id);

        return R.ok().put("walletDetail",walletDetail);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:walletdetail:save")
    public R save(@RequestBody WalletDetailEntity walletDetail) {
        walletDetailService.save(walletDetail);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:walletdetail:update")
    public R update(@RequestBody WalletDetailEntity walletDetail) {

        if (walletDetail.getStatus() != 1) {
            return R.error("请选择审核中的记录!");
        }


        walletDetailService.updateWalletDetailStatus(2,walletDetail.getId(),walletDetail.getReason());

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:walletdetail:delete")
    public R delete(@RequestBody Long[] ids) {
        walletDetailService.deleteBatch(ids);

        return R.ok();
    }


    /**
     * 审核通过
     * @param walletDetail
     * @return
     * @throws Exception
     */
    @RequestMapping("/payToUser")
    @RequiresPermissions("sys:walletdetail:pay")
    public R payToUser(@RequestBody WalletDetailEntity walletDetail) throws Exception {
        if (walletDetail.getStatus()!= Constant.WalletDetailStatus.CHECKING.getValue()){
            return R.error("请选择审核中的记录!");
        }

        int count = walletDetailService.updateWalletDetailStatus(Constant.WalletDetailStatus.CHECK_PAYING.getValue(), walletDetail.getId(),walletDetail.getReason());
        // return walletDetailService.payToUser(walletDetail.getOpenId(), walletDetail.getMoney(), walletDetail.getOrderNo(), walletDetail.getId());
        if (count>0){
            return R.ok();
        }
        return R.reError("提现失败");
    }

}
