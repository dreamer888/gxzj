/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: UserInfoController
 * Author:   Administrator
 * Date:     2018/5/18 18:49
 * Description: 用户基本信息
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.dlc.modules.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.dlc.common.utils.*;
import com.dlc.common.validator.ValidatorUtils;
import com.dlc.common.validator.group.AddGroup;
import com.dlc.common.validator.group.LoginGroup;
import com.dlc.common.validator.group.UpdateGroup;
import com.dlc.modules.api.entity.Agent;
import com.dlc.modules.api.entity.GzhIncomeDetail;
import com.dlc.modules.api.entity.UserInfo;
import com.dlc.modules.api.service.*;
import com.dlc.modules.api.vo.AgentVo;
import com.dlc.modules.api.vo.UserInfoVo;
import com.dlc.modules.qd.utils.MD5Util;
import com.dlc.modules.qd.utils.PhoneCodeVer;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/agent")
public class AgentController extends BaseController {
    final Logger logger = LoggerFactory.getLogger(UserInfoController.class);
    @Autowired
    private AgentService agentService;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private CommissionDetailService detailService;
    @Autowired
    private GZHIncomeDetailService incomeDetailService;


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public R login(Agent agent, HttpServletRequest req){
        logger.info("用户进入登录状态..................");
        ValidatorUtils.validateEntity(agent, LoginGroup.class);
        JSONObject obj = this.agentService.login(agent,req);
        if(obj.containsKey("agent")){
            req.getSession().setAttribute(ConfigConstant.ACCOUNT,obj.get("agent"));
            //如果用户没有openId，就更新用户的openId 否则不做处理
            AgentVo agentVo = (AgentVo) obj.get("agent");
            if(StringUtils.isBlank(agentVo.getOpenId())){
                Agent temp = new Agent();
                temp.setId(agentVo.getId());
                temp.setOpenId(agent.getOpenId());
                this.agentService.updateAgent(temp);
            }
            return R.reOk(obj);
        }
        return R.reError(CodeAndMsg.ERROR_USER_LOGIN);
    }

    /**
     * 发送验证码
     * @param phone
     * @return
     */
    @RequestMapping(value="/sendCode", method = RequestMethod.POST)
    public R sendCode(String phone){
        if(StringUtils.isEmpty(phone) || !PhoneCodeVer.isPhoneNum(phone)){
            return R.reError("手机号码有误");
        }
        PhoneCodeVer.sendCode(phone);
        return R.reOk();
    }

    /**
     * 忘记密码
     * @param phone  手机号
     * @param phoneCode  验证码
     * @param password  密码
     * @param rePassword  二次密码
     * @return
     */
    @RequestMapping(value="/forgetPassword", method = RequestMethod.POST)
    public R forgetPassword(String phone, String phoneCode, String password, String rePassword){

        if(StringUtils.isEmpty(phone) || !PhoneCodeVer.isPhoneNum(phone)){
            return R.reError("手机号码有误");
        }
        if(StringUtils.isEmpty(phoneCode) || !phoneCode.equals(redisUtils.get(phone))){
            return R.reError("验证码有误");
        }
        if(StringUtils.isEmpty(password) || !password.equals(rePassword)){
            return R.reError("密码为空，或密码不一致");
        }
        Map<String, Object> map = agentService.getAgentByPhone(phone);
        if(map == null){
            return R.reError("手机号码未注册用户");
        }
        Agent agent = new Agent();
        agent.setId((Long)map.get("id"));
        agent.setPassword(MD5Util.MD5Encode(password,"utf-8"));
        agentService.updateAgent(agent);
        return R.reOk();
    }

    /**
     * @api {POST} /account/qd/updatePassword  修改密码
     * @apiGroup login
     * @apiParam {String} password 旧密码
     * @apiParam {String} newPassword 新密码
     * @apiParam {String} rePassword 确认新密码
     * @apiSuccessExample {json} 成功的响应
     * */
    @RequestMapping(value = "/updatePwd", method = RequestMethod.POST)
    public R updatePwd(String password, String newPassword, String rePassword, HttpServletRequest req){
        AgentVo agentVo = (AgentVo) req.getSession().getAttribute(ConfigConstant.ACCOUNT);
        if (agentVo == null){
            return R.error(-2,"用户未登录");
        }
        if (!(agentVo.getPassword().equalsIgnoreCase(MD5Util.MD5Encode(password,"utf-8")))){
            return R.error("旧密码错误");
        }
        if (StringUtils.isBlank(newPassword)){
            return R.error("新密码不能为空");
        }
        if (!(newPassword.equalsIgnoreCase(rePassword))){
            return R.error("两次密码不一致");
        }
        Agent agent = new Agent();
        agent.setId(agentVo.getId());
        agent.setPassword(MD5Util.MD5Encode(newPassword,"utf-8"));
        agentService.updateAgent(agent);
        return R.reOk();
    }

