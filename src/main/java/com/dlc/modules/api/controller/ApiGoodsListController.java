package com.dlc.modules.api.controller;

import com.dlc.common.utils.PageUtils;
import com.dlc.common.utils.Query;
import com.dlc.common.utils.R;
import com.dlc.modules.api.entity.*;
import com.dlc.modules.api.service.GoodsListService;
import com.dlc.modules.api.vo.AgentVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @author: 廖修坤
 * @date 2018/7/17

 * @return
 * @throws
 * @since
 * @description:共享纸巾：供货端商品列表
 */
@RestController
@RequestMapping("/api/goods")//goods
public class ApiGoodsListController extends BaseController{
    @Autowired
    private GoodsListService goodsListService;

    /**
     * 商品列表展示:根据商品上架状态
     * */
    @RequestMapping("/goodsList")
    public R goodsList(@RequestParam Map<String, Object> params,HttpServletRequest request){
        Long userId = getAgentVo(request).getId();
        params.put("userId",userId);

        if (org.springframework.util.StringUtils.isEmpty(params.get("page")) || org.springframework.util.StringUtils.isEmpty(params.get("limit"))) {
            return R.error("分页信息不能为空");
        }
        if(org.springframework.util.StringUtils.isEmpty(params.get("status"))){
            return R.reError("status不能为空");
        }
        Query query =new Query(params);
        List<Map<String, Object>> list =goodsListService.showGoodsList(query);//数据查询
        int total = goodsListService.showGoodsListCount(query);//总记录数查询
        PageUtils pageUtil = new PageUtils(list,total,query.getLimit(), query.getPage());
        return R.reOk(pageUtil);
    }


    /**
     * 商品的上架/下架
     * parameterType:id 商品ID
     * */
    @RequestMapping("/changeGoodsToDown")
    @ResponseBody
    public R changeGoodsToDown(@RequestParam Long id,Byte status){
            goodsListService.changeGoodsToDown(id,status);
        return R.reOk();
    }


    /**
     * 根据商品ID删除商品
     * parameterType:id 商品ID
     * */
    @RequestMapping("/deleteGoodsById")
    public R deleteGoodsById(Long id){
        goodsListService.deleteGoodsById(id);
        return R.reOk();
    }

    /**
     * 添加商品
     *
     * */
    @RequestMapping("/AddGoodsById")
    public R AddGoodsById(Goods record, HttpServletRequest request){
        AgentVo userInfo = getAgentVo(request);
        record.setUserId(userInfo.getId());
        goodsListService.AddGoodsById(record);
        return R.reOk();
    }

    /**
     * 编辑商品
     *根据商品ID编辑商品内容
     * */
    @RequestMapping("/editGoodsById")
    public R EditGoodsById(Goods goods, HttpServletRequest request){
        AgentVo userInfo = getAgentVo(request);
        Long userId = userInfo.getId();
        goods.setUserId(userId);
      int change = goodsListService.editGoodsById(goods);
      if(change<=0){
        return R.reError("更新失败");
      }
        return R.reOk();
    }

    /**
     * 查询商品分类信息
     *
     * */
    @RequestMapping("/goodsCategoryList")
    public R goodsCategoryList(){
        List<GoodsCategory> goodsCategoryList = goodsListService.goodsCategoryList();
        return R.reOk().put("goodsCategoryList",goodsCategoryList);
    }

    /**
     * 根据商品Id查询单条商品
     * */
    @RequestMapping("/queryGoodsById")
    public R queryGoodsById(Long id,HttpServletRequest request){
        AgentVo userInfo = getAgentVo(request);
        Long userId = userInfo.getId();
       Goods goods = goodsListService.queryGoodsById(id,userId);
        return R.reOk().put("goods",goods);
    }

    /*+++++++++++++++++++++++++++++++++++++++++++++++++++*/

    /**
     * @author: 廖修坤
     * @date 2018/7/20

     * @return
     * @throws
     * @since
     * @description:管理端：
    */

