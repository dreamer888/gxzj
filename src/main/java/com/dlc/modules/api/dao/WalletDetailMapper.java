package com.dlc.modules.api.dao;

import com.dlc.modules.api.entity.MyOrder;
import com.dlc.modules.api.entity.OrderDetail;
import com.dlc.modules.api.entity.WalletDetail;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
@Repository
public interface WalletDetailMapper {
    int deleteByPrimaryKey(Long id);

    int insert(WalletDetail record);

    int insertSelective(WalletDetail record);

    WalletDetail selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WalletDetail record);

    int updateByPrimaryKeyWithBLOBs(WalletDetail record);

    int updateByPrimaryKey(WalletDetail record);

    List<Map<String,Object>> wdList(Map<String, Object> params);

    void creatRcharge(WalletDetail walletDetail);

    int addBalance(Map<String,Object> maps);//生产者增加余额

    int cutDownBalance(MyOrder myOrder);//管理者减少余额

    Integer selectMoney(Long customer);//查询用户余额

    int addMoneyById(Long id, Integer goodsSum);//供货端增加余额

    int addMsgToglWallet(BigDecimal money);//增加个联钱包明细记录

    int addWalletDetail(Map<String,Object> temp);//钱包明细增加条记录

    WalletDetail findWdByOrderNo(String orderNo);
    //钱包明细详情
    Map<String,Object> queryWdInfo(Long id);

    int editMsgToglWallet(BigDecimal money);//个联钱包修改金额

    int addMoneyById2(Map<String, Object> map);//供货端增加余额（微信支付）
    //列表条数统计
    int queryTotal(Map<String, Object> params);
    //根据orderNo查询walletDetail
    WalletDetail selectByOrderNo(String orderNo);
    //查询广告主钱包列表明细
    List<Map<String,Object>> selectAdWdListByType(Map<String, Object> params);
    //查询广告主钱包列表明细条数
    int queryAdvTotal(Map<String, Object> params);
    //查询供货主/代理商钱包列表明细(type为空)
    List<Map<String,Object>> selectDlWdListByType(Map<String, Object> params);
    //查询供货主/代理商钱包列表明细(type为空)条数
    int queryDlTotal(Map<String, Object> params);

    List<OrderDetail> queryOrderDetailByOderNo(@Param("out_trade_no") String out_trade_no);

    int addMonthlySales(Map<String, Object> goodsMap);//月销量增加
}