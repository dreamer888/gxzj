package com.dlc.modules.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.dlc.common.utils.*;
import com.dlc.modules.api.entity.AdvTotal;
import com.dlc.modules.api.entity.Advertising;
import com.dlc.modules.api.entity.Agent;
import com.dlc.modules.api.service.*;
import com.dlc.modules.qd.utils.GetWeiXinCode;
import com.dlc.modules.qd.utils.MyConfig;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chenyuexin
 * @version 1.0
 * @date 2018-07-13 09:54
 */
@RestController
@RequestMapping("/api/h5")
public class UserInfoController {
    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private OfficialAccountsService officialAccountsService;

    @Autowired
    private GZHIncomeDetailService gzhIncomeDetailService;

    @Autowired
    private DeviceService deviceService;
    @Autowired
    private AdvertisingService advertisingService;
    @Autowired
    private AdvTotalService advTotalService;

    private static RedisUtils redisUtils;

    private static Logger log = LoggerFactory.getLogger(UserInfoController.class);

    //获取用户信息
    @RequestMapping("/getOpenId")
    public R saveUserInfo(HttpServletRequest request, String code) throws Exception {
        HttpSession session = request.getSession();
        MyConfig config = new MyConfig();
        log.info(">>code:"+code+",request.getSession:"+session.getId());
        if(org.springframework.util.StringUtils.isEmpty(code)){
            return R.reError("Code is wrong,请与管理员联系");
        }
        JSONObject jsonObject;
        JSONObject result = GetWeiXinCode.getOpenId(request,code);

        String gxzjjAcceToken = result.get("access_token").toString();
        String openId = result.get("openid").toString();
        //  String gxzjjAcceToken = GetWeiXinCode.getAccessToken(config.getAppID(), config.getAppSecret(), "gxzjjAcceToken");

      //  String accToken = GetWeiXinCode.getAccToken(code);
       /* if(org.springframework.util.StringUtils.isEmpty(openId)){
            return R.reError("openId is wrong,请与管理员联系");
        }*/
       // String accessToken = GetWeiXinCode.getAccessToken(config.getAppID(),config.getAppSecret());
       // log.info("++++++++++++++"+accessToken+"+++++++++++++++++++++++");

      /*  if(org.springframework.util.StringUtils.isEmpty(accessToken)){
            return R.reError("accessToken is wrong,请与管理员联系");
        }*/
        //String openId =jsonObject1.get("openid").toString();

        jsonObject = GetWeiXinCode.getInfoUrlByAccessToken(gxzjjAcceToken,openId);
        /*JSONObject ticket = GetWeiXinCode.getTicket(accessToken);
        jsonObject.put("ticket",ticket);*/

        userInfoService.save(jsonObject);

        Map<String,Object> openIdMap = new HashMap<>();
        openIdMap.put("openId",jsonObject.get("openid").toString());
        log.info("++++++++++++++++++openId="+jsonObject.get("openid").toString());
        return R.reOk(openIdMap);
    }

    //获取用户信息
    @RequestMapping("/getUserInfo")
    public R queryUserInfo(String openId){
        if (StringUtils.isBlank(openId)){
            return R.reError("openId不能为空");
        }
        //String token  = redisUtils.get("tokenAndOpenid");

        Map<String,Object> map = userInfoService.queryUserInfo(openId);
        //JSONObject jsonObject = GetWeiXinCode.getInfoUrlByAccessToken(token,openId);
        //log.info("++++++++++++jsonObject"+jsonObject+"+++++++++++++++++");
        if (map==null){
            return R.reError("没有该用户信息");
        }
        return R.reOk(map);
    }

    /**
     * 用户端公众号列表
     * @return
     */
    @RequestMapping("/getUserOfficialList")
    public R queryUserOfficialList(@RequestParam Map<String, Object> params){
        if (org.springframework.util.StringUtils.isEmpty(params.get("deviceNo"))){
            return R.reError("设备编号不能为空");
        }

        Object deviceNo = params.get("deviceNo");
        params.put("page",1);
        params.put("limit",9);
        //上架的公众号
        params.put("status",1);
        Query query =new Query(params);
        List<Map<String,Object>> list = officialAccountsService.queryOfficialAccountsList(query);
        query.put("deviceNo",deviceNo);
        String addressDetail = deviceService.queryDeviceAddress(query.get("deviceNo").toString());

        //根据设备编码拿到设备地址


        JSONObject jsonObject = new JSONObject();
        jsonObject.put("wx_public",list);
        jsonObject.put("addressDetail",addressDetail);

        return R.reOk(jsonObject);
       /* int total = officialAccountsService.queryofficialAccountsCount(query);
        PageUtils pageUtil = new PageUtils(list, total, query.getLimit(), query.getPage());*/

    }

    /**
     * 用户点击推广公众号获取的信息(生成带参数的二维码)
     * @return 用户openId  公众号主键Id
     */
    @RequestMapping("/getUserOfficialInfo")
    public R queryUserOfficiaInfo(String openid, Long id, String imei, String deviceNo) {
        if (StringUtils.isBlank(openid) ||StringUtils.isBlank(imei) || null == id||StringUtils.isBlank(deviceNo)) {
            log.info("++++++++++++++++++deviceNo==="+deviceNo);
            return R.reError("参数不存在！");
        }

        Map<String,Object> officiaMap =  officialAccountsService.queryOfficialInfoById(id);

        String appId = officiaMap.get("appId").toString();
        String freeCode = officiaMap.get("freeCode").toString();
        String appSecret = officiaMap.get("appSecret").toString();
        int upperLimit = Integer.parseInt(officiaMap.get("upperLimit").toString());
        //判断公众号每日关注上限
        //查询改设备绑定的公众号的每日关注次数
        int count = gzhIncomeDetailService.queryOfficialAccountsListCount(deviceNo,appId);
        if (count > upperLimit){
            return R.reError("该公众号今日关注已达到上限");
        }

        String qrCodeImg = WxQRUtil.getQRCodeImg(appSecret, imei, openid, appId, deviceNo,freeCode);

        officiaMap.put("url",qrCodeImg);
        return R.reOk(officiaMap);
    }

    //轮播图广告
    @RequestMapping("/sowingMapList")
    public R sowingMapList(@RequestParam Map<String,Object> params){
        log.info("sowingMapList-----params---->"+params.toString());
        List<Map<String,Object>> list = advertisingService.sowingMapList(params);
        if (list.size() != 0){
            return R.reOk(list);
        }else {
            return R.error("该地区暂无广告推荐！");
        }
    }
    //添加广告的展示次数
    @RequestMapping("/addShowNum")
    public R AdvTotalShowNum(Long advId){
        Advertising adv = advertisingService.selectById(advId);
        if (adv.getType() == 2){
            return  advTotalService.addAdvShowNum(advId);
        }else {
            //添加一次展示次数
            advTotalService.addCpcAdvShowNum(advId);
            return R.reOk();
        }
    }
    //添加广告点击次数
    @RequestMapping("/addClickNum")
    public R AdvTotalClickNum(Long advId){
        Advertising adv = advertisingService.selectById(advId);
        if (adv.getType() == 1){
            return advTotalService.addClickNum(advId);
        }else {
            //添加一次点击次数
            advTotalService.addCpmAdvClickNum(advId);
            return R.reOk(advertisingService.AdvertisingInfo(advId));
        }
    }

    @RequestMapping("/queryDeviceStatus")
    public R queryDeviceStatus(String deviceNo){
        int status = deviceService.queryDeviceStatus(deviceNo);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status",status);
        return R.reOk(jsonObject);
    }

}
