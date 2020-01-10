package com.dlc.modules.api.controller;
import com.dlc.common.utils.PageUtils;
import com.dlc.common.utils.R;
import com.dlc.modules.api.entity.*;
import com.dlc.modules.api.service.MyOrderService;
import com.dlc.modules.api.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.dlc.common.utils.Query;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author: 廖修坤
 * @date 2018/7/17

 * @description:供货者订单管理
*/
@RestController
@RequestMapping("/api/myOrder")//myOrder
public class ApiMyOrderController extends BaseController{
    @Autowired
    MyOrderService myOrderService;
    @Autowired
    private OrderService orderService;
    /**
     *订单管理:订单列表查询
     * @parame: userId status
     * */
    @RequestMapping("/MyOrderlList")
    public R OrderManag(@RequestParam Map<String, Object> params, HttpServletRequest request){
        Long userId = getAgentVo(request).getId();
        params.put("producer",userId);//生成者Id
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
     *订单详情
     * */
    @RequestMapping("/OrderDetailList")
    public R OrderDetailList(String orderNo){
        if(orderNo==null){
            return R.reError("订单Id不能为空");
        }
        List<Map<String, Object>> orderInfo = myOrderService.queryOrderInfo(orderNo);
        return R.reOk(orderInfo);
    }


    /**
    * 保存物流信息
    * */
    @RequestMapping("/AddLogistics")
    public R AddLogistics(MyOrder myOrder,HttpServletRequest request){
        Long producer = getAgentVo(request).getId();
        myOrder.setProducer(producer);
        if(myOrder==null){
            R.error("填写的数据不能为空");
        }
        int LogistCount = myOrderService.AddLogistics(myOrder);
    if(LogistCount<=0){
        return R.reError("添加失败");
    }
        return R.reOk();
    }


    /**
     * 修改订单状态
     *   根据订单号 orderNo
     * */
    @RequestMapping("/ChangeOrderStatus")
    public R ChangeOrderStatus(String orderNo,Byte status){
        int x = myOrderService.ChangeOrderStatusByOrderNo(orderNo,status);
        return R.reOk();
    }


    /**
     * 修改订单
     *
     * */
    @RequestMapping("/editOrder")
    public R OrderStatusToComplete(MyOrder myOrder){
       int edit =  myOrderService.editOrder(myOrder);
        return R.reOk();
    }


    /**
     * 添加订单(没有使用了)
     * */
    @RequestMapping(value = "/AddOrder",method = RequestMethod.POST)
    public R AddOrder(@RequestBody MyOrder myOrder,HttpServletRequest request)  {
        Long customer= getAgentVo(request).getId();
        myOrder.setCustomer(customer);
        List<OrderDetail> orderDetailList= myOrder.getOrderDetailList();
        for (OrderDetail temp:orderDetailList) {
             myOrderService.addOrderDetails(temp);//订单详情
        }
          myOrderService.AddMyOrder(myOrder);//订单管理添加()
        return R.reOk();

    }
}
