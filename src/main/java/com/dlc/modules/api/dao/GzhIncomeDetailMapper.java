package com.dlc.modules.api.dao;

import com.dlc.modules.api.entity.GzhIncomeDetail;

import java.util.Map;

public interface GzhIncomeDetailMapper {
    int deleteByPrimaryKey(Long id);

    int insert(GzhIncomeDetail record);

    int insertSelective(GzhIncomeDetail record);

    GzhIncomeDetail selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(GzhIncomeDetail record);

    int updateByPrimaryKey(GzhIncomeDetail record);

    GzhIncomeDetail findIncomeDetail(GzhIncomeDetail gzhIncomeDetail);

    String queryOfficialAccountsListCount(Map<String, Object> paramMap);
}