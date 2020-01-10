package com.dlc.modules.sys.service;

import com.dlc.common.utils.Constant;
import com.dlc.common.utils.R;
import com.dlc.modules.sys.entity.WalletDetailEntity;

import java.util.List;
import java.util.Map;

/**
 * 钱包明细表
 * 
 * @author dlc.dg.java
 * @email dlc.dg.java@163.com
 * @date 2018-07-24 13:42:27
 */
public interface WalletDetailService {
	
	WalletDetailEntity queryObject(Long id);
	
	List<WalletDetailEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(WalletDetailEntity walletDetail);
	
	void update(WalletDetailEntity walletDetail);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);

    R payToUser(String openId, Integer money, String orderNo,Long id,Long userId) throws Exception;

    int updateWalletDetailStatus(Integer status,Long id,String reason);
}
