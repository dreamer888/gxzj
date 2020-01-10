package com.dlc.modules.sys.service.impl;

import com.dlc.modules.api.dao.GlWalletMapper;
import com.dlc.modules.api.entity.GlWallet;
import com.dlc.modules.sys.dao.GlWalletDao;
import com.dlc.modules.sys.service.GlWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/***********************************
 *Class by 王楚荣
 *2018/7/20/020
 * **********************************/
@Service("glWalletServiceImpl")
public class GlWalletServiceImpl implements GlWalletService{
    @Autowired
    private GlWalletDao glWalletDao;
    @Autowired
    private GlWalletMapper glWalletMapper;

    @Override
    public List<Map<String, Object>> queryList(Map<String, Object> map) {
        return glWalletDao.queryListMapByMap(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return glWalletDao.queryTotal(map);
    }

    @Override
    public int moneyTotal() {
        return glWalletDao.moneyTotal();
    }

    @Override
    public List<Map<String, Object>> payment(Map<String, Object> params) {
        return glWalletDao.payment(params);
    }

    @Override
    public int paymentTotal(Map<String, Object> map) {
        return glWalletDao.paymentTotal(map);
    }
    //添加个联钱包的余额
    @Override
    public void updateGlWallet(int wallet) {
        //下面更新个联钱包余额
        GlWallet glWallet = this.glWalletMapper.findGlWallet();
        if(null == glWallet){
            glWallet = new GlWallet();
            glWallet.setBalance(Long.valueOf(wallet));
            this.glWalletMapper.insertSelective(glWallet);
        }else{
            glWallet.setBalance(glWallet.getBalance()+wallet);
            this.glWalletMapper.updateByPrimaryKeySelective(glWallet);
        }

    }

//    //广告充值记录
//    @Override
//    public void insertGwInfo(int wallet) {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//        GlWallet gw = new GlWallet();
//        gw.setType(2);
//        gw.setMoney(wallet);
//        gw.setCreateTime(new Date());
//        glWalletDao.insertGwInfo(gw);
//    }
}
