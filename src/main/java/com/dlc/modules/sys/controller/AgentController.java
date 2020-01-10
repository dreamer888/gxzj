package com.dlc.modules.sys.controller;

import com.dlc.common.utils.*;
import com.dlc.modules.sys.entity.DeviceEntity;
import com.dlc.modules.sys.service.AgentService;
import com.dlc.modules.sys.service.DeviceService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;


/**
 * 纸巾机设备表
 * 
 * @author dlc.dg.java
 * @email dlc.dg.java@163.com
 * @date 2018-07-15 18:06:09
 */
@RestController("sysAgentController")
@RequestMapping("/sys/agent")
public class AgentController {
	@Autowired
	private AgentService agentService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/select")
	@RequiresPermissions("sys:agent:list")
	public R list(@RequestParam Map<String, Object> params){
		List<Map<String,Object>> list = agentService.queryListByType(params);
		return R.ok().put("agent", list);
	}
	

	
}
