package com.dlc.modules.sys.service;

import com.dlc.modules.sys.entity.UserLikeGzh;

import java.util.List;
import java.util.Map;

/***********************************
 *Class by 王楚荣
 *2018/7/20/020
 * **********************************/
public interface UserLikeGzhService {

    List<Map<String,Object>> queryList(Map<String, Object> map);

    int queryTotal(Map<String, Object> map);
}
