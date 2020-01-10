package com.dlc.modules.sys.controller;

import com.dlc.common.utils.PageUtils;
import com.dlc.common.utils.Query;
import com.dlc.common.utils.R;
import com.dlc.modules.api.entity.GlWalletDetail;
import com.dlc.modules.sys.entity.AdvertisingEntity;
import com.dlc.modules.sys.entity.AgencyEntity;
import com.dlc.modules.sys.entity.GlWalletDetailEntity;
import com.dlc.modules.sys.service.GlWalletDetailService;
import com.dlc.modules.sys.service.GlWalletService;
import com.dlc.modules.sys.service.UserLikeGzhService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;



@RestController("sysGlWalletController")
@RequestMapping("/sys/glWallet")
public class GlWalletController {
	@Autowired
	private GlWalletService glWalletService;
	@Autowired
	private GlWalletDetailService glWalletDetailService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:glwallet:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<Map<String,Object>> glWalletList = glWalletDetailService.queryList(query);
		int total = glWalletDetailService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(glWalletList, total, query.getLimit(), query.getPage());
		
		return R.reOk().put("page", pageUtil);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	//@RequiresPermissions("sys:glwallet:info")
	public R info(@PathVariable("id") Long id){
		GlWalletDetailEntity userLikeGzh = glWalletDetailService.queryObject(id);
		Date start = userLikeGzh.getCreateTime();
		SimpleDateFormat sft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		userLikeGzh.setCreateTimes(sft.format(start));
		return R.ok().put("userLikeGzh", userLikeGzh);
	}

	/**
	 * 支出统计
	 */
	@RequestMapping("/payment")
	@RequiresPermissions("sys:glwallet:list")
	public R payment(@RequestParam Map<String, Object> params){
		//查询列表数据
		Query query = new Query(params);

		List<Map<String,Object>> paymentList = glWalletDetailService.payment(query);
		int total = glWalletDetailService.paymentTotal(query);

		PageUtils pageUtil = new PageUtils(paymentList, total, query.getLimit(), query.getPage());

		return R.reOk().put("page", pageUtil);
	}

	@RequestMapping("/moneyTotal")
	@RequiresPermissions("sys:glwallet:list")
	public R moneyTotal(){
		int total = glWalletService.moneyTotal();
		return R.reOk().put("moneyTotal", total);
	}

    }
	

