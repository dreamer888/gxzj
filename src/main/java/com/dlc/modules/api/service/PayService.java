package com.dlc.modules.api.service;

import com.dlc.modules.api.entity.UserConsume;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.SortedMap;

public interface PayService {

    //R zfbPay(Integer payType, String OutOrderNo, BigDecimal money, HttpServletResponse httpResponse) throws IOException;

    /**
     * 退款申请
     */

    //String wxRefund(UserConsume userConsume) throws Exception;

    //String zfbRefund(UserConsume userConsume) throws AlipayApiException;

    SortedMap<String, String> wxPay(String outOrderNo, BigDecimal money, String openId,String notify_url) throws Exception;

    String wxRefund(UserConsume userConsume) throws Exception;
}
