package com.dlc.modules.api.dao;

import com.dlc.modules.api.entity.GlAdv;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface GlAdvMapper {
    int deleteByPrimaryKey(Long id);

    int insert(GlAdv record);

    int insertSelective(GlAdv record);

    GlAdv selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(GlAdv record);

    int updateByPrimaryKey(GlAdv record);

    List<Map<String,Object>> selectAllGlAdv();
}