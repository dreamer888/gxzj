package com.dlc.modules.api.service.impl;

import com.dlc.common.utils.CommonUtil;
import com.dlc.common.utils.ConfigConstant;
import com.dlc.modules.api.entity.UserConsume;
import com.dlc.modules.api.service.PayService;
import com.dlc.modules.qd.utils.MyConfig;
import com.dlc.modules.qd.utils.WxPayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

@Service
public class PayServiceImpl implements PayService {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Value("${project.url}")
    private String projectUrl;
   /* @Autowired
    private OrderDao orderDao;
*/
    /*@Override
    public R zfbPay(Integer payType, String OutOrderNo, BigDecimal money, HttpServletResponse httpResponse) throws IOException {
        SortedMap<Object, Object> map = new TreeMap<>();
        AlipayClient alipayClient = new DefaultAlipayClient(MyConfig.ZFB_GATEWAY, MyConfig.ZFB_APPID, MyConfig.ZFB_PRIVATE_KEY, MyConfig.ZFB_OBJECT, MyConfig.ZFB_CHARSET, MyConfig.ZFB_PUBLIC_KEY, MyConfig.ZFB_SIGN_TYPE);
        //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody("购乐智能科技有限公司");
        model.setSubject("购乐支付");
        model.setOutTradeNo(OutOrderNo);
        model.setTimeoutExpress("30m");
        model.setTotalAmount(String.valueOf(money));
        request.setBizModel(model);
        request.setNotifyUrl(MyConfig.ZFB_NOTIFYURL);
        try {
            //这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
            map.put("data", response.getBody());//就是orderString 可以直接给客户端请求，无需再做处理。
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return R.reOk(map);
    }*/

    /**
     * 退款申请
     *
     *
     */
     /*  @Override
        public String wxRefund(UserConsume orderEntity) throws Exception {
       log.debug("------进入微信退款方法------");
        MyConfig config = new MyConfig();
        SortedMap<String,String> packageParams = new TreeMap<>();
        packageParams.put("appid",config.getAppID());
        packageParams.put("mch_id",config.getMchID());
        packageParams.put("nonce_str", WxPayUtils.getRandomStringByLength(19));
        packageParams.put("out_trade_no", orderEntity.getTransactionNo());
        packageParams.put("out_refund_no", orderEntity.getTransactionNo());
        packageParams.put("total_fee", orderEntity.getTotalPrice().multiply(new BigDecimal(100)).intValue()+"");
        packageParams.put("refund_fee", orderEntity.getMoney().multiply(new BigDecimal(100)).intValue()+"");
        packageParams.put("notify_url", "http://goule.j.xiaozhuschool.com/pay/wxPayRefundNotify");
        String sign = WxPayUtils.createSign("UTF-8", packageParams);
        packageParams.put("sign", sign);
        String result = CommonUtil.requestWithCert(MyConfig.REFUND_URL,packageParams ,5000,5000);
        return result;
    }
*/
    /*@Override
    public String zfbRefund(User orderEntity) throws AlipayApiException {
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", MyConfig.ZFB_APPID, MyConfig.ZFB_PRIVATE_KEY,"json","GBK", MyConfig.ZFB_PUBLIC_KEY,"RSA2");
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        request.setBizContent("{" +
                "\"out_trade_no\":\"" + orderEntity.getOutOrderNo() + "\","  +
                "\"refund_amount\":\"" + orderEntity.getMoney() + "\"}]}");
        AlipayTradeRefundResponse response = alipayClient.execute(request);
        if(response.isSuccess()){
            System.out.println("调用成功");
            //修改 订单
            orderEntity.setPayStatus(4);
            orderEntity.setOpenType(2);
        } else {
            System.out.println("调用失败");
        }
        return null;
    }
*/
    @Override
    public SortedMap<String, String> wxPay(String outOrderNo,BigDecimal money,String openId,String notify_url) throws Exception {
        //统一下单,获得预支付Id
        String prepayId = unifiledOrder(outOrderNo, money,openId,notify_url);
        MyConfig config = new MyConfig();
        SortedMap<String, String> packageParams = new TreeMap<>();
        packageParams.put("appId", config.getAppID());
        packageParams.put("timeStamp",String.valueOf(Calendar.getInstance().getTimeInMillis()/1000));
        packageParams.put("nonceStr", WxPayUtils.getRandomStringByLength(19));
        packageParams.put("package", "prepay_id="+prepayId);
        packageParams.put("signType", "MD5");
        packageParams.put("paySign", WxPayUtils.createSign("UTF-8", packageParams));
        return packageParams;
    }

