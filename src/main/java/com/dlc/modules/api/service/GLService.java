/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: GLService
 * Author:   Administrator
 * Date:     2018/7/13 13:52
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.dlc.modules.api.service;

import net.sf.json.JSONObject;

/**
 * @author chenyuexin
 * @date 2018-07-13 13:52
 * @version 1.0
 */
public interface GLService {
    /**
     * 出纸巾
     * @param imei
     * @param serial
     * @param num
     * @return
     */
    JSONObject getTissue(String imei, String serial, int num);

    /**
     * 开锁纸巾机
     * @param imei
     * @param type
     * @return
     */
    JSONObject unlocking(String imei, int type);

    /**
     * 获取设备状态
     * @param imei
     * @return
     */
    JSONObject getDevSta(String imei);

    /**
     * 解析数据，有透传内容，httpResponseData中bodyJson有值，无透传内容，仅headJson有值
     * Handler业务处理逻辑需添加代码
     * @param reqData
     * @param ip
     */
    String handData(byte[] reqData, String ip);
}
