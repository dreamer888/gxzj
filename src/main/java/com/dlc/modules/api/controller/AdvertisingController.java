package com.dlc.modules.api.controller;
import com.dlc.common.utils.PageUtils;
import com.dlc.common.utils.Query;
import com.dlc.common.utils.R;
import com.dlc.modules.api.entity.Advertising;
import com.dlc.modules.api.service.AdvertisingService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @Auther:YD
 * @Date: Creat in 10:02 2018/7/17/017
 */
@RestController
@RequestMapping("/api/advertising")
public class AdvertisingController extends BaseController{
    @Autowired
    private AdvertisingService advertisingService;

    //广告列表
    @RequestMapping("/advertisingList")

    public R advertisingList(@RequestParam Map<String,Object> params,HttpServletRequest request){
        Long id = getAgentId(request);
        params.put("userId",id);
        Query query = new Query(params);
        List<Advertising> list = advertisingService.queryAdvertisingList(query);

        int total = advertisingService.queryTotal(query);

        PageUtils page = new PageUtils(list,total,query.getLimit(),query.getPage());

        return R.reOk().put("page",page);
}

    //广告详情
    @RequestMapping("/adversitingInfo")
    public R AdvertisingInfo(Long id){
        return R.reOk(advertisingService.AdvertisingInfo(id));
    }

    //添加广告
    @RequestMapping("/addAdversiting")
    public R addAdversiting(@RequestParam Map<String,Object> pamars, HttpServletRequest request){
        return advertisingService.addAdversiting(pamars);
    }

}
