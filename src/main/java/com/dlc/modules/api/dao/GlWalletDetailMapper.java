package com.dlc.modules.api.dao;

import com.dlc.modules.api.entity.GlWalletDetail;
import org.springframework.stereotype.Repository;

@Repository
public interface GlWalletDetailMapper {
    int deleteByPrimaryKey(Long id);

    int insert(GlWalletDetail record);

    int insertSelective(GlWalletDetail record);

    GlWalletDetail selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(GlWalletDetail record);

    int updateByPrimaryKey(GlWalletDetail record);
}