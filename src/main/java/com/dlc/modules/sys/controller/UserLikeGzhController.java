package com.dlc.modules.sys.controller;

import com.dlc.common.utils.*;
import com.dlc.modules.sys.entity.DeviceEntity;
import com.dlc.modules.sys.entity.UserLikeGzh;
import com.dlc.modules.sys.service.DeviceService;
import com.dlc.modules.sys.service.UserLikeGzhService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;



@RestController("sysUserLikeGzhController")
@RequestMapping("/sys/userLikeGzh")
public class UserLikeGzhController {
	@Autowired
	private UserLikeGzhService userLikeGzhService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:serlikegzh:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<Map<String,Object>> userLikeGzhList = userLikeGzhService.queryList(query);
		int total = userLikeGzhService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(userLikeGzhList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	


    }
	

