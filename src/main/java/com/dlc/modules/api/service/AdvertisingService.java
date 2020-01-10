package com.dlc.modules.api.service;

import com.dlc.common.utils.PageUtils;
import com.dlc.common.utils.Query;
import com.dlc.common.utils.R;
import com.dlc.modules.api.entity.Advertising;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Auther:YD
 * @Date: Creat in 10:55 2018/7/17/017
 */

public interface AdvertisingService {
    //查询广告列表
    List<Advertising> queryAdvertisingList(Map<String, Object> params);
    //广告条数
    int queryTotal(Map<String, Object> params);

    Map<String, Object> AdvertisingInfo(Long id);

    //添加广告
    R addAdversiting(Map<String,Object> pamars);
    //轮播图

    List<Map<String,Object>> sowingMapList(Map<String, Object> params);

    Advertising selectById(Long advId);
}
