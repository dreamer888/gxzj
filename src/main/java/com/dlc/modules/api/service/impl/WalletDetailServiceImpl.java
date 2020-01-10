package com.dlc.modules.api.service.impl;
import com.dlc.common.utils.CommonUtil;
import com.dlc.common.utils.OrderNoGenerator;
import com.dlc.common.utils.R;
import com.dlc.modules.api.dao.AgentMapper;
import com.dlc.modules.api.dao.GlWalletMapper;
import com.dlc.modules.api.dao.WalletDetailMapper;
import com.dlc.modules.api.entity.*;
import com.dlc.modules.api.service.GoodsListService;
import com.dlc.modules.api.service.WalletDetailService;
import com.dlc.modules.qd.utils.MyConfig;
import com.dlc.modules.qd.utils.WxPayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.dlc.modules.api.service.impl.PayServiceImpl.getXMLPayKey;

/**
 * @Auther:YD
 * @Date: Creat in  2018/7/19/019
 */
@Service
@Transactional
public class WalletDetailServiceImpl implements WalletDetailService{

    private Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private WalletDetailMapper walletDetailMapper;
    @Autowired
    private AgentMapper agentMapper;
    @Autowired
    private GoodsListService goodsListService;
    @Autowired
    private GlWalletMapper glWalletMapper;
    @Value("${project.url}")
    private String projectUrl;
    //钱包明细列表
    @Override
    public List<Map<String, Object>> wdList(Map<String, Object> params) {
        Long useId = Long.valueOf(String.valueOf(params.get("userId")));
        Agent ag = agentMapper.selectByPrimaryKey(useId);
        int agType = ag.getType();
        //如果登入的用户是广告主
        if (agType == 0 && (params.get("type") == null || params.get("type") =="")){
           List<Map<String,Object>> list = walletDetailMapper.selectAdWdListByType(params);
            for (Map<String,Object> ad:list) {
                BigDecimal money = BigDecimal.valueOf((Integer) ad.get("money")).divide(new BigDecimal(100));
                ad.put("money",money);
            }
            return list;
        }else if (agType == 1 && (params.get("type") == null || params.get("type") =="")){
            //如果登入的用户是供货主
            List<Map<String,Object>> list = walletDetailMapper.selectDlWdListByType(params);
            for (Map<String,Object> ad:list) {
                BigDecimal money = BigDecimal.valueOf((Integer) ad.get("money")).divide(new BigDecimal(100));
                ad.put("money",money);
            }
            return list;
        }else if (agType == 2 && (params.get("type") == null || params.get("type") =="")){
            //如果登入的用户是代理商
            List<Map<String,Object>> list = walletDetailMapper.selectDlWdListByType(params);
            for (Map<String,Object> ad:list) {
                BigDecimal money = BigDecimal.valueOf((Integer) ad.get("money")).divide(new BigDecimal(100));
                ad.put("money",money);
            }
            return list;
        }else if (agType == 4 && (params.get("type") == null || params.get("type") =="")) {
            //如果登入用户为财务人员
            params.put("userId", ag.getParentId());
            List<Map<String, Object>> list = walletDetailMapper.selectDlWdListByType(params);
            for (Map<String, Object> ad : list) {
                BigDecimal money = BigDecimal.valueOf((Integer) ad.get("money")).divide(new BigDecimal(100));
                ad.put("money", money);
            }
            return list;
        }else if (agType == 4 && params.get("type") != null){
            params.put("userId", ag.getParentId());
            List<Map<String, Object>> list = walletDetailMapper.wdList(params);
            for (Map<String, Object> ad : list) {
                BigDecimal money = BigDecimal.valueOf((Integer) ad.get("money")).divide(new BigDecimal(100));
                ad.put("money", money);
            }
            return list;
        }else {
            List<Map<String,Object>> list = walletDetailMapper.wdList(params);
            for (Map<String,Object> ad:list) {
                BigDecimal money = BigDecimal.valueOf((Integer) ad.get("money")).divide(new BigDecimal(100));
                ad.put("money",money);
            }
            return list;
        }
    }
    //钱包明细列表条数统计
    @Override
    public int queryTotal(Map<String, Object> params) {
        Long useId = Long.valueOf(String.valueOf(params.get("userId")));
        Agent ag = agentMapper.selectByPrimaryKey(useId);
        int agType = ag.getType();
        //如果登入的用户是广告主
        if (agType == 0 && (params.get("type") == null || params.get("type") =="")){
           int total = walletDetailMapper.queryAdvTotal(params);
            return total;
        }else if (agType == 1 && (params.get("type") == null || params.get("type") =="")){
            //如果登入的用户是供货主
            int total = walletDetailMapper.queryDlTotal(params);
            return total;
        }else if (agType == 2 && (params.get("type") == null || params.get("type") =="")){
            //如果登入的用户是代理商
            int total = walletDetailMapper.queryDlTotal(params);
            return total;
        }else if (agType == 4 && (params.get("type") == null || params.get("type") =="")) {
            params.put("userId", ag.getParentId());
            int total = walletDetailMapper.queryDlTotal(params);
            return total;
        }else if (agType == 4 && params.get("type") != null){
            params.put("userId", ag.getParentId());
            int total = walletDetailMapper.queryTotal(params);
            return total;
        }else{
            int total = walletDetailMapper.queryTotal(params);
            return total;
        }
    }