    /**
     * 修改个人信息
     * @param agent
     * @param req
     * @return
     */
    @RequestMapping("/updateAgent")
    public R updateUserInfo(Agent agent, HttpServletRequest req) {
        AgentVo agentVo = (AgentVo) req.getSession().getAttribute(ConfigConstant.ACCOUNT);
        if (agentVo == null) {
            return R.error(-2,"用户未登录");
        }
        ValidatorUtils.validateEntity(agent, UpdateGroup.class);
        agent.setId(agentVo.getId());
        agentService.updateAgent(agent);
        return R.reOk();
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

//    /**
//     * 查找用户信息
//     * @return
//     */
//    @RequestMapping(value="/findUserInfo", method = RequestMethod.POST)
//    public R findUserInfo(HttpServletRequest req){
//        UserInfoVo userVo = (UserInfoVo) req.getSession().getAttribute(ConfigConstant.ACCOUNT);
//        if (userVo == null) {
//            return R.error(-2,"用户未登录");
//        }
//        Map<String,Object> map = this.agentService.findUserInfoById(getUserId(req));
//        if(null == map){
//            return R.reError("用户不存在");
//        }
//        return R.reOk(map);
//    }

    @RequestMapping(value = "/listAgent", method = RequestMethod.POST)
    public R listAgent(String status, String role, HttpServletRequest req){
        Map<String, Object> map = new HashMap<>();
        if(StringUtils.isNotBlank(status)){// 按用户状态
            map.put("status", status);
        }
        if(StringUtils.isNotBlank(role)){
            map.put("role", role);
        }
        map.put("agentId", getAgentId(req));
        List<Map<String, Object>> listMap = this.agentService.listAgent(map);
        return R.reOk(listMap);
    }

    @RequestMapping(value = "/saveAgent", method = RequestMethod.POST)
    public R saveAgent(Agent agent, String rePassword, BigDecimal commissionValueFloat, HttpServletRequest req){
        agent.setType(2);//类型为2表示代理商
        ValidatorUtils.validateEntity(agent, AddGroup.class);
        if(null == commissionValueFloat){
            return R.reError("分佣值不能为空");
        }
        if(agent.getCommissionType() == 2){ //如果分佣类型等于 按比例
            commissionValueFloat = commissionValueFloat.multiply(new BigDecimal(100));
        }else{
            if(commissionValueFloat.intValue()>100){
                return R.reError("百分比不能大于100，并且只能是整数");
            }
        }
        agent.setCommissionValue(commissionValueFloat.intValue());
        if(!agent.getPassword().equals(rePassword)){
            return R.reError("两次密码不一致");
        }
        Map<String, Object> map = agentService.getAgentByPhone(agent.getPhone());
        if(map != null){
            return R.reError("该用户已存在");
        }
        agent.setParentId(getAgentId(req));
        agent.setPassword(MD5Util.MD5Encode(agent.getPassword(), "utf-8"));
        agent.setWallet(0); //初始化钱包金额为0
        agent.setDeleteStatus(0); //初始化删除状态为0
        this.agentService.saveAgent(agent);
        return R.reOk();
    }

    /**
     * 假删除代理
     * @param agentId
     * @param req
     * @return
     */
    @RequestMapping(value = "/deleteAgent", method = RequestMethod.POST)
    public R deleteAgent(String agentId, HttpServletRequest req){
        if(StringUtils.isBlank(agentId)){
            return R.reError("id不能为空");
        }
        this.agentService.deleteAgent(Long.valueOf(agentId));
        return R.reOk();
    }

    @RequestMapping(value = "/saveCommissionDetail", method = RequestMethod.POST)
    public R saveCommissionDetail(Integer money, String deviceNo, String imei, String orderNo,Integer goodsNum){
        this.detailService.saveCommissionDetail(money, deviceNo, imei, orderNo,goodsNum);
        return R.reOk();
    }

    @RequestMapping(value = "/saveGzhIncome", method = RequestMethod.POST)
    public R saveGzhIncome(GzhIncomeDetail incomeDetail){
        this.incomeDetailService.saveIncome(incomeDetail);
        return R.reOk();
    }

    @RequestMapping(value = "/updateAgent", method = RequestMethod.POST)
    public R updateAgent(Agent agent, String agentId,BigDecimal commissionValueFloat, String rePassword, HttpServletRequest req){
        if(StringUtils.isNotBlank(agent.getPassword()) && StringUtils.isNotBlank(rePassword)){
            if(!agent.getPassword().equals(rePassword)){
                return R.reError("两次密码不一致");
            }
            //下面是更新用户密码
            agent.setPassword(MD5Util.MD5Encode(agent.getPassword(), "utf-8"));
        }else{
            agent.setPassword(null);
        }
        if(StringUtils.isBlank(agentId)){
            return R.reError("id不能为空");
        }
        if(null != commissionValueFloat){
            if(agent.getCommissionType() == 2){ //如果分佣类型等于 按比例
                commissionValueFloat = commissionValueFloat.multiply(new BigDecimal(100));
            }else{
                if(commissionValueFloat.intValue()>100){
                    return R.reError("百分比不能大于100，并且只能是整数");
                }
            }
            agent.setCommissionValue(commissionValueFloat.intValue());
        }
        agent.setId(Long.valueOf(agentId));
        this.agentService.updateAgent(agent);
        return R.reOk();
    }

    @RequestMapping(value = "/findAgent", method = RequestMethod.POST)
    public R findAgent(String agentId, HttpServletRequest req){
        if(StringUtils.isBlank(agentId)){
            return R.reError("id不能为空");
        }
        Map<String, Object> map = this.agentService.findAgent(Long.valueOf(agentId));
        return R.reOk(map);
    }
}
