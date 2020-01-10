package com.dlc.modules.api.controller;

import com.dlc.common.utils.R;
import com.dlc.modules.api.entity.Agent;
import com.dlc.modules.api.service.AgentService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/***********************************
 *Class by 王楚荣
 *2018/7/19/019
 * **********************************/
@RestController
@RequestMapping("/api/apiAgent")
public class ApiAgentController {
    @Autowired
    private AgentService agentService;
    /**
     * 获取运维人员列表
     * @param params
     * @return
     */
    @RequestMapping("/getOpsList")
    public R getOps(@RequestParam Map<String,Object> params) {
        if(StringUtils.isBlank((String)params.get("agentId"))){
            return R.reError("代理商id为空");
        }
        params.put("type",3);
        params.put("status",1);
        return R.reOk(agentService.getOps(params));
    }
    /**
     * 获取上级
     * @param
     * @return
     */
    @RequestMapping("/getParent")
    public R getParent(Long id) {
        return R.reOk(agentService.selectParent(id));
    }
    /**
     * @Auther:YD
     * @Date: Creat in  2018/7/19/019
     * 我的账户
     */
    @RequestMapping("/queryAgentInfo")
    public R queryAgentInfo(Long id){
        return R.reOk(agentService.queryAgentInfo(id));
    }
    /**
     * @Auther:YD
     * @Date: Creat in  2018/7/19/019
     *  修改头像 和保存用户信息
     */
    @RequestMapping("/updataHeadImgOrInfo")
    public R updataHeadImgOrInfo(Long id,String imgUrl,String name){
        Agent agent = new Agent();
        agent.setId(id);
        agent.setHeadImgUrl(imgUrl);
        agent.setName(name);
        agentService.updataHeadImgOrInfo(agent);
        return R.reOk();
    }

}
