package com.dlc.modules.api.service;

import com.dlc.modules.api.entity.FreeMapperRecord; /**
 * @author chenyuexin
 * @version 1.0
 * @date 2018-08-04 19:17
 */
public interface FreeMapperRecordService {
    int addRecord(FreeMapperRecord freeMapperRecord);

    FreeMapperRecord queryRecordByAppId(String appid, String openid);
}
