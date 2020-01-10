package com.dlc.modules.sys.service.impl;

import com.dlc.common.utils.CommonUtil;
import com.dlc.common.utils.ConfigConstant;
import com.dlc.common.utils.Constant;
import com.dlc.common.utils.R;
import com.dlc.modules.api.dao.GlWalletMapper;
import com.dlc.modules.api.entity.WalletDetail;
import com.dlc.modules.qd.utils.MyConfig;
import com.dlc.modules.qd.utils.WxPayUtils;
import com.dlc.modules.sys.dao.AgentDao;
import com.dlc.modules.sys.dao.GlWalletDao;
import com.dlc.modules.sys.dao.GlWalletDetailDao;
import com.dlc.modules.sys.dao.WalletDetailDao;
import com.dlc.modules.sys.entity.GlWalletDetailEntity;
import com.dlc.modules.sys.entity.GlWalletEntity;
import com.dlc.modules.sys.entity.WalletDetailEntity;
import com.dlc.modules.sys.service.WalletDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service("walletDetailService")
public class WalletDetailServiceImpl implements WalletDetailService {
    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private WalletDetailDao walletDetailDao;

    @Autowired
    private GlWalletDao glWalletDao;

    @Autowired
    private GlWalletMapper glWalletMapper;

    @Autowired
    private GlWalletDetailDao glWalletDetailDao;

    @Autowired
    private AgentDao agentDao;

    @Override
    public WalletDetailEntity queryObject(Long id) {
        return walletDetailDao.queryObject(id);
    }

    @Override
    public List<WalletDetailEntity> queryList(Map<String, Object> map) {
        return walletDetailDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return walletDetailDao.queryTotal(map);
    }

    @Override
    public void save(WalletDetailEntity walletDetail) {
        walletDetailDao.save(walletDetail);
    }

    @Override
    public void update(WalletDetailEntity walletDetail) {
        walletDetailDao.update(walletDetail);
    }

    @Override
    public void delete(Long id) {
        walletDetailDao.delete(id);
    }

    @Override
    public void deleteBatch(Long[] ids) {
        walletDetailDao.deleteBatch(ids);
    }

