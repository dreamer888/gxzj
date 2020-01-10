package com.dlc.modules.api.service;

import com.dlc.modules.api.entity.GlAdv;
import com.dlc.modules.sys.entity.GlAdvEntity;

import java.util.List;
import java.util.Map;

/**
 * @author chenyuexin
 * @version 1.0
 * @date 2018-07-24 16:40
 */
public interface GLAdvService {
//    List<Map<String,Object>> queryInfo();

    List<Map<String, Object>> findGlAdv();
}
