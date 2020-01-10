package com.dlc.modules.api.service.impl;

import com.baomidou.framework.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.dlc.common.utils.Query;
import com.dlc.common.utils.R;
import com.dlc.modules.api.dao.GoodsMapper;
import com.dlc.modules.api.dao.MyOrderMapper;
import com.dlc.modules.api.dao.ShoppingCarMapper;
import com.dlc.modules.api.entity.*;
import com.dlc.modules.api.service.GoodsListService;
import com.dlc.modules.api.service.WalletDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 廖修坤
 * @date 2018/7/17
 */
@Service("GoodsListService")
public class GoodsListServiceImpl implements GoodsListService{
    @Autowired
    GoodsMapper goodsMapper;
    @Autowired
    ShoppingCarMapper shoppingCarMapper;
    @Autowired
    MyOrderMapper myOrderMapper;
    /**
     * 商品列表展示
     * */
    public List<Map<String,Object>> showGoodsList(Map<String, Object> params) {
        List<Map<String,Object>> mapList =null;
        mapList =goodsMapper.showGoodsList(params);
        if(mapList.size()>0){
        for(Map map:mapList){
            Integer priceTemp= (Integer) map.get("price");
            Integer freightTemp = (Integer) map.get("freight");
            Double price = (priceTemp.doubleValue())/100;
            Double freight = (freightTemp.doubleValue())/100;
            map.put("price",price);
            map.put("freight",freight);

        }
            return mapList;
        }
        return null;
    }

    /**
     * 商品上架/商品下架
     * */
    public Integer changeGoodsToDown(Long id,Byte status) {
        return goodsMapper.changeGoodsToDown(id,status);
    }

    /**
     * 根据商品ID删除商品(假删除)
     * */
    public Integer deleteGoodsById(Long id) {
        return goodsMapper.deleteByPrimaryKey(id);
    }

    /**
     * 添加商品
     * */
    public Integer AddGoodsById(Goods record) {
        return goodsMapper.insert(record);
    }

    /**
     * 查询商品分类信息
     * */
    public List<GoodsCategory> goodsCategoryList() {
        return goodsMapper.goodsCategoryList();
    }

    /*+++++++++++++++++++++++++++++++++++++++++++++++++*/

    /**
     * 管理端：商品列表的展示
     * */
    public List<Goods> showManagerGoodsList(Goods goods) {
        return goodsMapper.showManagerGoodsList(goods);
    }

    /**
     * 添加购物车
     * */
    public int AddShopCar(Goods goods) {
        return goodsMapper.AddShopCar(goods);
    }

    /**
     * 根据分类Id查询分类名称
     * */
    public String searchCateNameBycateId(Integer categoryId) {
        return goodsMapper.searchCateNameBycateId(categoryId);
    }

    /**
     * 删除购物车
     * */
    public void DelShopCarById(String ids) {
        String[] arr = ids.split(",");
        for(String temp:arr){
            this.shoppingCarMapper.deleteByPrimaryKey(Long.valueOf(temp));
        }

    }

    /**
     * 设为默认地址
     * */
    /*public int changeAddressStatus(Byte isDefault,Long addressId) {
        return goodsMapper.changeAddressStatus(isDefault,addressId);
    }*/

    /**
     * 管理端：商品列表展示
     * */
    public List<Map<String, Object>> shopCategoryList(Map<String, Object> params) {
        List<Map<String,Object>> mapList =null;
        mapList  = goodsMapper.shopCategoryList(params);
        if(mapList.size()>0){
            for(Map map:mapList){
                Integer priceTemp= (Integer) map.get("price");
                Integer freightTemp = (Integer) map.get("freight");
                Double price = (priceTemp.doubleValue())/100;
                Double freight = (freightTemp.doubleValue())/100;
                map.put("price",price);
                map.put("freight",freight);

            }
            return mapList;
        }
          return mapList;
    }

    /**
     * 总记录条数
     * */
    public int showGoodsListCount(Map<String, Object> map) {
        return goodsMapper.showGoodsListCount(map);
    }

    @Override
    public int CategoryListCount(Query query) {
       int count = goodsMapper.CategoryListCount(query);
        return count;
    }

    @Override
    public Map<String, Object> goodsDetails(Long id){
        Map<String, Object> map = null;
        map = goodsMapper.goodsDetails(id);
        if(map==null){
          return R.reError("查询的商品不存在或已下架");
        }
        Integer priceTemp= (Integer) map.get("price");
        Integer freightTemp = (Integer) map.get("freight");
        Double price = (priceTemp.doubleValue())/100;
        Double freight = (freightTemp.doubleValue())/100;
        map.put("price",price);
        map.put("freight",freight);
        return map;
    }

    /**
     * 购物车列表
     * */
    public List<ShoppingCar> shopCarList(Long userId) {
        List<ShoppingCar> shoppingCarList = goodsMapper.shopCarList(userId);
        if(shoppingCarList.size()<=0){
          return null;
        }
        return shoppingCarList;
    }

    /**
     * 下单(根据商品Id添加订单)
     * */
    public List<Map<String, Object>> placeAnOrder(String goodsIds,Long userId) {
        List<Map<String, Object>> mapList = goodsMapper.placeAnOrder(goodsIds,userId);
        return mapList;
    }

    /**
     * 订单详情
     * */
    public int addOrderDetails(OrderDetail orderDetail) {
        Integer price = orderDetail.getGoodsPrice();
        orderDetail.setGoodsPrice(price);
        return myOrderMapper.addOrderDetails(orderDetail);
    }

    /**
     * 订单管理
     * */
    public int AddMyOrder(MyOrder myOrder) {
        Integer goodsSum =myOrder.getGoodsSum();
        Integer realPayment = myOrder.getRealPayment();
        myOrder.setGoodsSum(goodsSum);
        myOrder.setRealPayment(realPayment);
        return myOrderMapper.AddMyOrder(myOrder);
    }

    /**
     * 编辑商品:根据商品ID编辑商品内容
     * */
    public int editGoodsById(Goods goods) {
      return myOrderMapper.editGoodsById(goods);
    }

   /**
    * 删除购物车
    * */
    public int delShoppingCarById(Long goodsId,Long customer) {
        return myOrderMapper.delShoppingCarById(goodsId,customer);
    }

    /**
     * 余额支付成功,更改status
     * */
    public int upOrdreStatus(String orderNo,String transactionNumber) {
        return myOrderMapper.upOrdreStatus(orderNo,transactionNumber);
    }

    /**
     * 根据商品Id查询单条商品
     * */
    public Goods queryGoodsById(Long id,Long userId) {
        return goodsMapper.queryGoodsById(id,userId);
    }

}