    @Override
    public R payToUser(String openId, Integer money, String orderNo, Long id,Long userId) throws Exception {
        String result;
        try {
            MyConfig config = new MyConfig();
            String payToUserURL = ConfigConstant.PAY_TO_USER;
            TreeMap<String, String> parms = new TreeMap<>();
            parms.put("mch_appid", config.getAppID());//企业公众号appid
            parms.put("mchid", config.getMchID());//微信支付分配的商户号
            parms.put("nonce_str", WxPayUtils.getRandomStringByLength(19));//随机字符串，不长于32位
            parms.put("partner_trade_no", orderNo);//商户订单号，需保持唯一性
            parms.put("amount", money + "");//企业付款金额，单位为分
            parms.put("desc", "代理商提现");//企业付款描述信息
            parms.put("spbill_create_ip", WxPayUtils.getLocalhostIp());//调用接口的机器Ip地址
            parms.put("openid", openId);//用户openid
            parms.put("check_name", "NO_CHECK");//NO_CHECK：不校验真实姓名 FORCE_CHECK：强校验真实姓名,OPTION_CHECK：针对已实名认证的用户才校验真实姓名
            //	parms.put("re_user_name", "mch_appid");//如果check_name设置为FORCE_CHECK或OPTION_CHECK，则必填用户真实姓名
            String sign = WxPayUtils.createSign("UTF-8", parms);
            parms.put("sign", sign);//签名//WxPayUtils.getRequestXml(parms)
            //带证书的双向请求
            result = CommonUtil.requestWithCert(payToUserURL, parms, 5000, 5000);
            log.info("++++++++++++++++++" + result + "++++++++++++++++++");
        }catch (Exception e){
            return R.error("企业支付配置有误,请联系管理员");
        }


        //交易结果处理
        Map map = WxPayUtils.doXMLParse(result);
        String return_code = map.get("return_code").toString();
        String result_code = map.get("result_code").toString();

        log.info("result_code&return_code=="+ result_code+"++++"+return_code+"+++++++");
        WalletDetailEntity walletDetailEntity;
        if (return_code.equalsIgnoreCase("SUCCESS") && result_code.equalsIgnoreCase("SUCCESS")) {

            log.info("+++++++++提现成功+++++++++++++");

            try {
                //交易成功后更新提现记录信息
                // 微信订单号（流水号）
                String payment_no = map.get("payment_no").toString();
                //商户订单号(订单号)
                String partner_trade_no = map.get("partner_trade_no").toString();
                walletDetailEntity = new WalletDetailEntity();
                walletDetailEntity.setId(id);
                walletDetailEntity.setTransactionNumber(payment_no);
                walletDetailEntity.setStatus(Constant.WalletDetailStatus.CHECK_PAY_SUCCESS.getValue());
                walletDetailEntity.setTransactionTime(new Date());
                int res = walletDetailDao.update(walletDetailEntity);
                if (res > 0) {
                    log.info("+++++++++++++++更新提现记录成功++++++++++++++");
                } else {
                    log.error("--------------更新提现记录失败---------------");
                }
            } catch (Exception e) {
                log.error("--------------更新提现记录失败---------------");
                e.printStackTrace();
            }
            try {
                //新增个联平台钱包记录
                GlWalletDetailEntity glWallet = new GlWalletDetailEntity();
                glWallet.setCreateTime(new Date());
                glWallet.setMoney(money);
                //4 :提现
                glWallet.setType(4);
                int res = glWalletDetailDao.save(glWallet);
                if (res > 0) {
                    log.info("+++++++++++++++新增个联平台钱包记录成功++++++++++++++");
                } else {
                    log.error("------------新增个联平台钱包记录失败---------");
                }
            } catch (Exception e) {
                log.error("------------新增个联平台钱包记录失败---------");
                e.printStackTrace();
            }
            return R.reOk();
        } else {
            //转账失败
            String err_code_des = map.get("err_code_des").toString();
            walletDetailEntity = new WalletDetailEntity();
            walletDetailEntity.setStatus(Constant.WalletDetailStatus.CHECK_PAY_FAILED.getValue());
            walletDetailEntity.setId(id);
            walletDetailEntity.setReason(err_code_des);
            int update = walletDetailDao.update(walletDetailEntity);
            if (update > 0) {
                log.info("++++++++转账失败更新状态成功+++++++++");
            }

            //转账失败,恢复代理商钱包余额
            Map<String,Object> walletCount =  agentDao.queryAgentWalletByUserId(userId);
            //原始金额
            int initWallet = (int) walletCount.get("wallet");
            int wallet = initWallet + money;
            Map<String,Object> param = new HashMap<>();
            param.put("wallet",wallet);
            param.put("userId",userId);
            int res = agentDao.updateAgentWalletByUserId(param);
            if (res > 0) {
                log.info("+++++转账失败,余额恢复成功!++++++");
            }else {
                log.info("-------转账失败,余额恢复失败------!");
            }

            //恢复个联平台的钱余额
            //查出个联平台的余额
            Integer balance = glWalletDao.moneyTotal();
            Integer nowBalance = balance + money;
            int balanceRes = glWalletDao.updateBalance(nowBalance);
            if (balanceRes > 0) {
                log.info("+++++++++++提现失败恢复个联平台钱包成功+++++++++++");
            } else {
                log.info("-----------提现失败恢复个联平台钱包失败-----------");
            }
            return R.error(err_code_des);
        }
    }

    @Override
    public int updateWalletDetailStatus(Integer status,Long id,String reason) {
        WalletDetailEntity walletDetailEntity = new WalletDetailEntity();
        walletDetailEntity.setId(id);
        walletDetailEntity.setStatus(status);
        walletDetailEntity.setReason(reason);
        walletDetailEntity.setCheckedTime(new Date());

        return walletDetailDao.update(walletDetailEntity);
    }

}
