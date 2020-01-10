package com.dlc.modules.api.schedule;

import com.dlc.modules.api.service.UserInfoService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 每周清零用户领取次数的定时任务
 * @author chenyuexin
 * @version 1.0
 * @date 2018-07-24 20:03
 */
public class UpdateUserCountTask {
    private Log log  = LogFactory.getLog(UpdateUserCountTask.class);

    @Autowired
    private UserInfoService userInfoService;

    public void updateUserCount(){
        log.info("+++++++++++进入每周用户领取数量清零操作++++++++++");
        userInfoService.updateUserCountToZero();
    }

}
