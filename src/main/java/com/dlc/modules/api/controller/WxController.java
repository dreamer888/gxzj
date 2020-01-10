package com.dlc.modules.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.dlc.common.exception.RRException;
import com.dlc.common.utils.ConfigConstant;
import com.dlc.common.utils.R;
import com.dlc.common.utils.RedisUtils;
import com.dlc.modules.api.service.WechatService;
import com.dlc.modules.qd.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author chenyuexin
 * @version 1.0
 * @date 2018-07-12 16:49
 */
@RestController
@RequestMapping("/wx")
public class WxController {
    private Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private RedisUtils redisUtils;

  //  @Value("${DNBX_TOKEN}")
    //private String DNBX_TOKEN = "chenyuexin1993";

    @Autowired
    private WechatService wechatService;

    @RequestMapping("/getOpenId")
    public R getOpenId(HttpServletRequest request, String code) {
        try {
            String url = WxUtil.getOpenId(code);
            JSONObject jsonObject = WxUtil.doGetJson(url);
            if (!StringUtils.isEmpty(jsonObject.get("openid"))) {
                request.getSession().setAttribute(ConfigConstant.USER, jsonObject.get("openid"));
            }
            Map<String, Object> data = new HashMap<>();
            data.put("openId", jsonObject.get("openid"));
            return R.reOk(data);
        } catch (Exception e) {
            throw new RRException("获取openId失败，请重新获取", e);
        }
    }

    @RequestMapping("/getTicket")
    public JSONObject getTicket(HttpServletRequest request) throws Exception {
        String ticket = redisUtils.get("gxzjj_ticket");
        log.info("ticket--->" + ticket);
        if (ticket == null || ticket.equals("")) {
            MyConfig config = new MyConfig();
            String accessToken = GetWeiXinCode.getAccessToken(config.getAppID(), config.getAppSecret());

            log.info("accessToken--" + accessToken);
            JSONObject newTicket = GetWeiXinCode.getTicket(accessToken);
            return newTicket;
        } else {
            return JSONObject.parseObject(ticket);
        }
    }

    /**
     * 微信公众号接入
     *
     * @param
     * @return
     * @throws IOException
     */
    @RequestMapping("/connect")
    public void connectWeixin(HttpServletRequest request, HttpServletResponse response,String code) throws IOException {
        // 将请求、响应的编码均设置为UTF-8（防止中文乱码）
        request.setCharacterEncoding("UTF-8");  //微信服务器POST消息时用的是UTF-8编码，在接收时也要用同样的编码，否则中文会乱码；
        response.setCharacterEncoding("UTF-8"); //在响应消息（回复消息给用户）时，也将编码方式设置为UTF-8，原理同上；
        boolean isGet = request.getMethod().toLowerCase().equals("get");

        PrintWriter out = response.getWriter();

        try {
            if (isGet) {
                String signature = request.getParameter("signature");// 微信加密签名
                String timestamp = request.getParameter("timestamp");// 时间戳
                String nonce = request.getParameter("nonce");// 随机数
                String echostr = request.getParameter("echostr");//随机字符串

                // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
                if (SignUtil.checkSignature(MD5Util.MD5Encode(code,"utf-8"), signature, timestamp, nonce)) {
                    log.info("Connect the weixin server is successful.");
                    response.getWriter().write(echostr);
                } else {
                    log.error("Failed to verify the signature!");
                }
            } else {
                String respMessage = "异常消息!";

                try {
                    respMessage = wechatService.weixinPost(request);
                    log.info(respMessage.toString());
                    out.write(respMessage);
                    log.info("The request completed successfully");
                    log.info("to weixin server " + respMessage);
                } catch (Exception e) {
                    log.error("Failed to convert the message from weixin!");

                }

            }
        } catch (Exception e) {
            log.error("Connect the weixin server is error.");
        } finally {
            out.close();
        }
    }

 /*   @RequestMapping("/getGZHUrl")
    public R getWXGZHUrl(HttpServletRequest request,HttpServletResponse response){
       String qrCodeImg = WxQRUtil.getQRCodeImg(request, response, ConfigConstant.WECHAT_APPID, ConfigConstant.WECHAT_APPSECRET);

        return R.reOk(qrCodeImg);

    }*/

}


