package com.dlc.modules.api.controller;

import com.dlc.common.utils.PageUtils;
import com.dlc.common.utils.Query;
import com.dlc.common.utils.R;
import com.dlc.modules.api.entity.AdvTotal;
import com.dlc.modules.api.entity.Advertising;
import com.dlc.modules.api.service.AdvTotalService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Auther:YD
 * @Date: Creat in  2018/7/18/018
 */
@RestController
@RequestMapping("/api/advtotal")
public class AdvTotalController {
    @Autowired
    private AdvTotalService advTotalService;

    //广告点击列表
    @RequestMapping("/advOnLineList")
    public R advOnLineList(@RequestParam Map<String,Object> params){
        params.put("status",3);
        Query query = new Query(params);
        List<Advertising> list = advTotalService.queryAdvOnLineList(query);
        int total = advTotalService.queryTotal(query);
        PageUtils page = new PageUtils(list,total,query.getLimit(),query.getPage());
        if (list.size() != 0){
            return R.reOk().put("page",page);
        }else {
            return R.error("暂时还没投放中的广告！");
        }
    }
    //
}