    //钱包明细详情
    @Override
    public Map<String,Object> queryWdInfo(Long id) {
        Map<String, Object> map = walletDetailMapper.queryWdInfo(id);
        BigDecimal money = BigDecimal.valueOf((Integer) map.get("money")).divide(new BigDecimal(100));
        map.put("money",money);
        return map;
    }

    //  创建充值
    @Override
    public String creatRcharge(Long userId) {
        String orderNo = OrderNoGenerator.getOrderIdByTime();
        String openId = agentMapper.getOpenId(userId);
        WalletDetail walletDetail = new WalletDetail();
        walletDetail.setUserId(userId);
        walletDetail.setMoney(0);
        walletDetail.setType(3);
        walletDetail.setOrderNo(orderNo);
        walletDetail.setOpenId(openId);
        walletDetail.setStatus(6);
        walletDetail.setCreateTime(new Date());
        walletDetailMapper.insertSelective(walletDetail);
        return orderNo;
    }

    //充值
    @Override
    public SortedMap<String, String> rechargePay(String openId, BigDecimal money, String orderNo) throws Exception {
        //统一下单获取预支付id
        String prepayId = getPrepayId(openId,money,orderNo);
        MyConfig mc = new MyConfig();
        SortedMap<String,String> packageParams = new TreeMap<>();
        //ReAppId
        packageParams.put("appId",mc.getAppID());
        packageParams.put("timeStamp",String.valueOf(Calendar.getInstance().getTimeInMillis()/1000));
        packageParams.put("nonceStr", WxPayUtils.getRandomStringByLength(19));
        packageParams.put("package","prepay_id="+prepayId);
        packageParams.put("signType","MD5");
        packageParams.put("paySign",WxPayUtils.createSign("UTF-8",packageParams));
        return packageParams;
    }

    public String getPrepayId(String openid, BigDecimal money,String outTradeNo) throws Exception {

//         money = money.multiply(new BigDecimal(100)); // 转化为分
        //money = BigDecimal.valueOf(1);
        MyConfig config = new MyConfig();
        SortedMap<String, String> packageParams = new TreeMap<>();
        packageParams.put("appid", config.getAppID());
        packageParams.put("mch_id", config.getMchID());
        packageParams.put("device_info", "WEB");
        packageParams.put("body", "深圳市个联科技有限公司");
        packageParams.put("out_trade_no", outTradeNo);
        packageParams.put("total_fee", money + "");
        packageParams.put("fee_type", "CNY");
        packageParams.put("spbill_create_ip", WxPayUtils.getLocalhostIp());
        packageParams.put("notify_url", projectUrl+"/api/walletDetail/reNotify");
        packageParams.put("trade_type", "JSAPI");
        packageParams.put("nonce_str", WxPayUtils.getRandomStringByLength(19));
        packageParams.put("sign_type","MD5");
        packageParams.put("openid", openid);
        String sign = WxPayUtils.createSign("UTF-8", packageParams);
        packageParams.put("sign", sign);
        String result = CommonUtil.httpsRequest(MyConfig.UNIFIED_ORDER_URL, "POST", WxPayUtils.getRequestXml(packageParams));
        return getXMLPayKey(result, "prepay_id");
    }

    //保存充值记录
    @Override
    public int updateWalletDetail(String orderNo, int wallet, String transaction_id) {
        WalletDetail wd = walletDetailMapper.findWdByOrderNo(orderNo);
        if(null != wd){
            wd.setUserId(wd.getUserId());
            wd.setType(3);
            wd.setMoney(wallet);
            wd.setTransactionNumber(transaction_id);
            wd.setStatus(3);
            wd.setCreateTime(new Date());
            return walletDetailMapper.updateByPrimaryKeySelective(wd);
        }
        return 0;
    }
    /**
     *  @Auther:YD
     *  @parameters: agentId,money
     *  提现
     */
    @Override
    public int withdrawDeposit(Map<String,Object> params) {
        Agent ag = agentMapper.selectByPrimaryKey(Long.valueOf((String) params.get("agentId")) );
        //个联账户
        GlWallet glWallet = glWalletMapper.selectByPrimaryKey(1l);
        WalletDetail walletDetail = new WalletDetail();
        if (ag.getType() == 1 || ag.getType() == 2){
            //参数要代理商id 提现的金额
            walletDetail.setUserId(ag.getId());
            walletDetail.setType(1);
            walletDetail.setStatus(1);
            walletDetail.setMoney(Integer.parseInt((String) params.get("money")));
            walletDetail.setOrderNo(OrderNoGenerator.getOrderIdByTime());
            walletDetail.setOpenId(ag.getOpenId());
            walletDetail.setCreateTime(new Date());
            //扣除账户余额
            ag.setWallet(ag.getWallet() - Integer.parseInt((String) params.get("money")));
            agentMapper.updateByPrimaryKeySelective(ag);
            //扣除个联余额
            glWallet.setBalance(glWallet.getBalance() - Integer.parseInt((String) params.get("money")));
            glWalletMapper.updateByPrimaryKeySelective(glWallet);
            return walletDetailMapper.insertSelective(walletDetail);

        }else {
            Agent agent = agentMapper.selectByPrimaryKey(ag.getParentId());
            walletDetail.setUserId(agent.getId());
            walletDetail.setType(1);
            walletDetail.setStatus(1);
            walletDetail.setMoney(Integer.parseInt((String) params.get("money")));
            walletDetail.setOrderNo(OrderNoGenerator.getOrderIdByTime());
            walletDetail.setOpenId(agent.getOpenId());
            walletDetail.setCreateTime(new Date());
            //扣除账户余额
            agent.setWallet(agent.getWallet() - Integer.parseInt((String) params.get("money")));
            agentMapper.updateByPrimaryKeySelective(agent);
            //扣除个联余额
            glWallet.setBalance(glWallet.getBalance() - Integer.parseInt((String) params.get("money")));
            glWalletMapper.updateByPrimaryKeySelective(glWallet);
            return walletDetailMapper.insertSelective(walletDetail);
        }
    }

