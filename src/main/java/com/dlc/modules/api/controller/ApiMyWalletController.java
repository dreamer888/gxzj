package com.dlc.modules.api.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dlc.common.utils.ConfigConstant;
import com.dlc.common.utils.R;
import com.dlc.modules.api.entity.MyOrder;
import com.dlc.modules.api.entity.OrderDetail;
import com.dlc.modules.api.service.*;
import com.dlc.modules.qd.utils.WxPayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 廖修坤
 * @date 2018/7/23 20:15
 * 钱包
 */
@RestController
@RequestMapping("/api/myWallet")//myWallet
public class ApiMyWalletController extends BaseController{
    private Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    WalletDetailService walletDetailService;
    @Autowired
    MyOrderService myOrderService;
    @Autowired
    private GoodsListService goodsListService;

    @Value("${project.url}")
    private String projectUrl;

    /**
     *当管理端支付完成后供货端增加余额,管理端减少相应余额
     * */
    @RequestMapping("/balancePayment")
    public R addBalance(String jsonArray, HttpServletRequest request){
        JSONObject obj = JSONObject.parseObject(jsonArray);
        MyOrder myOrder = JSONObject.toJavaObject(obj,MyOrder.class);//json转对象
        String notify_url = projectUrl + "/api/myWallet/goodsCallBk";
        log.info("notify_url------------->"+notify_url);
        Long customer= getAgentVo(request).getId();
        myOrder.setCustomer(customer);
        Map<String,Object> map =walletDetailService.balancePayment(myOrder,notify_url);//增加供货端余额，减少管理端余额
        if(map.size()<=0){
           return R.reError("操作失败");
        }else{
           return R.reOk(map);
        }
    }


    /**
     *供货端收取支付款后回调方法
     * */
    @RequestMapping(value = "/goodsCallBk", method = RequestMethod.POST)
    public void goodsCallBk(HttpServletRequest request, HttpServletResponse response) {
        log.info(">>进入微信回调AKON1");
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
            log.info(">>进入微信回调AKON2" + map);
            if (map.get("return_code").equals("SUCCESS")) {

                //订单号
                String out_trade_no = map.get("out_trade_no");//订单号
                String total_fee = map.get("total_fee");//付款额
                String tradeNo = map.get("transaction_id");//流水号
                //BigDecimal money = BigDecimal.valueOf(Long.valueOf(total_fee)).divide(new BigDecimal(100));
                BigDecimal money = BigDecimal.valueOf(Long.valueOf(total_fee));
                //String money = String.valueOf(BigDecimal.valueOf(Long.valueOf(total_fee)).divide(new BigDecimal(100))) ;
                log.info("out_trade_no:AKON3========" + out_trade_no + ",money:--------" + money);

                //通过orderNo将商户端余额增加、个联钱包增加一条记录
                    //根据订单号查询出我的订单
                List<Map<String,Object>> myOrderlist = myOrderService.queryOrderInfoByOrderNo(out_trade_no);
                //userId  type=2 money  transactionNumber  orderNo  status=3 checkedTime

                if(myOrderlist==null){
                    R.reError("未查询到相关信息，请检查orderNo是否正确");
                }
                for(Map<String,Object> maps:myOrderlist){
                    log.info("详情条数>>>"+maps.size()+"<<<<");
                    log.info("用户ID>>>"+maps.get("userId")+"<<<<");
                 /* Map<String,Object> temp = new HashMap<>();
                  Integer realPayment = (Integer) maps.get("realPayment");*/
                 /* temp.put("realPayment",realPayment);
                  temp.put("money",money);
                  //temp.put("producer",producer);
                  temp.put("transactionNumber",tradeNo);
                  temp.put("orderNo",out_trade_no);*/
                  log.info("realPayment>>>"+maps.get("realPayment")+"<<<<");
                        int MoneyCount = walletDetailService.addMoneyById(maps);//供货端增加余额agent
                        log.info("用户ID>>>"+maps.get("userId")+"<<<<");
                            log.info("供货端增加余额成功MoneyCount>>>"+MoneyCount+"<<<<");

                            //个联钱包明细增加条记录（gl_wallet_detail）ok
                            int glWall = walletDetailService.addMsgToglWallet(money);
                            log.info("个联钱包明细增加条记录glWall>>>"+glWall+"<<<<");

                            //更改订单状态，添加支付时间/添加交易号
                            int sta = goodsListService.upOrdreStatus(out_trade_no,tradeNo);
                            log.info(">>添加支付时间/添加交易号成功"+sta+"<<<");

                            //供货端钱包明细增加条记录wallet_detail
                            int detail= walletDetailService.addWalletDetail(maps);
                            log.info("供货端钱包明细增加条记录detail>>>"+detail+"<<<<");

                            //个联钱包修改金额gl_wallet  ok
                            int tog = walletDetailService.editMsgToglWallet(money);
                            log.info("个联钱包修改金额tog>>>"+tog+"<<<<");
                            response.getWriter().print("SUCCESS");
                }
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info(">>回调通知异常");
        }
    }


  /*
  * 本地模拟测试支付成功回调
  * */
  /*@RequestMapping("/test22")
  public R test2(String orderNo,String transactionNumber,Integer mone,String openId){
      //通过orderNo将商户端余额增加（快递费除外）、个联钱包增加一条记录
      //根据订单号查询出订单详情
      MyOrder myOrder = myOrderService.queryOrderInfoByOrderNo(orderNo);
      if(myOrder==null){
          R.reError("未查询到相关信息，请检查orderNo是否正确");
      }
      Long id =  myOrder.getProducer();
      Integer goodsSum1 = myOrder.getGoodsSum();
      BigDecimal goodsSum = BigDecimal.valueOf(Long.valueOf(goodsSum1)).divide(new BigDecimal(100));
      Long producer = myOrder.getProducer();
      BigDecimal money = BigDecimal.valueOf(Long.valueOf(mone)).divide(new BigDecimal(100));

      Map<String,Object> map = new HashMap<>();
      map.put("goodsSum",goodsSum);
      map.put("money",money);
      map.put("producer",producer);
      map.put("transactionNumber",transactionNumber);
      map.put("orderNo",orderNo);
      map.put("openId",openId);

      int x = walletDetailService.addMoneyById2(map);//供货端增加余额
      int y=0;
      int z=0;
      int m = 0;
      if(x>0){
          //个联钱包明细增加条记录
          y = walletDetailService.addMsgToglWallet(money);
          //钱包明细增加条记录
          z = walletDetailService.addWalletDetail(map);
          //个联钱包增加金额
          m = walletDetailService.editMsgToglWallet(money);
          //更改订单状态，添加支付时间
          goodsListService.upOrdreStatus(orderNo,transactionNumber);
      }
      return R.reOk();
  }*/
}
