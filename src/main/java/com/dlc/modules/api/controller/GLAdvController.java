package com.dlc.modules.api.controller;

import com.dlc.common.utils.R;
import com.dlc.modules.api.service.GLAdvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 个联广告展示Controller
 * @author chenyuexin
 * @version 1.0
 * @date 2018-07-24 16:38
 */
@RestController("apiGLAdv")
@RequestMapping("/api/h5")
public class GLAdvController {
    @Autowired
    private GLAdvService glAdvService;

//    @RequestMapping("/showAdv")
//    public R showAdv(){
//        List<Map<String, Object>> map = glAdvService.queryInfo();
//        if (CollectionUtils.isEmpty(map)){
//            return R.reError("获取广告失败!");
//        }
//        return R.reOk(map.get(0));
//    }


    @RequestMapping("/showAdv")
    public R findGlAdv(){
        List<Map<String,Object>> list = glAdvService.findGlAdv();
        if (list.size() == 0){
            return R.reError("很抱歉，暂时没有广告投放！");
        }else {
            Map<String,Object> map = list.get(0);
            return R.reOk(map);
        }
    }
}