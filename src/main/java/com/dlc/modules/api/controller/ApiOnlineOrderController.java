package com.dlc.modules.api.controller;

import com.dlc.common.utils.ConfigConstant;
import com.dlc.common.utils.PageUtils;
import com.dlc.common.utils.R;
import com.dlc.modules.api.service.GoodsListService;
import com.dlc.modules.api.service.OrderService;
import com.dlc.modules.api.vo.UserInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.dlc.common.utils.Query;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 廖修坤
 * @date 2018/7/20 21:21
 */
@RestController
@RequestMapping("/api/onlineOrder")
public class ApiOnlineOrderController extends BaseController{
    @Autowired
    private OrderService orderService;
    @Autowired
    GoodsListService goodsListService;

    /**
     * 管理端/运维查询我的订单
     * */
    @RequestMapping("/OnlineOrderlist")//分页要改的
    public R list(HttpServletRequest request,@RequestParam Map<String, Object> params){
        Long userId = getAgentVo(request).getId();
        params.put("customer",userId);//消费者Id
        if (org.springframework.util.StringUtils.isEmpty(params.get("page")) || org.springframework.util.StringUtils.isEmpty(params.get("limit"))) {
            return R.error("分页信息不能为空");
        }
        Query query =new Query(params);
        List<Map<String, Object>> list = orderService.queryList(query);//数据查询
        int total = orderService.queryListCount(query);//总记录数查询
        PageUtils pageUtil = new PageUtils(list,total,query.getLimit(), query.getPage());
        return R.reOk(pageUtil);

    }


    /**
     * 在线订货：订单详情
     * */
    @RequestMapping("/info")
    public R info(String orderNo){
        if(orderNo==null){
            return R.error("订单ID不能为空");
        }
        List<Map<String,Object>> orderInfo = orderService.queryOrderInfo(orderNo);
        return R.reOk(orderInfo);
    }


    /**
     * 在线订货：更改订单状态
     * */
    @RequestMapping("/updateOrderStatus")
    public R updateOrderStatus(String orderNo,Byte status){
        if(orderNo==null || status==null){
            return R.reError("订单编号和订单状态不能为空");
        }
        Map<String,Object> map = new HashMap<>();
        Date tradeTime=null;
        if(status==3){
            tradeTime = new Date();
        }
        map.put("orderNo",orderNo);
        map.put("status",status);
        map.put("tradeTime",tradeTime);
        orderService.update(map);
        return R.reOk();
    }

}
