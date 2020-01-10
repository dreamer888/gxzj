package com.dlc.modules.api.controller;

import com.dlc.common.utils.ConfigConstant;
import com.dlc.common.utils.PageUtils;
import com.dlc.common.utils.Query;
import com.dlc.common.utils.R;
import com.dlc.modules.api.entity.*;
import com.dlc.modules.api.service.*;
import com.dlc.modules.sys.entity.ParamSetEntity;
import com.dlc.modules.sys.service.ParamSetService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 用户消费记录模块controller
 *
 * @author chenyuexin
 * @version 1.0
 * @date 2018-07-14 15:12
 */
@RestController
    @RequestMapping("api/h5")
public class UserConsumeController {

    @Autowired
    private UserConsumeService userConsumeService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private GLService glService;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private AgentDeviceRelationService agentDeviceRelationService;

    @Autowired
    private ParamSetService paramSetService;

    @Autowired
    private FreeMapperRecordService freeMapperRecordService;

    private Logger log = LoggerFactory.getLogger(getClass());


    /**
     * 购买纸巾接口
     * @param deviceNo
     * @return
     */
    @RequestMapping("/getDeviceGoods")
    public R getDeviceGoods(String deviceNo){
        if (org.apache.commons.lang.StringUtils.isBlank(deviceNo)){
            return R.error("设备号不能为空");
        }
        return deviceService.queryDeviceGoods(deviceNo);
    }



    /**
     * 创建用户消费订单记录
     *
     * @param userConsume
     * @return
     */
    @RequestMapping("/createUserOrder")
    public R createUserOrder(UserConsume userConsume) {
        String orderNo = userConsumeService.save(userConsume);
        return R.reOk(orderNo);
    }

    /**
     * 获取用户端订单记录
     *
     * @param params 参数（page:页码，limit:每页条数，openId:openId）
     * @return
     */
    @RequestMapping("/getUserOrderList")
    public R list(@RequestParam Map<String, Object> params) {
        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit")) || StringUtils.isEmpty(params.get("openId"))) {
            return R.error("分页信息不能为空");
        }
        Query query = new Query(params);
        List<Map<String, Object>> userOrderList = userConsumeService.queryUserOrderList(query);
        int total = userConsumeService.queryUserOrderCount(query);
        PageUtils pageUtil = new PageUtils(userOrderList, total, query.getLimit(), query.getPage());
        return R.reOk(pageUtil);
    }

    @RequestMapping("/userOrderInfo")
    public R info(Long id) {
        if (id == null) {
            return R.reError("参数不能为空");
        }
        Map<String, Object> orderInfo = userConsumeService.queryUserOrderInfo(id);
        if (orderInfo == null) {
            return R.reError("订单不存在");
        }
        return R.reOk(orderInfo);
    }


    /**
     * 免费领取纸巾接口
     *
     * @param imei
     * @param openid
     * @param appid
     * @return
     */
    @RequestMapping("/gainTissue")
    public R gainTissueFree(String deviceNo, String imei, String openid, String appid) {

        //判断该用户是否已经在公众号领取过纸巾了
        // UserInfo userInfo = userInfoService.queryUserAppId(appid, openid);
        FreeMapperRecord record = freeMapperRecordService.queryRecordByAppId(appid,openid);
        if (null != record) {
            return R.reError("您已经领取过纸巾了");
        }

        //判断用户每周领取纸巾是否上限
        ParamSetEntity paramSet = paramSetService.queryParamSetInfo();
        //上限
        int limitUp = Integer.parseInt(paramSet.getParamValue());
        Map<String, Object> map = userInfoService.queryUserInfo(openid);
        int count = Integer.parseInt(map.get("count").toString());
        if (count > limitUp) {
            return R.reError("您本周已经达到领取纸巾上限!,请下周再来哦");
        }

        //调用硬件接口
        JSONObject res = glService.getTissue(imei, openid, 1);
        String status = res.get("result").toString();
        JSONArray onlineArray = (JSONArray) res.get("online");
        if ("0".equals(status)&& onlineArray.size() > 0) {
            //修改设备商品库存
            deviceService.updateGoodsNum(deviceNo, 1);
            //添加用户领取标识标识
            userInfoService.updateUserAppId(openid, appid);
            //添加领取记录
            FreeMapperRecord freeMapperRecord = new FreeMapperRecord();
            freeMapperRecord.setAppId(appid);
            freeMapperRecord.setOpenId(openid);
            freeMapperRecord.setCreateTime(new Date());
            int resCount =freeMapperRecordService.addRecord( freeMapperRecord);
            if (resCount>0){
                log.info("++++++++添加领取记录成功+++++++++++");
            }else {
                log.info("--------添加领取记录失败-----------");
            }

            //更新用户每周领取数量加1
            userInfoService.updateUserCount(openid);

            //统计出纸巾
            try {
                //通过设备编码查询出设备代理商
                List<Map<String, Object>> relationList = agentDeviceRelationService.findAgentDeviceRelationByImei(imei);
                if (!CollectionUtils.isEmpty(relationList)){
                    Long agentId = (Long) relationList.get(0).get("agentId");
                    AgentDeviceRelation agentDeviceRelation = new AgentDeviceRelation();
                    agentDeviceRelation.setFreePaperTotal(1);
                    agentDeviceRelation.setPayPaperSum(0);
                    agentDeviceRelation.setPayPaperTotal(0);
                    agentDeviceRelation.setGzhLikeTotal(0);
                    agentDeviceRelation.setGzhIncomeSum(0);
                    agentDeviceRelation.setCommissionSum(0);
                    agentDeviceRelation.setDeviceNo(deviceNo);
                    agentDeviceRelation.setAgentId(agentId);
                    agentDeviceRelationService.update(agentDeviceRelation);
                    log.info("+++++++++++++++统计免费出纸巾成功+++++++++++++++");
                }else {
                    log.info("--------无法通过imei查询出关系");
                }

            } catch (Exception e) {
                e.printStackTrace();
                log.error("-------------统计免费出纸巾异常-------------");
            }

        } else {
            return R.reError("机器故障!");
        }

        return R.reOk();
    }

    /**
     * 根据订单号查询订单状状态，判断订单是否成功出纸巾了
     * @param orderNo
     * @return
     */
    @RequestMapping("/queryConsumeStatus")
    public R queryUserConsume(String orderNo){
        Integer status = userConsumeService.queryUserOrderStatus(orderNo);
        if (status==1){
            return R.reOk();
        }else if (status==2){
            return R.reError("设备故障,出货失败");
        }
        return null;
    }

}