    /**
    *商品列表（按分类）：
    * */
    @RequestMapping("/managerGoodsList")
    public R managerGoodsList(@RequestParam Map<String, Object> params,HttpServletRequest request){
        if (org.springframework.util.StringUtils.isEmpty(params.get("page")) || org.springframework.util.StringUtils.isEmpty(params.get("limit"))) {
            return R.error("分页信息不能为空");
        }
        if(org.springframework.util.StringUtils.isEmpty(params.get("categoryId"))){
            return R.reError("categoryId不能为空");
        }

        Query query =new Query(params);
        List<Map<String, Object>> list =goodsListService.shopCategoryList(query);//数据查询
        int total = goodsListService.CategoryListCount(query);//总记录数查询
        if(list.size()==0 || total==0){
            return R.reOk("此分类下没有查询到商品");
        }
        PageUtils pageUtil = new PageUtils(list,total,query.getLimit(), query.getPage());
        return R.reOk(pageUtil);
    }

    /**
     * 商品搜索
     * */
    @RequestMapping("/searchGoodsList")
    public R searchGoodsList(@RequestParam Map<String, Object> params){
        if (org.springframework.util.StringUtils.isEmpty(params.get("page")) || org.springframework.util.StringUtils.isEmpty(params.get("limit"))) {
            return R.error("分页信息不能为空");
        }
        if(org.springframework.util.StringUtils.isEmpty(params.get("name"))){
            return R.reError("name不能为空");
        }
        Query query =new Query(params);
        List<Map<String, Object>> list =goodsListService.shopCategoryList(query);//数据查询
        if(list.size()==0){
            return R.reOk("此分类下没有查询到商品");
        }
        int total = goodsListService.CategoryListCount(query);//总记录数查询
        PageUtils pageUtil = new PageUtils(list,total,query.getLimit(), query.getPage());
        return R.reOk(pageUtil);
    }

    /**
    * 添加购物车
    * */
    @RequestMapping("/AddShopCar")
    public R AddShopCar(Goods goods,HttpServletRequest request){
        goods.setUserId(getAgentVo(request).getId());
        Integer categoryId = goods.getCategoryId();
        String cateName =  goodsListService.searchCateNameBycateId(categoryId);//根据分类Id查询分类名称
        goods.setCateName(cateName);
        int x = goodsListService.AddShopCar(goods);
        return R.reOk();
    }

    /**
     * 删除购物车
    * */
    @RequestMapping("/DelShopCar")
    public R DelShopCar(String ids){
       if(StringUtils.isBlank(ids)){
           return R.reError("ids,不能为空");
       }
        this.goodsListService.DelShopCarById(ids);
        return R.reOk();
    }

    /**
     * 商品详情
     * Long id：商品Id
     * */
    @RequestMapping("/goodsDetails")
    public R goodsDetails(Long id){
        Map<String,Object> goods = goodsListService.goodsDetails(id);
        return R.reOk().put("goods",goods);
    }

    /**
     *购物车列表
     * */
    @RequestMapping("/shopCarList")
    public R shopCarList(HttpServletRequest request){
        Long userId =  getAgentVo(request).getId();
        List<ShoppingCar> shoppingCarList = goodsListService.shopCarList(userId);
        if(shoppingCarList==null){
            R.reError("该用户购物车为空");
        }
        return R.reOk().put("shoppingCarList",shoppingCarList);
    }

    /**
     *下单(根据商品Id添加订单)
     * */
    @RequestMapping("/placeAnOrder")
    public R placeAnOrder(String goodsIds,HttpServletRequest request){
        Long userId =  getAgentVo(request).getId();
        List<Map<String,Object>> mapList = goodsListService.placeAnOrder(goodsIds,userId);
        return R.reOk().put("mapList",mapList);
    }

    /**
    * 提交订单（支付）
    * */
    /*@RequestMapping(value = "/submitOrder",method = RequestMethod.POST)
    public R submitOrder(@RequestBody MyOrder myOrder, HttpServletRequest request){
        Long customer= getAgentVo(request).getId();
        myOrder.setCustomer(customer);
        List<OrderDetail> orderDetailList= myOrder.getOrderDetailList();
        for (OrderDetail temp:orderDetailList) {
            int y = goodsListService.addOrderDetails(temp);//订单详情
        }
        int x = goodsListService.AddMyOrder(myOrder);//订单管理添加()
        return R.reOk();
    }*/
}
