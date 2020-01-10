package com.dlc.modules.api.controller;

import com.alibaba.fastjson.JSON;
import com.dlc.common.exception.RRException;
import com.dlc.common.utils.ConfigConstant;
import com.dlc.common.utils.R;
import com.dlc.common.utils.RedisUtils;
import com.dlc.modules.api.entity.UserConsume;
import com.dlc.modules.api.service.*;
import com.dlc.modules.qd.utils.WxPayUtils;
import com.github.wxpay.sdk.WXPayUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Map;
import java.util.SortedMap;

/**
 * @author chenyuexin
 * @version 1.0
 * @date 2018-07-14 17:24
 */
@RestController
@RequestMapping("/api/h5")
public class WxPayController {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private PayService payService;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private GLService glService;

    @Autowired
    private UserConsumeService userConsumeService;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private CommissionDetailService commissionDetailService;

    @Value("${project.url}")
    private String projectUrl;


    @RequestMapping("/orderPay")
    public R orderPay(String openId, BigDecimal money, String orderNo, HttpServletResponse response) {
        if (StringUtils.isBlank(orderNo) || StringUtils.isBlank(openId)) {
            return R.reError("参数不能为空");
        }

        /*Device eq = eqService.queryObject(order.getEqId());
        if (eq == null || eq.getStatus().intValue() != 1) {
            return R.error("设备故障，或不在线");
        }*/
        //回调地址
        String notify_url = projectUrl +"/api/h5/wxNotify";
        log.info("projectUrl--------->"+notify_url);
        try {
            SortedMap<String, String> map = payService.wxPay(orderNo, money, openId, notify_url);
            return R.reOk(map);
        } catch (Exception e) {
            throw new RRException("微信支付失败", e);
        }

    }

    //微信支付后回调方法
    @RequestMapping(value = "/wxNotify", method = RequestMethod.POST)
    public void wcPayNotify(HttpServletRequest request, HttpServletResponse response) {
        log.info(">>进入微信回调");
        InputStream inStream;
        try {
            inStream = request.getInputStream();
            ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = inStream.read(buffer)) != -1) {
                outSteam.write(buffer, 0, len);
            }
            outSteam.close();
            inStream.close();
            String result = new String(outSteam.toByteArray(), "utf-8");
            Map<String, String> map = WxPayUtils.doXMLParse(result);
            log.info(">>进入微信回调" + map);
            if (map.get("return_code").equals("SUCCESS")) {

                //订单号
                String out_trade_no = map.get("out_trade_no");
                String total_fee = map.get("total_fee");
                String tradeNo = map.get("transaction_id");
                BigDecimal money = BigDecimal.valueOf(Long.valueOf(total_fee)).divide(new BigDecimal(100));
                //String money = String.valueOf(BigDecimal.valueOf(Long.valueOf(total_fee)).divide(new BigDecimal(100))) ;
                log.info("out_trade_no:========" + out_trade_no + ",money:--------" + money);


                //通过orderNo查询单这个订单的imei
                Map<String, Object> infoMap = userConsumeService.queryDeviceImei(out_trade_no);
                String imei = infoMap.get("imei").toString();

                //String imei ="9p5b47124a95e3f";

                int num = (int) infoMap.get("num");
                String deviceNo = infoMap.get("deviceNo").toString();

                //调用硬件出纸巾
                JSONObject tissue = glService.getTissue(imei, out_trade_no, num);
                String status = tissue.get("result").toString();
                JSONArray onlineArray = (JSONArray) tissue.get("online");

                log.info("++++++++++++++" + tissue.toString() + "+++++++++++++");
                int payStatus = 0;
                //status需要等于0和online同时存在才能说明出纸巾成功
                if ("0".equals(status) && onlineArray.size() > 0) {
                    log.info("+++++++++设备成功出货++++++++++++");
                    //修改设备商品库存
                    deviceService.updateGoodsNum(deviceNo, num);
                    payStatus = 1;
                    //int totalMoney = Integer.valueOf(total_fee);
                    int totalMoney = 100;
                    commissionDetailService.saveCommissionDetail(totalMoney, deviceNo, imei, out_trade_no, num);
                } else {
                    log.info("---------设备出货异常------------");
                    payStatus = 2;
                }
                // 修改订单记录
                log.info("payStatus--->" + payStatus);
                userConsumeService.updateOrder(out_trade_no, tradeNo, payStatus);
                log.info(">>微信回调成功");
                response.getWriter().print("SUCCESS");
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info(">>回调通知异常");
        }
    }

    /**
     * 微信退款
     *
     * @param orderNo
     * @return
     */
    @RequestMapping("/orderRefund")
    public R orderRefund(String orderNo) throws Exception {
        if (StringUtils.isBlank(orderNo)) {
            return R.reError("缺少参数-订单号");
        }
        UserConsume userConsume = userConsumeService.queryUserOrderInfoOrderNo(orderNo);
        String res = payService.wxRefund(userConsume);
        Map<String, String> map = WxPayUtils.doXMLParse(res);
        log.info("+++++Map==="+map);
        if (res == null) {
            return R.reError("退款失败");
        } else {
            return R.reOk();
        }
    }

    /**
     * 微信退款回调
     *
     * @return
     */
    @RequestMapping(value = "/wxRefoundNotify", method = RequestMethod.POST)
    public void wxRefoundNotify(HttpServletRequest request, HttpServletResponse response) {
        log.info(">>进入微信退款回调");
        InputStream inStream;
        try {
            inStream = request.getInputStream();
            ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = inStream.read(buffer)) != -1) {
                outSteam.write(buffer, 0, len);
            }
            outSteam.close();
            inStream.close();
            String result = new String(outSteam.toByteArray(), "utf-8");
            Map<String, String> map = WxPayUtils.doXMLParse(result);

            log.info(">>进入微信退款回调" + map);
            if (map.get("return_code").equals("SUCCESS")) {
                //更行订单数据
                //回调成功
                log.info(">>退款回调成功执行操作!");
                String req_info = map.get("req_info");  //加密信息
                String reqInfo = WxPayUtils.decryptData(req_info);
                log.info("base64>>" + reqInfo);
                Map<String, String> reInfoMap = WXPayUtil.xmlToMap(reqInfo);
                log.info(">> reInfoMap" + reInfoMap);
                // 转换成map
                log.info("reInfoMap=" + JSON.toJSONString(reInfoMap));
                // OrderEntity orderEntity = orderService.queryOrderByOutTradeNo(reInfoMap.get("out_trade_no"));
                UserConsume userConsume = userConsumeService.queryUserOrderInfoOrderNo(reInfoMap.get("out_trade_no"));
                if (userConsume != null) {
                    //更新购买纸巾订单状态 设为已退款
                    //状态: 0未支付 1已完成 2未出货 3已退款
                    Integer res = userConsumeService.updateOrder(userConsume.getOrderNo(), userConsume.getTransactionNo(), 3);
                    if (res > 0) {
                        log.info("++++++++++++退款更改状态成功++++++++++++");
                    } else {
                        log.error("------------退款更改状态失败-----------");
                    }
                }
                log.info(">>微信退款回调完成");
                response.getWriter().print("SUCCESS");
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info(">>退款回调通知异常");

        }
    }


}