    //微信退款
    @Override
    public String wxRefund(UserConsume userConsume) throws Exception {
        log.info("+++++++++进入微信退款方法+++++++++");
        MyConfig config = new MyConfig();
        SortedMap<String,String> packageParams = new TreeMap<>();
        packageParams.put("appid",config.getAppID());
        packageParams.put("mch_id",config.getMchID());
        packageParams.put("nonce_str", WxPayUtils.getRandomStringByLength(19));
        packageParams.put("out_trade_no", userConsume.getOrderNo());
        packageParams.put("out_refund_no", userConsume.getOrderNo());
        packageParams.put("total_fee", userConsume.getTotalPrice()+"");
        packageParams.put("refund_fee", userConsume.getTotalPrice()+"");
        packageParams.put("refund_desc", "设备故障,出纸失败");
        packageParams.put("notify_url", projectUrl+"/api/h5/wxRefoundNotify");
        String sign = WxPayUtils.createSign("UTF-8", packageParams);
        packageParams.put("sign", sign);
        String result = CommonUtil.requestWithCert(MyConfig.REFUND_URL,packageParams ,5000,5000);
        return result;
    }

    // 统一下单
    public String unifiledOrder(String outTradeNo, BigDecimal money,String openid,String notify_url) throws Exception {
       // money = money.multiply(new BigDecimal(100)); // 转化为分
        MyConfig config = new MyConfig();
        SortedMap<String, String> packageParams = new TreeMap<>();
        packageParams.put("appid", config.getAppID());
        packageParams.put("mch_id", config.getMchID());
        packageParams.put("device_info", "WEB");
        packageParams.put("body", "深圳市个联科技有限公司");
        packageParams.put("out_trade_no", outTradeNo);
        packageParams.put("total_fee", 1 + "");
        packageParams.put("fee_type", "CNY");
        packageParams.put("spbill_create_ip", WxPayUtils.getLocalhostIp());
        packageParams.put("notify_url", notify_url);
        packageParams.put("trade_type", "JSAPI");
        packageParams.put("nonce_str", WxPayUtils.getRandomStringByLength(19));
        packageParams.put("sign_type","MD5");
        packageParams.put("openid", openid);
        String sign = WxPayUtils.createSign("UTF-8", packageParams);
        packageParams.put("sign", sign);
        String result = CommonUtil.httpsRequest(MyConfig.UNIFIED_ORDER_URL, "POST", WxPayUtils.getRequestXml(packageParams));
        return getXMLPayKey(result, "prepay_id");
    }


  /*  /**
     * 根据订单号获取订单信息
     *
     * @param orderId 订单号
     * @return
     *//*
    private UserConsume getOrder(Integer orderId) {
        return orderDao.queryObject(orderId);
    }
*/


    public static String getXMLPayKey(String result, String key) throws Exception {
        Map<String, String> map = WxPayUtils.doXMLParse(result);
        String returnCode = map.get("return_code");
        String resultCode = map.get("result_code");
        if (returnCode.equalsIgnoreCase("SUCCESS") && resultCode.equalsIgnoreCase("SUCCESS")) {
            String prepayId = map.get(key);
            if (map.containsKey("code_url")) {
                prepayId = map.get("code_url");
            }
            return prepayId;
        } else {
            return null;
        }
    }
}
