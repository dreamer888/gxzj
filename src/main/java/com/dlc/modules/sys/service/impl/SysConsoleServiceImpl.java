package com.dlc.modules.sys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.dlc.modules.sys.dao.SysConsoleDao;
import com.dlc.modules.sys.entity.SysConsoleEntity;
import com.dlc.modules.sys.service.SysConsoleService;



@Service("sysConsoleService")
public class SysConsoleServiceImpl implements SysConsoleService {
	@Autowired
	private SysConsoleDao sysConsoleDao;
	
	@Override
	public SysConsoleEntity queryObject(Integer id){
		return sysConsoleDao.queryObject(id);
	}
	
	@Override
	public List<SysConsoleEntity> queryList(Map<String, Object> map){
		return sysConsoleDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return sysConsoleDao.queryTotal(map);
	}
	
	@Override
	public void save(SysConsoleEntity sysConsole){
		sysConsoleDao.save(sysConsole);
	}
	
	@Override
	public void update(SysConsoleEntity sysConsole){
		sysConsoleDao.update(sysConsole);
	}
	
	@Override
	public void delete(Integer id){
		sysConsoleDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		sysConsoleDao.deleteBatch(ids);
	}
	
}