    /**
     * @author: 廖修坤
     * @date 2018/7/24

     * @return
     * @throws
     * @since
     * @description: 增加供货端余额，减少管理端余额
    */
    public Map<String,Object> balancePayment(MyOrder myOrder,String notify_url) {
        //判断余额是否充足
        String openId = myOrder.getOpenId();
        int producerCount=0;
        int customerCount =0;
        String prepayId="";
        SortedMap<String, String> packageParams = null;
        Map<String,Object> map = new HashMap<>();
        //String prepayId ="";
        PayServiceImpl payService = new PayServiceImpl();
        Long customer = myOrder.getCustomer();
        if(customer==null){
            return R.reError("customer不能为空");
        }
        //Integer money =  walletDetailMapper.selectMoney(customer);//查询用户余额
        Integer realPayment = myOrder.getRealPayment();
        BigDecimal bigDecimal = new BigDecimal(realPayment);
        //随机产生订单号
        String orderNo2 = OrderNoGenerator.getOrderIdByTime();
        myOrder.setOrderNo(orderNo2);
        String orderNo = myOrder.getOrderNo();

        List<OrderDetail> orderDetailList= myOrder.getOrderDetailList();
        int  details=0;
        for (OrderDetail temp:orderDetailList) {
            temp.setOrderNo(orderNo);
            details= goodsListService.addOrderDetails(temp);//订单详情
        }
        int myorder =goodsListService.AddMyOrder(myOrder);//订单管理添加()
        if(myorder>0 && details>0){
            //删除购物车
            for (OrderDetail temp:orderDetailList) {
                Long goodsId = temp.getGoodsId();
                goodsListService.delShoppingCarById(goodsId,customer);
            }
        }

        //支付（微信）
        if(myOrder.getOrderNo()!=null){
            log.info("orderNo>>>"+orderNo+">>>notify_url>>>"+notify_url);
            try {
                packageParams =  payService.wxPay(orderNo,bigDecimal,openId,notify_url);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /*if(money<realPayment){
            //余额不足，微信支付

            try {

            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            //余额充足，余额支付
            producerCount = walletDetailMapper.addBalance(myOrder);//生产者增加余额

            customerCount = walletDetailMapper.cutDownBalance(myOrder);//管理者减少余额
        }*/

        if(producerCount>0 && customerCount>0){
            //余额支付成功,更改status
            String transactionNumber = null;
            goodsListService.upOrdreStatus(orderNo,transactionNumber);

        }
        //微信支付
        map.put("packageParams",packageParams);
        return map;
    }

    /**
     * 供货端增加余额
     * */
    public int addMoneyById(Map<String,Object> maps) {
        return walletDetailMapper.addBalance(maps);
    }

    /**
     * 个联钱包明细增加一条记录
     * */
    public int addMsgToglWallet(BigDecimal money) {
        return walletDetailMapper.addMsgToglWallet(money);
    }

    /**
     * 供货端钱包明细增加条记录
     * */
    public int addWalletDetail(Map<String,Object> temp) {
        return walletDetailMapper.addWalletDetail(temp);
    }


    /**
     * 个联钱包修改金额
     * */
    public int editMsgToglWallet(BigDecimal money) {
        return walletDetailMapper.editMsgToglWallet(money);
    }

    /**
     * 供货端增加余额（微信支付）
     * */
    public int addMoneyById2(Map<String, Object> map) {
        return walletDetailMapper.addMoneyById2(map);
    }

    @Override
    public List<OrderDetail> queryOrderDetailByOderNo(String out_trade_no) {
        return walletDetailMapper.queryOrderDetailByOderNo(out_trade_no);
    }

    /**
     * 月销量增加
     * */
    public int addMonthlySales(Map<String, Object> goodsMap) {
        return walletDetailMapper.addMonthlySales(goodsMap);
    }
}
