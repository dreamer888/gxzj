package com.dlc.modules.api.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.dlc.common.utils.ConfigConstant;
import com.dlc.common.utils.Query;
import com.dlc.modules.api.dao.DeviceMapper;
import com.dlc.modules.api.dao.OfficialAccountsMapper;
import com.dlc.modules.api.entity.OfficialAccounts;
import com.dlc.modules.api.service.OfficialAccountsService;
import com.dlc.modules.qd.utils.MD5;
import com.dlc.modules.qd.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chenyuexin
 * @version 1.0
 * @date 2018-07-18 16:34
 */
@Service
public class OfficialAccountsServiceImpl implements OfficialAccountsService {

    @Autowired
    OfficialAccountsMapper officialAccountsMapper;
    @Autowired
    DeviceMapper deviceMapper;

    @Override
    public List<Map<String,Object>> queryOfficialAccountsList(Map<String, Object> map) {
        //为了照顾**前端方便渲染而构造这个结构
        Map<String, Object> item = new HashMap<>();
        item.put("is_null", 0);


        //固定返回9个对象方便前端渲染(前端还有这种操作？？？)
        List<Map<String, Object>> list = officialAccountsMapper.queryOfficialAccountsList(map);
        //查询总平台添加的
        map.put("agentId",0);
        map.put("deviceNo",null);
        List<Map<String, Object>> list1 = officialAccountsMapper.queryOfficialAccountsList(map);
        list.addAll(list1);
        for (Map<String, Object> objectMap : list) {
            objectMap.put("is_null", 1);
        }
        int length = 9 - list.size();

        for (int i = 0; i < length; i++) {
            list.add(item);

        }
        return list;
    }

    @Override
    public int queryofficialAccountsCount(Map<String, Object> map) {
        return officialAccountsMapper.queryofficialAccountsCount(map);

    }

    @Override
    public int updateStatus(Integer status, Long id) {
        OfficialAccounts officialAccounts = new OfficialAccounts();
        officialAccounts.setId(id);
        officialAccounts.setStatus(status);
        return officialAccountsMapper.updateByPrimaryKeySelective(officialAccounts);
    }

    @Override
    public int addOfficialAccounts(OfficialAccounts officialAccounts, Long userId) {
        officialAccounts.setOriginalPrice(officialAccounts.getOriginalPrice() * 100);
        officialAccounts.setDeductPrice(officialAccounts.getDeductPrice() * 100);
        officialAccounts.setAgentId(userId);
        officialAccounts.setToken(MD5Util.MD5Encode(officialAccounts.getAppId(), "utf-8"));
        officialAccounts.setServerURL(ConfigConstant.PRO_VER_URL + "/wx/connect?code=" + officialAccounts.getAppId());
        officialAccounts.setCreateTime(new Date());
        return officialAccountsMapper.insertSelective(officialAccounts);

    }

    @Override
    public int delete(Long id) {
        return officialAccountsMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Map<String, Object> queryOfficialInfoById(Long id) {
        Map<String, Object> map = new HashMap<>();
        OfficialAccounts officialAccounts = officialAccountsMapper.queryOfficialCountInfo(id);
        //领取纸巾码
        map.put("freeCode", officialAccounts.getFreeCode());
        map.put("name", officialAccounts.getName());
        map.put("appId", officialAccounts.getAppId());
        map.put("appSecret", officialAccounts.getAppSecret());
        map.put("upperLimit", officialAccounts.getUpperLimit());
        return map;
    }

    @Override
    public List<Map<String, Object>> queryManagementOfficialAccountsList(Map<String,Object> map) {
        Map<String,Object> paraMap = new HashMap<>();
        paraMap.put("agentId",0);
        paraMap.put("status",map.get("status"));
        //总平台添加的公众号 代理商Id为0
        List<Map<String, Object>> agentOfficiaList = officialAccountsMapper.queryOfficialAccountsList(paraMap);
        List<Map<String, Object>> list = officialAccountsMapper.queryOfficialAccountsList(map);
        list.addAll(agentOfficiaList);
        return list;

    }

    @Override
    public Map<String, Object> queryManagementOfficialInfoById(Long id) {
        JSONObject map = new JSONObject();
        OfficialAccounts officialAccounts = officialAccountsMapper.queryOfficialCountInfo(id);
        map.put("imgUrl",officialAccounts.getImgUrl());
        map.put("name", officialAccounts.getName());
        map.put("appId", officialAccounts.getAppId());
        map.put("appSecret", officialAccounts.getAppSecret());
        map.put("freeCode", officialAccounts.getFreeCode());
        map.put("wxCount",officialAccounts.getWxCount());

        Integer originalPrice = officialAccounts.getOriginalPrice();
        Integer deductPrice = officialAccounts.getDeductPrice();
        double originalPrice1 = originalPrice.doubleValue()/100;
        double deductPrice1 = deductPrice.doubleValue()/100;
        map.put("originalPrice", originalPrice1);
        map.put("deductPrice", deductPrice1);
        map.put("upperLimit", officialAccounts.getUpperLimit());
        map.put("status",officialAccounts.getStatus());
        return map;
    }

    @Override
    public int updateOfficialAccount(OfficialAccounts officialAccounts) {
        officialAccounts.setDeductPrice(officialAccounts.getDeductPrice()*100);
        officialAccounts.setOriginalPrice(officialAccounts.getOriginalPrice()*100);
        return officialAccountsMapper.updateByPrimaryKeySelective(officialAccounts);

    }
}
