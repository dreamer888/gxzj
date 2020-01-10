package com.dlc.modules.sys.service.impl;

import com.dlc.common.utils.ConfigConstant;
import com.dlc.modules.qd.utils.MD5Util;
import com.dlc.modules.sys.dao.OfficialAccountsDao;
import com.dlc.modules.sys.entity.OfficialAccountsEntity;
import com.dlc.modules.sys.service.OfficialAccountsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("officialAccountsService")
public class OfficialAccountsServiceImpl implements OfficialAccountsService {

    @Autowired
    private OfficialAccountsDao officialAccountsDao;

    @Override
    public List<OfficialAccountsEntity> queryAppId(String AppId) {
        return officialAccountsDao.queryAppId(AppId);
    }

    @Override
    public OfficialAccountsEntity queryObject(Long id){
        OfficialAccountsEntity officialAccountsEntity = officialAccountsDao.queryObject(id);
        String[] areas = officialAccountsEntity.getArea().split(",");
        if(areas!=null){
            for(int i=0;i<=areas.length;i++){
                String province = areas[0];
                String city = areas[1];
                String area = areas[2];
                String address = province+city+area;
                officialAccountsEntity.setProvince(province);
                officialAccountsEntity.setCity(city);
                officialAccountsEntity.setArea(area);
                officialAccountsEntity.setAddress(address);
            }
        }
        return officialAccountsEntity;
    }

    @Override
    public List<OfficialAccountsEntity> queryList(Map<String, Object> map){
        return officialAccountsDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map){
        return officialAccountsDao.queryTotal(map);
    }

    @Override
    public void save(OfficialAccountsEntity officialAccounts){
        //后台添加的公众号关联到所有代理商，故代理商id设为0
        officialAccounts.setAgentId(0L);
        officialAccounts.setToken(MD5Util.MD5Encode(officialAccounts.getAppId(), "utf-8"));
        officialAccounts.setServerURL(ConfigConstant.PRO_VER_URL + "/wx/connect?code=" + officialAccounts.getAppId());
        officialAccounts.setCreateTime(new Date());
        String area = officialAccounts.getProvince()+','+officialAccounts.getCity()+','+officialAccounts.getArea();
        officialAccounts.setArea(area);
        officialAccountsDao.save(officialAccounts);
    }

    @Override
    public void update(OfficialAccountsEntity officialAccounts){
        String area = officialAccounts.getProvince()+','+officialAccounts.getCity()+','+officialAccounts.getArea();
        officialAccounts.setArea(area);
        officialAccountsDao.update(officialAccounts);
    }

    @Override
    public void delete(Long id){
        officialAccountsDao.delete(id);
    }

    @Override
    public void deleteBatch(Long[] ids){
        officialAccountsDao.deleteBatch(ids);
    }

}
