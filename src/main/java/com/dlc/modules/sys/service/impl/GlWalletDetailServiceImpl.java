package com.dlc.modules.sys.service.impl;

import com.dlc.modules.api.dao.GlWalletDetailMapper;
import com.dlc.modules.api.entity.GlWallet;
import com.dlc.modules.api.entity.GlWalletDetail;
import com.dlc.modules.sys.dao.GlWalletDao;
import com.dlc.modules.sys.dao.GlWalletDetailDao;
import com.dlc.modules.sys.entity.GlWalletDetailEntity;
import com.dlc.modules.sys.service.GlWalletDetailService;
import com.dlc.modules.sys.service.GlWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/***********************************
 *Class by 王楚荣
 *2018/7/20/020
 * **********************************/
@Service("glWalletDetailServiceImpl")
public class GlWalletDetailServiceImpl implements GlWalletDetailService {
    @Autowired
    private GlWalletDetailDao glWalletDetailDao;
    @Autowired
    private GlWalletDetailMapper glWalletDetailMapper;

    @Override
    public GlWalletDetailEntity queryObject(Long agencyId) {
        return glWalletDetailDao.queryObject(agencyId);
    }

    @Override
    public List<Map<String, Object>> queryList(Map<String, Object> map) {
        return glWalletDetailDao.queryListMapByMap(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return glWalletDetailDao.queryTotal(map);
    }

    @Override
    public int moneyTotal() {
        return glWalletDetailDao.moneyTotal();
    }

    @Override
    public List<Map<String, Object>> payment(Map<String, Object> params) {
        return glWalletDetailDao.payment(params);
    }

    @Override
    public int paymentTotal(Map<String, Object> map) {
        return glWalletDetailDao.paymentTotal(map);
    }
    //广告充值记录
    @Override
    public void insertGwInfo(int wallet) {
        GlWalletDetail gw = new GlWalletDetail();
        gw.setType(2);
        gw.setMoney(wallet);
        gw.setCreateTime(new Date());
        glWalletDetailMapper.insertSelective(gw);
    }
}
