package com.dlc.modules.api.service;

import com.dlc.common.utils.Query;
import com.dlc.modules.api.entity.*;

import java.util.List;
import java.util.Map;

/**
 * 商品列表
 * */
public interface GoodsListService {
    List<Map<String,Object>> showGoodsList(Map<String, Object> params);//商品列表展示

    Integer changeGoodsToDown(Long id,Byte status);//商品上架/下架

    Integer deleteGoodsById(Long id);//根据商品ID删除商品

    Integer AddGoodsById(Goods record);//添加商品

    List<GoodsCategory> goodsCategoryList();//查询商品分类信息


/*++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
    List<Goods> showManagerGoodsList(Goods goods);//管理端：商品列表的展示

    int AddShopCar(Goods goods);//添加购物车

    String searchCateNameBycateId(Integer categoryId);//根据分类Id查询分类名称

    void DelShopCarById(String ids);//删除购物车

    /*int changeAddressStatus(Byte isDefault,Long addressId);//设为默认地址*/

    List<Map<String, Object>> shopCategoryList(Map<String, Object> params);//管理端商品列表展示(含搜索)

    int showGoodsListCount(Map<String, Object> map); //总记录数查询

    int CategoryListCount(Query query);

    Map<String,Object> goodsDetails(Long id);//根据商品Id查询商品详情

    List<ShoppingCar> shopCarList(Long userId);//购物车列表

    List<Map<String,Object>> placeAnOrder(String goodsIds,Long userId);//下单(根据商品Id添加订单)

    int addOrderDetails(OrderDetail temp);//订单详情

    int AddMyOrder(MyOrder myOrder);//订单管理添加

    int editGoodsById(Goods goods);//编辑商品:根据商品ID编辑商品内容

    int delShoppingCarById(Long goodsId,Long customer);//删除购物车

    int upOrdreStatus(String orderNo,String transactionNumber);//余额支付成功,更改status

    Goods queryGoodsById(Long id,Long userId);//根据商品Id查询单条商品
}
