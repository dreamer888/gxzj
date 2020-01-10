package com.dlc.modules.api.service;


import com.dlc.common.utils.ConfigConstant;
import com.dlc.modules.api.dao.PublicFansMapper;
import com.dlc.modules.api.dao.TempUserDeviceMapper;
import com.dlc.modules.api.entity.GzhIncomeDetail;
import com.dlc.modules.api.entity.PublicFans;
import com.dlc.modules.api.entity.TempUserDevice;
import com.dlc.modules.api.entity.TextMessage;
import com.dlc.modules.api.service.impl.GZHIncomeDetailServiceImpl;
import com.dlc.modules.qd.utils.MessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author chenyuexin
 * @version 1.0
 * @date 2018-07-19 16:00
 */
@Service
@Transactional
public class WechatService {
    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private TempUserDeviceMapper tempUserDeviceMapper;

    //关注统计service
    @Autowired
    private GZHIncomeDetailServiceImpl gzhIncomeDetailService;

    @Autowired
    private PublicFansMapper publicFansMapper;

    /**
     * 处理微信发来的请求
     *
     * @param request
     * @return
     */
    public String weixinPost(HttpServletRequest request) {
        String respMessage = null;
        Map<String, Object> parmMap = null;
        //参数
        try {

            // xml请求解析
            Map<String, String> requestMap = MessageUtil.xmlToMap(request);

            // 发送方帐号（open_id）
            String fromUserName = requestMap.get("FromUserName");
            // 公众帐号
            String toUserName = requestMap.get("ToUserName");
            // 消息类型
            String msgType = requestMap.get("MsgType");
            // 消息内容
            String content = requestMap.get("Content");

            log.info("FromUserName is:" + fromUserName + ", ToUserName is:" + toUserName + ", MsgType is:" + msgType);

            // 文本消息


            /*else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {// 事件推送
                String eventType = requestMap.get("Event");// 事件类型

                if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {// 订阅
                    respContent = "欢迎关注xxx公众号！";
                    return MessageResponse.getTextMessage(fromUserName , toUserName , respContent);
                } else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {// 自定义菜单点击事件
                    String eventKey = requestMap.get("EventKey");// 事件KEY值，与创建自定义菜单时指定的KEY值对应
                    logger.info("eventKey is:" +eventKey);
                    return xxx;
                }
            }
            //开启微信声音识别测试 2015-3-30
            else if(msgType.equals("voice"))
            {
                String recvMessage = requestMap.get("Recognition");
                //respContent = "收到的语音解析结果："+recvMessage;
                if(recvMessage!=null){
                    respContent = TulingApiProcess.getTulingResult(recvMessage);
                }else{
                    respContent = "您说的太模糊了，能不能重新说下呢？";
                }
                return MessageResponse.getTextMessage(fromUserName , toUserName , respContent);
            }
            //拍照功能
            else if(msgType.equals("pic_sysphoto"))
            {

            }
            else
            {
                return MessageResponse.getTextMessage(fromUserName , toUserName , "返回为空");
            }*/

            if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {


                log.info("++++++++++进入被动回复++++++++++++");
                //通过fromUserName获取用户关联的设备信息
                TempUserDevice userDevice = null;
                parmMap = new HashMap<>();
                parmMap.put("openid", fromUserName);
                StringBuffer paramsList = new StringBuffer();
                try {
                    userDevice = tempUserDeviceMapper.queryUserDeviceInfo(parmMap);
                    paramsList.append("deviceNo=" + userDevice.getDeviceNo() + "&").append("imei=" + userDevice.getImei() + "&").append("openid=" + userDevice.getGlOpenid() + "&").append("appid=" + userDevice.getAppid());
                    log.info("+++++++获取用户关联的设备信息成功+++++++++" + paramsList.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("---------获取用户关联的设备信息失败---------");
                }


                //这里根据关键字执行相应的逻辑，
                //用户输领取代码自动回复

                if (content.equals(userDevice.getFreeCode())) {
                    //自动回复
                    TextMessage text = new TextMessage();
                    String contentLink = "<a href =\"" + ConfigConstant.FREE_MAPPER + paramsList.toString() + "\">免费领取</a>";
                    text.setContent(contentLink);
                    text.setToUserName(fromUserName);
                    text.setFromUserName(toUserName);
                    text.setCreateTime(new Date().getTime() + "");
                    text.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
                    respMessage = MessageUtil.textMessageToXml(text);

                    //删除临时表数据
                    parmMap = new HashMap<>();
                    parmMap.put("appid", userDevice.getAppid());
                    parmMap.put("openid", userDevice.getOpenid());
                    tempUserDeviceMapper.deleteRecordByMap(parmMap);
                }
            }

            // 事件推送
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
                String eventType = requestMap.get("Event");// 事件类型
                String eventkey = requestMap.get("EventKey");
                String otherOpenid = fromUserName;

                //拼接的参数
                String params = eventkey.replace("qrscene_", "");
                //
                String[] strArr = params.split("@@");

                String deviceNo = strArr[0];
                String imei = strArr[1];
                //个联平台公众号openid
                String glopenid = strArr[2];
                String appid = strArr[3];
                String freeCode = strArr[4];

                //判断是否存在记录
                parmMap = new HashMap<>();
                parmMap.put("appid", appid);
                parmMap.put("openid", otherOpenid);
                TempUserDevice userDevice = tempUserDeviceMapper.queryUserDeviceInfo(parmMap);
                TempUserDevice tempUserDevice = new TempUserDevice();

                tempUserDevice.setAppid(appid);
                tempUserDevice.setDeviceNo(deviceNo);
                tempUserDevice.setGlOpenid(glopenid);
                tempUserDevice.setImei(imei);
                tempUserDevice.setFreeCode(freeCode);
                //第三方公众号用户openid
                tempUserDevice.setOpenid(otherOpenid);
                if (null != userDevice) {
                    try {
                        //更新
                        tempUserDeviceMapper.updateByPrimaryKeySelective(tempUserDevice);
                        log.info("+++++++++++++++对参数列表更新成功++++++++++++");
                    } catch (Exception e) {
                        e.printStackTrace();
                        log.error("+++++++++++++++对参数列表更新异常++++++++++++");
                    }

                } else {
                    try {
                        //对参数列表持久化到数据库
                        tempUserDeviceMapper.insert(tempUserDevice);
                        log.info("+++++++++++++++对参数列表持久化成功++++++++++++");
                    } catch (Exception e) {
                        e.printStackTrace();
                        log.error("+++++++++++++++对参数列表持久化异常++++++++++++");
                    }
                }


                log.info("++++++++++++++++++++" + params + "+++++++++++");
                // 订阅
                if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {

                    TextMessage text = new TextMessage();
                    text.setContent("欢迎关注!");
                    text.setToUserName(fromUserName);
                    text.setFromUserName(toUserName);
                    text.setCreateTime(new Date().getTime() + "");
                    text.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
                    respMessage = MessageUtil.textMessageToXml(text);

                    //统计关注报表
                    GzhIncomeDetail gzhIncomeDetail = new GzhIncomeDetail();
                    gzhIncomeDetail.setLikeNum(1);
                    gzhIncomeDetail.setAppId(appid);
                    gzhIncomeDetail.setDeviceImei(imei);
                    gzhIncomeDetail.setDeviceNo(deviceNo);
                    try {
                        gzhIncomeDetailService.saveIncome(gzhIncomeDetail);
                        log.info("+++++++++统计关注数正常+++++++++++");
                    } catch (Exception e) {
                        e.printStackTrace();
                        log.error("---------统计关注数异常-----------");
                    }


                    PublicFans publicFans = new PublicFans();
                    publicFans.setAppId(appid);
                    publicFans.setOpenId(glopenid);
                    publicFans.setCreateTime(new Date());
                    int res = publicFansMapper.insertSelective(publicFans);
                    if (res > 0){
                        log.info("++++++++++++增加关注粉丝记录成功+++++++++++"+publicFans.toString());
                    }else {
                        log.error("---------------增加关注粉丝记录失败-----------");
                    }



                }
               /* // TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
                else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {// 取消订阅


                }*/


               /* // 自定义菜单点击事件
                else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
                    String eventKey = eventKey1;// 事件KEY值，与创建自定义菜单时指定的KEY值对应
                    if (eventKey.equals("customer_telephone")) {
                        TextMessage text = new TextMessage();
                        text.setContent("0755-86671980");
                        text.setToUserName(fromUserName);
                        text.setFromUserName(toUserName);
                        text.setCreateTime(new Date().getTime() + "");
                        text.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);

                        respMessage = MessageUtil.textMessageToXml(text);
                    }
                }*/
            }
        } catch (Exception e) {
            log.error("error......");
        }
        return respMessage;
    }

}
