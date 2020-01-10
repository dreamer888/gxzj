package com.dlc.modules.api.service.impl;

import com.dlc.common.utils.OrderNoGenerator;
import com.dlc.common.utils.R;
import com.dlc.modules.api.dao.AdvTotalMapper;
import com.dlc.modules.api.dao.AdvertisingMapper;
import com.dlc.modules.api.dao.AgentMapper;
import com.dlc.modules.api.dao.WalletDetailMapper;
import com.dlc.modules.api.entity.AdvTotal;
import com.dlc.modules.api.entity.Advertising;
import com.dlc.modules.api.entity.Agent;
import com.dlc.modules.api.entity.WalletDetail;
import com.dlc.modules.api.service.AdvTotalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Auther:YDs
 * @Date: Creat in 2018/7/18/018
 */

@Service
@Transactional
public class AdvTotalServiceImpl implements AdvTotalService{
    @Autowired
    private AdvTotalMapper advTotalMapper;
    @Autowired
    private AdvertisingMapper advertisingMapper;
    @Autowired
    private AgentMapper agentMapper;
    @Autowired
    private WalletDetailMapper walletDetailMapper;

    /**
     *  @Auther:YD
     *  @parameters:
     *  广告点击列表
     */
    @Override
    public List<Advertising> queryAdvOnLineList(Map<String, Object> params) {
        return advTotalMapper.queryAdvOnLineList(params);
    }
    //广告条数
    @Override
    public int queryTotal(Map<String, Object> params) {
        return advertisingMapper.queryTotal(params);
    }
    /**
     *  @Auther:YD
     *  @parameters: advId
     *  添加cpc广告的点击次数
     */
    @Override
    public int addCpcAdvShowNum(Long advId) {
        //根据id查出广告
        Advertising advertising = advertisingMapper.selectAdById(advId);
        //根据advId查询是否有今天的数据
        AdvTotal atl = advTotalMapper.queryTodayAdv(advId);
        //查出广告的单价
        int price = advertising.getPrice();
        if (atl == null){
            //新建广告统计展示次数
            atl = new AdvTotal();
            atl.setAdvId(advId);
            atl.setClickNum(0);
            atl.setShowNum(1);
            atl.setCreateTime(new Date());
            atl.setAdvType(advertising.getType());
            atl.setPrice(price);
            //插入一条新的广告统计记录
            return advTotalMapper.insertSelective(atl);
        }else {
            //添加广告统计展示次数
            return advTotalMapper.updateShowNum(advId);
        }
    }
    /**
     *  @Auther:YD
     *  @parameters:
     *  添加cpm广告的点击次数
     */
    @Override
    public int addCpmAdvClickNum(Long advId) {
        //根据id查出广告
        Advertising advertising = advertisingMapper.selectAdById(advId);
        //根据advId查询是否有今天的数据
        AdvTotal atl = advTotalMapper.queryTodayAdv(advId);
        //查出广告的单价
        int price = advertising.getPrice();
        if (atl == null){
            //新建广告统计点击次数
            atl = new AdvTotal();
            atl.setAdvId(advId);
            atl.setClickNum(1);
            atl.setShowNum(0);
            atl.setCreateTime(new Date());
            atl.setAdvType(advertising.getType());
            atl.setPrice(price);
            //插入一条新的广告统计记录
            return advTotalMapper.insertSelective(atl);
        }else {
            //添加广告统计点击次数
            return advTotalMapper.updateClickNum(advId);
        }
    }

