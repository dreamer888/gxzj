package com.dlc.modules.api.dao;

import com.dlc.common.utils.Query;
import com.dlc.modules.api.entity.Goods;
import com.dlc.modules.api.entity.GoodsCategory;
import com.dlc.modules.api.entity.MyOrder;
import com.dlc.modules.api.entity.ShoppingCar;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface GoodsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Goods record);

    int insertSelective(Goods record);

    Goods selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Goods record);

    int updateByPrimaryKey(Goods record);

    List<Map<String,Object>> showGoodsList(Map<String,Object> map);//商品列表展示

    Integer changeGoodsToDown(@Param("id")Long id,@Param("status")Byte status);//商品上架/下架

    List<GoodsCategory> goodsCategoryList();//查询商品分类信息
/*++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/


    List<Goods> showManagerGoodsList(Goods goods);//管理端：商品列表的展示

    int AddShopCar(Goods goods);//添加购物车

    String searchCateNameBycateId(Integer categoryId);

    int DelShopCarById(List<ShoppingCar> shoppingCarList);//删除购物车

    int changeAddressStatus(@Param("isDefault") Byte isDefault);//设为默认地址

    List<Map<String, Object>> shopCategoryList(Map<String, Object> params);//管理端：商品列表展示(含搜索)

    int showGoodsListCount(Map<String, Object> map);//总记录条数

    int CategoryListCount(Query query);

    Map<String,Object>  goodsDetails(Long id);//根据商品Id查询商品详情

    List<ShoppingCar> shopCarList(Long userId);//购物车列表String goodsIds,Long userId

    List<Map<String,Object>> placeAnOrder(@Param("goodsId") String goodsIds,@Param("userId")Long userId);//下单(根据商品Id添加订单)

    Goods queryGoodsById(@Param("id") Long id,@Param("userId")Long userId);//根据商品Id查询单条商品
}