package com.dlc.modules.api.service;

import com.alibaba.fastjson.JSONObject;
import com.dlc.common.utils.Query;
import com.dlc.modules.api.entity.OfficialAccounts;

import java.util.List;
import java.util.Map;

/**
 * @author chenyuexin
 * @version 1.0
 * @date 2018-07-18 16:09
 */
public interface OfficialAccountsService {
    List<Map<String,Object>> queryOfficialAccountsList(Map<String,Object> map);

    int queryofficialAccountsCount(Map<String, Object> map);

    int updateStatus(Integer status, Long id);

    int addOfficialAccounts(OfficialAccounts officialAccounts, Long userId);

    int delete(Long id);

    /**
     * 查询公众号信息
     * @param id
     * @return
     */
    Map<String,Object> queryOfficialInfoById(Long id);

    /**
     * 管理端列表公众号
     */
    List<Map<String,Object>> queryManagementOfficialAccountsList(Map<String,Object> map);

    /**
     * 管理端
     * @param id
     * @return
     */
    Map<String,Object> queryManagementOfficialInfoById(Long id);

    /**
     * 更新
     * @param officialAccounts
     * @return
     */
    int updateOfficialAccount(OfficialAccounts officialAccounts);
}