    /**
     *  @Auther:YD
     *  @parameters:
     *  广告展示频次
     */
    @Override
    public R addAdvShowNum(Long advId) {
        //根据id查出广告
        Advertising advertising = advertisingMapper.selectAdById(advId);
        //根据id查出代理商
        Agent ag = agentMapper.selectByPrimaryKey(advertising.getUserId());
        //查出广告的单价
        int price = advertising.getPrice();
        //查询广告的历史展示次数
        int cpmUseNum = advertising.getCpmUseNum();
        //获取当前时间和广告结束时间
        Date date = new Date();
        Long nowTime = date.getTime();
        Long endTime = advertising.getEndTime().getTime();
        //统计展示次数
        int showSum = advTotalMapper.sumShowSum(advId);
        //比较当前时间是否大于广告结束时间 如果大于则吧广告的状态设置成已结束 status=4 并且结算展示费用
        if (endTime < nowTime && advertising.getStatus() != 4){
            //广告投放结束时间小于当前时间
            advertising.setId(advId);
            advertising.setStatus(4);
            //更新广告的satatus
            advertisingMapper.updateByPrimaryKeySelective(advertising);
            //计算总的展示次数是否大于1000 大于则 小于则按价格的百分比扣除费用
            if (showSum < 1000){
                ag.setWallet(ag.getWallet() - ((showSum * price)/1000)); // 新的余额 = 原来余额减去展示总次数乘以单价除以1000
                //更新广告代理商余额
                agentMapper.updateByPrimaryKeySelective(ag);
                //添加钱包明细
                String orderNo = OrderNoGenerator.getOrderIdByTime();
                String openId = ag.getOpenId();
                WalletDetail wd = new WalletDetail();
                wd.setUserId(advertising.getUserId());
                wd.setType(4);
                wd.setStatus(3);
                wd.setMoney((showSum * price)/1000);
                wd.setOpenId(openId);
                wd.setOrderNo(orderNo);
                wd.setCreateTime(new Date());
                walletDetailMapper.insertSelective(wd);
                return R.reError("该广告已过期！");
            }else {
                //更新代理商余额
                ag.setId(advertising.getUserId());
                ag.setWallet(ag.getWallet() - (((showSum - cpmUseNum) * price)/ 1000));
                agentMapper.updateByPrimaryKeySelective(ag);
                //添加钱包明细
                String orderNo = OrderNoGenerator.getOrderIdByTime();
                String openId = ag.getOpenId();
                WalletDetail wd = new WalletDetail();
                wd.setUserId(advertising.getUserId());
                wd.setType(4);
                wd.setStatus(3);
                wd.setMoney(((showSum - cpmUseNum) * price)/1000);
                wd.setOpenId(openId);
                wd.setOrderNo(orderNo);
                wd.setCreateTime(new Date());
                walletDetailMapper.insertSelective(wd);
                return R.reError("该广告已过期！");
            }

        }else {

            //根据advId查询是否有今天的数据
            AdvTotal atl = advTotalMapper.queryTodayAdv(advId);
            //没有今天的展示次数记录就插入新的展示次数
            if (atl == null){
                //判断余额是否够扣下一次的钱 如果不够 则设置成广告已结束 status = 4
                if ((ag.getWallet() < price) && (advertising.getStatus() != 4)) {
                    //把广告的状态 status设置为4 已结束
                    advertising.setId(advId);
                    advertising.setStatus(4);
                    //把广告下架
                    advertisingMapper.updateByPrimaryKeySelective(advertising);
                    return R.reError("该广告已下架！");
                } else if (ag.getWallet() >= price){
                    //新建广告统计展示次数
                    atl = new AdvTotal();
                    atl.setAdvId(advId);
                    atl.setClickNum(0);
                    atl.setShowNum(1);
                    atl.setCreateTime(new Date());
                    atl.setAdvType(advertising.getType());
                    atl.setPrice(price);
                    //插入一条新的广告统计记录
                    advTotalMapper.insertSelective(atl);
                    //扣除展示费用
                    if (((showSum - cpmUseNum) / 1000) >= 1){ // 判断展示次数减去历史展示次数是否大于1000次 大于就扣费，小于就继续累计
                        ag.setId(advertising.getUserId());
                        ag.setWallet(ag.getWallet() - price);
                        //跟新用户余额
                        agentMapper.updateByPrimaryKeySelective(ag);
                        //添加cpm使用过的次数
                        advertising.setId(advId);
                        //每次扣费cpmUseNum 都会加1000
                        advertising.setCpmUseNum(advertising.getCpmUseNum() + 1000);
                        advertisingMapper.updateByPrimaryKeySelective(advertising);
                        //添加钱包明细
                        String orderNo = OrderNoGenerator.getOrderIdByTime();
                        String openId = ag.getOpenId();
                        WalletDetail wd = new WalletDetail();
                        wd.setUserId(advertising.getUserId());
                        wd.setType(4);
                        wd.setStatus(3);
                        wd.setMoney(price);
                        wd.setOpenId(openId);
                        wd.setOrderNo(orderNo);
                        wd.setCreateTime(new Date());
                        walletDetailMapper.insertSelective(wd);
                        //返回广告链接
                        return R.reOk(advertisingMapper.selectByPrimaryKey(advId));
                    }
                }
            }else {
                /*有今天的展示次数记录就累计
                判断余额是否够扣下一次的钱 如果不够 则设置成广告已结束 status = 4*/
                if ((ag.getWallet()/price) < 1 && advertising.getStatus() != 4) {
                    advertising.setId(advId);
                    advertising.setStatus(4);
                    advertisingMapper.updateByPrimaryKeySelective(advertising);
                    return R.reError("该广告已下架！");
                } else if (ag.getWallet() >= price){
                    //添加广告统计展示次数
                    advTotalMapper.updateShowNum(advId);
                    if (((showSum - cpmUseNum) / 1000) >= 1) {
                        //余额充足时，扣除费用并更新用户余额
                        ag.setWallet(ag.getWallet() - price);
                        agentMapper.updateByPrimaryKeySelective(ag);
                        //添加cpm使用过的次数
                        advertising.setId(advId);
                        advertising.setCpmUseNum(advertising.getCpmUseNum() + 1000);
                        advertisingMapper.updateByPrimaryKeySelective(advertising);
                        //添加钱包明细
                        String orderNo = OrderNoGenerator.getOrderIdByTime();
                        String openId = ag.getOpenId();
                        WalletDetail wd = new WalletDetail();
                        wd.setUserId(advertising.getUserId());
                        wd.setType(4);
                        wd.setStatus(3);
                        wd.setMoney(price);
                        wd.setOpenId(openId);
                        wd.setOrderNo(orderNo);
                        wd.setCreateTime(new Date());
                        //插入新的钱包明细记录
                        walletDetailMapper.insertSelective(wd);
                    }
                }
            }
            //返回广告链接
            return R.reOk(advertisingMapper.selectByPrimaryKey(advId));
        }
    }
    /**
     *  @Auther:YD
     *  @parameters:
     *  点击次数及点击扣费
     */
    @Override
    public R addClickNum(Long advId) {

        //根据id查出广告
        Advertising advertising = advertisingMapper.selectAdById(advId);
        //根据id查出代理商
        Agent ag = agentMapper.selectByPrimaryKey(advertising.getUserId());
        //查出广告单价
        int price = advertising.getPrice();
        //获取当前时间和广告结束时间
        Date date = new Date();
        Long nowTime = date.getTime();
        Long endTime = advertising.getEndTime().getTime();
        // 如果当前时间大于广告结束时间更新广告的状态 status=4 广告投放结束
        if (nowTime > endTime){
            advertising.setId(advId);
            //更新广告的satatus
            advertising.setStatus(4);
            advertisingMapper.updateByPrimaryKeySelective(advertising);
            return R.reError("该广告已过期!");
        }else {
            //根据advId查询是否有今天的数据
            AdvTotal atl = advTotalMapper.queryTodayAdv(advId);
            //没有今天的点击次数记录就插入新的展示次数
            if (atl == null) {
                //判断余额是否够扣下一次的钱 如果不够 则设置成广告已结束 status = 4
                if (ag.getWallet() < price) {
                    advertising.setId(advId);
                    advertising.setStatus(4);
                    //更新广告状态
                    advertisingMapper.updateByPrimaryKeySelective(advertising);
                    return R.reError("该广告已下架！");
                } else {
                    //新建广告统计点击次数
                    atl = new AdvTotal();
                    atl.setAdvId(advId);
                    atl.setClickNum(1);
                    atl.setShowNum(0);
                    atl.setCreateTime(new Date());
                    atl.setAdvType(advertising.getType());
                    atl.setPrice(price);
                    //插入一条广告的数据
                    advTotalMapper.insertSelective(atl);
                    //余额充足时
                    ag.setId(advertising.getUserId());
                    ag.setWallet(ag.getWallet() - price);
                    //更新用户的余额
                    agentMapper.updateByPrimaryKeySelective(ag);
                    //添加钱包明细
                    String orderNo = OrderNoGenerator.getOrderIdByTime();
                    String openId = ag.getOpenId();
                    WalletDetail wd = new WalletDetail();
                    wd.setUserId(advertising.getUserId());
                    wd.setType(4);
                    wd.setStatus(3);
                    wd.setMoney(price);
                    wd.setOpenId(openId);
                    wd.setOrderNo(orderNo);
                    wd.setCreateTime(new Date());
                    //插入一条新的钱包明细数据
                    walletDetailMapper.insertSelective(wd);
                }
            } else {
                //判断余额是否够扣下一次的钱 如果不够 则设置成广告已结束 status = 4
                if (ag.getWallet() < price) {
                    advertising.setId(advId);
                    advertising.setStatus(4);
                    //更新广告的状态 设置为已结束
                    advertisingMapper.updateByPrimaryKeySelective(advertising);
                    return R.reError("该广告已下架！");
                } else {
                    //添加广告统计点击次数
                    advTotalMapper.updateClickNum(advId);
                    //扣除点击费用
                    ag.setId(advertising.getUserId());
                    ag.setWallet(ag.getWallet() - price);
                    //更新广告商余额
                    agentMapper.updateByPrimaryKeySelective(ag);
                    //添加钱包明细
                    String orderNo = OrderNoGenerator.getOrderIdByTime();
                    String openId = ag.getOpenId();
                    WalletDetail wd = new WalletDetail();
                    wd.setUserId(advertising.getUserId());
                    wd.setType(4);
                    wd.setStatus(3);
                    wd.setMoney(price);
                    wd.setOpenId(openId);
                    wd.setOrderNo(orderNo);
                    wd.setCreateTime(new Date());
                    //插入一条新的钱包明细数据
                    walletDetailMapper.insertSelective(wd);
                }
            }
        }
        //返回广告信息用于点击广告链接
        return R.reOk(advertisingMapper.selectByPrimaryKey(advId));
    }
}
