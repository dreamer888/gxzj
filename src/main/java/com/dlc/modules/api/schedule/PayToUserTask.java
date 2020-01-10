package com.dlc.modules.api.schedule;

import com.dlc.common.utils.Constant;
import com.dlc.common.utils.DateUtils;
import com.dlc.common.utils.R;
import com.dlc.modules.sys.entity.ParamSetEntity;
import com.dlc.modules.sys.entity.WalletDetailEntity;
import com.dlc.modules.sys.service.ParamSetService;
import com.dlc.modules.sys.service.WalletDetailService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * T+？ 定时任务企业支付
 *
 * @author chenyuexin
 * @version 1.0
 * @date 2018-07-29 20:57
 */
public class PayToUserTask {
    private Log log = LogFactory.getLog(PayToUserTask.class);

    @Autowired
    private WalletDetailService walletDetailService;

    @Autowired
    private ParamSetService paramSetService;

    public void payToUser() {
        log.info("++++++++++++进入提现定时任务+++++++++++++++");
        Map<String, Object> paramMap = new HashMap<>();
        //查询出所以提现支付中的状态记录
        paramMap.put("status", Constant.WalletDetailStatus.CHECK_PAYING.getValue());
        List<WalletDetailEntity> walletDetailEntities = walletDetailService.queryList(paramMap);
        paramMap.put("type", 2);
        List<ParamSetEntity> paramSetEntities = paramSetService.queryList(paramMap);
        int value = 0;
        for (ParamSetEntity paramSetEntity : paramSetEntities) {
            value = Integer.parseInt(paramSetEntity.getParamValue());
        }

        for (WalletDetailEntity walletDetailEntity : walletDetailEntities) {
            //获取应到账时间
            Date endTime = DateUtils.addDate(walletDetailEntity.getCheckedTime(), value);
            //对比到账时间是否大于当前时间 大于的话就调支付接口
            if (endTime.before(new Date())) {
                R res = null;
                try {
                    res = walletDetailService.payToUser(walletDetailEntity.getOpenId(), walletDetailEntity.getMoney(), walletDetailEntity.getOrderNo(), walletDetailEntity.getId(),walletDetailEntity.getUserId());
                    log.info("+++++++++++++" + res.get("msg") + "+++++++++++++++++");
                } catch (Exception e) {
                    log.error("-------------------提现异常---------------------" + res.get("msg"));
                    e.printStackTrace();
                }
            }
        }

    }
}
