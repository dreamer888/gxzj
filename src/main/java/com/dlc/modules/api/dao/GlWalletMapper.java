package com.dlc.modules.api.dao;

import com.dlc.modules.api.entity.GlWallet;
import org.springframework.stereotype.Repository;

@Repository
public interface GlWalletMapper {
    int deleteByPrimaryKey(Long id);

    int insert(GlWallet record);

    int insertSelective(GlWallet record);

    GlWallet selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(GlWallet record);

    int updateByPrimaryKey(GlWallet record);

    GlWallet findGlWallet();

}