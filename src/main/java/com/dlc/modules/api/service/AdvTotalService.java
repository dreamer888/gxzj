package com.dlc.modules.api.service;


import com.dlc.common.utils.R;
import com.dlc.modules.api.entity.AdvTotal;
import com.dlc.modules.api.entity.Advertising;

import java.util.List;
import java.util.Map;

/**
 * @Auther:YD
 * @Date: Creat in 2018/7/18/018
 */
public interface AdvTotalService {

    List<Advertising> queryAdvOnLineList(Map<String, Object> params);
    R addAdvShowNum(Long advId);

    R addClickNum(Long advId);

    int queryTotal(Map<String, Object> params);

    int addCpcAdvShowNum(Long advId);

    int addCpmAdvClickNum(Long advId);
}
