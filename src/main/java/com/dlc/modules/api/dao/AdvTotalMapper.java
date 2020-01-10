package com.dlc.modules.api.dao;

import com.dlc.common.utils.R;
import com.dlc.modules.api.entity.AdvTotal;
import com.dlc.modules.api.entity.Advertising;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface AdvTotalMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AdvTotal record);

    int insertSelective(AdvTotal record);

    AdvTotal selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AdvTotal record);

    int updateByPrimaryKey(AdvTotal record);

    AdvTotal queryTodayAdv(Long advId);

    int updateShowNum(Long advId);

    int updateClickNum(Long advId);
    //计算累计点击总和
    int sumShowSum(Long advId);
    //广告点击列表
    List<Advertising> queryAdvOnLineList(Map<String, Object> params);
    //广告条数
    int queryTotal(Map<String, Object> params);
}