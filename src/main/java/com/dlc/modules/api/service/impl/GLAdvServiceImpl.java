package com.dlc.modules.api.service.impl;

import com.dlc.modules.api.dao.GlAdvMapper;
import com.dlc.modules.api.entity.GlAdv;
import com.dlc.modules.api.service.GLAdvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chenyuexin
 * @version 1.0
 * @date 2018-07-24 16:41
 */
@Service
public class GLAdvServiceImpl implements GLAdvService {
    @Autowired
    private GlAdvMapper glAdvMapper;

//    @Override
//    public List<Map<String,Object>> queryInfo() {
//        return glAdvDao.queryInfo();
//    }
    /**
     *  @Auther:YD
     *  @parameters:
     *  首页广告
     */
    @Override
    public List<Map<String, Object>> findGlAdv() {
        return glAdvMapper.selectAllGlAdv();
    }
}
