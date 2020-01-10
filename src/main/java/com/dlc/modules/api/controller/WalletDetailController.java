package com.dlc.modules.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.dlc.common.exception.RRException;
import com.dlc.common.utils.PageUtils;
import com.dlc.common.utils.Query;
import com.dlc.common.utils.R;
import com.dlc.modules.api.entity.Agent;
import com.dlc.modules.api.entity.WalletDetail;
import com.dlc.modules.api.service.AgentService;
import com.dlc.modules.api.service.PayService;
import com.dlc.modules.api.service.WalletDetailService;
import com.dlc.modules.api.vo.AgentVo;
import com.dlc.modules.api.vo.UserInfoVo;
import com.dlc.modules.qd.utils.WxPayUtils;
import com.dlc.modules.sys.service.GlWalletDetailService;
import com.dlc.modules.sys.service.GlWalletService;
import net.sf.json.JSONArray;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

/**
 * @Auther:YD
 * @Date: Creat in  2018/7/19/019
 *  钱包
 */
@RestController
@RequestMapping("/api/walletDetail")
public class WalletDetailController extends BaseController{
    private Logger log =  LoggerFactory.getLogger(getClass());
    @Autowired
    private WalletDetailService walletDetailService;
    @Autowired
    private AgentService agentService;
    @Autowired
    private GlWalletService glWalletService;
    @Autowired
    private GlWalletDetailService glWalletDetailService;

    //余额
    @RequestMapping("/findWallet")
    public R findWallet(Long id){

        BigDecimal wallet = agentService.findWallet(id);
        JSONObject jsonArray = new JSONObject();
        jsonArray.put("wallet",wallet);
        return  R.reOk(jsonArray);
    }
    //钱包明细
    @RequestMapping("/queryWdInfo")
    public R queryWdInfo(Long id){
        return R.reOk(walletDetailService.queryWdInfo(id));
    }

    //钱包明细列表
    @RequestMapping("/wdList")
    public R wdList(@RequestParam Map<String,Object> params){
        Query query = new Query(params);
        List<Map<String,Object>> list=walletDetailService.wdList(query);
        int total = walletDetailService.queryTotal(query);
        PageUtils pageUtil = new PageUtils(list,total,query.getLimit(),query.getPage());
        return R.reOk(pageUtil);
    }

    //创建充值订单
    @RequestMapping("/creatRecharge")
    public R creatRcharge(Long userId){
        String orderNo = walletDetailService.creatRcharge(userId);
        return R.reOk(orderNo);
    }

    //充值
    @RequestMapping("/rechargePay")
    public R rechargePay(String openId, BigDecimal money, String orderNo){
        if (StringUtils.isBlank(orderNo) || StringUtils.isBlank(openId)){
            return R.error("订单号或设备号不能为空！");
        }
        if (StringUtils.isBlank(money+"")){
            return  R.error("金额不能为空！");
        }
        try {
            SortedMap<String,String> map = walletDetailService.rechargePay(openId,money,orderNo);
            return R.reOk(map);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RRException("微信支付失败",e);
        }
    }
    //充值回调
    @RequestMapping(value = "/reNotify",method = RequestMethod.POST)
    public void getBackRechargePay(HttpServletRequest request,HttpServletResponse response){
        log.info("回调充值返回结果...");
       /* InputStream inputStream;
        try {
            inputStream = request.getInputStream();
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = inputStream.read(buffer);
            while (len != -1) {
                outStream.write(buffer, 0, len);
            }
            outStream.close();
            inputStream.close();
*/
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

            Map<String, String> resultMap = WxPayUtils.doXMLParse(result);

            log.info("进入微信回调" + resultMap);

            if (resultMap.get("return_code").equals("SUCCESS") && resultMap.get("result_code").equals("SUCCESS")) {
                log.info("回调的return_code:" + resultMap.get("return_code"));
                log.info("回调的result_code:" + resultMap.get("result_code"));
                log.info("回调的trade_state：" + resultMap.get("trade_state"));

                //充值金额
                int wallet = Integer.parseInt(resultMap.get("total_fee"));

                log.info("-------回调的充值金额------:" + wallet);

                //流水号/订单号？
                String transaction_id = resultMap.get("transaction_id");
                log.info("-------回调的充值单号------:" + transaction_id);
                String orderNo = resultMap.get("out_trade_no");
                log.info("-------回调的商户单号（列表明细Id）------:"+ orderNo);
                //openId
                String openId = resultMap.get("openid");
                //个联钱包余额添加 新的余额 = 余额 + 充值的金额
                glWalletService.updateGlWallet(wallet);
                //个联钱包充值明细
                glWalletDetailService.insertGwInfo(wallet);
                //储存充值记录
                walletDetailService.updateWalletDetail(orderNo, wallet, transaction_id);
                //更新账户金额
                agentService.updateWallet(orderNo, wallet);

                log.info(">>微信回调成功");
                response.getWriter().print("SUCCESS");
                return;
            }
        }catch (Exception e) {
            e.printStackTrace();
            log.info(">>回调通知异常");
        }
    }

    //提现
    @RequestMapping("/withdrawDeposit")
    public R withdrawDeposit(@RequestParam Map<String,Object> params){
        Agent agent = agentService.selectInfoById(Long.valueOf((String) params.get("agentId")));

        if (agent.getType() ==4){
            agent = agentService.selectInfoById(agent.getParentId());
        }
        int money = Integer.parseInt((String) params.get("money"));
        int wallet = agent.getWallet();
        if (wallet < money){
            return R.reError("您好，你提现的金额不能大于您的余额！");
        }else {
            walletDetailService.withdrawDeposit(params);
            return R.reOk();
        }
    }
}